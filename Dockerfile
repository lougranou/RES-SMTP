FROM java:8

COPY MockMock.jar /opt/app/MockMock.jar

EXPOSE 25 8282

CMD ["java", "-jar", "/opt/app/MockMock.jar"]