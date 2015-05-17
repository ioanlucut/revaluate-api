CREATE TABLE subscription_plan (
  id                INTEGER                NOT NULL,
  value             NUMERIC(11, 2)         NOT NULL,
  description       CHARACTER VARYING(255) NOT NULL DEFAULT '',
  subscription_type CHARACTER VARYING(255) NOT NULL
);

CREATE SEQUENCE subscription_plans_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY subscription_plan
ADD CONSTRAINT subscription_plan_pkey PRIMARY KEY (id);

ALTER TABLE ONLY subscription_plan
ADD CONSTRAINT uk_6b5di7puf2eyr06yandsad1rqw UNIQUE (subscription_type);

-- INSERT FIRST TYPE
INSERT INTO subscription_plan (id, value, description, subscription_type)
VALUES (1, 5, 'Basic subscription plan', 'STANDARD');