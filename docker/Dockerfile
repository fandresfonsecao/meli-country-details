FROM tomcat:jdk15-openjdk-oracle

COPY /validaIp-1.0.0.war /usr/local/tomcat/webapps/validaIp.war
COPY /sql/meli_db_script.sql /docker-entrypoint-initdb.d/