# pangolin-webshop
BME - VIK - Computer Security Homework Project

### TL;DR
The easiest way to run the application and the db:
* First: `./mvnw clean install -DskipTests` (building the server)
* Second: `./run-app.sh` (deploying the whole application with docker)

**Important**: on first run after executing the above command run this as well:
`docker exec pangolin-client rails webpacker:install` 

`Tip: with maven located in the root of the application, the root folder can be easily opened from editors as a project.`

## Deploy
The application deployment is managed by Docker Compose.

The docker compose configuration file can be found in the `docker/` folder.

There are convenience shell scripts (_run-app.sh_, _run-db-only.sh_, _stop-app.sh_), which run the `docker-compose` command with the adequate options.

`NOTE: If docker can be run only in sudo mode on your system, these commands shall be run in sudo mode also.`

* `run-app.sh` - Starts the application (server and db) - **IMPORTANT - The server should be built already see below**
* `run-db-only.sh` - Starts the db only
* `stop-app.sh` - Stops the running components (works for db only and server-db combined also)

## Build
The application's build tool is maven. See the README of _webshop-server_ for further details.

`NOTE: If you don't have mvn (with the adequate version) on your system, instead of mvn use ./mvnw in the commands below.`

* `mvn clean install` - Builds the server application - **IMPORTANT - This process runs tests also, so the db must be running for these to be successful**
* `mvn clean install -DskipTests` - Builds the server application, without tests (so running the db is not a requirement)
* `mvn spring-boot:run` - Runs the server application locally (outside of docker) - **IMPORTANT - For running the application the db must be running**
