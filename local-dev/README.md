# Introduction

The Explorer interacts with multiple `eclipse dataspace connectors`.  
For local development we use a docker-compose based setup of all required components.  

This folder contains the setup configuration. 
The folder `generator` contains an `ansible` playbook that is used to generate the required configuration
for a data-provider / data-consumer.

The `generator` is capable of generating multiple data-provider and data-consumer.
The container internal ports remain same. The port mapping to the host will be incremented by an index.
Please chechk the `./generator/roles/edc/docker-compose.yml.j2` file for the detailed port mappings.

# Using the generator

1. Navigate to the generator folder  
2. Run the command `ansible-playbook site.yml -i localhost`  
   If you want additional data-providers / data-consumers to be generated,  
   you can add a new entry to the array in `site.yml`.

# Building new images for local dev

The official images from `https://github.com/catenax-ng/product-edc` have dependencies to  
DAPS and Azure Keyvault. This is problematic for local development. Therefore we build custom images based  
on the product-edc codebase that replaces the DAPS and Azure Keyvault with local integrations (edc iam:mock & edc  filesystem:vault).  

The repository with the modifications is: `https://github.com/bci-oss/product-edc/tree/feature/images-for-local-dev`.  
If you want to build new images, you need to do the following.  

1. Checkout the branch `https://github.com/bci-oss/product-edc/tree/feature/images-for-local-dev`  
2. Run `git submodule update --init --recursive`  
3. Navigate to the `edc` directory and run `./gradlew clean build publishToMavenLocal -x test`  
4. Navigate to the project root and run `mvn clean install -DskipTests`  
5. Navigate to `edc-controlplane/edc-controlplane-postgresql` and run `mvn clean install -DskipTests -Pwith-docker-image`  
6. Navigate to `edc-dataplane` and run `mvn clean install -DskipTests -Pwith-docker-image`  
7. You can now tag the images and push them:  
    Controlplane:
    `docker tag edc-controlplane-postgresql:0.0.3  ghcr.io/catenax-ng/product-explorer/edc-controlplane-postgresql:0.0.3-local-dev`  
    `docker push ghcr.io/catenax-ng/product-explorer/edc-controlplane-postgresql:0.0.3-local-dev`  
    Dataplane:  
    `docker tag edc-dataplane:0.0.3  ghcr.io/catenax-ng/product-explorer/edc-dataplane:0.0.3-local-dev`  
    `docker push ghcr.io/catenax-ng/product-explorer/edc-dataplane:0.0.3-local-dev`  
