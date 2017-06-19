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

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.hanadb.converter.PointConverter;
import org.springframework.data.hanadb.data.Point;
import org.springframework.data.hanadb.query.HanaQuery;
import org.springframework.data.hanadb.query.HanaQueryResult;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HanaDBTemplate<T> extends HanaDBAccessor implements HanaDBOperations<T>
{
  private static final Logger LOGGER = LoggerFactory.getLogger(HanaDBTemplate.class);

  private PointConverter<T> converter;
  private final Gson gson = new Gson();

  public HanaDBTemplate()
  {

  }

  public HanaDBTemplate(final HanaDBConnectionFactory connectionFactory, final PointConverter<T> converter)
  {
    setConnectionFactory(connectionFactory);
    setConverter(converter);
  }


  public void setConverter(final PointConverter<T> converter)
  {
    this.converter = converter;
  }

  @Override
  public void afterPropertiesSet()
  {
    super.afterPropertiesSet();
    Assert.notNull(converter, "PointConverter is required");
  }

  @Override
  public void write(final T payload)
  {
    Preconditions.checkArgument(payload != null, "Parameter 'payload' must not be null");
    final String database = getDatabase();
    final String retentionPolicy = getRetentionPolicy();
    final BatchPoints ops = BatchPoints.database(database)
      .retentionPolicy(retentionPolicy)
      .consistency(HanaDB.ConsistencyLevel.ALL)
      .build();
    converter.convert(payload).forEach(ops::point);
    getConnection().write(ops);
  }

  @Override
  public void write(final List<T> payload)
  {
    payload.forEach(this::write);
  }

  @Override
  public HanaQueryResult query(HanaQuery query) {
    return null;
  }

  @Override
  public HanaQueryResult query(HanaQuery query, TimeUnit timeUnit) {
    return null;
  }
}
