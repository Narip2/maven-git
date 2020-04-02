package Demo;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class demo2{
    private static final String USER="root";
    private static final String PASSWORD="a1b2c3d4E5";
    private static final String HOST="39.97.255.250";
    private static final int DEFAULT_SSH_PORT=22;

    
    public static void exec() {
    	 try{
    		 String command = "git clone --bare root@39.97.255.250:/root/narip/demo /root/narip2/demo";
             JSch jsch=new JSch();

             Session session = jsch.getSession(USER,HOST,DEFAULT_SSH_PORT);
             session.setPassword(PASSWORD);
             Properties config = new Properties();
             config.put("StrictHostKeyChecking", "no");

             // username and password will be given via UserInfo interface.
             session.setUserInfo(new MyUserInfo());
             session.connect();

             Channel channel=session.openChannel("exec");
             ((ChannelExec)channel).setCommand(command);

             channel.setInputStream(null);

             ((ChannelExec)channel).setErrStream(System.err);
             
             channel.connect();
             
             InputStream in = channel.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
             String buf = null;
             while ((buf = reader.readLine()) != null){
                 System.out.println(buf);
             }
             reader.close();
             channel.disconnect();
             session.disconnect();
         }
         catch(Exception e){
             System.out.println(e);
         }
    	 
    }

    private static class MyUserInfo implements UserInfo{
        public String getPassphrase() {
            System.out.println("getPassphrase");
            return null;
        }
        public String getPassword() {
            System.out.println("getPassword");
            return null;
        }
        public boolean promptPassword(String s) {
            System.out.println("promptPassword:"+s);
            return false;
        }
        public boolean promptPassphrase(String s) {
            System.out.println("promptPassphrase:"+s);
            return false;
        }
        public boolean promptYesNo(String s) {
            System.out.println("promptYesNo:"+s);
            return true;//notice here!
        }
        public void showMessage(String s) {
            System.out.println("showMessage:"+s);
        }
    }
    public static void main(String [] args) {
    	exec();
    }    
    
}
       