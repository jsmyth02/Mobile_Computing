package values;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import android.net.Uri;
import android.widget.Toast;

import jamiesmyth.mobilecomputingapplication.AssignToUI;
import jamiesmyth.mobilecomputingapplication.CurrentDate;
import jamiesmyth.mobilecomputingapplication.GSONModel;
import jamiesmyth.mobilecomputingapplication.R;
import jamiesmyth.mobilecomputingapplication.Results;
import jamiesmyth.mobilecomputingapplication.SaveAndReadToInternalStorage;

public class PastResultsFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";
    private int teamApi = 99;
    RequestQueue mRequestQueue;
    private String jsonURLHome = " ";
    private String jsonURLAway = " ";
    ArrayList<String> awayTeams = new ArrayList<>();
    ArrayList<String> homeTeams = new ArrayList<>();
    ArrayList<String> awayTeamsDatesPlayed = new ArrayList<>();
    ArrayList<String> homeTeamsDatesPlayed = new ArrayList<>();
    ArrayList<String> homeGameGoals = new ArrayList<>();
    ArrayList<String> homeGameGoalsAwayTeam = new ArrayList<>();
    ArrayList<String> awayGameGoals = new ArrayList<>();
    ArrayList<String> awayGameGoalsAwayTeam = new ArrayList<>();
    String filenameHomeResults = "jsonArrayHomeResults";
    String filenameAwayResults = "jsonArrayAwayResults";
    String filenameLastKnownDate = "lastKnownDate";
    String jsonSaved = " ";
    String currentDate = " ";

    TextView currentDateText;

    CardView homeTeamResultCardOne;
    CardView homeTeamResultCardTwo;
    CardView homeTeamResultCardThree;
    CardView homeTeamResultCardFour;
    CardView homeTeamResultCardFive;

    CardView awayTeamResultCardOne;
    CardView awayTeamResultCardTwo;
    CardView awayTeamResultCardThree;
    CardView awayTeamResultCardFour;
    CardView awayTeamResultCardFive;

    TextView awayTeamNameOne;
    TextView awayTeamNameTwo;
    TextView awayTeamNameThree;
    TextView awayTeamNameFour;
    TextView awayTeamNameFive;

    TextView awayTeamDateOne;
    TextView awayTeamDateTwo;
    TextView awayTeamDateThree;
    TextView awayTeamDateFour;
    TextView awayTeamDateFive;

    TextView awayTeamResultOne;
    TextView awayTeamResultTwo;
    TextView awayTeamResultThree;
    TextView awayTeamResultFour;
    TextView awayTeamResultFive;

    TextView homeTeamNameOne;
    TextView homeTeamNameTwo;
    TextView homeTeamNameThree;
    TextView homeTeamNameFour;
    TextView homeTeamNameFive;

    TextView homeTeamDateOne;
    TextView homeTeamDateTwo;
    TextView homeTeamDateThree;
    TextView homeTeamDateFour;
    TextView homeTeamDateFive;

    TextView homeTeamResultOne;
    TextView homeTeamResultTwo;
    TextView homeTeamResultThree;
    TextView homeTeamResultFour;
    TextView homeTeamResultFive;


    public PastResultsFragment() {
    }

    public static PastResultsFragment newInstance(int page, int teamAPIID) {
        // Sets Fragment up storing teamAPIID
        PastResultsFragment fragment = new PastResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, teamAPIID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_past_results, container, false);

        // Sets CardViews and TextViews so they can be changed
        currentDateText = (TextView)rootView.findViewById(R.id.Current_Date);

        homeTeamResultCardOne = (CardView) rootView.findViewById(R.id.Home_Team_One_Played);
        homeTeamResultCardTwo = (CardView) rootView.findViewById(R.id.Home_Team_Two_Played);
        homeTeamResultCardThree = (CardView) rootView.findViewById(R.id.Home_Team_Three_Played);
        homeTeamResultCardFour = (CardView) rootView.findViewById(R.id.Home_Team_Four_Played);
        homeTeamResultCardFive = (CardView) rootView.findViewById(R.id.Home_Team_Five_Played);

        awayTeamResultCardOne = (CardView) rootView.findViewById(R.id.Away_Team_One_Played);
        awayTeamResultCardTwo = (CardView) rootView.findViewById(R.id.Away_Team_Two_Played);
        awayTeamResultCardThree = (CardView) rootView.findViewById(R.id.Away_Team_Three_Played);
        awayTeamResultCardFour = (CardView) rootView.findViewById(R.id.Away_Team_Four_Played);
        awayTeamResultCardFive = (CardView) rootView.findViewById(R.id.Away_Team_Five_Played);

        awayTeamNameOne = (TextView) rootView.findViewById(R.id.Away_Team_Name_One_Played);
        awayTeamNameTwo = (TextView) rootView.findViewById(R.id.Away_Team_Name_Two_Played);
        awayTeamNameThree = (TextView) rootView.findViewById(R.id.Away_Team_Name_Three_Played);
        awayTeamNameFour = (TextView) rootView.findViewById(R.id.Away_Team_Name_Four_Played);
        awayTeamNameFive = (TextView) rootView.findViewById(R.id.Away_Team_Name_Five_Played);

        awayTeamDateOne = (TextView) rootView.findViewById(R.id.Away_Team_Date_One_Played);
        awayTeamDateTwo = (TextView) rootView.findViewById(R.id.Away_Team_Date_Two_Played);
        awayTeamDateThree = (TextView) rootView.findViewById(R.id.Away_Team_Date_Three_Played);
        awayTeamDateFour = (TextView) rootView.findViewById(R.id.Away_Team_Date_Four_Played);
        awayTeamDateFive = (TextView) rootView.findViewById(R.id.Away_Team_Date_Five_Played);

        awayTeamResultOne = (TextView) rootView.findViewById(R.id.Away_Team_Result_One);
        awayTeamResultTwo = (TextView) rootView.findViewById(R.id.Away_Team_Result_Two);
        awayTeamResultThree = (TextView) rootView.findViewById(R.id.Away_Team_Result_Three);
        awayTeamResultFour = (TextView) rootView.findViewById(R.id.Away_Team_Result_Four);
        awayTeamResultFive = (TextView) rootView.findViewById(R.id.Away_Team_Result_Five);

        homeTeamNameOne = (TextView) rootView.findViewById(R.id.Home_Team_Name_One_Played);
        homeTeamNameTwo = (TextView) rootView.findViewById(R.id.Home_Team_Name_Two_Played);
        homeTeamNameThree = (TextView) rootView.findViewById(R.id.Home_Team_Name_Three_Played);
        homeTeamNameFour = (TextView) rootView.findViewById(R.id.Home_Team_Name_Four_Played);
        homeTeamNameFive = (TextView) rootView.findViewById(R.id.Home_Team_Name_Five_Played);

        homeTeamDateOne = (TextView) rootView.findViewById(R.id.Home_Team_Date_One_Played);
        homeTeamDateTwo = (TextView) rootView.findViewById(R.id.Home_Team_Date_Two_Played);
        homeTeamDateThree = (TextView) rootView.findViewById(R.id.Home_Team_Date_Three_Played);
        homeTeamDateFour = (TextView) rootView.findViewById(R.id.Home_Team_Date_Four_Played);
        homeTeamDateFive = (TextView) rootView.findViewById(R.id.Home_Team_Date_Five_Played);

        homeTeamResultOne = (TextView) rootView.findViewById(R.id.Home_Team_Result_One);
        homeTeamResultTwo = (TextView) rootView.findViewById(R.id.Home_Team_Result_Two);
        homeTeamResultThree = (TextView) rootView.findViewById(R.id.Home_Team_Result_Three);
        homeTeamResultFour = (TextView) rootView.findViewById(R.id.Home_Team_Result_Four);
        homeTeamResultFive = (TextView) rootView.findViewById(R.id.Home_Team_Result_Five);

        // Sets Request Queue up
        mRequestQueue = Volley.newRequestQueue(getActivity());

        // Retrieves and stores teamAPIID
        teamApi = getArguments().getInt(ARG_PAGE_NUMBER, -1);

        // Sets API URL's up
        jsonURLHome = "http://api.football-data.org/v1/teams/" + teamApi + "/fixtures?timeFrame=p35&venue=home";

        jsonURLAway = "http://api.football-data.org/v1/teams/" + teamApi + "/fixtures?timeFrame=p35&venue=away";

        // Requests JSON
        jsonRequesting(jsonURLHome, jsonURLAway);

        return rootView;
    }

    private void jsonRequesting(String jsonUrlHome, String jsonUrlAway) {
        // Method that handles all the JSON requests with error handling
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, jsonUrlHome, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        AssignToUI.returnVisibility(homeTeamResultCardOne, homeTeamResultCardTwo, homeTeamResultCardThree, homeTeamResultCardFour, homeTeamResultCardFive);
                        // Calls GSON parsing method
                        homeResultsGSON(response, true);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        JSONObject jsonHomeResults = null;
                        // Calls read file to retrieve previous requested JSON Object
                        jsonSaved = SaveAndReadToInternalStorage.readJsonFromLocalStorage(filenameHomeResults, getContext());

                        // Converts String into JSON Object
                        try
                        {
                            jsonHomeResults = new JSONObject(jsonSaved);
                        }
                        catch (Exception e)
                        {

                        }

                        if (jsonHomeResults != null) {
                            // Calls GSON parsing method
                            homeResultsGSON(jsonHomeResults, false);
                        }
                        else
                        {
                            // If no file is found changes the visibilty of CardViews for a better user experience
                            AssignToUI.changeVisibility(awayTeams, homeTeamResultCardOne, homeTeamResultCardTwo, homeTeamResultCardThree, homeTeamResultCardFour, homeTeamResultCardFive);
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };


        mRequestQueue.add(jsObjRequest);

        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest
                (Request.Method.GET, jsonUrlAway, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        AssignToUI.returnVisibility(awayTeamResultCardOne, awayTeamResultCardTwo, awayTeamResultCardThree, awayTeamResultCardFour, awayTeamResultCardFive);
                        // Calls GSON parsing method
                        awayResultsGSON(response, true);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        JSONObject jsonAwayResults = null;
                        // Calls read file to retrieve previous requested JSON Object
                        jsonSaved = SaveAndReadToInternalStorage.readJsonFromLocalStorage(filenameAwayResults, getContext());

                        // Converts String into JSON Object
                        try
                        {
                            jsonAwayResults = new JSONObject(jsonSaved);
                        }
                        catch (Exception e)
                        {

                        }

                        if (jsonAwayResults != null) {
                            // Calls GSON parsing method
                            awayResultsGSON(jsonAwayResults, false);
                        }
                        else
                        {
                            // If no file is found changes the visibilty of CardViews for a better user experience
                            AssignToUI.changeVisibility(homeTeams, awayTeamResultCardOne, awayTeamResultCardTwo, awayTeamResultCardThree, awayTeamResultCardFour, awayTeamResultCardFive);
                        }

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHttpHeaders();
            }
        };

        mRequestQueue.add(jsObjRequest2);
    }

    public Map<String, String> getHttpHeaders() {
        // Method for the API token
        Map<String, String> params = new HashMap<>();
        params.put("X-Auth-Token", "debf9352e2b745759c3eb424fc776d6d");
        return params;

    }

    public void homeResultsGSON(JSONObject response, boolean online)
    {
        // Sets up GSON model and loops through retrieving correct data from those classes
        Gson gson = new Gson();
        GSONModel gsonModel;
        gsonModel = gson.fromJson(response.toString(), GSONModel.class);
        for (int i = 0; i < gsonModel.getFixtures().size(); i++) {
            awayTeams.add(gsonModel.getFixtures().get(i).getAwayTeamName());
            awayTeamsDatesPlayed.add(sortDate(gsonModel.getFixtures().get(i).getDate()));
            homeGameGoals.add(gsonModel.getFixtures().get(i).getScores().getGoalsHomeTeam());
            homeGameGoalsAwayTeam.add(gsonModel.getFixtures().get(i).getScores().getGoalsAwayTeam());
        }

        // Checks to se if the user is online then saves the file if they are if not displays them a message informing they are being shown
        // data from when they were previous online
        if (online)
        {
            currentDate  = CurrentDate.getCurrentDate(currentDate);
            currentDateText.setText("Updated: " + currentDate);
            SaveAndReadToInternalStorage.saveJsonToLocalStorage(response, filenameHomeResults, getContext());
        }
        else
        {
            currentDate = SaveAndReadToInternalStorage.readDateFromLocalStorage(filenameLastKnownDate, getContext());
            currentDateText.setText("Last Updated: " + currentDate);
            Toast.makeText(getContext(), "You are offline, Displaying fixtures and results from when you were previously online!" , Toast.LENGTH_SHORT ).show();
        }

        // Reverses the data so the most recent result is displayed first
        Collections.reverse(awayTeams);
        Collections.reverse(awayTeamsDatesPlayed);
        Collections.reverse(homeGameGoals);
        Collections.reverse(homeGameGoalsAwayTeam);

        // Assigns to the UI
        AssignToUI.assignToTeamNameUI(awayTeamNameOne, awayTeamNameTwo, awayTeamNameThree, awayTeamNameFour, awayTeamNameFive, awayTeams);
        AssignToUI.assignToTeamDateUI(awayTeamDateOne, awayTeamDateTwo, awayTeamDateThree, awayTeamDateFour, awayTeamDateFive, awayTeamsDatesPlayed);
        AssignToUI.assignResultToUI(homeGameGoals, homeGameGoalsAwayTeam, awayTeamResultOne, awayTeamResultTwo, awayTeamResultThree, awayTeamResultFour, awayTeamResultFive, true);
        AssignToUI.changeVisibility(awayTeams, homeTeamResultCardOne, homeTeamResultCardTwo, homeTeamResultCardThree, homeTeamResultCardFour, homeTeamResultCardFive);
    }

    public void awayResultsGSON (JSONObject response, boolean online)
    {
        // Sets up GSON model and loops through retrieving correct data from those classes
        Gson gson = new Gson();
        GSONModel gsonModel;
        gsonModel = gson.fromJson(response.toString(), GSONModel.class);
        for (int i = 0; i < gsonModel.getFixtures().size(); i++) {
            homeTeams.add(gsonModel.getFixtures().get(i).getHomeTeamName());
            homeTeamsDatesPlayed.add(sortDate(gsonModel.getFixtures().get(i).getDate()));
            awayGameGoals.add(gsonModel.getFixtures().get(i).getScores().getGoalsHomeTeam());
            awayGameGoalsAwayTeam.add(gsonModel.getFixtures().get(i).getScores().getGoalsAwayTeam());
        }

        // Checks to se if the user is online then saves the file if they are if not displays them a message informing they are being shown
        // data from when they were previous online
        if (online)
        {
            SaveAndReadToInternalStorage.saveJsonToLocalStorage(response, filenameAwayResults, getContext());
        }
        else
        {
            Toast.makeText(getContext(), "You are offline, Displaying fixtures and results from when you were previously online!" , Toast.LENGTH_SHORT ).show();
        }

        // Reverses the data so the most recent result is displayed first
        Collections.reverse(homeTeams);
        Collections.reverse(homeTeamsDatesPlayed);
        Collections.reverse(awayGameGoals);
        Collections.reverse(awayGameGoalsAwayTeam);

        // Assigns to the UI
        AssignToUI.assignToTeamNameUI(homeTeamNameOne, homeTeamNameTwo, homeTeamNameThree, homeTeamNameFour, homeTeamNameFive, homeTeams);
        AssignToUI.assignToTeamDateUI(homeTeamDateOne, homeTeamDateTwo, homeTeamDateThree, homeTeamDateFour, homeTeamDateFive, homeTeamsDatesPlayed);
        AssignToUI.assignResultToUI(awayGameGoals, awayGameGoalsAwayTeam, homeTeamResultOne, homeTeamResultTwo, homeTeamResultThree, homeTeamResultFour, homeTeamResultFive, false);
        AssignToUI.changeVisibility(homeTeams, awayTeamResultCardOne, awayTeamResultCardTwo, awayTeamResultCardThree, awayTeamResultCardFour, awayTeamResultCardFive);
    }

    public String sortDate (String date)
    {
        // Method that sorts the recieved date so it is in a more readable format for the user
        String[] dateArray = date.split("T");
        String returnDate = "";

        returnDate += dateArray[0].substring(8, 10) + "/"
                + dateArray[0].substring(5, 7) + "/"
                + dateArray[0].substring(0,4);

        return returnDate;
    }

}
