Инструкция по запуску:
1. Склонируйте репозиторий: https://github.com/IvanCherep/baby-billing.git
2. Перейдите в директорию с проектом: cd baby-billing
3. Запустите Docker и введите: docker-compose up

HRS-service:
POST localhost:8083/calculate
JSON:
{
    "msisdn": 79999999999,
    "startTime": 1713866400,
    "endTime": 1713868200,
    "pricePerMinute": 1.5,
    "clientRemainingMinutes": 100,
    "lastPaymentTimestamp": 1669372200,
    "tariffMonthlyFee": 100,
    "tariffMinutesPlan": 500
}

Чтобы посмотерть таблицы CDR и BRT сервиса перейдите на localhost:5050. admin@admin.com password
и добавтье серверса:
BRT:
Название: любое
Адрес: brt-service_db
Порт: 5432
Логин: postgres
Пароль: changemeinprod!

CDR:
Название: любое
Адрес: cdr-service_db
Порт: 5432
Логин: postgres
Пароль: changemeinprod!
