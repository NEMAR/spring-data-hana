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

package org.springframework.data.hanadb.query;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HanaQueryResult {

    public static HanaQueryResult EMPTY = new HanaQueryResult(new ResultContainer(Collections.emptyList()));

    private HanaQueryResult(ResultContainer container) {
        this.resultContainer = container;
    }

    @SerializedName("d")
    private ResultContainer resultContainer;

    private static class ResultContainer {

        @SerializedName("results")
        private List<Entry> entries;
        private ResultContainer(List<Entry> results) {
            this.entries = results;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).addValue(entries).toString();
        }

    }
    public List<HanaQueryResult.Entry> results() {
        return resultContainer.entries;
    }

    private static class Entry {
        @SerializedName("__metadata")
        private Entry.Metadata metadata;
        @SerializedName("ZEITREIHE")
        private String zeitreihe;
        @SerializedName("VALUE")
        private BigDecimal value;
        @SerializedName("TIME")
        private String time;
        @SerializedName("GREGORIAN_DATE")
        private Calendar date;
        @SerializedName("RECEIVED")
        private Calendar received;
        private static class Metadata {

            @SerializedName("type")
            private String type;
            @SerializedName("url")
            private String url;
            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("type", type)
                        .add("url", url)
                        .toString();
            }
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("metadata", metadata)
                    .add("zeitreihe", zeitreihe)
                    .add("value", value)
                    .add("time", time)
                    .add("date", date)
                    .add("received", received)
                    .toString();
        }
    }
    @Override
    public String toString() {
        return resultContainer.toString();
    }
}
