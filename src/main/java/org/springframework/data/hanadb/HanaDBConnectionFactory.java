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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class HanaDBConnectionFactory implements InitializingBean
{
  private static Logger logger = LoggerFactory.getLogger(HanaDBConnectionFactory.class);

  private HanaDB connection;

  private HanaDBProperties properties;

  public HanaDBConnectionFactory()
  {

  }

  public HanaDBConnectionFactory(final HanaDBProperties properties)
  {
    this.properties = properties;
  }

  public HanaDB getConnection()
  {
    Assert.notNull(getProperties(), "HanaDBProperties are required");
    if (connection == null)
    {
      connection = HanaDBFactory.connect(properties.getUrl(), properties.getUsername(), properties.getPassword());
      logger.debug("Using HanaDB '{}' on '{}'", properties.getDatabase(), properties.getUrl());
    }
    return connection;
  }

  /**
   * Returns the configuration properties.
   *
   * @return Returns the configuration properties
   */
  public HanaDBProperties getProperties()
  {
    return properties;
  }

  /**
   * Sets the configuration properties.
   *
   * @param properties The configuration properties to set
   */
  public void setProperties(final HanaDBProperties properties)
  {
    this.properties = properties;
  }

  @Override
  public void afterPropertiesSet() throws Exception
  {
    Assert.notNull(getProperties(), "HanaDBProperties are required");
  }
}
