FROM mysql:8.0.28
RUN apt-get update -y
COPY my.cnf /etc/mysql/conf.d/
COPY ./sql/test_table.sql /docker-entrypoint-initdb.d/