FROM ubuntu:24.04

ARG CONFLUENT_VERSION
WORKDIR /kafka-sandbox

ENV GRADLE_VERSION "8.8"
ENV GRADLE_HOME "/opt/gradle"
ENV GRADLE_BIN "${GRADLE_HOME}/bin"
ENV CONFLUENT_VERSION "$CONFLUENT_VERSION"
ENV CONFLUENT_HOME "/opt/confluent"
ENV CONFLUENT_BIN "${CONFLUENT_HOME}/bin"
ENV PATH "${PATH}:${CONFLUENT_BIN}:${GRADLE_BIN}:/root/.local/bin"
ENV COLORTERM "truecolor"
ENV TERM "xterm-256color"

RUN apt update \
    && apt install -y \
        wget \
        curl \
        vim \
        unzip \
        kcat \
        openjdk-17-jdk-headless \
        jq \
        httpie \
        postgresql-client \
        mysql-client \
        mosquitto-clients \
        python-is-python3 \
        python3 \
        pipx \
    && rm -rf /var/lib/apt/lists/* \
    && pipx install kaskade

RUN wget -q "http://packages.confluent.io/archive/$(echo "${CONFLUENT_VERSION}" | cut -c 1-3)/confluent-community-${CONFLUENT_VERSION}.zip" -O /tmp/confluent.zip \
    && unzip /tmp/confluent.zip -d /tmp \
    && mv "/tmp/confluent-${CONFLUENT_VERSION}" "${CONFLUENT_HOME}" \
    && rm /tmp/confluent.zip

RUN wget -q "https://services.gradle.org/distributions/gradle-8.8-bin.zip" -O /tmp/gradle.zip \
    && unzip /tmp/gradle.zip -d /tmp \
    && mv "/tmp/gradle-${GRADLE_VERSION}" "${GRADLE_HOME}" \
    && rm /tmp/gradle.zip
