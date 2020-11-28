package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {
private final String MESSAGE_KEY = "message";
private final String COUNT_KEY = "count";
private final String WORD = "word";
ArrayList<String> title;
Intent newIntent;
ListView listView;
BaseAdapter adapter;
//ArrayAdapter<String> adapter;
SharedPreferences preferences;
SharedPreferences.Editor editor;
ImageView imageView;
int pos;
int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        setTitle("BookmarksActivity");
        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);
        newIntent = new Intent(BookmarksActivity.this, BrowserActivity.class);
        preferences = getSharedPreferences(MESSAGE_KEY, MODE_PRIVATE);
        count = preferences.getInt(COUNT_KEY, 0);
        editor = preferences.edit();
        //message = preferences.getString(MESSAGE_KEY, null);
        title = new ArrayList<>();
       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIntent = new Intent(Intent.ACTION_VIEW);
                newIntent.setData(Uri.parse("https://google.com"));
                startActivity(newIntent);
            }
        });*/
       // title.add(message);

        addTitle();
        //.notifyDataSetChanged();
        adapter = new BookmarkAdapter(this, title);

       // ArrayAdapter adapter = new ArrayAdapter(BookmarksActivity.this, android.R.layout.simple_list_item_1, mobileArray);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 //newIntent = new Intent(Intent.ACTION_VIEW);
                // newIntent.setData(Uri.parse("https://google.com"));
                 newIntent.putExtra(WORD, parent.getItemAtPosition(position).toString() );
                 newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                 startActivity(newIntent);
                 pos = position;
                //parent.getItemAtPosition(position).toString()
                //title.remove(position);
                /*editor.remove("string["+position+"]");
                title.remove(preferences.getString("string["+position+"]", ""));
                count--;
                editor.putInt(COUNT_KEY, count);
                editor.apply();
                adapter.notifyDataSetChanged();*/
                BookmarksActivity.this.finish();
            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog();
                return true;
            }
        });


        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BookmarksActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                title.remove(pos);
                                //adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });*/

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title.remove(which);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();*/
    }
    //confirm deletion
    private void dialog(){
        new AlertDialog.Builder(BookmarksActivity.this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //title.remove(pos);
                        //adapter.notifyDataSetChanged();
                        editor.remove("string["+pos+"]");
                        title.remove(preferences.getString("string["+pos+"]", ""));
                        count--;
                        editor.putInt(COUNT_KEY, count);
                        editor.apply();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    //get title
    public void addTitle(){
        if(count > 0){
            for(int i = 0; i < count; i++){
                title.add(preferences.getString("string["+i+"]", ""));
            }
        }
        //title.add(message);
       // title.add("Temple University");
        //title.add("Google");
       //adapter.notifyDataSetChanged();
    }

}
