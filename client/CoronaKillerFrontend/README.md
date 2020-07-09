# Group18/CoronaKiller - Frontend Development

This module consists of the implementations for our client-side development.

# Getting Started

## Build and run:
You first need to run Backend server in your machine. For that consult to its documentation in its [README](https://github.com/ysyesilyurt/CoronaKiller/tree/master/server/CoronaKillerBackend).

To run this application, first `git clone https://github.com/ysyesilyurt/CoronaKiller.git`, then run on:

### Your host machine:
Since project is developed using JDK-13, make sure you have JDK-13 installed in your system.
#### From your favourite terminal:
You can just execute `build.sh` which will build both executables (both backend and frontend) on a folder named `executables` in the root folder. Then you can run the resulting `jar` directly.
```
./build.sh
cd executables
java -jar -Dspring.profiles.active=prod target/CoronaKillerFrontend-Group18.jar
```

#### From your favourite IDE:
* Open the project with your favourite IDE (We prefer Intellij IDEA).
* First let `maven` import all the dependencies, then run `Main.java` in the `com.coronakiller` package 

## More About the Application
Frontend UI Application that is developed with a Spring Boot JavaFX application. Serves as a client for the backend server where players can self-sign, display different types of leaderboards and actually play the game.

## Documentation
### Design
Implementation is done using both `JavaFX` and `SpringBoot` Frameworks collaboratively. We created a `SpringBoot` Application which manages our `JavaFX` application stage in its `ApplicationContext`. As you would see in our GUI Documentation, game consists of 4 singleplayer levels and 1 multiplayer level. Multiplayer level is designed via utilizing network sockets. After a user reaches to the final (multiplayer) level of the game, if there is nobody in the matchmaking queue at the time user gets added to the queue and displayed a *Matchmaking In Progress...* dialog. When another user reaches to the same level of the game, server checks the queue and realizes that there is already someone in the queue, therefore matches them. Matching operation is actually nothing more than retrieval of the `IP` address of the waiting user. After fetching the `IP` of first user, second user establishes a `Peer-to-Peer` socket connection to the first by using the `IP` and predefined port number (10000). Then after initial set-up all the communication continues by utilizing this `p2p` socket connection. 

### GUI Documentation
You can check the provided [GUI Documentation](https://github.com/ysyesilyurt/CoronaKiller/blob/master/client/CoronaKillerFrontend/CoronaKiller_GUI_Documentation.pdf) pdf file in the parent folder to learn more about the game, its rules, developer notes and how to actually play it.

### Comment Style Documentation
For client documentation `javadoc` is utilized whenever possible. For API documentation both `Swagger2` and `javadoc` has been utilized in various parts of the project. One can check `localhost:8080/swagger-ui.html` for swagger Documentation page (it can be accessed anonymously, it will not require Authentication).

## Testing
### UI Test Cases
Consult to the provided [Test Cases](https://github.com/ysyesilyurt/CoronaKiller/blob/master/client/CoronaKillerFrontend/CoronaKiller_Frontend_Test_Cases.pdf) pdf file in the parent folder.

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
