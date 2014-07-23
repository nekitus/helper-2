package org.helepr;


public class Mythread {
    public Mythread(){

    }

    public void run(ChatServer server){
        server.sendToAll("hello from run");
    }
}
