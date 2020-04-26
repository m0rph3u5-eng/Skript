package com.kmb.skript;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmb.skript.SMSData;

/**
 * Main Activity. Displays a list of numbers.
 */
public class DisplaySingle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_single);

        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get message value from intent
        String number = intent.getStringExtra("name");
        String body = intent.getStringExtra("body");
        //String date1 = intent.getStringExtra("date");
        Long iv = intent.getLongExtra("date",0);
        // get action bar
        ActionBar actionBar = getActionBar();
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        // set the icons and titles
        if (number.equals("MPESA")) {
            actionBar.setIcon(R.drawable.m_pesa_act);
            actionBar.setTitle(null);
            //actionBar.setSubtitle(date);
        } else if (number.toUpperCase().equals("SAFARICOM")) {
            actionBar.setIcon(R.drawable.saf_action);
            actionBar.setTitle(null);
            //actionBar.setSubtitle(date);
        } else {
            actionBar.setIcon(R.drawable.s_icon_inv);
            String numform = PhoneNumberUtils.formatNumber(number,"+254");
            actionBar.setTitle(numform);
            //actionBar.setSubtitle(date);
        }
        TextView message = (TextView)findViewById(R.id.textView4);
        TextView date = (TextView)findViewById(R.id.textView11);

        Date d = new Date(iv);
        //SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy,HH:mm");
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        f.setTimeZone(TimeZone.getDefault());
        String s = f.format(d);
        date.setText(s);
        message.setText(body);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display_single, menu);

        return super.onCreateOptionsMenu(menu);
    }
    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_clipboard:
                clipboard ();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //@Override
    public void clipboard(){
        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get message value from intent
        String body = intent.getStringExtra("body");

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("SkriptMessageView",body);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Copied to CLIPBOARD", Toast.LENGTH_LONG).show();
    }

}





