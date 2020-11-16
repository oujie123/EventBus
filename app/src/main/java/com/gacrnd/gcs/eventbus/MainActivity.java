package com.gacrnd.gcs.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new Teacher("JackOu",31));
                    }
                }.start();
//                EventBus.getDefault().post(new Teacher("JackOu",30));
            }
        });
    }

    public void jump(View view) {
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(Student student){
        Log.e("Jack", "当前线程是："  + Thread.currentThread().getName());
        Log.e("Jack","收到的学生信息：" + student.getName() + "," + student.getAge());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void receive(Teacher teacher){
        Log.e("Jack", "当前线程是："  + Thread.currentThread().getName());
        Log.e("Jack","收到的学生信息：" + teacher.getName() + "," + teacher.getAge());
    }
}
