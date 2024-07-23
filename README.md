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

## Stage 5/5 : [Battle!](https://hyperskill.org/projects/8/stages/469/implement)
### Description
In this stage, you will upgrade your program to act just like the original Minesweeper game! We won't show all the hints from the beginning anymore, but we will allow the player to explore the minefield by themselves, which is much more challenging and fun.

The game starts with an unexplored minefield that has a user-defined number of mines.

The player can:

* Mark unexplored cells as cells that potentially have a mine, and also remove those marks. Any empty cell can be marked, not just the cells that contain a mine. The mark is removed by marking the previously marked cell.

* Explore a cell if they think it does not contain a mine.

There are three possibilities after exploring a cell:

1. If the cell is empty and has no mines around, all the cells around it, including the marked ones, can be explored, and it should be done automatically. Also, if next to the explored cell there is another empty one with no mines around, all the cells around it should be explored as well, and so on, until no more can be explored automatically.

2. If a cell is empty and has mines around it, only that cell is explored, revealing a number of mines around it.

3. If the explored cell contains a mine, the game ends and the player loses.

There are two possible ways to win:

1. Marking all the cells that have mines correctly.

2. Opening all the safe cells so that only those with unexplored mines are left.

### Objectives
In this final stage, your program should contain the following additional functionality:

1. Print the current state of the minefield starting with all unexplored cells at the beginning, 
ask the player for their next move with the message “**Set/unset mine marks or claim a cell as free:**”, 
treat the player's move according to the rules, and print the new minefield state. Ask for the player's 
next move until the player wins or steps on a mine. The player's input contains a pair of cell 
coordinates and a command: **mine** to mark or unmark a cell, **free** to explore a cell.

2. If the player explores a mine, print the field in its current state, with mines shown as **X** symbols. After that, output the message “*You stepped on a mine and failed!*”.

3. Generate mines like in the original game: the first cell explored with the **free** command cannot be a mine; it should always be empty. You can achieve this in many ways – it's up to you.

Use the following symbols to represent each cell’s state:

* **.** as unexplored cells

* **/** as explored free cells without mines around it

* Numbers from 1 to 8 as explored free cells with 1 to 8 mines around them, respectively

* **X** as mines

* _*_ as unexplored marked cells

### Examples
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

#### Example 1: the user loses after exploring a cell that contains a mine
```
How many mines do you want on the field? > 10

│123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│.........│
5│.........│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 3 2 free

│123456789│
—│—————————│
1│.1///1...│
2│.1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 1 free

│123456789│
—│—————————│
1│11///1...│
2│.1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 2 mine

│123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 8 free

│123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.......1.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 8 free

│123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│......11.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 6 8 free

│123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.....211.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 2 7 free

│123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.3.......│
8│.....211.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 5 6 free

│123456789│
—│—————————│
1│11///1X..│
2│X1//12...│
3│11//1X...│
4│////1....│
5│11111....│
6│.X..X....│
7│.3X...X..│
8│.X..X211.│
9│...X.....│
—│—————————│
You stepped on a mine and failed!

#### Example 2: the user wins by marking all mines correctly

How many mines do you want on the field? > 8

│123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│.........│
5│.........│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 5 5 free

│123456789│
—│—————————│
1│..1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23.1//111│
8│..21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 2 1 mine

│123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23.1//111│
8│..21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 3 7 mine

│123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│..21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 2 8 mine

│123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│.*21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 8 mine

│123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 3 mine

│123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1*..│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 3 free

│123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1*1.│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 9 3 free

│123456789│
—│—————————│
1│.*1//1...│
2│111//2.31│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 6 mine

│123456789│
—│—————————│
1│.*1//1...│
2│111//2.31│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 2 free

│123456789│
—│—————————│
1│.*1//1...│
2│111//2231│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 1 mine

│123456789│
—│—————————│
1│.*1//1*..│
2│111//2231│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 9 1 mine

│123456789│
—│—————————│
1│.*1//1*.*│
2│111//2231│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Congratulations! You found all the mines!
```
#### Example 3: the user wins by exploring all safe cells
```
How many mines do you want on the field? > 5

│123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│.........│
5│.........│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 5 5 free

│123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│..1//1.21│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 9 free

│123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│..1//1.21│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 4 free

│123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1.21│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 4 free

│123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1121│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 5 free

│123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1121│
5│111//11..│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 5 free

│123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1121│
5│111//112.│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Congratulations! You found all the mines!
```
### Issues when checking Stage 5 on Hyperskill Battle!
#### The solution:
* when performing the flood fill operation, check to see if a cell/location was already
marked by user, manually, as a mine. According to the comments, you should automatically
remove the faulty mine marker - so when the field is re-rendered it will show either a 
number (1-8) or the '/' character, indicating it is a safe free cell.
#### The problem is shown below:
```
java.lang.AssertionError: Wrong answer in test #4

The last grid contains '*' and '/' characters that are next to each other. This situation is impossible. If there is '*' character that is next to '/' it should be replaced to '/' or to a number.

Please find below the output of your program during this failed test.
Note that the '>' character indicates the beginning of the input line.

---

How many mines do you want on the field? > > 1
 |123456789|
-|---------|
1|.........|
2|.........|
3|.........|
4|.........|
5|.........|
6|.........|
7|.........|
8|.........|
9|.........|
-|---------|
Set/unset mines marks or claim a cell as free: > > 7 8 mine
 |123456789|
-|---------|
1|.........|
2|.........|
3|.........|
4|.........|
5|.........|
6|.........|
7|.........|
8|......*..|
9|.........|
-|---------|
Set/unset mines marks or claim a cell as free: > > 6 2 mine
 |123456789|
-|---------|
1|.........|
2|.....*...|
3|.........|
4|.........|
5|.........|
6|.........|
7|.........|
8|......*..|
9|.........|
-|---------|
Set/unset mines marks or claim a cell as free: > > 8 8 mine
 |123456789|
-|---------|
1|.........|
2|.....*...|
3|.........|
4|.........|
5|.........|
6|.........|
7|.........|
8|......**.|
9|.........|
-|---------|
Set/unset mines marks or claim a cell as free: > > 4 3 free
 |123456789|
-|---------|
1|/////////|
2|/////*///|
3|/////////|
4|/////////|
5|/////////|
6|/////////|
7|/////////|
8|/////1**/|
9|/////1.1/|
-|---------|
Set/unset mines marks or claim a cell as free: >
	at org.junit.Assert.fail(Assert.java:89)
	at org.hyperskill.hstest.stage.StageTest.start(StageTest.java:203)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)
	at org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)
	at org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:413)
	at org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.runTestClass(JUnitTestClassExecutor.java:110)
	at org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.execute(JUnitTestClassExecutor.java:58)
	at org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.execute(JUnitTestClassExecutor.java:38)
	at org.gradle.api.internal.tasks.testing.junit.AbstractJUnitTestClassProcessor.processTestClass(AbstractJUnitTestClassProcessor.java:62)
	at org.gradle.api.internal.tasks.testing.SuiteTestClassProcessor.processTestClass(SuiteTestClassProcessor.java:51)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
	at org.gradle.internal.dispatch.ContextClassLoaderDispatch.dispatch(ContextClassLoaderDispatch.java:33)
	at org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke(ProxyDispatchAdapter.java:94)
	at jdk.proxy1/jdk.proxy1.$Proxy2.processTestClass(Unknown Source)
	at org.gradle.api.internal.tasks.testing.worker.TestWorker$2.run(TestWorker.java:176)
	at org.gradle.api.internal.tasks.testing.worker.TestWorker.executeAndMaintainThreadName(TestWorker.java:129)
	at org.gradle.api.internal.tasks.testing.worker.TestWorker.execute(TestWorker.java:100)
	at org.gradle.api.internal.tasks.testing.worker.TestWorker.execute(TestWorker.java:60)
	at org.gradle.process.internal.worker.child.ActionExecutionWorker.execute(ActionExecutionWorker.java:56)
	at org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call(SystemApplicationClassLoaderWorker.java:133)
	at org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call(SystemApplicationClassLoaderWorker.java:71)
	at worker.org.gradle.process.internal.worker.GradleWorkerMain.run(GradleWorkerMain.java:69)
	at worker.org.gradle.process.internal.worker.GradleWorkerMain.main(GradleWorkerMain.java:74)

```

## Stage 4/5 : [Prepare for battle](https://hyperskill.org/projects/8/stages/49/implement)
### Description
We managed to create the minefield and fill it with clues: now it's time to play! Let's give our player the opportunity to guess where the mines are with the help of our hints.

All the numbers are still shown to the player, but now the mines are not. To win, the player must find all the mines on the field by marking them.

Update the field input and add the coordinate grid like in the examples so that the player can mark cells by entering their coordinates.

### Objectives
Your upgraded program should meet the following requirements:

1. After initializing the field, all the numbers are shown to the player, but not the positions of the mines.
2. The player sees the message “**Set/delete mine marks (x and y coordinates):**” and enters two numbers as coordinates on the field.
3. The user input is treated according to the rules:
   * If the player enters the coordinates of a non-marked cell, the program marks the cell, which means that the player thinks a mine is located there.
   * If the player enters the coordinates of a cell with a number, the program should print the message “**There is a number here!**” and ask the player again without printing the minefield, since cells with numbers are guaranteed to be free of mines.
   * If the player enters the coordinates of a marked cell, the cell becomes unmarked. This is necessary because the game ends only if all the marks are correct, but the player can mark more cells than there are mines.
4. After successfully marking or unmarking a cell, the new minefield state is printed. The symbol **.** is used to represent non-marked cells, and __*__ is for marked ones. The prompt for the player's next move is printed until the game is finished.
5. When the player marks all the mines correctly without marking any empty cells, they win and the game ends. If the player has marked extra cells that do not contain mines, they continue playing. After clearing all the excess mine marks, the player wins. Print the message “**Congratulations! You found all the mines!**” after the final field state.

### Examples
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

#### Example 1: the user enters the coordinates of a cell that contains a number
```
How many mines do you want on the field? > 5

│123456789│
—│—————————│
1│.111.....│
2│.1.1.....│
3│.1221....│
4│..1.1....│
5│.1221....│
6│.1.21....│
7│.12.1....│
8│..1221...│
9│...1.1...│
—│—————————│
Set/delete mines marks (x and y coordinates): > 2 1
There is a number here!
Set/delete mines marks (x and y coordinates): > 3 2

│123456789│
—│—————————│
1│.111.....│
2│.1*1.....│
3│.1221....│
4│..1.1....│
5│.1221....│
6│.1.21....│
7│.12.1....│
8│..1221...│
9│...1.1...│
—│—————————│
Set/delete mines marks (x and y coordinates): > 4 4

│123456789│
—│—————————│
1│.111.....│
2│.1*1.....│
3│.1221....│
4│..1*1....│
5│.1221....│
6│.1.21....│
7│.12.1....│
8│..1221...│
9│...1.1...│
—│—————————│
Set/delete mines marks (x and y coordinates): > 3 6

│123456789│
—│—————————│
1│.111.....│
2│.1*1.....│
3│.1221....│
4│..1*1....│
5│.1221....│
6│.1*21....│
7│.12.1....│
8│..1221...│
9│...1.1...│
—│—————————│
Set/delete mines marks (x and y coordinates): > 4 7

│123456789│
—│—————————│
1│.111.....│
2│.1*1.....│
3│.1221....│
4│..1*1....│
5│.1221....│
6│.1*21....│
7│.12*1....│
8│..1221...│
9│...1.1...│
—│—————————│
Set/delete mines marks (x and y coordinates): > 5 9

│123456789│
—│—————————│
1│.111.....│
2│.1*1.....│
3│.1221....│
4│..1*1....│
5│.1221....│
6│.1*21....│
7│.12*1....│
8│..1221...│
9│...1*1...│
—│—————————│
Congratulations! You found all the mines!
```
#### Example 2: the user wins after removing excessive mine marks
```
How many mines do you want on the field? > 1

│123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│....111..│
5│....1.1..│
6│....111..│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/delete mines marks (x and y coordinates): > 1 1

│123456789│
—│—————————│
1│*........│
2│.........│
3│.........│
4│....111..│
5│....1.1..│
6│....111..│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/delete mines marks (x and y coordinates): > 6 5

│123456789│
—│—————————│
1│*........│
2│.........│
3│.........│
4│....111..│
5│....1*1..│
6│....111..│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/delete mines marks (x and y coordinates): > 1 1

│123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│....111..│
5│....1*1..│
6│....111..│
7│.........│
8│.........│
9│.........│
—│—————————│
Congratulations! You found all the mines!
```
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
