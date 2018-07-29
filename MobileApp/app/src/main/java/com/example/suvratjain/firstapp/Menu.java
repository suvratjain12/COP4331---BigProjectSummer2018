package com.example.suvratjain.firstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    private TextView userNameField, displayNameField, displayNameField_value, userNameField_value;
    private String[] choices = new String[]{"Create Room", "Enter Room"};
    private AlertDialog gameOptionsDialog;

    Intent main;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        main = getIntent();
        b = main.getExtras();

        userNameField = findViewById(R.id.userName);
        displayNameField = findViewById(R.id.displayName);
        userNameField_value = findViewById(R.id.userName_value);
        displayNameField_value = findViewById(R.id.displayName_value);

        userNameField.setText("UserName: ");
        userNameField_value.setText(getUsername());
        displayNameField.setText("DisplayName: ");
        displayNameField_value.setText(getDisplayName());


//        //get extras from previous activity
//        Intent i = getIntent();
//        b = i.getExtras();
//
//        if(b!=null){
//            String j = (String) b.get("user name");
//            displayUserNameField.setText("Username: " + j);
//        } else {
//            System.out.println("It is null");
//        }

    }

    public String getUsername()
    {


        String username = null;

        if(b!=null){
            username = (String) b.get("user name");
        }

        return username;
    }

    public String getDisplayName()
    {
        Intent main = getIntent();
        Bundle b = main.getExtras();
        String displayName = null;

        if(b!=null){
            displayName = (String) b.get("display name");
            displayName = displayName.substring(3);
        }

        return displayName;
    }

    //open the dialog box with radio buttons
    public void openNewSession(View view)
    {

        final AlertDialog.Builder gameOptions = new AlertDialog.Builder(Menu.this);
        gameOptions.setTitle("Choose Option");

//        final AlertDialog workInProgress = new AlertDialog.Builder(this).create();
//        workInProgress.setTitle("Sorry!");
//        workInProgress.setMessage("Due to lack of time and resources this feature is currently unavailable. Please check back soon!");

        gameOptions.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int item)
            {

                if (item == 0)
                {
                  Toast.makeText(Menu.this, "Please wait...", Toast.LENGTH_LONG).show();
//                    workInProgress.show();

                    Thread welcomeThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                super.run();
                                sleep(1000);  //Delay of 1 second
                            } catch (Exception e) {

                            } finally {

                                Intent i = new Intent(Menu.this, CreateRoom.class);
                                i.putExtra("display name", (String)b.get("display name"));
                                startActivity(i);

                            }
                        }
                    };
                    welcomeThread.start();

                }
                else if (item == 1) {
                    Toast.makeText(Menu.this, "Please wait...", Toast.LENGTH_LONG).show();

                    //add delay
                    Thread welcomeThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                super.run();
                                sleep(1000);  //Delay of 1 second
                            } catch (Exception e) {

                            } finally {

                                Intent i = new Intent(Menu.this, EnterRoom.class);
                                startActivity(i);

                            }
                        }
                    };
                    welcomeThread.start();


                }

                dialogInterface.dismiss();

            }
        });

        gameOptionsDialog = gameOptions.create();
        gameOptionsDialog.show();



    }

    //take the activity out of the activity stack
    public void exit(View view) {
        finish();
    }

    //open Settings Activity
    public void openSettings(View view) {

        Intent settings = new Intent(this, Settings.class);
        settings.putExtra("user name", getUsername());
        settings.putExtra("display name", getDisplayName());
        startActivity(settings);
    }

    //openLeaderBoard Activity
    public void openLeaderBoard(View view) {

        LeaderBoardWorker leaderBoardWorker = new LeaderBoardWorker(this);
        leaderBoardWorker.execute();

    }
}
