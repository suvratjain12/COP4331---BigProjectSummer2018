package com.example.suvratjain.firstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.webkit.URLUtil;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static java.lang.Thread.sleep;

public class LoginPHPWorker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    private boolean authorization = false;
    private String user_name;
    private String password;


    LoginPHPWorker (Context cxt) {
        context = cxt;
    }

    @Override
    protected String doInBackground(String... params){

        String type = params[0];
//      String login_url = "http://192.168.1.2/login.php";
        String login_url = "http://ameade.us/Loginrpsx.php";

//        System.out.println();
//        System.out.println(" URL Validity: " + URLUtil.isValidUrl(login_url));
//        System.out.println();

        if(type.equals("login"))
        {
            try {

                //get the user entered username and password
                 user_name = params[1];
                 password = params[2];

                JSONObject loginVerification = new JSONObject();
                loginVerification.put("Username", user_name);
                loginVerification.put("Password", password);

                 //create a URL pointer that points to the file specified by the URL path
                URL url = new URL(login_url);
                //open port to allow http connection
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //create an output stream to connect to the open port and write to the stream using buffered writer
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

//                String post_data = URLEncoder.encode("Username", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"
//                        +URLEncoder.encode("Password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(String.valueOf(loginVerification));
                bufferedWriter.flush();


                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

//                String result = "";
                StringBuilder result = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null)
                {
//                    result += line;
                    result.append(line);
                }

                line = result.toString();

//                String[] line2 = line.split(",");
                String pattern = "[\\,]";
                String[] jsonObjects = line.split(pattern);

                for(int i = 0; i < jsonObjects.length; i++)
                {
                    System.out.println("jsonObjects["+i+"] = " + jsonObjects[i]);

                }

                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();




                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        alertDialog.hide();
    }

    @Override
    protected void onPostExecute(String result) {

        //start menu if the credentials are correct
        if(result.equals("\"0\""))
        {
            alertDialog.setMessage("Login Unsuccessful. Try Again!");
            alertDialog.show();
        }

        else
        {
            alertDialog.setMessage("Login Successful!");
            alertDialog.show();

            Intent menu = new Intent(context, Menu.class);
            menu.putExtra("user name", user_name);
            context.startActivity(menu);
        }


       System.out.println("JSON equals: " + result);
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }


}
