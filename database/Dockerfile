FROM postgres:14.11-alpine

ENV POSTGRES_USER devsu
ENV POSTGRES_PASSWORD devsu
ENV POSTGRES_DB devsujava

COPY BaseDatos.sql /docker-entrypoint-initdb.d/

RUN chmod 755 /docker-entrypoint-initdb.d/BaseDatos.sql
