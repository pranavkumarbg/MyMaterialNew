package com.rhcloud.phpnew_pranavkumar.mymaterialnew;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

public class SignupActivity extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mEmail;
    protected Button mSignUpButton,mUpload;
    String username;
    String password;
    String email;
    String imgDecodableString;
    ParseFile file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername = (EditText)findViewById(R.id.usernameField);
        mPassword = (EditText)findViewById(R.id.passwordField);
        mEmail = (EditText)findViewById(R.id.emailField);
        mSignUpButton = (Button)findViewById(R.id.signupButton);
        mUpload=(Button)findViewById(R.id.buttonu);
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                startActivityForResult(galleryIntent, 0);

            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                email = mEmail.getText().toString();

                username = username.trim();
                password = password.trim();
                email = email.trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(SignupActivity.this, imgDecodableString,
                            Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory
                            .decodeFile(imgDecodableString);
                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] image = stream.toByteArray();

                    // Create the ParseFile
                    file = new ParseFile("androidbegin.png", image);
                    // Upload the image into Parse Cloud
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(null == e) {
                                savetoParse();
                            }
                            else
                            {

                            }
                        }
                    });

//                    // Create a New Class called "ImageUpload" in Parse
//                    ParseObject imgupload = new ParseObject("ImageUpload");
//
//
//                    // Create a column named "ImageName" and set the string
//                    imgupload.put("ImageName", "AndroidBegin Logo");
//
//                    // Create a column named "ImageFile" and insert the image
//                    imgupload.put("ImageFile", file);
//
//                    // Create the class and the columns
//                    imgupload.saveInBackground();

                    // create the new user!
//                    ParseUser newUser = new ParseUser();
//                    newUser.setUsername(username);
//                    newUser.setPassword(password);
//                    newUser.setEmail(email);
//                    newUser.put("ImageName", "Avatar");
//
//                    newUser.put("ImageFile", file);
//                    newUser.signUpInBackground(new SignUpCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                // Success!
//                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                            }
//                            else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
//                                builder.setMessage(e.getMessage())
//                                        .setTitle(R.string.signup_error_title)
//                                        .setPositiveButton(android.R.string.ok, null);
//                                AlertDialog dialog = builder.create();
//                                dialog.show();
//                            }
//                        }
//                    });
                }
            }
        });
    }

    private void savetoParse() {

        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.put("ImageName", "Avatar");

        newUser.put("ImageFile", file);
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Success!
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
               // ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
//                imgView.setImageBitmap(BitmapFactory
//                        .decodeFile(imgDecodableString));

//                Bitmap bitmap = BitmapFactory
//                        .decodeFile(imgDecodableString);
//                // Convert it to byte
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                // Compress image to lower quality scale 1 - 100
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] image = stream.toByteArray();
//
//                // Create the ParseFile
//                file = new ParseFile("androidbegin.png", image);
//                // Upload the image into Parse Cloud
//                file.saveInBackground();
//
//                // Create a New Class called "ImageUpload" in Parse
//                ParseObject imgupload = new ParseObject("ImageUpload");
//
//
//                // Create a column named "ImageName" and set the string
//                imgupload.put("ImageName", "AndroidBegin Logo");
//
//                // Create a column named "ImageFile" and insert the image
//                imgupload.put("ImageFile", file);
//
//                // Create the class and the columns
//                imgupload.saveInBackground();

                // Show a simple toast message
                Toast.makeText(SignupActivity.this, imgDecodableString,
                        Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }



}


