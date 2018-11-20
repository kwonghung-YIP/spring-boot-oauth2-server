## spring-boot-oauth2-server

### Description
A production ready **Authorization Server** implementation provided by the [spring-security-oauth](https://projects.spring.io/spring-security-oauth/docs/oauth2.html) project, illustrate the advance features such as:

- Support multiple nodes, client registration, token and user approval records are share among the nodes with
  - Redis
  - Jdbc
  - Spring session
- Enable the CORS filter to support ajax preflight request
- Provide the UI to manage the client registration records
- User Repository backed by the openldap

### Getting Started with the docker compose file

- mysql
- redis
- openldap

```
docker-compose
```

### Support following OAuth grant types

- [Authorization Code](../../wiki/grant-type-authorization-code)
- [Implicit](../../wiki/grant-type-implicit)
- [Resource Owner Credential](../../wiki/grant-type-resource-owner-credential)
- [Client Credential](../../wiki/grant-type-client-credential)
- [Refresh Token](../../wiki/grant-type-refresh-token)
