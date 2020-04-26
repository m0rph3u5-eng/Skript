package com.kmb.skript;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * List adapter for storing SMS data
 */
public class ListAdapter1 extends ArrayAdapter<SMSData>{
    // List context
    private final Context context;
    // List values
    private final List<SMSData> list;

    public ListAdapter1(Context context, List<SMSData> list) {
        super(context, R.layout.activity_display_message, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  rowView = inflater.inflate(R.layout.activity_display_message, parent, false);

            TextView messageRcv = (TextView) rowView.findViewById(R.id.textView15);
            TextView timeRcv = (TextView) rowView.findViewById(R.id.textView16);
            TextView timeSnt = (TextView) rowView.findViewById(R.id.textView18);
            TextView messageSnt = (TextView) rowView.findViewById(R.id.textView17);


        if (list.get(position).getBody() == null) {

            messageRcv.setVisibility(View.GONE);
            messageSnt.setVisibility(View.GONE);
            timeRcv.setVisibility(View.GONE);
            timeSnt.setVisibility(View.GONE);
        }

        if (list.get(position).getType() == 1) {
            messageRcv.setText(list.get(position).getBody());
            Date d = new Date(list.get(position).getDate());
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            f.setTimeZone(TimeZone.getDefault());
            String s = f.format(d);
            timeRcv.setText(s);
            messageSnt.setVisibility(View.GONE);
            timeSnt.setVisibility(View.GONE);
        } else {
            messageSnt.setText(list.get(position).getBody());
            Date d = new Date(list.get(position).getDate());
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            f.setTimeZone(TimeZone.getDefault());
            String s = f.format(d);
            timeSnt.setText(s);
            messageRcv.setVisibility(View.GONE);
            timeRcv.setVisibility(View.GONE);
        }
        return rowView;
    }
}
