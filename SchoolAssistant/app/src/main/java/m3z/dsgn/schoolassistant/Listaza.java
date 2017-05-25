package m3z.dsgn.schoolassistant;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.Vector;



/**
 * Created by M3z on 20/11/2016.
 */

public class Listaza extends ListActivity {

    private Vector<String> listaProfe;
    private Vector<String> listaFecha;
    private String user;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");

        super.onCreate(savedInstanceState);
       /* Log.i("MIAS ---------------- ", "Ha entrado en el constructor del super()");
        setContentView(R.layout.tutorias_layout);
        Log.i("MIAS ---------------- ", "Ha inflado el tutorias_layout");
       // setListAdapter(new MyAdapter(this, listaTitulos, listaDetalles));
        Log.i("MIAS ---------------- ", "ha seteado el ListAdapter");*/

    }


    @Override public void onResume(){
        super.onResume();

        BbddTutorias conexion = new BbddTutorias(this, "bbddtutoria", null, 1);
        SQLiteDatabase bbdd=conexion.getWritableDatabase();

        Cursor c1;

        c1= bbdd.rawQuery("SELECT profesor, fecha FROM Tutorias where padre = '" + user + "'", null);

        int num=c1.getCount();

        listaProfe = new Vector<>();
        listaFecha= new Vector<>();
        if(c1.moveToFirst()){
            String nombre;
            String codigo;
            do{
                listaProfe.add(c1.getString(0));
                listaFecha.add(c1.getString(1));

            }while(c1.moveToNext());
        }
        setListAdapter(new MyAdapter(this, listaProfe, listaFecha));
    }
}
