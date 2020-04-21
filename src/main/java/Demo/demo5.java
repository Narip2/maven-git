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
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListNotesCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.alee.utils.encryption.Base64;
import com.test.first_maven.Function;
import com.test.first_maven.Search;
import com.test.first_maven.after_login;

public class demo5 {
	public static void main(String[] args) {
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		Repository repository = null;
		try {
			repository = repositoryBuilder.setGitDir(new File("D:\\Git\\narip\\demo\\.git"))
			.readEnvironment() // scan environment GIT_* variables
			.findGitDir() // scan up the file system tree
			.setMustExist(true)
			.build();
			Git git = new Git(repository);
			List<Note> lnc = git.notesList().call();
			String file[] = repository.getWorkTree().list();
			for(String str:file) {
				System.out.println(str);
			}
			for(Note note:lnc) {
				System.out.println("in");
				System.out.println(note.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
