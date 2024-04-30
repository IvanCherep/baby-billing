# Приложение в микросервисной архитектуре эмулирующее работу биллинговой системы
Практическое задание на Nexign Bootcamp 2024.<br>
**О проекте:** Приложение включает в себя три микросервиса:
* Сервис генерации CDR-фалов
* Сервис расчета цен для конкретных звоноков
* Сервис обработки CDR-файлов

## Для запуска приложения необходимо:
* [Установить докер](https://docs.docker.com/get-docker/)
* Скопировать репозиторий и перейте в папку с проектом

## Использование

Запуск приложения:
```shell
 $ docker-compose up
```

Остановка приложения:
```shell
 $ docker-compose down
```

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
