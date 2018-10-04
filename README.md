# Warble
A flexible and expressive middleware for IoT applications. It focuses on the personalization, interoperability, and simplicity of programming. Warble encapsulates device and communication protocol complexities, representing the interaction to the IoT devices as flexible programming abstractions. Due to the Warble nature being a middleware, there are three open-ended components, i.e. interface to the application using Warble (through proxy, selector), interface to the real devices in an IoT ecosystem (through adapter, discovery), and interface to the device it resides on (through database, context builder, communication service).

## Development Status : Ongoing
The current version has both Warble and an example of Warble application (in Android) together in the same repository. Therefore, this repository has an Android project structure. In the near future, we are going to refactor this repository to have Warble and the application sample to be separated.
#### Currently Supported Communication Protocol: Wi-Fi, Bluetooth
#### Currently Supported Discovery Protocol: SSDP, UPnP

## Quick Concepts
Warble views the IoT structure differently compared to the today's practice of IoT. Warble's IoT view is built upon device-to-device communications and an open vision of the IoT, rather than multi-centralized structure which relies on the internet connection and cloud services.

<img src="doc/media/WarbleIoTStructure.png" width="300px"/>

### IoT Thing and Structure
Warble divides IoT devices into these roles (a thing is possible to take one or more roles):
- **Controllers** (red)
  > user's personal device which hosts personal applications to interact with *things* based on its context, such as mobile phones, tablets, notebooks
- **Things**
  > IoT devices embedded in the physical environment which capable to provide services related to the environment
  - **Sensor** (blue)
    > *things* which sense the environment properties, such as camera
  - **Actuator** (green)
    > *things* which change the environment properties, such as light
  - **Accessor** (grey)
    > *things* which act as a middleman of one or more *things*, such as bridge.
    > Several evident usages include, but not limited to, extending *things* communication range and capabilities, providing security domain by making the accessor as the single access point.

### Other Terminologies
- **Application**
  > an application that uses Warble on its core to interact with *things*.

### Warble Components and Architecture

<img src="doc/media/WarbleArchitecture.png" width="300px"/>

#### Warble API
> **Application Interface**. *Application* interacts with Warble using the API accessible via *Warble* instance (there is no static methods).

#### *Thing* Proxy
> **Application Interface**. *Thing* Proxy is a Java object returned by a `fetch()` command. It has a type of `Thing`. It represents the corresponding *thing* interface to the application. With the proxy, application could get the attributes of the *thing*, such as name, UUID, *thing* type. Most importantly, the application sends commands to the *thing* via the *thing* proxy.

#### Binding
> **Application Interface**
- **One-Time Binding**
  > *One-Time Binding* is used when an application intends to make a one-time interaction to a specific *thing*.
  > Case example: User A turns on a light in the kitchen via *Warble* application.
- **Dynamic Binding**
  > *Dynamic Binding* is used when an application intends to make a persistent interaction to continuously.  
  > Case example: User A makes a persistent binding to an relevant light to illuminate his/her surrounding lit, including when he moves to another room. *Warble* would make a dynamic binding to a the most relevant light as the user moves.

#### Selector
> **Application Interface**. Selector returns the relevant *things* based on the given input by the application. *Warble* gives four pre-defined selectors (at the moment), i.e. `TypeSelector`, `NearestThingSelector`, `RangeSelector`, and `InteractionHistorySelector`. A group of *selectors* makes a template. When fetching using a template, *Warble* returns the intersection of the relevant *things* according to the *selectors*
For example: `TypeSelector` takes a *thing* type as the input parameters, let's say SMOKE_DETECTOR. When fetching using this selector instance, *Warble* returns *thing* proxies of the relevant smoke detectors that *Warble* could reach to.
> Most importantly, in addition to the pre-defined *selectors*, an *application* could define its own selectors, its own algorithm to select relevant *things* based on the inputs. This serves one of *Warble* traits: Personalization.

#### Interaction History
> **Application Interface**. *Interaction History* adds on *Warble*'s Personalization trait. *Interaction History* is stored in user's personal device to protect user privacy. From these records, *Warble* and its application are able to learn effectively on selecting the relevant *things* in the form of *selectors'* algorithm.

#### Adapter
> **IoT Ecosystem Interface**. *Adapter* is a *Warble* component that makes countless real *things* in the market, manufactured by different vendors, to be represented in generic forms to be recognized and processed in *Warble*. When a new *thing* from a specific vendor is released, the vendor defines the *thing* adapter to be identifiable by *Warble*.

#### Discovery
> **IoT Ecosystem Interface**. *Discovery* defines the mechanism to reach the *things*. *Warble* pre-defines several common-and-public-recognized discovery methods, such as Wi-Fi SDP, Bluetooth SDP, and SSDP. Similar to *adapter*, when a vendor creates its custom discovery for its products in *Warble* to be executed during discovery protocol.

#### Context Builder
> **Host Interface**. *Context Builder* defines context interfaces that can be extracted from the user's *controllers*, for example: location, time, activity, etc. As the *controllers* eveolves over time, more and more *context builder* services can be extracted. This information is usually re-routed to the selectors as input parameters.

#### Communication Service
> **Host Interface**. *Communication Service* is fulfilled and also limited by the communication capabilities of the *controller*, for example: Wi-Fi, bluetooth.

## How to Use Warble?
### Instantiation
```
Warble warble = new Warble();
```

### One-Time Binding
```
List<Selector> template = new ArrayList<Selector>();
template.add(new TypeSelector(THING_CONCRETE_TYPE.LIGHT, THING_CONCRETE_TYPE.THERMOSTAT));
template.add(new NearestThingSelector(myLoc));
template.add(new InteractionHistorySelector(myLoc));

List<Thing> things = warble.fetch(template, 3);    // fetch() returns the thing proxies
```

### Sending Command to a Thing
Use `warble.sendCommand(command : Command, thing : Thing)` to send a command to a thing. `thing` is the *thing* proxy returned by `warble.fetch()` wherease `command` can be instatantiated from `edu.utexas.mpc.warble3.warble.thing.command` package. The table below lists down the supported commands at the moment.

| Supported Command                                  | Description                            |
| -------------------------------------------------- | -------------------------------------- |
| AuthenticateCommand(cred : ThingAccessCredential)  | Command to authenticate a thing        |
| SetThingStateCommand(thingState : ThingState)      | Command to set thing state of a thing  |

Example:
```
List<Thing> things = warble.fetch(template, 3);

for (Thing thing : things) {
  if (thing instance of Light) {
    Light light = (Light) thing;
    
    LightState lightState = new LightState();
    lightState.setActive(ThingState.ACTIVE_STATE.ON)
    
    warble.sendCommand(new SetThingStateCommand(lightState), light)
    
  } 
  else if (thing instance of Thermostat) {
    Thermostat thermostat = (Thermostat) thing;
    //...
  }
}
```

### Creating a Custom *Selector*
A custom *selector* can be built by creating a new class extending *Selector* class. Next, we override two functions, i.e. `fetch()` and `select(List<Thing> things)`.
Function `select(List<Thing> things)` implements an algorithm to select/filter relevant *things* from the input *things*. Therefore, this function can be used to filter *things* independently. Function `fetch()` returns relevant *things* without the caller explicitly giving the input *things*. The input *things* comes from *Warble* database or a custom way to get the input *things*.

```
public final class LOSSelector extends Selector {
  public LOSSelector(Location location, double heading, double angle, double range) {
    // ... save instance variables
  }
	
  @Override
  public List<Thing> fetch() {
    return select(ThingManager.getInstance().getThings());
  }
  
  @Override
  public List<Thing> select(List<Thing> things) {
    CircleSector sector = // ... compute sector
    List<Thing> selectedThings = new ArrayList<>();
    
    for(Thing thing : things) {
      if (sector.contains(thing.getLocation())
        selectedThings.add(thing);
    }
    return selectedThings;
  }
}
```

<!--### Dynamic Binding
Plan plan = new Plan();
plan.set(Plan.LIGHTING_ON, True);
plan.set(Plan.LIGHTING_COLOR, "green");
plan.set(Plan.LIGHTING_BRIGHTNESS, 0.5);
plan.set(Plan.AMBIENT_TEMPERATURE, 298);-->
<!--DBinding dBind = warble.dynamicBind(template, 3);
dBind.bind(plan); // start binding based on plan
// ...
plan.set(Plan.LIGHTING_COLOR, "RGB#EEEEEE");
dBind.bind(plan); // bind again with changed light color-->
