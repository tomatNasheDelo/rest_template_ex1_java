# Демонстрация Spring `RestTemplate` и микросервисов — Java версия

Java-версия учебного проекта: React-интерфейс вызывает API первого Spring Boot-сервиса, а тот синхронно обращается ко второму сервису с помощью `RestTemplate`.

## Состав проекта

- **orchestrator-service** — API для фронтенда, порт `8080`; вызывает greeting-сервис через `RestTemplate`.
- **greeting-service** — возвращает приветствие, порт `8081`.
- **frontend** — React + TypeScript + Vite 7, порт `5173` (без изменений).
- **Docker Compose** — запускает все три приложения.

```text
Браузер → React/Vite (:5173) → orchestrator-service (:8080)
                                      │
                                      └── RestTemplate → greeting-service (:8081)
```

Структура сервисов и поведение API сохранены; Kotlin-код заменён на Java, Gradle Kotlin DSL оставлен.

## Быстрый запуск через Docker

Нужны Docker и Docker Compose. В корне проекта выполните:

```bash
docker compose up --build
```

Откройте http://localhost:5173, введите имя и нажмите кнопку. API также доступно напрямую по адресу http://localhost:8080/api/message.

Остановить приложения: `Ctrl+C`, затем `docker compose down`.

## Запуск без Docker

Нужны JDK 17+, Gradle 8.10+ и Node.js 20.19+ (или 22.12+).

В отдельных терминалах запустите сервисы:

```bash
cd greeting-service && gradle bootRun
```

```bash
cd orchestrator-service && gradle bootRun
```

Затем фронтенд:

```bash
cd frontend
npm install
npm run dev
```

Vite перенаправляет `/api` на `http://localhost:8080` по умолчанию. При необходимости адрес сервиса можно задать переменной `VITE_API_PROXY_TARGET`.

## Проверка API через curl

```bash
curl 'http://localhost:8081/api/greeting?name=Анна'
# {"message":"Привет, Анна! Ответ от greeting-service."}

curl 'http://localhost:8080/api/message?name=Анна'
# {"message":"Привет, Анна! Ответ от greeting-service."}
```

## Структура

```text
.
├── greeting-service/       # Spring Boot + Java, порт 8081
├── orchestrator-service/   # Spring Boot + Java + RestTemplate, порт 8080
├── frontend/               # React + TypeScript + Vite 7
└── compose.yaml
```
