package com.incognysis.cheque;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;

public class Add_activity extends AppCompatActivity {

    EditText givento,amount,chequeno,notes,takenfrom;
    public static EditText issued_date,entry_date;
    Spinner type,bank,status;
    SwitchCompat reminder;
    Button save;
    TextView id;
    DatabaseHandler db;
    String sp1,sp2,sp3,rem_status,given_or_taken;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        id=(TextView) findViewById(R.id.text_id);
        givento=(EditText) findViewById(R.id.givenOrTaken_Edit_text1);
        takenfrom=(EditText) findViewById(R.id.givenOrTaken_Edit_text2);
        amount=(EditText) findViewById(R.id.amount_Edit_Text);
      issued_date=(EditText) findViewById(R.id.date_issued_edit_text);
      entry_date=(EditText) findViewById(R.id.entry_date_edit_text);
        chequeno=(EditText) findViewById(R.id.cheque_no_edit_text);
        notes=(EditText) findViewById(R.id.notes_edit_text);
        type=(Spinner) findViewById(R.id.type_spinner);
        bank=(Spinner) findViewById(R.id.bank_spinner);
        status=(Spinner) findViewById(R.id.status_spinner);
        reminder=(SwitchCompat) findViewById(R.id.switch_toggle);
        save=(Button) findViewById(R.id.save);
        db= new DatabaseHandler(this);

//        sp1=String.valueOf(type.getSelectedItem());
//        sp2=String.valueOf(bank.getSelectedItem());
//        sp3=String.valueOf(status.getSelectedItem());
       // cdate=Calendar.getInstance();

        amount.setText("₹ ");
        Selection.setSelection(amount.getText(), amount.getText().length());


        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith("₹ ")){
                    amount.setText("₹ ");
                    Selection.setSelection(amount.getText(), amount.getText().length());

                }

            }
        });


        ////set the switch to ON
        reminder.setChecked(false);

////attach a listener to check for changes in state
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if(isChecked) {
                        rem_status = "true"; //edit here
                    }
                    else
                    {

                        rem_status="false";
                    }


            }

        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                 sp1 = parent.getItemAtPosition(pos).toString();
                    if(sp1.equalsIgnoreCase("credit"))
                    {
                        givento.setVisibility(View.GONE);
                        takenfrom.setVisibility(View.VISIBLE);
                        given_or_taken=takenfrom.getText().toString();
                    }
                    else if(sp1.equalsIgnoreCase("debit")) {

                        givento.setVisibility(View.VISIBLE);
                        takenfrom.setVisibility(View.GONE);
                        given_or_taken=givento.getText().toString();
                    }

                // make insertion into database
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                sp2 = parent.getItemAtPosition(pos).toString();
                // make insertion into database
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                sp3 = parent.getItemAtPosition(pos).toString();
                // make insertion into database
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        issued_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        entry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragments();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(getApplicationContext(),"Button pressed",Toast.LENGTH_SHORT).show();
                    db.add_cheque(new cheque(sp1, sp2, given_or_taken, entry_date.getText().toString(), issued_date.getText().toString(), amount.getText().toString(),
                            chequeno.getText().toString(), sp3, notes.getText().toString(), rem_status));

                    Toast.makeText(getApplicationContext(), givento.getText().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

    }



    @SuppressLint("ValidFragment")
    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            issued_date.setText(day+"/"+month+"/"+year);


        }

    }

    @SuppressLint("ValidFragment")
    public static class SelectDateFragments extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {

            entry_date.setText(day+"/"+month+"/"+year);

        }

    }





}




