package com.kmb.skript;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kmb.skript.SMSData;

/**
 * Main Activity. Displays a list of numbers.
 */
public class mPesa extends ListActivity {

    private Uri uriSMS;
    private Cursor cSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get action bar
        ActionBar actionBar = getActionBar();
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        // set the icon
        actionBar.setIcon(R.drawable.m_pesa_act);
        actionBar.setTitle(null);

        List<SMSData> list = new ArrayList<SMSData>();
        /*Uri*/ uriSMS = Uri.parse("content://sms/inbox");
        /*Cursor*/ cSMS = getContentResolver().query(uriSMS, null, null, null, "date asc");

        // Read the sms data and store it in the list
        if (cSMS.moveToFirst()) {
            for (int i = 0; i < cSMS.getCount(); i++) {
                SMSData sms = new SMSData();
                if (cSMS.getString(cSMS.getColumnIndexOrThrow("address")).equals("MPESA")) {
                    sms.setBody(cSMS.getString(cSMS.getColumnIndexOrThrow("body")));
                    //sms.setId(cSMS.getString(cSMS.getColumnIndexOrThrow("_id")));
                    sms.setDate(cSMS.getLong(cSMS.getColumnIndexOrThrow("date")));
                }
                list.add(sms);
                cSMS.moveToNext();
            }
        }

        //Remove Divider
        getListView().setDivider(null);

        // Set smsList in the ListAdapter
        setListAdapter(new ListAdapter2(this, list));
        getListView().setSelection(cSMS.getCount());
        cSMS.close();

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMSData sms = (SMSData)getListAdapter().getItem(position);
            Intent intentK = new Intent(this, DisplaySingle.class);
            //intentK.putExtra("_id",sms.getId());
            intentK.putExtra("name","MPESA");
            intentK.putExtra("body",sms.getBody());
            intentK.putExtra("date", sms.getDate());
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
            //View u = findViewById(R.id.tlay);
            View u = getListView().findFocus();
            u.setDrawingCacheEnabled(true);
            u.buildDrawingCache(true);
            Bitmap bitmap1 = Bitmap.createBitmap(u.getDrawingCache());
            u.setDrawingCacheEnabled(false);

            File file, outputFile;
            file = new File(android.os.Environment.getExternalStorageDirectory(), "Skript");
            String path = file.getAbsolutePath() + File.separator + "MPESA"+" "+ UUID.randomUUID().getLeastSignificantBits() + ".jpg";
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






