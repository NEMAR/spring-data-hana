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

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.hanadb.converter.PointConverter;
import org.springframework.data.hanadb.data.Point;
import org.springframework.data.hanadb.query.HanaQuery;
import org.springframework.data.hanadb.query.HanaQueryResult;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HanaDBTemplate<T> extends HanaDBAccessor implements HanaDBOperations<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HanaDBTemplate.class);

    private PointConverter<T> converter;
    private final Gson gson;

    public HanaDBTemplate() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Point.class, (JsonSerializer<Point>)(Point src, Type type, JsonSerializationContext context) -> {
            JsonObject obj = new JsonObject();
            // serialize it all to strings for reasons..
            obj.addProperty("ZEITREIHE", src.getTimeseries());
            obj.addProperty("TIME", String.valueOf(src.getTimestamp()));
            obj.addProperty("VALUE", src.getValue().toString());
            return obj;
        });
        gson = builder.create();
    }

    public HanaDBTemplate(final HanaDBProperties properties, final PointConverter<T> converter) {
        this();

        setProperties(properties);
        setConverter(converter);
    }


    public void setConverter(final PointConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notNull(converter, "PointConverter is required");

        if (StringUtils.hasText(getProperties().getTrustStore().getLocation())) {
            System.setProperty("javax.net.ssl.trustStore", getProperties().getTrustStore().getLocation());
            System.setProperty("javax.net.ssl.trustStorePassword", getProperties().getTrustStore().getPassword());
        }
        // FIXME setting an all-trusting hostname verifier is a really fucking stupid idea, but hey
        HttpsURLConnection.setDefaultHostnameVerifier((a, b) -> true);
    }

    @Override
    public void write(final T payload) {
        Objects.requireNonNull(payload, "Parameter 'payload' must not be null");
        HttpURLConnection connection = null;
        try {
            final String hanaUrl = getProperties().getUrl() + getProperties().getDataEndpoint();
            connection = (HttpURLConnection) new URL(hanaUrl).openConnection();
            connection.setRequestProperty("Authorization", getProperties().getAuthorizationHeader());
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.connect();

            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                // this call appends the json result to the output stream
                Point data = converter.convert(payload);
                gson.toJson(data, Point.class, out);
                out.flush();
            }

            int responseCode = connection.getResponseCode();
            String response;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
            LOGGER.info("Status {}, Response {}", responseCode, response);
        } catch (IOException e) {
            LOGGER.warn("Something went wrong when writing payload.");
            LOGGER.warn("Debug information:", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void write(final List<T> payload) {
        payload.forEach(this::write);
    }

    @Override
    public HanaQueryResult query(HanaQuery query) {
        Objects.requireNonNull(query, "Query must not be null");

        HttpURLConnection connection = null;
        try {
            final String queryText = query.getQueryText();
            String url = getProperties().getUrl() + (query.isRaw() ? "Raw" : "")  + getProperties().getDataEndpoint() + "?$format=json" + (queryText.length() > 0 && queryText.startsWith("&") ? "" : "&") + queryText;
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", getProperties().getAuthorizationHeader());

            connection.connect();

            int code = connection.getResponseCode();
            String response;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
            LOGGER.info("Query: Status {}, Response {}", code, response);
            return gson.fromJson(response, HanaQueryResult.class);

        } catch (IOException e) {
            LOGGER.warn("Something went really wrong when executing a query: {}");
            LOGGER.warn("Debug information. {}", e);
            return HanaQueryResult.EMPTY;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
