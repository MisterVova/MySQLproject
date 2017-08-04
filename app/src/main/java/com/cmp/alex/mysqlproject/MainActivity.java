package com.cmp.alex.mysqlproject;

import static android.R.attr.id;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
  Cursor userCursor, myFlag,buttonCursor;
  SimpleCursorAdapter userAdapter;
  MyCursorAdapter newAdapter;
  private static final String TAG = "myLogs";
  int firstMain, myId = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userList = (ListView) findViewById(R.id.list);
    userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, final View view, final int position,
          final long id) {
        final Button myButton = (Button) view.findViewById(R.id.button2);
//        myButton.setVisibility(View.VISIBLE);
//
newAdapter.setSelectedPosition(position);
        myButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
//            Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
            ContentValues cv = new ContentValues();
            myFlag = db.rawQuery("select flag from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
            myFlag.moveToFirst();
            myButton.setText(String.valueOf(myFlag.getInt(0)));

//            String myString = myFlag.getString( myFlag.getColumnIndex("flag") ); // id is column name in db
                        Log.d(TAG, "по id vivodim znachenie "+myFlag);

            myId = myFlag.getInt(0);
//         String str = myFlag.getString(myFlag.getColumnIndex("content"));
            if (myId == 0) {
                          myButton.setText("YES");

              cv.put(DatabaseHelper.COLUMN_FLAG, 1);
              db.update(DatabaseHelper.TABLE, cv,
                  DatabaseHelper.COLUMN_ID + "=" + String.valueOf(id),
                  null);
            } else {
              myButton.setText("NO");

              cv.put(DatabaseHelper.COLUMN_FLAG, 0);
              db.update(DatabaseHelper.TABLE, cv,
                  DatabaseHelper.COLUMN_ID + "=" + String.valueOf(id),
                  null);
            }
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
    userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
    // определяем, какие столбцы из курсора будут выводиться в ListView
    String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR,
        DatabaseHelper.COLUMN_TRNS, DatabaseHelper.COLUMN_FLAG};
    // создаем адаптер, передаем в него курсор
    newAdapter = new MyCursorAdapter(this, R.layout.item,
        userCursor, headers, new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.button2}, 0);
    userList.setAdapter(newAdapter);


//    buttonCursor=db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
//        DatabaseHelper.COLUMN_FLAG + "=?", new String[]{String.valueOf(id)});
//    final Button justButton = (Button) findViewById(R.id.button2);
//
//    while(buttonCursor.moveToFirst()){
//      int temp;
//      temp=buttonCursor.getInt(0);
//      if(temp==0){
//      justButton.setText("NO");
//    }else {
//        justButton.setText("YES");
//      }
//    }
//    buttonCursor.close();
  }

  // по нажатию на кнопку запускаем UserActivity для добавления данных
  public void add(View view) {
    Intent intent = new Intent(this, FavorActivity.class);
    startActivity(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    // Закрываем подключение и курсор
    db.close();
    userCursor.close();
  }
}