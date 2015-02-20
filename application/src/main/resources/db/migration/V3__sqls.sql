-- ALTER column
ALTER TABLE users
RENAME COLUMN user_email_token_id TO reset_email_token_id;

ALTER TABLE users
DROP CONSTRAINT fk_user_email_token_id;

ALTER TABLE users
ADD CONSTRAINT fk_user_email_token_id FOREIGN KEY (reset_email_token_id)
REFERENCES user_email_token (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE users
ADD COLUMN initiated BOOLEAN NOT NULL DEFAULT FALSE;