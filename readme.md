## Maven
# UT tests are automatically started using `mvn clean install`
# IT tests are automatically disabled. Can be enabled using `mvn clean install -DskipITs=false or  mvn clean install -PexecITs`

# Default command to be used before commits: `mvn clean install -DskipITs=false`.
# `mvn clean install -DskipCodeAnalysis=true -DskipDbMigration=true -DskipITs=fals`
