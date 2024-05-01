DROP TABLE IF EXISTS clients CASCADE;
CREATE TABLE clients (
    "id" BIGINT,
    "name" VARCHAR(255),
    CONSTRAINT "client_id_pk" PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS data_plans CASCADE;
CREATE TABLE data_plans (
    id INT,
    title VARCHAR(255),
    ppm_to_native DECIMAL(12, 2),
    ppm_from_native DECIMAL(12, 2),
    ppm_to_foreign DECIMAL(12, 2),
    ppm_from_foreign DECIMAL(12, 2),
    minutes_plan INT,
    monthly_payment DECIMAL(12, 2),
    CONSTRAINT "data_plan_id_pk" PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS msisdns CASCADE;
CREATE TABLE msisdns (
    msisdn BIGINT,
    data_plan_id INT,
    remaining_minutes INT,
    remaining_money DECIMAL(12, 2),
    client_id BIGINT,
    last_payment_timestamp BIGINT,
    CONSTRAINT "msisdn_pk" PRIMARY KEY ("msisdn"),
    CONSTRAINT "data_plan_fk" FOREIGN KEY ("data_plan_id")
    REFERENCES data_plans("id"),
    CONSTRAINT "client_fk" FOREIGN KEY ("client_id")
    REFERENCES clients("id")
);