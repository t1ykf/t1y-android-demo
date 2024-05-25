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

    private Button createOne, deleteOne, updateOne, findOne, findAll;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建一个 Gson 对象
        Gson gson = new Gson();

        // 创建一个 T1YClient 对象
        T1YClient t1y = new T1YClient("https://api.t1y.net", "1001", "2c6118c4e02b40fe96f5c40ee1dc5561", "650bd657da0243b282d9cab6d75a80ff");

        result = findViewById(R.id.result);

        createOne = findViewById(R.id.createOne);
        deleteOne = findViewById(R.id.deleteOne);
        updateOne = findViewById(R.id.updateOne);
        findOne = findViewById(R.id.findOne);
        findAll = findViewById(R.id.findAll);

        createOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyBean myBean = new MyBean("王华", 21, "男");
                        String data = t1y.createOne("student", myBean); // 向 student 集合中添加一条数据
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
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
                        String data = t1y.deleteOne("student", "65431d617ed5bb441885c097"); // 删除 student 集合中 ObjectID 为 65431d617ed5bb441885c097 的数据
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
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
                        String data = t1y.updateOne("student", "65431b5f7ed5bb441885c095", jsonObject); // 修改 student 集合中 ObjectID 为 65431b5f7ed5bb441885c095 的数据
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        findOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String data = t1y.findOne("student", "65431b5f7ed5bb441885c095"); // 查询 student 表中 ObjectID 为 65431b5f7ed5bb441885c095 的数据
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        findAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String data = t1y.findAll("student", 1, 10); // 查询 student 集合中第1页的全部数据，每页10条
                        createOne.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(data);
                                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}