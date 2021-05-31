Streaming template
------------------
The focus point of this example/template is about create a simple ecosystem with streaming services and apis. Plus provie
a simple strategy to stand up a local environment for development.
                             
## Streaming ecosystem
The architecture is simple, the concept is to create a system which ingest quickly incoming HTTP requests from external 
clients, using Kafka as the isolation layer. Afterwards an ingestion service is consuming those messages for storing them 
into a relational database.

The two customised services, the streaming api service, and the ingestion consumer service are able to scale up
horizontally, the backing services, Apache Kafka, Redis and MariaDB are also scalable. How to scale them depends on the platform,
but that topic is out of scope from the template.

* [Api service](./api/API.md)
* [Message consumer service](./message-consumer/MESSAGE_CONSUMER.md)

## Local environment for development
It's also focused on creating the simplest way to stand up a dev local environment, noticeable for devs to start working 
as fast as possible.

### Final scenario
The final scenario is to provide an useful and integral local environment for development.

It requires initialize some backing services and the customized services properly configured, plus with some OS rules.

In order to reach the goal, Docker provides the tool docker-compose which allows elaborating it easily.

To build the docker images, we could do it manually or adding the commands into the deployment script, but gets introduced 
a maven plugin by Spotify to do it out of the box at compilation time.

Taking advantage of Spring profiles and other features from different Spring modules, the database schema plus some testing 
data gets configured from the beginning.

### How to initialize it
Inside the ```local``` folder there's a script for standing up the local environment.
1. Configures the hosts urls.
2. Compiles the code with mvn, running tests and creating the Docker images into the local registry.
3. Runs docker-compose for running the services. 
4. Takes advantage of some Spring utilities for configuring the local database with testing data.
                                
To run it just run the init script from the ```local``` folder.
> ./init {path_to the hosts file}

### Resources
Inside the ```api/__resources``` folder there's a simple Postman collection to interact with the local API instance.

## Kafka ops
For monitoring and perform other actions on Kafka run the kafka scripts accessing them from inside the docker container.

### Topics

Create:
> docker-compose exec st-kafka-1 kafka-topics --create --zookeeper st-zookeeper:2181 --replication-factor 2 --partitions 2 --topic message

Delete
> docker-compose exec st-kafka-1 kafka-topics --delete --zookeeper st-zookeeper:2181 --topic message

List
> docker-compose exec st-kafka-1 kafka-topics --list --zookeeper st-zookeeper:2181

Describe
> docker-compose exec st-kafka-1 kafka-topics --describe --zookeeper st-zookeeper:2181 --topic message

### Consumer groups

List
> docker-compose exec st-kafka-1 kafka-consumer-groups --bootstrap-server st-kafka-1:9092 --list

Active Members
> docker-compose exec st-kafka-1 kafka-consumer-groups --bootstrap-server st-kafka-1:9092 --describe --group message-consumer --members --verbose

Describe / Lag
> docker-compose exec st-kafka-1 kafka-consumer-groups --bootstrap-server st-kafka-1:9092 --describe --group message-consumer

### Producing and consuming.

Producing data (each line is a new message).
> docker-compose exec st-kafka-1 kafka-console-producer --broker-list st-kafka-1:9092 --topic message

Listening data
> docker-compose exec st-kafka-1 kafka-console-consumer --bootstrap-server st-kafka-1:9092 --consumer-property group.id=message-consumer-console --topic message --from-beginning