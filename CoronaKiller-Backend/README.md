# Group18/CoronaKiller - Backend Development

This module consists of the implementations for our server-side development.

# Getting Started

## Build and run:

Development is performed in 2 platforms, namely `dev` and `prod`. `dev` refers to our local environment which we connect to our local database and `prod` refers to the server environment which we connect to the database that is given to us. For the environment changes for our development process we have used `Spring Profiles`. We have defined 2 `application.yml` files (`application-dev.yml`, `application-prod.yml`) under the `resources` folder. And to actually use them, we have specified which active profile we want to use. For below configurations, we specify `prod` profile.

* `git clone http://144.122.71.144:8080/Alper.KOCAMAN/group18.git`
* Open the project with your favourite IDE (We prefer Intellij IDEA).
* First let `maven` import all the dependencies, then run `Main.java` in the `com.coronakiller` package with `spring profile = prod` (you can set this by the "Edit Configurations" tab on the up right corner of the IDE)
* You can now test the application using our Postman Collection or by executing unit tests under `src/java/test`

## More About the Application
Simply put, this API is developed for a game named _Corona Killer_ which aims to serve its `JavaFX` users with its SpringBoot Backend and MYSQL DBMS. Game's aim will be to eliminate as much Coronaviruses as possible (:blush:) throughout the different levels of the game using different types of guns. In the game one can _play_ game, _continue_ his/her last game, _display_ different types of scoreboards and also play the game in _multiplayer_ mode (only for the last level of the game).

##### Currently API provides endpoints for:
*  _Player/User Management_ (`/api/players/*`), which basically keeps the necessary services to apply CRUD operations on the users as well as their `login` and `register` processes,
*  _Game Management_ (`/api/game/*`), which basically constructs main medium that game sessions modifications (newgame, continue, level-up, finish) can be applied through,
*  _ScoreBoard Management_ (`/api/scoreboard`), which provides access to the services that creates requested (can be weekly, monthly or all times) scoreboard on the fly (has only 1 endpoint, different board types are specified through the query strings).

## DB Design
In this project, there are 4 different type of entities.
* Player object is responsible for holding the player and it's attributes,
* Score object is responsible for keeping the score data for each player as
well as it's creation time data,
* GameSession entity holds the necessary level-score-spaceship information 
along with the player knowledge, 
* Also, spaceship entity holds necessary attributes in it.   

Relationships between entities are listed as:
* One player can have multiple scores so there is one-to-many relationship exists,
* One player can have one gameSession so there is one-to-one relationship exists,
* lastly, one gameSession has one spaceShip so there is one-to-one relationship exists.
  
![Entity-Relationship Diagram](img/ERDiagram.png)

### Testing
#### Unit Tests
Unit tests for each RestApiController can be found in `src/test/java/com.coronakiller/ControllerTest`. 
All methods in `src/main/java/com.coronakiller/Conroller` package are tested and 
verified by these tests. Similarly, for each RestApiService has also unit test which can be found in 
`src/test/java/com.coronakiller/ServiceTest`. You can run all tests in IntelliJ IDEA by applying
right click on CoronoKiller-Backend in the project section than select `Run 'All Tests'`
 option.
#### Postman
You can find the corresponding postman collection `CoronaKiller-Backend.postman_collection.json` under the main folder, which has a request collection that covers pretty much all possible requests that can be applied to the API endpoints. Both parent collection and requests have their descriptions. For manual testing of the endpoints using this collection, there is a guide in the collection description.

### Documentation
For API documentation `Swagger2` and javadoc has been used in various parts of the project. One can check `localhost:8080/swagger-ui.html` for swagger Documentation page (it can be accessed anonymously, it will not require Authentication).

## Authors
###### Group 18
* Yavuz Selim YEŞİLYURT - 2259166
* Alper KOCAMAN - 2169589
