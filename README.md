# github-fop-sb

A mini project to generate a basic PDF of a GitHub user's repos using SpringBoot, Apache FOP and XSLT.
Definitely not the best, but it was a fun thing to mess around with for a bit.

## Test Calls

- ```http://127.0.0.1:8080/api/v1/report/?username=barrettotte``` 
  - calls GitHub GraphQL API with GraphQL queries [src/main/resources/gql/user.gql](src/main/resources/gql/user.gql) and [src/main/resources/gql/repos_all.gql](src/main/resources/gql/repos_all.gql)
  - returns PDF in [docs/barrettotte.pdf](docs/barrettotte.pdf)
- ```http://127.0.0.1:8080/api/v1/report/test``` 
  - uses local XML data in [src/main/resources/data/test_data.xml](src/main/resources/data/test_data.xml)
  - returns PDF in [docs/test.pdf](docs/test.pdf)

## Setup

- Add github token with read permissions to ```src/main/resources/application.properties```
- ```gradlew bootrun``` - listening on ```http://127.0.0.1:8080```

## References

- [Apache FOP](https://xmlgraphics.apache.org/fop/)
