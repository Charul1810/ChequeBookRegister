package com.incognysis.cheque;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Charul on 25-12-2017.
 */

public class Tab_Debit extends Fragment {

    public List<cheque> mylist;
    EditText givento, amount, chequeno, notes, takenfrom;
    public static EditText issued_date, entry_date;
    Spinner type, bank, status;
    SwitchCompat reminder;
    Button save;
    TextView id;
    DatabaseHandler db;
    ListView listView;
    AppAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_debit, container, false);
        setHasOptionsMenu(true);
        id = (TextView) v.findViewById(R.id.text_id);
        givento = (EditText) v.findViewById(R.id.givenOrTaken_Edit_text1);
        takenfrom = (EditText) v.findViewById(R.id.givenOrTaken_Edit_text2);
        amount = (EditText) v.findViewById(R.id.amount_Edit_Text);
        issued_date = (EditText) v.findViewById(R.id.date_issued_edit_text);
        entry_date = (EditText) v.findViewById(R.id.entry_date_edit_text);
        chequeno = (EditText) v.findViewById(R.id.cheque_no_edit_text);
        notes = (EditText) v.findViewById(R.id.notes_edit_text);
        type = (Spinner) v.findViewById(R.id.type_spinner);
        bank = (Spinner) v.findViewById(R.id.bank_spinner);
        status = (Spinner) v.findViewById(R.id.status_spinner);
        reminder = (SwitchCompat) v.findViewById(R.id.switch_toggle);
        save = (Button) v.findViewById(R.id.save);
        db = new DatabaseHandler(v.getContext());
        listView = (ListView) v.findViewById(R.id.debit_list_view);

        load();

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        CharSequence sorting[]= new CharSequence[]{"Alphabet","Date"};
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            Toast.makeText(getActivity(),"Filter",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder UnitSelection = new AlertDialog.Builder(getActivity());
            UnitSelection.setTitle("Sort By");
            UnitSelection.setItems(sorting, new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(i==0)
                    {
                        mylist.sort(new Comparator<cheque>() {
                            @Override
                            public int compare(cheque o1, cheque o2) {
                                return o1.get_givenTo().toString().compareTo(o2.get_givenTo().toString());
                            }
                        }
                        );
                        adapter.notifyDataSetChanged();
                        //Toast.makeText(getApplicationContext(),"By alpa",Toast.LENGTH_SHORT).show();
                    }

                    if(i==1)
                    {
                        load();
                        //Toast.makeText(getApplicationContext(),"By alpa",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AlertDialog alert = UnitSelection.create();
            alert.show();
            return true;
        }




        return super.onOptionsItemSelected(item);
    }

    public void load() {

        List<cheque> list = db.getAllDebitCheques();
        mylist = list;
        adapter = new AppAdapter();
        Collections.reverse(mylist);
        listView.setAdapter(adapter);


    }

    //
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


            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

//
                    // Setting Dialog Title
                    alertDialog.setTitle("Details");

                    String alert1 = "Name : " + mylist.get(position).get_givenTo();
                    String alert2 = "Amount : " + mylist.get(position).get_amount();
                    String alert3 = "Bank Name : " + mylist.get(position).get_bank();
                    String alert4 = "Cheque Number : " + mylist.get(position).get_chequeNo();
                    String alert5 = "Status : " + mylist.get(position).get_status();
                    String alert6 = "Deposited Date : " + mylist.get(position).get_entry_date();
                    String alert7 = "Notes : " + mylist.get(position).get_notes();
                    alertDialog.setMessage(alert1 + "\n\n" + alert2 + "\n\n" + alert3 + "\n\n" + alert4 + "\n\n" + alert5 + "\n\n" + alert6 + "\n\n" + alert7);


                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.ic_info_outline_black_24dp);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(getContext(), Update.class);
                            i.putExtra("id", mylist.get(position).getId()+"");
                            i.putExtra("type", mylist.get(position).get_type());
                            i.putExtra("bank", mylist.get(position).get_bank());
                            i.putExtra("takenfrom", mylist.get(position).get_givenTo());
                            i.putExtra("e_date", mylist.get(position).get_entry_date());
                            i.putExtra("i_date", mylist.get(position).get_issue_date());
                            i.putExtra("amount", mylist.get(position).get_amount());
                            i.putExtra("chequeNo", mylist.get(position).get_chequeNo());
                            i.putExtra("status", mylist.get(position).get_status());
                            i.putExtra("notes", mylist.get(position).get_notes());
                            i.putExtra("reminder", mylist.get(position).get_reminder());

                            startActivity(i);

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            Toast.makeText(alertDialog.getContext(), "Checking", Toast.LENGTH_SHORT).show();


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
                                    db.deleteNote(new cheque(mylist.get(position).getId(), mylist.get(position).get_type(), mylist.get(position).get_bank(), mylist.get(position).get_givenTo(),
                                            mylist.get(position).get_entry_date(), mylist.get(position).get_issue_date(),
                                            mylist.get(position).get_amount(), mylist.get(position).get_chequeNo(),
                                            mylist.get(position).get_status(), mylist.get(position).get_notes(),
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
                    AlertDialog alertDialog1 = alertDialog.create();
                    alertDialog1.show();
                    TextView textView = ((TextView) alertDialog1.findViewById(android.R.id.message));
                    textView.setTextColor(Color.BLACK);
                    textView.setTextAppearance(R.style.TextAppearance_AppCompat_Title);
                    textView.setTextSize(16);
                    //  textView.setGravity(Gravity.CENTER);
                }
            });


            return convertView;

        }


        class ViewHolder {
            TextView tv_id;
            TextView tv_name;
            TextView tv_chq_no;
            TextView tv_dep_date;
            TextView tv_amt;
            RelativeLayout row;


            public ViewHolder(View view) {
                tv_id = (TextView) view.findViewById(R.id.id);
                tv_name = (TextView) view.findViewById(R.id.name);
                tv_chq_no = (TextView) view.findViewById(R.id.chq_no);
                tv_dep_date = (TextView) view.findViewById(R.id.d_date);
                tv_amt = (TextView) view.findViewById(R.id.amt);
                row = (RelativeLayout) view.findViewById(R.id.row);
                view.setTag(this);
            }
        }
    }
}
