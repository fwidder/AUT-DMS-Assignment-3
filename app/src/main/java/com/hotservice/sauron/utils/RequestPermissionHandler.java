package com.hotservice.sauron.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handel's all permission requests
 */
public class RequestPermissionHandler {

    /**
     * Calling activity
     */
    private Activity mActivity;

    /**
     * Action listener
     */
    private RequestPermissionListener mRequestPermissionListener;

    /**
     * Android permission request code
     */
    private int mRequestCode;

    /**
     * Requests permissions
     *
     * @param activity    calling activity
     * @param permissions list of permissions
     * @param requestCode Android permission request code
     * @param listener    listener for request reaction
     */
    public void requestPermission(Activity activity, @NonNull String[] permissions, int requestCode,
                                  RequestPermissionListener listener) {
        mActivity = activity;
        mRequestCode = requestCode;
        mRequestPermissionListener = listener;

        if (!needRequestRuntimePermissions()) {
            mRequestPermissionListener.onSuccess();
            return;
        }
        requestUnGrantedPermissions(permissions, requestCode);
    }

    /**
     * Check android version
     *
     * @return if permission request is needed for this android version
     */
    private boolean needRequestRuntimePermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Requests permissions using system function
     *
     * @param permissions Permissions to be requested
     * @param requestCode Android permission request code
     */
    private void requestUnGrantedPermissions(String[] permissions, int requestCode) {
        String[] unGrantedPermissions = findUnGrantedPermissions(permissions);
        if (unGrantedPermissions.length == 0) {
            mRequestPermissionListener.onSuccess();
            return;
        }
        ActivityCompat.requestPermissions(mActivity, unGrantedPermissions, requestCode);
    }

    /**
     * Check for having a permission
     *
     * @param permission permission to check
     * @return if permitted
     */
    private boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * looks trough an array of permissions and checks if they are permitted
     *
     * @param permissions list of permissions to check
     * @return list of not permitted permissions
     */
    private String[] findUnGrantedPermissions(String[] permissions) {
        List<String> unGrantedPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                unGrantedPermissionList.add(permission);
            }
        }
        return unGrantedPermissionList.toArray(new String[0]);
    }

    /**
     * Handle permission result
     *
     * @param requestCode  Android permission request code
     * @param permissions  list of permissions
     * @param grantResults list of results
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d("Permissions", Arrays.toString(permissions) + "|" + Arrays.toString(grantResults));
        if (requestCode == mRequestCode) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mRequestPermissionListener.onFailed();
                        return;
                    }
                }
                mRequestPermissionListener.onSuccess();
            } else {
                mRequestPermissionListener.onFailed();
            }
        }
    }

    /**
     * Interface for permission handling
     */
    public interface RequestPermissionListener {
        void onSuccess();

        void onFailed();
    }
}
