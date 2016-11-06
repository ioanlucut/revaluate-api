FROM java:8-jre
MAINTAINER ioan.lucut88@gmail.com
EXPOSE 8085 8081
COPY ./resources/target/resources-1.0.jar /dist/
COPY ./resources/src/main/resources/config_prod.yaml /dist/
CMD java -DENVIRONMENT=prod -jar \
	/dist/resources-1.0.jar server "dist/config_prod.yaml"
