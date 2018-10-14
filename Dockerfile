FROM tomcat:8.5.34-jre8
VOLUME /tmp
COPY target/time-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/app.war