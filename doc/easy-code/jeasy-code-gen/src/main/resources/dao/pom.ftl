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

    <artifactId>${conf.daoModuleName}</artifactId>
    <packaging>jar</packaging>

    <properties>
        <skip_maven_deploy>true</skip_maven_deploy>
    </properties>

    <dependencies>
        <!-- Module -->
        <dependency>
            <groupId>${conf.parentGroupId}</groupId>
            <artifactId>${conf.coreArtifactId}</artifactId>
            <version>3.0-SNAPSHOT</version>
        </dependency>
        <!-- END -->
    </dependencies>

    <build>
        <finalName>${r'${'}project.artifactId}</finalName>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.*</include>
                </includes>
            </resource>
        </resources>
    </build>
</project>
