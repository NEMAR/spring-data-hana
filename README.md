
Spring Data HANA
--------------------

The primary goal of the [Spring Data](http://projects.spring.io/spring-data/) project is to make it easier to build Spring-powered applications that use new data access technologies such as non-relational databases, map-reduce frameworks, and cloud based data services.

## Artifacts

### Maven

```xml
<dependency>
  <groupId>com.github.vogel612</groupId>
  <artifactId>spring-data-hana</artifactId>
  <version>1.5</version>
</dependency>
```

## Usage (Spring Boot)

* Following properties can be used in your `application.yml`:

    ```yml
    spring:
      hana:
        url: http://localhost:8086
        username: user
        password: ~
        database: test
        retention-policy: autogen
    ```

* Create `HanaDBConnectionFactory` and `HanaDBTemplate` beans:

    ```java
    @Configuration
    @EnableConfigurationProperties(HanaDBProperties.class)
    public class HanaDBConfiguration
    {
      @Bean
      public HanaDBConnectionFactory connectionFactory(final HanaDBProperties properties)
      {
        return new HanaDBConnectionFactory(properties);
      }

      @Bean
      public HanaDBTemplate<Point> hanaDBTemplate(final HanaDBConnectionFactory connectionFactory)
      {
        /*
         * You can use your own 'PointCollectionConverter' implementation, e.g. in case
         * you want to use your own custom measurement object.
         */
        return new HanaDBTemplate<>(connectionFactory, new PointConverter());
      }
      
      @Bean
      public DefaultHanaDBTemplate defaultTemplate(final HanaDBConnectionFactory connectionFactory)
      {
        /*
         * If you are just dealing with Point objects from 'hanadb-java' you could
         * also use an instance of class DefaultHanaDBTemplate.
         */
        return new DefaultHanaDBTemplate(connectionFactory);
      }
    }
    ```

* Use `HanaDBTemplate` to interact with the HanaDB database:

    ```java
    @Autowired
    private HanaDBTemplate<Point> hanaDBTemplate;

    hanaDBTemplate.createDatabase();
    final Point p = Point.measurement("disk")
      .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
      .tag("tenant", "default")
      .addField("used", 80L)
      .addField("free", 1L)
      .build();
    hanaDBTemplate.write(p);
    ```

## Building

Spring Data HanaDB uses Maven as its build system. 

```bash
mvn clean install
```
