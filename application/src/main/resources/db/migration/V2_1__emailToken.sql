ALTER TABLE email_token
  RENAME COLUMN validated to tokenValidated;

ALTER TABLE email_token
  RENAME to email;