# sent schedule
A desktop application based on the JavaFX platform
To set up a project build, you need to follow these steps:
- In the settings.gradle file.kts in the root folder, you must specify the path to [cmake gradle plugin](https://github.com/GloRRian55/cmake-gradle-plugin) for building CMake projects. So that you can download the plugin project to your computer. (This task is mandatory because I haven't published this plugin anywhere yet)
- Perhaps you should change some settings for the CMake build, because I have not yet configured it for cross-platform build (I have successfully used macOS and Linux, but Windows could not be used).
- You must have or install some dependencies: JDK(I use 17), C++ compiler(I use Clang), CMake (and also specify them in the PATH system variable)

To launch the application, use the following commands:
- ./gradlew clean build (use if you have any changes in the CMake project code)
- ./gradlew run --stacktrace (launch the application)

Example of application charts:
![telegram-cloud-photo-size-2-5359596776107138431-y](https://github.com/user-attachments/assets/84192597-5d2d-48f1-9c83-50f89d18c8aa)

