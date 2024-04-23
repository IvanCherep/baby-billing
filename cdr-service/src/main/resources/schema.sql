DROP TABLE IF EXISTS users;

CREATE TABLE users (
    "id" BIGINT,
    "msisdn" BIGINT,
    CONSTRAINT "user_id_pk" PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS transactions;
DROP SEQUENCE IF EXISTS transactions_id_seq;
CREATE SEQUENCE transactions_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE transactions (
    "id" BIGINT DEFAULT nextval('transactions_id_seq'),
    "call_type" VARCHAR(2),
    "client_msisdn" BIGINT,
    "target_msisdn" BIGINT,
    "start_time" BIGINT,
    "end_time" BIGINT,
    CONSTRAINT "transaction_id_pk" PRIMARY KEY ("id")
);