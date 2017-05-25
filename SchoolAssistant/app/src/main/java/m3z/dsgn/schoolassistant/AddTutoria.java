package m3z.dsgn.schoolassistant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by M3z on 31/01/2017.
 */

public class AddTutoria extends Activity {

    private String user;
    private EditText profCuadro;
    private EditText dateCuadro;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtutoria_layout);

        //permisos - calendario
        int permissionReadCalendario = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR);
        int permissionWriteCalendario = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR);
        if (permissionReadCalendario == PackageManager.PERMISSION_DENIED || permissionWriteCalendario==PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.READ_CALENDAR}, 0);
        }

        //permisos - memoria externa
        int permissionCheckExternalStorage = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        //int permissionCheckExternalStorage = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheckExternalStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");
        profCuadro = (EditText) findViewById(R.id.proftext);
        dateCuadro = (EditText) findViewById(R.id.fechaText);
    }

    public void addClick(View v){

        String pattern = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/[0-2][0-9][0-9][0-9]$";
        if(profCuadro.getText().toString() == null || profCuadro.getText().toString().length() <=0){
            Toast.makeText(this, R.string.failProf, Toast.LENGTH_SHORT).show();
            return;
        }

        if(dateCuadro.getText().toString() == null || dateCuadro.getText().toString().length() != 10
                || !dateCuadro.getText().toString().matches(pattern)){
            Toast.makeText(this, R.string.failDate, Toast.LENGTH_SHORT).show();
            return;
        }


        BbddTutorias conexion = new BbddTutorias(this, "bbddtutoria", null, 1);
        SQLiteDatabase bbdd = conexion.getWritableDatabase();

        // profesor text, padre text, fecha date
        if(bbdd!= null){

            try {
                bbdd.execSQL("insert into Tutorias(profesor, padre, fecha) Values('" + profCuadro.getText().toString() +"', '" + user + "', '" +dateCuadro.getText().toString() + "')");


                //calendario
                apuntarCalendario();

                //guardar Backup
                guardarBackup();

            }catch(IllegalStateException e) {
                System.out.println(e.getMessage());
            }

            bbdd.close();

        }

        conexion.close();
        finish();

    }

    private void guardarBackup(){


        String ruta= String.format("%s/%sBUP.txt",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString(),
                user);

        Log.i("MIAS!!!! -------- :", "Directory: " + ruta);
        FileOutputStream file;

        String state=Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            try{
                file=new FileOutputStream(ruta, true);
                file.write(String.format("TUTORIA: %s | %s | %s",
                                                profCuadro.getText().toString(), user,
                                                dateCuadro.getText().toString()).getBytes());
                Log.i("MIAS!!!! -------- :", "Existe? en: " + new File(ruta).exists());
                // cerramos el fichero
                file.close();
            }
            catch(FileNotFoundException e){
                System.out.println("File not found exception al backup");
            }
            catch(IOException e){
                System.out.println("IOException en Backup");
            }
        }
        else{
            System.out.println("No se encontró la tarjeta SD");
        }
    }

    private void apuntarCalendario(){

        int anyo = Integer.parseInt(dateCuadro.getText().toString().substring(6)),
            mes = Integer.parseInt(dateCuadro.getText().toString().substring(3,5)),
            dia = Integer.parseInt(dateCuadro.getText().toString().substring(0,2));


        // Construct event details
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        //beginTime.set(Integer.parseInt(a), Integer.parseInt(m)-1, Integer.parseInt(d), hora, minuto);
        beginTime.set(anyo,mes, dia, 11  , 30);
        // beginTime.set(2017, 0, 27, 15, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        //endTime.set(Integer.parseInt(a), Integer.parseInt(m)-1, Integer.parseInt(d), hora + 1, minuto);
        endTime.set(anyo, mes, dia, 12, 0);
        // endTime.set(2017, 0, 27, 16, 30);
        endMillis = endTime.getTimeInMillis();



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have to allow this", Toast.LENGTH_SHORT).show();
            return;
        }


        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);

        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europa");
        values.put(CalendarContract.Events.TITLE, "Tutoria con: ");
        values.put(CalendarContract.Events.DESCRIPTION, profCuadro.getText().toString());
        values.put(CalendarContract.Events.CALENDAR_ID, 3);
        //Uri uri=cr.insert(Uri.parse("content://com.android.calendar/events"), values);
        Uri uri=cr.insert(CalendarContract.Events.CONTENT_URI, values);


    }
}
