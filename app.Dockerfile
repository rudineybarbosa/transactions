FROM ubuntu:latest as builder

#evitar interações durante instalação 
ENV DEBIAN_FRONTEND=noninteractive

#instalando git
RUN apt-get update
RUN	apt-get install -y git

#instalando OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

#installando mysql-client
RUN apt-get install -y mysql-client

#cria pasta pismo
#RUN mkdir pismoDocker

#RUN cd pismoDocker

#tentativa de reduzir o tamanho da imagem
FROM alpine
COPY --from=builder . .

#o comando abaixo apenas faz o donwload de um arquivo qualquer para que o hash final seja alterado e, como consequentemente, o git faça o clone novamente ao inves de usar o cache
#ADD "https://www.random.org/cgi-bin/randbyte?nbytes=10&format=h" skipcache

RUN git clone https://github.com/rudineybarbosa/transactions.git

WORKDIR "/transactions"

ENV spring.datasource.username=root
ENV spring.datasource.password=root

#Utilizando o RUN, esta linha é executada durante a criação da imagem (docker build). Porém, o correto é utillizar CMD, pois esta linha deve ser executada durante a construção do container (docker run)
#RUN java -jar transactions-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","transactions-0.0.1-SNAPSHOT.jar"]

