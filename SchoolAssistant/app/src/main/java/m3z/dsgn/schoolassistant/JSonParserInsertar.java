package m3z.dsgn.schoolassistant;

import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by M3z on 01/02/2017.
 */

public class JSonParserInsertar {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    private static final String TAG_USER = "user";
    private static final String TAG_PASS = "pass";

    public JSonParserInsertar() {

    }

    public  JSONObject getJSONFromUrl(String urlInput, String user, String pass) {

        HttpURLConnection connection = null;
        BufferedReader readerRespuesta = null;
        BufferedWriter writerLlamada = null;
        OutputStream streamLlamada = null;
        InputStream streamRespuesta = null;
        StringBuffer bufferRespuesta = null;


        try{
            URL url = new URL(urlInput);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();

            //Establecemos los parámetros
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(TAG_USER, user)
                    .appendQueryParameter(TAG_PASS, pass);
            String parametros = builder.build().getEncodedQuery();

            //Creamos un stream de salida y escribimos en él los parámetros POST
            streamLlamada = connection.getOutputStream();
            writerLlamada = new BufferedWriter(new OutputStreamWriter(streamLlamada));
            writerLlamada.write(parametros);
            writerLlamada.flush();

            //Obtenemos la respuesta
            streamRespuesta = connection.getInputStream();
            readerRespuesta = new BufferedReader(new InputStreamReader(streamRespuesta));

            //Leemos los valores devueltos y los añadimos a un StringBuffer
            bufferRespuesta = new StringBuffer();
            String line ="";
            while ((line = readerRespuesta.readLine()) != null){
                bufferRespuesta.append(line);
            }

            //Convertimos a String el buffer de String
            //creamos un JSON a partir de la respuesta
            json = bufferRespuesta.toString();
            jObj = new JSONObject(json);

            //closes
            readerRespuesta.close();
            streamRespuesta.close();
            writerLlamada.close();
            streamLlamada.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Devolvemos el JSON
        return jObj;
    }
}
