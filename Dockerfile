FROM ubuntu:latest

ENV DEBIAN_FRONTEND=noninteractive

#enter bash
#RUN /bin/sh

#intalling git
RUN apt-get update
RUN	apt-get install -y git

#cria pasta pismo
RUN mkdir pismoDocker

RUN cd pismoDocker

RUN git clone https://github.com/rudineybarbosa/transactions.git

# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

RUN java -jar transactions/target/transactions-0.0.1-SNAPSHOT.jar

