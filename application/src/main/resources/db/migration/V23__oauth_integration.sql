CREATE TABLE oauth_integration (
  id                   INTEGER                     NOT NULL,
  user_id              INTEGER                     NOT NULL,
  discriminator_oauth_type          CHARACTER VARYING(255)      NOT NULL,
  oauth_integration_type CHARACTER VARYING(255)      NOT NULL,
  oauth_integration_scope_type CHARACTER VARYING(255)      NOT NULL,
  access_token CHARACTER VARYING(255)      NOT NULL,
  slack_user_id CHARACTER VARYING(255)      ,
  slack_team_id CHARACTER VARYING(255)      ,
  created_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  modified_date        TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE SEQUENCE oauth_integration_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY oauth_integration
ADD CONSTRAINT oauth_integration_pkey PRIMARY KEY (id);

ALTER TABLE ONLY oauth_integration
ADD CONSTRAINT uk_652di7sad3eyr06dsndsad1rqw UNIQUE (slack_user_id);

ALTER TABLE ONLY oauth_integration
ADD CONSTRAINT uk_652fsasad3eyr06dsndsad1rqw UNIQUE (slack_team_id);