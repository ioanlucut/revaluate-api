#!/bin/sh
java -DENVIRONMENT=local -jar resources/target/resources-1.0.jar server "resources/src/main/resources/config_local.yaml"