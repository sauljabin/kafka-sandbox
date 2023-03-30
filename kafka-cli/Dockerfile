FROM ubuntu:20.04

WORKDIR /opt

ENV CONFLUENT_VERSION "7.3"
ENV CONFLUENT_FULL_VERSION "7.3.2"
ENV CONFLUENT_URL "http://packages.confluent.io/archive/${CONFLUENT_VERSION}/confluent-community-${CONFLUENT_FULL_VERSION}.zip"
ENV CONFLUENT_HOME "/opt/confluent"
ENV CONFLUENT_BIN "${CONFLUENT_HOME}/bin"

ENV PATH "$PATH:${CONFLUENT_BIN}"

RUN apt-get update \
    && apt-get install -y \
    wget \
    curl \
    vim \
    unzip \
    kafkacat \
    openjdk-11-jdk \
    jq \
    httpie \
    && rm -rf /var/lib/apt/lists/*

RUN wget -q "${CONFLUENT_URL}" -O /tmp/confluent.zip \
    && unzip /tmp/confluent.zip \
    && mv "confluent-${CONFLUENT_FULL_VERSION}" confluent \
    && rm /tmp/confluent.zip