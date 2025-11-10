# Clean Architecture - Java (Spring Boot)

Questo progetto è una codebase Java che applica i principi della Clean Architecture.
Fornisce una base organizzata e manutenibile per lo sviluppo di applicazioni con Spring Boot.

Contenuto principale
- Architettura a strati: domain, repository, service, controller (presentation)
- Persistenza: JPA + PostgreSQL
- Migrazioni: Flyway (cartella `src/main/resources/db/migration`)
- Autenticazione: JWT (HS256) con claims subject=userId e claim `email`
- Password hashing: PBKDF2WithHmacSHA512
- Logging: Logback + Logstash encoder -> Seq (docker-compose espone porta 5341)
- Domain events: semplice raccolta in `BaseEntity` e pubblicazione tramite ApplicationEventPublisher dopo il save
- Permission system: entità `Permission` + `PermissionService` per controlli autorizzativi

Motivazione della Clean Architecture
-----------------------------------
Clean Architecture separa le responsabilità in layer chiari per rendere il codice più manutenibile, testabile e indipendente dal framework o dalla tecnologia di persistenza.

Principi chiave presenti in questo progetto:
- Entities (Domain): contengono la logica di dominio e i modelli di dati (cartella `domain`).
- Use cases / Services (Application): orchestrano operazioni di alto livello (cartella `service`).
- Adapters / Infrastructure: implementazioni tecniche (JPA repositories, JWT provider, password hashing).
- Presentation: controller REST che espongono gli endpoint.

Come sono state realizzate le scelte architetturali
--------------------------------------------------
- Separazione dei layer: il codice è organizzato in pacchetti `domain`, `repository`, `service`, `controller`, `security`, `permission`, `mapper`, `exception`.
- Dipendenza verso il basso: i servizi dipendono dalle interfacce dei repository e non dal framework direttamente (Spring wiring attraverso constructor injection).
- Domain Events: ogni entità può raccogliere domain events tramite `BaseEntity`. I servizi pubblicano questi eventi dopo il salvataggio con `ApplicationEventPublisher`.
- Password hashing: PBKDF2WithHmacSHA512 configurato con salt 16, 500k iterazioni e hash 32 bytes.
- Autenticazione e autorizzazione: JWT con subject=userId e claim `email`. `SecurityConfig` aggiunge un filtro che valida il token e imposta il principal come userId; il `SecurityUserContext` estrae userId per i service.
- Permissions: tabella `permissions` e `PermissionService` per i controlli autorizzativi.

Esecuzione e sviluppo
---------------------
Prerequisiti:
- Java 21
- Maven
- Docker (per eseguire docker-compose)

Build del progetto:

```powershell
mvn -f java/pom.xml clean package -DskipTests
```

Avvio con Docker (nel folder `java`):

```powershell
cd java
docker compose up --build
```

Endpoint principali
- POST /api/users/register -> registra un utente
- POST /api/users/login -> ritorna { token }
- GET /api/todos -> lista todo (autenticazione richiesta)
- POST /api/todos -> crea todo (autenticazione richiesta)
- PUT /api/todos/{id} -> aggiorna todo (deve essere owner o possedere permission 'todos.manage')

Note operative e raccomandazioni
--------------------------------
- Secret JWT: NON usare il valore hard-coded in produzione. Utilizzare secret manager o variabili d'ambiente sicure.
- Flyway: se il database ha già una baseline, creare migrazioni incrementali invece di modificare `V1__init.sql`.
- Test: aggiungere test unitari e d'integrazione (es. Mockito per services, Testcontainers per DB).
- Domain Events: al momento gli eventi sono pubblicati dai servizi dopo il salvataggio; valutare l'uso di transaction synchronization o repository wrapper per automatizzare la pubblicazione.

Struttura directory (rilevante)

 - `src/main/java/it/alf/cleana/domain` - entità e domain events
 - `src/main/java/it/alf/cleana/repository` - JPA repositories
 - `src/main/java/it/alf/cleana/service` - casi d'uso / services
 - `src/main/java/it/alf/cleana/controller` - API REST
 - `src/main/java/it/alf/cleana/security` - JWT provider, PBKDF2 hasher
- `src/main/resources/db/migration` - Flyway migrations
- `docker-compose.yml` - compose con postgres e seq

Contatti e next steps
- Se vuoi, posso:
	- aggiungere test + Testcontainers,
	- integrare un `PermissionEvaluator` personalizzato e usare `@PreAuthorize` per metodi,
	- migliorare OpenAPI/Swagger con schemi e security definitions,
	- aggiungere un meccanismo centrale per pubblicare domain events automaticamente post-commit.

