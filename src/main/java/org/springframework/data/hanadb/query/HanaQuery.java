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

public class HanaQuery {

    private final boolean raw;
    private final String queryText;

    private HanaQuery(HanaQuery.Builder builder) {
        this.raw = builder.raw;
        this.queryText = builder.queryText;
    }

    public static class Builder {

        private boolean raw;
        private String queryText;
        public Builder() {
        }

        public Builder raw(boolean value) {
            this.raw = value;
            return this;
        }

        public Builder text(String queryText) {
            this.queryText = queryText;
            return this;
        }

        public HanaQuery build() {
            if (queryText == null) {
                throw new IllegalStateException("We do actually need a query text");
            }
            return new HanaQuery(this);
        }

    }

    public String getQueryText() {
        return queryText;
    }

    public boolean isRaw() {
        return raw;
    }
}
