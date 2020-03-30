# Ceng453 Term Project - Server Development

This module consists of the implementations for our server-side development.

## Getting Started

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

###  Useful Tools
######  Postman

### Development Tools
###### Intellij IDE

## Build With

* This project was built with Maven (pom.xml) Dependency management.
* You can import the project by using pom.xml file easily. 

## Authors
###### Group 18
###### * Yavuz Selim YEŞİLYURT - 2259166
###### * Alper KOCAMAN - 2169589
