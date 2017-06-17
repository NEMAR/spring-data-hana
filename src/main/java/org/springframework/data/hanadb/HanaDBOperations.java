/*
 * Copyright 2016 the original author or authors.
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

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface HanaDBOperations<T>
{
  /**
   * Ensures that the configured database exists.
   */
  void createDatabase();

  /**
   * Write a single measurement to the database.
   *
   * @param payload the measurement to write to
   */
  void write(T payload);

  /**
   * Write a set of measurements to the database.
   *
   * @param payload the values to write to
   */
  void write(List<T> payload);

  /**
   * Executes a query agains the database.
   *
   * @param query the query to execute
   * @return a List of time series data matching the query
   */
  QueryResult query(final Query query);

  /**
   * Executes a query agains the database.
   *
   * @param query    the query to execute
   * @param timeUnit the time unit to be used for the query
   * @return a List of time series data matching the query
   */
  QueryResult query(final Query query, final TimeUnit timeUnit);

  /**
   * Ping the database.
   *
   * @return the response of the ping execution
   */
  Pong ping();

  /**
   * Return the version of the connected database.
   *
   * @return the version String, otherwise unknown
   */
  String version();
}