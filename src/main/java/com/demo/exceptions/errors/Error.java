package com.demo.exceptions.errors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class Error {
    String code;
    String message;

    public static Error deserialize(String input) throws Exception {
        // Extract values from input
        Map<String, String> map = new HashMap<>();
        for (String part : input.split(";")) {
            String[] kv = part.trim().split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].replaceAll("^'(.*)'$", "$1"));
            }
        }

        if (!map.containsKey("typ")) {
            return new Error(ErrorCode.UNKNOWN.getCode(), input);
        }

        // Insert values from input into the message template
        var code = ErrorCode.valueOf(map.get("typ")).getCode();
        var messageTemplate = ErrorMessage.valueOf(map.get("typ")).getTemplate();
        if (map.containsKey("name")) {
            messageTemplate = messageTemplate.replace("%name", map.get("name"));
        }
        if (map.containsKey("min")) {
            messageTemplate = messageTemplate.replace("%min", map.get("min"));
        }
        if (map.containsKey("max")) {
            messageTemplate = messageTemplate.replace("%max", map.get("max"));
        }

        return new Error(code, messageTemplate);
    }
}
