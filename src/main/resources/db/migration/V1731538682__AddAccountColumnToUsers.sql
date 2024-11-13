CREATE SEQUENCE user_account_seq START 1;

ALTER TABLE "users"
ADD COLUMN account NUMERIC NOT NULL UNIQUE DEFAULT nextval('user_account_seq');
