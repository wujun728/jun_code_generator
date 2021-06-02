FROM java:8
MAINTAINER shichenyang89
ADD target/layui-rbac-1.0.jar a.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Xmx512m","-Xms512m","a.jar"]
