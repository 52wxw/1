package com.example.service.impl;

import com.example.model.Ranking;
import com.example.model.Submission;
import com.example.model.User;
import com.example.repository.RankingRepository;
import com.example.repository.SubmissionRepository;
import com.example.repository.UserRepository;
import com.example.service.RankingService;
import com.example.websocket.RankingWebSocketHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RankingServiceImpl implements RankingService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final RankingRepository rankingRepository;
    private final RankingWebSocketHandler rankingWebSocketHandler;

    public RankingServiceImpl(SubmissionRepository submissionRepository,
                              UserRepository userRepository,
                              RankingRepository rankingRepository,
                              RankingWebSocketHandler rankingWebSocketHandler) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.rankingRepository = rankingRepository;
        this.rankingWebSocketHandler = rankingWebSocketHandler;
    }

    @Override
    public void updateRanking(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        // 计算用户正确提交数（返回 long 类型）
        long correctCount = submissionRepository.findByUserAndCorrectIsTrue(user).size();

        // 更新或创建排名记录
        rankingRepository.findByUser(user).ifPresentOrElse(
            ranking -> {
                // 关键修正：假设实体类的 score 和 solvedTasks 为 Long 类型（而非 int）
                ranking.setScore(correctCount * 100); // 直接赋值 long，无需转换
                ranking.setSolvedTasks(correctCount); // 直接赋值 long，无需转换
                ranking.setLastUpdate(LocalDateTime.now());
            },
            () -> {
                Ranking newRanking = Ranking.builder()
                        .user(user)
                        .score(correctCount * 100) // 直接使用 long 类型
                        .solvedTasks(correctCount) // 直接使用 long 类型
                        .lastUpdate(LocalDateTime.now())
                        .build();
                rankingRepository.save(newRanking);
            }
        );

        // 广播最新排名
        broadcastRankings();
    }

    @Override
    public List<Ranking> getTopRankings(int limit) {
        // 假设 repository 方法返回 Long 类型的 score 和 solvedTasks
        return rankingRepository.findTop20ByOrderByScoreDescSolvedTasksDescLastUpdateAsc();
    }

    @Override
    public Ranking getRankingByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        return rankingRepository.findByUser(user)
                .orElse(null);
    }

    @Override
    public void broadcastRankings() {
        List<Ranking> topRankings = getTopRankings(20);
        String rankingsJson = topRankings.stream()
                .map(r -> "{\"username\":\"" + r.getUser().getUsername() + "\", " +
                        "\"score\":" + r.getScore() + ", " +
                        "\"solvedTasks\":" + r.getSolvedTasks() + "}")
                .collect(Collectors.joining(",", "[", "]"));
        rankingWebSocketHandler.broadcastRankings(rankingsJson);
    }
}
