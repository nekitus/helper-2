package org.helper;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;


public class MyExecJsch {
    private JSch jsch;
    private ChannelExec channelExec;
    private Session session;
    private boolean flag;

    private String user;
    private String password;
    private String host;


    public MyExecJsch() {
        this.jsch = new JSch();
    }

    public MyExecJsch connect() throws Exception {

        Stack file = ReadFile.read("config/homeAuth");

        host = file.pop().toString();
        password = file.pop().toString();
        user = file.pop().toString();

        session = jsch.getSession(user, host, 22);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        channelExec = (ChannelExec) session.openChannel("exec");
        return this;
    }

    public void execute(String command, Stack execQueue) throws Exception {
        flag = true;
        info("executing: " + command);
        InputStream stream = channelExec.getInputStream();
        channelExec.setCommand(command);
        info("set command");
        channelExec.connect();
        info("connect");

        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null && flag) {
            info(line);

            execQueue.push(line);
        }


        info("execution continues");
    }

//    public String toString(String s){
//
//        for(int i = 0; i < stack.size(); i++){
//            System.out.println(stack.elementAt(i));
//        }
//    }

    public MyExecJsch stop(){
        this.flag = false;
        info("execution stopped");
        return this;
    }

    public MyExecJsch disconnect() throws Exception {
        int exitCode = channelExec.getExitStatus();
//        info(ExitStatus.getFor(exitCode).message());
        if (channelExec != null) channelExec.disconnect();
        if (session != null) session.disconnect();
        info("disconnected from " + host);
        return this;
    }

//    public String name() {
//        return machine.getName();
//    }
    private void info(String log){
        System.out.println(log);
    }
}