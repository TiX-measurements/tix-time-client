# tix-time-client
TiX Time Client is the application that runs on the client. Its task is to send packages to the server and report the full statistics from it.

## Prerequisites

In order to compile and run the tix time client, the following dependencies are required:
* Java 8
* Gradle 3.3

## Installing

In order to be able to compile the client, the file [Application.yml](tix-time-client-cli/src/main/resources/Application.yml) needs to be configured accordingly.

```yaml
clientRepositoryUrl: '<client-repository-url>'
deployTargetUrl:  '<deploy-target-url>'

webApiUrl:  '<web-api-url>'

serverIp: '<server-ip>'
serverPort: <server-port>
clientPort: <client-port>
```

If you only want to run the client locally without deploying it, you can leave the `deployTargetUrl` parameter blank.

If you want to run the client in GUI mode locally, the `clientRepositoryUrl` should match the url you will use to host the client repository specified in the **Running the client** section below.

After configuring the [Application.yml](tix-time-client-cli/src/main/resources/Application.yml), the only step left is to generate the executable jar files.

#### CLI mode
```
gradle :tix-time-client-cli:jar 
```
After executing this command, the jars will be located in `tix-time-client-cli/build/libs/`

#### GUI mode

```
gradle :tix-time-client-gui:embedApplicationManifest 
```
After executing this command, the jars will be located in `tix-time-client-gui/build/fxlauncher/`

## Running the client

There are three ways of running the client:

* CLI mode by running `java -jar tix-time-client-cli/build/libs/tix-time-client.jar username password installation port logs_directory`
* GUI mode by running `java -jar tix-time-client-gui/build/fxlauncher/fxlauncher.jar`
* GUI mode by using the native installer and running the application


If you want to run the GUI mode locally with changes in the source code, you will need a local repository on which the application is hosted. Therefore it is recommended to follow these steps:

```
cd tix-time-client-gui/build/fxlauncher/
python3 -m http.server <port>
```

This script will mount a local server in http://localhost:<port> to roleplay the client repository. **Before compiling** in GUI mode, you should change the `clientRepositoryUrl` in the [Application.yml](tix-time-client-cli/src/main/resources/Application.yml) to this local url.

## Generating native installer

To create a native installer for the GUI mode, clone the repository into a computer/VM with the desired native platform (e.g. get a Macbook to generate native OSX installer) and execute: 

```
gradle :tix-time-client-gui:generateNativeInstaller
```
The installer will be located in `build/installer/bundles/`

## Deployng the client 

```
gradle :tix-time-client-gui:deployApp
```

The deploy will simply copy all the files in `tix-time-client-gui/build/fxlauncher/` to the directory specified in the `deployTargetUrl` in the[Application.yml](tix-time-client-cli/src/main/resources/Application.yml)