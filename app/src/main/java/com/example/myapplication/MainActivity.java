package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DeviceAdminReceiver;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.view.WindowManager;

public class MainActivity extends Activity{

    DevicePolicyManager cameraDPM;
    ComponentName componame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);

        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        setContentView(R.layout.activity_main);

        cameraDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componame = new ComponentName(this, CameraDisableReceiver.class);
        // camera admin add

        findViewById(R.id.cameraBtn).setOnClickListener(myClickListener);
    }

    Button.OnClickListener myClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            // do camera on / off
            System.out.println("버튼 클릭클릭");

            if(!cameraDPM.isAdminActive(componame)){
                System.out.println("권한이 없습니다.");
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componame);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "카메라 차단");
                startActivityForResult(intent, 0);
            }
            else{
                System.out.println("권한이 있습니다.");
            }

            if(cameraDPM.getCameraDisabled(componame)) {
                cameraDPM.setCameraDisabled(componame, false);
                toastMessageEnabled();
            }
            else if(!cameraDPM.getCameraDisabled(componame)) {
                cameraDPM.setCameraDisabled(componame, true);
                toastMessageDisabled();
            }
            else{
                System.out.println("왜안되징..");
            }

        }
    };

    public void toastMessageDisabled(){
        Toast.makeText(this, "카메라가 차단되었습니다.", Toast.LENGTH_LONG).show();
    }
    public void toastMessageEnabled(){
        Toast.makeText(this, "카메라 차단이 해제되었습니다.", Toast.LENGTH_LONG).show();
    }
}


class CameraDisableReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
    }

    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
    }
}
