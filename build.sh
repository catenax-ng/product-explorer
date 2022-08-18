#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

cd self-description-hub
#docker build image
mvn clean package -DskipTests
docker image build -t self-description-hub .

cd ../explorer/edc-integration/edc
./gradlew publishToMavenLocal -x test

cd ../edc-controlplane-memory
./gradlew clean build

cd ../edc-dataplane
./gradlew clean build