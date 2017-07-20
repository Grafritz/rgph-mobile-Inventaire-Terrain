package ht.ihsi.inventaireterrain.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ht.ihsi.inventaireterrain.Constant.Constant;

/**
 * Created by JeanFritz on 3/30/2016.
 */
public class ToastUtility {
    public static String TAG = "TAG-IHSI:";
    private static Toast toast;
    private static ProgressDialog progressDialog;

    public static void ToastMessage(Context context, String Message, int _Gravity) {
        toast = Toast.makeText(context, Message, Toast.LENGTH_LONG);
        if(_Gravity == Constant.GravityCenter)
            toast.setGravity(Constant.GravityCenter, 0, 0);
        else if(_Gravity == Constant.GravityBottom)
            toast.setGravity(Constant.GravityBottom, 0, 0);
        else if(_Gravity == Constant.GravityTop)
            toast.setGravity(Constant.GravityTop, 0, 0);
        else if(_Gravity == Constant.GravityEnd)
            toast.setGravity(Constant.GravityEnd, 0, 0);

        toast.show();
    }

    public static void ToastMessage(Context context, String Message) {
        toast = Toast.makeText(context, Message, Toast.LENGTH_LONG);
        toast.setGravity(Constant.GravityBottom, 0, 0);
        toast.show();
    }

    public static void LogCat(Context context, String msg) {
        String TAG2 = TAG + context.getClass().getSimpleName();
        Log.i(TAG2, msg);
    }
    public static void LogCat( String msg) {
        Log.e(TAG, msg);
    }
    public static void LogCat(String log, String msg) {
        if(log.equalsIgnoreCase("E"))
            Log.e(TAG, msg);
        if(log.equalsIgnoreCase("I"))
            Log.i(TAG, msg);
        if(log.equalsIgnoreCase("D"))
            Log.d(TAG, msg);
        if(log.equalsIgnoreCase("W"))
            Log.w(TAG, msg);
        if(log.equalsIgnoreCase("V"))
            Log.v(TAG, msg);
    }
    //

}