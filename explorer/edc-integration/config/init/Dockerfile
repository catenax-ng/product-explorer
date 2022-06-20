FROM ubuntu:latest

COPY create-asset.sh /

RUN apt-get update && apt-get install -y curl

CMD ["bash", "/create-asset.sh"]