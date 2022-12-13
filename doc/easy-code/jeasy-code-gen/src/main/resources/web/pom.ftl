<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>${conf.parentArtifactId}</artifactId>
        <groupId>${conf.parentGroupId}</groupId>
        <version>3.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>${conf.webModuleName}</artifactId>
    <packaging>war</packaging>

    <properties>
        <skip_maven_deploy>true</skip_maven_deploy>
    </properties>

    <dependencies>
        <!-- Module -->
        <dependency>
            <groupId>${conf.parentGroupId}</groupId>
            <artifactId>${conf.shiroModuleName}</artifactId>
            <version>3.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>${conf.parentGroupId}</groupId>
            <artifactId>rabbitmq-demo-consumer</artifactId>
            <version>3.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>${conf.parentGroupId}</groupId>
            <artifactId>cxf-demo-provider</artifactId>
            <version>3.0-SNAPSHOT</version>
            <!-- WEB容器 启动时排除cxf-rt-transports-http-jetty依赖 -->
            <exclusions>
                <exclusion>
                    <groupId>org.apache.cxf</groupId>
                    <artifactId>cxf-rt-transports-http-jetty</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>${conf.parentGroupId}</groupId>
            <artifactId>msa-demo-provider</artifactId>
            <version>3.0-SNAPSHOT</version>
            <!-- 与cxf同时引入注意排除com.sun.xml.bind包避免冲突 -->
            <exclusions>
                <exclusion>
                    <groupId>com.sun.xml.bind</groupId>
                    <artifactId>jaxb-impl</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>javax.servlet</groupId>
                    <artifactId>javax.servlet-api</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.apache.tomcat.embed</groupId>
                    <artifactId>tomcat-embed-core</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>${conf.parentGroupId}</groupId>
            <artifactId>jeasy-scheduler</artifactId>
            <version>3.0-SNAPSHOT</version>
        </dependency>
        <!-- END -->

        <!-- web依赖包 -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jsp-api</artifactId>
        </dependency>
        <!-- END -->

        <!-- test -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
        </dependency>
        <!-- END -->
    </dependencies>

    <build>
        <finalName>${r'${'}project.artifactId}</finalName>
        <resources>
            <resource>
                <directory>src/main/resources/</directory>
                <includes>
                    <include>*.xml</include>
                </includes>
            </resource>
            <resource>
                <directory>src/main/resources/${r'${'}resourcePath}/</directory>
            </resource>
        </resources>

        <plugins>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat6-maven-plugin</artifactId>
                <configuration>
                    <port>8080</port>
                    <path>/${r'${'}project.artifactId}</path>
                    <uriEncoding>${r'${'}project.encoding}</uriEncoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <configuration>
                    <port>8080</port>
                    <path>/${r'${'}project.artifactId}</path>
                    <uriEncoding>${r'${'}project.encoding}</uriEncoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.mortbay.jetty</groupId>
                <artifactId>jetty-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
