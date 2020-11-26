package com.warehouse.base.utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.View;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.warehouse.base.R;

/**
 * Created by Yu on 2017/5/4.
 * Dialog工具类
 */
public class DialogUtils {
    private static AlertDialog dialog;
    public static void showDialog(Context context, String tips, String content, CharSequence negBtnMsg, CharSequence posBtnMsg,
                                  DialogInterface.OnClickListener negativeListener,
                                  DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder( context).setTitle( tips)
                .setMessage( content).setNegativeButton( negBtnMsg, negativeListener)
                .setPositiveButton( posBtnMsg, positiveListener).setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton( AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        alertDialog.getButton( AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
    }

    public static void showDialog(Context context, int tipsId, int contentId, CharSequence negBtnMsg, CharSequence posBtnMsg,
                                  DialogInterface.OnClickListener negativeListener,
                                  DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder( context).setTitle( tipsId)
                .setMessage( contentId).setNegativeButton( negBtnMsg, negativeListener)
                .setPositiveButton( posBtnMsg, positiveListener).setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton( AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        alertDialog.getButton( AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
    }


    public static void showDialog(Context context, String tips, CharSequence negBtnMsg, CharSequence posBtnMsg,
                                  DialogInterface.OnClickListener negativeListener,
                                  DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder( context).setTitle( tips)
                .setNegativeButton( negBtnMsg, negativeListener)
                .setPositiveButton( posBtnMsg, positiveListener).setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton( AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        alertDialog.getButton( AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
    }

    public static void showDialog(Context context, int tipsId, CharSequence negBtnMsg, CharSequence posBtnMsg,
                                  DialogInterface.OnClickListener negativeListener,
                                  DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder( context).setTitle( tipsId)
                .setNegativeButton( negBtnMsg, negativeListener)
                .setPositiveButton( posBtnMsg, positiveListener).setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton( AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        alertDialog.getButton( AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
    }

    /**
     * 带布局的dialog
     */
    public static AlertDialog showDialog(Context context, int tipsId, View view, CharSequence negBtnMsg,
                                         DialogInterface.OnClickListener negativeListener,
                                         CharSequence posBtnMsg,
                                         DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder( context).setTitle( tipsId).setView( view)
                .setNegativeButton( negBtnMsg, negativeListener)
                .setPositiveButton( posBtnMsg, positiveListener).setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton( AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        alertDialog.getButton( AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        return alertDialog;
    }

    public static AlertDialog showDialog(Context context, View view, CharSequence negBtnMsg,
                                         DialogInterface.OnClickListener negativeListener,
                                         CharSequence posBtnMsg,
                                         DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder( context).setView( view)
                .setNegativeButton( negBtnMsg, negativeListener)
                .setPositiveButton( posBtnMsg, positiveListener).setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton( AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        alertDialog.getButton( AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor( context, R.color.b));
        return alertDialog;
    }

    public static void setAlertDialog(Context context, String title, String message, String negative, DialogInterface.OnClickListener negativeListener, String positive, DialogInterface.OnClickListener posiListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);//用户点击对话框外面，对话框不会消失
        dialog=builder.setTitle(title).setMessage(message).setNegativeButton(negative, negativeListener).setPositiveButton(positive, posiListener).show();
    }

    /**
     * 显示一个对话框
     *
     * @param context                    上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title                      标题
     * @param message                    消息
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param cancelButtonClickListener  取消按钮点击监听器
     * @return 对话框
     */
    public static AlertDialog showConfirm(Context context, String title, String message,
                                          DialogInterface.OnClickListener confirmButtonClickListener,
                                          DialogInterface.OnClickListener cancelButtonClickListener)
    {
        return showAlert(context, title, message, "是",
                confirmButtonClickListener, null, null, "否", cancelButtonClickListener, null, true, null, null);
    }


    /**
     * 显示一个对话框
     *
     * @param context                    上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title                      标题
     * @param message                    消息
     * @param confirmButton              确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param centerButton               中间按钮
     * @param centerButtonClickListener  中间按钮点击监听器
     * @param cancelButton               取消按钮
     * @param cancelButtonClickListener  取消按钮点击监听器
     * @param onShowListener             显示监听器
     * @param cancelable                 是否允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     * @param onCancelListener           取消监听器
     * @param onDismissListener          销毁监听器
     * @return 对话框
     */
    public static AlertDialog showAlert(Context context, String title, String message,
                                        String confirmButton, DialogInterface.OnClickListener confirmButtonClickListener,
                                        String centerButton, DialogInterface.OnClickListener centerButtonClickListener,
                                        String cancelButton, DialogInterface.OnClickListener cancelButtonClickListener,
                                        DialogInterface.OnShowListener onShowListener, boolean cancelable,
                                        DialogInterface.OnCancelListener onCancelListener,
                                        DialogInterface.OnDismissListener onDismissListener)
    {
        AlertDialog.Builder promptBuilder = new AlertDialog.Builder(context);
        if (title != null)
        {
            promptBuilder.setTitle(title);
        }
        if (message != null)
        {
            promptBuilder.setMessage(message);
        }
        if (confirmButton != null)
        {
            promptBuilder.setPositiveButton(confirmButton, confirmButtonClickListener);
        }
        if (centerButton != null)
        {
            promptBuilder.setNeutralButton(centerButton, centerButtonClickListener);
        }
        if (cancelButton != null)
        {
            promptBuilder.setNegativeButton(cancelButton, cancelButtonClickListener);
        }
        promptBuilder.setCancelable(true);
        if (cancelable)
        {
            promptBuilder.setOnCancelListener(onCancelListener);
        }

        AlertDialog alertDialog = promptBuilder.create();
        if (!(context instanceof Activity))
        {
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        alertDialog.setOnDismissListener(onDismissListener);
        alertDialog.setOnShowListener(onShowListener);
        alertDialog.show();
        return alertDialog;
    }


}
