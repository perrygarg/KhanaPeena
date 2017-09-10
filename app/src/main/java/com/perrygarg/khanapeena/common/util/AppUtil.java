package com.perrygarg.khanapeena.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.perrygarg.khanapeena.application.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by perry.garg on 15-01-2017.
 */
public class AppUtil {

    /************** String/Collection/Integer Related *************/

    /**
     * Check String Empty
     *
     * @param stringRes String to be checked
     * @return
     */
    public static boolean isStringEmpty(String stringRes)
    {
        return (stringRes == null || stringRes.trim().isEmpty());
    }

    /**
     * Check Collection Empty
     *
     * @param collection Collection to be checked
     * @return
     */
    public static boolean isCollectionEmpty(Collection<? extends Object> collection)
    {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Check Array Empty
     *
     * @param objectArray Array to be checked
     * @return
     */
    public static<T> boolean isArrayEmpty(Object[] objectArray)
    {
        return objectArray == null || objectArray.length == 0;
    }

    /**
     * Check Object Empty
     *
     * @param object Object to be checked
     * @return
     */
    public static<T> boolean isObjectEmpty(Object object)
    {
        return object == null;
    }


    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

    //Set String Not NULL
    public static String setStringNotNull(String stringRes)
    {
        if(stringRes == null || stringRes.isEmpty())
        {
            return "";
        }

        //Convert Non_breaking (ASCI 160) to Simple Space. trim() does'nt handle it.
        stringRes = stringRes.replace(String.valueOf((char) 160), " ");

        String tempString = stringRes.trim();
        return tempString;
    }

    /**
     * @author perry.garg This method copy the file from assets to Device.
     * @param fileName
     *            , the name of file which has to be copy.
     * @param act
     *            , reference of current activity.
     * @param directoryPath
     *            , the path of directory where file needs to be copied.
     */
    public static void copyFile(String fileName, Activity act, String directoryPath) {
        File file;
        InputStream is = null;
        OutputStream os = null;
        try {
            file = new File(directoryPath + fileName);
            if (file.exists()) {
                return;
            }
            File copyDirectory = new File(directoryPath);
            if (!copyDirectory.exists()) {
                copyDirectory.mkdirs();
            }
            file.createNewFile();
            is = act.getAssets().open(fileName);
            os = new FileOutputStream(file);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Check for NULL - Integer
    public static Integer setIntegerNotNull(Integer value)
    {
        return ((value == null) ? 0 : value);
    }


    /*************** Check Android Version *************/

    /**
     * To check if Android version is 4.1 and above
     * @return True if Android version is 4.1 and above else False
     */
    public static boolean isCurrentVersionJellyBeanAndAbove()
    {
        try
        {
            int currentApiVersion = Build.VERSION.SDK_INT;

            return (currentApiVersion >= Build.VERSION_CODES.JELLY_BEAN);

        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * To check if Android version is 4.1 and above
     * @return True if Android version is 4.1 and above else False
     */
    public static boolean isCurrentVersionKitkatAndAbove()
    {
        try
        {
            int currentApiVersion = Build.VERSION.SDK_INT;

            return (currentApiVersion >= Build.VERSION_CODES.KITKAT);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * To check if Android version is 4.0 and above
     * @return True if Android version is 4.0 and above else False
     */
    public static boolean isCurrentVersionICSAndAbove()
    {
        try
        {
            int currentapiVersion = Build.VERSION.SDK_INT;

            return (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH);

        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * To check if Android version is 3.0 and above
     * @return True if Android version is 3.0 and above else False
     */
    public static boolean isCurrentVersionHoneycombAndAbove()
    {
        try
        {
            int currentapiVersion = Build.VERSION.SDK_INT;

            if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static boolean IsGreaterThanApi19()
    {
        int sdkCode = Build.VERSION.SDK_INT;

        if(Build.VERSION_CODES.KITKAT <= sdkCode)
        {
            return true;
        }

        return false;
    }

    public static boolean isGreaterThanApi21()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return true;

        return false;
    }

    public static boolean isGreaterThanApi23()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return true;

        return false;
    }

    public static boolean isGreaterThanApi24()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return true;

        return false;
    }

    /**
     * To check if Android version is 3.0 and above
     * @return True if Android version is 3.0 and above else False
     */
    public static boolean isCurrentVersionHoneycombmr2AndAbove()
    {
        try
        {
            int currentapiVersion = Build.VERSION.SDK_INT;

            if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /***
     * Close cursor
     * @param cursor
     */
    public static void closeCursor(Cursor cursor)
    {
        if (cursor != null && !cursor.isClosed())
        {
            try
            {
                cursor.close();
            }
            catch (Exception e)
            {
                //Do Nothing
                e.printStackTrace();
            }
        }
    }

    /**
     * Get App Version
     */
    public static int getAppVersionCode()
    {
        //String appVersionName = "";
        int appVersionCode = 0;

        try
        {
            PackageInfo pInfo = MyApplication.appContext.getPackageManager().getPackageInfo(MyApplication.appContext.getPackageName(), 0);
            //appVersionName = pInfo.versionName;
            appVersionCode = pInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            //Consume
        }
        return appVersionCode;
    }

    /**
     * Get Device Id - Android Secure Settings ID
     * @return
     */
    public static String getDeviceId()
    {
        String deviceId = Settings.Secure.getString(MyApplication.appContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        return deviceId;
    }

    /** Returns the consumer friendly device name */
    public static String getDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        return manufacturer + " " + model;
    }


    /** ************************ Deprecated Methods Handling ******************/

    @TargetApi(Build.VERSION_CODES.M)
    public static int getColor(int colorId)
    {
        int color = 0;

        if(AppUtil.isGreaterThanApi23())
        {
            color = MyApplication.appContext.getResources().getColor(colorId, null);
        }
        else
        {
            color = MyApplication.appContext.getResources().getColor(colorId);
        }

        return color;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(int resourceId)
    {
        Drawable drawable = null;

        if(AppUtil.isGreaterThanApi21())
        {
            drawable = MyApplication.appContext.getResources().getDrawable(resourceId, null);
        }
        else
        {
            drawable = MyApplication.appContext.getResources().getDrawable(resourceId);
        }

        return drawable;
    }

    /**
     * This method provides information whether the Internet is available on device or not
     * @return true if internet available otherwise false
     */
    public static boolean isInternetAvailableOnDevice()
    {
        boolean isConnected = false;

        try
        {
            ConnectivityManager connectivity = (ConnectivityManager) MyApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity != null)
            {
                int numberOfNetworkInfo = 0;
                NetworkInfo[] networkInfo = connectivity.getAllNetworkInfo();

                if (networkInfo != null)
                {
                    numberOfNetworkInfo = networkInfo.length;
                    for (int i = 0; i < numberOfNetworkInfo; i++)
                    {
                        if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                        {
                            isConnected = true;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception exception)
        {
            // Consume it
        }

        return isConnected;
    }

    public static boolean isCursorEmpty(Cursor cursor)
    {
        return (cursor == null || cursor.isClosed() || cursor.getCount() == 0);
    }

    public static String getCursorString(Cursor cursor, String name)
    {
        if(isCursorEmpty(cursor))
        {
            return "";
        }
        else
        {
            return cursor.getString(cursor.getColumnIndex(name));
        }
    }

    public static int getCursorInt(Cursor cursor, String name)
    {
        if(isCursorEmpty(cursor))
        {
            return 0;
        }
        else
        {
            return cursor.getInt(cursor.getColumnIndex(name));
        }
    }

    public static float getCursorFloat(Cursor cursor, String name)
    {
        if(isCursorEmpty(cursor))
        {
            return 0;
        }
        else
        {
            return cursor.getFloat(cursor.getColumnIndex(name));
        }
    }

    public static String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "SUN";
                break;
            case 2:
                day = "MON";
                break;
            case 3:
                day = "TUE";
                break;
            case 4:
                day = "WED";
                break;
            case 5:
                day = "THU";
                break;
            case 6:
                day = "FRI";
                break;
            case 7:
                day = "SAT";
                break;
        }
        return day;
    }

    /**
     * This method is used to save file
     * @param path where to save file
     * @param mJsonResponse what to save
     */
    public static void createAndSaveFile(String path, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(path);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is to check Chromebook environment of device
     * @return boolean value
     */
    public static boolean isChromebookEnvironment() {
        if (Build.MANUFACTURER.equalsIgnoreCase("chromium") || Build.MODEL.contains("chrome")) {
            return true;
        }
        return false;
    }

}
