package jamiesmyth.mobilecomputingapplication;

import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private TextView mTeamName;
    private Location mLocation;
    double userLat;
    double userLong;
    double bestTeamLat;
    double bestTeamLong;
    int teamAPIID = 0;
    boolean findTeamPressed = false;
    RequestQueue mRequestQueue;
    private String badgePNG;
    ImageView clubBadge;
    TextView stadiumText;
    private ProgressDialog loadingLocation;
    boolean isLocationOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);


        mTeamName = (TextView) findViewById(R.id.Team_Name_Text);

        clubBadge = (ImageView) findViewById(R.id.Club_Badge_Image);

        stadiumText = (TextView) findViewById(R.id.Stadium_Text);

        mRequestQueue = Volley.newRequestQueue(this);

        // Waits for the user to press the find nearest team button
        final Button button = (Button) findViewById(R.id.find_nearest_team);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                checkLocationEnabled();

                // Checks to make sure the users location is on
                if (isLocationOn) {
                    if (userLat > 0 && loadingLocation != null) {
                        // If the location is on calls findNearestTeam method, allows the user to press Upcoming Fixtures and Results
                        // button and clears the loading message
                        findNearestTeam();
                        findTeamPressed = true;
                        loadingLocation.dismiss();
                    } else if (userLat > 0) {
                        findNearestTeam();
                        findTeamPressed = true;
                    } else {
                        // Displays retrieving location message
                        loadingLocation = ProgressDialog.show(MainActivity.this, "Retrieving Location", "Just retrieving your location, won't be long!", true);
                    }
                }
            }
        });

        // Waits for user to press Upcoming Fixtures button
        final Button fixturesbutton = (Button) findViewById(R.id.Upcoming_Fixtures_Button);
        fixturesbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Checks to make sure the user has pressed to find a nearest team
                if (findTeamPressed == true)
                {
                    // Loads up the next screen passing the teamAPIID variable
                    Intent myIntent = new Intent(MainActivity.this, FragmentHolderActivity.class);
                    myIntent.putExtra("intVariableName", teamAPIID);
                    startActivity(myIntent);
                }
                else
                {
                    // If the user hasn't pressed the button a toast will pop up informing them they need to do so
                    Toast.makeText(getBaseContext(), "You need to find a team" , Toast.LENGTH_SHORT ).show();
                }
            }
        });

        // Waits for user to press Upcoming Fixtures button
        final Button directionsButton = (Button) findViewById(R.id.Get_Directions_Button);
        directionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Checks to make sure the user has pressed to find a nearest team
                if (findTeamPressed == true)
                {
                    // Loads up the next screen passing the teamAPIID variable
                    Intent myIntent = new Intent(MainActivity.this, DirectionsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putDouble("userLat", userLat);
                    extras.putDouble("userLong", userLong);
                    extras.putDouble("teamLat", bestTeamLat);
                    extras.putDouble("teamLong", bestTeamLong);
                    myIntent.putExtras(extras);
                    startActivity(myIntent);
                }
                else
                {
                    // If the user hasn't pressed the button a toast will pop up informing them they need to do so
                    Toast.makeText(getBaseContext(), "You need to find a team" , Toast.LENGTH_SHORT ).show();
                }
            }
        });

        // Sets up Google API client to be used for location
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    protected void onStart() {
        super.onStart();

        // Connects Google API Client
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    protected void onStop() {
        super.onStop();

        // Stops Google API Client
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Requests location updates when connected
        requestLocationUpdates();

        return;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // Request user permission to access location
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If permission was granted then request updates

                    requestLocationUpdates();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }


    @Override
    public void onLocationChanged(Location location) {
        // Checks to see if the user has a retrieving location message
        if (loadingLocation != null)
        {
            // If they do, removes message stores Latitude and Longitude and begins to findNearestTeam so the user doesn't have to press
            // find nearest team button again
            loadingLocation.dismiss();
            userLat = location.getLatitude();
            userLong = location.getLongitude();
            findTeamPressed = true;
            findNearestTeam();
        }
        // Stores users latitude and longitude on each update and stops location updates running
        userLat = location.getLatitude();
        userLong = location.getLongitude();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(500);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            return;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        if (mLocation != null) {
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    public void findNearestTeam() {

        // Local variables
        ArrayList<Double> teamLats = new ArrayList<Double>();
        ArrayList<Double> teamLongs = new ArrayList<Double>();
        ArrayList<Integer> teamIDs = new ArrayList<Integer>();
        ArrayList<String> teamNames = new ArrayList<String>();
        ArrayList<String> teamStadiums = new ArrayList<>();
        int bestID = 0;
        float distanceInMeters = 0;
        float bestDistanceInMeters = 100000;

        // Set arrays up
        teamLats = setTeamLats(teamLats);
        teamLongs = setTeamLongs(teamLongs);
        teamNames = setTeamNames(teamNames);
        teamIDs = setTeamIDS(teamIDs);
        teamStadiums = setTeamStadiums(teamStadiums);

        // Checks using arrays and users current location which team is closest to them
        for (int i = 0; i < teamIDs.size(); i++) {
            Location loc1 = new Location("");
            loc1.setLatitude(userLat);
            loc1.setLongitude(userLong);

            Location loc2 = new Location("");
            loc2.setLatitude(teamLats.get(i));
            loc2.setLongitude(teamLongs.get(i));

            distanceInMeters = loc1.distanceTo(loc2);

            if (distanceInMeters < bestDistanceInMeters) {
                bestDistanceInMeters = distanceInMeters;
                bestID = i;
                bestTeamLat = teamLats.get(i);
                bestTeamLong = teamLongs.get(i);
            }
        }

        // Displays relevant information (Badge, Name and Statdium)
        requestTeamBadge(teamIDs.get(bestID));

        mTeamName.setText(teamNames.get(bestID));

        stadiumText.setText(teamStadiums.get(bestID));

        teamAPIID = teamIDs.get(bestID);

    }

    public void checkLocationEnabled()
    {
        // Method to check whether the user has their location switched on if not will open their settings allowing them to turn it on
        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    isLocationOn = true;
                }
            });
            dialog.setNegativeButton(this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
        else
        {
            isLocationOn = true;
        }
    }

    public ArrayList setTeamLats(ArrayList teamLats)
    {
        // Sets teams latitudes array up
        teamLats.add(50.735278);
        teamLats.add(51.5549);
        teamLats.add(53.789167);
        teamLats.add(51.481667);
        teamLats.add(51.398333);
        teamLats.add(53.438889);
        teamLats.add(53.746111);
        teamLats.add(52.620278);
        teamLats.add(53.430828);
        teamLats.add(53.483056);
        teamLats.add(53.463056);
        teamLats.add(54.578333);
        teamLats.add(50.905833);
        teamLats.add(52.988333);
        teamLats.add(54.9144);
        teamLats.add(51.6422);
        teamLats.add(51.603333);
        teamLats.add(51.649836);
        teamLats.add(52.509167);
        teamLats.add(51.538611);

        return teamLats;

    }

    public ArrayList setTeamLongs(ArrayList teamLongs)
    {
        // Sets team longitudes up
        teamLongs.add(-1.838333);
        teamLongs.add(-0.108611);
        teamLongs.add(-2.230278);
        teamLongs.add(-0.191111);
        teamLongs.add(-0.085556);
        teamLongs.add(-2.966389);
        teamLongs.add(-0.367778);
        teamLongs.add(-1.142222);
        teamLongs.add(-2.960847);
        teamLongs.add(-2.200278);
        teamLongs.add(-2.291389);
        teamLongs.add(-1.216944);
        teamLongs.add(-1.391111);
        teamLongs.add(-2.175556);
        teamLongs.add(-1.3882);
        teamLongs.add(-3.9351);
        teamLongs.add(-0.065833);
        teamLongs.add(-0.401486);
        teamLongs.add(-1.963889);
        teamLongs.add(-0.016389);

        return teamLongs;
    }

    public ArrayList setTeamNames(ArrayList teamNames)
    {
        // Sets team names up
        teamNames.add("AFC Bournemouth");
        teamNames.add("Arsenal");
        teamNames.add("Burnley");
        teamNames.add("Chelsea");
        teamNames.add("Crystal Palace");
        teamNames.add("Everton");
        teamNames.add("Hull City");
        teamNames.add("Leicester City");
        teamNames.add("Liverpool");
        teamNames.add("Manchester City");
        teamNames.add("Manchester United");
        teamNames.add("Middlesborough");
        teamNames.add("Southampton");
        teamNames.add("Stoke City");
        teamNames.add("Sunderland");
        teamNames.add("Swansea City");
        teamNames.add("Tottenham Hotspur");
        teamNames.add("Watford");
        teamNames.add("West Bromwich Albion");
        teamNames.add("West Ham United");

        return teamNames;
    }

    public ArrayList setTeamIDS(ArrayList teamIDs)
    {
        // Sets the different ID's of the teams for the API calls
        teamIDs.add(1044);
        teamIDs.add(57);
        teamIDs.add(328);
        teamIDs.add(61);
        teamIDs.add(358);
        teamIDs.add(62);
        teamIDs.add(322);
        teamIDs.add(338);
        teamIDs.add(64);
        teamIDs.add(65);
        teamIDs.add(66);
        teamIDs.add(343);
        teamIDs.add(340);
        teamIDs.add(70);
        teamIDs.add(71);
        teamIDs.add(72);
        teamIDs.add(73);
        teamIDs.add(346);
        teamIDs.add(74);
        teamIDs.add(563);

        return teamIDs;
    }

    public ArrayList setTeamStadiums(ArrayList teamStadiums)
    {
        // Sets team stadiums up
        teamStadiums.add("Vitality Stadium");
        teamStadiums.add("Emirates Stadium");
        teamStadiums.add("Turf Moor");
        teamStadiums.add("Stamford Bridge");
        teamStadiums.add("Selhurst Park");
        teamStadiums.add("Goodison Park");
        teamStadiums.add("KCOM Stadium");
        teamStadiums.add("King Power Stadium");
        teamStadiums.add("Anfield");
        teamStadiums.add("Etihad Stadium");
        teamStadiums.add("Old Trafford");
        teamStadiums.add("Riverside Stadium");
        teamStadiums.add("St.Mary's Stadium");
        teamStadiums.add("Bet365 Stadium");
        teamStadiums.add("Stadium of Light");
        teamStadiums.add("Liberty Stadium");
        teamStadiums.add("White Hart Lane");
        teamStadiums.add("Vicarage Road");
        teamStadiums.add("The Hawthorns");
        teamStadiums.add("London Stadium");

        return teamStadiums;
    }

    public void requestTeamBadge(int apiID)
    {
        // Method to get and set the team badge from fifabuddy API
        String badgeURL = "http://fifabuddy.net/api/crest/" + apiID;

        StringRequest badgeStringRequest = new StringRequest
                (Request.Method.GET, badgeURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        badgePNG = response.substring(1, response.length() -1);

                        Picasso.with(clubBadge.getContext()).load(badgePNG).into(clubBadge);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                       clubBadge.setImageDrawable(getResources().getDrawable(R.drawable.sports_badge_recognition));

                    }
                });

        mRequestQueue.add(badgeStringRequest);
    }
}
