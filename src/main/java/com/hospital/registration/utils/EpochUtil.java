package com.hospital.registration.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class EpochUtil {
    private EpochUtil() {}

    public static LocalDate toLocalDate(Long epochSeconds) {
        if (epochSeconds == null) return null;
        return Instant.ofEpochSecond(epochSeconds)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Long fromLocalDate(LocalDate date) {
        if (date == null) return null;
        return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }
}