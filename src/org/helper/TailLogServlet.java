package org.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Run several ssh commands in a single JSch session
 */
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

/**
 * Servlet implementation class TailLogServlet
 */
@WebServlet(description = "This servlet tails the remote logs", urlPatterns = { "/TailLogServlet" })
public class TailLogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TailLogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            response.setContentType("text/html");

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

            PrintWriter out = new PrintWriter (response.getOutputStream());//.getWriter();
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

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}