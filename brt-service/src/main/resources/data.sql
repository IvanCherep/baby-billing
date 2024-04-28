INSERT INTO clients ("id", "name")
VALUES  (1, 'Totoro'),
        (2, 'Mike Wasowski'),
        (3, 'Wall-E'),
        (4, 'Dumbo'),
        (5, 'Buzz Lightyear'),
        (6, 'Elizabeth Bennet'),
        (7, 'Homer J Simpson'),
        (8, 'Jon Snow'),
        (9, 'Patrick Star'),
        (10, 'Sherlock Holmes');

INSERT INTO data_plans ("id", "title", "ppm_to_native", "ppm_from_native",
    "ppm_to_foreign", "ppm_from_foreign", "minutes_plan", "monthly_payment")
VALUES  (11, 'Классика', 1.5, 0, 2.5, 0, 0, 0),
        (12, 'Помесячный', 1.5, 0, 2.5, 0, 50, 100);

INSERT INTO msisdns ("msisdn", "data_plan_id", "remaining_minutes",
    "remaining_money", "client_id", "last_payment_timestamp")
VALUES  (79218740659, 11, 0, 100, 1, 1704056400),
        (79214568932, 11, 0, 100, 2, 1704056400),
        (79219876543, 11, 0, 100, 3, 1704056400),
        (79210123456, 11, 0, 100, 4, 1704056400),
        (79217654321, 11, 0, 100, 5, 1704056400),
        (79213579268, 12, 50, 10, 6, 1704056400),
        (79219999999, 12, 50, 10, 7, 1704056400),
        (79212345678, 12, 50, 10, 8, 1704056400),
        (79215478962, 12, 50, 10, 9, 1704056400),
        (79218654321, 12, 50, 10, 10, 1704056400);
