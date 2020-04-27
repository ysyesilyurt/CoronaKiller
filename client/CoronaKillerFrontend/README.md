# Group18/CoronaKiller - Frontend Development

This module consists of the implementations for our client-side development.

# Getting Started

## Build and run:
You first need to run Backend server in your machine. For that consult to its README.

First `git clone http://144.122.71.144:8080/Alper.KOCAMAN/group18.git`, then run on:

### Your host machine:
Since project is developed using JDK-13, make sure you have JDK-13 installed in your system.
#### From your favourite terminal:
```
mvn clean install
java -jar -Dspring.profiles.active=prod target/ui-0.0.1-SNAPSHOT.jar
```

#### From your favourite IDE:
* Open the project with your favourite IDE (We prefer Intellij IDEA).
* First let `maven` import all the dependencies, then run `Main.java` in the `com.coronakiller` package 


## More About the Application
Frontend UI Application that is developed with a Spring Boot JavaFX application. Serves as a client for the backend server.

## Documentation
### GUI Documentation
Consult to the provided GUI Documentation pdf file in the parent folder.

### Comment Style Documentation
For client documentation `javadoc` is utilized whenever possible. For API documentation both `Swagger2` and `javadoc` has been utilized in various parts of the project. One can check `localhost:8080/swagger-ui.html` for swagger Documentation page (it can be accessed anonymously, it will not require Authentication).

## Testing
### UI Tests
Consult to the provided Test Cases pdf file in the parent folder.

### Unit Tests
Unit tests for each RestApiController can be found in `src/test/java/com.coronakiller/ControllerTest`. 
All methods in `src/main/java/com.coronakiller/Conroller` package are tested and 
verified by these tests. Similarly, for each RestApiService has also unit test which can be found in 
`src/test/java/com.coronakiller/ServiceTest`. You can run all tests in IntelliJ IDEA by applying
right click on CoronoKiller-Backend in the project section than select `Run 'All Tests'`
 option.
#### Postman
You can find the corresponding postman collection `CoronaKiller-Backend.postman_collection.json` under the main folder, which has a request collection that covers pretty much all possible requests that can be applied to the API endpoints. Both parent collection and requests have their descriptions. For manual testing of the endpoints using this collection, there is a guide in the collection description.

### Documentation
For client documentation `javadoc` is utilized whenever possible. For API documentation `Swagger2` and javadoc has been used in various parts of the project. One can check `localhost:8080/swagger-ui.html` for swagger Documentation page (it can be accessed anonymously, it will not require Authentication).

## Authors
###### Group 18
* Yavuz Selim YEŞİLYURT - 2259166
* Alper KOCAMAN - 2169589
