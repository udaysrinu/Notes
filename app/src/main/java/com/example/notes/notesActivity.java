package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class notesActivity extends AppCompatActivity {


    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Intent intent=getIntent();
         Id =intent.getIntExtra("noteId",-1);
        String bkh=String.valueOf(Id);
        Toast.makeText(this,"Likh beta,jho likhna hai",Toast.LENGTH_SHORT).show();
        EditText editText=(EditText)findViewById(R.id.editText);
        if(Id!=-1)
        {
            editText.setText(MainActivity.notes.get(Id));
        }
        else
        {
            MainActivity.notes.add("");
            Id= MainActivity.notes.size()-1;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(Id,String.valueOf(s));

                MainActivity.ad.notifyDataSetChanged();
                SharedPreferences sp= getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(MainActivity.notes);
                sp.edit().putStringSet("notes",set).apply();
                //Toast.makeText(this,"Wahhhhh",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(getApplicationContext(),"Wahhhhhh",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
