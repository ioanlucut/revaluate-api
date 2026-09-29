# Account API

[Project overview and local walkthrough](../../../../../../../../README.md#try-it-locally) · [Resource implementation](UserResource.java)

Sign up with `POST /account`, then log in with `POST /account/login`. The login response supplies the JWT in the `AuthToken` header. Authenticated requests use `Authorization: Bearer <JWT>`.

Read account details with `GET /account` and delete the authenticated account with `DELETE /account`. Validation groups, update routes, confirmation, password recovery and historical OAuth routes are defined in the resource implementation.

The [HTTP smoke test](../../../../../../../../scripts/smoke-test.py) provides an executable sign-up and login example with synthetic credentials. It removes only the account it creates. This is a local demonstration, not a security review of the historical account flows.
