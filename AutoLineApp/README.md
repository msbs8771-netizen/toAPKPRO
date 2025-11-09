# 📱 AUTO LINE - אפליקציית אנדרואיד

אפליקציית אנדרואיד עם עיגול צף למנהל מנויים אוטומטי.

## 🎯 תכונות

### ✨ עיגול צף קבוע
- עיגול צף שנשאר על המסך תמיד
- ניתן לגרירה לכל מקום
- לחיצה פותחת את הממשק
- עובד מעל כל אפליקציה

### 🎨 ממשק מתקדם
- WebView עם HTML/CSS/JavaScript
- עיצוב מודרני ומעוצב
- סטטיסטיקות בזמן אמת
- היסטוריה מלאה

### 🔧 תכונות טכניות
- Foreground Service - לא נסגר
- Boot Receiver - הפעלה אוטומטית
- Local Storage - שמירת נתונים
- Material Design

---

## 📦 דרישות מקדימות

### 1. התקנת Android Studio
```
הורד מ: https://developer.android.com/studio
התקן את Android Studio
```

### 2. SDK Tools
```
Android SDK 23 ומעלה
Build Tools 34.0.0
```

### 3. JDK
```
Java Development Kit 8 ומעלה
```

---

## 🚀 בניית האפליקציה

### שלב 1: פתיחת הפרויקט
```
1. פתח Android Studio
2. File → Open
3. בחר את תיקיית AutoLineApp
4. חכה ש-Gradle sync יסתיים
```

### שלב 2: בדיקת הגדרות
```
File → Project Structure
- Compile SDK: 34
- Min SDK: 23
- Target SDK: 34
```

### שלב 3: בנייה
```
אופציה 1: דרך Android Studio
Build → Build Bundle(s) / APK(s) → Build APK(s)

אופציה 2: דרך Terminal
./gradlew assembleDebug

אופציה 3: דרך Command Line (Windows)
gradlew.bat assembleDebug
```

### שלב 4: מציאת ה-APK
```
הקובץ נמצא ב:
AutoLineApp/app/build/outputs/apk/debug/app-debug.apk
```

---

## 📲 התקנה במכשיר

### שלב 1: העברת הקובץ
```
העתק את app-debug.apk למכשיר האנדרואיד
(דרך USB, Bluetooth, Email, וכו')
```

### שלב 2: הפעלת "מקורות לא ידועים"
```
הגדרות → אבטחה → מקורות לא ידועים
או
הגדרות → אפליקציות → גישה מיוחדת → התקנה ממקורות לא ידועים
```

### שלב 3: התקנה
```
1. פתח את קובץ app-debug.apk במכשיר
2. לחץ "התקן"
3. אשר הרשאות
```

### שלב 4: הפעלה
```
1. פתח את AUTO LINE
2. לחץ "הפעל שירות"
3. אשר הרשאה להצגה מעל אפליקציות
4. העיגול הצף יופיע!
```

---

## 🎮 שימוש באפליקציה

### הפעלה ראשונה
```
1. פתח את האפליקציה
2. לחץ "▶ הפעל שירות"
3. אשר הרשאה להצגה מעל אפליקציות
4. העיגול הצף מופיע בצד השמאלי
5. סגור את האפליקציה - העיגול נשאר!
```

### שימוש יומיומי
```
1. לחץ על העיגול הצף
2. הממשק נפתח
3. הזן מספר טלפון
4. בחר יומי/שנתי
5. לחץ "התחל אוטומציה"
6. סגור את הפאנל - העיגול נשאר
```

### גרירה
```
לחץ והחזק על העיגול
גרור לכל מקום במסך
שחרר
```

### עצירה
```
פתח את האפליקציה
לחץ "⏹ עצור שירות"
```

---

## 🔧 התאמה אישית

### שינוי צבעים
```
עריכת: app/src/main/res/values/colors.xml

<color name="primary">#00ffaa</color>  ← צבע ראשי
<color name="secondary">#ef4444</color> ← צבע משני
```

### שינוי מיקום התחלתי
```
עריכת: FloatingWindowService.java
שורה 120:

params.x = 0;      ← מרחק מצד שמאל
params.y = 100;    ← מרחק מלמעלה
```

### שינוי גודל פאנל
```
עריכת: FloatingWindowService.java
שורה 175:

(int) (getResources().getDisplayMetrics().widthPixels * 0.95)  ← רוחב 95%
(int) (getResources().getDisplayMetrics().heightPixels * 0.8)  ← גובה 80%
```

### שינוי ממשק
```
עריכת: app/src/main/assets/index.html

שנה HTML, CSS, JavaScript כרצונך
```

---

## 🐛 פתרון בעיות

### שגיאת Build
```
❌ Gradle sync failed

פתרון:
1. File → Invalidate Caches → Invalidate and Restart
2. Build → Clean Project
3. Build → Rebuild Project
```

### לא מוצא SDK
```
❌ Android SDK not found

פתרון:
1. File → Project Structure → SDK Location
2. הגדר את נתיב ה-SDK
3. Sync Project with Gradle Files
```

### העיגול לא מופיע
```
❌ Floating button doesn't appear

בדיקה:
1. הגדרות → אפליקציות → AUTO LINE
2. הרשאות → הצגה מעל אפליקציות אחרות
3. ודא שמופעל ✅
```

### הממשק לא נטען
```
❌ WebView shows blank page

פתרון:
1. בדוק שקובץ index.html קיים ב-assets
2. בדוק ש-JavaScript enabled בקוד
3. נקה את ה-cache של האפליקציה
```

### השירות נעצר
```
❌ Service stops after some time

פתרון:
1. הגדרות → אפליקציות → AUTO LINE
2. סוללה → ללא הגבלה
3. או: הוסף לרשימה לבנה של החיסכון בסוללה
```

---

## 📂 מבנה הפרויקט

```
AutoLineApp/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml          ← הגדרות + הרשאות
│   │       ├── java/com/autoline/app/
│   │       │   ├── MainActivity.java        ← מסך ראשי
│   │       │   ├── FloatingWindowService.java ← עיגול צף
│   │       │   └── BootReceiver.java        ← הפעלה אוטומטית
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml    ← UI מסך ראשי
│   │       │   │   ├── floating_button.xml  ← UI עיגול
│   │       │   │   └── floating_panel.xml   ← UI פאנל
│   │       │   ├── drawable/                ← אייקונים ורקעים
│   │       │   └── values/
│   │       │       ├── colors.xml           ← צבעים
│   │       │       ├── strings.xml          ← טקסטים
│   │       │       └── styles.xml           ← סגנונות
│   │       └── assets/
│   │           └── index.html               ← ממשק WebView
│   └── build.gradle                         ← הגדרות בנייה
│
├── build.gradle                             ← הגדרות פרויקט
├── settings.gradle                          ← הגדרות Gradle
├── gradle.properties                        ← מאפייני Gradle
└── README.md                                ← המדריך הזה
```

---

## 🔒 הרשאות

### הרשאות נדרשות
```xml
SYSTEM_ALERT_WINDOW      ← הצגה מעל אפליקציות
INTERNET                 ← גישה לאינטרנט
FOREGROUND_SERVICE       ← שירות רקע
RECEIVE_BOOT_COMPLETED   ← הפעלה אוטומטית
```

### למה צריך כל הרשאה?
```
SYSTEM_ALERT_WINDOW:
→ להציג את העיגול הצף מעל אפליקציות אחרות

INTERNET:
→ לתקשר עם מערכת ILVIP

FOREGROUND_SERVICE:
→ לשמור את השירות פעיל ברקע

RECEIVE_BOOT_COMPLETED:
→ להפעיל אוטומטית אחרי אתחול
```

---

## 🎯 שימוש מתקדם

### חיבור ל-ILVIP
```javascript
// ב-index.html, הוסף:

async function connectToILVIP() {
    const response = await fetch('https://ilvip.example.com/api', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            phone: phoneNumber,
            type: subscriptionType
        })
    });
    
    const result = await response.json();
    return result;
}
```

### JavaScript Bridge
```java
// ב-FloatingWindowService.java, הוסף:

webView.addJavascriptInterface(new WebAppInterface(), "Android");

class WebAppInterface {
    @JavascriptInterface
    public void showToast(String message) {
        Toast.makeText(FloatingWindowService.this, message, Toast.LENGTH_SHORT).show();
    }
}
```

### שימוש מ-JavaScript
```javascript
// ב-index.html:
Android.showToast('הודעה מ-JavaScript!');
```

---

## 📋 Checklist לפני שליחה

לפני שמשתף את ה-APK:

- [ ] נבנה בהצלחה ללא שגיאות
- [ ] נבדק במכשיר אמיתי
- [ ] עובד עם כל הרשאות
- [ ] העיגול צף מופיע
- [ ] הפאנל נפתח
- [ ] WebView טוען נכון
- [ ] השירות לא נעצר
- [ ] הפעלה אוטומטית עובדת
- [ ] ה-APK חתום (לפרודקשן)
- [ ] גרסה מעודכנת ב-build.gradle

---

## 🚀 גרסה לפרודקשן

### חתימת APK
```
1. Build → Generate Signed Bundle / APK
2. APK
3. Create new key store
4. מלא פרטים
5. Release
6. Finish
```

### APK החתום יהיה ב:
```
app/build/outputs/apk/release/app-release.apk
```

---

## 💡 טיפים

1. **תמיד בדוק ב-Logcat** - Logcat מציג שגיאות
2. **נקה Cache** - Build → Clean Project
3. **Rebuild** - Build → Rebuild Project
4. **USB Debugging** - הפעל למכשיר בפיתוח
5. **ProGuard** - הפעל לפרודקשן (מקטין APK)

---

## 📞 תמיכה

אם יש בעיה:
1. בדוק Logcat (View → Tool Windows → Logcat)
2. בדוק Build Output
3. נקה ו-Rebuild
4. עדכן Gradle

---

## 🎉 מזל טוב!

יש לך עכשיו:
- ✅ אפליקציית אנדרואיד מלאה
- ✅ עיגול צף עובד
- ✅ ממשק מעוצב
- ✅ מוכן להתקנה

**זמן בניה משוער:** 5-10 דקות  
**גודל APK:** ~3-5 MB  
**תאימות:** Android 6.0 (API 23) ומעלה

---

**גרסה:** 1.0.0  
**תאריך:** נובמבר 2025  
**מפתח:** מלכה - כנפי רוח  
**רישיון:** MIT
