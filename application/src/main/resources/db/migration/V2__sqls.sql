--UserEmailToken TABLE
CREATE TABLE user_email_token
(
  id              SERIAL                      NOT NULL,
  token           CHARACTER VARYING(255)      NOT NULL,
  validated         BOOLEAN                     NOT NULL
);

--Add primary key
ALTER TABLE user_email_token
ADD CONSTRAINT user_email_token_pkey PRIMARY KEY (id);

-- Add new column
ALTER TABLE users
ADD COLUMN user_email_token_id integer;

ALTER TABLE users
ADD CONSTRAINT fk_user_email_token_id FOREIGN KEY (user_email_token_id)
      REFERENCES user_email_token (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;