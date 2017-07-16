package com.androidhive.flashlight;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.content.IntentFilter;

/**
 * Created by lucadalseno on 25/06/17.
 */

public class MessageInService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    private SMSreceiver mSMSreceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate()
    {
        super.onCreate();

        //SMS event receiver
        mSMSreceiver = new SMSreceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
    }


    private class SMSreceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle extras = intent.getExtras();

            String messageText = "";

            if ( extras != null )
            {
                Object[] smsextras = (Object[]) extras.get( "pdus" );

                for ( int i = 0; i < smsextras.length; i++ )
                {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])smsextras[i]);

                    String messageBody = smsMessage.getMessageBody().toString();
                    String messageSource = smsMessage.getOriginatingAddress();

                    messageText += "SMS from " + messageSource + " : " + messageBody;

                    Log.i("Message Info", messageText);
                }

            }

        }

    }
}
