FROM openjdk:8-jre

ARG SCALA_VERSION
ARG KAFKA_VERSION

ENV KAFKA_HOME /kafka
ENV KAFKA_BIN $KAFKA_HOME/bin
ENV PATH $PATH:$KAFKA_BIN

WORKDIR $KAFKA_HOME

RUN wget -q "https://archive.apache.org/dist/kafka/$KAFKA_VERSION/kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz" -O /tmp/kafka.tgz && \
    tar xfz /tmp/kafka.tgz --strip-components 1 && \
    rm /tmp/kafka.tgz

COPY docker-entrypoint.sh $KAFKA_HOME
COPY server.properties $KAFKA_HOME/config

RUN ln -s $KAFKA_BIN/kafka-server-start.sh $KAFKA_BIN/kafka && \
    chmod +x $KAFKA_BIN/kafka-server-start.sh && \
    chmod +x $KAFKA_HOME/docker-entrypoint.sh

ENTRYPOINT ["./docker-entrypoint.sh"]
CMD ["kafka", "config/server.properties"]
