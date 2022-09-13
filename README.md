# Explorer Demo

Table of content:

- [Prerequisites](#prerequisites)
    - [Build the EDC artifacts](#build-the-edc-artifacts)
- [Build](#build)
- [Building each service manually](#building-each-service-manually---without-use-of-buildsh)  
    - [Build EDC-Controlplane-Memory](#build-edc-controlplane-memory)
    - [Build EDC-Dataplane](#build-edc-dataplane)
    - [Build EDC-Controlplane-Memory with needed extension](#build-edc-controlplane-memory-with-needed-extension)
    - [Build SDH - Self description hub](#build-sdh---self-description-hub)
- [Run self-build multiple docker container](#run-self-build-multiple-docker-container)
- [Initialize services with needed data](#initialize-services-with-needed-data)
- [Known Issues](#known-issues)

## Prerequisites

### Build the EDC artifacts

```shell
git submodule update --init
cd edc && ./gradlew publishToMavenLocal -x test
```

## Build

To build all components in one command, you can execute the following script.
It's necessary, that `mvn` is installed on the local machine.
There is still need to build the control-plane with the needed extension manually [here](#build-edc-controlplane-memory-with-needed-extension)

```shell
./build.sh
```

## Building each service manually - without use of `build.sh`

### Build EDC-Controlplane-Memory
```shell
cd edc-controlplane-memory && ./gradlew clean build
```

### Build EDC-Dataplane
```shell
cd edc-dataplane && ./gradlew clean build
```

### Build EDC-Controlplane-Memory with needed extension

WIP - need to move the right control plane to project, for now it is build separetly from product-edc project:
https://github.com/marcingajek-zf/product-edc-cp-adapter.git
```shell
cd edc-controlplane/edc-controlplane-memory
mvn clean package -DSkipTests
docker image build -t edc-controlplane-memory-with-extension .
```

### Build SDH - Self description hub
```shell
cd self-description-hub && mvn clean package -DskipTests
```

## Run self-build multiple docker container

```shell
docker-compose up --build
```

## Initialize services with needed data

Run `./populate.sh` in order to populate services with needed test data.

After everything was booted and initialized, the UI can be accessed with this url:
<http://localhost:8080> and search for given submodel: `urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918`

## Known Issues

The following SF4J Error can be ignored:

```
java.lang.AbstractMethodError: Receiver class org.eclipse.dataspaceconnector.boot.monitor.MonitorProvider does not
define or inherit an implementation of the resolved method 'abstract java.lang.String getRequestedApiVersion()' of
interface org.slf4j.spi.SLF4JServiceProvider.
```
