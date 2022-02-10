# ekichabi-business-registration

### Description

### How to build

- Install Gradle (`Brew install gradle`)
- Install Postgresql (`Brew install postgres`)
- Start local postgres server (`Brew services start postgresql`)

### How to run locally
You have two options! You can
1. Run the Spring Boot app from CLI (`gradle bootrun`) or from Intellij
2. Run via Heroku CLI
   1. First, build the project with `gradle build` - this generates the JAR file which Heroku will run
   2. Next, run `Heroku local -f Procfile.dev` - this directly mirrors the run config used by Heroku in production


### How to Deploy
- You can deploy the application directly to Heroku by pushing to Main.
- PLEASE DONT THOUGH! 
   - We love PRs here at ekichabi-business-registration. Run ya tests (Limitation: Tests are not run as a part of CI - please run them locally before opening a PR), get some eyes on ya code, and merge your feature branch into Main once ya get ya approval.


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