package com.kmb.skript;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;


import android.app.ActionBar;
import android.app.ListActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import com.kmb.skript.SMSData;

/**
 * Main Activity. Displays a list of numbers.
 */
public class Home extends ListActivity {

    private Cursor cSMS;
    private Uri uriSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ActionBar
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.s_icon_inv);
        actionBar.setTitle(null);

        List<SMSData> smsList = new ArrayList<SMSData>();
        /*Uri*/ uriSMS = Uri.parse("content://sms");
        /*Cursor*/ cSMS = getContentResolver().query(uriSMS, null, null, null, null);

        // Read the sms data and store it in the list
        if (cSMS.moveToFirst()) {
            for (int i = 0; i < cSMS.getCount(); i++) {
                SMSData sms = new SMSData();
                sms.setThread_id(cSMS.getInt(cSMS.getColumnIndexOrThrow("thread_id")));
                sms.setBody(cSMS.getString(cSMS.getColumnIndexOrThrow("body")));
                if ((cSMS.getString(cSMS.getColumnIndexOrThrow("address")).substring(0,1).equals("0"))){
                    sms.setNumber(cSMS.getString(cSMS.getColumnIndexOrThrow("address")).replaceFirst("0","+254"));
                }else{
                    sms.setNumber(cSMS.getString(cSMS.getColumnIndexOrThrow("address")));
                }
                sms.setDate(cSMS.getLong(cSMS.getColumnIndexOrThrow("date")));
                smsList.add(sms);
                cSMS.moveToNext();
            }
        }

        if (cSMS.getCount()==0){
            setListAdapter(null);
            actionBar.setTitle("Skript");
            actionBar.setSubtitle("No Messages");
            cSMS.close();
        }else {
            cSMS.close();
            getListView().setDivider(null);
            // Set smsList in the ListAdapter
            setListAdapter(new ListAdapter(this, smsList));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMSData sms = (SMSData)getListAdapter().getItem(position);
        if (sms.getNumber()==null){
            Intent intentD = new Intent(this, DisplayMessage.class);
            intentD.putExtra("number", "----DRAFT----");
            intentD.putExtra("body",sms.getBody());
            startActivity(intentD);

        }else {
            switch (sms.getNumber()) {
                case "MPESA":
                    Intent intentM = new Intent(this, mPesa.class);
                    startActivity(intentM);
                    break;
                case "Safaricom":
                    Intent intentS = new Intent(this, DisplayMessage.class);
                    intentS.putExtra("number", sms.getNumber());
                    intentS.putExtra("body",sms.getBody());
                    startActivity(intentS);
                    break;
                default:
                    Intent intent = new Intent(this, DisplayMessage.class);
                    intent.putExtra("number", sms.getNumber());
                    intent.putExtra("body",sms.getBody());
                    startActivity(intent);

            }
        }
    }
    @Override
    protected final void onResume (){
        super.onResume();
        uriSMS = Uri.parse("content://sms");
        cSMS = getContentResolver().query(uriSMS, null, null, null, null);
    }

}

