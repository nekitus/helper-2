package org.helper;


import java.util.Stack;

public class Mythread {
    public Mythread(){

    }

    public void run(ChatServer server) throws Exception {


        Stack stack = new Stack();

        MyExecJsch ssh = new MyExecJsch();
        ssh.connect();

        ssh.execute("tail -f /home/nikita/Desktop/myFile", stack);


        // change branch
//        ssh.execute("cd www_dragons; hg branches", stack);

        System.out.println(stack.toString());


//        Watch w = new Watch();

        server.sendToAll("hello from run");

    }
}
