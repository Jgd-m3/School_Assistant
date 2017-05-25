package m3z.dsgn.schoolassistant;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;


/**
 * Created by M3z on 20/11/2016.
 */

public class MyAdapter extends BaseAdapter {

    private final Activity activity;
    private final Vector<String> listaTitulos;
    private final Vector<String> listaDetalles;

    public MyAdapter(Activity actividad, Vector<String> list0, Vector<String> list1){
        super();
        Log.i("MIAS ---------------- ", "entra al MyAdapter");

        this.activity = actividad;
        this.listaTitulos = list0;
        this.listaDetalles = list1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("MIAS ---------------- ", "Ha getView");

        LayoutInflater inf = activity.getLayoutInflater();
        View view = inf.inflate(R.layout.element_list, null, true);
        TextView txtV1 = (TextView) view.findViewById(R.id.tvListaNombre);
        txtV1.setText(listaTitulos.elementAt(position));
        TextView txtV2 = (TextView) view.findViewById(R.id.tvListaCodigo);
        txtV2.setText(listaDetalles.elementAt(position));
        ImageView imgV = (ImageView) view.findViewById(R.id.ivListaImagen);
        imgV.setImageResource(R.drawable.escribiendo);
        Log.i("MIAS ---------------- ", "se han cargado los resources en el adaptador");

        return view;
    }

    @Override
    public int getCount() {
        return listaTitulos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTitulos.elementAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
