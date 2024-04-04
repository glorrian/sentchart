cmake_minimum_required(VERSION 3.5)

project(${PROJECT_NAME} LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(GSL)
find_package(Java 1.8 REQUIRED COMPONENTS Development)

string(REGEX REPLACE "/bin/java$" "" JAVA_HOME ${Java_JAVA_EXECUTABLE})

set(JAVA_INCLUDE_PATH "${JAVA_HOME}/include")
message("regasd${JAVA_INCLUDE_PATH}")
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${LIBRARY_PATH})
add_library(${PROJECT_NAME} SHARED fourier.cpp)
target_link_libraries(${PROJECT_NAME} PUBLIC GSL::gsl)

if (CMAKE_HOST_WIN32)
    set(JAVA_INCLUDE_PATH2 "${JAVA_INCLUDE_PATH}/win32")
elseif(CMAKE_HOST_APPLE)
    set(JAVA_INCLUDE_PATH2 "${JAVA_INCLUDE_PATH}/darwin")
elseif(CMAKE_HOST_UNIX)
    set(JAVA_INCLUDE_PATH2 "${JAVA_INCLUDE_PATH}/linux")
endif()

target_include_directories(${PROJECT_NAME} PUBLIC ${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2})


