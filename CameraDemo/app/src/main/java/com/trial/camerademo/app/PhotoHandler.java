package com.trial.camerademo.app;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stamboli on 4/24/14.
 */
public class PhotoHandler implements Camera.PictureCallback {
    private final Context context;

    public PhotoHandler(Context context){
        this.context = context;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFileDir = getDir();

        if(!pictureFileDir.exists() && !pictureFileDir.mkdirs()){
            Log.d(MainActivity.DEBUG_TAG, "Cant create directory to save Image");
            Toast.makeText(context,"Cant create directory to save Image",Toast.LENGTH_SHORT).show();
        }
        Log.d(MainActivity.DEBUG_TAG, "create directory to save Image");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_"+date+".jpg";

        String fileName = pictureFileDir.getPath() + File.separator + photoFile;
        File pictureFile = new File(fileName);

        try{
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();

        }catch(Exception error){
            Log.d(MainActivity.DEBUG_TAG, "File" + fileName + "not saved: "+ error.getMessage());
            Toast.makeText(context, "Image could not be saved.", Toast.LENGTH_LONG).show();
        }
    }

    private File getDir(){
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir,"CameraApiDemo");
    }
}
