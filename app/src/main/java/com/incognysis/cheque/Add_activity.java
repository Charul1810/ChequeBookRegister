package com.incognysis.cheque;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class Add_activity extends AppCompatActivity {

    EditText givento, amount, chequeno, notes, takenfrom, bank;
    public static EditText issued_date, entry_date;
    Spinner type, status;
    SwitchCompat reminder;
    Button save;
    TextView id;
    DatabaseHandler db;
    String sp1, sp2, sp3, rem_status, given_or_taken;
    TextInputLayout textInputLayout1, textInputLayout2, textInputLayout;
    String table;
    List<String> list;
    String selectedItem = "", strEditText;
    private boolean doubleBackToExitPressedOnce;
    public static final String mypreference = "mypref";

    public static final String TAG_NAME = "nameKey";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        id = (TextView) findViewById(R.id.text_id);
        givento = (EditText) findViewById(R.id.givenOrTaken_Edit_text1);
        takenfrom = (EditText) findViewById(R.id.givenOrTaken_Edit_text2);
        amount = (EditText) findViewById(R.id.amount_Edit_Text);
        issued_date = (EditText) findViewById(R.id.date_issued_edit_text);
        entry_date = (EditText) findViewById(R.id.entry_date_edit_text);
        chequeno = (EditText) findViewById(R.id.cheque_no_edit_text);
        notes = (EditText) findViewById(R.id.notes_edit_text);
        type = (Spinner) findViewById(R.id.type_spinner);
        bank = (EditText) findViewById(R.id.bank_edit_text);
        //bank=(Spinner) findViewById(R.id.bank_spinner);
        status = (Spinner) findViewById(R.id.status_spinner);
        reminder = (SwitchCompat) findViewById(R.id.switch_toggle);
        save = (Button) findViewById(R.id.save);
        db = new DatabaseHandler(this);
        textInputLayout = (TextInputLayout) findViewById(R.id.bank_text);
        textInputLayout1 = (TextInputLayout) findViewById(R.id.text_input_layout1);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.text_input_layout2);


        //for type spinner
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.type)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        type.setAdapter(spinnerArrayAdapter1);

        //for bank spinner
//        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item,
//                        getResources().getStringArray(R.array.bank_names)); //selected item will look like a spinner set from XML
//        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);
//        bank.setAdapter(spinnerArrayAdapter2);

        //for status spinner
        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.status)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        status.setAdapter(spinnerArrayAdapter3);


//        amount.setText("₹ ");
//        Selection.setSelection(amount.getText(), amount.getText().length());
//
//
//        amount.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(!s.toString().startsWith("₹ ")){
//                    amount.setText("₹ ");
//                    Selection.setSelection(amount.getText(), amount.getText().length());
//
//                }
//
//            }
//        });


        ////set the switch to ON
        reminder.setChecked(false);

////attach a listener to check for changes in state
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rem_status = "true"; //edit here
                } else {

                    rem_status = "false";
                }


            }

        });
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchBank.class));
            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                sp1 = parent.getItemAtPosition(pos).toString();
                if (sp1.equalsIgnoreCase("credit")) {
                    givento.setVisibility(View.GONE);
                    takenfrom.setVisibility(View.VISIBLE);
                    textInputLayout1.setVisibility(View.GONE);
                    textInputLayout2.setVisibility(View.VISIBLE);
                    givento.setText("");
                    // given_or_taken=takenfrom.getText().toString();
                } else if (sp1.equalsIgnoreCase("debit")) {

                    givento.setVisibility(View.VISIBLE);
                    takenfrom.setVisibility(View.GONE);
                    textInputLayout1.setVisibility(View.VISIBLE);
                    textInputLayout2.setVisibility(View.GONE);
                    takenfrom.setText("");
                    //    given_or_taken=givento.getText().toString();
                }

                // make insertion into database
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
//                sp2 = parent.getItemAtPosition(pos).toString();
//                // make insertion into database
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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

        bank.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bank.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                bank.setError(null);
            }
        });
        givento.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                givento.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                givento.setError(null);
            }
        });
        takenfrom.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                takenfrom.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                takenfrom.setError(null);
            }
        });
        entry_date.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                entry_date.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                entry_date.setError(null);
            }
        });
        issued_date.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                issued_date.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                issued_date.setError(null);
            }
        });
        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                amount.setError(null);
            }
        });
        chequeno.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chequeno.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                chequeno.setError(null);
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int f_bank=0,f_name=0,f_edate=0,f_idate=0,f_amt=0,f_chq=0,f_status=0;

                if (sp1.equalsIgnoreCase("debit")) {
                    if (bank.getText().toString().trim().equalsIgnoreCase("")) {
                        bank.setError("Empty Field!");
                        f_bank=1;
                    }
                    if (givento.getText().toString().trim().equalsIgnoreCase("")) {
                        givento.setError("Empty Field!");
                        f_name=1;
                    }
                    if (entry_date.getText().toString().trim().equalsIgnoreCase("")) {
                        entry_date.setError("Empty Field!");
                        f_edate=1;
                    }
                    if (issued_date.getText().toString().trim().equalsIgnoreCase("")) {
                        issued_date.setError("Empty Field!");
                        f_idate=1;
                    }
                    if (amount.getText().toString().trim().equalsIgnoreCase("")) {
                        amount.setError("Empty Field!");
                        f_amt=1;
                    }
                    if (chequeno.getText().toString().trim().equalsIgnoreCase("")) {
                        chequeno.setError("Empty Field!");
                        f_chq=1;
                    }
                    if (sp3.trim().equalsIgnoreCase("select status"))
                    {
                        Toast.makeText(getApplicationContext(),"Please select Status!",Toast.LENGTH_SHORT).show();
                        f_status=1;
                    }
                    if(f_bank==0 && f_name==0 && f_edate==0 && f_idate==0 && f_amt==0 && f_chq==0 && f_status==0) {
                        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                        db.add_cheque(new cheque(sp1, bank.getText().toString(), givento.getText().toString(), entry_date.getText().toString(), issued_date.getText().toString(), amount.getText().toString(),
                                chequeno.getText().toString(), sp3, notes.getText().toString(), rem_status));
                        finish();
                    }

                } else if (sp1.equalsIgnoreCase("credit")) {
                    if (bank.getText().toString().trim().equalsIgnoreCase("")) {
                        bank.setError("Empty Field!");
                        f_bank=1;
                    }
                    if (takenfrom.getText().toString().trim().equalsIgnoreCase("")) {
                        takenfrom.setError("Empty Field!");
                        f_name=1;
                    }
                    if (entry_date.getText().toString().trim().equalsIgnoreCase("")) {
                        entry_date.setError("Empty Field!");
                        f_edate=1;
                    }
                    if (issued_date.getText().toString().trim().equalsIgnoreCase("")) {
                        issued_date.setError("Empty Field!");
                        f_idate=1;
                    }
                    if (amount.getText().toString().trim().equalsIgnoreCase("")) {
                        amount.setError("Empty Field!");
                        f_amt=1;
                    }
                    if (chequeno.getText().toString().trim().equalsIgnoreCase("")) {
                        chequeno.setError("Empty Field!");
                        f_chq=1;
                    }
                    if (sp3.trim().equalsIgnoreCase("select status"))
                    {
                        Toast.makeText(getApplicationContext(),"Please select Status!",Toast.LENGTH_SHORT).show();
                        f_status=1;
                    }
                    if (f_bank==0 && f_name==0 && f_edate==0 && f_idate==0 && f_amt==0 && f_chq==0 && f_status==0){
                        Toast.makeText(getApplicationContext(),"Hello1",Toast.LENGTH_SHORT).show();
                        db.add_cheque(new cheque(sp1, bank.getText().toString(), takenfrom.getText().toString(), entry_date.getText().toString(), issued_date.getText().toString(), amount.getText().toString(),
                                chequeno.getText().toString(), sp3, notes.getText().toString(), rem_status));
                        finish();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please select type!! ",Toast.LENGTH_SHORT).show();
                }
            }


        });

    }


    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        bank.setText(sharedPreferences.getString("Name", ""));
        super.onResume();


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
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            if (month < 10 && day < 10) {
                issued_date.setText("0" + day + "/0" + month + "/" + year);
            } else if (month < 10) {
                issued_date.setText(day + "/0" + month + "/" + year);

            } else if (month > 10 && day < 10) {
                issued_date.setText("0" + day + "/" + month + "/" + year);

            } else {
                issued_date.setText(day + "/" + month + "/" + year);

            }

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
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {

            if (month < 10 && day < 10) {
                entry_date.setText("0" + day + "/0" + month + "/" + year);

            } else if (month < 10) {
                entry_date.setText(day + "/0" + month + "/" + year);

            } else if (month > 10 && day < 10) {
                entry_date.setText("0" + day + "/" + month + "/" + year);

            } else {
                entry_date.setText(day + "/" + month + "/" + year);

            }
        }

    }


}




