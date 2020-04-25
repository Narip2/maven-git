package Demo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListNotesCommand;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.FS;

import com.alee.utils.encryption.Base64;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.test.first_maven.Function;
import com.test.first_maven.Search;
import com.test.first_maven.after_login;
import com.test.first_maven.login;

public class demo5 {
	public static void main(String[] args) {
		final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
            	//第一次访问服务器不用输入yes
                session.setConfig("StrictHostKeyChecking", "no");
            }
            @Override
            protected JSch getJSch(final OpenSshConfig.Host hc, FS fs) throws JSchException {

                JSch jsch = super.getJSch(hc, fs);
                jsch.removeAllIdentity();
                jsch.addIdentity("C:\\Users\\50264\\.ssh\\id_rsa2");
                return jsch;
            }
            
        };
		
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		Repository repository = null;
		try {
			repository = repositoryBuilder.setGitDir(new File("D:\\Git\\narip2\\demo\\.git"))
			.readEnvironment() // scan environment GIT_* variables
			.findGitDir() // scan up the file system tree
			.setMustExist(true)
			.build();
			Git git = new Git(repository);
			FetchCommand fetchCommand = git.fetch();
			fetchCommand.setRefSpecs("refs/heads/master");
			fetchCommand.setTransportConfigCallback(new TransportConfigCallback() {

				@Override
				public void configure(Transport transport) {
					// TODO Auto-generated method stub
					SshTransport sshTransport = (SshTransport) transport;
	                sshTransport.setSshSessionFactory(sshSessionFactory);
				}});
			fetchCommand.call();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
