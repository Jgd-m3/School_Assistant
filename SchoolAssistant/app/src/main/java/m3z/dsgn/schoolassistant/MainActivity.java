package m3z.dsgn.schoolassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String typeUser;
    private Button elije;
    private EditText txtUser;
    private EditText txtPass;
    private ProgressDialog pDialog;
    private boolean correctPass,
                    trabajandoJSON;
	private static String urlSelect = "http://www.m3nanana.esy.es/accesoservicio/selectindividual.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    private static final String TAG_PASSES = "passes";
    private static final String TAG_PASS = "pass";
	JSONArray provArray = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //metemos layout
        setContentView(R.layout.activity_main);

        // creamos objetos de los componentes principales
        elije = (Button) findViewById(R.id.btnCtxMenu);
        txtPass = (EditText) findViewById(R.id.txtPassMain);
        txtUser= (EditText) findViewById(R.id.txtUserMain);
        typeUser = null;
        txtUser.setText("");
        txtPass.setText("");
        //petamos el contextMenu
        registerForContextMenu(elije);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        correctPass = false;
        trabajandoJSON = false;
    }

    protected void mainConnClick(View view){

        //usuario y pass en una String
        String user, pass;
        Intent i = null;
        trabajandoJSON = true;

        user =  txtUser.getText().toString();
        pass = txtPass.getText().toString();
        if(typeUser != null && (user != null && user.length() > 0)
                            && (pass != null && pass.length()>0)){

            new SelectPass(pass).execute(user);
            // esperamos a k el JSON conecte
            while(trabajandoJSON) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(correctPass){
            if (typeUser.equals(getText(R.string.contextOp1))) i = new Intent(this, InicialPadre.class );
            else {
                // TODO: 18/11/2016  HAY QUE CAMBIARLO AL IMPLEMENTAR FUNCIONES DE PROFESOR
                // TODO: EN FUTURAS UPDATES, (de momento metemos el del padre)
                i = new Intent(this, InicialPadre.class );
            }
            i.putExtra("user", user);
            i.putExtra("pass", pass);
        }

        if (i != null) startActivityForResult(i, 123); //clave 123
        else if(typeUser == null || (user == null || user.length() == 0)
                || (pass == null || pass.length()==0))
            Toast.makeText(this, getText(R.string.rellenaMain), Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, getText(R.string.logfail), Toast.LENGTH_SHORT).show();

    }

    public void registrar(View v){
        Intent j = new Intent(this, Registrar.class);
        startActivityForResult(j, 321);;
    }


    protected void mainExitClick(View view){
        finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater infl = getMenuInflater();

        if(v.getId() == R.id.btnCtxMenu) infl.inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo inf =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case  R.id.cxtPadre:
                typeUser = getText(R.string.contextOp1).toString();
                elije.setText(typeUser);
                return true;
            case R.id.cxtProfe:
                typeUser = getText(R.string.contextOp2).toString();
                elije.setText(typeUser);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        txtPass = (EditText) findViewById(R.id.txtPassMain);
        txtUser= (EditText) findViewById(R.id.txtUserMain);
        txtPass.setText("");
        txtUser.setText("");

        if(requestCode==123 && resultCode==RESULT_OK){
            Toast.makeText(this, getText(R.string.despedidaMain) + " " + data.getStringExtra("user")
                            , Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==321 && resultCode==RESULT_OK){
            Toast.makeText(this, R.string.regSucc, Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==321 && resultCode==RESULT_CANCELED){
            Toast.makeText(this, R.string.regfail, Toast.LENGTH_SHORT).show();
        }

    }

	class SelectPass extends AsyncTask<String, String, String> {
        private String passJSON;

        public SelectPass(String pass){
            passJSON = pass;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Buscando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            JSonParser jParser = new JSonParser();
            JSONObject json = jParser.getJSONFromUrl(urlSelect, args[0]);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    provArray = json.getJSONArray(TAG_PASSES);
                    String passRecibida = provArray.getJSONObject(0).getString(TAG_PASS);
                    correctPass = (passRecibida.equals(passJSON));
                    Log.i("MIAS:--------" ,String.format("La pass preguntada es: %s la recibida es: %s y el bool: %b%n", passJSON,passRecibida, correctPass));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            trabajandoJSON = false;
            return null;
        }

        protected void onProgressUpdate(String... args){ }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }



}
