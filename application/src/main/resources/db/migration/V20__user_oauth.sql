ALTER TABLE users
ADD COLUMN connected_via_oauth BOOLEAN NOT NULL DEFAULT FALSE,
ALTER COLUMN password DROP NOT NULL;