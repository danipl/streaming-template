Api service
-----------
A simple streaming api, with some endpoints ingesting into the isolation layer the messages coming from the external clients,
and others to retrieve data from the database. The underlying service repo provides meaningful cache capabilities for retrieving  
data from cache instead of querying it, something remarkable about the cache is that it is distributed and backed on Redis, 
it has alto to take into account that the cache is not valid for all scenarios depending on the use case.

It also provides KPI capabilities working with resilence4j, a library which provides different strategies to face with errors,
timeouts and so on.

### Security
Security enabled, authentication process and authorization with two different roles for ADMIN and USER with access to 
different endpoints.

### Swagger
Swagger is enabled at

> http://localhost:8080/swagger-ui/index.html