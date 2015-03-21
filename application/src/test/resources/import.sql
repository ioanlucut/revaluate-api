INSERT INTO currencies VALUES (1,'USD');
INSERT INTO currencies VALUES (2,'EUR');

INSERT INTO users(
  id, created_date, email, enabled, first_name, initiated, last_name,
  modified_date, password, currency_id)
VALUES (1, now(), 'x@x.x', true, 'fn', true, 'ln',
        now(), 'pw', 1);

INSERT INTO email_token(
  id, email_type, token, validated, user_id)
VALUES (1, 'CREATED_ACCOUNT', 'xxx', false, 1);


INSERT INTO email_token(
  id, email_type, token, validated, user_id)
VALUES (2, 'CREATED_ACCOUNT', 'xxx', false, 1);
