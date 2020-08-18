package com.kowin.iot.udp.broadcastdemo.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class StringUtils {

    public static String join(CharSequence delimiter, String[] array) {
        return join(delimiter, Arrays.asList(array));
    }

    public static String join(CharSequence delimiter,
                              Iterable<? extends CharSequence> elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs: elements) {
            joiner.add(cs);
        }

        return joiner.toString();
    }

    public static String makePlaceholders(int len) {
        if (len < 1) {
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");

            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }

            return sb.toString();
        }
    }
}
