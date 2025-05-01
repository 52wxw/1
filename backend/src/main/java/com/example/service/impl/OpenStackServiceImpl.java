package com.example.service.impl;

import com.example.service.OpenStackService;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenStackServiceImpl implements OpenStackService {

    @Value("${openstack.auth.url}")
    private String authUrl;

    @Value("${openstack.username}")
    private String username;

    @Value("${openstack.password}")
    private String password;

    @Value("${openstack.domain.name}")
    private String domainName;

    @Value("${openstack.project.name}")
    private String projectName;

    @Value("${openstack.console.url}")
    private String consoleUrl;

    @Override
    public String getConsoleUrl() {
        String token = authenticate();
        return consoleUrl + "?token=" + token;
    }

    @Override
    public boolean validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", token);
        
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                authUrl + "/auth/tokens", 
                HttpMethod.GET, 
                entity, 
                String.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }

    private String authenticate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // 构建认证请求体
        Map<String, Object> authRequest = new HashMap<>();
        Map<String, Object> identity = new HashMap<>();
        Map<String, Object> passwordObj = new HashMap<>();
        Map<String, Object> user = new HashMap<>();
        Map<String, Object> domain = new HashMap<>();
        Map<String, Object> scope = new HashMap<>();
        Map<String, Object> project = new HashMap<>();
        
        domain.put("name", domainName);
        user.put("name", username);
        user.put("password", password);
        user.put("domain", domain);
        passwordObj.put("user", user);
        identity.put("methods", List.of("password"));
        identity.put("password", passwordObj);
        
        project.put("name", projectName);
        project.put("domain", domain);
        scope.put("project", project);
        
        authRequest.put("auth", Map.of("identity", identity, "scope", scope));
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(authRequest, headers);
        
        // 发送认证请求
        ResponseEntity<Void> response = restTemplate.exchange(
            authUrl + "/auth/tokens", 
            HttpMethod.POST, 
            request, 
            Void.class
        );
        
        // 从响应头获取令牌
        return response.getHeaders().getFirst("X-Subject-Token");
    }
}
