package Demo;

import java.util.Vector;

import com.test.first_maven.SSH;

public class demo3 {
	public static void main(String[] args) {
		SSH ssh = new SSH();
		Vector<String> str = new Vector<String>();
		ssh.exec("cd /root/narip/demo &&git fetch root@39.97.255.250:/root/narip2/demo master &&git diff master FETCH_HEAD");
		str = ssh.GetOutput();
		System.out.println("-------------------------------------");
		System.out.println(str.toString());
		System.out.println("-------------------------------------");
	}
}
