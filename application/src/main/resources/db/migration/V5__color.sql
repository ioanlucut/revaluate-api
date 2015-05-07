CREATE TABLE color (
  id         INTEGER                NOT NULL,
  color      CHARACTER VARYING(255) NOT NULL,
  color_name CHARACTER VARYING(255) NOT NULL,
  priority   INTEGER                NOT NULL
);

CREATE SEQUENCE colors_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY color
ADD CONSTRAINT color_pkey PRIMARY KEY (id);

ALTER TABLE ONLY color
ADD CONSTRAINT uk_6b5di7puf2eyr06yangpd1rqw UNIQUE (color);

ALTER TABLE ONLY color
ADD CONSTRAINT uk_6b5di7puf2eyr06yangpd1qqw UNIQUE (priority);

INSERT INTO color (id, color, color_name, priority) VALUES (1, '#C3272B', '', 1);
INSERT INTO color (id, color, color_name, priority) VALUES (2, '#DB5A6B', '', 2);
INSERT INTO color (id, color, color_name, priority) VALUES (3, '#875F9A', '', 3);
INSERT INTO color (id, color, color_name, priority) VALUES (4, '#22A7F0', '', 4);
INSERT INTO color (id, color, color_name, priority) VALUES (5, '#317589', '', 5);
INSERT INTO color (id, color, color_name, priority) VALUES (6, '#1F4788', '', 6);
INSERT INTO color (id, color, color_name, priority) VALUES (7, '#006442', '', 7);
INSERT INTO color (id, color, color_name, priority) VALUES (8, '#26A65B', '', 8);
INSERT INTO color (id, color, color_name, priority) VALUES (9, '#36D7B7', '', 9);
INSERT INTO color (id, color, color_name, priority) VALUES (10, '#FFB61E', '', 10);
INSERT INTO color (id, color, color_name, priority) VALUES (11, '#CA6924', '', 11);
INSERT INTO color (id, color, color_name, priority) VALUES (12, '#6C7A89', '', 12);
INSERT INTO color (id, color, color_name, priority) VALUES (13, '#121212', '', 13);
INSERT INTO color (id, color, color_name, priority) VALUES (14, '#BDC3C7', '', 14);

-- CHANGE THE CATEGORY TABLE

-- Alter column type
ALTER TABLE categories ALTER COLUMN color TYPE INTEGER USING 1;

-- Add non null constraint
ALTER TABLE categories ALTER COLUMN color SET NOT NULL;

-- Rename column
ALTER TABLE categories RENAME COLUMN color TO color_id