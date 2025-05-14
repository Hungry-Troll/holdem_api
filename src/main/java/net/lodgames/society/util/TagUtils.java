package net.lodgames.society.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

public class TagUtils {
    public static String tagsToString(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        return tags.stream()
                .map(tag -> tag.replaceAll(" ", "").toLowerCase().trim())
                .filter(tag -> !tag.isBlank())
                .reduce((tag1, tag2) -> tag1 + "," + tag2)
                .orElse(null);
    }

    public static List<String> getTagList(String tag) {
        return tag != null && !tag.trim().isEmpty()
                ? Arrays.asList(tag.split(","))
                : null;
    }
}