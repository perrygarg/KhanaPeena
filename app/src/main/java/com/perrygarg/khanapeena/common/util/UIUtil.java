package com.perrygarg.khanapeena.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.application.MyApplication;

/**
 * Created by perry.garg on 07/02/17.
 */

public class UIUtil {

    private static Toast toast;
    private static boolean isShowingGotoSetting;

    /********** Hide / Show Soft Keyboard ***********/

    //Show Soft Keyboard
    public static void showSoftKeyboard(EditText editText)
    {
        //Request Focus
        editText.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    //Hide Soft Keyboard
    public static void hideSoftKeyboard(Activity activity)
    {
        //Get View
        View view = activity.getWindow().getDecorView();

        //Hide Soft Keyboard
        hideSoftKeyboard(view);
    }

    //Hide Soft Keyboard
    public static void hideSoftKeyboard(View view)
    {
        if(view == null)
        {
            return;
        }

        //Get Window Token
        IBinder token = view.getWindowToken();

        //Get InputMethodManager
        InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        //Gain Focus on Current View
        view.requestFocus();

        //Hide Soft Input
        inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /************ Show Toast ************/

    //Show Toast
    public static void showToast(int messageId)
    {
        //Get Application Context
        Context appContext = MyApplication.appContext;

        showToast(appContext.getString(messageId));
    }

    //Show Toast
    public static void showToast(String message)
    {
        //Check Toast
        if(toast != null)
        {
            //Get View of Toast
            View view = toast.getView();

            if(view.isShown())
            {
                return;
            }
        }

        //Get Application Context
        Context appContext = MyApplication.appContext;

        //Create Toast
        toast = Toast.makeText(appContext, message, Toast.LENGTH_LONG);

        //Show toast
        toast.show();
    }

    public static void makeAlert(Context ctx, String message, String title, String positive) {
        DialogInterface.OnClickListener onClickListner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        makeAlert(ctx, message, title, positive, null, onClickListner);

    }

    public static void makeAlert(Context ctx, String message, String title, String positive, DialogInterface.OnClickListener onClickListner) {
        makeAlert(ctx, message, title, positive, null, onClickListner);
    }

    public static void makeAlert(Context ctx, String message, String title, String positive, String negative, DialogInterface.OnClickListener onClickListener) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ctx, R.style.AppTheme);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(positive, onClickListener);
        if (negative != null) {
            alertDialogBuilder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    /************* Progress Dialog *************/

    /**
     * Show Progress Dialog
     *
     * @param context
     * @param title
     * @param message
     * @param isInDeterminent
     * @param isCancelable
     * @return
     */
    public static ProgressDialog showProgressDialog(Context context, String title, String message, boolean isInDeterminent, boolean isCancelable)
    {
        return (ProgressDialog.show(context, title, message, isInDeterminent, isCancelable));
    }

    /**
     * Shows Progress Bar Dialog
     * @param activityContext
     * @return
     */
    public static Dialog showProgressDialog(Context activityContext)
    {
        Dialog dialog = null;

        dialog = showProgressDialog(activityContext, "", "", true, false);

        return dialog;
    }

    public static void dismissDialog(Dialog dialog)
    {
        try
        {
            if (dialog != null)
            {
                dialog.dismiss();
            }
        }
        catch (Exception exception)
        {
            //Consume it
        }
    }

    /**
     * Show SnackBar
     *
     * @param view
     * @param message
     */
    public static Snackbar showSnackbar(View view, String message, boolean isIndefinite)
    {
        int duration = 0;

        if(isIndefinite)
            duration = Snackbar.LENGTH_INDEFINITE;
        else
            duration = Snackbar.LENGTH_LONG;

        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.show();
        return snackbar;
    }

    /**
     * Show SnackBar
     *
     * @param view
     * @param message
     */
    public static Snackbar showSnackbar(View view, String message, String action, View.OnClickListener onClickListener)
    {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, onClickListener);
        snackbar.show();

        return snackbar;
    }

}
