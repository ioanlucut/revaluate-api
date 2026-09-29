# Engineering notes and code tour

[Back to the project](../README.md)

Revaluate was a personal expense tracker built and launched in `2015`, with a web app, paid subscriptions and external integrations. I owned the backend and most of the frontend; Sorin Pantis owned product and design. These notes explain three designs visible in the preserved implementation, their trade-offs, and how I would approach them today. They are not claims about measured scale or performance.

## 1. Importing expenses without imposing another app's categories

**Problem.** An export from another expense tracker has its own column names, date format and categories. Parsing a CSV is only the first step: the imported expenses must fit the user's existing categories.

**Implementation.** Source-specific profiles describe columns, delimiters and date formats. The parser produces expense DTOs, then the application groups the source category names and returns a preview. The user maps each source category to a Revaluate category or deselects it. The import call checks that every source category has a matching entry, filters out deselected entries, applies the mappings and persists the expenses.

**Trade-off.** Explicit profiles avoid guessing ambiguous dates, at the cost of maintaining a profile for each supported format. Returning the parsed expenses to the client makes the preview straightforward, but also means the import request contains client-controlled expense data and the whole dataset is held in memory. This is not a streaming import pipeline.

**Evidence.** The integration tests exercise grouping repeated category names, persisting mapped expenses, rejecting incomplete mappings and skipping deselected categories. They demonstrate the flow, not large-file throughput or complete validation of hostile uploads.

- [Parser and profile handling](../importer/src/main/java/com/revaluate/importer/ImporterParserServiceImpl.java)

- [Preview, mapping and import service](../application/src/main/java/com/revaluate/expense/service/ExpenseImportServiceImpl.java)

- [Import integration tests](../application/src/test/java/com/revaluate/expense/service/ExpenseImportServiceImplTestIT.java)

**Today.** I would define file and row limits, revalidate destination-category ownership at the persistence boundary, and make repeated submissions safe. Larger imports would use a server-side import record rather than round-tripping the full parsed file through the client.

## 2. Separating payment integration from access rules

**Problem.** An account can be in a trial, have an expired trial, or have an active subscription. Saving a payment method and granting access are related but different operations.

**Implementation.** The payment module wraps the Braintree gateway. The application service stores the provider's customer and payment-method references and updates the local subscription status after a successful subscription call. Jersey resources mark gated operations with `@PaymentRequired`; a request filter transitions expired trials to `TRIAL_EXPIRED` and responds with `402` when access is blocked.

**Trade-off.** The annotation keeps access policy out of individual endpoint bodies, and local subscription state avoids a provider lookup on every gated request. But the remote subscription and local database update are not one atomic operation. A successful provider call followed by a local failure needs recovery; a database transaction alone cannot undo a remote charge or subscription.

**Evidence.** Payment-status tests cover local persistence and duplicate payment-method setup using a mocked gateway. Filter tests cover trial expiry, active subscribers and unannotated endpoints. The offline suite does not prove that the historical Braintree integration still works against the live service.

- [Payment orchestration](../application/src/main/java/com/revaluate/payment/service/PaymentStatusServiceImpl.java)

- [Payment-status integration tests](../application/src/test/java/com/revaluate/payment/service/PaymentStatusServiceImplTest_createPaymentStatus_IT.java)

- [Access filter](../resources/src/main/java/com/revaluate/settings/filter/PaymentAuthorizationRequestFilter.java)

- [Access-filter tests](../resources/src/test/java/com/revaluate/settings/filter/PaymentAuthorizationRequestFilterTest_paymentRequired_IT.java)

**Today.** I would make subscription transitions explicit, use idempotent provider operations where supported, and reconcile local state through verified webhooks and periodic checks. Tests should include the failure between provider success and the local save, not just successful calls.

## 3. Turning expense records into useful insights

**Problem.** A chart needs more than a sum: category totals, transaction counts, the largest expense and a stable view of categories with no spending in the selected period.

**Implementation.** The monthly service fetches the user's expenses in a date range, groups them by category and sums stored `BigDecimal` amounts. It computes rankings and adds zero-total categories when other spending exists. The result is a chart-oriented DTO rather than a list of database rows. An entirely empty period has a separate empty-result path.

**Trade-off.** In-memory aggregation keeps the calculations together and easy to exercise, but loads every matching expense. Also, decimal storage is not the same as end-to-end decimal arithmetic: the API DTOs convert amounts to `double`, and formatting uses a fixed two-decimal scale. Date queries use local timestamps, so interval and timezone semantics need an explicit contract.

**Evidence.** The integration tests check category totals, transaction counts, zero-total categories, ordering and the largest expense. They do not establish performance at scale or exhaustively cover timezone and rounding boundaries.

- [Monthly insight calculation](../application/src/main/java/com/revaluate/insights/service/MonthlyInsightsServiceImpl.java)

- [Expense storage model](../application/src/main/java/com/revaluate/expense/persistence/Expense.java)

- [Monthly insight integration tests](../application/src/test/java/com/revaluate/insights/service/MonthlyInsightsServiceTestIT.java)

**Today.** I would specify rounding and date-boundary rules, preserve decimal amounts across the API boundary, and test exact boundaries and empty periods. I would measure query cost before moving aggregation into SQL or adding precomputed summaries.

## What the revival verifies

The original code is preserved rather than presented as a current production template. The revival provides two complementary checks:

- The Java unit and integration suite exercises domain behavior, primarily with an in-memory HSQLDB database. External-service tests remain explicitly skipped.

- The [HTTP smoke test](../scripts/smoke-test.py) exercises the packaged application against PostgreSQL: startup, authentication enforcement, sign-up, login, category and expense creation, persisted retrieval, insight totals and account cleanup. CI starts it with the same Docker Compose configuration used in the quick start.

The old Java HTTP tests under `e2e-tests` are not enabled. The smoke test covers one local journey; it is not a replacement for those tests, a security audit, or proof that retired external integrations still work.

## Before using this as a production starting point

I would first move to supported framework and database versions, review authentication and account recovery, rotate any previously exposed credentials, and add dependency and secret scanning. Rewriting Git history removes committed values from this repository; it does not revoke copies elsewhere.

The default configuration is for local, synthetic data only. Docker Compose publishes the database, application and admin ports on loopback. Do not expose this preserved stack publicly or connect it to real financial data without a separate modernization and security review.
