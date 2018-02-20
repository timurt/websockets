WebSocket
===================

Sample web-application to demonstrate basics of client-server communication using 
**WebSocket** protocol. Client defines two variables and operator, server performs calculation.


----------
Technologies
-------------

- javaee-api 8.0
- javax.websocket-api 1.1
- Gradle 4.2
- JBoss WildFly 10.1.0

----------
Installation
------------

To load dependencies
> $ gradle build

To package war file
> $ gradle war

----------
Communication
------------
Logic for the server part is located inside `WebSocketServer.java` file.
Server listens connections on `ws://localhost:8080/websockets-1.0/ws`.

Logic for the client part is located inside `websocket.js` file.

Sample request
```
{
    action : "add",
    var1 : 23,
    var2 : 65
}
```

Sample response
```
{
    action : "add",
    result : 88,
    status : {
        code : 100,
        message : "Request was successfully processed"
    }
}
```