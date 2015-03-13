#!/bin/sh
java -DENVIRONMENT=dev -jar application/target/application-1.0.jar server "application/src/main/resources/config_dev.yaml"