package Demo;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.ProcessResult.Status;

import com.jcraft.jsch.Session;

public class demo2 {
	public static void main(String args[]) {
		SshSessionFactory.setInstance( new JschConfigSessionFactory() {
		    @Override
		    protected void configure( Host host, Session session ) {
		      session.setPassword( "a1b2c3d4E5" );
		    }
		} );
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		Repository repo;
		try {
			repo = repositoryBuilder.setGitDir(new File("D:\\github\\demo\\.git"))
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .setMustExist(true)
			        .build();
			Git git = new Git(repo);
			git.push().call();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
