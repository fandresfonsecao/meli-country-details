FROM tomcat:jdk15-openjdk-oracle

COPY /target/validaIp-1.0.0.war /usr/local/tomcat/webapps/validaIp.war