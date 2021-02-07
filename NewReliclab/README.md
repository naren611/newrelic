# NewRelicLab
This repository contains newRelicLab


### Project Layout
- `.github` — contains workflow files that govern GitHub CI functionality
- `src/main` — contains java files from which the build generates a jar file
- `src/main/resources` — contains application configuration

### Common build tasks
- run the service locally: `./startServer`
- run tests, coverage verification: `./gradlew check`
- run tests, without additional checks: `./gradlew test`
- package the application into a `.jar` file: `./gradlew assemble`


To start the server, use "startServer" shell script
To start the one or more clients, use "startClient" shell script.

for some reason the dependencies are not getting bundled and the gradle test is also not executing for some reason. Please import the project into eclipse

run NewRelicServer -- this starts up the server socket
run NewRelicClient (5 processes) and that should publish 1000 messages. this can be configured

server only accepts 5 connections
when client receives terminate, it'll close all client connections and the server (this i have tested using the system input) 
