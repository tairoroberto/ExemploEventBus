package com.example.viniciusthiengo.exemploeventbus.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.viniciusthiengo.exemploeventbus.MainActivity;
import com.example.viniciusthiengo.exemploeventbus.com.example.viniciusthiengo.exemploeventbus.domain.Person;
import com.example.viniciusthiengo.exemploeventbus.eventbus.MessageEB;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by viniciusthiengo on 12/15/14.
 */
public class ServiceTest extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate(){
        Log.i("LOG", "ServiceTest.onCreate()");

        // EventBus Register
            EventBus.getDefault().register(ServiceTest.this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LOG", "ServiceTest.onStartCommand()");
        countThread();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();

        // EventBus Unregister
            EventBus.getDefault().unregister(ServiceTest.this);
    }


    public void countThread(){
        Thread t = new Thread(new Runnable(){
            public void run(){
                for(int i = 0; i < 2; i++){

                    MessageEB m = new MessageEB();
                    m.setClassTester(MainActivity.class+"");
                    m.setNumber(i + 1);
                    m.setText("Random message: "+(i + 1));

                    EventBus.getDefault().post(m);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }


    public void onEvent(MessageEB mMessageEB){
        if(!mMessageEB.getClassTester().equalsIgnoreCase(ServiceTest.class+""))
            return;

        Log.i("LOG", "ServiceTest.onEvent()");

        Person p = new Person("Thiengo", "Developer");
        List<Person> list = new ArrayList<Person>();
        list.add(p);
        mMessageEB.setClassTester(MainActivity.class+"");
        mMessageEB.setList(list);

        EventBus.getDefault().postSticky(mMessageEB);
    }
}
