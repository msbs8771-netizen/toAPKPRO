package com.autoline.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1234;
    private Button btnStartService;
    private Button btnStopService;
    private TextView tvStatus;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // אתחול Views
        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);
        tvStatus = findViewById(R.id.tvStatus);
        
        // בדיקת הרשאות
        checkOverlayPermission();
        
        // כפתור הפעלה
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOverlayPermission()) {
                    startFloatingService();
                }
            }
        });
        
        // כפתור עצירה
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopFloatingService();
            }
        });
        
        updateStatus();
    }
    
    /**
     * בדיקה ובקשת הרשאה לעיגול צף
     */
    private boolean checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // צריך לבקש הרשאה
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
                
                Toast.makeText(this, 
                    "נא לאשר הרשאה להציג מעל אפליקציות אחרות", 
                    Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
    
    /**
     * טיפול בתוצאת בקשת הרשאה
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "הרשאה אושרה! עכשיו ניתן להפעיל", Toast.LENGTH_SHORT).show();
                    updateStatus();
                } else {
                    Toast.makeText(this, "הרשאה נדחתה. האפליקציה לא תוכל לעבוד.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    
    /**
     * הפעלת שירות העיגול הצף
     */
    private void startFloatingService() {
        Intent serviceIntent = new Intent(this, FloatingWindowService.class);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
        
        Toast.makeText(this, "⚡ AUTO LINE מופעל!", Toast.LENGTH_SHORT).show();
        updateStatus();
        
        // סגירת האפליקציה - השירות ימשיך לרוץ
        finish();
    }
    
    /**
     * עצירת שירות העיגול הצף
     */
    private void stopFloatingService() {
        Intent serviceIntent = new Intent(this, FloatingWindowService.class);
        stopService(serviceIntent);
        
        Toast.makeText(this, "השירות הופסק", Toast.LENGTH_SHORT).show();
        updateStatus();
    }
    
    /**
     * עדכון סטטוס השירות
     */
    private void updateStatus() {
        boolean isRunning = FloatingWindowService.isServiceRunning();
        
        if (isRunning) {
            tvStatus.setText("⚡ AUTO LINE פעיל");
            tvStatus.setTextColor(0xFF00FFAA);
            btnStartService.setEnabled(false);
            btnStopService.setEnabled(true);
        } else {
            tvStatus.setText("AUTO LINE כבוי");
            tvStatus.setTextColor(0xFF999999);
            btnStartService.setEnabled(true);
            btnStopService.setEnabled(false);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateStatus();
    }
}
