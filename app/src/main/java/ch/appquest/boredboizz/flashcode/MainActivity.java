package ch.appquest.boredboizz.flashcode;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Bundle;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import android.support.design.widget.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // in diesem Attribut wird die Nachricht gespeichert die man eingetipt hat
    private String message;



    private static final  int REQUEST_CAMERA_PERMISSION_RESULT = 0;

    // für auf die Kamera zu zugreifen
    private String mCameraId;
    private CameraManager mCameraManager;
    private CaptureRequest.Builder mBuilder;
    private senderFragment transmit;



    // die Android Main methode wo alles initzialisiert wird
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // seztz das activity_main layout als main an
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // erstellt das Fragment und Plaziert sie auf den Content tag
        transmit = new senderFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content, transmit).commit();


        // TODO flashlight für marcel ????
        if(Build.VERSION_CODES.LOLLIPOP == Build.VERSION.SDK_INT) {
            mBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO);
            mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
        }



        // initzialisiert die BottomNavigation inklusive den Click event.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Diese Methode überpüft ob das Gerät eine Kamera berechtigung hat
        checkValidety();

        // holt sich die Kamera vom System
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
    // Setzt ein Onklick even für die Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();

            // Die Fragments die geswitcht werden:
            // TODO ALLE Fragmenst werden hier definiert.. ändern?


            // switch für welches icon ausgewählt wurde
            switch (item.getItemId()) {
                case R.id.navigation_transmit:
                    // es wird jedes mal ein neuse Fragment erstellt
                    transmit.changeViewPager(true);
                    return true;
                case R.id.navigation_receive:
                    transmit.changeViewPager(false);
                    return true;
            }
            return false;

        }

    };
    public String getMessage() {
        return this.message;
    }
    // setzt die Nachricht für bearbetungs zwecke
    public void setMessage(String message) {
        this.message = message;
    }


    // TODO für marcel
    public void turnOnFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);

            }else {
                // hiä für lolipop
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO für marcel
    public void turnOffFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }else {
                // hiä für lolipop
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // überpüft ob das Handy eine Kamera hat falls nicht kommt ein Popup mit einer nachricht und das Programm wird beendet.
    private void checkValidety() {
        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == REQUEST_CAMERA_PERMISSION_RESULT){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"CameraReceiver can't run without camera services ", Toast.LENGTH_SHORT).show();
            }
        }
    }

}