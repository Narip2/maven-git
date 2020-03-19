package com.test.first_maven;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public class spider2 {
	public static void main(String [] args) throws IOException {
		SSH ssh = new SSH();
		ssh.exec("mkdir zzy");
	}
}