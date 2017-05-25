package m3z.dsgn.schoolassistant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by M3z on 01/02/2017.
 */

public class FragmentA extends android.support.v4.app.Fragment implements View.OnClickListener {

    Aviso aviso;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentelement, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aviso = (Aviso) getActivity();
    }

    @Override
    public void onClick(View v) {
        aviso.haClicado();
    }
}
