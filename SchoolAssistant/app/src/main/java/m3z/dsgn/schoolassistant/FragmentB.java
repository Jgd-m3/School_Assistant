package m3z.dsgn.schoolassistant;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by M3z on 01/02/2017.
 */

public class FragmentB extends android.support.v4.app.Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentb, container, false);

    }


    public void mostarLog(){

        try {
            InputStream file= getContext().openFileInput("regLogins.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(file));
            //String rdo = bufferedReader.readLine();
            StringBuilder sb=new StringBuilder() ;
            String line;
            int progreso=1;
            while((line = bf.readLine())!=null){
                sb.append(line);
                progreso++;
            }
            file.close();
        } catch (FileNotFoundException e) {
            Log.e("Archivo no encontrado", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("Error al escribir", e.getMessage(), e);
        }
    }
}
