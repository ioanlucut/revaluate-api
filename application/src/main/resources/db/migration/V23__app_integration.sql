CREATE TABLE app_integration (
  id                   INTEGER                     NOT NULL,
  user_id              INTEGER                     NOT NULL,
  discriminator_app_type          CHARACTER VARYING(255)      NOT NULL,
  app_integration_type CHARACTER VARYING(255)      NOT NULL,
  app_integration_scope_type CHARACTER VARYING(255)      NOT NULL,
  access_token CHARACTER VARYING(255)      NOT NULL,
  slack_user_id CHARACTER VARYING(255)      ,
  slack_team_id CHARACTER VARYING(255)      ,
  created_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  modified_date        TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE SEQUENCE app_integration_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY app_integration
ADD CONSTRAINT app_integration_pkey PRIMARY KEY (id);

ALTER TABLE ONLY app_integration
ADD CONSTRAINT uk_652di7sad3eyr06dsndsad1rqw UNIQUE (slack_user_id);

ALTER TABLE ONLY app_integration
ADD CONSTRAINT uk_652fsasad3eyr06dsndsad1rqw UNIQUE (slack_team_id);