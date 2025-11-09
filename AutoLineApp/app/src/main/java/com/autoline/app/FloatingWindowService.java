package com.autoline.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class FloatingWindowService extends Service {
    
    private WindowManager windowManager;
    private View floatingButton;
    private View floatingPanel;
    private WebView webView;
    
    private static boolean isRunning = false;
    
    // פרמטרים לגרירה
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // התחלה כ-Foreground Service
        startForegroundService();
        
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        
        // יצירת העיגול הצף
        createFloatingButton();
        
        // יצירת הפאנל (מוסתר בהתחלה)
        createFloatingPanel();
        
        isRunning = true;
    }
    
    /**
     * הפעלה כ-Foreground Service עם Notification
     */
    private void startForegroundService() {
        String channelId = "floating_service_channel";
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                channelId,
                "AUTO LINE Service",
                NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("שירות רקע של AUTO LINE");
            
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        );
        
        Notification notification = new Notification.Builder(this, channelId)
            .setContentTitle("⚡ AUTO LINE")
            .setContentText("מערכת ניהול מנויים פעילה")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build();
        
        startForeground(1, notification);
    }
    
    /**
     * יצירת העיגול הצף
     */
    private void createFloatingButton() {
        floatingButton = LayoutInflater.from(this).inflate(R.layout.floating_button, null);
        
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        );
        
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;
        
        windowManager.addView(floatingButton, params);
        
        // טיפול בלחיצה וגרירה
        final ImageView buttonImage = floatingButton.findViewById(R.id.floating_icon);
        
        floatingButton.setOnTouchListener(new View.OnTouchListener() {
            private long lastTouchTime = 0;
            private static final long CLICK_THRESHOLD = 200; // 200ms
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastTouchTime = System.currentTimeMillis();
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                        
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingButton, params);
                        return true;
                        
                    case MotionEvent.ACTION_UP:
                        long touchDuration = System.currentTimeMillis() - lastTouchTime;
                        
                        // אם זו לחיצה קצרה (לא גרירה)
                        if (touchDuration < CLICK_THRESHOLD) {
                            togglePanel();
                        }
                        return true;
                }
                return false;
            }
        });
    }
    
    /**
     * יצירת הפאנל המרחף
     */
    private void createFloatingPanel() {
        floatingPanel = LayoutInflater.from(this).inflate(R.layout.floating_panel, null);
        
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
            (int) (getResources().getDisplayMetrics().heightPixels * 0.8),
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        );
        
        params.gravity = Gravity.CENTER;
        
        // WebView להצגת הממשק
        webView = floatingPanel.findViewById(R.id.webView);
        setupWebView();
        
        // כפתור סגירה
        View closeButton = floatingPanel.findViewById(R.id.btnClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePanel();
            }
        });
        
        // לא מוסיפים את הפאנל עדיין - רק כשלוחצים על הכפתור
    }
    
    /**
     * הגדרת WebView
     */
    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        
        webView.setWebViewClient(new WebViewClient());
        
        // טעינת הממשק מ-assets
        webView.loadUrl("file:///android_asset/index.html");
    }
    
    /**
     * פתיחה/סגירה של הפאנל
     */
    private void togglePanel() {
        if (floatingPanel.getParent() == null) {
            showPanel();
        } else {
            hidePanel();
        }
    }
    
    /**
     * הצגת הפאנל
     */
    private void showPanel() {
        if (floatingPanel.getParent() == null) {
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) floatingPanel.getLayoutParams();
            if (params == null) {
                params = new WindowManager.LayoutParams(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
                    (int) (getResources().getDisplayMetrics().heightPixels * 0.8),
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT
                );
                params.gravity = Gravity.CENTER;
            }
            
            try {
                windowManager.addView(floatingPanel, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * הסתרת הפאנל
     */
    private void hidePanel() {
        if (floatingPanel.getParent() != null) {
            windowManager.removeView(floatingPanel);
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        if (floatingButton != null && floatingButton.getParent() != null) {
            windowManager.removeView(floatingButton);
        }
        
        if (floatingPanel != null && floatingPanel.getParent() != null) {
            windowManager.removeView(floatingPanel);
        }
        
        isRunning = false;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    /**
     * בדיקה אם השירות רץ
     */
    public static boolean isServiceRunning() {
        return isRunning;
    }
}
