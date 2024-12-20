cmake -S .. `
    -G "Ninja" `
    -DCMAKE_TOOLCHAIN_FILE=C:\Users\xfusb\AppData\Local\Android\Sdk\ndk\25.1.8937393\build\cmake\android.toolchain.cmake `
    -DANDROID_USE_LEGACY_TOOLCHAIN_FILE=False `
    -DANDROID_PLATFORM=android-21 `
    -DCMAKE_BUILD_TYPE=Release `
    -DANDROID_STL=c++_static `
    -DANDROID_ARM_NEON=ON `
    -DANDROID_ABI="arm64-v8a" `
    -DCMAKE_INSTALL_PREFIX="install"
    
cmake --build . --config Release

