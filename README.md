# tix-time-client
TiX Time Client is the application that runs on the client. Its task is to send packages to the server and report the
full statistics from it.

## How to run the client:
* GUI mode by using the native installer and running the application
* GUI mode by running `java -jar fxlauncher.jar` (from command line)
* CLI mode by using `java -jar tix-time-client.jar username password installation port logs_dir` (from command line)

### Prerequisites

* Java 8
* Gradle 3

### How to generate executable jar file (GUI):
Clone this repository and run
```
gradle embedApplicationManifest and look for fxlauncher.jar under build/fxlauncher/ dir
```

### How to generate executable jar file (CLI):
Clone this repository and run
```
gradle :Model:jar and look for tix-time-client.jar under Model/build/libs/ dir
```

### How to generate native installer:
Clone this repository into a computer/VM with the desired native platform (e.g. get a Macbook to generate native OSX installer) and run 
```
gradle generateNativeInstaller and look for the installer file under build/installer/bundles/ dir
```

## How to push client code changes or dependency changes to new+existing users:
Clone this repository and run 
```
gradle deployApp
```
Or simply use SCP to copy all files under `PROJECT_DIR/build/fxlauncher/` directory to server assets directory.
