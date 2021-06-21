package sg.edu.np.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
    }
    private void requestPermissions(){
        TrackingUtility tU = new TrackingUtility();
        if(tU.HasPermissions(MainActivity.this)){
            return;
        }
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.Q){
            EasyPermissions.requestPermissions(
                    MainActivity.this,
                    "You have to accept permissions",
                    0,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACTIVITY_RECOGNITION
            );
        }else{
            EasyPermissions.requestPermissions(
                    MainActivity.this,
                    "You have to accept permissions",
                    0,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            );
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        TrackingUtility tU = new TrackingUtility();
        if (EasyPermissions.somePermissionPermanentlyDenied(MainActivity.this, perms)){
            new AppSettingsDialog.Builder(MainActivity.this).build().show();
        }else{
            requestPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,MainActivity.this);
    }
}