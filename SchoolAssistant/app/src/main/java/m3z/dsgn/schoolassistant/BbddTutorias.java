package m3z.dsgn.schoolassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by M3z on 31/01/2017.
 */

public class BbddTutorias extends SQLiteOpenHelper {

    public BbddTutorias(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Tutorias(id integer primary key autoincrement, profesor text, padre text, fecha date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Tutorias");
        db.execSQL("create table Tutorias(id integer primary key autoincrement, profesor text, padre text, fecha date)");
    }
}
