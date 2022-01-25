# ekichabi-business-registration

### Description

### How to build

- Install Gradle (`Brew install gradle`)
- Install Postgresql (`Brew install postgres`)
- Start local postgres server (`Brew services start postgresql`)

### Initialize DB
- Run `flyway migrate -url=jdbc:postgresql://localhost:5432/ekichabi -user=ekichabi -password=ekichabi`

### Clean DB
- Run `flyway clean -url=jdbc:postgresql://localhost:5432/ekichabi -user=ekichabi -password=ekichabi`

### How to run locally
- Run the Spring Boot app from CLI (`gradle bootrun`) or from Intellij