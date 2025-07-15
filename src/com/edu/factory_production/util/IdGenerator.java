package com.edu.factory_production.util;

import com.edu.factory_production.model.Detail;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdGenerator {
    private static int counter = 1;
    private static final Pattern ID_PATTERN = Pattern.compile("P-(\\d{3})");

    public static void initializeCounter(List<Detail> existingDetails) {
        int maxIdNum = 0;
        for (Detail detail : existingDetails) {
            Matcher matcher = ID_PATTERN.matcher(detail.getId());
            if (matcher.matches()) {
                try {
                    int idNum = Integer.parseInt(matcher.group(1));
                    if (idNum > maxIdNum) {
                        maxIdNum = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Игнорируем некорректные ID
                }
            }
        }
        counter = maxIdNum + 1;
    }

    public static String generateId() {
        return String.format("P-%03d", counter++);
    }
} 