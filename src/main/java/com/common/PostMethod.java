package com.common;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PostMethod {

    /**
     * 使用post方法，但如果外部map传参进来，然后遍历获取参数放入reqEntity中，此时url接收方式获取不到数据的
     * @throws Exception
     */
    public void postone() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String uri = "http://ehsure.imwork.net:8083/openapi/proj/store/create";
            HttpPost httppost = new HttpPost(uri);

//          这里的是有附件的情况的请求提交
//            String path="C:/Users/Administrator/Desktop/study/微服务/netflix/eureka.png";
//            File file = new File(path);
//            FileBody bin = new FileBody(file);
            StringBody source = new StringBody("11", ContentType.TEXT_PLAIN);
            StringBody xzCode = new StringBody("402", ContentType.TEXT_PLAIN);
            StringBody shortName = new StringBody("宣臻测试", ContentType.TEXT_PLAIN);
            StringBody fullName = new StringBody("宣臻测试", ContentType.TEXT_PLAIN);
            StringBody dealerCode = new StringBody("111111", ContentType.TEXT_PLAIN);
            StringBody dealerName = new StringBody("111111", ContentType.TEXT_PLAIN);

//            HttpEntity reqEntity = MultipartEntityBuilder.create()
//                    .addPart("billData", billData)
////                    .addPart("bin",bin);
//                    .build();

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("source", source)
                    .addPart("xzCode", xzCode)
                    .addPart("shortName", shortName)
                    .addPart("fullName", fullName)
                    .addPart("dealerCode", dealerCode)
                    .addPart("dealerName", dealerName)
//                    .addPart("bin",bin);
                    .build();

            httppost.setEntity(reqEntity);
            httppost.addHeader("Authorization","Basic eHp0ZXN0OmVoc3VyZUAyMDIwX3h6dGVzdA==");

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
//                    System.out.println("Response content length: " + resEntity.getContentLength());
//                    System.out.println("Response content length: " + resEntity.getContent());
                    String result = EntityUtils.toString(resEntity);
                    //打印获取到的返回值
                    System.out.println("Response content: " + result);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    /**
     * 使用post方法
     * @throws Exception
     */
    public void posttwo(List<NameValuePair> formparams, String url) throws Exception{
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            httppost.addHeader("Authorization","Basic eHp0ZXN0OmVoc3VyZUAyMDIwX3h6dGVzdA==");
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        PostMethod postMethod = new PostMethod();

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        //经销商新增
        String url = "http://ehsure.imwork.net:8083/openapi/proj/dealer/user/create";
        formparams.add(new BasicNameValuePair("userName", "133456655656"));
        formparams.add(new BasicNameValuePair("realName", "宣臻测试"));
        formparams.add(new BasicNameValuePair("dealerCode", "0001"));
        formparams.add(new BasicNameValuePair("userStatus", "1"));
        try {
//            postMethod.postone();
            postMethod.posttwo(formparams, url);
        } catch (Exception e) {
            System.out.println("post方法调用异常");
        }
    }
}
