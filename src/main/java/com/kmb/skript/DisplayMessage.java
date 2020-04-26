package com.kmb.skript;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kmb.skript.SMSData;


/**
 * Main Activity. Displays a list of numbers.
 */
public class DisplayMessage extends ListActivity {

    private Cursor cSMS;
    private Uri uriSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get message value from intent
        String number = intent.getStringExtra("number");
        String body = intent.getStringExtra("body");
        //ActionBar
        ActionBar actionBar = getActionBar();
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Title and Icon
        if (number.toUpperCase().equals("SAFARICOM")) {
            actionBar.setIcon(R.drawable.saf_action);
            actionBar.setTitle(null);
        } else {
            actionBar.setIcon(R.drawable.s_icon_inv);
            String numform = PhoneNumberUtils.formatNumber(number,"+254");
            actionBar.setTitle(numform);        }

        List<SMSData> list = new ArrayList<SMSData>();

        /*Uri*/ uriSMS = Uri.parse("content://sms");
        /*Cursor*/ cSMS = getContentResolver().query(uriSMS, null, null, null, "date asc");

        // Read the sms data and store it in the list
        if (cSMS.moveToFirst()) {
            for (int i = 0; i < cSMS.getCount(); i++) {
                SMSData sms = new SMSData();
                if (cSMS.getString(cSMS.getColumnIndexOrThrow("address")) == null) {
                    sms.setNumber(null);
                    sms.setBody(body);
                } else {
                    if (cSMS.getString(cSMS.getColumnIndexOrThrow("address")).equals(number)||cSMS.getString(cSMS.getColumnIndexOrThrow("address")).replaceFirst("0", "+254").equals(number)) {
                        sms.setBody(cSMS.getString(cSMS.getColumnIndexOrThrow("body")));
                        sms.setType(cSMS.getInt(cSMS.getColumnIndexOrThrow("type")));
                        sms.setDate(cSMS.getLong(cSMS.getColumnIndexOrThrow("date")));
                        //sms.setNumber(cSMS.getString(cSMS.getColumnIndexOrThrow("address")));
                        if ((cSMS.getString(cSMS.getColumnIndexOrThrow("address")).substring(0,1).equals("0"))){
                            sms.setNumber(cSMS.getString(cSMS.getColumnIndexOrThrow("address")).replaceFirst("0","+254"));
                        }else{
                            sms.setNumber(cSMS.getString(cSMS.getColumnIndexOrThrow("address")));
                        }
                        //sms.setId(cSMS.getString(cSMS.getColumnIndexOrThrow("_id")));
                    }
                    list.add(sms);
                    cSMS.moveToNext();
                }
            }
        }

        //Remove dividers
        getListView().setDivider(null);
        getListView().setSmoothScrollbarEnabled(true);
        getListView().setFastScrollEnabled(true);
        // Set smsList in the ListAdapter
        setListAdapter(new ListAdapter1(this, list));
        getListView().setSelection(cSMS.getCount());
        cSMS.close();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMSData sms = (SMSData) getListAdapter().getItem(position);
        Intent intentK = new Intent(this, DisplaySingle.class);
        //intentK.putExtra("_id", sms.getId());
        intentK.putExtra("name", sms.getNumber());
        intentK.putExtra("date", sms.getDate());
        intentK.putExtra("body",sms.getBody());
        startActivity(intentK);
    }
    @Override
    protected final void onResume (){
        super.onResume();
        uriSMS = Uri.parse("content://sms");
        cSMS = getContentResolver().query(uriSMS, null, null, null, "date asc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display_message, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_screenshot:
               screenshot();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void screenshot(){
        try {
            //View u = findViewById(R.id.rlay);
            View u = getListView().findFocus();
            u.setDrawingCacheEnabled(true);
            u.buildDrawingCache(true);
            Bitmap bitmap1 = Bitmap.createBitmap(u.getDrawingCache());
            u.setDrawingCacheEnabled(false);

            Intent intent = getIntent();
            String number = intent.getStringExtra("number");

            File file, outputFile;
            file = new File(android.os.Environment.getExternalStorageDirectory(), "Skript");
            String path = file.getAbsolutePath() + File.separator + number+" "+ UUID.randomUUID().getLeastSignificantBits() + ".jpg";
            outputFile = new File(path);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.close();

            Toast.makeText(getApplicationContext(), "SCREENSHOT SAVED in Skript FOLDER", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}





