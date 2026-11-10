package com.liu.testDemo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ author   : liu long fei
 * @ date     : 2025/11/7 22:38
 * @ version  : 1.0
 * @ describe : // todo
 **/
@Configuration
public class RestTemplateConfig {


    // 配置代理
    private static final Proxy proxy = new Proxy(Proxy.Type.HTTP,
            new InetSocketAddress("192.168.0.44", 10808));

    // 配置OkHttpClient
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .proxy(proxy)  // 设置代理
            .connectTimeout(30, TimeUnit.SECONDS) // 连接超时
            .readTimeout(30, TimeUnit.SECONDS)    // 读取超时
            .writeTimeout(30, TimeUnit.SECONDS)   // 写入超时
            .followRedirects(true)               // 跟随重定向
            .followSslRedirects(true)            // 跟随SSL重定向
            .retryOnConnectionFailure(true)      // 连接失败时重试
            .build();

    public static String fetchWebPage(String url) throws IOException {
        // 创建请求，设置合适的User-Agent和其他头部
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
//                .header("Accept-Encoding", "gzip, deflate, br")
                .header("content-type","text/html; charset=utf-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

    public static void main(String[] args) {
        String url = "https://sukebei.nyaa.si/?f=0&c=0_0&q=%E6%9E%AB%E5%8F%AF%E6%80%9C&p=1";

        try {
            String htmlContent = fetchWebPage(url);
            System.out.println("成功获取网页内容，长度: " + htmlContent.length());
            // 可以在这里处理获取到的html内容
        } catch (IOException e) {
            System.err.println("请求网页时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
