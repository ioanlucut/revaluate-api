--USERS TABLE
CREATE TABLE users
(
  id              SERIAL                      NOT NULL,
  email           CHARACTER VARYING(255)      NOT NULL,
  enabled         BOOLEAN                     NOT NULL,
  first_name      CHARACTER VARYING(255)      NOT NULL,
  last_name       CHARACTER VARYING(255)      NOT NULL,
  password        CHARACTER VARYING(255)      NOT NULL,
  registered_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- Constraint: users_pkey

-- ALTER TABLE users DROP CONSTRAINT users_pkey;

ALTER TABLE users
ADD CONSTRAINT users_pkey PRIMARY KEY (id);

-- Constraint: uk_email

-- ALTER TABLE users DROP CONSTRAINT uk_email;

ALTER TABLE users
ADD CONSTRAINT uk_email UNIQUE (email);