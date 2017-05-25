package m3z.dsgn.schoolassistant;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Localizacion extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int permisos=0;
    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, permisos);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng local = new LatLng(38.990553, -3.920864);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 10F));
        mMap.addMarker(new MarkerOptions().position(local).title("IES MAESTRITO"));

        //mMap.animateCamera(CameraUpdateFactory.newLatLng(local));
    }

    public void ir(View view){
        EditText lati=(EditText)findViewById(R.id.idLatitud);
        EditText longi=(EditText)findViewById(R.id.idLongitud);
        try{
            LatLng pos = new LatLng(Double.parseDouble(lati.getText().toString()), Double.parseDouble(longi.getText().toString()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15F));
        }catch(Exception e){
            Toast.makeText(this, "METE BIEN LAS COORDENADAS", Toast.LENGTH_SHORT);
        }

    }


    public void irGPS(View view) {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mostrarPosicion(loc);


        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            public void onProviderDisabled(String provider){}

            public void onProviderEnabled(String provider){}

            public void onStatusChanged(String provider, int status, Bundle extras){}

        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener);

    }
    private void mostrarPosicion(Location loc) {
        if (loc != null) {
            LatLng posicion = new LatLng(loc.getLatitude(), loc.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion, 17F));
            mMap.addMarker(new MarkerOptions().position(posicion).title("Edificio"));

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try{
            locManager.removeUpdates(locListener);
        }
        catch(Exception e){

        }

    }
}
