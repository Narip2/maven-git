package Demo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class demo4 {
	public static void main(String[] args) {
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		try {
			Repository repository = repositoryBuilder.setGitDir(new File("D:\\Git\\narip\\demo\\.git"))
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .setMustExist(true)
			        .build();
			Git git = new Git(repository);
			git.branchDelete().setBranchNames("dev").call();
//			System.out.println(repository.getBranch());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
