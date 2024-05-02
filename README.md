# Baby-billing
## _Приложение в микросервисной архитектуре эмулирующее работу биллинговой системы_
Практическое задание на Nexign Bootcamp 2024.
**О проекте:** Приложение включает в себя три микросервиса:
* CDR-севрис
Основная задача сервиса - генерировать данные: CDR-файлы, запросы о пополнение счета и смене тарифа.
* BRT-сервис
Хранит данные о абонентах. Обрабатывает CDR-файлы. Передает данные необходимые для высталвения счета в HRS-сервис. Изменяет баланс на основе примененных данных.
* HRS-сервис
Расчитывает сумму списания с клиента.

| Инструменты |
| ------ |
| OpenJDK 17 |
| maven |
| Junit5 |
| Spring Boot |
| Kafka |
| Docker |
| PostgreSQL |
| Postman |
| Swagger |

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

<details>
<summary>Click to toggle contents of `code`</summary>

```
CODE!
```
</details>

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

## Dictionary
- BRT – Billing Real Time
- CDR – Call Data Record
- HRS – High performance rating server
- msisdn  - Mobile Subscriber Integrated Services Digital Number
- PPM - price per minute
