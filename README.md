# Hello World using JMS

A simple test project for demonstrating a simple JMS pubs-subs to a Queue. This app is deployed on top of JBoss EAP 7.3 with JDK 8, and Red Hat AMQ.

## How to test

By default, opening below url would generate a 100 JMS request.
```
http://127.0.0.1:8080/HelloWorldJMS-1.0/hello-world
```

While opening below url would test a 1000 JMS request.
```
http://127.0.0.1:8080/HelloWorldJMS-1.0/hello-world?count=1000
```