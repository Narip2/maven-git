package com.test.first_maven;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SSH{
    private static final String USER="root";
    private static final String PASSWORD="a1b2c3d4E5";
    private static final String HOST="39.97.255.250";
    private static final int DEFAULT_SSH_PORT=22;
    private Vector<String> output;

    public Vector<String> GetOutput(String str) {
    	return output;
    }
    
    public void exec(String command) {
    	 try{
             JSch jsch=new JSch();

             Session session = jsch.getSession(USER,HOST,DEFAULT_SSH_PORT);
             session.setPassword(PASSWORD);

             // username and password will be given via UserInfo interface.
             session.setUserInfo(new MyUserInfo());
             session.connect();

             Channel channel=session.openChannel("exec");
             ((ChannelExec)channel).setCommand(command);

             channel.setInputStream(null);

             ((ChannelExec)channel).setErrStream(System.err);

             channel.connect();
             //从命令行输入，用于debug
             InputStream in = channel.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
             String buf = null;
             Vector<String> str = new Vector<String>();
             while ((buf = reader.readLine()) != null){
            	 str.add(buf);
                 System.out.println(buf);
             }
             output = str;
             reader.close();
             channel.disconnect();
             session.disconnect();
         }
         catch(Exception e){
             System.out.println(e);
         }
    }
    
    public Vector<String> GetBranch(String user){
    	exec("git branch");
    	Vector<String> temp = new Vector<String>();
    	temp = output;
    	
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
}
