FROM openjdk:latest
WORKDIR /app
COPY . /app
RUN javac CPUScheduling.java
CMD ["java" , "CPUScheduling"]