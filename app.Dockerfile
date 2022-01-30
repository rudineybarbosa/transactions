FROM alpine:latest as builder

#evitar interações durante instalação 
ENV DEBIAN_FRONTEND=noninteractive

#instalando git
RUN apk update && \
	apk add git

#instalando OpenJDK-8
RUN apk update && \
    apk add openjdk8-jre && \
    apk add apache-ant

# Setup JAVA_HOME -- util para docker command line
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

#installando mysql-client
RUN apk add mysql-client

#cria pasta pismo
#RUN mkdir pismoDocker

#RUN cd pismoDocker

#tentativa de reduzir o tamanho da imagem
FROM alpine
COPY --from=builder . .

#o comando abaixo apenas faz o donwload de um arquivo qualquer para que o hash final seja alterado e, consequentemente, o git faça o clone novamente ao inves de usar o cache
ADD "https://www.random.org/cgi-bin/randbyte?nbytes=10&format=h" skipcache

RUN git clone https://github.com/rudineybarbosa/transactions.git

WORKDIR "/transactions"

ENV spring.datasource.username=root
ENV spring.datasource.password=root

#Utilizando o RUN a isntrução é executada durante a criação da imagem (docker build). É errado. O que quero é executar o comando durante a criação do container. Para isso, o correto é utilizar CMD, pois o comando CMD será executado durante a construção do container (docker run)
#RUN java -jar transactions-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","transactions-0.0.1-SNAPSHOT.jar"]

