# Catena-X Product Explorer #

## Local direct Twin registry integration ##

Setup for running product explorer with a direct connection to several twin registries containers and a mocked registry hub.
This setup accesses the registry hub which provides addresses to known EDCs.
When calling those addresses, the response is a DTR with needed endpoints to retrieve shells descriptors from the twin registry.
Product explorer is making calls to those endpoints to retrieve needed data from the twin registry and aggregate them in order to present the results to the user.

### Prerequisites ###

* Docker

### Setup ###

1) Run ./run.sh from examples/1_twin_registries (chmod +x ./run.sh may be needed before). This script runs some populated with mocked data twin registries and a registry hub.
2) Run mvn install from parent pom.xml
3) Run mvn clean spring-boot:run from core module
4) Import the postman collection in order to interact with the api. TODO