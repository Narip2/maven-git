package com.test.first_maven;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class beta {
	public static void main(String[] args) {
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
//		Git.open(new File("/path/to/repo/.git"))
//	    .checkout();
//	Repository repository = git.getRepository();
		
		try {
			Repository repository = repositoryBuilder.setGitDir(new File("D:\\github\\Apptest\\.git"))
			                .readEnvironment() // scan environment GIT_* variables
			                .findGitDir() // scan up the file system tree
			                .setMustExist(true)
			                .build();
			Git git = new Git(repository);
			Status status = git.status().call();
			git.rm().addFilepattern(".").call();
			
			for(String set:status.getAdded()) {//已经在缓存区的   缓存
				System.out.println("getAdded");
				System.out.println(set);
			}
			
			for(String set:status.getChanged()) {//缓存
				System.out.println("getChanged");
				System.out.println(set);
			}
			for(String set:status.getMissing()) {//Add 了之后，再把文件删除了，删除的动作 本地
				System.out.println("getMissing");
				System.out.println(set);
			}
			for(String set:status.getModified()) {//Add之后，修改文件 本地
				System.out.println("getModified");
				System.out.println(set);
			}
			for(String set:status.getRemoved()) { //缓存
				System.out.println("getRemoved");
				System.out.println(set);
			}
			for(String set:status.getUncommittedChanges()) {//总的 所有Uncommit都在 
				System.out.println("getUncommited");
				System.out.println(set);
			}
			for(String set:status.getUntracked()) {//没有Add 本地
				System.out.println("Untracted");
				System.out.println(set);
			}
//			System.out.println(status.isClean());
//			System.out.println(status.hasUncommittedChanges());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoWorkTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
