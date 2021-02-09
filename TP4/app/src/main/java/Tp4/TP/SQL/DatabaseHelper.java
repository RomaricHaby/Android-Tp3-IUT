package Tp4.TP.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.util.ArrayList;
import Tp4.TP.Contact;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DataBase";
    public static final String CONTACTS_TABLE_NAME = "Contact";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ CONTACTS_TABLE_NAME +"(id integer primary key, firstname text, lastname text, birthday text, phone text, email text, cap text, adresspostal text, genre text, image integer, pathImg text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }


    public boolean insert(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("firstname", contact.getFirstName());
        contentValues.put("lastname", contact.getLastName());
        contentValues.put("birthday", contact.getBirthday());
        contentValues.put("phone", contact.getPhone());
        contentValues.put("email", contact.getEmail());
        contentValues.put("cap", contact.getCap());
        contentValues.put("adresspostal", contact.getAdressPostal());
        contentValues.put("genre", contact.getGenre());
        contentValues.put("image", contact.getImage());
        contentValues.put("pathImg", contact.getPathImg());

        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<Contact> loadContact(){
        String fistname, lastname, birthday,phone,email,cap,adressPostal, genre,pathImg;
        int id, image;
        ArrayList<Contact> listContact = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + CONTACTS_TABLE_NAME,null);


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex("id"));
                    fistname = cursor.getString(cursor.getColumnIndex("firstname"));
                    lastname = cursor.getString(cursor.getColumnIndex("lastname"));
                    birthday = cursor.getString(cursor.getColumnIndex("birthday"));
                    phone = cursor.getString(cursor.getColumnIndex("phone"));
                    email = cursor.getString(cursor.getColumnIndex("email"));
                    cap = cursor.getString(cursor.getColumnIndex("cap"));
                    adressPostal = cursor.getString(cursor.getColumnIndex("adresspostal"));
                    genre = cursor.getString(cursor.getColumnIndex("genre"));
                    image = cursor.getInt(cursor.getColumnIndex("image"));
                    pathImg = cursor.getString(cursor.getColumnIndex("pathImg"));

                    Contact contact = new Contact(id,fistname,lastname,birthday,phone,email,cap,adressPostal,genre,image,pathImg);

                    listContact.add(contact);
                } while (cursor.moveToNext());
            }
        }
        return listContact;
    }

    public boolean deleteContact(int idContact,Contact contact){
        if(contact.getPathImg() != null){
            File fdelete = new File(contact.getPathImg());
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    this.getReadableDatabase().delete(CONTACTS_TABLE_NAME,  "id"+ " = "+ idContact,null);
                    return true;
                }
                else {
                    System.out.println("file not Deleted :");
                    return false;
                }
            }
        }
        else {
            this.getReadableDatabase().delete(CONTACTS_TABLE_NAME,  "id"+ " = "+ idContact,null);
            return true;
        }
        return false;
    }
}
