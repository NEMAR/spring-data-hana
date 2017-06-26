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

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HanaQueryResult {

    public static HanaQueryResult EMPTY = new HanaQueryResult(new ResultContainer(Collections.emptyList()));

    private HanaQueryResult(ResultContainer container){
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
        }
    }
}
