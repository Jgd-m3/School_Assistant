package m3z.dsgn.schoolassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M3z on 01/02/2017.
 */

public class Registrar extends AppCompatActivity {


    private static String urlInsert = "http://www.m3nanana.esy.es/accesoservicio/insert.php";
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    private boolean estaRegistrando,
                    registroBien;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_layout);

    }

    public void Ok(View v){

        EditText user = (EditText) findViewById(R.id.userReg);
        EditText pass = (EditText) findViewById(R.id.passReg);

        if(user.getText().toString() != null && user.getText().toString().length() > 0 &&
                pass.getText().toString() != null && pass.getText().toString().length() > 0){
            estaRegistrando= true;
            registroBien = true;
            registerUser(user.getText().toString(),pass.getText().toString() );

            while(estaRegistrando){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (registroBien) setResult(RESULT_OK, new Intent());
            else setResult(RESULT_CANCELED, new Intent());
            finish();
        }
    }

    private void registerUser(String user, String pass){
        new InsertUser().execute(user, pass);
    }

    public void Cancel(View v){
        finish();
    }



    class InsertUser extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registrar.this);
            pDialog.setMessage("Guardando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            JSonParserInsertar jParser = new JSonParserInsertar();

            //obtener el JSON from URL
            JSONObject json = jParser.getJSONFromUrl(urlInsert, args[0],args[1]);


            try {
                //Verificamos si la select devolvió datos
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Ha devuelto datos
                    publishProgress("Correcto");
                } else  publishProgress(json.getString("message"));
            } catch (JSONException e) {
                registroBien = false;
                e.printStackTrace();
            }
            estaRegistrando = false;
            return null;
        }

        protected void onProgressUpdate(String... args){
        }

        protected void onPostExecute(String file_url) { pDialog.dismiss(); }

    }
}
