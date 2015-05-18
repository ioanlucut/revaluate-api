CREATE TABLE payment_status (
  id                   INTEGER                     NOT NULL,
  user_id              INTEGER                     NOT NULL,
  customer_id          CHARACTER VARYING(255)      NOT NULL,
  payment_method_token CHARACTER VARYING(255)      NOT NULL,
  created_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  modified_date        TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE SEQUENCE payment_status_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY payment_status
ADD CONSTRAINT payment_status_pkey PRIMARY KEY (id);

ALTER TABLE ONLY payment_status
ADD CONSTRAINT uk_652di7gas3eyr06yandsad1rqw UNIQUE (user_id);

ALTER TABLE ONLY payment_status
ADD CONSTRAINT uk_652di7sad3eyr06yandsad1rqw UNIQUE (customer_id);

ALTER TABLE ONLY payment_status
ADD CONSTRAINT uk_652di7ggs2eys06yandsad1rqw UNIQUE (payment_method_token);