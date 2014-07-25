package org.helper;

import java.io.*;
import java.util.Hashtable;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class SSHUploader {

    Session session = null;

    public SSHUploader(){

    }

    public void connect(){
//		String SFTPHOST = PropertiesHandler.getProperty("SFTP_HOST");
//		int SFTPPORT = 22;
//		String SFTPUSER = PropertiesHandler.getProperty("USER");
//		String SFTPPASS =PropertiesHandler.getProperty("PASS");
//		String passKey=PropertiesHandler.getProperty("KEY_PASSKEY");
//
        try {

            JSch jsch = new JSch();

//can do private key, or private key with passphrase
//			if(passKey==null){
//				jsch.addIdentity(PropertiesHandler.getProperty("KEY_FILE"));
//			} else {
//				jsch.addIdentity(PropertiesHandler.getProperty("KEY_FILE"), passKey);
//			}

            //hacked in direct for now; had been pulling from props
            session = jsch.getSession("nikita", "localhost", 22);
//			if(SFTPPASS!=null){
            session.setPassword("qwe");
//			}
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public void uploadFile(File f) {

        String workingDir = PropertiesHandler.getProperty("UPLOAD_TO_DIR");

        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            if(workingDir!=null){
                channelSftp.cd(workingDir);
            }
            String fileName = PropertiesHandler.getProperty("FILENAME_OVERRIDE");
            if(fileName==null){
                fileName=f.getName();
            }
            System.out.println("Upload as: "+fileName);
            channelSftp.put(new FileInputStream(f), fileName);
            channelSftp.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/


    public static void exexuteCommand2 (String script){
        // TODO Auto-generated method stub
        try{
            System.out.println("**************************ADI NEW*****************");
            JSch jsch = new JSch();
            String user = "nikita";            //CHANGE ME
            String host = "localhost"; //CHANGE ME
            String passwd = "qwe";      //CHANGE ME
            int port = 22;
            Session session = jsch.getSession(user, host, port);
            session.setPassword(passwd);

            Hashtable<String, String> hashtable  = new Hashtable<String, String>();
            hashtable.put("StrictHostKeyChecking", "no");
            session.setConfig(hashtable);

            session.connect();

            Channel channel = session.openChannel("shell");
            OutputStream ops = channel.getOutputStream();
            PrintStream ps = new PrintStream(ops, true);

            channel.connect();
            InputStream input = channel.getInputStream();

            ps.println("tail -f /home/nikita/Desktop/myFile");
            ps.close();

            // response.setIntHeader("Refresh", 1);
//            response.setContentType("text/html");

              /*Calendar calendar = new GregorianCalendar();
              String am_pm;
              int hour = calendar.get(Calendar.HOUR);
              int minute = calendar.get(Calendar.MINUTE);
              int second = calendar.get(Calendar.SECOND);
              if(calendar.get(Calendar.AM_PM) == 0)
                am_pm = "AM";
              else
                am_pm = "PM";

              String CT = hour+":"+ minute +":"+ second +" "+ am_pm;*/

            PrintWriter out = new PrintWriter (channel.getOutputStream());//.getWriter();
            out.println("<html><head><title>GuestBookServlet</title></head>");
            out.println("<body>********************");

            int SIZE = 1024;
            byte[] tmp = new byte[SIZE];
            while (true)
            {
                while (input.available() > 0)
                {
                    int i = input.read(tmp, 0, SIZE);
                    if(i < 0)
                        break;
                    System.out.print(new String(tmp, 0, i)); // use document.write(new String(tmp, 0, i));
                    // System.out.println("******Adi i :"+i);
                    //out.println("Out by Adi i :"+i);
                    out.println(new String(tmp, 0, i));
                }
                if(!channel.isConnected())
                {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try
                {
                    Thread.sleep(300);
                }
                catch (Exception ee)
                {
                }
            }
            out.println("</body></html>");
            out.close();


            channel.disconnect();
            session.disconnect();
        }catch(Exception e){
            e.printStackTrace();

        }
    }


    public void executeCommand(String script) throws JSchException, IOException{
        System.out.println("Execute sudo");
//		String sudo_pass = PropertiesHandler.getProperty("PASS");
        ChannelExec channel = (ChannelExec) session.openChannel("exec");


        // man sudo
        // -S The -S (stdin) option causes sudo to read the password from
        // the
        // standard input instead of the terminal device.
        // -p The -p (prompt) option allows you to override the default
        // password prompt and use a custom one.
//		String script = PropertiesHandler.getProperty("SUDO_SCRIPT");
        ((ChannelExec) channel).setCommand( script);

        InputStream in = channel.getInputStream();
        OutputStream out = channel.getOutputStream();
        ((ChannelExec) channel).setErrStream(System.err);

        channel.connect();
//pass for sudo
//		out.write((sudo_pass + "\n").getBytes());
//		out.flush();

        byte[] tmp = new byte[1024];
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis()- startTime)< 3*1000) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
                System.out.println(ee);
            }
        }
        channel.disconnect();
        System.out.println("Sudo disconnect");
    }

    public void disconnect(){
        session.disconnect();
    }


    public static void main(String... args) throws JSchException, IOException// {

        SSHUploader up = new SSHUploader();
//        up.connect();

        exexuteCommand2("");
//        up.executeCommand("tail -f /home/nikita/Desktop/myFile");

//        up.disconnect();
    }

}