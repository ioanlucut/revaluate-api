CREATE TABLE goals (
    goal_id integer NOT NULL,
    value numeric(11,2) NOT NULL,
    category_id integer,
    user_id integer NOT NULL
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
    created_date timestamp without time zone NOT NULL,
    modified_date timestamp without time zone NOT NULL,
    goal_target CHARACTER VARYING(255) NOT NULL;
);

CREATE SEQUENCE goals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY goals
    ADD CONSTRAINT goals_pkey PRIMARY KEY (goal_id);

ALTER TABLE ONLY goals
    ADD CONSTRAINT fk_a40ypqkuexxyjf68r0sthyqr6 FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY goals
    ADD CONSTRAINT fk_qo5xu6mr1jckbf5u08jgouyqr FOREIGN KEY (category_id) REFERENCES categories(category_id);