package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class BookmarksActivity extends AppCompatActivity {
private final String MESSAGE_KEY = "message";
ArrayList<String> title;
ListView listView;
BaseAdapter adapter;
SharedPreferences prefs;
SharedPreferences.Editor editor;
ImageView imageView;
Map<String, String> allEntries;
int pos;
int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        setTitle("BookmarksActivity");
        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);
        prefs = getSharedPreferences(MESSAGE_KEY, MODE_PRIVATE);
        editor = prefs.edit();
        title = new ArrayList<>();

       // editor.clear();
       // editor.apply();

     //   myText.setText(w);
       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIntent = new Intent(Intent.ACTION_VIEW);
                newIntent.setData(Uri.parse("https://google.com"));
                startActivity(newIntent);
            }
        });*/

        addTitle();
        adapter = new BookmarkAdapter(this, title);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 //newIntent = new Intent(Intent.ACTION_VIEW);
               // Bundle bundle = new Bundle();
               // bundle.putString(WORD, (String) allEntries.get(parent.getItemAtPosition(position).toString()));
                //iIntent.putExtra(WORD, (Bundle) allEntries.get(parent.getItemAtPosition(position).toString()));
                //iIntent.getBundleExtra(parent.getItemAtPosition(position).toString());
               // iIntent.putExtra(WORD, parent.getItemAtPosition(position).toString());
               Intent iIntent = getIntent();
             //   String ed = allEntries.get(parent.getItemAtPosition(position).toString());
                iIntent.putExtra(BrowserActivity.WORD, allEntries.get(parent.getItemAtPosition(position).toString()));
                setResult(RESULT_OK, iIntent);
               // Intent e = iIntent.putExtra(BrowserActivity.WORD, ed);

                // iIntent.putExtra(WORD, allEntries.get(parent.getItemAtPosition(position).toString()));
                //Intent e = iIntent.putExtra(WORD, allEntries.get(parent.getItemAtPosition(position).toString()));
                 //iIntent.getParcelableArrayExtra(parent.getItemAtPosition(position).toString());
                 //iIntent.
              //  Log.d("hider", ed);
               // Log.d("yow", String.valueOf(e));
                // newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               //  startActivity(iIntent);
                 pos = position;
                 //editor.clear();
                // editor.apply();
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
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(BookmarksActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.remove(parent.getItemAtPosition(position).toString());
                                title.remove(parent.getItemAtPosition(position));
                                editor.apply();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
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
                                editor.remove(parent.getItemAtPosition(pos).toString());
                                title.remove(parent.getItemAtPosition(pos));
                                editor.apply();
                                adapter.notifyDataSetChanged();
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


    //get title
    public void addTitle(){
        count = prefs.getAll().size();
        //count--;
        if(count>0) {
            allEntries = (Map<String, String>) prefs.getAll();
            for (Map.Entry<String, String> entry : allEntries.entrySet()) {
                title.add(entry.getKey());
            }
        }
    }

}
