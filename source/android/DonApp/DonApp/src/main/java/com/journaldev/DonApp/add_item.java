package com.journaldev.DonApp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Spinner;



import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/** \brief A class to  add an item to the list
 */
public class add_item extends AppCompatActivity implements View.OnClickListener {
    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    //private EditText editTextImageName;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    EditText editPlace, editName,editDescription,editContact;
    Button  btnRegister;
    Spinner spinner;


    public static final String UPLOAD_URL = "http://"+ipaddress.ipadd+"/itemphp/upload.php";//"http://10.4.224.148/AndroidImageUpload/upload.php";

    //addItemJSONParser itemJsonParser =new addItemJSONParser();

    int i=0;

    @Override
    /** Function called at the beginning
     @param savedInstanceState:  A reference to a Bundle object that is passed into the onCreate method of every Android Activity


     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

//        editEmail=(EditText)findViewById(R.id.editEmail);
        editName = (EditText)findViewById(R.id.editName);
        editPlace = (EditText)findViewById(R.id.editPlace);
        editDescription = (EditText)findViewById(R.id.editDescription);
        editContact = (EditText)findViewById(R.id.editContact);
        spinner = (Spinner) findViewById(R.id.itemCategory);
//        btnSignIn=(Button)findViewById(R.id.btnSignIn);
//        btnRegister=(Button)findViewById(R.id.btnAdd);



        // Spinner element


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Electronics");
        categories.add("Education");
        categories.add("Sports");
        categories.add("Food");
        categories.add("Clothing");
        categories.add("Footwear");
        categories.add("Stationary");
        categories.add("Others");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);




        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        //editTextImageName = (EditText) findViewById(R.id.editTextName);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);


//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                AttemptLogin attemptLogin= new AttemptLogin();
//                attemptLogin.execute(editName.getText().toString(), editPlace.getText().toString(), editDescription.getText().toString(), editContact.getText().toString(),editTextImageName.getText().toString());
//            }
//        });


    }

    /**
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void uploadMultipart() {
        //getting name for the image
        //String name = editTextImageName.getText().toString().trim();
        Log.e("i1",editName.getText().toString().trim());
        Log.e("i2",editPlace.getText().toString().trim());
        Log.e("i3",editDescription.getText().toString().trim());
        Log.e("i4",editContact.getText().toString().trim());

        //getting the actual path of the image
        String path = getPath(filePath);
        Log.e("i5",path);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            //(`item_name`, `place`, `image_path`, `description`, `contact`)
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("item_name", editName.getText().toString().trim()) //Adding text parameter to the request
                    .addParameter("place", editPlace.getText().toString().trim()) //Adding text parameter to the request
                    .addParameter("description",editDescription.getText().toString().trim()) //Adding text parameter to the request
                    .addParameter("contact", editContact.getText().toString().trim()) //Adding text parameter to the request
                    .addParameter("category",spinner.getSelectedItem().toString().trim())

                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    /** A method to show file chooser
     */
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    /** A function for handling the image chooser activity result
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    /** A method to get the file path from uri
     @param uri: The uniform resource indicator for the file
     */
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    /**Method for Requesting permission from the user
     */
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    /** This method will be called when the user will tap on allow or deny
     @param requestcode: The request code corresponding to this request
     @param permissions: The permissions which are needed
     @param grantResults: The reply for permission requests from the user
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    /** Function to show file chooser and to upload the image
     @param v: View corresponding to the button
     */
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadMultipart();
            openList();
        }
    }




//    public class AttemptLogin extends AsyncTask<String, String, JSONObject> {
//
//        @Override
//
//        protected void onPreExecute() {
//
//            super.onPreExecute();
//
//        }
//
////        @Override
////
////        protected JSONObject doInBackground(String... args) {
////
////
////            String image_path = args[4];
////            String contact= args[3];
////            String description= args[2];
////            String place = args[1];
////            String name= args[0];
////
////            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
////            params.add(new BasicNameValuePair("item_name", name));
////            params.add(new BasicNameValuePair("place", place));
////            params.add(new BasicNameValuePair("description", description));
////            params.add(new BasicNameValuePair("contact", contact));
////            params.add(new BasicNameValuePair("image_path", image_path));
////
////
////            Log.e("JSON",""+params);
////
////            //JSONObject json = itemJsonParser.makeHttpRequest(URL, "POST", params);
//////            Log.e("doInBackground",""+json);
//////
//////
//////            return json;
////
////        }
//
//        protected void onPostExecute(JSONObject result) {
//
//            // dismiss the dialog once product deleted
//            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
//
//            try {
//                if (result != null) {
//                    Log.e("Debug","reached here"+result);
////                    if(result.getInt("success")==1){
//                        openList();
////                    }
//
//                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//    }
    /** Function to open list of items
     */
    public void openList(){
        finish();
        Intent intent = new Intent(this,List_of_items.class);
        startActivity(intent);
    }
}
