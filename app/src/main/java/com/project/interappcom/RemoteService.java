/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.project.interappcom;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author openmobster@gmail.com
 */
public class RemoteService extends Service
{
	public static final String TAG = "RemoteService";
	private Messenger messenger = new Messenger(new IncomingHandler()); //receives remote invocations

	private Messenger messenger_1001;
	private Messenger messenger_1002;





	@Override
	public IBinder onBind(Intent intent) 
	{
		Log.d(TAG, "onBind");

		Log.d(TAG, "" + intent.getPackage());

		if(intent.getPackage().equals("com.project.app2")){

		}else if(intent.getPackage().equals("com.project.interappcom")){

	}

//		if(this.messenger == null)
//		{
//			synchronized(this)
//			{
//				if(this.messenger == null)
//				{
//					this.messenger = new Messenger(new IncomingHandler());
//				}
//			}
//		}
		//Return the proper IBinder instance
		return this.messenger.getBinder();         //pass the handler assoicates with this process back to the sender process
	}
	
	private class IncomingHandler extends Handler
	{
		@Override
        public void handleMessage(Message msg) {
			System.out.println("*****************************************");
			System.out.println("Remote Service successfully invoked!!!!!!");
			System.out.println("*****************************************");

			int what = msg.what;
			Log.d(TAG, "what: " + what);
			//init message
			if ( what == 0 ) {
				Log.d(TAG, "sendingUid: " + msg.sendingUid);

				if ( msg.sendingUid == 10137 ) {
					Log.d(TAG, "" + "init sendingUid: " + msg.sendingUid );

					messenger_1001 = msg.replyTo;

				} else if ( msg.sendingUid == 10140 ) {
					Log.d(TAG, "" + "init sendingUid: " + msg.sendingUid );

					messenger_1002 = msg.replyTo;

				}
			} else {

				if(msg.sendingUid == 10137){
					//sent to 1002
					try {
						Thread.sleep(5000);
						messenger_1002.send(msg);
					} catch ( RemoteException | InterruptedException e ) {
						e.printStackTrace();
					}
				}
				else if(msg.sendingUid == 10140){
					try {
						Thread.sleep(5000);

						messenger_1001.send(msg);
					} catch ( RemoteException | InterruptedException e ) {
						e.printStackTrace();
					}

				}

				//Toast.makeText(RemoteService.this.getApplicationContext() , "Remote Service invoked-(" + what + ")" , Toast.LENGTH_LONG).show();

//				//Setup the reply message
//				Message message = Message.obtain(null , 2 , 0 , 0);
//				try {
//					//make the RPC invocation
//					Messenger replyTo = msg.replyTo;     //handler from the sender
//					//replyTo.send(message);
//
//				} catch ( RemoteException rme ) {
//					//Show an Error Message
//					Toast.makeText(RemoteService.this , "Invocation Failed!!" , Toast.LENGTH_LONG).show();
//				}
			}
		}
	}
}
