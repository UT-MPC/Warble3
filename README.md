# Warble3
A flexible and expressive middleware for IoT applications. It focuses on the personalization, interoperability, and simplicity of programming. Warble encapsulates device and communication protocol complexities, representing the interaction to the IoT devices as flexible programming abstractions.

## Development Status : Ongoing
The current version has both Warble and an example of Warble application (in Android) together in the same repository. The reason is because Warble is in early development. Therefore, this repository has an Android project structure. In the near future, we are going to refactor this repository to have Warble and the application sample to be separated.

## Warble IoT Structure
<img src="./doc/media/WarbleIoTStructure.png" width="300" align="right"/>
Warble views the IoT structure differently compared to the today's practice of IoT. Warble's IoT view is built upon device-to-device communications and an open vision of the IoT, rather than multi-centralized structure which relies on the internet connection and cloud services.
IoT devices are divided into two main classes, *Controllers* and *Things*. Things are devices embedded in the environment and providing services as sensors, actuators, accessors (e.g. bridges), or other derivatives. Controllers are users' personal devices that host personal applications that interact with the things. In the figure on the right, the IoT structure instance consists of controller (in <span style="color:red">**red**</span>), sensor (in <span style="color:blue">**blue**</span>), actuator (in <span style="color:green">**green**</span>), and accessor (in <span style="color:gray">**gray**</span>). It is possible for a thing to take one or more roles, such as thermostat (which is a sensor and also an actuator).

## Warble Components
<img src="./doc/media/WarbleArchitecture.png" width="200" style="float: right;"/>
### Warble API
### Thing Proxy
### Binding
### Selector
### Interaction History
