package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ListView mylistview;
    static ArrayList<String> notes;
    SharedPreferences sp;
    static ArrayAdapter<String> ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylistview = findViewById(R.id.lview);
        notes=new ArrayList<String>();
        sp = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet)sp.getStringSet("notes",null);
        if(set==null)
        {
            notes.add("Example...");
        }
        else
        {
            notes=new ArrayList(set);
        }
        ad = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,notes);
        mylistview.setAdapter(ad);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), notesActivity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });
        mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int i=position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.abeysaale)
                        .setTitle("Delete ho jayega")
                        .setMessage("Baad me mat rona")
                        .setPositiveButton("Thekhey", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    notes.remove(i);
                                    ad.notifyDataSetChanged();
                                HashSet<String> set =new HashSet<>(MainActivity.notes);
                                sp.edit().putStringSet("notes",set) .apply();
                            }
                        })
                        .setNegativeButton("Nhi nhi",null).show();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif=getMenuInflater();
        mif.inflate(R.menu.menufile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.AddNote)
         {
             Intent intent = new Intent(getApplicationContext(), notesActivity.class);
             startActivity(intent);
             return true;
         }
        if(item.getItemId()==R.id.DeleteAll)
        {
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.abeysaale)
                    .setTitle("Pagal ho gya??")
                    .setMessage("Shi me sab mit jayega")
                    .setPositiveButton("Ha kar de", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setIcon(android.R.drawable.ic_delete)
                                    .setTitle("Lock kar de?")
                                    .setMessage("wapas nhi aayega")
                                    .setPositiveButton("Ha kar de", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            notes.clear();
                                            ad.notifyDataSetChanged();
                                            HashSet<String> set =new HashSet<>(MainActivity.notes);
                                            sp.edit().putStringSet("notes",set) .apply();
                                            Toast.makeText(MainActivity.this,"Jaa maar",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Nahi",null).show();

                        }
                    })
                    .setNegativeButton("Nahi",null).show();

            return true;
        }
         return false;
    }
}
