# API Java Web Services
Api responsabel por prover endpoints para uma aplicação de planejamento de viagens.

## Features

- [x] Criar viagens
- [x] Consultar Viagens
- [x] Criar participantes
- [x] Consultar participantes
- [x] Criar atividades
- [x] Consultar atividades
- [x] Criar links
- [x] Consultar links
- [x] Enviar convites aos participantes
- [x] Confirmar viagens
- [x] Persistir dados em memória (M2)


## Endpoints

- Criar viagens: 
```
POST /trips

body:
{
  "destination": "",
  "starts_at": "2000-00-00T00:00:00.0000",
  "ends_at": "2000-00-00T00:00:00.0000",
  "emails_to_invite": [""],
  "owner_name": "",
  "owner_email": ""
}
```
- Consultar Viagens: `GET trips/[tripId]`
- Confirmar viagens - Participant: `POST /participants/[tripId]/confirm`
- Criar participantes: `POST / participants/`
- Consultar participantes
- Criar atividades
- Consultar atividades
- Criar links
- Consultar links
- Enviar convites aos participantes

### Principais Referências
Abaixo estão relacionadas as principais ferramentas utilizadas no projeto:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.flyway)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#using.devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

### Guides
Para se aprofundar:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
