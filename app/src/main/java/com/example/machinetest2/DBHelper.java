package com.example.machinetest2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MyDBName.db";
   public static final String CONTACTS_TABLE_NAME = "contacts";
   public static final String CONTACTS_COLUMN_ID = "id";
   public static final String CONTACTS_COLUMN_USERID = "userId";
   public static final String CONTACTS_COLUMN_TITLE = "title";
   public static final String CONTACTS_COLUMN_BODY = "body";
   public static final String CONTACTS_COLUMN_LATITUDE = "latitude";
   public static final String CONTACTS_COLUMN_LONGITUDE= "longitude";
   public static final String CONTACTS_COLUMN_TIME = "time";

 //  private HashxMap hp;

   public DBHelper(Context context) {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL(
         "create table contacts " +
         "(id integer primary key, userid text,title text,body text,latitude text,longitude text,time text)"
      );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS contacts");
      onCreate(db);
   }

   public boolean insertContact (String userid, String title, String body, String latitude, String longitude, String time) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("userid", userid);
      contentValues.put("title", title);
      contentValues.put("body", body);
      contentValues.put("latitude", latitude);
      contentValues.put("longitude", longitude);
      contentValues.put("time", time);

      db.insert("contacts", null, contentValues);
      return true;
   }
   
   public Cursor getData(int id) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
      return res;
   }
   
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
      return numRows;
   }
   //String userid, String title, String body, String latitude, String longitude, String time
   public boolean updateContact (String id ,String userid, String title, String body, String latitude, String longitude, String time) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();

      contentValues.put("userid", userid);
      contentValues.put("title", title);
      contentValues.put("body", body);
      contentValues.put("latitude", latitude);
      contentValues.put("longitude", longitude);
      contentValues.put("time", time);

      db.update("contacts", contentValues, "id = ? ", new String[] { id } );
      return true;
   }

   public boolean deleteContact (String id ) {
      SQLiteDatabase db = this.getWritableDatabase();
       db.delete("contacts",
      "id = ? ", 
      new String[] { id });
      return true;
   }
   
   public ArrayList<String> getAllCotacts() {
      ArrayList<String> array_list = new ArrayList<String>();
      
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from contacts", null );
      res.moveToFirst();
      
      while(res.isAfterLast() == false){
         array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_USERID)));
         res.moveToNext();
      }
      return array_list;
   }


   public ArrayList<NoteModel> getNotes() {
      ArrayList<NoteModel> arrayList = new ArrayList<>();

      // select all query
      String select_query= "SELECT *FROM " + CONTACTS_TABLE_NAME;

      SQLiteDatabase db = this .getWritableDatabase();
      Cursor cursor = db.rawQuery(select_query, null);

      // looping through all rows and adding to list
      if (cursor.moveToFirst()) {
         do {
            NoteModel noteModel = new NoteModel();
            noteModel.setID(cursor.getString(0));
            noteModel.setUserId(cursor.getString(1));
            noteModel.setTitle(cursor.getString(2));
            noteModel.setBody(cursor.getString(3));


            noteModel.setLatitude(cursor.getString(4));
            noteModel.setLongitude(cursor.getString(5));
            noteModel.setTime(cursor.getString(6));
            arrayList.add(noteModel);
         }while (cursor.moveToNext());
      }
      return arrayList;
   }

   public  boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                     String dbfield, String fieldValue) {
      SQLiteDatabase db = this .getWritableDatabase();
      String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
      Cursor cursor = db.rawQuery(Query, null);
      if(cursor.getCount() <= 0){
         cursor.close();
         return false;
      }
      cursor.close();
      return true;
   }
}