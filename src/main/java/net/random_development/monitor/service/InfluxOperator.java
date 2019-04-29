package net.random_development.monitor.service;

public enum InfluxOperator {
    EQ("="),
    GT(">"),
    LT("<");

    private String value;

    InfluxOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
