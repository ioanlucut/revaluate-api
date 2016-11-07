#!/bin/sh
nohup java -DENVIRONMENT=prod -jar resources/target/resources-1.1.jar server "resources/src/main/resources/config_prod.yaml" 2>&1 >> logfile.log &