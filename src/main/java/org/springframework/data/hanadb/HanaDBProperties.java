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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.hanadb")
public class HanaDBProperties
{
  @NotEmpty
  private String url;

  @NotEmpty
  private String writeEndpoint;

  @NotEmpty
  private String dataEndpoint;

  @NotEmpty
  private String statsEndpoint;

  @NotEmpty
  private String authorizationHeader;


  public String getUrl()
  {
    return url;
  }

  public void setUrl(final String url)
  {
    this.url = url;
  }

  public String getAuthorizationHeader() {
    return authorizationHeader;
  }

  public void setAuthorizationHeader(String authorizationHeader) {
    this.authorizationHeader = authorizationHeader;
  }

  public String getWriteEndpoint() {
    return writeEndpoint;
  }

  @Override
  public String toString()
  {
    return String.format("url: %s, authToken not included", url);
  }

  public void setWriteEndpoint(String writeEndpoint) {
    this.writeEndpoint = writeEndpoint;
  }

  public String getDataEndpoint() {
    return dataEndpoint;
  }

  public void setDataEndpoint(String dataEndpoint) {
    this.dataEndpoint = dataEndpoint;
  }

  public String getStatsEndpoint() {
    return statsEndpoint;
  }

  public void setStatsEndpoint(String statsEndpoint) {
    this.statsEndpoint = statsEndpoint;
  }
}
