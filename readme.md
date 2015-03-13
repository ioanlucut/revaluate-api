# Maven
* UT tests are automatically started using `mvn clean install`
* IT tests are automatically disabled. Can be enabled using `mvn clean install -DskipITs=false or  mvn clean install -PexecITs`

# Default command to be used before commits: 
* Do `mvn clean install -DskipITs=false  -DskipDbMigration=true -Dspring.profiles.active="IT"`

# IT tests:
* For IT tests we use an embedded H2 database, so therefore we need a different configuration.
* Do `mvn clean install -DskipITs=false  -Dspring.profiles.active="IT"` in order to activate the IT spring profile.

# Flyway.
* Migrations are done automatically on deploy app (run) using Flyway API.
* However, for dev, there is a possibility to run flyway commands through maven.
    * E.g. `cd application`
    * E.g. `mvn flyway:migrate -DENVIRONMENT=dev`
    
# Localhost.
* In order to run the app in localhost, you have to have a Postgres instance running at localhost and call `./start_dev.sh`.
    
# Heroku.
* There is a small `Procfile` where all the configrations are defined (how to start the app).
* However, for dev, there is a possibility to run flyway commands through maven.
    * E.g. `cd application`
    * E.g. `mvn flyway:migrate -DENVIRONMENT=dev`
    
# Existing endpoints:
    `DELETE  /expenses/remove/{expenseId} (com.revaluate.expense.resource.ExpenseResource)`
    `GET     /expenses/retrieve (com.revaluate.expense.resource.ExpenseResource)`
    `POST    /expenses/create (com.revaluate.expense.resource.ExpenseResource)`
    `POST    /expenses/update (com.revaluate.expense.resource.ExpenseResource)`
    `DELETE  /account/remove (com.revaluate.account.resource.UserResource)`
    `GET     /account/details (com.revaluate.account.resource.UserResource)`
    `GET     /account/isUniqueEmail (com.revaluate.account.resource.UserResource)`
    `POST    /account/create (com.revaluate.account.resource.UserResource)`
    `POST    /account/login (com.revaluate.account.resource.UserResource)`
    `POST    /account/requestResetPassword/{email} (com.revaluate.account.resource.UserResource)`
    `POST    /account/resetPassword/{email}/{token} (com.revaluate.account.resource.UserResource)`
    `POST    /account/update (com.revaluate.account.resource.UserResource)`
    `POST    /account/updatePassword (com.revaluate.account.resource.UserResource)`
    `POST    /account/validateResetPasswordToken/{email}/{token} (com.revaluate.account.resource.UserResource)`
    `DELETE  /categories/remove/{categoryId} (com.revaluate.category.resource.CategoryResource)`
    `GET     /categories/isUniqueCategory (com.revaluate.category.resource.CategoryResource)`
    `GET     /categories/retrieve (com.revaluate.category.resource.CategoryResource)`
    `POST    /categories/create (com.revaluate.category.resource.CategoryResource)`
    `POST    /categories/update (com.revaluate.category.resource.CategoryResource)`
