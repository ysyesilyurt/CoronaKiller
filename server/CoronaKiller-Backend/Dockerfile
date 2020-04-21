# Start with a base image containing Java runtime
FROM openjdk13:jdk-13.0.2_8

# Add Maintainer Info
LABEL maintainer="yesilyurt.selim@metu.edu.tr"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/CoronaKiller-Backend-1.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} coronakiller-backend.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/potential-playlist.jar"]

#######
# sudo docker build -t coronakiller-backend .
# sudo docker run -e"SPRING_PROFILES_ACTIVE=dev" --network="host" -d --rm --name coronakiller-backend coronakiller-backend
#######