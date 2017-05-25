package m3z.dsgn.schoolassistant;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by M3z on 01/02/2017.
 */

public class ShowBackup extends AppCompatActivity {
    private String user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showbackup);

        int permissionCheckExternalStorage = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheckExternalStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        user = getIntent().getExtras().getString("user");
        leerBackup();
    }


    public void exitClick(View v){
        finish();
    }

    private void leerBackup(){

        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            try {
                String ruta= String.format("%s/%sBUP.txt",
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString(),
                        user);

                Log.i("MIAS!!!! -------- :", "Directory: " + ruta);
                FileInputStream file = new FileInputStream(ruta);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file));
                //String rdo = bufferedReader.readLine();
                StringBuilder line = new StringBuilder();
                String rdo;
                while ((rdo = bufferedReader.readLine()) != null) {
                    line.append(rdo + "\n");
                }
                ((TextView)findViewById(R.id.txtBackup)).setText(line.toString());

                // cerramos el fichero
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
                e.getCause();
                Log.i("MIASSSS ----- ", e.toString());
                Toast.makeText(this, "NO BACKUP", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
