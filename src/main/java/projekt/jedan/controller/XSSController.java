package projekt.jedan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class XSSController {

    @Autowired
    private VulnerabillityController vulnerabillityController;

    @GetMapping("/api/xss")
    public void handleXSS(@RequestParam String input, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");  // Set the content type to HTML

        if (vulnerabillityController.isXssEnabled()) {
            response.setContentType("text/html");
            // Reflect the input directly to demonstrate the vulnerability
            response.getWriter().write("<div>" + input + "</div>");
        } else {
            response.setContentType("text/plain");
            // Manually sanitize input to prevent XSS
            String safeInput = sanitizeInput(input);
            // Use <pre> or <code> tags to preserve the literal format of the sanitized input
            response.getWriter().write("<div>" + safeInput + "</div>");
        }
    }

    // Method to manually sanitize user input
    private String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder sanitized = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '<':
                    sanitized.append("&lt;");
                    break;
                case '>':
                    sanitized.append("&gt;");
                    break;
                case '&':
                    sanitized.append("&amp;");
                    break;
                case '"':
                    sanitized.append("&quot;");
                    break;
                default:
                    sanitized.append(c);
            }
        }
        return sanitized.toString();
    }
}
