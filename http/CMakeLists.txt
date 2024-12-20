# Set the minimum version of CMake required
cmake_minimum_required(VERSION 3.10)

# Define the project name and version
project(http VERSION 1.0)

# Set C++ standard (optional but recommended)
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED True)

#set(CMAKE_FIND_LIBRARY_SUFFIXES .a .so)  # Prefer static libraries (.a) over shared ones (.so)
#set(CMAKE_EXE_LINKER_FLAGS "-static-libgcc -static-libstdc++ -static")  # Static linking flags

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -ffunction-sections -fdata-sections")
set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -Wl,--gc-sections")

add_executable(http main.cpp)

# add_library(http SHARED main.cpp)