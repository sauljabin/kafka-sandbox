FROM peaceiris/mdbook:latest AS builder
WORKDIR /kafka-sandbox
COPY book.toml /kafka-sandbox/book.toml
COPY md /kafka-sandbox/md
RUN mdbook build

FROM nginx
COPY --from=builder /kafka-sandbox/book /usr/share/nginx/html
