# Category API

[Project overview and local walkthrough](../../../../../../../../README.md#try-it-locally) · [Resource implementation](CategoryResource.java)

All category routes require `Authorization: Bearer <JWT>`. Create and update use `POST /categories` and `PUT /categories`; deletion uses `DELETE /categories/{categoryId}`. These individual mutations are gated by `@PaymentRequired`.

List categories with `GET /categories/retrieve`. Available color objects come from `GET /appconfig/fetchConfig`, under `ALL_COLORS`. Bulk operations and the uniqueness check are defined in the resource implementation.

The [HTTP smoke test](../../../../../../../../scripts/smoke-test.py) creates a category using a color returned by the application, then reuses the saved category in an expense.
