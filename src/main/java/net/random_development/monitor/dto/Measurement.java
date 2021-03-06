package net.random_development.monitor.dto;

public class Measurement {

    private int time;
    private double value;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "time=" + time +
                ", value=" + value +
                '}';
    }
}
