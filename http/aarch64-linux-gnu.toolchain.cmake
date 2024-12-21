# aarch64-linux-gnu.toolchain.cmake
set(CMAKE_C_COMPILER "aarch64-linux-gnu-gcc")
set(CMAKE_CXX_COMPILER "aarch64-linux-gnu-g++")
set(CMAKE_SYSROOT "/home/xf/ubuntu-docker-osrm")

set(PYTHON_SOABI cpython-310-aarch64-linux-gnu)
set(PythonExtra_EXTENSION_SUFFIX cpython-310-aarch64-linux-gnu)    

set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)