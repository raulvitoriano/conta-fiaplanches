FROM postgres:14.3
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin123
ENV POSTGRES_DB=fiap-lanches-client
COPY *.sql /docker-entrypoint-initdb.d/
