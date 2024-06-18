# Okta inline hook

I do want to set and example for an authentication system that holds payment method.

The idea is that a user needs to pay for his access. Then the user could access the services if he has payed but if is out of payment then it will revoke the call.

There will be a client service that will represent the UI backend. 

The user will be able to access to payment services when he is registered even beeing out of payment, but not to the service.

## Update gradle

I lear to auto update gredle with

```shell
./gradlew wrapper --gradle-version=8.8
```