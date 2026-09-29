# Expense API

[Project overview and local walkthrough](../../../../../../../../README.md#try-it-locally) · [Resource implementation](ExpenseResource.java)

All expense routes require `Authorization: Bearer <JWT>`. Create and update use `POST /expenses` and `PUT /expenses`; deletion uses `DELETE /expenses/{expenseId}`. These mutations are gated by `@PaymentRequired`.

List expenses with `GET /expenses/retrieve`. Date-range, category and grouped queries are defined in the resource implementation. Responses contain the saved expense or query result directly, not a shared data envelope.

The [HTTP smoke test](../../../../../../../../scripts/smoke-test.py) demonstrates category creation, expense creation, retrieval and insight totals using returned IDs.
