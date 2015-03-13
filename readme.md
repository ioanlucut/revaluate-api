# Maven
* UT tests are automatically started using `mvn clean install`
* IT tests are automatically disabled. Can be enabled using `mvn clean install -DskipITs=false or  mvn clean install -PexecITs`

# Default command to be used before commits: `mvn clean install -DskipITs=false`.
* Do `mvn clean install -DskipDbMigration=true`
* Do `java -jar target/application-1.0-SNAPSHOT.jar`
* Do `mvn clean install -PexecITs -DskipDbMigration=true`
* Do `java -jar target/application-1.0-SNAPSHOT.jar server config.yaml`
* Do `mvn clean install -PexecITs -DENVIRONMENT=prod`
* Do `mvn clean install -DskipITs=false  -DskipDbMigration=true -Dspring.profiles.active="IT"`

Existing endpoints:
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
