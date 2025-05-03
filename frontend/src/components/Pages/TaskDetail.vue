<template>
  <div class="bg-dark-bg min-h-screen pb-12">
    <!-- 页面标题 -->
    <div class="bg-sub-bg border-b border-gray-800 py-8 px-4">
      <div class="container mx-auto">
        <div class="flex flex-col md:flex-row md:items-center md:justify-between">
          <div>
            <div class="flex items-center mb-2">
              <span 
                class="px-2.5 py-0.5 rounded-full text-xs font-medium mr-2"
                :class="taskCategoryClass"
              >
                {{ $t(`taskList.categories.${task.category}`) }}
              </span>
              <span 
                class="px-2.5 py-0.5 rounded-full text-xs font-medium"
                :class="taskDifficultyClass"
              >
                {{ $t(`taskList.difficulties.${task.difficulty}`) }}
              </span>
            </div>
            <h1 class="text-[clamp(1.5rem,3vw,2.5rem)] font-bold text-white">
              {{ task.title }}
            </h1>
            <p class="text-gray-400 mt-2 max-w-2xl">
              {{ task.points }} {{ $t('taskDetail.points') }}
            </p>
          </div>
          
          <div class="mt-4 md:mt-0 flex items-center space-x-3">
            <div class="flex items-center text-gray-400">
              <i class="fa-solid fa-users mr-1"></i>
              <span>{{ task.solves || 0 }} {{ $t('taskDetail.solves') }}</span>
            </div>
            <div class="flex items-center text-gray-400">
              <i class="fa-solid fa-clock mr-1"></i>
              <span>{{ task.created_at || '-' }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 任务内容 -->
    <div class="container mx-auto px-4 mt-8">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- 任务描述 -->
        <div class="lg:col-span-2">
          <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 mb-6">
            <h2 class="text-xl font-semibold text-white mb-4">
              {{ $t('taskDetail.description') }}
            </h2>
            <div class="prose prose-invert max-w-none">
              <p v-html="task.description"></p>
            </div>
          </div>
          
          <!-- 附件 -->
          <div v-if="task.attachments && task.attachments.length > 0" class="bg-sub-bg border border-gray-800 rounded-xl p-6 mb-6">
            <h2 class="text-xl font-semibold text-white mb-4">
              {{ $t('taskDetail.attachments') }}
            </h2>
            <div class="space-y-3">
              <a 
                v-for="attachment in task.attachments" 
                :key="attachment.id" 
                :href="attachment.url" 
                target="_blank"
                class="flex items-center text-primary hover:text-primary/80 transition-colors"
              >
                <i class="fa-solid fa-download mr-2"></i>
                <span>{{ attachment.name }}</span>
              </a>
            </div>
          </div>
          
          <!-- 提示 -->
          <div v-if="task.hints && task.hints.length > 0" class="bg-sub-bg border border-gray-800 rounded-xl p-6 mb-6">
            <h2 class="text-xl font-semibold text-white mb-4">
              {{ $t('taskDetail.hints') }}
            </h2>
            <div class="space-y-4">
              <div v-for="hint in task.hints" :key="hint.id" class="bg-dark-bg border border-gray-700 rounded-lg p-4">
                <div class="flex items-center justify-between mb-2">
                  <h3 class="font-medium text-white">{{ hint.title }}</h3>
                  <span class="text-xs text-gray-400">
                    {{ hint.cost }} {{ $t('taskDetail.points') }}
                  </span>
                </div>
                <p class="text-gray-300">
                  {{ hint.content }}
                </p>
              </div>
            </div>
          </div>
          
          <!-- 提交答案 -->
          <div v-if="!isSolved" class="bg-sub-bg border border-gray-800 rounded-xl p-6">
            <h2 class="text-xl font-semibold text-white mb-4">
              {{ $t('taskDetail.submitAnswer') }}
            </h2>
            <form @submit.prevent="submitAnswer">
              <div class="mb-4">
                <input
                  type="text"
                  v-model="answer"
                  class="bg-dark-bg border border-gray-700 text-white rounded-lg block w-full px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all"
                  placeholder="{{ $t('taskDetail.answerPlaceholder') }}"
                  required
                />
              </div>
              <div class="flex items-center justify-end">
                <button
                  type="submit"
                  class="bg-primary hover:bg-primary/90 text-white font-medium rounded-lg px-6 py-2.5 transition-colors flex items-center space-x-2"
                  :disabled="loading"
                >
                  <span>{{ $t('taskDetail.submitButton') }}</span>
                  <div v-if="loading" class="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                </button>
              </div>
            </form>
          </div>
          
          <!-- 已解决状态 -->
          <div v-else class="bg-sub-bg border border-green-600 rounded-xl p-6">
            <div class="flex items-center">
              <div class="bg-green-600/20 p-3 rounded-full mr-4">
                <i class="fa-solid fa-check text-green-500 text-xl"></i>
              </div>
              <div>
                <h2 class="text-xl font-semibold text-white">
                  {{ $t('taskDetail.solved') }}
                </h2>
                <p class="text-green-500">
                  {{ $t('taskDetail.congratulations') }}
                </p>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 任务信息侧边栏 -->
        <div>
          <!-- 任务信息卡片 -->
          <div class="bg-sub-bg border border-gray-800 rounded-xl p-6 sticky top-24">
            <h2 class="text-xl font-semibold text-white mb-4">
              {{ $t('taskDetail.taskInfo') }}
            </h2>
            
            <!-- 任务作者 -->
            <div class="flex items-center mb-4">
              <img 
                src="https://picsum.photos/40/40?random=2" 
                alt="Author avatar" 
                class="w-10 h-10 rounded-full object-cover border-2 border-gray-700"
              />
              <div class="ml-3">
                <p class="text-sm font-medium text-white">
                  {{ $t('taskDetail.author') }}
                </p>
                <p class="text-xs text-gray-400">
                  {{ task.author || 'Unknown' }}
                </p>
              </div>
            </div>
            
            <!-- 难度 -->
            <div class="mb-4">
              <p class="text-sm text-gray-400 mb-1">
                {{ $t('taskDetail.difficulty') }}
              </p>
              <div class="flex items-center">
                <div class="flex items-center mr-2">
                  <div 
                    v-for="i in 5" 
                    :key="i" 
                    class="w-3 h-3 rounded-full mr-1"
                    :class="{ 'bg-green-500': i <= difficultyLevel, 'bg-gray-700': i > difficultyLevel }"
                  ></div>
                </div>
                <span class="text-sm text-gray-400">
                  {{ $t(`taskList.difficulties.${task.difficulty}`) }}
                </span>
              </div>
            </div>
            
            <!-- 解决统计 -->
            <div class="mb-4">
              <p class="text-sm text-gray-400 mb-1">
                {{ $t('taskDetail.solveRate') }}
              </p>
              <div class="flex items-center">
                <div class="w-full bg-gray-700 rounded-full h-2.5 mr-2">
                  <div 
                    class="bg-primary h-2.5 rounded-full"
                    :style="{ width: solveRate + '%' }"
                  ></div>
                </div>
                <span class="text-sm text-gray-400 min-w-[40px] text-right">
                  {{ solveRate }}%
                </span>
              </div>
            </div>
            
            <!-- 首次解决者 -->
            <div v-if="task.first_blood" class="mb-4">
              <p class="text-sm text-gray-400 mb-1">
                {{ $t('taskDetail.firstBlood') }}
              </p>
              <div class="flex items-center">
                <img 
                  src="https://picsum.photos/32/32?random=3" 
                  alt="First blood user" 
                  class="w-8 h-8 rounded-full object-cover border-2 border-red-500"
                />
                <span class="ml-2 text-sm text-gray-300">
                  {{ task.first_blood.username || 'Unknown' }}
                </span>
              </div>
            </div>
            
            <!-- 相关任务 -->
            <div v-if="relatedTasks.length > 0">
              <p class="text-sm font-medium text-white mb-2">
                {{ $t('taskDetail.relatedTasks') }}
              </p>
              <div class="space-y-2">
                <router-link 
                  v-for="relatedTask in relatedTasks" 
                  :key="relatedTask.id" 
                  :to="`/tasks/${relatedTask.id}`"
                  class="block text-sm text-gray-400 hover:text-white transition-colors"
                >
                  {{ relatedTask.title }}
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/store/modules/user.js';
import { useTasksStore } from '@/store/modules/tasks.js';

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const taskStore = useTaskStore();

// 状态
const loading = ref(false);
const answer = ref('');
const errorMessage = ref('');

// 获取任务详情
const fetchTask = async () => {
  try {
    loading.value = true;
    await taskStore.fetchTask(route.params.id);
  } catch (error) {
    window.$notify.error(
      $t('taskDetail.fetchErrorTitle'),
      $t('taskDetail.fetchErrorMessage')
    );
    router.push('/tasks');
  } finally {
    loading.value = false;
  }
};

// 提交答案
const submitAnswer = async () => {
  if (!answer.value.trim()) {
    errorMessage.value = $t('taskDetail.answerRequired');
    return;
  }
  
  errorMessage.value = '';
  loading.value = true;
  
  try {
    const result = await taskStore.submitAnswer(route.params.id, answer.value);
    
    if (result.correct) {
      // 更新用户已解决任务
      userStore.addSolvedTask(route.params.id);
      
      // 显示成功通知
      window.$notify.success(
        $t('taskDetail.correctTitle'),
        $t('taskDetail.correctMessage')
      );
    } else {
      // 显示错误通知
      window.$notify.error(
        $t('taskDetail.incorrectTitle'),
        $t('taskDetail.incorrectMessage')
      );
    }
  } catch (error) {
    errorMessage.value = error.response?.data?.message || $t('taskDetail.submitError');
  } finally {
    loading.value = false;
  }
};

// 计算属性：当前任务
const task = computed(() => taskStore.currentTask || {});

// 计算属性：任务是否已解决
const isSolved = computed(() => {
  return userStore.solvedTasks.includes(route.params.id);
});

// 计算属性：任务难度级别（数字）
const difficultyLevel = computed(() => {
  const levels = {
    'easy': 1,
    'medium': 3,
    'hard': 4,
    'expert': 5
  };
  return levels[task.value.difficulty] || 3;
});

// 计算属性：解决率
const solveRate = computed(() => {
  if (!task.value.total_teams || task.value.total_teams === 0) {
    return 0;
  }
  return Math.round((task.value.solves / task.value.total_teams) * 100);
});

// 计算属性：相关任务
const relatedTasks = computed(() => {
  return taskStore.tasks
    .filter(t => t.category === task.value.category && t.id !== task.value.id)
    .slice(0, 3);
});

// 计算属性：任务分类样式
const taskCategoryClass = computed(() => {
  const classes = {
    'web': 'bg-web-bg/20 text-web-text',
    'crypto': 'bg-crypto-bg/20 text-crypto-text',
    'pwn': 'bg-pwn-bg/20 text-pwn-text',
    'reversing': 'bg-reversing-bg/20 text-reversing-text',
    'forensics': 'bg-forensics-bg/20 text-forensics-text',
    'misc': 'bg-misc-bg/20 text-misc-text'
  };
  return classes[task.value.category] || 'bg-gray-700 text-gray-300';
});

// 计算属性：任务难度样式
const taskDifficultyClass = computed(() => {
  const classes = {
    'easy': 'bg-green-500/20 text-green-400',
    'medium': 'bg-yellow-500/20 text-yellow-400',
    'hard': 'bg-red-500/20 text-red-400',
    'expert': 'bg-purple-500/20 text-purple-400'
  };
  return classes[task.value.difficulty] || 'bg-gray-700 text-gray-300';
});

// 监听路由变化，重新加载任务
watch(() => route.params.id, () => {
  fetchTask();
});

// 初始化
onMounted(() => {
  fetchTask();
});
</script>

<style scoped>
/* 动画效果 */
.animate-spin {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

/* 自定义Markdown样式 */
.prose-invert {
  color: rgba(209, 213, 219, 1);
}

.prose-invert h1,
.prose-invert h2,
.prose-invert h3,
.prose-invert h4 {
  color: rgba(255, 255, 255, 1);
}

.prose-invert a {
  color: rgba(59, 130, 246, 1);
}

.prose-invert code {
  color: rgba(249, 115, 22, 1);
  background-color: rgba(30, 41, 59, 1);
  padding: 0.125rem 0.25rem;
  border-radius: 0.25rem;
}
</style>
