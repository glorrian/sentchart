# sent-chart
The desktop application based on JavaFX platform
For setup build of the project you should do this points:
- In the file settings.gradle.kts at root fold you should insert path to the [cmake gradle plugin](https://github.com/GloRRian55/cmake-gradle-plugin) for CMake projects assembling. So you can download project of plugin at your PC. (This task is required because I don't publish this plugin anywhere yet)
- Maybe you should change some setting for the CMake assembling, because I don't setup this for cross-platform build yet(I succesfully used MacOS and Linux but Windows usage has been failed).
- You should have or install some dependencies: JDK(I use 17), C++ compiler(I use Clang), CMake(and also have it in your system PATH variable)

For running application use this commands:
- ./gradlew clean build ( use if your have any change in CMake project code)
- ./gradlew run --stacktrace ( run application)

Good luck! (and apologize me for my bad english grammar)
