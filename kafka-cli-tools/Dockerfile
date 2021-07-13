# CLI TOOLS: kafkacat, zoe, confluent cli
FROM ubuntu:20.04

WORKDIR /opt

ENV ZOE_VERSION "0.27.2"
ENV ZOE_URL "https://github.com/adevinta/zoe/releases/download/v${ZOE_VERSION}/zoe-${ZOE_VERSION}.tar"
ENV ZOE_HOME "/opt/zoe"
ENV ZOE_BIN "${ZOE_HOME}/bin"

ENV CONFLUENT_VERSION "6.1"
ENV CONFLUENT_FULL_VERSION "6.1.0"
ENV CONFLUENT_URL "http://packages.confluent.io/archive/${CONFLUENT_VERSION}/confluent-community-${CONFLUENT_FULL_VERSION}.zip"
ENV CONFLUENT_HOME "/opt/confluent"
ENV CONFLUENT_BIN "${CONFLUENT_HOME}/bin"

ENV PATH "$PATH:${CONFLUENT_BIN}:${ZOE_BIN}"

RUN apt-get update \
    && apt-get install -y \
    wget \
    curl \
    vim \
    unzip \
    kafkacat \
    openjdk-11-jdk \
    && rm -rf /var/lib/apt/lists/*

RUN wget -q "${ZOE_URL}" -O /tmp/zoe.tar \
    && tar fx /tmp/zoe.tar \
    && rm /tmp/zoe.tar

RUN wget -q "${CONFLUENT_URL}" -O /tmp/confluent.zip \
    && unzip /tmp/confluent.zip \
    && mv "confluent-${CONFLUENT_FULL_VERSION}" confluent \
    && rm /tmp/confluent.zip