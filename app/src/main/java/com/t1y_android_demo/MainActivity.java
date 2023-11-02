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