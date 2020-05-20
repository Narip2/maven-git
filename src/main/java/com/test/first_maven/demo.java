package com.test.first_maven;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.transport.OpenSshConfig.Host;

import com.jcraft.jsch.Session;
import com.test.first_maven.Repo_manager;
import com.test.first_maven.login;

public class demo {
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
			
			//git merge操作
//			List<Ref> refs = git.branchList().call();
//			for(Ref ref:refs) {
//				System.out.println(ref);
//			}
//			git.merge().include(refs.get(0)).call();
			
			
			//git diff操作
//			AbstractTreeIterator oldtree = prepareTreeParser(repository,"5724adae27f4c276e7d8fd7f96689aad22ea5a40");
//			AbstractTreeIterator newtree = prepareTreeParser(repository,"f682bb124152fad8d82610a2c7de467827c8109b");
//			List<DiffEntry> res = git.diff().setSourcePrefix("refs/heads/master").setDestinationPrefix("refs/heads/dev").call();
//			List<DiffEntry> res = git.diff().setOldTree(oldtree).setNewTree(newtree).call();
//			for(DiffEntry d:res) {
//				System.out.println(d);
//				 try (DiffFormatter formatter = new DiffFormatter(System.out)) {
//                     formatter.setRepository(repository);
//                     formatter.format(d);
//                     }
//			}
			
//			git 获取refs name操作  diff操作需要用到
			List<Ref> refs = git.branchList().call();
			System.out.println(repository.getIndexFile());
			for(Ref ref:refs) {
				System.out.println(ref.getName());
//				System.out.println(ref.getUpdateIndex());
				System.out.println(ref.getObjectId().getName());
			}
			
//			git.pull().setRemote("origin").setRemoteBranchName("dev").call();
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
	
	 private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
	        // from the commit we can build the tree which allows us to construct the TreeParser
	        //noinspection Duplicates
	        try (RevWalk walk = new RevWalk(repository)) {
	            RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
	            RevTree tree = walk.parseTree(commit.getTree().getId());

	            CanonicalTreeParser treeParser = new CanonicalTreeParser();
	            try (ObjectReader reader = repository.newObjectReader()) {
	                treeParser.reset(reader, tree.getId());
	            }

	            walk.dispose();

	            return treeParser;
	        }
	    }
}
