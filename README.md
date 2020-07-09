# CoronaKiller 🦠
A complex (!) structured online and multiplayer 2D virus shooting game which lets you find your way up to the harsh levels and finally defeat the CoronaVirus King :blush:

## Get Started...
The game has a backend application, which has been deployed in http://144.122.71.144:8083/coronakillerbackend-group18/api on-prem server. Application is a SpringBoot application which covers frontend application's requirements since this is an online game. The deployed application may no longer be active (since this was a term project for an Undergraduate course in METU-CENG) therefore to test the application you would also need to build and run CoronaKillerBackend application.

The game also has a frontend application (as all you would guess :blush:) implemented as a JavaFX Application and managed within SpringBoot's Application Context (namely JavaFX + SpringBoot).

To actually build, run and start experiencing killing CoronaViruses please first consult to [backend documentation](https://github.com/ysyesilyurt/CoronaKiller/tree/master/server/CoronaKillerBackend) then consult to [frontend documentation](https://github.com/ysyesilyurt/CoronaKiller/tree/master/client/CoronaKillerFrontend).

## ... Killing Corona?
Game's aim will be to eliminate as much Coronaviruses as possible (:blush:) throughout the different levels of the game using different types of guns. In the game one can play game, continue his/her last game, display different types of scoreboards and also play the game in multiplayer mode (only for the last level of the game).

## Tech Background
Keywords: `Spring Boot Data MVC Security`, `JavaFX`, `JFoenix`, `Docker`, `Swagger2`, `JUnit`, `P2P`

### Backend
Infrastructure implemented using `Spring Boot Data MVC` frameworks, its also secured using `Spring Security`. For the testing purposes of backend `JUnit` and `Postman` has been utilized. `Swagger2` and `javadoc` applied for the documentation. Also application is containerized using `Docker`.

### Frontend
Client side is implemented as a desktop application using `JavaFX` in collaboration with `SpringBoot` (namely, managed using `SpringBoot`s `ApplicationContext`). `JFoenix` has been used for the styling of the application. UI test cases document and GUI Documentation pdf files has been created for detailed insight of frontend application. For the request chains `OkHttp` has been utilized. For the multiplayer part of the game a `peer2peer` architecture is implemented in collaboration with backend. Briefly, a matchmaking operation happens on the server-side and then players (namely `peers`) connect to each other and all the game flow passes between them via `TCP sockets` (For a more detailed explanation about this architecture please consult to [here](https://github.com/ysyesilyurt/CoronaKiller/tree/master/client/CoronaKillerFrontend#design))   

## Teaser - Sample Build + Run + Play
[![CoronaKiller Video](https://img.youtube.com/vi/PCt-ORYCpS8/0.jpg)](https://www.youtube.com/watch?v=PCt-ORYCpS8)
