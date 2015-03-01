## Maven
# UT tests are automatically started using `mvn clean install`
# IT tests are automatically disabled. Can be enabled using `mvn clean install -DskipITs=false or  mvn clean install -PexecITs`

# Default command to be used before commits: `mvn clean install -DskipITs=false`.
# Do `mvn clean install -DskipDbMigration=true`
# Do `java -jar target/application-1.0-SNAPSHOT.jar`
# Do `mvn clean install -PexecITs -DskipDbMigration=true`
# Do `java -jar target/application-1.0-SNAPSHOT.jar server config.yaml`
# Do `mvn clean install -PexecITs -DENVIRONMENT=prod`
