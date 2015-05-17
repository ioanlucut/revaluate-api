CREATE TABLE user_subscription_plan (
  id                   INTEGER                     NOT NULL,
  subscription_plan_id INTEGER                     NOT NULL,
  user_id              INTEGER                     NOT NULL,
  created_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  modified_date        TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE SEQUENCE user_subscription_plan_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY user_subscription_plan
ADD CONSTRAINT user_subscription_plan_pkey PRIMARY KEY (id);

ALTER TABLE ONLY user_subscription_plan
ADD CONSTRAINT uk_6b5di7gas3eyr06yandsad1rqw UNIQUE (user_id);