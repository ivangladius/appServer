# appServer

to test this

run this in Terminal:
```
git clone https://https://github.com/ivangladius/appServer.git
cd appServer
javac Main.java
javac client.java
javac appServer/Server.java
```

Now open two terminals in one you start the server: 
```
java Main 8888 8
```
where 8888 can be any port number and 8 is the threadpool count

in the other terminal start the client and request the server: 
```
java client 8888
```
where 8888 is the port of the Server

