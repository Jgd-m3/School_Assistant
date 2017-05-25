package m3z.dsgn.schoolassistant;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by M3z on 20/11/2016.
 */

public class SetPreferences extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new Preferencia()).commit();

    }
}
