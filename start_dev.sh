#!/bin/sh
java -DENVIRONMENT=dev -jar resources/target/resources-1.1.jar server "resources/src/main/resources/config_dev.yaml"