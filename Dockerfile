FROM openjdk:8
COPY target/RegisterAccount.jar /opt/register-account/RegisterAccount.jar
COPY src/main/resources/application.properties /opt/register-account/config/application.properties
ENTRYPOINT ["java", "-jar", "/opt/register-account/RegisterAccount.jar", "--spring.config.location=/opt/register-account/config/application.properties"]