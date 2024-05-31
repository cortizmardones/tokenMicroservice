package com.example.demo.utils;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

import java.nio.charset.StandardCharsets;

import org.owasp.html.Sanitizers;

public class SanitizationUtil {
	
    public static String sanitizeInput(String input) {
    	return htmlEscape(input, String.valueOf(StandardCharsets.UTF_8));
    }
    
    public static String sanitizeInputOwasp(String input) {    	
        return Sanitizers.FORMATTING.and(Sanitizers.LINKS).sanitize(input);
    }

}
