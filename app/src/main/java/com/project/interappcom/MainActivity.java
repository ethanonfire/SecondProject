package com.project.interappcom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity
{
    private Messenger messenger = null; //used to make an RPC invocation
    private boolean isBound = false;
    private ServiceConnection connection;//receives callbacks from bind and unbind invocations
    private Messenger replyTo = null; //invocation replies are processed by this Messenger

    public static final String TAG = "MainActivity";
    //this is branch2! i am awesome!


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button invokeButton = (Button) findViewById(R.id.button);
        invokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {

                final Intent intent = new Intent();
                intent.setAction("com.project.app2.Ruby");
             //   intent.putExtra("KeyName" , "code1id");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.setComponent(new ComponentName("com.project.app2" , "com.project.app2.MyBroadcastReceiver"));
                sendBroadcast(intent);
            }

    });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart");
//
//        //Bind to the remote service
//        Intent intent = new Intent();
//        intent.putExtra("num",111);
//        intent.setPackage( this.getPackageName());
//        intent.setClassName(this, "com.project.interappcom.RemoteService");

        //this.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop");

//        //Unbind if it is bound to the service
//        if(this.isBound)
//        {
//            this.unbindService(connection);
//            this.isBound = false;
//        }
    }

    @Override
    protected void onResume()
    {

        super.onResume();
        Log.d(TAG, "onResume");


    }

}
