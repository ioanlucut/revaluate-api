ALTER TABLE email
  DROP COLUMN tokenValidated,
  ADD COLUMN token_validated boolean NOT NULL default false,
  ADD COLUMN sent boolean NOT NULL default false;