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