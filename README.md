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

* `clientRepositoryUrl`: The URL where the jars of the client will be located. When running the client in GUI mode, the contents of **tix-time-client-gui/build/fxlauncher/** need to be hosted in this URL (see the **GUI mode** section below for an example on how to do this), since the client will retreive those jars when starting. This parameter can be left as an empty string when running in CLI mode.
* `deployTargetUrl`: The location where the jars of the client will be deployed (see the **Deployng the client** section). Note that write access will be required to this location. If you only want to run the client locally without deploying it, you can leave this parameter as an empty string.
* `webApiUrl`: The URL where the web API from TiX is hosted. For more information, refer to [tix-api](https://github.com/TiX-measurements/tix-api)
* `serverIp`: The server IP from TiX to which UDP messages from the client will be sent. For more information, refer to [tix-time-server](https://github.com/TiX-measurements/tix-time-server)
* `serverPort`: The port corresponding to the `serverIp`.
* `clientPort`: The port to be used by the client on the connection with the server.

If you want to run the client in GUI mode locally, the `clientRepositoryUrl` should match the url you will use to host the client repository, as specified in the **Running the client** section below.

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

If you want to run the GUI mode locally, you will need a local repository on which the application is hosted. Therefore it is recommended to follow these steps:

```
cd tix-time-client-gui/build/fxlauncher/
python3 -m http.server <port>
```

This script will mount a local server in `http://localhost:<port>` to roleplay the client repository. **Before compiling** in GUI mode, you should change the `clientRepositoryUrl` in the [Application.yml](tix-time-client-cli/src/main/resources/Application.yml) to this local URL.


## Running the client

There are three ways of running the client:

* CLI mode by running `java -jar tix-time-client-cli/build/libs/tix-time-client.jar username password installation port logs_directory`
* GUI mode by running `java -jar tix-time-client-gui/build/fxlauncher/fxlauncher.jar`
* GUI mode by using the native installer and running the application

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

The deploy will simply copy all the files in `tix-time-client-gui/build/fxlauncher/` to the directory specified in the `deployTargetUrl` in the [Application.yml](tix-time-client-cli/src/main/resources/Application.yml). The url can be local or remote. Bear in mind that if the location is remote, the url `<user>@<deploy-target-ip>:<directory>` must indicate a valid user with write access to the directory.
