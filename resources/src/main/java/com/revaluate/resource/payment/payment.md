## Payment

* The main logic, separated from application package, PaymentService, is used to comunicate with braintree gateway.
* The main flow is in the following way: 
* When user gets created, he has a subscription (TRIAL) and an expiration date, both persisted to the database.
* During API calls, we check in `PaymentAuthorizationRequestFilter` if user is eligible to use that resource (few of them are annotated).
* We also check if the user status is expired, and if yes we set the user as `TRIAL_EXPIRED` and return back unauthorized.
* In frontend we need to `relogin` the user because he has to get back from the user the new status.
* In this case, user will be forwarded to the settings page (payment) and he will not be able to access our expenses and insights pages. Currently we display an error message.

## Payment method for user in trial
* If user has entered his payment method before he went in `TRIAL_EXPIRED`, he does not get the braintree subscription activated, and `UserSubscriptionJobService`will activate it after he gets into `TRIAL_EXPIRED` mode.
* He must call only the resource which adds the payment method;

## Payment method for user in trial_expired
* If user has entered his payment method after he went in `TRIAL_EXPIRED`, he does get the braintree subscription activated.
* He must call only the resource which adds the payment method and activate subscription;