package org.helper;

import org.java_websocket.WebSocketImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class App {
    public static void main(String arg[]) throws Throwable
    {
//        SSHReadFile ssh = new SSHReadFile();
//                ssh.readFile();
//        writeCommand();
        initSocket();

        // Create window
//        HttpServer.init() ;

//        ssh.execute("cd www_dragons; hg branch", stack);




//

//        hg up "branch"

//            ./bin/restart.sh; grunt build




    }
    public static void initSocket() throws InterruptedException , IOException {


        WebSocketImpl.DEBUG = true;
        int port = 8887; // 843 flash policy port
        try {
//            port = Integer.parseInt( args[ 0 ] );
        } catch ( Exception ex ) {
        }
        ChatServer s = new ChatServer( port, new Mythread() );
        s.start();
        System.out.println( "ChatServer started on port: " + s.getPort() );

        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();
            s.sendToAll( in );
            if( in.equals( "exit" ) ) {
                s.stop();
                break;
            } else if( in.equals( "restart" ) ) {
                s.stop();
                s.start();
                break;
            }
        }
    }

}