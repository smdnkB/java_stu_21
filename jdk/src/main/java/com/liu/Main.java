package com.liu;

import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    // 匹配常见番号格式的正则表达式, 例如 ABC-123, ipx515 等, 不区分大小写
    private static final Pattern CODE_PATTERN = Pattern.compile("([A-Z]{2,5})-?(\\d{2,5})", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {

        String s = parseCode("と絶頂汁まみれで交わりまabSSNI-097くった3日間 楓カレン");
        System.out.println(s);

    }

    public static String parseCode(String title) {
        if (title == null || title.isEmpty()) {
            return null;
        }
        Matcher matcher = CODE_PATTERN.matcher(title);
        if (matcher.find()) {
            // group(1) 是字母部分, group(2) 是数字部分
            // 统一格式化为 "字母-数字"
            return matcher.group(1).toUpperCase() + "-" + matcher.group(2);
        }
        return null;
    }
}