# SmsSender
Get phone number and send information
The developer needs to get a text message and some phone numbers and then send an information SMS to each phone.

## Architecture
Use clean architecture layers in module, is not necessary, with packages is enough. App module can implement data domain and usecases modules but domain for example can not see anything of other modules. Data and usecases only use domain and not know nothing of the presentation layer.

### Modules
#### Domain

#### Data

#### UsesCases

#### App Module

## Test
Run all ( Unit and instrumentation) test  ./gradlew testAll

## Manage Errors with retrofit


## Devices tested SAMSUNG SM -J510FN and NEXUS 5X
Testes version in TAG v0.0.1