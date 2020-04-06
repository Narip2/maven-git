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

import com.jcraft.jsch.Session;
import com.test.first_maven.Repo_manager;
import com.test.first_maven.login;

public class demo3 {
	public static void main(String[] args) {
		//打开本地仓库
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		try {
			//暂时使用的是密码直接登录的SSH连接方式，之后改进使用公钥的方式
			SshSessionFactory.setInstance( new JschConfigSessionFactory() {
			    @Override
			    protected void configure( Host host, Session session ) {
			      session.setPassword( "a1b2c3d4E5" );
			    }
			} );
			Repository repository = repositoryBuilder.setGitDir(new File("D:\\Git\\narip\\demo\\.git"))
			                .readEnvironment() // scan environment GIT_* variables
			                .findGitDir() // scan up the file system tree
			                .setMustExist(true)
			                .build();
			Git git = new Git(repository);
			git.fetch().setRefSpecs("\"refs/heads/*:refs/heads/*\"").call();
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
