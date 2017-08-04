package com.cmp.alex.mysqlproject;

import static android.R.attr.id;
import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by vitiok on 7/30/17.
 */

public class MyCursorAdapter extends SimpleCursorAdapter {
  SQLiteDatabase db;
  DatabaseHelper databaseHelper;
  Cursor  myFlajok;
  int temp,myId = 0;

  public MyCursorAdapter(Context context, int layout, Cursor c,
      String[] from, int[] to, int flags) {
    super(context, layout, c, from, to, flags);
    mInflater = LayoutInflater.from(context);
  }

  private Context context;
  private int mSelectedPosition;
  LayoutInflater mInflater;


  public void setSelectedPosition(int position) {
    mSelectedPosition = position;
    // something has changed.
    notifyDataSetChanged();
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    TextView list_item = (TextView) view.findViewById(R.id.text1);
    list_item.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
    TextView list_item1 = (TextView) view.findViewById(R.id.text2);
    list_item1.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR)));
    TextView list_item2 = (TextView) view.findViewById(R.id.text3);
    list_item2.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRNS)));
    final Button myButton = (Button) view.findViewById(R.id.button2);

    int position = cursor.getPosition(); // that should be the same position
    if (mSelectedPosition == position) {
      view.setBackgroundColor(Color.RED);
    } else {
      view.setBackgroundColor(Color.WHITE);
    }


    myButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
//             ContentValues cv = new ContentValues();

//        myFlajok = db.rawQuery("select flag from " + DatabaseHelper.TABLE + " where " +
//            DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
//        myFlajok.moveToFirst();
//        myButton.setText(String.valueOf(mSelectedPosition));
if(temp==0){
      myButton.setText("NO");
  temp=1;
    }else {
        myButton.setText("YES");
  temp=0;
      }

////            String myString = myFlag.getString( myFlag.getColumnIndex("flag") ); // id is column name in db
////        Log.d(TAG, "по id vivodim znachenie "+myFlag);
//
//        myId = myFlajok.getInt(0);
//         String str = myFlajok.getString(myFlajok.getColumnIndex("content"));
//        if (myId == 0) {
//          myButton.setText("YES");
//
//          cv.put(DatabaseHelper.COLUMN_FLAG, 1);
//          db.update(DatabaseHelper.TABLE, cv,
//              DatabaseHelper.COLUMN_ID + "=" + String.valueOf(id),
//              null);
//        } else {
//          myButton.setText("NO");
//
//          cv.put(DatabaseHelper.COLUMN_FLAG, 0);
//          db.update(DatabaseHelper.TABLE, cv,
//              DatabaseHelper.COLUMN_ID + "=" + String.valueOf(id),
//              null);
//        }
      }
    });
//    databaseHelper = new DatabaseHelper(getApplicationContext());
    // создаем базу данных
//    databaseHelper.create_db();

  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    View v = mInflater.inflate(R.layout.item, parent, false);

    // edit: no need to call bindView here. That's done automatically
    return v;
  }


}


