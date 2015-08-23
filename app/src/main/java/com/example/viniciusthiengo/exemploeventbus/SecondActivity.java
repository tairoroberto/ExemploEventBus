package com.example.viniciusthiengo.exemploeventbus;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.viniciusthiengo.exemploeventbus.eventbus.MessageEB;

import de.greenrobot.event.EventBus;


public class SecondActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        // EventBus register
            EventBus.getDefault().registerSticky(SecondActivity.this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        // EventBus register
            EventBus.getDefault().unregister(SecondActivity.this);
    }


    // Listeners
        public void onEventMainThread(MessageEB mMessageEB){

            Log.i("LOG", "SecondActivity.this.onEventMainThread()");

            if(mMessageEB.getList() != null){
                Toast.makeText(SecondActivity.this,
                        "Name: " + mMessageEB.getList().get(0).getName() + "\nJob: " + mMessageEB.getList().get(0).getJob(),
                        Toast.LENGTH_SHORT).show();
            }
        }
}
