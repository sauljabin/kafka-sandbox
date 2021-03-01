FROM openjdk:8-jre

ARG SCALA_VERSION
ARG KAFKA_VERSION

ENV KAFKA_HOME /kafka
ENV KAFKA_BIN $KAFKA_HOME/bin
ENV KAFKA_CONFIG $KAFKA_HOME/config
ENV PATH $PATH:$KAFKA_BIN

RUN mkdir $KAFKA_HOME && \
    cd $KAFKA_HOME && \
    wget -q "https://archive.apache.org/dist/kafka/$KAFKA_VERSION/kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz" -O /tmp/kafka.tgz && \
    tar xfz /tmp/kafka.tgz --strip-components 1 && \
    rm /tmp/kafka.tgz

COPY docker-entrypoint.sh $KAFKA_BIN
COPY server.properties $KAFKA_CONFIG

RUN cd $KAFKA_BIN && /bin/bash -c 'find . -name "*.sh" | while read f; do chmod +x $f ; ln -s $f ${f:2:-3} ; done'

WORKDIR $KAFKA_BIN
ENTRYPOINT ["docker-entrypoint"]
CMD ["kafka-server-start", "/kafka/config/server.properties"]
