package com.journaldev.DonApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class List_of_items extends AppCompatActivity {
    HashMap<String,String>hashMap;
    SearchView searchView;
    ArrayList<String> list;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_list_of_items);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        list = new ArrayList<>();
        list.add("OS Textbook");
        list.add("Headphones");
        list.add("LAN cable");
        list.add("mouse");
        list.add("Tshirt");
        list.add("Sushi");
        list.add("Football");
        list.add("a");
        list.add("sadfa");
        list.add("mouse");
        searchView=(SearchView)findViewById(R.id.search);
        searchView.setQueryHint("Search Item Here ...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query!=null){
                    getJSON("http://"+ipaddress.ipadd+"/listdb/getdata.php");
                }else{
                    Toast.makeText(List_of_items.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
        getJSON("http://"+ipaddress.ipadd+"/listdb/getdata.php");
        ListView listView = (ListView) findViewById(R.id.movies_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item_id=hashMap.get(String.valueOf(id));
                Log.e("HelloListView", "You clicked Item: " + id + " at position:" + position+" with item_id "+item_id);
                Intent i=getIntent();
                //String item_id=i.getStringExtra("item_id");
                String username=i.getStringExtra("username");
                Intent intent = new Intent(getApplicationContext(), ItemDescription.class);
                intent.putExtra("item_id", item_id);
                intent.putExtra("username", username);

                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton refresh = (FloatingActionButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                finish();
                startActivity(getIntent());
                //Add_items_to_list();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Add_items_to_list();
            }
        });

    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        MenuItem searchViewItem = menu.findItem(R.id.action_settings);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //    public void itemDescription(View v)
//    {
//        Toast.makeText(getApplicationContext(), "hey bawa", Toast.LENGTH_LONG).show();
//    }
    //this method is actually fetching the json string
    private void getJSON(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
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
        Log.d("milestone", "1");
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);
        //creating a string array for listview
        Log.d("milestone", "2");
        String[] items = new String[jsonArray.length()];

        ArrayList<Item> ItemList = new ArrayList<>();


//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        Map<String, String> map;
        //looping through all the elements in json array
        ImageView imageView;
        hashMap=new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
//            URL url = new URL(obj.getString("image_path"));
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            imageView.setImageBitmap(bmp);

            int drawable_image=R.drawable.other;


            String category = obj.getString("category");
            if(category.equals("Electronics")){
                drawable_image=R.drawable.electronic;
            }
            else if (category.equals("Education")){
                drawable_image=R.drawable.book;
            }
            else if (category.equals("Sports")){
                drawable_image=R.drawable.sports;
            }
            else if (category.equals("Food")){
                drawable_image=R.drawable.food;
            }
            else if (category.equals("Clothing")){
                drawable_image=R.drawable.shirts;
            }
            else if (category.equals("Footwear")){
                drawable_image=R.drawable.shoe;
            }
            else if (category.equals("Stationary")){
                drawable_image=R.drawable.stationary;
            }
            String item_name=obj.getString("name");
            String description=obj.getString("description");
            hashMap.put(String.valueOf(i),obj.getString("id"));
            // EditText srch=(EditText) findViewById(R.id.search);
            // String search=srch.getText().toString();
            String search =searchView.getQuery().toString();
            //String input = "Android gave new life to Java";
            boolean isFound1 = item_name.indexOf(search) !=-1? true: false;
            boolean isFound2 = description.indexOf(search) !=-1? true: false;

            if(isFound1||isFound2)
                if(!obj.getString("status").equals("requested"))
                ItemList.add(new Item(drawable_image,item_name,"Available at "+obj.getString("place")));
        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);



        ListView listView = (ListView) findViewById(R.id.movies_list);

        mAdapter = new ItemAdapter(this,ItemList);
        //attaching adapter to listview
        listView.setAdapter(mAdapter);
    }



    public void Add_items_to_list(){

        Intent intent = new Intent(this,add_item.class);

        startActivity(intent);
    }
}