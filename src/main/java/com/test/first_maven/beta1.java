/*公钥测试
 * 
 */

package com.test.first_maven;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;

import com.jcraft.jsch.Session;




public class beta1 {
	public static void main(String[] args) {
				try {
					SshSessionFactory.setInstance( new JschConfigSessionFactory() {
					    @Override
					    protected void configure( Host host, Session session ) {
					      session.setPassword( "a1b2c3d4E5" );
					    }
					} );
					Git git = Git.cloneRepository().setURI("git@github.com:Narip2/Apptest.git")
//					Git git = Git.cloneRepository().setURI("root@39.97.255.250:/root/narip2/dfad")
							.setDirectory(new File("D:\\gg"))
							.call();
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