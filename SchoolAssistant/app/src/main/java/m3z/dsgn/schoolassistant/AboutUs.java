package m3z.dsgn.schoolassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import m3z.dsgn.schoolassistant.R;

/**
 * Created by M3z on 19/11/2016.
 */

public class AboutUs extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        TextView tv = (TextView) findViewById(R.id.textViewAbout);

        InputStream file;
        StringBuffer sb=new StringBuffer();
        try{
            file=getResources().openRawResource(R.raw.about);
            BufferedReader buffer=new BufferedReader(new InputStreamReader(file));
            String linea;

            while((linea=buffer.readLine())!=null){
                sb.append(linea);
            }
            file.close();
            tv.setText(sb.toString());
        }
        catch(FileNotFoundException e){
            Log.e("Archivo no encontrado", e.getMessage(), e);
        }
        catch(IOException e){
            Log.e("Error al escribir", e.getMessage(), e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    public void cerrarAbout(View v){
        finish();
    }
}
