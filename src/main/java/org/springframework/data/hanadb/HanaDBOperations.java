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

package org.springframework.data.hanadb;

import java.io.IOException;
import java.util.List;

import org.springframework.data.hanadb.query.HanaQuery;
import org.springframework.data.hanadb.query.HanaQueryResult;

public interface HanaDBOperations<T> {

    /**
     * Write a single measurement to the database.
     *
     * @param payload the measurement to write to
     */
    void write(T payload) throws IOException;

    /**
     * Write a set of measurements to the database.
     *
     * @param payload the values to write to
     */
    void write(List<T> payload) throws IOException;

    /**
     * Executes a query agains the database.
     *
     * @param query the query to execute
     * @return a List of time series data matching the query
     */
    HanaQueryResult query(final HanaQuery query);
}
