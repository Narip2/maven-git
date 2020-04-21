package com.test.first_maven;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

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
    private String username;
    private String repo_name;
    private static DefaultMutableTreeNode tempnode;
    
    public void SetUserName(String user) {
    	username = user;
    }
    public void SetProName(String proname) {
    	repo_name = proname;
    }

    public Vector<String> GetOutput() {
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
    
    //返回对应user的branch
    public Vector<String> GetBranch(String user, String proname){
    	exec("cd /root/"+user+"/"+proname+" && git branch");
    	Vector<String> temp = new Vector<String>();
    	temp = (Vector)output.clone();
    	for(int i = 0; i < temp.size(); i++) {
    		temp.set(i, temp.get(i).substring(2));
    	}
    	return temp;
    }
    
    //返回文件
    public DefaultMutableTreeNode GetAllFiles(String nodename,String branch) {
    	DefaultMutableTreeNode parent = new DefaultMutableTreeNode(nodename);
    	exec("cd /root/"+username+"/"+repo_name+" &&git cat-file -p "+branch+"^{tree}");
    	Vector<String> temp = new Vector<String>();
    	temp = (Vector<String>) output.clone();
    	if(temp.isEmpty()) {
    		//空文件夹
    		DefaultMutableTreeNode son = new DefaultMutableTreeNode(nodename,false);//false表示没有子目录
    		return son;
    	}else {
    		for(int i = 0; i < temp.size(); i++) {
    			if(temp.get(i).split(" ")[1].equals("tree")) {
    				//是目录，生成节点，并递归调用，添加里面的节点
    				parent.add(GetAllFiles(temp.get(i).split("	")[1],temp.get(i).split(" ")[2].split("	")[0]));
    			}else if(temp.get(i).split(" ")[1].equals("blob")){
    				//是文件，直接生成节点，加到父节点
    				tempnode = new DefaultMutableTreeNode(temp.get(i).split("	")[1]);//false表示没有子目录
    				parent.add(tempnode);
    			}
    		}
    	}
    	return parent;
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
