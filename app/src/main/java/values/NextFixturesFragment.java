package values;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.drive.internal.StringListResponse;
import com.google.android.gms.vision.text.Text;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jamiesmyth.mobilecomputingapplication.AssignToUI;
import jamiesmyth.mobilecomputingapplication.CurrentDate;
import jamiesmyth.mobilecomputingapplication.GSONModel;
import jamiesmyth.mobilecomputingapplication.MainActivity;
import jamiesmyth.mobilecomputingapplication.R;
import jamiesmyth.mobilecomputingapplication.SaveAndReadToInternalStorage;

import java.io.File;
import java.io.FileOutputStream;

public class NextFixturesFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";
    private int teamAPI = 99;
    private String jsonURLHome = " ";
    private String jsonURLAway = " ";
    ArrayList<String> awayTeams = new ArrayList<>();
    ArrayList<String> homeTeams = new ArrayList<>();
    ArrayList<String> awayTeamsDates = new ArrayList<>();
    ArrayList<String> homeTeamsDates = new ArrayList<>();
    RequestQueue mRequestQueue;
    String filenameHomeFixtures = "jsonArrayHomeFixtures";
    String filenameAwayFixtures = "jsonArrayAwayFixtures";
    String filenameLastKnownDate = "lastKnownDate";
    GSONModel gsonModel;
    String jsonSaved = " ";
    private ProgressDialog loadingJson;
    String currentDate = " ";

    TextView currentDateText;

    CardView homeTeamOneCard;
    CardView homeTeamTwoCard;
    CardView homeTeamThreeCard;
    CardView homeTeamFourCard;
    CardView homeTeamFiveCard;

    CardView awayTeamOneCard;
    CardView awayTeamTwoCard;
    CardView awayTeamThreeCard;
    CardView awayTeamFourCard;
    CardView awayTeamFiveCard;

    TextView awayTeamOne;
    TextView awayTeamTwo;
    TextView awayTeamThree;
    TextView awayTeamFour;
    TextView awayTeamFive;

    TextView homeTeamOne;
    TextView homeTeamTwo;
    TextView homeTeamThree;
    TextView homeTeamFour;
    TextView homeTeamFive;

    TextView awayTeamDateOne;
    TextView awayTeamDateTwo;
    TextView awayTeamDateThree;
    TextView awayTeamDateFour;
    TextView awayTeamDateFive;

    TextView homeTeamDateOne;
    TextView homeTeamDateTwo;
    TextView homeTeamDateThree;
    TextView homeTeamDateFour;
    TextView homeTeamDateFive;


    public NextFixturesFragment() {
    }

    public static NextFixturesFragment newInstance(int page, int teamAPIID) {
        // Sets Fragment up storing teamAPIID
        NextFixturesFragment fragment = new NextFixturesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, teamAPIID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_next_fixtures, container, false);

        // Sets CardViews and TextViews so they can be changed
        currentDateText = (TextView)rootView.findViewById(R.id.Current_Date);

        homeTeamOneCard = (CardView)rootView.findViewById(R.id.Home_Team_One);
        homeTeamTwoCard = (CardView)rootView.findViewById(R.id.Home_Team_Two);
        homeTeamThreeCard = (CardView)rootView.findViewById(R.id.Home_Team_Three);
        homeTeamFourCard = (CardView)rootView.findViewById(R.id.Home_Team_Four);
        homeTeamFiveCard = (CardView)rootView.findViewById(R.id.Home_Team_Five);

        awayTeamOneCard = (CardView)rootView.findViewById(R.id.Away_Team_One);
        awayTeamTwoCard = (CardView)rootView.findViewById(R.id.Away_Team_Two);
        awayTeamThreeCard = (CardView)rootView.findViewById(R.id.Away_Team_Three);
        awayTeamFourCard = (CardView)rootView.findViewById(R.id.Away_Team_Four);
        awayTeamFiveCard = (CardView)rootView.findViewById(R.id.Away_Team_Five);

        awayTeamOne = (TextView)rootView.findViewById(R.id.Away_Team_Name_One);
        awayTeamTwo = (TextView)rootView.findViewById(R.id.Away_Team_Name_Two);
        awayTeamThree = (TextView)rootView.findViewById(R.id.Away_Team_Name_Three);
        awayTeamFour = (TextView)rootView.findViewById(R.id.Away_Team_Name_Four);
        awayTeamFive = (TextView)rootView.findViewById(R.id.Away_Team_Name_Five);

        awayTeamDateOne = (TextView)rootView.findViewById(R.id.Away_Team_Date_One);
        awayTeamDateTwo = (TextView)rootView.findViewById(R.id.Away_Team_Date_Two);
        awayTeamDateThree = (TextView)rootView.findViewById(R.id.Away_Team_Date_Three);
        awayTeamDateFour = (TextView)rootView.findViewById(R.id.Away_Team_Date_Four);
        awayTeamDateFive = (TextView)rootView.findViewById(R.id.Away_Team_Date_Five);

        homeTeamOne = (TextView)rootView.findViewById(R.id.Home_Team_Name_One);
        homeTeamTwo = (TextView)rootView.findViewById(R.id.Home_Team_Name_Two);
        homeTeamThree = (TextView)rootView.findViewById(R.id.Home_Team_Name_Three);
        homeTeamFour = (TextView)rootView.findViewById(R.id.Home_Team_Name_Four);
        homeTeamFive = (TextView)rootView.findViewById(R.id.Home_Team_Name_Five);

        homeTeamDateOne = (TextView)rootView.findViewById(R.id.Home_Team_Date_One);
        homeTeamDateTwo = (TextView)rootView.findViewById(R.id.Home_Team_Date_Two);
        homeTeamDateThree = (TextView)rootView.findViewById(R.id.Home_Team_Date_Three);
        homeTeamDateFour = (TextView)rootView.findViewById(R.id.Home_Team_Date_Four);
        homeTeamDateFive = (TextView)rootView.findViewById(R.id.Home_Team_Date_Five);

        // Sets Request Queue up
        mRequestQueue = Volley.newRequestQueue(getActivity());

        // Retrieves and stores teamAPIID
        teamAPI = getArguments().getInt(ARG_PAGE_NUMBER, -1);

        // Sets API URL's up
        jsonURLHome = "http://api.football-data.org/v1/teams/" + teamAPI + "/fixtures?timeFrame=n35&venue=home";

        jsonURLAway = "http://api.football-data.org/v1/teams/" + teamAPI + "/fixtures?timeFrame=n35&venue=away";

        // Requests JSON
        jsonRequesting(jsonURLHome, jsonURLAway);

        return rootView;
    }

    public void jsonRequesting (String jsonUrlHome, String jsonUrlAway)
    {
        // Method that handles all the JSON requests with error handling
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, jsonUrlHome, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        // Removes message informing the user data is being retrieved
                        loadingJson.dismiss();
                        AssignToUI.returnVisibility(homeTeamOneCard, homeTeamTwoCard, homeTeamThreeCard, homeTeamFourCard, homeTeamFiveCard);
                        // Calls GSON parsing method
                        homeFixturesGSON(response, true);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        // Removes message informing the user data is being retrieved if connection is lost
                        loadingJson.dismiss();
                        AssignToUI.returnVisibility(homeTeamOneCard, homeTeamTwoCard, homeTeamThreeCard, homeTeamFourCard, homeTeamFiveCard);

                        JSONObject jsonHomeFixtures = null;
                        // Calls read file to retrieve previous requested JSON Object
                        jsonSaved = SaveAndReadToInternalStorage.readJsonFromLocalStorage(filenameHomeFixtures, getContext());

                        // Converts String to JSONObject
                        try
                        {
                            jsonHomeFixtures = new JSONObject(jsonSaved);
                        }
                        catch (Exception e)
                        {

                        }

                        if (jsonHomeFixtures != null) {
                            // Calls GSON parsing method
                            homeFixturesGSON(jsonHomeFixtures, false);
                        }
                        else
                        {
                            // If no file is found changes the visibilty of CardViews for a better user experience
                            AssignToUI.changeVisibility(awayTeams,homeTeamOneCard, homeTeamTwoCard, homeTeamThreeCard, homeTeamFourCard, homeTeamFiveCard);
                        }
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };

        // Displays message informing the user data is being retrieved and hides CardViews for a better user experience
        loadingJson = ProgressDialog.show(getActivity(), "Loading Fixtures and Results", "Just Retrieving Your Teams Fixtures and Results", true);
        AssignToUI.changeVisibility(awayTeams,homeTeamOneCard, homeTeamTwoCard, homeTeamThreeCard, homeTeamFourCard, homeTeamFiveCard);
        AssignToUI.changeVisibility(homeTeams, awayTeamOneCard, awayTeamTwoCard, awayTeamThreeCard, awayTeamFourCard, awayTeamFiveCard);

        mRequestQueue.add(jsObjRequest);

        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest
                (Request.Method.GET, jsonUrlAway, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        // Returns CardView Visibilities
                        AssignToUI.returnVisibility(awayTeamOneCard, awayTeamTwoCard, awayTeamThreeCard, awayTeamFourCard, awayTeamFiveCard);

                        //Calls GSON parsing method
                        awayFixturesGSON(response, true);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        AssignToUI.returnVisibility(awayTeamOneCard, awayTeamTwoCard, awayTeamThreeCard, awayTeamFourCard, awayTeamFiveCard);

                        JSONObject jsonAwayFixtures = null;
                        // Calls read file to retrieve previous requested JSON Object
                        jsonSaved = SaveAndReadToInternalStorage.readJsonFromLocalStorage(filenameAwayFixtures, getContext());

                        // Converts String into JSONObject
                        try
                        {
                            jsonAwayFixtures = new JSONObject(jsonSaved);
                        }
                        catch (Exception e)
                        {

                        }

                        if (jsonAwayFixtures != null) {
                            // Calls GSON method
                            awayFixturesGSON(jsonAwayFixtures, false);
                        }
                        else
                        {
                            // If no file is found changes the visibilty of CardViews for a better user experience
                            AssignToUI.changeVisibility(homeTeams, awayTeamOneCard, awayTeamTwoCard, awayTeamThreeCard, awayTeamFourCard, awayTeamFiveCard);
                        }
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };

        mRequestQueue.add(jsObjRequest2);
    }

    public void homeFixturesGSON (JSONObject response, boolean online)
    {
        // Sets up GSON model and loops through retrieving correct data from those classes
        Gson gson = new Gson();
        gsonModel = gson.fromJson(response.toString(), GSONModel.class);
        for (int i = 0; i < gsonModel.getFixtures().size(); i++) {
            awayTeams.add(gsonModel.getFixtures().get(i).getAwayTeamName());
            awayTeamsDates.add(sortDate(gsonModel.getFixtures().get(i).getDate()));
        }

        // Checks to se if the user is online then saves the file if they are if not displays them a message informing they are being shown
        // data from when they were previous online
        if (online)
        {
            currentDate  = CurrentDate.getCurrentDate(currentDate);
            currentDateText.setText("Updated: " + currentDate);
            SaveAndReadToInternalStorage.saveJsonToLocalStorage(response, filenameHomeFixtures, getContext());
            SaveAndReadToInternalStorage.saveDateToLocalStorage(currentDate, filenameLastKnownDate, getContext());
        }
        else
        {
            currentDate = SaveAndReadToInternalStorage.readDateFromLocalStorage(filenameLastKnownDate, getContext());
            currentDateText.setText("Last Updated: " + currentDate);
            Toast.makeText(getContext(), "You are offline, Displaying fixtures and results from when you were previously online!" , Toast.LENGTH_SHORT ).show();
        }

        // Assigns to the UI
        AssignToUI.assignToTeamNameUI(awayTeamOne, awayTeamTwo, awayTeamThree, awayTeamFour, awayTeamFive, awayTeams);
        AssignToUI.assignToTeamDateUI(awayTeamDateOne, awayTeamDateTwo, awayTeamDateThree, awayTeamDateFour, awayTeamDateFive, awayTeamsDates);
        AssignToUI.changeVisibility(awayTeams,homeTeamOneCard, homeTeamTwoCard, homeTeamThreeCard, homeTeamFourCard, homeTeamFiveCard);
    }

    public void awayFixturesGSON (JSONObject response, boolean online)
    {
        // Sets up GSON model and loops through retrieving correct data from those classes
        Gson gson = new Gson();
        gsonModel = gson.fromJson(response.toString(), GSONModel.class);
        for (int i = 0; i < gsonModel.getFixtures().size(); i++) {
            homeTeams.add(gsonModel.getFixtures().get(i).getHomeTeamName());
            homeTeamsDates.add(sortDate(gsonModel.getFixtures().get(i).getDate()));
        }

        // Checks to se if the user is online then saves the file if they are if not displays them a message informing they are being shown
        // data from when they were previous online
        if (online)
        {
            SaveAndReadToInternalStorage.saveJsonToLocalStorage(response, filenameAwayFixtures, getContext());
        }
        else
        {
            Toast.makeText(getContext(), "You are offline, Displaying fixtures and results from when you were previously online!" , Toast.LENGTH_SHORT ).show();
        }

        // Assigns to the UI
        AssignToUI.assignToTeamNameUI(homeTeamOne, homeTeamTwo, homeTeamThree, homeTeamFour, homeTeamFive, homeTeams);
        AssignToUI.assignToTeamDateUI(homeTeamDateOne, homeTeamDateTwo, homeTeamDateThree, homeTeamDateFour, homeTeamDateFive, homeTeamsDates);
        AssignToUI.changeVisibility(homeTeams, awayTeamOneCard, awayTeamTwoCard, awayTeamThreeCard, awayTeamFourCard, awayTeamFiveCard);
    }

    public String sortDate (String date)
    {
        // Method to sort the date retrieved from the API so it is more readable for the user
        String[] dateArray = date.split("T");
        String returnDate = "";

        returnDate += dateArray[0].substring(8, 10) + "/"
                + dateArray[0].substring(5, 7) + "/"
                + dateArray[0].substring(0,4) + " "
                + dateArray[1].substring(0, 8);

        return returnDate;
    }

    public Map<String, String> getHttpHeaders()
    {
        // Method for the API token
        Map<String, String> params = new HashMap<>();
        params.put("X-Auth-Token", "a00bf892f6ef42b795b494e30f8804e0");
        return params;

    }


}
