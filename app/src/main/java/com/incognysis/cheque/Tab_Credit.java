package com.incognysis.cheque;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
/**
 * Created by Charul on 25-12-2017.
 */

public class Tab_Credit extends Fragment {
    List<cheque> mylist;
    EditText givento,amount,chequeno,notes,takenfrom;
    public static EditText issued_date,entry_date;
    Spinner type,bank,status;
    SwitchCompat reminder;
    Button save;
    TextView id;
    DatabaseHandler db;
    ListView listView;
    AppAdapter adapter;
    String sp1,sp2,sp3,rem_status,given_or_taken;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_credit, container,false);

        id=(TextView) v.findViewById(R.id.text_id);
        givento=(EditText) v.findViewById(R.id.givenOrTaken_Edit_text1);
        takenfrom=(EditText) v.findViewById(R.id.givenOrTaken_Edit_text2);
        amount=(EditText) v.findViewById(R.id.amount_Edit_Text);
        issued_date=(EditText) v.findViewById(R.id.date_issued_edit_text);
        entry_date=(EditText) v.findViewById(R.id.entry_date_edit_text);
        chequeno=(EditText) v.findViewById(R.id.cheque_no_edit_text);
        notes=(EditText) v.findViewById(R.id.notes_edit_text);
        type=(Spinner) v.findViewById(R.id.type_spinner);
        bank=(Spinner) v.findViewById(R.id.bank_spinner);
        status=(Spinner) v.findViewById(R.id.status_spinner);
        reminder=(SwitchCompat) v.findViewById(R.id.switch_toggle);
        save=(Button) v.findViewById(R.id.save);
        db= new DatabaseHandler(v.getContext());
        listView=(ListView) v.findViewById(R.id.credit_list_view);

        load();

        return v;
    }
    public void load() {



        List<cheque> list = db.getAllCheques();
        mylist = list;
        adapter = new AppAdapter();
        listView.setAdapter(adapter);



    }
     class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mylist.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity().getApplicationContext(),
                        R.layout.list_item, null);
                new ViewHolder(convertView);
            }


            final ViewHolder holder = (ViewHolder) convertView.getTag();



   //         if(type.equalsIgnoreCase("credit")) {

                holder.tv_id.setText(mylist.get(position).getId() + "");
                holder.tv_name.setText(mylist.get(position).get_givenTo());
                holder.tv_chq_no.setText(mylist.get(position).get_chequeNo());
                holder.tv_dep_date.setText(mylist.get(position).get_entry_date());
                holder.tv_amt.setText(mylist.get(position).get_amount());

        //    }
//            else{
//
//                holder.tv_id.setText(mylist.get(position).getId() + "");
//                holder.tv_name.setText(mylist.get(position).get_givenTo());
//                holder.tv_chq_no.setText(mylist.get(position).get_chequeNo());
//                holder.tv_dep_date.setText(mylist.get(position).get_entry_date());
//                holder.tv_amt.setText(mylist.get(position).get_amount());
//
//
//            }



            //holder.tv_time.setText(mylist.get(position).getDateTime().toString());

            //  This code is working but temporary disables to try something

            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//
                    // Setting Dialog Title
                    alertDialog.setTitle("Details");

                    String alert1 = "Name: " + mylist.get(position).get_givenTo();
                    String alert2 = "Amount: " + mylist.get(position).get_amount();
                    String alert3 = "Bank Name: " + mylist.get(position).get_bank();
                    String alert4 = "Cheque Number: " + mylist.get(position).get_chequeNo();
                    String alert5 = "Status: " + mylist.get(position).get_status();
                    String alert6 = "Deposited Date: " + mylist.get(position).get_entry_date();
                    String alert7 = "Notes: " + mylist.get(position).get_bank();
                    alertDialog.setMessage(alert1 +"\n"+ alert2 +"\n"+ alert3 +"\n"+alert4 +"\n"+alert5 +"\n"+alert6 +"\n"+alert7);

                    // Setting Dialog Message
//                    alertDialog.setMessage(mylist.get(position).get_givenTo());
//                    alertDialog.setMessage(mylist.get(position).get_amount());
//                    alertDialog.setMessage(mylist.get(position).get_bank());
//                    alertDialog.setMessage(mylist.get(position).get_chequeNo());
                 //   alertDialog.setMessage(mylist.get(position).get_entry_date());



                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            Toast.makeText(alertDialog.getContext(),"CHecking",Toast.LENGTH_SHORT).show();


                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                    // Setting Dialog Title
                    alertDialog.setTitle("Confirm Delete...");

                    // Setting Dialog Message
                    alertDialog.setMessage("Are you sure you want delete this?");

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            db.deleteNote(new cheque(mylist.get(position).getId(), mylist.get(position).get_type(), mylist.get(position).get_bank(),mylist.get(position).get_givenTo(),
                            mylist.get(position).get_entry_date(),mylist.get(position).get_issue_date(),
                                    mylist.get(position).get_amount(),mylist.get(position).get_chequeNo(),
                                    mylist.get(position).get_status(),mylist.get(position).get_notes(),
                                    mylist.get(position).get_reminder()));
                            load();
                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();

                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event

                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();


                        }
                    });

                    // Showing Alert Message
                    AlertDialog alertDialog1= alertDialog.create();
                    alertDialog1.show();

                }
            });


//            holder.row.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
//
//                    // Setting Dialog Title
//                    alertDialog.setTitle("Confirm Delete...");
//
//                    // Setting Dialog Message
//                    alertDialog.setMessage("Are you sure you want delete this?");
//
//                    // Setting Icon to Dialog
//                    //alertDialog.setIcon(R.drawable.delete);
//
//                    // Setting Positive "Yes" Button
//                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            // Write your code here to invoke YES event
//                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
//                            db.deleteNote(new note(mylist.get(position).get_id(), mylist.get(position).get_title(), mylist.get(position).get_note()));
//                            load();
//                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                    // Setting Negative "NO" Button
//                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Write your code here to invoke NO event
//
//                            dialog.cancel();
//                        }
//                    });
//
//                    // Showing Alert Message
//                    alertDialog.show();
//
//
//                    return true;
//                }
//            });


            return convertView;

        }


        class ViewHolder {
            TextView tv_id;
            TextView tv_name;
            TextView tv_chq_no;
            TextView tv_dep_date;
            TextView tv_amt;
            RelativeLayout row;
          //  LinearLayout row_c;


            public ViewHolder(View view) {
                tv_id = (TextView) view.findViewById(R.id.id);
                tv_name = (TextView) view.findViewById(R.id.name);
                tv_chq_no = (TextView) view.findViewById(R.id.chq_no);
                tv_dep_date = (TextView) view.findViewById(R.id.d_date);
                tv_amt = (TextView) view.findViewById(R.id.amt);
                row = (RelativeLayout) view.findViewById(R.id.row);
               // row_c=(LinearLayout) view.findViewById(R.id.row_credit);
                view.setTag(this);
            }
        }
    }
}
