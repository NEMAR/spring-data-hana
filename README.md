
Spring Data HANA
--------------------

The primary goal of the [Spring Data](http://projects.spring.io/spring-data/) project is to make it easier to build Spring-powered applications that use new data access technologies such as non-relational databases, map-reduce frameworks, and cloud based data services.

## Artifacts

### Maven

```xml
<dependency>
  <groupId>com.github.vogel612</groupId>
  <artifactId>spring-data-hanadb</artifactId>
  <version>1.5</version>
</dependency>
```

## Usage (Spring Boot)

* Following properties can be used in your `application.yml`:

    ```yml
    spring:
      hana:
        url: http://localhost/
        authorization-header: basic authenticationToken==
        data-endpoint: SensorData
        stats-endpoint: SensorStats
    ```
  When your connection is based on HTTPS it may be necessary to specify a `trust-store` under `hana` like so:
  
  ```yml
      trust-store:
        location: /path/to/java/keystore/file
        password: securepassword
  ```
  
  The information given here will be used to set the system properties `javax.net.ssl.trustStore` and
  `javax.net.ssl.trustStorePassword`, which in turn govern how SSL certificates are validated.
   

* Create `HanaDBConnectionFactory` and `HanaDBTemplate` beans:

    ```java
    @Configuration
    @EnableConfigurationProperties(HanaDBProperties.class)
    public class HanaDBConfiguration {
        @Bean
        public HanaDBTemplate<Point> template(final HanaDBProperties properties) {
            /*
             * You can specify a custom PointConverter implementation as the second parameter, 
             * if you want to use your own Measurement container  
             */
            return new DefaultHanaDBTemplate(properties);
        }
    }
    ```

* Use `HanaDBTemplate` to interact with the HanaDB database:

    ```java
    @Autowired
    private HanaDBTemplate<Point> hanaDBTemplate;

    final Point p = Point.timeseries("disk")
      .time(System.currentTimeMillis())
      .value(12.25)
      .build();
    hanaDBTemplate.write(p);
    ```

## Building

Spring Data HanaDB uses Maven as its build system. 

```bash
mvn clean install
```
