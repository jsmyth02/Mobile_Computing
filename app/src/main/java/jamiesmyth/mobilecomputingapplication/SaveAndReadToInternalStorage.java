package jamiesmyth.mobilecomputingapplication;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class SaveAndReadToInternalStorage {

    public static void saveJsonToLocalStorage (JSONObject Fixtures, String filename, Context context)
    {
        // Stores retrieved JSON into local storage
        FileOutputStream fos = null;
        String jsonObjectFixtures;

        // Converts JSON Object to string to be stored
        jsonObjectFixtures = Fixtures.toString();

        // Creates the file with filepath
        File file = context.getFileStreamPath(filename);

        // Checks to see if the file exists if not saves it
        if(file == null || !file.exists()) {
            try {
                fos = context.openFileOutput(filename, context.MODE_PRIVATE);
                fos.write(jsonObjectFixtures.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // If the file exists, then it deletes that file and re creates it with the new data
        else if (file.exists())
        {
            file.delete();
            try {
                fos = context.openFileOutput(filename, context.MODE_PRIVATE);
                fos.write(jsonObjectFixtures.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String readJsonFromLocalStorage (String filename , Context context)
    {
        // Method for reading data from local storage
        StringBuilder fixturesStringBuilder = new StringBuilder("");
        String homeFixtures = " ";

        // Checks the file exists if and then stores that data to a string which will be converted back to JSON Object further in the code
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader ois = new InputStreamReader(fis);
            BufferedReader buffreader = new BufferedReader(ois) ;
            String readString = buffreader.readLine();
            while (readString != null)
            {
                fixturesStringBuilder.append(readString);
                readString = buffreader.readLine();
            }
            homeFixtures = fixturesStringBuilder.toString();
        }
        catch (IOException e)
        {
            // If the file does not exists it means the user has not been online once before so they are informed of this
            e.printStackTrace();
            Toast.makeText(context, "Unfortunately, you need to a connection before retrieving your first set of fixtures and results" , Toast.LENGTH_SHORT ).show();
        }

        return homeFixtures;
    }

    public static void saveDateToLocalStorage (String date, String filename, Context context)
    {
        // Stores retrieved JSON into local storage
        FileOutputStream fos = null;

        // Creates the file with filepath
        File file = context.getFileStreamPath(filename);

        // Checks to see if the file exists if not saves it
        if(file == null || !file.exists()) {
            try {
                fos = context.openFileOutput(filename, context.MODE_PRIVATE);
                fos.write(date.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // If the file exists, then it deletes that file and re creates it with the new data
        else if (file.exists())
        {
            file.delete();
            try {
                fos = context.openFileOutput(filename, context.MODE_PRIVATE);
                fos.write(date.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String readDateFromLocalStorage (String filename, Context context)
    {
        // Method for reading data from local storage
        StringBuilder fixturesStringBuilder = new StringBuilder("");
        String returnedDate = " ";

        // Checks the file exists if and then stores that data to a string which will be converted back to JSON Object further in the code
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader ois = new InputStreamReader(fis);
            BufferedReader buffreader = new BufferedReader(ois) ;
            String readString = buffreader.readLine();
            while (readString != null)
            {
                fixturesStringBuilder.append(readString);
                readString = buffreader.readLine();
            }
            returnedDate = fixturesStringBuilder.toString();
        }
        catch (IOException e)
        {
            // If the file does not exists it means the user has not been online once before so they are informed of this
            e.printStackTrace();
            Toast.makeText(context, "Unfortunately, you need to a connection before retrieving your first set of fixtures and results" , Toast.LENGTH_SHORT ).show();
        }

        return returnedDate;
    }
}
