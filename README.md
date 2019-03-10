# PacMan Game for CSE11: Intro to OOP in Java(accelerated)
* This program is from an assignment of athe first Java class at UCSD, which provided a graphic Pacman game. 
## What does the game look like
![Game Start](/image/GameStart.jpg)
## How to play the game
##### 1. Compile these files
Since the program is written by Javafx, which is a GUI Library in java, you must have pre-installed the library, or it will not compile. I recommand using JDK8, which has already included Javafx.
##### 2. Start a game
User can simply type `java GuiPacman (-s <integer>) (-i inputfile) (-o outputfile)` (order doesn't matter)
to set the size of the board and play the game.

* `-s <integer>`	- used to specify the grid size. If not specified, using default size 10.

* `-i <string>`  - used to specify the file from which you want to load your game progress. If not specified, it will start a new game.

* `-o <string>`  - used to specify the file to which you want to save your game progress. If not specified, it will save as 'Pac-Man.board'.
##### 3. Play the game
* Press `←`, `↑`, `→` , and  `↓` for moving.
* Press `s` if you want to save the game progress.
* Press `Q` to undo.
## Extra features
1. Different pictures for different ghost objects.
2. User can press 'Q' to go back a step, when he regrets his previous moving choice. However,
if he press Q before any movement, the console will print a message and the game continues.
## Skills practiced
* Object-Oriented Programming and UML design.
* Abstraction and Polymorphism
* Inner Class/Abstract Class/Interface
* Gui Application
* etc.
## Author

* **Yuan Gao** - *Initial work* - [cs11wkk](mailto:y1gao@ucsd.edu)


## Acknowledgments

* Thanks my Dedication and TAs/Tutors
