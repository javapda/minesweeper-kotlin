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
* [discord markdown live previewer](https://discord-markdown-live-previewer.vercel.app/)

## Stage 5/5 : 
## Stage 4/5 : 
## Stage 3/5 : [Look around you](https://hyperskill.org/projects/8/stages/48/implement)
### Description
The player needs hints to be able to win, and we want them to have a chance to win! Let's show the number of mines around the empty cells so that our players have something to work with.

### Objectives
As in the previous step, you need to initialize the field with mines. Then, calculate how many mines there are around each empty cell. Check 8 cells if the current cell is in the middle of the field, 5 cells if it's on the side, and 3 cells if it's in the corner.

If there are mines around the cell, display the number of mines (from 1 to 8) instead of the symbol representing an empty cell. The symbols for empty cells and mines stay the same.

Check all the possibilities carefully.

### Examples
The greater-than symbol followed by a space (**> **) represents the user input. Note that it's not part of the input.

#### Example 1:
```
How many mines do you want on the field? > 10
.........
.111111..
.1X22X211
.112X33X1
...12X211
....1221.
..1111X1.
123X1222.
1XX211X1.
```
#### Example 2:
```
How many mines do you want on the field? > 15
1221.....
2XX21....
X34X2..11
112X2..2X
11211..3X
1X1....2X
12321..11
12XX11232
X22211XXX
```
#### Example 3:
```
How many mines do you want on the field? > 20
.2X3X23XX
13X43X3X3
1X3X32211
2232X1...
2X2221...
X32X1..11
X32331.1X
X21XX2.22
1113X2.1X
```
### Solve in IDE errors
```courseignore
Wrong answer in test #24

In this cell should be number 2, but found symbol "1".
In line 9, symbol 9.

Please find below the output of your program during this failed test.
Note that the '>' character indicates the beginning of the input line.

---

How many mines do you want on the field? > > 12
2X2......
3X2......
X21......
1211..122
12X2111XX
X213X2133
11.2X212X
...1112X3
......2X1

java.lang.AssertionError: Wrong answer in test #24

In this cell should be number 2, but found symbol "1".
In line 9, symbol 9.
```

## Stage 2/5 : [Flexible mines](https://hyperskill.org/projects/8/stages/47/implement)
### Description
It's no fun when the field has the same setup every time and you know where all the mines are located. Let's generate a random configuration every time the player wants to play the game.

To improve the program, we need to let the player choose how many mines they want on the field. The player needs to input the number of mines they want with their keyboard.

### Objectives
Your program should ask the player to define the number of mines to add to a 9x9 field with the message 
**"How many mines do you want on the field?"**. It should then use the input to initialize the field and display it with the mines. At this point, the mines are still visible to the player; you will hide them later.

Make sure to use the following marking symbols:

* **X** for mines
* **.** for safe cells
### Examples
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

#### Example 1:
```
How many mines do you want on the field? > 10
........X
........X
......X.X
X........
.........
......X..
XX......X
.........
.....X...
```
#### Example 2:
```
How many mines do you want on the field? > 10
.........
.X.......
...X...XX
X........
.X.......
.........
.........
X......X.
...X....X
```
#### Example 3:
```
How many mines do you want on the field? > 20
.X..XX...
.....XX.X
....XX...
....XX.XX
.X......X
.....X...
..X..XX..
.........
.X.....X.
```
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
