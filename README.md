# appServer

to test this

run this in Terminal:
```
git clone https://https://github.com/ivangladius/appServer.git
cd appServer
``` 
just run: 
```
bash run
```
which compiles all java files appropiate

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

The client will Simulate an getUsername() request, the Server then replies which the proper Username

