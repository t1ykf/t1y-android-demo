package com.t1y_android_demo.t1y;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class T1YClient {
    private OkHttpClient client;
    private Gson gson;
    private String baseUrl;
    private int appId;
    private String apiKey;
    private String secretKey;

    public T1YClient(String baseUrl, int appId, String apiKey, String secretKey) {
        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.baseUrl = baseUrl;
        this.appId = appId;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public <T> String createOne(String table, T data) {
        return sendRequest("POST", table, gson.toJson(data));
    }

    public String deleteOne(String table, String id) {
        return sendRequest("DELETE", table + "/" + id, null);
    }

    public <T> String updateOne(String table, String id, T data) {
        return sendRequest("PUT", table + "/" + id, gson.toJson(data));
    }

    public String readOne(String table, String id) {
        return sendRequest("GET", table + "/" + id, null);
    }

    public String readAll(String table, int page, int size) {
        return sendRequest("GET", table + "?page=" + page + "&size=" + size, null);
    }

    public String sendRequest(String method, String table, String data) {
        // 消灭GET请求参数
        int index = table.indexOf("?page=");
        String _table = table;
        if (index != -1) {
            _table = table.substring(0, index);
        }

        RequestBody requestBody = data != null ? RequestBody.create(data, MediaType.parse("application/json")) : null;

        long timestamp = System.currentTimeMillis() / 1000; // 获取当前时间戳（以秒为单位）
        String nonceStr = MD5(Long.toString(timestamp)); // 生成32位随机码
        Request.Builder requestBuilder = new Request.Builder()
                .url(this.baseUrl + "/v5/classes/" + table)
                .method(method, requestBody)
                .header("Content-Type", "application/json")
                .header("X-T1Y-Application-ID", String.valueOf(this.appId))
                .header("X-T1Y-Api-Key", this.apiKey)
                .header("X-T1Y-Safe-NonceStr", nonceStr)
                .header("X-T1Y-Safe-Timestamp", String.valueOf(System.currentTimeMillis() / 1000))
                .header("X-T1Y-Safe-Sign", MD5("/v5/classes/" + _table + this.appId + this.apiKey + nonceStr + timestamp + this.secretKey));
        Request request = requestBuilder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String MD5(String input) {
        try {
            // 创建一个MessageDigest对象，指定算法为MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将输入字符串转换为字节数组
            byte[] inputBytes = input.getBytes("UTF-8");

            // 计算MD5哈希值
            byte[] md5Bytes = md.digest(inputBytes);

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                String hex = Integer.toHexString(0xFF & md5Byte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
