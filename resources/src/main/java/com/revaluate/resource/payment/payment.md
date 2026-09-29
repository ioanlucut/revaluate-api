# Payment and subscription access

[Engineering notes and test links](../../../../../../../../docs/engineering.md#2-separating-payment-integration-from-access-rules) · [Resource implementation](PaymentResource.java)

The payment module wraps Braintree; the application service stores customer and payment-method references and manages local subscription status. Saving a payment method is distinct from activating a subscription.

For routes marked `@PaymentRequired`, the request filter moves an expired trial to `TRIAL_EXPIRED` and blocks it with `402 Payment Required`. Active subscribers pass this filter. Authentication is handled separately.

The offline tests verify local behavior with a mocked gateway. Neither the local smoke test nor the Java CI suite establishes that this historical integration still works against the live provider. See the engineering notes for provider/local-state consistency trade-offs.
