package com.numo.portfolio.comm.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public abstract class Timestamped {
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public String getCreateTimeString() {
        return getTimeString(createdAt);
    }

    public String getUpdateTimeString() {
        return getTimeString(modifiedAt);
    }

    public static String getTimeString(LocalDateTime time) {
        if (time == null) {
            return "";
        }
        return getFormatTime(time, "yyyy-MM-dd");
    }

    public static String getFormatTime(LocalDateTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }
}
