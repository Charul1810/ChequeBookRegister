package com.incognysis.cheque;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static com.incognysis.cheque.R.array.bank_names;

public  class SearchBank extends AppCompatActivity {
    ListView lv;
    EditText inputSearch;
    ArrayAdapter<String> search_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bank);
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.search_banks);
        search_list=new ArrayAdapter<String>
                (this,R.layout.list_item_search,R.id.product_name,getResources().getStringArray(bank_names));
        lv.setAdapter(search_list);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public  void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchBank.this.search_list.getFilter().filter(cs);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Name",lv.getItemAtPosition(position).toString());
                editor.commit();
//                Toast.makeText(getApplicationContext(),lv.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }


}
