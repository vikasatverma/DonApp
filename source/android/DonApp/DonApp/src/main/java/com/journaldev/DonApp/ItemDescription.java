package com.journaldev.DonApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
/** \brief This class is responsible for providing full description of the item.

 */
public class ItemDescription extends AppCompatActivity {

    ImageView item_image;
    Button claim;
    /** Function executed at the time of creation of the page.
     @param savedInstanceState: A reference to a Bundle object that is passed into the onCreate method of every Android Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description);

        item_image=(ImageView)findViewById(R.id.photo);
        claim=(Button)findViewById(R.id.claim);
        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=getIntent();
                String item_id=i.getStringExtra("item_id");
                String username=i.getStringExtra("username");
                Intent intent = new Intent(getApplicationContext(), CallDibs.class);

                intent.putExtra("item_id", item_id);
                intent.putExtra("username", username);
                Toast.makeText(getApplicationContext(),"hey",Toast.LENGTH_LONG);
                startActivity(intent);

//                finish();
//                startActivity(getIntent());
            }
        });

        Intent intent=getIntent();
        String item_id=intent.getStringExtra("item_id");
        getJSON("http://"+ipaddress.ipadd+"/listdb/getitem.php?id="+item_id);

    }

    //this method is actually fetching the json string
    /** This method is fetching the json string
     @param urlWebService: url for fetching JSON string
     */
    private void getJSON(final String urlWebService) {
        /** Class for fetching JSON string using Asynctask thread

         As fetching the json string is a network operation
         And we cannot perform a network operation in main thread
         so we need an AsyncTask
         The constrains defined here are
         @param Void: We are not passing anything
         @param Void: Nothing at progress update as well
         @param String: After completion it should return a string and it will be the json string
         */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            /** In this method we are fetching the json string in background
             */
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.e("vikas string",s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {

                    Log.e("Vikas", "Error at 78");
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {


                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private ItemAdapter mAdapter;

//    public  Drawable DownloadDrawable(String url, String src_name) throws java.io.IOException {
//        return Drawable.createFromStream(((java.io.InputStream) new java.net.URL(url).getContent()), src_name);
//    }

    private void loadIntoListView(String json) throws JSONException {


        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);


//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        Map<String, String> map;
        //looping through all the elements in json array
        ImageView imageView;
//        Toast.makeText(getApplicationContext(),Integer.toString(jsonArray.length()),Toast.LENGTH_LONG).show();
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            final JSONObject obj = jsonArray.getJSONObject(i);

//           Toast.makeText(getApplicationContext(),obj.getString("name"),Toast.LENGTH_LONG).show();
            TextView item_name=(TextView) findViewById(R.id.itemname);
            TextView item_place=(TextView) findViewById(R.id.itemplace);
            TextView item_desc=(TextView) findViewById(R.id.itemdesc);
            TextView contact=(TextView) findViewById(R.id.contact);
            ImageView item_image=(ImageView)findViewById(R.id.photo) ;
            Toast.makeText(getApplicationContext(),obj.getString("image_path"),Toast.LENGTH_LONG).show();
            item_name.setText(obj.getString("name"));
            item_place.setText(obj.getString("place"));
            item_desc.setText(obj.getString("description"));
            contact.setText(obj.getString("contact"));
            String image_path="http://"+ipaddress.ipadd+":8000"+obj.getString("image_path");
            String[] data = image_path.split("/", 100);
            image_path = "http://"+ipaddress.ipadd+":8000/"+data[data.length-1];
            Toast.makeText(getApplicationContext(),image_path,Toast.LENGTH_LONG).show();





//            image_path="https://image.shutterstock.com/image-photo/orange-fruit-leaves-clipping-path-600w-1026670936.jpg";
            ImageFetch fetch=new ImageFetch();
            fetch.execute(image_path);



        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

    }

    /** Function for calling intent to display same page again.
     */
    public void Add_items_to_list(){

        Intent intent = new Intent(this,add_item.class);

        startActivity(intent);
    }

    /** Class for fetching image
     */
    class ImageFetch extends AsyncTask<String, Void, Bitmap> {

        //this method will be called before execution
        //you can display a progress bar or something
        //so that user can understand that he should wait
        //as network operation may take some time
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //this method will be called after execution
        //so here we are displaying a toast with the json string
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Bitmap bmap = bitmap;
            try {
                //   Log.e("bitmap----------", bmap);
                item_image.setImageBitmap(bmap);
            } catch (Exception e) {

                Log.e("Vikas", "Error at 78");
                e.printStackTrace();
            }

        }
        //in this method we are fetching the json string
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap btm=null;
            String str=strings[0];
            try{
                URL url=new URL(str);
//                HttpsURLConnection connection= (HttpsURLConnection) url.openConnection();
                HttpURLConnection connection1=(HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.connect();
//                if(connection.getResponseCode()==200){
//                    InputStream is=connection.getInputStream();
//                    btm= BitmapFactory.decodeStream(is);
//                }
                connection1.setRequestMethod("GET");
                connection1.connect();
                if(connection1.getResponseCode()==200){
                    InputStream is=connection1.getInputStream();
                    btm= BitmapFactory.decodeStream(is);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return btm;
        }

    }

/////////++++++++++++++++++++    image fetching  end ++++++++++++++++++++++


}
