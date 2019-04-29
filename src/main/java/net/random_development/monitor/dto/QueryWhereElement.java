package net.random_development.monitor.dto;

import net.random_development.monitor.service.InfluxOperator;

public class QueryWhereElement {

    private String field;
    private String value;
    private InfluxOperator operator;

    public QueryWhereElement(String field, String value, InfluxOperator operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public InfluxOperator getOperator() {
        return operator;
    }

    public void setOperator(InfluxOperator operator) {
        this.operator = operator;
    }
}
