FROM java:8

COPY MockMock.jar /opt/app/MockMock.jar


EXPOSE 8080
EXPOSE 2525

CMD ["java", "-jar", "/opt/app/MockMock.jar", "-p", "2525", "-h", "8080"]