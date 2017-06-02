package jamiesmyth.mobilecomputingapplication;


import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AssignToUI {

    public static void assignToTeamNameUI(TextView teamOne, TextView teamTwo, TextView teamThree, TextView teamFour, TextView teamFive, ArrayList teamNames )
    {
        // Assigns team names to the correct TextViews on each fragment
        for (int i = 0; i < teamNames.size(); i++) {
            if (i == 0) {
                teamOne.setText(teamNames.get(i).toString());
            } else if (i == 1) {
                teamTwo.setText(teamNames.get(i).toString());
            } else if (i == 2) {
                teamThree.setText(teamNames.get(i).toString());
            } else if (i == 3) {
                teamFour.setText(teamNames.get(i).toString());
            } else if (i == 4) {
                teamFive.setText(teamNames.get(i).toString());
            }
        }
    }

    public static void assignToTeamDateUI(TextView dateOne, TextView dateTwo, TextView dateThree, TextView dateFour, TextView dateFive, ArrayList teamDates)
    {
        // Assigns the dates to the correct TextViews in each fragment
        for (int i = 0; i < teamDates.size(); i++)
        {
            if (i == 0)
            {
                dateOne.setText(teamDates.get(i).toString());
            }
            else if (i == 1)
            {
                dateTwo.setText(teamDates.get(i).toString());
            }
            else if (i == 2)
            {
                dateThree.setText(teamDates.get(i).toString());
            }
            else if (i == 3)
            {
                dateFour.setText(teamDates.get(i).toString());
            }
            else if (i == 4)
            {
                dateFive.setText(teamDates.get(i).toString());
            }
        }
    }

    public static void assignResultToUI (ArrayList homeGoals, ArrayList awayGoals, TextView scoreOne, TextView scoreTwo, TextView scoreThree, TextView scoreFour, TextView scoreFive, boolean atHome)
    {
        // Method to assign whether the team has won, drawn or lost and displays on the correct TextViews in the result fragment
        int homeGoal = 0;
        int awayGoal = 0;

        for (int i = 0; i < homeGoals.size(); i++)
        {
            if (i == 0)
            {
                // Converts home and away goals from string into an integer to be compared.
                homeGoal = Integer.parseInt(homeGoals.get(i).toString());
                awayGoal = Integer.parseInt(awayGoals.get(i).toString());
                // Checks whether the home team or away has the most goals and displays the correct result changing win and
                // loss according to whether the team is playing at home
                if (homeGoal > awayGoal)
                {
                    if (atHome) {
                        scoreOne.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreOne.setTextColor(Color.parseColor("#32CD32"));
                    }
                    else
                    {
                        scoreOne.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreOne.setTextColor(Color.parseColor("#cc0000"));
                    }
                }
                else if (homeGoal == awayGoal)
                {
                    scoreOne.setText("Draw " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                }
                else if (homeGoal < awayGoal)
                {
                    if (atHome) {
                        scoreOne.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreOne.setTextColor(Color.parseColor("#cc0000"));
                    }
                    else
                    {
                        scoreOne.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreOne.setTextColor(Color.parseColor("#32CD32"));
                    }
                }
            }
            else if (i == 1)
            {
                homeGoal = Integer.parseInt(homeGoals.get(i).toString());
                awayGoal = Integer.parseInt(awayGoals.get(i).toString());
                if (homeGoal > awayGoal)
                {
                    if (atHome) {
                        scoreTwo.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreTwo.setTextColor(Color.parseColor("#32CD32"));
                    }
                    else
                    {
                        scoreTwo.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreTwo.setTextColor(Color.parseColor("#cc0000"));
                    }
                }
                else if (homeGoal == awayGoal)
                {
                    scoreTwo.setText("Draw " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                }
                else if (homeGoal < awayGoal)
                {
                    if (atHome) {
                        scoreTwo.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreTwo.setTextColor(Color.parseColor("#cc0000"));
                    }
                    else
                    {
                        scoreTwo.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreTwo.setTextColor(Color.parseColor("#32CD32"));
                    }
                }
            }
            else if (i == 2)
            {
                homeGoal = Integer.parseInt(homeGoals.get(i).toString());
                awayGoal = Integer.parseInt(awayGoals.get(i).toString());
                if (homeGoal > awayGoal)
                {
                    if (atHome) {
                        scoreThree.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreThree.setTextColor(Color.parseColor("#32CD32"));
                    }
                    else
                    {
                        scoreThree.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreThree.setTextColor(Color.parseColor("#cc0000"));
                    }
                }
                else if (homeGoal == awayGoal)
                {
                    scoreThree.setText("Draw " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                }
                else if (homeGoal < awayGoal)
                {
                    if (atHome) {
                        scoreThree.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreThree.setTextColor(Color.parseColor("#cc0000"));
                    }
                    else
                    {
                        scoreThree.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreThree.setTextColor(Color.parseColor("#32CD32"));
                    }
                }
            }
            else if (i == 3)
            {
                homeGoal = Integer.parseInt(homeGoals.get(i).toString());
                awayGoal = Integer.parseInt(awayGoals.get(i).toString());
                if (homeGoal > awayGoal)
                {
                    if (atHome) {
                        scoreFour.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFour.setTextColor(Color.parseColor("#32CD32"));
                    }
                    else
                    {
                        scoreFour.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFour.setTextColor(Color.parseColor("#cc0000"));
                    }
                }
                else if (homeGoal == awayGoal)
                {
                    scoreFour.setText("Draw " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                }
                else if (homeGoal < awayGoal)
                {
                    if (atHome) {
                        scoreFour.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFour.setTextColor(Color.parseColor("#cc0000"));
                    }
                    else
                    {
                        scoreFour.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFour.setTextColor(Color.parseColor("#32CD32"));
                    }
                }
            }
            else if (i == 4)
            {
                homeGoal = Integer.parseInt(homeGoals.get(i).toString());
                awayGoal = Integer.parseInt(awayGoals.get(i).toString());
                if (homeGoal > awayGoal)
                {
                    if (atHome) {
                        scoreFive.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFive.setTextColor(Color.parseColor("#32CD32"));
                    }
                    else {
                        scoreFive.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFive.setTextColor(Color.parseColor("#cc0000"));
                    }
                }
                else if (homeGoal == awayGoal)
                {
                    scoreFive.setText("Draw " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                }
                else if (homeGoal < awayGoal)
                {
                    if (atHome) {
                        scoreFive.setText("Loss " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFive.setTextColor(Color.parseColor("#cc0000"));
                    }
                    else {
                        scoreFive.setText("Won " + homeGoals.get(i).toString() + " - " + awayGoals.get(i).toString());
                        scoreFive.setTextColor(Color.parseColor("#32CD32"));
                    }
                }
            }
        }
    }

    public static void changeVisibility(ArrayList teamArray, CardView teamCardOne, CardView teamCardTwo, CardView teamCardThree, CardView teamCardFour, CardView teamCardFive)
    {
        // Sets the visibility of each CardView according to whether they have information displayed in them
        if (teamArray.size() == 0)
        {
            teamCardOne.setVisibility(View.INVISIBLE);
            teamCardTwo.setVisibility(View.INVISIBLE);
            teamCardThree.setVisibility(View.INVISIBLE);
            teamCardFour.setVisibility(View.INVISIBLE);
            teamCardFive.setVisibility(View.INVISIBLE);
        }
        else if (teamArray.size() == 1)
        {
            teamCardTwo.setVisibility(View.INVISIBLE);
            teamCardThree.setVisibility(View.INVISIBLE);
            teamCardFour.setVisibility(View.INVISIBLE);
            teamCardFive.setVisibility(View.INVISIBLE);
        }
        else if (teamArray.size() == 2)
        {
            teamCardThree.setVisibility(View.INVISIBLE);
            teamCardFour.setVisibility(View.INVISIBLE);
            teamCardFive.setVisibility(View.INVISIBLE);
        }
        else if (teamArray.size() == 3)
        {
            teamCardFour.setVisibility(View.INVISIBLE);
            teamCardFive.setVisibility(View.INVISIBLE);
        }
        else if (teamArray.size() == 4)
        {
            teamCardFive.setVisibility(View.INVISIBLE);
        }
    }

    public static void returnVisibility(CardView cardOne, CardView cardTwo, CardView cardThree, CardView cardFour, CardView cardFive)
    {
        //  Returns all CardView visibilities to be used again
        cardOne.setVisibility(View.VISIBLE);
        cardTwo.setVisibility(View.VISIBLE);
        cardThree.setVisibility(View.VISIBLE);
        cardFour.setVisibility(View.VISIBLE);
        cardFive.setVisibility(View.VISIBLE);
    }
}
