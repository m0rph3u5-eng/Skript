package com.kmb.skript;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kmb.skript.SMSData;

import static java.lang.System.out;


/**
 * List adapter for storing SMS data
 */
public class ListAdapter extends ArrayAdapter<SMSData> {

    // List context
    private final Context context;
    // List values
    private final List<SMSData> smsList;

    public ListAdapter(Context context, List<SMSData> smsList) {
        super(context, R.layout.activity_home, smsList);

            this.context=context;
            this.smsList=smsList;
        }


static class ViewHolder{
    TextView senderNumber;
    TextView messageShort;
    TextView messageDate;
    ImageView avi;
}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View  rowView = inflater.inflate(R.layout.activity_home, parent, false);
            ViewHolder holder;
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.activity_home, null);
                holder = new ViewHolder();
                holder.senderNumber = (TextView) convertView.findViewById(R.id.textView);
                holder.messageShort = (TextView) convertView.findViewById(R.id.textView2);
                holder.messageDate = (TextView) convertView.findViewById(R.id.textView3);
                holder.avi = (ImageView) convertView.findViewById(R.id.imageView2);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
                //convertView = null;
            }
        String num = smsList.get(position).getNumber();
        String numform = PhoneNumberUtils.formatNumber(num,"+254");
        holder.senderNumber.setText(numform);
        //int thr = smsList.get(position).getThread_id();
        //String gh = " "+thr;
        //holder.messageShort.setText(gh);
        holder.messageShort.setText(smsList.get(position).getBody());

        if(smsList.get(position).getNumber()== null) {
        holder.senderNumber.setText("----DRAFT----");
        }

    /** Date d = new Date(smsList.get(position).getDate());
        //SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy,HH:mm");
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        f.setTimeZone(TimeZone.getDefault());
        String s = f.format(d);
        holder.messageDate.setText(s);
    */

        // Set up time display format
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(smsList.get(position).getDate());
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat dateFormat = null;
        if(date.get(Calendar.YEAR)==curDate.get(Calendar.YEAR)){
            if(date.get(Calendar.DAY_OF_YEAR) == curDate.get(Calendar.DAY_OF_YEAR) ){
                dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            }
            else{
                dateFormat = new SimpleDateFormat("MMM d", Locale.US);
            }
        }
        else{
            dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        }
        holder.messageDate.setText(dateFormat.format(smsList.get(position).getDate()));

            String number = holder.senderNumber.getText().toString();
            switch (number.toUpperCase()) {
                case "MPESA":
                    holder.avi.setImageResource(R.drawable.m_pesa);
                    break;
                case "SAFARICOM":
                    holder.avi.setImageResource(R.drawable.safcom);
                    break;
                default:
                    holder.avi.setImageResource(R.drawable.avi);
            }
        return convertView;
    }

}
