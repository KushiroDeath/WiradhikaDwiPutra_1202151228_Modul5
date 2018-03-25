package com.example.android.WiradhikaDwiPutra_1202151228_Modul5;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LinkedList<ToDo> mWordList = new LinkedList<>();
    private int mCount = 0;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check DB
        databaseHandler = new DatabaseHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Change");
            dialog.setCancelable(true);
            // there are a lot of settings, for dialog, check them all out!
            // set up radiobutton
            final RadioButton rdRed = (RadioButton) dialog.findViewById(R.id.rdRed);
            final RadioButton rdBlue = (RadioButton) dialog.findViewById(R.id.rdBlue);
            final RadioButton rdGreen = (RadioButton) dialog.findViewById(R.id.rdGreen);
            Button btnChange = (Button)dialog.findViewById(R.id.btnChange);
            btnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rdRed.isChecked()){
                        mRecyclerView.setBackgroundResource(R.color.redBackgroud);
                        Toast.makeText(view.getContext(),"Red Choosen",Toast.LENGTH_SHORT).show();
                    }
                    if (rdBlue.isChecked()){
                        mRecyclerView.setBackgroundResource(R.color.blueBackgroud);
                        Toast.makeText(view.getContext(),"Blue Choosen",Toast.LENGTH_SHORT).show();
                    }
                    if (rdGreen.isChecked()){
                        mRecyclerView.setBackgroundResource(R.color.greenBackgroud);
                        Toast.makeText(view.getContext(),"Green Choosen",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(view.getContext(),"Changed",Toast.LENGTH_SHORT).show();
                }
            });

            // now that the dialog is set up, it's time to show it
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoAdd(View view) {
        Intent intent = new Intent(this, AddTodo.class);
//        startActivity(intent);
        startActivityForResult(intent, 1);
    }

    public void setRecyclerView(){
        mWordList = databaseHandler.findAll();
        // Get a handle to the RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new RecyclerViewAdapter(this, mWordList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SwipeHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            Log.d("new name : ",data.getStringExtra("name"));
            Log.d("new desc : ",data.getStringExtra("desc"));
            Log.d("new priority : ",data.getStringExtra("priority"));
            databaseHandler.save(new ToDo(data.getStringExtra("name"), data.getStringExtra("desc"), data.getStringExtra("priority")));
        }
        setRecyclerView();
        mAdapter.notifyDataSetChanged();
    }
}
