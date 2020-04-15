package Demo;



import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class demo4 {
	public static void main(String[] args) {
		 final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
	            @Override
	            protected void configure(OpenSshConfig.Host host, Session session) {
	                session.setConfig("StrictHostKeyChecking", "no");
	            }

	            @Override
	            protected JSch createDefaultJSch(FS fs){
	            	JSch jSch = null;
	                try {
	                	jSch = super.createDefaultJSch(fs);
//	 	                jSch.addIdentity("C:\\Users\\50264\\.ssh\\id_rsa");
	 	                jSch.addIdentity("D:\\id_rsa");
//						jSch.setKnownHosts("C:\\Users\\50264\\.ssh\\known_hosts");
					} catch (JSchException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                return jSch;
	            }
	        };
//			Repository repository = repositoryBuilder.setGitDir(new File("D:\\Git\\narip\\demo\\.git"))
//			        .readEnvironment() // scan environment GIT_* variables
//			        .findGitDir() // scan up the file system tree
//			        .setMustExist(true)
//			        .build();
//			Git git = new Git(repository);
			try {
//				Git git = Git.cloneRepository().setURI("root@39.97.255.250:/root/narip/demo")
//						.setDirectory(new File("D:\\Git\\demo"))
//						.call();
				 CloneCommand cloneCommand = Git.cloneRepository();
			        cloneCommand.setTransportConfigCallback(new TransportConfigCallback() {
			            @Override
			            public void configure(Transport transport) {
			                SshTransport sshTransport = (SshTransport) transport;
			                sshTransport.setSshSessionFactory(sshSessionFactory);
			            }
			        });
			        cloneCommand.setURI("root@39.97.255.250:/root/narip/demo");
			        cloneCommand.setDirectory(new File("D:\\\\Git\\\\demo"));
			        cloneCommand.call();
			} catch (InvalidRemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
