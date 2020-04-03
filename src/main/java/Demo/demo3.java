package Demo;

import java.util.Vector;

import com.test.first_maven.SSH;

public class demo3 {
	public static void main(String[] args) {
		SSH ssh = new SSH();
		System.out.println(ssh.GetBranch("narip", "demo"));
	}
}
