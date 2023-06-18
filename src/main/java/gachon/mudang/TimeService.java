package gachon.mudang;

import java.time.*;

// time function
// Display time when registering a post
public class TimeService {

    public static ZonedDateTime getPresentTime() {
        // Get the current local date and time
        LocalDateTime now = LocalDateTime.now();

        // Convert the local date and time to the specific time zone ("Asia/Seoul")
        return now.atZone(ZoneId.of("Asia/Seoul"));
    }

    public static String replaceProductDate(LocalDateTime date) {
        // Get the current date and time in the "Asia/Seoul" time zone
        ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul"));

        // Calculate the duration between the specified date and the current date and time
        Duration duration = Duration.between(date, now);

        // Calculate the period between the specified date and the current date
        Period period = Period.between(date.toLocalDate(), now.toLocalDate());

        // Determine the appropriate time format based on the duration and period

        if (duration.toHours() < 1) {
            return duration.toMinutes() + " 분 전";
        }
        if (duration.toDays() < 1) {
            return duration.toHours() + " 시간 전";
        }
        if (period.getMonths() < 1) {
            return period.getDays() + " 일 전";
        }
        if (period.getYears() < 1) {
            return period.getMonths() + " 달 전";
        }

        return period.getYears() + " 년 전";
    }
}
