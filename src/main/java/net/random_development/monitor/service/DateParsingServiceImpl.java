package net.random_development.monitor.service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Service;

@Service
public class DateParsingServiceImpl implements DateParsingService {

    private static final long MILISECONDS_IN_SECOND = 1000;
    private static final DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();

    @Override
    public int toTimestamp(String iso8601) {
        return (int) millisecondsToSeconds(parser.parseDateTime(iso8601).withZone(DateTimeZone.UTC).getMillis());
    }

    @Override
    public String fromTimestamp(int timestamp) {
        DateTime dateTime = new DateTime(secondsToMilliseconds(timestamp)).withZone(DateTimeZone.UTC);
        return parser.print(dateTime);
    }

    private long millisecondsToSeconds(long miliseconds) {
        return miliseconds / MILISECONDS_IN_SECOND;
    }


    private long secondsToMilliseconds(long miliseconds) {
        return miliseconds * MILISECONDS_IN_SECOND;
    }

}
