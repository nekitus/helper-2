package org.helper;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 *
 * @author World
 */
public class SSHReadFile
{
    public String user;
    public String password;
    public String host;


    SSHReadFile()
    {
        Stack file = ReadFile.read("config/homeAuth");
        host = file.pop().toString();
        password = file.pop().toString();
        user = file.pop().toString();
    }

    public void readFile(){

        int port=22;
        String privateKey = "~/.ssh/id_rsa";

        String remoteFile="test.sh";

        try
        {
            JSch jsch = new JSch();

            try {
                jsch.addIdentity(privateKey);
            } catch (Exception e){
//                System.out.println(e);
            }

            System.out.println("identity added ");

            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");

            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");



            InputStream out= null;
            out= sftpChannel.get(remoteFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
            br.close();

        }
        catch(Exception e){System.err.print("error" + e);}

    }

}
