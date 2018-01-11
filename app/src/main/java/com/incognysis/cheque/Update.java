package com.incognysis.cheque;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
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

import java.util.Calendar;

public class Update extends AppCompatActivity {

    EditText givento, amount, chequeno, notes, takenfrom, bank;
    public static EditText issued_date, entry_date;
    Spinner type, status;
    SwitchCompat reminder;
    Button save;
    TextView id;
    DatabaseHandler db;
    String sp1 = "", sp2 = "", sp3 = "", rem_status = "", given_or_taken = "";
    TextInputLayout textInputLayout1, textInputLayout2;

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
        bank = (EditText) findViewById(R.id.bank_edit_text);
        notes = (EditText) findViewById(R.id.notes_edit_text);
        type = (Spinner) findViewById(R.id.type_spinner);
        status = (Spinner) findViewById(R.id.status_spinner);
        reminder = (SwitchCompat) findViewById(R.id.switch_toggle);
        save = (Button) findViewById(R.id.save);
        db = new DatabaseHandler(this);
        textInputLayout1 = (TextInputLayout) findViewById(R.id.text_input_layout1);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.text_input_layout2);

        amount.setText("₹ ");
        Selection.setSelection(amount.getText(), amount.getText().length());

        Bundle b = getIntent().getExtras();
        id.setText(b.getString("id", "1"));
        //type.setSelection(b.getInt("type"));
        //  bank.setSelection(b.getInt("bank"));
        bank.setText(b.getString("bank"));
        givento.setText(b.getString("takenfrom"));
        takenfrom.setText(b.getString("takenfrom"));
        entry_date.setText(b.getString("e_date"));
        issued_date.setText(b.getString("i_date"));
        amount.setText(b.getString("amount"));
        chequeno.setText(b.getString("chequeNo"));
        //    status.setSelection(b.getInt("status"));
        notes.setText(b.getString("notes"));
        reminder.setChecked(false);

        //for type spinner
        String compareValue1 = b.getString("type");
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.type));
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        type.setAdapter(spinnerArrayAdapter1);
        if (!compareValue1.equals(null)) {
            int spinnerPosition = spinnerArrayAdapter1.getPosition(compareValue1);
            type.setSelection(spinnerPosition);
        }


        //for bank spinner
//        String compareValue2 = b.getString("bank");
//        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item,
//                        getResources().getStringArray(R.array.bank_names));
//        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);
//        bank.setAdapter(spinnerArrayAdapter2);
//        if (!compareValue2.equals(null)) {
//            int spinnerPosition = spinnerArrayAdapter2.getPosition(compareValue2);
//            bank.setSelection(spinnerPosition);
//        }

        //for status spinner
        String compareValue3 = b.getString("status");
        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.status));
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        status.setAdapter(spinnerArrayAdapter3);
        if (!compareValue3.equals(null)) {
            int spinnerPosition = spinnerArrayAdapter3.getPosition(compareValue3);
            status.setSelection(spinnerPosition);
        }


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
//                if (!s.toString().startsWith("₹ ")) {
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


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {

                sp1 = parent.getItemAtPosition(pos).toString();
                if (sp1.equalsIgnoreCase("credit")) {
                    givento.setVisibility(View.GONE);
                    takenfrom.setVisibility(View.VISIBLE);
                    textInputLayout1.setVisibility(View.GONE);
                    textInputLayout2.setVisibility(View.VISIBLE);
                    // given_or_taken=takenfrom.getText().toString();
                } else if (sp1.equalsIgnoreCase("debit")) {

                    givento.setVisibility(View.VISIBLE);
                    takenfrom.setVisibility(View.GONE);
                    textInputLayout1.setVisibility(View.VISIBLE);
                    //    given_or_taken=givento.getText().toString();
                }

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
                DialogFragment newFragment = new Update.SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        entry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new Update.SelectDateFragments();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sp1.equalsIgnoreCase("debit")) {
                    if (bank.getText().toString().trim().length()==0) {
                        bank.setError("Empty Field!");
                    }
                    if (givento.getText().toString().trim().equalsIgnoreCase("")) {
                        givento.setError("Empty Field!");
                    }

                    if (entry_date.getText().toString().trim().equalsIgnoreCase("")) {
                        entry_date.setError("Empty Field!");
                    }

                    if (issued_date.getText().toString().trim().equalsIgnoreCase("")) {
                        issued_date.setError("Empty Field!");
                    }

                    if (amount.getText().toString().trim().equalsIgnoreCase("")) {
                        amount.setError("Empty Field!");
                    }

                    if (chequeno.getText().toString().trim().equalsIgnoreCase("")) {
                        chequeno.setError("Empty Field!");
                    }
//                    else {
//                        db.updateCheque(new cheque(Integer.parseInt(id.getText().toString()), sp1, bank.getText().toString(), givento.getText().toString(), entry_date.getText().toString(), issued_date.getText().toString(), amount.getText().toString(),
//                                chequeno.getText().toString(), sp3, notes.getText().toString(), rem_status));
//                        finish();
//                    }

                }
                else if (sp1.equalsIgnoreCase("credit")) {
                    if (bank.getText().toString().trim().equalsIgnoreCase("")) {
                        bank.setError("Empty Field!");
                    }
                    if (takenfrom.getText().toString().trim().equalsIgnoreCase("")) {
                        takenfrom.setError("Empty Field!");
                    }
                    if (entry_date.getText().toString().trim().equalsIgnoreCase("")) {
                        entry_date.setError("Empty Field!");
                    }
                    if (issued_date.getText().toString().trim().equalsIgnoreCase("")) {
                        issued_date.setError("Empty Field!");
                    }
                    if (amount.getText().toString().trim().equalsIgnoreCase("")) {
                        amount.setError("Empty Field!");
                    }
                    if (chequeno.getText().toString().trim().equalsIgnoreCase("")) {
                        chequeno.setError("Empty Field!");
                    }
//                    else {
//                        db.updateCheque(new cheque(Integer.parseInt(id.getText().toString()), sp1, bank.getText().toString(), takenfrom.getText().toString(), entry_date.getText().toString(), issued_date.getText().toString(), amount.getText().toString(),
//                                chequeno.getText().toString(), sp3, notes.getText().toString(), rem_status));
//                        finish();
//                    }
                }

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
