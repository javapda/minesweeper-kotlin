# [minesweeper-kotlin](https://hyperskill.org/projects/8)
* based on the Hyperskill [Minesweeper (Kotlin)](https://hyperskill.org/projects/8) project
* project [minesweeper-kotlin](https://github.com/javapda/minesweeper-kotlin) on github

## setup build.gradle.kts (Kotlin form of gradle build script)
```
plugins {
    // other plugins ...
    application
}
val mainKotlinClass = "com.javapda.minesweeper.MinesweeperKt"
application {
    mainClass = mainKotlinClass
}

```
## build and run
```
.\gradlew.bat clean build 
java -jar .\build\libs\minesweeper-kotlin-0.0.1-SNAPSHOT-all.jar
# - or - 
#  java -cp  .\build\libs\minesweeper-kotlin-0.0.1-SNAPSHOT-all.jar  com.javapda.minesweeper.MinesweeperKt
# - or - 
#  .\gradlew.bat clean build :run
# - or - 
#  .\gradlew.bat :run
```
## Hyperskill
* [my profile](https://hyperskill.org/profile/615178637)
* [Troubleshooting: no tests have run](https://plugins.jetbrains.com/plugin/10081-jetbrains-academy/docs/troubleshooting-guide.html#no_tests_have_run)

## Stage 5/5 : 
## Stage 4/5 : 
## Stage 3/5 : 
## Stage 2/5 : 
## Stage 1/5 : [Lay the groundwork](https://hyperskill.org/projects/8/stages/46/implement)
### Description
Minesweeper is a game of logic where the player is presented with a field full of hidden mines. The goal is to mark the positions of all mines without setting any of them off. It's not a game of wild guessing: it offers hints showing the number of mines around each cell. One wrong move, and game over!

### Objective
Your first step is easy: you need to output some state of the minefield.

Set the minefield size and place any number of mines you want on it. At this point, all the mines are there in plain sight – we are not going to hide them from the player just yet.

You can use any character you want to represent mines and safe cells at this step. Later on, we will use X for mines and . for safe cells.

### Example
In this example, there are 10 mines on a 9x9 field. Feel free to use your own marking symbols and field configuration!
```
.X.......
.....X..X
....X....
......X..
..X......
....X....
..X......
..X......
......X..
```
