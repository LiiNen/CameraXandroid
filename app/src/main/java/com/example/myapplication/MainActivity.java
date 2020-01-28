package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DeviceAdminReceiver;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    DevicePolicyManager cameraDPM;
    ComponentName componame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componame = new ComponentName(this, CameraDisableReceiver.class);

        // camera admin add
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componame);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "카메라 차단");
        startActivityForResult(intent, 0);
    }
}


class CameraDisableReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context, "카메라가 차단되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Toast.makeText(context, "카메라 차단이 해제되었습니다.", Toast.LENGTH_LONG).show();
    }
}
