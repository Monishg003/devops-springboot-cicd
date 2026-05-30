package com.devops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "DevOps Demo App");
        response.put("version", "1.0.0");
        return response;
    }

    @GetMapping("/api/info")
    public Map<String, String> info() {
        Map<String, String> response = new HashMap<>();
        response.put("engineer", "Monish G");
        response.put("stack", "Spring Boot + Docker + GitHub Actions");
        response.put("phase", "CI/CD Learning");
        return response;
    }
}
