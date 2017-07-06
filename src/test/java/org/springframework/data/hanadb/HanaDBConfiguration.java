package org.springframework.data.hanadb;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hanadb.data.Point;

@Configuration
@EnableConfigurationProperties(HanaDBProperties.class)
public class HanaDBConfiguration {
    @Bean
    public HanaDBTemplate<Point> template(final HanaDBProperties properties) {
        return new DefaultHanaDBTemplate(properties);
    }
}
