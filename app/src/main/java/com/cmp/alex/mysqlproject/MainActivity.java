package com.cmp.alex.mysqlproject;

import static android.R.attr.id;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    private static final String TAG = "myLogs";
int firstMain=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button justButton = (Button) findViewById(R.id.button2);

        userList = (ListView)findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, final long id) {
                final Button myButton = (Button) view.findViewById(R.id.button2);
                myButton.setVisibility(View.VISIBLE);
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
                    }
                });
                Log.d(TAG, "кнопка ОК");
//                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);

            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
    }

    @Override
    public void onResume() {
        super.onResume();

        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
//      userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
//          DatabaseHelper.COLUMN_NOMER + "=?",new String[]{String.valueOf(firstMain)});
        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR,DatabaseHelper.COLUMN_TRNS,DatabaseHelper.COLUMN_ID};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.item,
            userCursor, headers, new int[]{R.id.text1, R.id.text2, R.id.text3,R.id.button2}, 0);
        userList.setAdapter(userAdapter);
    }
    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}