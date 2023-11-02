T1 后端云 Android 例子

本文档是 T1 后端云 官方提供的 Android 例子，方便 Android 开发人员快速使用 T1 进行后端开发。

![alt 预览](./images/1.png)

# 前期准备

## 克隆代码

```shell
git clone git@github.com:t1ykf/t1y-android-demo.git
```

# 使用说明

将 `app/src/main/java/com/t1y_android_demo/t1y/T1YClient.java` 复制到您自己的 Android 项目中，修改包名，并继续。

## 添加依赖

```gradle
implementation 'com.squareup.okhttp3:okhttp:4.11.0'
implementation 'com.google.code.gson:gson:2.10.1'
```

## 配置权限

```xml
<!-- 访问网络权限 -->
<uses-permission android:name="android.permissionINTERNET" />
<!-- 访问网络状态权限 -->
<uses-permission android:name="android.permissionACCESS_NETWORK_STATE" />
<!-- 访问Wi-Fi状态权限 -->
<uses-permission android:name="android.permissionACCESS_WIFI_STATE" />
```

## 配置允许 HTTP 访问

如果你的应用目标为 Android 9（API 级别 28）或更高版本，还需要在清单文件中明确声明允许使用 HTTP 的域名。这可以通过在 `AndroidManifest.xml` 中的 `<application>` 元素内部添加以下内容来完成：

```xml
android:usesCleartextTraffic="true"
```

# 封装说明

- 创建 Gson 和 T1YClient 对象

```java
// 创建一个Gson对象
Gson gson = new Gson();
// 创建一个T1YClient对象
T1YClient t1y = new T1YClient("您的域名", 您的Application ID, "您的API Key", "您的Secret Key");
```

- 创建一条数据

```java
MyBean myBean = new MyBean("王华", 21, "男");
String data = t1y.createOne("student", myBean);
```

- 删除一条数据

```java
String data = t1y.deleteOne("student", "65431d617ed5bb441885c097");
```

- 修改一条数据

```java
MyBean myBean = new MyBean("王华", 22, "男");
JsonObject jsonObject = newJsonObject();
jsonObject.add("$set",(JsonObject) gson.toJsonTre(myBean));
String data = t1y.updateOn("student", "65431b5f7ed5bb441885c095", jsonObject);
```

- 读取一条数据

```java
String data = t1y.readOne("student", "65431b5f7ed5bb441885c095");
```

- 读取全部数据（分页查询）

```java
String data = t1y.readAll("student", 1, 10);
```

# 运行效果

打开 `app/src/main/java/com/t1y_android_demo/t1y/T1YClient.java` 文件，可以看到 CURD 相关方法。

```java
package com.t1y_android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.t1y_android_demo.t1y.T1YClient;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private Button createOne, deleteOne, updateOne, readOne, readAll;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建一个Gson对象
        Gson gson = new Gson();

        // 创建一个T1YClient对象
        T1YClient t1y = new T1YClient("http://dev.t1y.net/api", 1001, "2c6118c4e02b40fe96f5c40ee1dc5561", "650bd657da0243b282d9cab6d75a80ff");

        result = findViewById(R.id.result);

        createOne = findViewById(R.id.createOne);
        deleteOne = findViewById(R.id.deleteOne);
        updateOne = findViewById(R.id.updateOne);
        readOne = findViewById(R.id.readOne);
        readAll = findViewById(R.id.readAll);

        createOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyBean myBean = new MyBean("王华", 21, "男");
                        String data = t1y.createOne("student", myBean);
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Log.i("DATA", data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        deleteOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String data = t1y.deleteOne("student", "65431d617ed5bb441885c097");
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Log.i("DATA", data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        updateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyBean myBean = new MyBean("王华", 22, "男");
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.add("$set", (JsonObject) gson.toJsonTree(myBean));
                        Log.i("DATA", jsonObject.toString());
                        String data = t1y.updateOne("student", "65431b5f7ed5bb441885c095", jsonObject);
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Log.i("DATA", data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        readOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String data = t1y.readOne("student", "65431b5f7ed5bb441885c095");
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Log.i("DATA", data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        readAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String data = t1y.readAll("student", 1, 10);
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Log.i("DATA", data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
```

这只是一个简单的示例，完成了基础的增删改查方法，需根据自己项目需求进行开发。
