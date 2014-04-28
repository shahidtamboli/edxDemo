package com.trial.camerademo.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends Activity{

    public final static String DEBUG_TAG = "MainActivity";
    private Camera camera;
    private int cameraId = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
        }else{
            cameraId = findFrontFacingCamera();
            if(cameraId < 0){
                Toast.makeText(this, "No front facing camera found.", Toast.LENGTH_LONG).show();
            }else{
                camera = Camera.open(cameraId);

                System.out.println("Camera Initialized");
            }
        }
    }

    public void onClick(View view){
        System.out.println("Camera Initialized");
        if(camera != null) {
            try{
                camera.startPreview();
                camera.takePicture(null,null,new PhotoHandler(getApplicationContext()));
            }catch(Exception e){
                Log.e(DEBUG_TAG,"Error in opening Camera");
            }
        }else{
            Log.d(DEBUG_TAG,"Camera is null");
        }
    }

    private int findFrontFacingCamera(){
        int cameraId = -1;
        //Searching for the front facing Camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for(int i=0; i< numberOfCameras; i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i,info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }
}
