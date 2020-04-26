package com.kmb.skript;

import android.app.ActionBar;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


/**
 * List adapter for storing SMS data
 */
public class ListAdapter2 extends ArrayAdapter<SMSData> {

    // List context
    private final Context context;
    // List values
    private final List<SMSData> list;

    public ListAdapter2(Context context, List<SMSData> list) {
        super(context, R.layout.m_pesa, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  rowView = inflater.inflate(R.layout.m_pesa, parent, false);

            TextView details = (TextView) rowView.findViewById(R.id.textView6);
            TextView in = (TextView) rowView.findViewById(R.id.textView7);
            TextView out = (TextView) rowView.findViewById(R.id.textView9);
            TextView balance = (TextView) rowView.findViewById(R.id.textView8);
            RelativeLayout trRow = (RelativeLayout) rowView.findViewById(R.id.trRow);

        if (list.get(position).getBody()== null) {
            trRow.setVisibility(View.GONE);
        }
        else {
            String[] tokens = list.get(position).getBody().split("[ \n.]+");

            //1.Buying airtime
            if (tokens[3].equals("bought")) {
                details.setText(Html.fromHtml(tokens[7].toUpperCase() + "<br />" + "<small>" + tokens[9] + "<br />" + tokens[11] + " " + tokens[12] + "</small>"));
                String[] bin1 = tokens[4].split("[Ksh]+");
                out.setText(bin1[1]);
                String[] bal1 = tokens[17].split("[Ksh]+");
                balance.setText(bal1[1]);
            }
             //2.Receive from user
            else if(tokens[4].equals("received")&&tokens[11].equals("on")) {
                details.setText(Html.fromHtml(tokens[8]+" "+tokens[9] + "<br />" + "<small>" + tokens[12] + "<br />" + tokens[14] + tokens[15] + "</small>"));
                String[] bin = tokens[5].split("[Ksh.]+");
                in.setText(bin[1]);
                String[] bal = tokens[20].split("[Ksh.]+");
                balance.setText(bal[1]);
            }
            //3.Withdraw from agent
            else if (tokens[7].toLowerCase().equals("withdraw")){
                details.setText(Html.fromHtml(tokens[12]+" "+tokens[13]+" "+tokens[14]+"<br />"+"<small>"+tokens[3]+"<br />"+tokens[5]+tokens[6]+"</small>"));
                String[] bin2 = tokens[8].split("[Ksh.]+");
                out.setText(bin2[1]);
                String[] bal2 = tokens[21].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            //4.Send to user
            else if (tokens[4].equals("sent")&&tokens[9].equals("on")){
                details.setText(Html.fromHtml(tokens[6]+" "+tokens[7]+"<br />"+"<small>"+tokens[10]+"<br />"+tokens[12]+tokens[13]+"</small>"));
                String[] bin2 = tokens[2].split("[Ksh.]+");
                out.setText(bin2[1]);
                String[] bal2 = tokens[18].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            //5.Send to Paybill account
            else if (tokens[4].equals("sent")){
                details.setText(Html.fromHtml(tokens[6]+" "+tokens[7]+" "+tokens[8]+"<br />"+"<small>"+tokens[13]+"<br />"+tokens[15]+tokens[16]+"</small>"));
                String[] bin2 = tokens[2].split("[Ksh.]+");
                out.setText(bin2[1]);
                String[] bal2 = tokens[21].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            //6.Deposit into account
            else if (tokens[7].toLowerCase().equals("give")){
                details.setText(Html.fromHtml(tokens[12]+" "+tokens[13]+" "+tokens[14]+"<br />"+"<small>"+tokens[3]+"<br />"+tokens[5]+tokens[6]+"</small>"));
                String[] bin2 = tokens[8].split("[Ksh.]+");
                in.setText(bin2[1]);
                String[] bal2 = tokens[21].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            //7.Error
            else if (tokens[0].equals("Failed")){
                details.setText("Failed Transaction");
                //String[] bin2 = tokens[2].split("[Ksh.]+");
                //in.setText("-");
                //out.setText("-");
                //String[] bal2 = tokens[20].split("[Ksh.]+");
                //balance.setText("-");
            }
            //8.Reversal
            else if (tokens[2].equals("Transaction")){
                details.setText("Reversed Transaction");
                //String[] bin2 = tokens[2].split("[Ksh.]+");
                //out.setText(bin2[1]);
                String[] bal2 = tokens[12].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            //9.Deposit into account from bank account
            else if (tokens[4].equals("received")){
                details.setText(Html.fromHtml(tokens[9]+" "+tokens[10]+" "+tokens[11]+"<br />"+"<small>"+tokens[14]+"<br />"+tokens[16]+tokens[17]+"</small>"));
                String[] bin2 = tokens[5].split("[Ksh.]+");
                in.setText(bin2[1]);
                String[] bal2 = tokens[22].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            //10.Sent to M-Shwari
            else if (tokens[6].equals("M-Shwari")){
                details.setText(Html.fromHtml("M-Shwari"+"<br />"+"<small>"+tokens[9]+"<br />"+tokens[11]+tokens[12]+"</small>"));
                String[] bin2 = tokens[2].split("[Ksh.]+");
                out.setText(bin2[1]);
                String[] bal2 = tokens[16].split("[Ksh.]+");
                String[] bal3 = tokens[23].split("[Ksh.]+");
                balance.setText(Html.fromHtml("<small>"+"M-Pesa"+"</small>"+"<br />"+bal2[1]+"<small>"+"M-Shwari"+"</small>"+"<br />"+bal3[1]));
            }
            //11.Received from M-Shwari
            else if (tokens[9].equals("M-Shwari")){
                details.setText(Html.fromHtml("M-Shwari"+"<br />"+"<small>"+tokens[12]+"<br />"+tokens[14]+tokens[15]+"</small>"));
                String[] bin2 = tokens[5].split("[Ksh.]+");
                in.setText(bin2[1]);
                String[] bal2 = tokens[24].split("[Ksh.]+");
                String[] bal3 = tokens[19].split("[Ksh.]+");
                balance.setText(Html.fromHtml("<small>"+"M-Pesa"+"</small>"+"<br />"+bal2[1]+"<small>"+"M-Shwari"+"</small>"+"<br />"+bal3[1]));
            }
            //12.Refund
            else if (tokens[3].toLowerCase().equals("pay")){
                details.setText(Html.fromHtml(tokens[14]+" "+tokens[15]+"<br />"+"<small>"+"REFUND"+"</small>"));
                String[] bin2 = tokens[8].split("[Ksh.]+");
                in.setText(bin2[1]);
                String[] bal2 = tokens[29].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            //13.Buy goods
            else if (tokens[9].equals("received")){
                details.setText(Html.fromHtml(tokens[12]+" "+tokens[13]+"<br />"+"<small>"+tokens[3]+"<br />"+tokens[5]+tokens[6]+"</small>"));
                String[] bin2 = tokens[7].split("[Ksh.]+");
                in.setText(bin2[1]);
                String[] bal2 = tokens[18].split("[Ksh.]+");
                balance.setText(bal2[1]);
            }
            else {
                        details.setText("Format Unavailable");
            }
        }
       return rowView;
    }

}
