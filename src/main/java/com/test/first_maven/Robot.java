package com.test.first_maven;

import java.io.IOException;

/**
 * GITHUBLoginApater.java
 *
 * Function：Jsoup model apater class.
 *
 *   ver     date           author
 * ──────────────────────────────────
 *   1.0     2017/06/22     bluetata
 *
 * Copyright (c) 2017, [https://github.com/] All Rights Reserved.
 */
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
 
/**
 * @since      crawler(datasnatch) version(1.0)</br>
 * @author     bluetata / sekito.lv@gmail.com</br>
 * @reference  http://bluetata.blog.csdn.net/</br>
 * @version    1.0</br>
 * @update     03/14/2019 16:00
 */
public class Robot {
	public static void main(String[] args) throws IOException {
		Connection connect = Jsoup.connect("https://github.com/session");
		
		//伪造请求头
		connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		connect.header("Accept-Encoding", "gzip, deflate, br");
		connect.header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8,zh-CN;q=0.7,zh;q=0.6");
		connect.header("Cache-Control", "max-age=0");
		connect.header("Connection", "keep-alive");
		connect.header("Content-Length", "404");
		connect.header("Content-Type", "application/x-www-form-urlencoded");
		connect.header("Host", "github.com");
		connect.header("Origin", "https://github.com");
		connect.header("Referer", "https://github.com/login");
		connect.header("Sec-Fetch-Dest", "document");
		connect.header("Sec-Fetch-Mode", "navigate");
		connect.header("Sec-Fetch-Site", "same-origin");
		connect.header("Sec-Fetch-User", "?1");
		connect.header("Upgrade-Insecure-Requests", "1");
		connect.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
		
		//携带登录信息
		connect.data("commit","Sign in").data("authenticity_token","mlzLYaVG3hfj3Hsb35ui6d6VDCVwMc64RluJaOSAKnSDudsOns9tkGUTWZe+RDnO9ljuLp3lUY09kztWlzlqoA==");
		connect.data("ga_id","288412572.1584432574");
		connect.data("login","502648055@qq.com").data("password","1286884843narip");
		connect.data("webauthn-support","supported");
		connect.data("webauthn-iuvpaa-support","supported");
		connect.data("return_to","");
		connect.data("required_field_18a2","");
		connect.data("timestamp","1584538608637");
		connect.data("timestamp_secret","cdf57b59be68de1d54eb86552fadb7b0742f28664d9e657600fcc2c9078d6654");
		
		
		//请求url获取响应信息
        Response res = connect.ignoreContentType(true).method(Method.POST).execute();// 执行请求
        // 获取返回的cookie
        Map<String, String> cookies = res.cookies();
        for (Entry<String, String> entry : cookies.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
        System.out.println("---------华丽的分割线-----------");
        String body = res.body();// 获取响应体
        System.out.println(body);
	}
	
}