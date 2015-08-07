# API Framework - 使用说明

- 当前版本：0.4.0
- 发布日期：2015-08-07

## 配置方法

> 以 Goldmine 项目为例

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.alimama.goldmine</groupId>
    <artifactId>goldmine-api</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.7</java.version>
        <junit.version>4.12</junit.version>
        <servlet.version>3.1.0</servlet.version>
        <spring.version>4.1.6.RELEASE</spring.version>
        <maven-compiler-plugin.version>3.3</maven-compiler-plugin.version>
        <maven-surefire-plugin.version>2.18.1</maven-surefire-plugin.version>
        <maven-war-plugin.version>2.6</maven-war-plugin.version>
        <tomcat7-maven-plugin.version>2.2</tomcat7-maven-plugin.version>
    </properties>

    <dependencies>
        <!-- JUnit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <!-- Servlet -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>${servlet.version}</version>
            <scope>provided</scope>
        </dependency>
        <!-- Spring Test -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
            <scope>test</scope>
        </dependency>
        <!-- API Framework -->
        <dependency>
            <groupId>com.adchina.api</groupId>
            <artifactId>api-framework</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <!-- Resource Filter -->
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <plugins>
            <!-- Compile Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven-compiler-plugin.version}</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
            <!-- Test Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven-surefire-plugin.version}</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
            <!-- War Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>${maven-war-plugin.version}</version>
                <configuration>
                    <warName>ROOT</warName>
                </configuration>
            </plugin>
            <!-- Tomcat Plugin -->
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>${tomcat7-maven-plugin.version}</version>
                <configuration>
                    <path>/</path>
                    <port>8080</port>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>dev</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <maven.jdbc.host>localhost</maven.jdbc.host>
                <maven.jdbc.port>3306</maven.jdbc.port>
                <maven.jdbc.db>goldmine</maven.jdbc.db>
                <maven.jdbc.username>root</maven.jdbc.username>
                <maven.jdbc.password>root</maven.jdbc.password>
            </properties>
        </profile>
        <profile>
            <id>test</id>
            <properties>
                <maven.jdbc.host>xxx.xxx.xxx.xxx</maven.jdbc.host>
                <maven.jdbc.port>3306</maven.jdbc.port>
                <maven.jdbc.db>xxx</maven.jdbc.db>
                <maven.jdbc.username>xxx</maven.jdbc.username>
                <maven.jdbc.password>xxx</maven.jdbc.password>
            </properties>
        </profile>
        <profile>
            <id>prod</id>
            <properties>
                <maven.jdbc.host>xxx.xxx.xxx.xxx</maven.jdbc.host>
                <maven.jdbc.port>3306</maven.jdbc.port>
                <maven.jdbc.db>xxx</maven.jdbc.db>
                <maven.jdbc.username>xxx</maven.jdbc.username>
                <maven.jdbc.password>xxx</maven.jdbc.password>
            </properties>
        </profile>
    </profiles>

</project>
```

### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
         http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0">

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring.xml</param-value>
    </context-param>

    <filter>
        <filter-name>corsFilter</filter-name>
        <filter-class>com.adchina.api.cors.CorsFilter</filter-class>
        <init-param>
            <param-name>allowOrigin</param-name>
            <param-value>*</param-value>
        </init-param>
        <init-param>
            <param-name>allowMethods</param-name>
            <param-value>GET,POST,PUT,DELETE,OPTIONS</param-value>
        </init-param>
        <init-param>
            <param-name>allowHeaders</param-name>
            <param-value>Content-Type,X-Username,X-Token</param-value>
        </init-param>
        <init-param>
            <param-name>exposeHeaders</param-name>
            <param-value>Content-Type,X-Token</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>corsFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <filter>
        <filter-name>EncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>EncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <error-page>
        <error-code>404</error-code>
        <location>/404.html</location>
    </error-page>

</web-app>
```

### log4j.properties

```properties
log4j.rootLogger=DEBUG,console,file

log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%p %c (%L) - %m%n

log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
log4j.appender.file.file=${catalina.base}/logs/goldmine-api/log
log4j.appender.file.DatePattern='_'yyyyMMdd
log4j.appender.file.encoding=UTF-8
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{HH:mm:ss,SSS} %p %c (%L) - %m%n

log4j.logger.org.springframework=ERROR
log4j.logger.org.apache.ibatis=ERROR
log4j.logger.org.mybatis.spring=ERROR
log4j.logger.org.hibernate.validator=ERROR
log4j.logger.org.jboss.logging=ERROR
```

### config.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://${maven.jdbc.host}:${maven.jdbc.port}/${maven.jdbc.db}
jdbc.username=${maven.jdbc.username}
jdbc.password=${maven.jdbc.password}
jdbc.max_total=100
jdbc.max_idle=10
```

### spring.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.alimama.goldmine.api">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        <context:exclude-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
    </context:component-scan>

    <context:property-placeholder location="classpath:config.properties"/>

    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxTotal" value="${jdbc.max_total}"/>
        <property name="maxIdle" value="${jdbc.max_idle}"/>
    </bean>

    <import resource="classpath:spring-mybatis.xml"/>

</beans>
```

### spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <context:component-scan base-package="com.adchina.api.advice"/>
    <context:component-scan base-package="com.alimama.goldmine.api.controller"/>

    <bean id="objectMapper" class="com.adchina.api.json.CustomObjectMapper">
        <property name="camelCaseToLowerCaseWithUnderscores" value="true"/>
        <property name="dateFormatPattern" value="yyyy-MM-dd HH:mm:ss"/>
    </bean>

    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper" ref="objectMapper"/>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <mvc:default-servlet-handler/>

    <bean class="org.springframework.validation.beanvalidation.MethodValidationPostProcessor"/>

    <aop:aspectj-autoproxy proxy-target-class="true"/>

    <bean id="tokenManager" class="com.adchina.api.security.impl.DefaultTokenManager"/>

    <bean id="securityAspect" class="com.adchina.api.security.SecurityAspect">
        <property name="tokenManager" ref="tokenManager"/>
        <property name="tokenName" value="X-Token"/>
    </bean>

</beans>
```

### spring-mybatis.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd">

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis.xml"/>
        <property name="mapperLocations" value="classpath:mybatis-mapper.xml"/>
        <property name="typeAliasesPackage" value="com.alimama.goldmine.api.result"/>
    </bean>

    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>

    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <tx:annotation-driven/>

    <bean id="dataAccessor" class="com.adchina.api.jdbc.dao.impl.DefaultDataAccessor">
        <property name="sqlSession" ref="sqlSession"/>
    </bean>

    <bean id="idGenerator" class="com.adchina.api.jdbc.id.impl.DefaultIdGenerator"/>

</beans>
```

### mybatis.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>

    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

    <plugins>
        <plugin interceptor="com.adchina.api.jdbc.paging.MySQLPagingInterceptor">
            <property name="paging" value=":paging"/>
        </plugin>
    </plugins>

</configuration>
```

### mybatis-mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="goldmine">

    ...

</mapper>
```

## 使用方法

参见示例代码
