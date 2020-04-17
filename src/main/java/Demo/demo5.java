package Demo;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import com.alee.utils.encryption.Base64;
import com.test.first_maven.Search;
import com.test.first_maven.after_login;

public class demo5 {
	public static void main(String[] args) {
		String priKeyStr = "MIICXgIBAAKBgQCwdW0m+lYmBw2G8SoTnr+CoOH51pEYUkrEbbItAYoX1guEsyMp\r\n" + 
				"56LBy/auXfuaZTnViAHNbgeMDozKdIQkJo9zW2h6MKQN6eUFGijtXjtW4Kr3XtAi\r\n" + 
				"V/piMdl+cZ141oIBgmfJTk8QEFWXh3PTfzMRmKZHQKimzzM0Qd0H1GhUxwIDAQAB\r\n" + 
				"AoGAZCficnM4npD2Ppd3uHcsYCqM2M/Ovy1GPsO8lAsBpJHbK9C5rrlJChKIy2OQ\r\n" + 
				"wYufDfIKg91l7zr68pa7wgEY/gFaCSdxOTvVumD7DLZV0Rkm9tCTdgqxw+YJaBVY\r\n" + 
				"W2kbbrXG7zfDXvWBGkQK1lgzNhcmnv9EpmSgE0JPvxW+oYECQQDfecF61EOwxNEc\r\n" + 
				"AB3MMtdof/2CLcGOmnKHgfjEEN6ENCXYuqDZDrZB0Yb+mKTdY5bwMDHqOTWGEdGs\r\n" + 
				"mGhxuIqnAkEAyiPqpnZG3UHMzEEiXcDRZqHC2X6BHhu+jmC6xmP6wc1NRx0RV9jW\r\n" + 
				"1aGsRLdEzI0iP4u4OUV923tSL/FolHzI4QJBAJoEAMtVZT17t54zmlW0KG8V3rKM\r\n" + 
				"NFzCpvIrKjZ+Zcz5X6gJn3dYw1WDQSVQMDeAPnNU0+HYAcJfn4DzHPBbPfsCQQCg\r\n" + 
				"VB6Qy61nXwJeW6czb3VnjxQqSDGHPQBGk6hxJOnGf900c3THFaFIrsQheK9kAulQ\r\n" + 
				"N7T2EA1EYasNhc+qPtNhAkEAlp5TUNiqDiez5jRMgPG0fXCy2P8FEEaiDMGhmSzC\r\n" + 
				"dRbw9x+lOyWk9j5ohZo4gIRAWV7mTlQtFSrMPaMM4URq9g==";
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.decode(priKeyStr));
        KeyFactory keyFactory1;
		try {
			keyFactory1 = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory1.generatePrivate(priKeySpec);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
