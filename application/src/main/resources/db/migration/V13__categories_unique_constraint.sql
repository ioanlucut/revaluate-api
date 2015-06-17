-- DROP old constraint
ALTER TABLE ONLY categories
DROP CONSTRAINT uk_6b5di7puf2eyr06yangpd1rws;

-- Add new constraint
ALTER TABLE ONLY categories
ADD CONSTRAINT uk_6b5di7puf2sgx06yangpd1rws UNIQUE (user_id, name);