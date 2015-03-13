--
-- TOC entry 163 (class 1259 OID 43000)
-- Dependencies: 7
-- Name: categories; Type: TABLE; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

CREATE TABLE categories (
    category_id integer NOT NULL,
    color character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE revaluate_prod.categories OWNER TO postgres;

--
-- TOC entry 168 (class 1259 OID 43065)
-- Dependencies: 7
-- Name: categories_id_seq; Type: SEQUENCE; Schema: revaluate_prod; Owner: postgres
--

CREATE SEQUENCE categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE revaluate_prod.categories_id_seq OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 43008)
-- Dependencies: 7
-- Name: currencies; Type: TABLE; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

CREATE TABLE currencies (
    id integer NOT NULL,
    currency_code character varying(255) NOT NULL
);


ALTER TABLE revaluate_prod.currencies OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 43067)
-- Dependencies: 7
-- Name: currencies_id_seq; Type: SEQUENCE; Schema: revaluate_prod; Owner: postgres
--

CREATE SEQUENCE currencies_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE revaluate_prod.currencies_id_seq OWNER TO postgres;

--
-- TOC entry 165 (class 1259 OID 43013)
-- Dependencies: 7
-- Name: email_token; Type: TABLE; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

CREATE TABLE email_token (
    id integer NOT NULL,
    email_type character varying(255) NOT NULL,
    token character varying(255) NOT NULL,
    validated boolean NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE revaluate_prod.email_token OWNER TO postgres;

--
-- TOC entry 166 (class 1259 OID 43021)
-- Dependencies: 7
-- Name: expenses; Type: TABLE; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

CREATE TABLE expenses (
    expense_id integer NOT NULL,
    created_date timestamp without time zone NOT NULL,
    description character varying(255),
    modified_date timestamp without time zone NOT NULL,
    spent_date timestamp without time zone NOT NULL,
    value numeric(11,2) NOT NULL,
    category_id integer,
    user_id integer NOT NULL
);


ALTER TABLE revaluate_prod.expenses OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 43069)
-- Dependencies: 7
-- Name: expenses_id_seq; Type: SEQUENCE; Schema: revaluate_prod; Owner: postgres
--

CREATE SEQUENCE expenses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE revaluate_prod.expenses_id_seq OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 37709)
-- Dependencies: 7
-- Name: user_email_token_id_seq; Type: SEQUENCE; Schema: revaluate_prod; Owner: postgres
--

CREATE SEQUENCE user_email_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE revaluate_prod.user_email_token_id_seq OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 43071)
-- Dependencies: 7
-- Name: user_token_id_seq; Type: SEQUENCE; Schema: revaluate_prod; Owner: postgres
--

CREATE SEQUENCE user_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE revaluate_prod.user_token_id_seq OWNER TO postgres;

--
-- TOC entry 167 (class 1259 OID 43026)
-- Dependencies: 7
-- Name: users; Type: TABLE; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

CREATE TABLE users (
    id integer NOT NULL,
    created_date timestamp without time zone NOT NULL,
    email character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    first_name character varying(255) NOT NULL,
    initiated boolean NOT NULL,
    last_name character varying(255) NOT NULL,
    modified_date timestamp without time zone NOT NULL,
    password character varying(255) NOT NULL,
    currency_id integer NOT NULL
);


ALTER TABLE revaluate_prod.users OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 43073)
-- Dependencies: 7
-- Name: users_id_seq; Type: SEQUENCE; Schema: revaluate_prod; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE revaluate_prod.users_id_seq OWNER TO postgres;

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (category_id);


--
-- TOC entry 1888 (class 2606 OID 43012)
-- Dependencies: 164 164 2017
-- Name: currencies_pkey; Type: CONSTRAINT; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT currencies_pkey PRIMARY KEY (id);


--
-- TOC entry 1892 (class 2606 OID 43020)
-- Dependencies: 165 165 2017
-- Name: email_token_pkey; Type: CONSTRAINT; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY email_token
    ADD CONSTRAINT email_token_pkey PRIMARY KEY (id);


--
-- TOC entry 1894 (class 2606 OID 43025)
-- Dependencies: 166 166 2017
-- Name: expenses_pkey; Type: CONSTRAINT; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY expenses
    ADD CONSTRAINT expenses_pkey PRIMARY KEY (expense_id);


--
-- TOC entry 1886 (class 2606 OID 43035)
-- Dependencies: 163 163 163 2017
-- Name: uk_6b5di7puf2eyr06yangpd1rws; Type: CONSTRAINT; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT uk_6b5di7puf2eyr06yangpd1rws UNIQUE (category_id, name);


--
-- TOC entry 1896 (class 2606 OID 43039)
-- Dependencies: 167 167 2017
-- Name: uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 1890 (class 2606 OID 43037)
-- Dependencies: 164 164 2017
-- Name: uk_jekn45c17p62ja9i4g7xj1st8; Type: CONSTRAINT; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT uk_jekn45c17p62ja9i4g7xj1st8 UNIQUE (currency_code);


--
-- TOC entry 1898 (class 2606 OID 43033)
-- Dependencies: 167 167 2017
-- Name: users_pkey; Type: CONSTRAINT; Schema: revaluate_prod; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 1900 (class 2606 OID 43045)
-- Dependencies: 165 1897 167 2017
-- Name: fk_668u0bx7e9egiitqc9qb81f2m; Type: FK CONSTRAINT; Schema: revaluate_prod; Owner: postgres
--

ALTER TABLE ONLY email_token
    ADD CONSTRAINT fk_668u0bx7e9egiitqc9qb81f2m FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 1902 (class 2606 OID 43055)
-- Dependencies: 167 166 1897 2017
-- Name: fk_a40ypqkuegvyjf68r0sthyqr6; Type: FK CONSTRAINT; Schema: revaluate_prod; Owner: postgres
--

ALTER TABLE ONLY expenses
    ADD CONSTRAINT fk_a40ypqkuegvyjf68r0sthyqr6 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 1899 (class 2606 OID 43040)
-- Dependencies: 1897 163 167 2017
-- Name: fk_f5hobmotrjyyffipi045ga93k; Type: FK CONSTRAINT; Schema: revaluate_prod; Owner: postgres
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT fk_f5hobmotrjyyffipi045ga93k FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 1901 (class 2606 OID 43050)
-- Dependencies: 163 1883 166 2017
-- Name: fk_qo1eu6mr1jckbf5u08jgouyqr; Type: FK CONSTRAINT; Schema: revaluate_prod; Owner: postgres
--

ALTER TABLE ONLY expenses
    ADD CONSTRAINT fk_qo1eu6mr1jckbf5u08jgouyqr FOREIGN KEY (category_id) REFERENCES categories(category_id);


--
-- TOC entry 1903 (class 2606 OID 43060)
-- Dependencies: 1887 167 164 2017
-- Name: fk_t5ca9hp1hytxlo0balie5o2i2; Type: FK CONSTRAINT; Schema: revaluate_prod; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_t5ca9hp1hytxlo0balie5o2i2 FOREIGN KEY (currency_id) REFERENCES currencies(id);


-- Completed on 2015-03-11 22:09:57 CET

--
-- PostgreSQL database dump complete
--