# github-fop-sb

A mini project to generate a basic report of a GitHub user's repos using SpringBoot, Apache FOP and XSLT.
Definitely not the best, but it was a fun thing to mess around with for a bit.

## Test Call

```http://127.0.0.1:8080/api/v1/report/?username=barrettotte``` - returns PDF binary in [docs/barrettotte.pdf](docs/barrettotte.pdf)

## Setup

- Add github token with read permissions to ```src/main/resources/application.properties```
- ```gradlew bootrun``` - listening on ```http://127.0.0.1:8080```

## References

- [Apache FOP](https://xmlgraphics.apache.org/fop/)
