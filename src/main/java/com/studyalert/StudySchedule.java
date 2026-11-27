package com.studyalert;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;

public class StudySchedule {
    private final Set<DayOfWeek> days;
    private final LocalTime start;
    private final LocalTime end;
    public StudySchedule(Set<DayOfWeek> days, LocalTime start, LocalTime end) {
        this.days = days;
        this.start = start;
        this.end = end;
    }
    public static StudySchedule load(Path file) throws IOException {
        if (!Files.exists(file)) {
            Files.writeString(file, """
                # Study Alert settings
                # Days: MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY
                study.days=MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY
                # Times use 24-hour format (19:00 = 7 PM)
                study.start=19:00
                study.end=21:00
                """);
        }
        Properties props = new Properties();
        try (Reader reader = Files.newBufferedReader(file)) {
            props.load(reader);
        }
        Set<DayOfWeek> days = EnumSet.noneOf(DayOfWeek.class);
        for (String day : props.getProperty("study.days", "").split(",")) {
            if (!day.isBlank()) {
                days.add(DayOfWeek.valueOf(day.trim().toUpperCase()));
            }
        }
        LocalTime start = LocalTime.parse(props.getProperty("study.start", "19:00").trim());
        LocalTime end = LocalTime.parse(props.getProperty("study.end", "21:00").trim());
        return new StudySchedule(days, start, end);
    }
    public boolean isStudyTime(LocalDateTime now) {
        LocalTime time = now.toLocalTime();
        return days.contains(now.getDayOfWeek())
                && !time.isBefore(start)
                && time.isBefore(end);
    }
    public String describe() {
        return "Study time: " + start + "–" + end;
    }
}