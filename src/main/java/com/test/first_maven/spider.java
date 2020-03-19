package com.test.first_maven;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public class spider {
	public static void main(String [] args) throws IOException {
		Connection connect = Jsoup.connect("https://gitee.com/login");
		
		connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		connect.header("Accept-Encoding", "gzip, deflate, br");
		connect.header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8,zh-CN;q=0.7,zh;q=0.6");
		connect.header("Cache-Control", "max-age=0");
		connect.header("Connection", "keep-alive");
		connect.header("Content-Length", "388");
		connect.header("Content-Type", "application/x-www-form-urlencoded");
		connect.header("Host", "gitee.com");
		connect.header("Origin", "https://gitee.com");
		connect.header("Referer", "https://gitee.com/login");
		connect.header("Sec-Fetch-Dest", "document");
		connect.header("Sec-Fetch-Mode", "navigate");
		connect.header("Sec-Fetch-Site", "same-origin");
		connect.header("Sec-Fetch-User", "?1");
		connect.header("Upgrade-Insecure-Requests", "1");
		connect.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
		
		connect.data("encrypt_key","password");
		connect.data("utf8","✓");
		connect.data("authenticity_token","kAyRHmh1/Aon8/8ojyJqz2aZxifI18e3dyJZICervVw=");
		connect.data("redirect_to_url","");
		connect.data("user[login]","narip");
		connect.data("encrypt_data[user[password]]","c6qgKYW0R+mSM2vmptVB6swzmRYeCw7OZbiqr+eIUEIRUrOPZzNBeiRWpkL4P4hWXjVcJUOrLBv2r0pD8yzpSRoak81YoGxrSrVKg16m9n7crOR8VLz3nkMcJI9Tskptc5sf4tahC1oKeQ5k9dOy+j/q8fmpgpnrRsBDBdOYcn4=");
		connect.data("user[remember_me]","0");
		
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
