package com.cmp.alex.mysqlproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FavorActivity extends AppCompatActivity {

  ListView userList;
  SimpleCursorAdapter userAdapter;
  DatabaseHelper sqlHelper;
  SQLiteDatabase db;
  Cursor userCursor;
  long userId=0;
  int flag=1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favor);
    userList = (ListView)findViewById(R.id.list_favor);
    sqlHelper = new DatabaseHelper(this);
    db = sqlHelper.open();

    //получаем данные из бд в виде курсора
      userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
          DatabaseHelper.COLUMN_FLAG + "=?",new String[]{String.valueOf(flag)});
    // определяем, какие столбцы из курсора будут выводиться в ListView
    String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR,DatabaseHelper.COLUMN_TRNS};
    // создаем адаптер, передаем в него курсор
    userAdapter = new SimpleCursorAdapter(this, R.layout.item,
        userCursor, headers, new int[]{R.id.text1, R.id.text2, R.id.text3}, 0);
    userList.setAdapter(userAdapter);

    }


  public void delete(View view){
    db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
    goHome();
  }
  private void goHome(){
    // закрываем подключение
    db.close();
    // переход к главной activity
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
  }
}