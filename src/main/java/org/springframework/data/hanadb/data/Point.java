/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
