ARG JAVA_VERSION="17"
FROM amazoncorretto:${JAVA_VERSION}
RUN yum update -y && yum install -y git openssh-clients tar
COPY . /workspace
WORKDIR /workspace