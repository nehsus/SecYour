package com.example.nehsus.hellothings;

import android.media.ImageReader;
import android.media.Image;
import android.app.Activity;
import android.os.*;
import android.util.*;
import android.hardware.*;
import android.os.Bundle;
import com.google.android.things.pio.PeripheralManagerService;
import java.nio.ByteBuffer;


/**
 Nehsus 2017
 **/
public class MainActivity extends Activity {
    private final String TAG = MainActivity.class.getSimpleName();

    private Handler deviceHandler = new Handler();

    private DoorbellCamera camera;
    private Handler cameraHandler;
    private HandlerThread cameraThread;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");


        PeripheralManagerService service = new PeripheralManagerService();

        initialize();
    }

    private void initialize() {
        Log.d(TAG, "initialize() start...");

        configCamera();

        Log.d(TAG, "initialize() done...");
    }
    private void configCamera() {
        Log.d(TAG, "configCamera() start...");

        camera = camera.getInstance();
        cameraThread = new HandlerThread("CameraThread");
        cameraThread.start();
        cameraHandler = new Handler(cameraThread.getLooper());
        DoorbellCamera.initializeCamera(this, cameraHandler, imageAvailableListener);

        Log.d(TAG, "configCamera() done...");
    }
    private ImageReader.OnImageAvailableListener imageAvailableListener =
            new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Log.d(TAG, "onImageAvailable()");

                    Image image = reader.acquireLatestImage();
                    ByteBuffer imageBuf = image.getPlanes()[0].getBuffer();
                    final byte[] imageBytes = new byte[imageBuf.remaining()];
                    imageBuf.get(imageBytes);
                    image.close();

                    savePicture(imageBytes);
                }
            };
    private void savePicture(final byte[] imageBytes) {
        if (imageBytes != null) {
            Log.d(TAG, "savePicture()");

            String imageStr = Base64.encodeToString(
                    imageBytes, Base64.NO_WRAP | Base64.URL_SAFE);
                }
            }
        }
