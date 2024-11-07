package projekt.jedan.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VulnerabillityController {

    private boolean xssEnabled = false;
    private boolean accessControlEnabled = false;

    @PostMapping("/toggle")
    public Map<String, String> toggleVulnerability(@RequestParam String name, @RequestParam boolean enabled) {
        if ("xss".equals(name)) {
            xssEnabled = enabled;
        } else if ("accessControl".equals(name)) {
            accessControlEnabled = enabled;
        }

        return Map.of("message", name + " vulnerability " + (enabled ? "enabled" : "disabled"));
    }

    public boolean isXssEnabled() {
        return xssEnabled;
    }

    @GetMapping("/getAccessControlStatus")
    public Map<String, Boolean> getAccessControlStatus() {
        return Map.of("enabled", accessControlEnabled);
    }

    public boolean isAccessControlEnabled() {
        return accessControlEnabled;
    }
}

