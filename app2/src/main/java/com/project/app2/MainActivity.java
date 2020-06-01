package com.project.app2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.appcompat.app.AppCompatActivity;
import com.Util;

public class MainActivity extends AppCompatActivity
{
    private Messenger messenger = null; //used to make an RPC invocation
    private boolean isBound = false;
    private ServiceConnection connection;//receives callbacks from bind and unbind invocations
    private Messenger replyTo = null; //invocation replies are processed by this Messenger

    public static final String TAG = "MainActivity";
    private MyBroadcastReceiver MyReceiver;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        

        
        
        MyReceiver = new MyBroadcastReceiver();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent=new Intent();
                intent.setAction("com.project.app2.Ruby");

               // intent.putExtra("KeyName","code1id");
               // intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
               // intent.setComponent(new ComponentName("com.project.app2", "com.project.app2.MainActivity"));
                sendBroadcast(intent);


            }
        });

    }
//    public class MyBroadcastReceiver extends BroadcastReceiver
//    {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(MainActivity.this, "Data Received from External App", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "Data Received from External App");
//        }
//    }

    @Override
    protected void onStart()
    {
        super.onStart();
        MyReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("com.project.app2.Ruby");
        registerReceiver(MyReceiver, intentFilter);

        Log.d(TAG, "onStart");


    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        unregisterReceiver(MyReceiver);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    private class RemoteServiceConnection implements ServiceConnection
    {
        @Override
        public void onServiceConnected(ComponentName component, IBinder binder)
        {
            MainActivity.this.messenger = new Messenger(binder);

            MainActivity.this.isBound = true;

            Message message = Message.obtain(null, 0, 0, 0);
            message.sendingUid = 1002;

            //send initial msg to
            try {
                messenger.send(message);
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }


            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName component)
        {
            MainActivity.this.messenger = null;

            MainActivity.this.isBound = false;
            Log.d(TAG, "onServiceDisconnected");

        }
    }

    private class IncomingHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            System.out.println("*****************************************");
            System.out.println("Return successfully received!!!!!!");
            System.out.println("*****************************************");

            int what = msg.what;

            Toast.makeText(MainActivity.this.getApplicationContext(), "Remote Service replied-("+what+")", Toast.LENGTH_LONG).show();
        }
    }
}
