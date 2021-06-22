# github-fop-sb

A mini project to generate a basic report of a GitHub user's repos using SpringBoot, Apache FOP and XSLT.
Definitely not the best, but it was a fun thing to mess around with for a bit.

## Test Calls

- ```http://127.0.0.1:8080/api/v1/report/?username=barrettotte``` - returns PDF binary in [docs/barrettotte.pdf](docs/barrettotte.pdf)
- ```http://127.0.0.1:8080/api/v1/report/test``` - returns PDF binary created with local XML data in [src/main/resources/data/test_data.xml](src/main/resources/data/test_data.xml)

## Setup

- Add github token with read permissions to ```src/main/resources/application.properties```
- ```gradlew bootrun``` - listening on ```http://127.0.0.1:8080```

## References

- [Apache FOP](https://xmlgraphics.apache.org/fop/)
