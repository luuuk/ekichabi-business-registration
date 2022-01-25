# ekichabi-business-registration

### Description

### How to build

- Install Gradle (Brew install gradle)

### Initialize DB
- Run `flyway migrate -url=jdbc:postgresql://localhost:5432/ekichabi -user=ekichabi -password=ekichabi`

### Clean DB
- Run `flyway clean -url=jdbc:postgresql://localhost:5432/ekichabi -user=ekichabi -password=ekichabi`
