# Baby-billing
## _Приложение в микросервисной архитектуре эмулирующее работу биллинговой системы_
Практическое задание на Nexign Bootcamp 2024. <br>

**О проекте:** Приложение логически делится на 2 части:
* Биллинговая система:
	* BRT-сервис. Сервис авторизации клиентов, хранения и изменения информации об абонентах.
	* HRS-сервис. Сервис расчета стоимости конкретных услуг для конкретных пользователей.
* Система генерации данных:
	* CDR-сервис. Сервис эмулции работы коммутатора и действий пользователей.
<details>
<summary>Общая схема проекта</summary>
	<p>CDR-сервис передает файлы в BRT-сервис через брокер сообщений, однако запросы на изменение тарифа или пополнение счета посылает напрямую. Также вместе с проектом поднимается pgAdmin.</p>
	<img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/application_schema.png" alt="Схема проекта">
</details>

| Инструменты |
| ------ |
| OpenJDK 17 |
| maven |
| Spring Boot |
| Kafka |
| Docker |
| PostgreSQL |
| Postman |

## Для запуска приложения необходимо:
* [Установить докер](https://docs.docker.com/get-docker/)
* Запустить докер
* Скопировать репозиторий и перейте в папку с проектом

## Использование

Запуск приложения:
```shell
 $ docker-compose build --no-cache
 $ docker-compose up
```

Остановка приложения:
```shell
 $ docker-compose down
```

## Посмотреть результаты работы приложения
<details>
<summary>Посмотреть логи</summary>
	<br>
	<p> В проекте логируется основная информация о работе системы. А именно: </p>
	<ul>
		<li> Создание нового cdr-файла </li>
		<li> Отправка cdr-файла в brt-сервис </li>
		<li> Получение cdr-файла в brt-сервисе </li>
		<li> Информация о пополнение баланса абонента </li>
		<li> Информация о изменении тарифа абонента </li>
	</ul>
	Пример:
	<img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/logs_example.png" alt="Пример логов">
	
</details>

<details>
<summary>Посмотреть содержимое баз данных</summary>
	<br>
	<p>Для просмотра содержимого баз данных вместе с проектом подниматеся pgAdmin.</p>
	<p>Для доступа к базам данных через pgAdmin необходимо:</p>
	<ul>
		<li> Перейти по адресу localhost:5050 </li>
		<li> Авторизоваться </li>
		<ul>
			<li> Логин: admin@admin.com </li>
			<li> Пароль: password </li>
		</ul>
		<li> Добавить базу даннух CDR сервиса </li>
		<ul>
			<li> Add New Server </li>
			<li> General -> Name: cdr-service </li>
			<li> Connection -> Host name/address: cdr-service_db </li>
			<li> Connection -> Port: 5432 </li>
			<li> Connection -> Maintenance database: postgres </li>
			<li> Connection -> Username: postgres </li>
			<li> Connection -> Password: changemeinprod! </li>
		</ul>
		<li> Добавить базу данных BRT сервиса </li>
		<ul>
			<li> Add New Server </li>
			<li> General -> Name: brt-service </li>
			<li> Connection -> Host name/address: brt-service_db </li>
			<li> Connection -> Port: 5432 </li>
			<li> Connection -> Maintenance database: postgres </li>
			<li> Connection -> Username: postgres </li>
			<li> Connection -> Password: changemeinprod! </li>
		</ul>
  	</ul>
   	<p>Теперь вы можете просматривать содержимое баз данных cdr-сервиса и brt-сервиса. Для просмтра содержимого необходимо открыть Server(2)->cdr-service->Database(1)->postgres->Schemas(1)->public->Tables(2) или Serverы(2)->brt-service->Database(1)->postgres->Schemas(1)->public->Tables(3) соответственно. </p>
	<p>Посмотрим содержимое таблицы msisdns в brt-service. Как видно, только 1 клиент имеет положительный баланс. Это отличиный повод написать сервис, который будет присылать клиентам с отрицательным балансом уведомление о том, что необходимо пополнить счет.</p>
	<img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/brt-service_db_msisdns_table.png" alt="Содержимое таблицы msisdns">
	<p>Также, давайте посмотрим содержимое таблицы clients в brt-service:</p>
	<img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/brt-service_db_clients_table.png" alt="Содержимое таблицы clients">
</details>

<details>
<summary>Запросы к API</summary>
	<p>BRT-сервис</p>
	<ul>
		<li>Пополнение счета.</li>
		<ul>
			<li>Адрес сервиса localhost:8082</li>
			<li>PUT /pay/{msisdn}</li>
			<li>Content-Type: application/json</li>
			<li>Пример запроса через postman: <img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/pay_postman_example.png" alt="pay запрос через postman"></li>
		</ul>
		<li>Смена тарифа.</li>
		<ul>
			<li>Адрес сервиса localhost:8082</li>
			<li>POST /changeTariff</li>
			<li>Content-Type: application/json</li>
			<li>Как временное решение, вместо Spring Security, менеджерский логин и пароль отправляется в теле POST запроса. В дальнейших итерациях проекта, это первое, что нужно исправить.</li>
			<li>Пример запроса через postman: <img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/changeTariff_postman_example.png" alt="changeTariff запрос через postman"></li> 
		</ul>
	</ul>
	<p>HRS-сервис</p>
	<ul>
		<li>Расчет стоимости звонка.</li>
		<ul>
			<li>Адрес сервиса localhost:8083</li>
			<li>POST /calculate</li>
			<li>Content-Type: application/json</li>
			<li>Пример запроса через postman для 30 минутного звонка. Дата последней оплаты датируется прошлым месяцем, стоимость минуты звонка 1.5 у.е.: <img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/calculate_postman_example.png" alt="calculate запрос через postman"></li>
		</ul>
	</ul>
	<p>Для простоты проверки прикладываю тела к запросам:</p>
	<ul>
		<li>
			<p>PUT localhost:8082/pay/79212345678</p>
<code>{
    "msisdn": 79212345678,
    "money": 100
}</code>
		</li>
  		<li>
			<p>POST localhost:8082/changeTariff</p>
<code>{
    "login": "admin",
    "password": "admin",
    "msisdn": 79218740659,
    "tariffId": 12
}</code>
		</li>
		<li>
			<p>POST localhost:8083/calculate</p>
<code>{
    "msisdn": 79999999999,
    "startTime": 1715526000,
    "endTime": 1715527800,
    "pricePerMinute": 1.5,
    "clientRemainingMinutes": 20,
    "lastPaymentTimestamp": 1712935763,
    "tariffMonthlyFee": 100,
    "tariffMinutesPlan": 20
}</code>
		</li>
	</ul>
</details>


## CDR-сервис
### Описание
CDR сервис имеет две основые функции: <br>
* Эмуляция работы коммутатора. Сервис генерирует данные о звонках, собирает из них CDR-файлы и отправляет в BRT сервис.
* Эмуляция действий абонентов. Сервис отправляет запросы на пополнение счета и смену тарифа в BRT сервис.
<details>
<summary>Обоснование возложенного на сервис функционала</summary>
<p>Функция эмуляции работы коммутатора была заложена в ТЗ CDR-сервиса. В то время, как эмуляция действий абонентов (пополнение счета, смена тарифа), задавалась как общее требованиее к проекту. Мы четко решили, что данный функционал должен быть реализован в рамках одного сервиса, чтобы держать временную согласованность. То есть в том же таймлайне, когда клиенты совершают звонки, эти же клиенты пополняют свои счета и меняют тарифы. Однако, данный подход имеет последствия.</p>
<p>В частности, если смотреть на данные в реальном времени они могут быть искажены. Это происходит из-за использования брокера сообщений в качестве посредника в передаче CDR-файлов. То есть может произойти ситуация, когда пользователь совершил звонок, а после пополнил счет. Но с точки зрения BRT ситуация будет выглядить по другому, потому-что информация о пополнение счета приходит сразу, а информация о звонке должна пройти через несколько итераций (запись в файл, хранение файла, пока в нем не наберется нужное количество записей, ожидание файла в очереди на обработку).</p>
<p>Но такая несогласованнасть с пополнением счета является вполне терпимой. А вот операция с изменением тарифа может привести к гораздо более серьезным последствиям. Например, в случае с клиентом, который совершает звонок и сразу после меняет тариф, произойдет следующее: BRT-сервис сначала изменит тариф, и только через какое-то время обработает запись о звонке клиента. И получается, что данные о звонке в BRT-сервисе будут обработаны по данным нового тарифа, хотя фактически совершлались по условиям прошлого. Мы видим два возможных решения. До совершения звонка, запрашивать данные о тарифе пользователя и передовать их как информацию о звонке в CDR-файле, либо хранить историю изменения состояний абонетов в BRT-сервисе. В рамках данного проекта мы не реализовывали соответствующее решение и рассматриваем это как дальнейшую точку роста проекта.</p>
<p>Так же можно было бы рассмотреть включение генерации действий абонентов в рамки BRT-сервиса. Это решило бы вышеописанные проблемы. Но такое решение мы считаем губительным для дальнейшего развития проекта. Так как мы видим проект логически разделенным на две части: генерация и биллинговый система. И в теории, сервис генерации можно заменить на реальный коммутатор и реаальных пользователей, а биллинговый сервис оставить без изменений. В то время, как включение генерации действий абонентов в рамки биллинговой системы делают это невозможным.</p>
</details>

<details>
<summary>Структура базы данных сервиса</summary>
	<p> Таблица users имеет поле id, для того, чтобы упростить выбор случайного номера телефона из таблицы. Таблица transactions является служебной и используется для того, чтобы проверять правильность работы системы. Так, как обе таблицы являются служебными, было принятно решение, не связывать таблицу transactions с таблицей users при помощи внешних ключей.</p>
	<img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/cdr-service_db_structure.png" alt="Структура базы данных у BRT-сервиса">
</details>

<details>
<summary>Структура сервиса</summary>
<ul>
	<li> config </li>
	<ul>
		<li> KafkaConfig - Конфигурация для продюсера Kafka. </li>
		<li> MapSerializer - Преобразует объекты типа Map&ltString, byte[]&gt в последовательность байт. Используется в качестве VALUE_SERIALIZER_CLASS для конфигурации Apache Kafka.</li>
		<li> RestTemplateConfig - Создает бин RestTemplate. </li>
	</ul>
	<li> domain </li>
	<ul>
		<li> entities </li>
		<ul>
			<li> CDREntity - Сущность представляющая из себя запись о звонке.</li> 
			<li> UserEntity - Сущность представляющая из себя номер телефона вместе с id.</li>
		</ul>
		<li> AccountRefill - Данные для пополнения счета. </li>
		<li> ManagerWill - Данные для смены тарифа. </li>
	</ul>
	<li> mappers </li>
	<ul>
		<li> impl </li>
		<ul>
			<li> CDREntityStringMapperImpl - Пробразует CDREntity (без id) в строку и обратно.</li> 
		</ul>
		<li> Mapper&ltA, B&gt - Маппинг интерфейс. </li>
	</ul>
	<li> producer </li>
	<ul>
		<li> KafkaProducer - Продюсер сообщений Kafka.</li>
	</ul>
	<li> repoitories </li>
	<ul>
		<li> CDRRepository - Репозиторий для взаимодействия с таблицей, содержащий записи о звонках.</li>
		<li> UserRepository - Репозиторий для взаимодействия с таблицей, содержит номера телефонов клиентов.</li>
	</ul>
	<li> services </li>
	<ul>
		<li> AccountRefillGenerationService - Отправляет запросы в brt-севрис на пополнение всех клиентских счетов на сумму от 1 до 1000 у.е.</li>
		<li> ChangeTariffGenerationService - Выбирает от 1 до 3 случайных клиентов и отправляет запросы в brt-севрис на смену их тарифов.</li>
		<li> FileGenerator - Записыват переданные данные о звонках в файл. После того, как записей в файле становится 10, отправляет файл в FileToKafkaProducerTransfer, который в дальнейшем преобразует его в массив байтов.</li>
		<li> FileToKafkaProducerTransfer - Преобразует файл в массив байтов и отправлят его в Kafka продюсер.</li>
		<li> GeneratorService - Генерирует действия пользователей за 1 год. Сюда относится:</li>
  		<ul>
			<li>  Генерация записей о звонках абонентов и их передача в FileGenerator. </li>
			<li>  Ежемесячный вызов генераторов смены тарифа и пополненичй счета клиентов. </li>
		</ul>
	</ul>
	<li> CdrServiceApplication - запускает приложение.</li>
</ul>
</details>

<details>
<summary>Особенности генерации CDR</summary>
	<br>
	<p>Записи о звонках генерируются в одном потоке. Однако благодаря использованному алгоритму могут накладываться друг на друга, что делает записи не однородными.</p>
	<p>Алгоритм генерации:</p>
	<ul>
		<li> Время начала нового звонка = Время начало прошлого звонка + (Длительность прошлого звонка / 2) </li>
		<li> Время конца нового звонка = Время начала нового зконка + Случайное количество минут от 1 до 120 </li>
		<li> Важно! После формирования любой записи о звонке к времени начала следуюещго звонка с вероятностью 0.5 может прибавиться случайное количество минут от 1 до 1440</li>
	</ul>
</details>

## BRT-сервис
### Описание
BRT сервис имеет пять основных функций:
<ul>
	<li> Хранение данных о абонентах, тарифе и текущем балансе. </li>
	<li> Авторизация абонентов, приходящий в CDR-файлах. </li>
	<li> Передача данных о звонках и тарифах в HRS-севрис. </li>
	<li> Изменение баланса на основе полученных данных. </li>
	<li> Измение пользовательских данных по запросу. </li>
</ul>

<details>
<summary>Структура базы данных сервиса</summary>
	<img src="https://raw.githubusercontent.com/IvanCherep/baby-billing/screenshots/images/brt-service_db_structure.png" alt="Структура базы данных у BRT-сервиса">
</details>

<details>
<summary>Структура сервиса</summary>
<ul>
	<li> config </li>
	<ul>
		<li> KafkaConfig - Конфигурация для консьюмера Kafka. </li>
		<li> MapDeserializer - Преобразует массив байт в объект типа Map&ltString, byte[]&gt. Используется в качестве VALUE_DESERIALIZER_CLASS_CONFIG для конфигурации Apache Kafka.</li>
		<li> MapperConfig - Создает бин ModelMapper. </li>
		<li> RestTemplateConfig - Создает бин RestTemplate. </li>
	</ul>
	<li> consumer </li>
	<ul>
		<li> KafkaProducer - Консьюмер сообщений Kafka.</li>
	</ul>
	<li> controllers </li>
	<ul>
		<li> ClientController - Контроллер принимающий запросы от клиентов. На данный момент принимает только запрос на пополнение счета.</li>
		<li> ManagerController - Контроллер принимающий запросы от менеджеров. На данный момент принимает запросы на смену тарифа.</li>
	</ul>
	<li> domain </li>
	<ul>
		<li> dto </li>
		<ul>
			<li> ClientDto - DTO для клиентской сущности. </li> 
			<li> DataPlanDto - DTO для сущности тарифа. </li>
			<li> MsisdnDto - DTO для сущности номера телефона. </li>
		</ul>
		<li> entity </li>
		<ul>
			<li> ClientEntity - Сущность представляющая из себя данные о клиенте. </li> 
			<li> DataPlanEntity - Сущность представляющая из себя данные о тарифе. </li> 
			<li> MsisdnEntity - Сущность представляющая из себя данные о номере телефоне. </li>
		</ul>
		<li> AccountRefill - Данные для пополнения счета. </li>
		<li> CDR - Данные о звонке. </li>
		<li> ClientBill - Данные о списание денег и минут с клиента от HRS-сервиса. </li>
		<li> ClientCallData - Данные необходимые HRS-сервису для того, чтобы расчитать сколько денег и минут нужно списать с клиента. </li>
		<li> ManagerWill - Данные для смены тарифа. </li>
	</ul>
	<li> mappers </li>
	<ul>
		<li> impl </li>
		<ul>
			<li> CDRMapperImpl - Пробразует запись о звонке в строку и обратно.</li> 
			<li> ClientMapperImpl - Преобразует ClientEntity в ClientDto и обратно.</li> 
			<li> DataPlanMapperImpl - Преобразует DataPlanEntity в DataPlanDto и обратно.</li> 
			<li> MsisdnMapperImpl - Преобразует MsisdnEntity в MsisdnDto и обратно.</li> 
		</ul>
		<li> Mapper&ltA, B&gt - Маппинг интерфейс. </li>
	</ul>
	<li> repoitories </li>
	<ul>
		<li> ClientRepository - Репозиторий для взаимодействия с таблицей клиентов.</li>
		<li> DataPlanRepository - Репозиторий для взаимодействия с таблицей тарифов.</li>
		<li> MsisdnRepository - Репозиторий для взаимодействия с таблицей телефонных номеров и их характеристик.</li>
	</ul>
	<li> services </li>
	<ul>
		<li> BillingService - Сервис принимат запись о звонке, собирает данные о клиенте, который совершил звонок, и передает эти данные для оценки в HRS-сервис. На основе полученных данных изменяет баланс.</li>
		<li> CDRFileHandlerService - Считывает cdr-файл и все записи о звонках, которые относятся к клиентам нашего оператора передает в BillingService. </li>
		<li> ClientManagementService - Сервис выполняет все задачи переданные от клиентских и менеджерских контролллеров. </li>
	</ul>
	<li> BrtServiceApplication - Запускает приложение.</li>
</ul>
</details>

## HRS-сервис
### Описание
<p>Главная функция HRS-сервиса - расчет стоимости звонка. Также сервис выставлет счету на оплату клиентам с помесячным тарифом. То есть деньги с клиентов, которые используют тарифы с ежемесячном оплатой, будут списаны с получением записи о звонке клиента, которая датирутся новым месяцем.</p>
<details>
<summary>Структура сервиса</summary>
<ul>
	<li> controllers </li>
	<ul>
		<li> CustomersCallDataController - Контроллер принимат все необходимые для расчета стоимости звонка данные и возвращает стоимость звонка. </li>
	</ul>
	<li> domain </li>
	<ul>
		<li> ClientBill - Данные о списании денег и минут с клиента. </li>
		<li> ClientCallData - Данные необходимые сервису для того, чтобы расчитать сколько денег и минут нужно списать с клиента. </li>
	</ul>
	<li> services </li>
	<ul>
		<li> BillCalculationService - Сервис расчитывает стоимость звонка на основании полученных данных. </li>
	</ul>
	<li> HrsServiceApplication - Запускает приложение. </li>
</ul>
</details>

## Dictionary
* BRT – Billing Real Time
* CDR – Call Data Record
* HRS – High performance rating server
* msisdn  - Mobile Subscriber Integrated Services Digital Number
* PPM - price per minute
