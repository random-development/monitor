package net.random_development.monitor.service;

public interface DateParsingService {

    int toTimestamp(String iso8601);

    String fromTimestamp(int timestamp);

}
