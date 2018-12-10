# tix-time-client
TiX Time Client is the application that runs on the client. Its task is to send packages to the server and report the full statistics from it.

### Prerequisites

In order to compile and run the tix time client, the following dependencies are required:
* Java 8
* Gradle 3

### Installing

In order to be able to compile the client, the file [Application.yml](tix-time-client-cli/src/main/resources/Application.yml) needs to be configured accordingly.

```yaml
clientRepositoryUrl: 'client-repository-url'
deployTargetUrl:  'deploy-target-url'
webApiUrl:  'web-api-url'
serverIp: 'server-ip'
serverPort: 1234
clientPort: 1234
```

If you want to run the GUI mode locally, you will need to mount a local server to roleplay the client repository. Therefore, you should change the `clientRepositoryUrl` value in Application.yml to a local url.

After configuring the [Application.yml](tix-time-client-cli/src/main/resources/Application.yml), the only step left is to generate the executable jar files.

##### GUI mode

```
gradle :tix-time-client-gui:embedApplicationManifest 
```
After executing this command, the jars will be located in `tix-time-client-gui/build/fxlauncher/`

##### CLI mode
```
gradle :tix-time-client-cli:jar 
```
After executing this command, the jars will be located in `tix-time-client-cli/build/libs/`

### Run

There are three ways of running the client:

* GUI mode by running `java -jar tix-time-client-gui/build/fxlauncher/fxlauncher.jar`
* CLI mode by running `java -jar tix-time-client-cli/build/libs/tix-time-client.jar username password installation port logs_directory`
* GUI mode by using the native installer and running the application

When running the CLI mode locally and make changes without deploying, a local repository is needed to bypass the automatic update from the client. Therefore it is recommended to follow these steps:

```
cd tix-time-client-gui/build/fxlauncher/
python3 -m http.server 
```

This script will mount a local server in http://localhost:8000 to roleplay the client repository. Before compiling in GUI mode, you should change the `clientRepositoryUrl` to this local url.

### Generating native installer

To create a native installer for the GUI mode, clone the repository into a computer/VM with the desired native platform (e.g. get a Macbook to generate native OSX installer) and execute: 

```
gradle :tix-time-client-gui:generateNativeInstaller
```
The installer will be located in `build/installer/bundles/`

### Deploying the client 

```
gradle :tix-time-client-gui:deployApp
```

The deploy will simply copy all the files in `tix-time-client-gui/build/fxlauncher/` to the directory specified in the `deployTargetUrl` in the[Application.yml](tix-time-client-cli/src/main/resources/Application.yml)