package com.ubs.opsit.interviews;

import java.util.Arrays;

import static java.lang.System.lineSeparator;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.apache.commons.lang.Validate.notEmpty;

/**
 * Created by Adam on 2017-07-15.
 */
public class BerlinClock implements TimeConverter {
    private static final String FULL_TIME_FORMAT = "\\d\\d:\\d\\d:\\d\\d";

    private static final String YELLOW = "Y";
    private static final String RED = "R";
    private static final String OFF = "O";
    private static final String LINE_SEPARATOR = lineSeparator();

    @Override
    public String convertTime(String aTime) {
        validateTime(aTime);

        return buildClock(aTime.split(":")).toString();
    }

    private void validateTime(String aTime) {
        notEmpty(aTime, "Provided time is null or empty!");

        if (!aTime.matches(FULL_TIME_FORMAT)) {
            throw new IllegalArgumentException("Provided time " + aTime + " should match format 24H HH:MM:SS");
        }
    }

    private StringBuilder buildClock(String[] splitTime) {
        int[] convertedTime = Arrays.stream(splitTime).mapToInt(Integer::parseInt).toArray();
        StringBuilder clockBuilder = new StringBuilder();

        clockBuilder.append(getSecondsLamp(convertedTime[2]));
        clockBuilder.append(LINE_SEPARATOR);
        clockBuilder.append(getTopHourLamps(convertedTime[0]));
        clockBuilder.append(LINE_SEPARATOR);
        clockBuilder.append(getBottomHourLamps(convertedTime[0]));
        clockBuilder.append(LINE_SEPARATOR);
        clockBuilder.append(getTopMinutesLamps(convertedTime[1]));
        clockBuilder.append(LINE_SEPARATOR);
        clockBuilder.append(getBottomMinutesLamps(convertedTime[1]));

        return clockBuilder;
    }

    private String getBottomMinutesLamps(int minutes) {
        return getLamps(4, getBottomRowLampNumber(minutes), YELLOW);
    }

    private String getTopMinutesLamps(int minutes) {
        return getLamps(11, getTopRowLampNumber(minutes), YELLOW)
                .replace("YYY", "YYR");
    }

    private String getBottomHourLamps(int hours) {
        return getLamps(4, getBottomRowLampNumber(hours), RED);
    }

    private String getTopHourLamps(int hours) {
        return getLamps(4, getTopRowLampNumber(hours), RED);
    }

    private String getSecondsLamp(int seconds) {
        return seconds % 2 == 0 ? YELLOW : OFF;
    }

    private String getLamps(int lampsInARow, int litLamps, String lampType) {
        return repeat(lampType, litLamps) +
                repeat(OFF, lampsInARow - litLamps);
    }

    private int getBottomRowLampNumber(int timePart) {
        return timePart % 5;
    }

    private int getTopRowLampNumber(int timePart) {
        return (timePart - (timePart % 5)) / 5;
    }

}