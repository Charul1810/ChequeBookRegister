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

            holder.tv_id.setText(mylist.get(position).getId() + "");
            holder.tv_name.setText(mylist.get(position).get_givenTo());
            holder.tv_chq_no.setText(mylist.get(position).get_chequeNo());
            holder.tv_dep_date.setText(mylist.get(position).get_entry_date());
            holder.tv_amt.setText(mylist.get(position).get_amount());



            //holder.tv_time.setText(mylist.get(position).getDateTime().toString());

            //  This code is working but temporary disables to try something

//            holder.row.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent i = new Intent(getActivity().getApplicationContext(), ViewNote.class);
//                    i.putExtra("id", mylist.get(position).get_id() + "");
//                    i.putExtra("title", mylist.get(position).get_title().toString());
//                    i.putExtra("note", mylist.get(position).get_note().toString());
//                    i.putExtra("time", mylist.get(position).get_time().toString());
//                    startActivity(i);
//
//                }
//            });


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
//                            // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
//                            dialog.cancel();
//                        }
//                    });
//
//                    // Showing Alert Message
//                    alertDialog.show();
//
//                    //Toast.makeText(getApplicationContext(),mylist.get(position).get_id() + "",Toast.LENGTH_LONG).show();
////                    txtid.setText(mylist.get(position).get_id() + "");
////                    txttitle.setText(mylist.get(position).get_title());
////                    txtnote.setText(mylist.get(position).get_note());
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
            LinearLayout row;


            public ViewHolder(View view) {
                tv_id = (TextView) view.findViewById(R.id.id);
                tv_name = (TextView) view.findViewById(R.id.name);
                tv_chq_no = (TextView) view.findViewById(R.id.chq_no);
                tv_dep_date = (TextView) view.findViewById(R.id.d_date);
                tv_amt = (TextView) view.findViewById(R.id.amt);
                row = (LinearLayout) view.findViewById(R.id.row);
                view.setTag(this);
            }
        }
    }
}
