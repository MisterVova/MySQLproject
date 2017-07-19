package com.cmp.alex.mysqlproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView userList;
    TextView header;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList = (ListView)findViewById(R.id.list);
//        header = (TextView)findViewById(R.id.header);
//        userList = (ListView)findViewById(R.id.list);
//
//        databaseHelper = new DatabaseHelper(getApplicationContext());
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }


        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }



//    public void onClick(View view){
//        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");
//        db.execSQL("INSERT INTO users VALUES ('Tom Smith', 23);");
//        db.execSQL("INSERT INTO users VALUES ('John Dow', 31);");
//
//        Cursor query = db.rawQuery("SELECT * FROM users;", null);
//        TextView textView = (TextView) findViewById(R.id.textView);
//        if(query.moveToFirst()){
//            do{
//                String name = query.getString(0);
//                int age = query.getInt(1);
//                textView.append("Name: " + name + " Age: " + age + "\n");
//            }
//            while(query.moveToNext());
//        }
//        query.close();
//        db.close();
//    }
public void onResume() {
    super.onResume();
//    // открываем подключение
//    db = databaseHelper.getReadableDatabase();
//
//    //получаем данные из бд в виде курсора
//    userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
//    // определяем, какие столбцы из курсора будут выводиться в ListView
//    String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
//    // создаем адаптер, передаем в него курсор
//    userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//            userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//    header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
//    userList.setAdapter(userAdapter);
    // открываем подключение
    db = databaseHelper.getReadableDatabase();

    //получаем данные из бд в виде курсора
    userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
    // определяем, какие столбцы из курсора будут выводиться в ListView
    String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
    // создаем адаптер, передаем в него курсор
    userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
            userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
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
