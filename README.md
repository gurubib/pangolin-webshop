# pangolin-webshop
BME - VIK - Computer Security Homework Project

## Deploy
### Generate SSL files (first time only)
First, you have to generate a self-signed certificate in the `pangolin-client/config/` folder.
```
cd webshop-client/config/
openssl req -x509 -sha256 -nodes -newkey rsa:2048 -days 365 -keyout localhost.key -out localhost.crt
```
### Build and run the application
Next, you should provide de required passwords and secrets. There is a `.env.sample` file, copy it as `.env` **CHANGE THE PASSWORDS AND SECRETS INSIDE IT**, then save it. After this the application will use the given passwords and secrets.

The easiest way to run the application and the db:
* First: `./mvnw clean install -DskipTests` (building the server)
* Second: `./run-app.sh` (deploying the whole application with docker) - Building the containers for the first time could take up several minutes (especially the pangolin-client's bundle install step - up to 10-15 minutes)

`NOTE: To stop the application just run stop-app.sh`

## Run Server Tests
To run the tests written for the server part you must run the test-database container. Issue the following commands (but stop the application before this):

```
./run-test-db-only
./mvnw clean install
```
The `stop-app.sh` can also be used to stop the test-database container.
