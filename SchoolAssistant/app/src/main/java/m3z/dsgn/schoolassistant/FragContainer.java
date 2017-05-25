package m3z.dsgn.schoolassistant;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by M3z on 01/02/2017.
 */

public class FragContainer extends AppCompatActivity implements Aviso{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragcontainer);
    }


    public void haClicado(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentB b = (FragmentB) manager.findFragmentById(R.id.fragB);
        b.mostarLog();
    }
}
