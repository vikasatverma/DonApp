package com.journaldev.DonApp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/** \mainpage Steps to setup the server and run the Project
 For setting up the database:
 1.  Download and install Xampp from the following link https://www.apachefriends.org/download.html
 2.  For Windows:  Open Xampp Control Panel from the directory where Xamppwas installed.

 (a)  In the Xampp control panel, start the modules for ’Apache’ and ’MySQL’

 (b)  After both the modules have started, click on ’Admin’ button for ’Apache’module.

 (c)  A webpage will open up in the browser.  Click on the tab ’phpMyAdmin’(usually on the top right).
 3.  For  Linux:   Go  to  the  directory  /opt/lampp  and  run  the  command:   sudo./xampp start4
 • In a browser open localhost/phpmyadmin
 4.  On the left click on option ’new’ to create a new database.  Name the databaseas ’firstDB’.
 5.  Click on the icon for newly created Database ’firstDB’ and go to the ’SQL’tab.
 6. Run the following command in terminal / command prompt for enabling httpserver to respond to image requests

 ruby -run -ehttpd .  -p8000 [2019-11-27 08:44:40] INFO WEBrick 1.4.2[2019-11-27  08:44:40]

 INFO  ruby  2.5.1  (2018-03-29)  [x8664-linux-gnu][2019-11-27 08:44:40]

 INFO WEBrick::HTTPServer#start: pid=5175 port=8000

 7.  Type the following commands and click on Go:

 CREATE TABLE ‘itemlist‘ (‘itemid‘ int(11) NOT NULL, ‘itemname‘varchar(30)  NOT  NULL,  ‘place‘  text  NOT  NULL  DEFAULT  ’IITB’,‘imagepath‘ text NOT NULL, ‘description‘ text NOT NULL DEFAULT’No further info’, ‘contact‘ varchar(20) NOT NULL DEFAULT ’0’, ‘cat-egory‘ enum(’Education’,’Sports’,’Food’,’Clothing’,’Footwear’,  ’Station-ary’,’Others’) NOT NULL DEFAULT ’Others’, ‘donorid‘ text NOT NULLDEFAULT ’0, ‘doneeid‘ text NOT NULL DEFAULT ’0’, ‘status‘ enum(’added’,’requested’,’approved’,”) NOT NULL DEFAULT ’added’)

 CREATE  TABLE  ‘users‘  (  ‘id‘  int(20)  NOT  NULL,  ‘username‘  var-char(70) NOT NULL, ‘password‘ varchar(40) NOT NULL, ‘email‘ var-char(50) NOT NULL, ‘dibscount‘ int(11) NOT NULL DEFAULT 0, ‘cre-atedat‘ datetime NOT NULL, ‘updatedat‘ datetime DEFAULT NULL)
 For the source code:
 8.  Find out the IP address of the system where the Xampp is being run.  Usethe commands ’ipconfig’ in Windows Command Prompt Shell or ’ifconfig’ inLinux to obtain the IP addresses.
 9.  Copy the IP address to the java file in the path:  ̃\DonApp-master\DonApp\src\main\java\com\journaldev\DonApp\ipaddress.java  in  the  static  String  variable’ipadd’.
 10.  We use Android Studio which can be downloaded from https://developer.android.com/studio
 11.  After installing Android Studio, download SDK for Android 8.0 (api 24)
 12.  The  entire  folder  ’Donapp-master’ should  be used  as  a  project  for  AndroidStudio.
 13.  Build and Run the Project.  The apk file can be made from here and installedin any smartphone for usage.

 */

/** /\brief A class for Login and registration functions

 */
public class MainActivity extends AppCompatActivity {

    String username;

    EditText editEmail, editPassword, editName;
    Button btnSignIn, btnRegister;

    String URL= "http://"+ipaddress.ipadd+"/test_android/index.php";

    LoginJSONParser loginJsonParser =new LoginJSONParser();

    int i=0;

    @Override
    /** Function executed at the time of creation of the page.

     @param savedInstanceState:  A reference to a Bundle object that is passed into the onCreate method of every Android Activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        editEmail=(EditText)findViewById(R.id.editEmail);
        editName=(EditText)findViewById(R.id.editName);
        editPassword=(EditText)findViewById(R.id.editPass);

        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin attemptLogin= new AttemptLogin();
                attemptLogin.execute(editName.getText().toString(),editPassword.getText().toString(),"");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i==0)
                {
                    i=1;
                    editEmail.setVisibility(View.VISIBLE);
                    btnSignIn.setVisibility(View.GONE);
                    btnRegister.setText("CREATE ACCOUNT");
                }
                else{

                    btnRegister.setText("REGISTER");
                    editEmail.setVisibility(View.GONE);
                    btnSignIn.setVisibility(View.VISIBLE);
                    i=0;

                    AttemptLogin attemptLogin= new AttemptLogin();
                    attemptLogin.execute(editName.getText().toString(),editPassword.getText().toString(),editEmail.getText().toString());

                }

            }
        });


    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {
        @Override
        /** Pre executes

         */
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {



            String email = args[2];
            String password = args[1];
            username= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
            params.add(new BasicNameValuePair("email",email));
            Log.e("JSON",""+params);

            JSONObject json = loginJsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Log.e("Debug","reached here"+result);
                    if(result.getInt("success")==1){
                        openList();
                    }

                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /** Function to call another activity

     Intent is used to call another activity

     */
    public void openList(){
        Intent intent = new Intent(this,List_of_items.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
