ALTER TABLE email
  ADD COLUMN created_date timestamp without time zone NOT NULL DEFAULT now(),
  ADD COLUMN sent_date timestamp without time zone;