package m3z.dsgn.schoolassistant;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by M3z on 20/11/2016.
 */

public class Preferencia extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_xml);
    }
}
