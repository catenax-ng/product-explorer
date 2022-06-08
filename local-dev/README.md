# Introduction

The Explorer interacts with multiple `eclipse dataspace connectors`.  
For local development we use a docker-compose based setup of all required components.  

This folder contains the setup configuration. 
The folder `generator` contains an `ansible` playbook that is used to generate the required configuration
for a data-provider / data-consumer.

# Using the generator

1. Navigate to the generator folder
2. Run the command `ansible-playbook site.yml -i localhost`
   If you want additional data-providers / data-consumers to be generated,
   you can add a new entry to the array in `site.yml`.