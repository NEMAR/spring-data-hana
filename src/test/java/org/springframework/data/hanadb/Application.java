package org.springframework.data.hanadb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hanadb.data.Point;
import org.springframework.data.hanadb.query.HanaQuery;
import org.springframework.data.hanadb.query.HanaQueryResult;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private HanaDBTemplate<Point> dbTemplate;

    @Override
    public void run(final String... args) throws Exception {
        // Create some data...
        final Point p1 = new Point.Builder()
                .timeseries("TEST")
                .timestamp(System.currentTimeMillis())
                .value(1337)
                .build();
        final Point p2 = new Point.Builder()
                .timeseries("TEST")
                .timestamp(System.currentTimeMillis())
                .value(new BigDecimal("1251.512513"))
                .build();
        dbTemplate.write(Arrays.asList(p1, p2));

        // ... and query the latest data
        final String q = "";
        final HanaQuery query = new HanaQuery.Builder().raw(true).text("").build();
        final HanaQueryResult queryResult = dbTemplate.query(query);
        logger.info(queryResult.toString());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
