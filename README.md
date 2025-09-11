# Diplom_3
Автотесты UI для Stellar Burgers

Stellar Burgers — это веб-приложение для сборки космических бургеров. Данный репозиторий содержит автоматизированные UI-тесты, написанные на Java с использованием Selenium WebDriver и TestNG.

✅ Цель проекта

Автоматизировать проверку ключевых сценариев пользовательского интерфейса приложения Stellar Burgers:

    Регистрация пользователя:
        Успешная регистрация с валидными данными.
        Проверка ошибки при вводе некорректного пароля (менее 6 символов).
         
    Вход в систему:
        Вход через кнопку "Войти в аккаунт" на главной странице.
        Вход через кнопку "Личный Кабинет".
        Вход через форму регистрации.
        Вход через форму восстановления пароля.
         
    Работа с конструктором бургеров:
        Проверка корректной работы переходов между разделами: "Булки", "Соусы", "Начинки".


🛠️ Технологический стек

    Язык программирования: Java 11
    Фреймворк для тестирования: TestNG
     

    Веб-драйвер: Selenium WebDriver 4+
    Модель проектирования: Page Object Model (POM)
    Генерация отчетов: Allure
     

    Управление зависимостями: Maven
    Поддерживаемые браузеры: Google Chrome, Яндекс.Браузер (на одном chromedriver.exe)

## Структура проекта

```
Diplom_3/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── constants/
│   │       │   └── AppConstants.java
│   │       └── org.example/
│   │           └── Main.java
│   └── test/
│       ├── java/
│       │   ├── pages/
│       │   │   ├── BasePage.java
│       │   │   ├── LoginPage.java
│       │   │   ├── MainPage.java
│       │   │   └── RegisterPage.java
│       │   ├── tests/
│       │   │   ├── ConstructorTest.java
│       │   │   ├── LoginTest.java
│       │   │   └── RegistrationTest.java
│       │   └── utils/
│       │       ├── ApiHelper.java
│       │       └── DriverFactory.java
│       └── resources/
│           ├── allure.properties
│           └── testng.xml
├── target/
│   └── allure-results/
├── .gitignore
├── pom.xml
└── README.md
```

⚙️ Настройка и запуск

    Установите Java 11.
    Скачайте chromedriver.exe и поместите его в папку E:\Webdriver\bin\ (или укажите свой путь в AppConstants.java).
    Убедитесь, что у вас установлен Яндекс.Браузер. Путь к его исполняемому файлу должен быть корректен в AppConstants.java.
    Склонируйте репозиторий и откройте его в вашей IDE (например, IntelliJ IDEA).
    Запустите тесты одним из способов:
        Через testng.xml: ПКМ по файлу -> Run 'testng.xml'.
        Через Maven: mvn clean test.


📊 Генерация отчета Allure

После выполнения тестов, для просмотра красивого HTML-отчета выполните в терминале:
bash
allure serve target/allure-results

Это автоматически сгенерирует и откроет отчет в вашем браузере.