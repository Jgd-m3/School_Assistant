package m3z.dsgn.schoolassistant;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by M3z on 16/11/2016.
 */

public class InicialPadre extends AppCompatActivity{

    private String usuario;
    private String pass;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.padre_inicial_activity);


        Bundle extra = getIntent().getExtras();
        usuario = extra.getString("user");
        pass = extra.getString("pass");
        TextView txtSaludo = (TextView) findViewById(R.id.txtSaludo);
        txtSaludo.setText(getText(R.string.saludoPI) + " " + usuario);
        btn = (Button) findViewById(R.id.btnTutoriasPadre);

        hacerLogDeConexion();

        //listener del boton
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                muestraToast();
                return false;
            }
        });

    }

    public void tutoriasClick(View v){
        Intent i = new Intent(this, Listaza.class);
        i.putExtra("user", usuario);
        startActivity(i);
    }

    public void newTutoriaClick(View v){
        Intent i = new Intent(this, AddTutoria.class);
        i.putExtra("user", usuario);
        startActivity(i);
    }


    public void clickDisconPadre(View v){
        // TODO: 19/11/2016  FUTURE FEATURES: implement persistence here before the disconnect

        Intent i = new Intent();
        i.putExtra("user", usuario);
        setResult(RESULT_OK, i);
        finish();

    }

    public void logsClick(View v){

        Intent j = new Intent(this, FragContainer.class);
        startActivity(j);
    }

    public void backupClick(View v){
        Intent i = new Intent(this, ShowBackup.class);
        i.putExtra("user", usuario);
        startActivity(i);
    }

    public void mapaClick(View v){
        startActivity(new Intent(this, Localizacion.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.mnOpPref){        //preferencias
            Intent i = new Intent();
            i.setClass(this, SetPreferences.class);
            startActivity(i);
        }
        else if(id == R.id.mnOpAbout){       // about
            startActivity(new Intent(this, AboutUs.class));
        }
        else if(id == R.id.mnOpContact){     //contacto

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED){

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:962849347"));
                startActivity(intent);
            }
        }
        else if(id == R.id.mnOpWeb){         //web
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.iesmaestredecalatrava.es/"));
            startActivity(intent);

        }
        else return super.onOptionsItemSelected(item);

        return true;
    }

    private void muestraToast(){
        Toast.makeText(this, getText(R.string.toastTutorias).toString(),
                            Toast.LENGTH_SHORT).show();

    }

    private void hacerLogDeConexion(){
        FileOutputStream file;
        try{
            file= openFileOutput("regLogins.txt", Context.MODE_APPEND);
            file.write((usuario + " - LOGIN").getBytes());
            // cerramos el fichero
            file.close();

            Toast.makeText(this, "se ha guardao un log", Toast.LENGTH_SHORT).show();
        }
        catch(FileNotFoundException e){
            Log.e("Archivo no encontrado", e.getMessage(), e);
        }
        catch(IOException e){
            Log.e("Error al escribir", e.getMessage(), e);
        }
    }
}
