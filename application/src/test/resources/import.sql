INSERT INTO currencies VALUES (1, 'USD');
INSERT INTO currencies VALUES (2, 'EUR');

INSERT INTO users (
  id, created_date, email, enabled, first_name, initiated, last_name,
  modified_date, password, currency_id)
VALUES (1, now(), 'x@x.x', TRUE, 'fn', TRUE, 'ln',
        now(), 'pw', 1);

INSERT INTO email (
  id, email_type, token, tokenValidated, user_id)
VALUES (1, 'CREATED_ACCOUNT', 'xxx', FALSE, 1);


INSERT INTO email (
  id, email_type, token, tokenValidated, user_id)
VALUES (2, 'CREATED_ACCOUNT', 'xxx', FALSE, 1);

--Colors
INSERT INTO color (id, color, color_name, priority) VALUES (1, '#eee', '', 1);
INSERT INTO color (id, color, color_name, priority) VALUES (2, '#fff', '', 2);
INSERT INTO color (id, color, color_name, priority) VALUES (3, '#fef', '', 3);

--
INSERT INTO subscription_plan (id, value, description, subscription_type) VALUES (1, 5, 'Basic subscription plan', 'STANDARD');