package com.android.aksiem.eizzy.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager {

    public static PermissionStatus getPermissionStatus(@NonNull Activity activity,
                                                       @NonNull String permission) {
        if (PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(activity, permission)) {
            return PermissionStatus.PERMISSION_GRANTED;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission)) {
            return PermissionStatus.CAN_ASK_PERMISSION;
        } else {
            return PermissionStatus.PERMISSION_DENIED;
        }
    }

    public static boolean checkPermissionEnabled(@NonNull Activity activity,
                                                 @NonNull String permission,
                                                 final int permissionCode,
                                                 String title,
                                                 String message) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(title);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage(message);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            activity.requestPermissions(
                                    new String[]
                                            {permission}
                                    , permissionCode);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionCode);
                }
            } else {
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }

    public static boolean checkPermissionEnabled(@NonNull Activity activity,
                                                 @NonNull String permission,
                                                 final int permissionCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionCode);
            return false;
        } else {
            return true;
        }
    }
}