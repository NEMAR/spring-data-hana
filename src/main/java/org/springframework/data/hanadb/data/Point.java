package org.springframework.data.hanadb.data;


import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Point {

    @SerializedName("ZEITREIHE")
    private final String timeseries;

    @SerializedName("TIME")
    private final long timestamp;

    @SerializedName("VALUE")
    private final BigDecimal value;


    public String getTimeseries() {
        return timeseries;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BigDecimal getValue() {
        return value;
    }

    private Point(Point.Builder builder){
        this.timeseries = builder.timeseries;
        this.timestamp = builder.timestamp;
        this.value = builder.value;
    }

    public static class Builder {

        private String timeseries;
        private Long timestamp;
        private BigDecimal value;

        public Point build() {
            if (timeseries == null || timestamp == null || value == null) {
                throw new IllegalStateException("Builder is missing required arguments");
            }
            return new Point(this);
        }

        public Builder timeseries(String timeseries) {
            this.timeseries = timeseries;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder value(double value) {
            this.value = BigDecimal.valueOf(value);
            return this;
        }
    }
}
