# Project specifications

## Protocol used

The protocol used to transfer data between client and server is **TCP** because the application cannot allow loss of data on the network and the **TCP** protocol makes sure the packages are well received.

## Addresses and ports used

### Server

The server will listen on port **2021** with the IPv4 address of the machine where the application will be running on (localhost).

### Client

The client will try to contact the server on port **2021** and on the server's address, which will be the address of the machine where the server program will be running on.

## Who speaks first

The first side to speak is the **client**. It will be contacting the server with a "greetings" message and if the server is available, it will answer with a "welcome" message.

## Sequence of messages exchanged (flow)

![image-20210318104637430](C:\Users\mazie\AppData\Roaming\Typora\typora-user-images\image-20210318104637430.png)

## What happens when a message is received from the other party ? (semantics)

When a message is received by the client / server it is written in an input stream. It is then processed.

**Client**

The client will read the stream content and display the received response (operation result or error)

**Server**

The server will read the stream, parse the data and make the operation or generate an error that will be sent through an output stream.

## What is the syntax of the messages ? How do we parse them ? (syntax)

All the messages are all structured the same way :

**Client message** : [**operation**] [**operand1**] [**operand2**]

Example : **Add 3 2**

**Server message** : [**response status**] [**operation result**]

Example : **OK 5**

The messages are streams that can be converted into strings, split and converted into integers if needed.

## When is the connexion closed and by who ?

When the **client** is done, it will close its socket. The server will be notified and will immediately close its connexion too.