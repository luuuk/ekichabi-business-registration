# eKichabi Business Registration Module

### Description

This repository contains the source code for the eKichabi Business Registration Module, implemented
as a Spring Boot Application with a Postgres Database (Managed by Flyway's DB Versioning) deployed
(for free) to Heroku. It also contains a USSD application and simulator - the simulator can be
accessed as a REST endpoint, and the application itself can be deployed to AfricasTalking.com or a
similar service. This README outlines steps for local development and deployment on a Mac system -
the app works on other OSes, but our team has used Mac.

### Required tools

- Gradle (`Brew install gradle`)
- Postgresql (`Brew install postgres`)

### Repository Layout

- config
    - _Configuration for helper tools, currently just our checkstyle_
- gradle
    - _Gradle stuff we don't really understand_
- src
    - main
        - java
            - com.ekichabi_business_registration
                - controller
                    - _REST API endpoint definitions and handlers for the app_
                - db
                    - entity
                        - _JPA Entity classes. Mappings from DB entries to Java objects are defined
                          in these classes_
                    - repository
                        - _JPA Repositories. CRUD operations for the DB are defined as Java methods
                          by these handy dandy interfaces._
                - screens
                    - _@YIHONG TODO_
                - service
                    - _The logic layer of our app. Typically invoked by Controllers or Screens,
                      Service classes handle validation of input and make calls to repositories._
                - util
                    - _Helper methods._
                - App.java
                    - _Spring app definition, pretty simple_
                - DatabaseConfig.java
                    - _Spring DB config - reads from application.properties files to get config
                      vars._
        - resources
            - db
                - migration
                    - _Versioned flyway migrations for the DB. They are replayed against the Prod DB
                      on deployment, so don't mess them up! Make sure the timestamps are ordered,
                      (Intellij has a good plugin for this) and never delete them once deployed._
                - model.sql
                    - _Best-effort attempt at a schema guide for our DB. However, it is maintained
                      by hand and may be out-of-date._
            - static
                - _Non-code files used by the project (think census data)_
            - templates
                - _files to replicate for different purposes_
            - application-*.properties
                - _Environment variables to set based on which Spring profile is running. In prod,
                  this is the prod profile. In development, it is default._
    - test
        - _Unit tests for components in src folder_
- build.gradle
    - _Defines dependencies and tasks to make the app go!_
- Procfile
    - _Used by Heroku to know how to run the app_
- Procfile.dev
    - _Used by the `heroku local -f Procfile.dev` command to replicate a Heroku deployment on local
      machine_

### How to build the project

- Run `gradle build` from the project root (this will run checkstyle and all tests in the project)

### How to run locally

First, start a local postgres server (`Brew services start postgresql`). Then you have two options!
You can:

1. Run the Spring Boot app from CLI (`gradle bootrun`) or from Intellij
2. Run via Heroku CLI
    1. First, build the project with `gradle build` - this generates the JAR file which Heroku will
       run
    2. Next, run `Heroku local -f Procfile.dev` - this directly mirrors the run config used by
       Heroku in production

### How to Deploy

- Our app is configured with @luuuk's personal Heroku account for Deployment. Continuous Deployment
  is disabled as active development is no longer happening.
    - It is easy to re-enable Heroku CD with this app on your own account, if you decide to go a
      different route.
    - There is nothing that will prevent this application from running on any other cloud provider (
      we picked Heroku cuz we hav no $). The Procfile's in the root of the project define Heroku
      deployment commands.

### USSD Testing
Our USSD app uses AfricasTalking.com as the interface between our Spring rest application and USSD app. In order to develop and test the USSD flow, we've configured a staging version of our application and deployed it to Heroku. This means that the recommended workflow for devs updating the USSD app is:

1. Create a local feature branch for your changes (based on master)
2. Make basic changes, verify that gradle build still succeeds
3. Merge your feature branch into remote Staging (this will kick off an auto-deploy to Heroku)
4. Repeat 2-3 until feature changes are functional in Staging
5. Open a PR from your feature branch into Main
6. Once approved and merged, delete your remote feature branch
7. Finally, rebase Staging off of Main and push Staging (this should have no effect, but we want to keep Staging and Main in sync for the next person who makes USSD changes)

### Running migrations from CLI
`flyway migrate -url=jdbc:postgresql://localhost:5432/{DBName} -user={DBUser} -password={DBPW} -locations=src/main/resources/db`