package com.example.controller;

import com.example.service.OpenStackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openstack")
@Tag(name = "OpenStack集成", description = "OpenStack相关接口")
public class OpenStackController {

    private final OpenStackService openStackService;

    public OpenStackController(OpenStackService openStackService) {
        this.openStackService = openStackService;
    }

    @GetMapping("/console-url")
    @Operation(summary = "获取OpenStack控制台URL", description = "获取用户访问OpenStack控制台的URL")
    public ResponseEntity<String> getConsoleUrl() {
        String consoleUrl = openStackService.getConsoleUrl();
        return ResponseEntity.ok(consoleUrl);
    }
}
