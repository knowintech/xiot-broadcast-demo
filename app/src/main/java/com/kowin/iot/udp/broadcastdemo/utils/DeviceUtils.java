package com.kowin.iot.udp.broadcastdemo.utils;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.PermissionChecker;

public final class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    public static String getDeviceName(Context context) {
        return Settings.Global.getString(context.getContentResolver(), Settings.Global.DEVICE_NAME);
    }

    public static String getSerialNumber(Context context) {
        int result = PermissionChecker.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (result == PermissionChecker.PERMISSION_GRANTED) {
            Log.e(TAG, "SN: " + Build.getSerial());
            return Build.getSerial();
        } else {
            Log.e(TAG, "READ_PHONE_STATE: PERMISSION_DENIED!!!");
            return "error-" +
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        }

        /*try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            String serial = (String) get.invoke(c, "ro.serialno");
            Log.e(TAG, "SN: " + serial);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       // return "";
    }

//
//    /**
//     * 获取手机序列号，正式版本使用此方法
//     *
//     * @return 手机序列号
//     */
//    @SuppressLint({"NewApi", "MissingPermission"})
//    public static String getSerialNumber(Context context) {
//        String serial = "";
//
//        try {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//                serial = Build.getSerial();
//            } else {
//                Class<?> c = Class.forName("android.os.SystemProperties");
//                Method get = c.getMethod("get", String.class);
//                serial = (String) get.invoke(c, "ro.serialno");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "读取设备序列号异常：" + e.toString());
//        }
//
//        return serial;
//    }

//    public static String getDeviceIp(Context context) {
//        String ip = "127.0.0.1";
//
//
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (manager != null) {
//            NetworkInfo mobileNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            NetworkInfo wifiNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//            if (Objects.requireNonNull(mobileNetworkInfo).isConnected()) {
//                ip = getLocalIpAddress();
//            } else if (Objects.requireNonNull(wifiNetworkInfo).isConnected()) {
//                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
//                int ipAddress = wifiInfo.getIpAddress();
//                ip = intToIp(ipAddress);
//            }
//        }
//
//        return ip;
//    }
//
//    private static String getLocalIpAddress() {
//        Enumeration allNetInterfaces;
//
//        try {
//            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
//        } catch (SocketException e) {
//            e.printStackTrace();
//            return "";
//        }
//
//        if (allNetInterfaces == null) {
//            return "";
//        }
//
//        while (allNetInterfaces.hasMoreElements()) {
//            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
//
//            Enumeration addresses = netInterface.getInetAddresses();
//            while (addresses.hasMoreElements()) {
//                InetAddress ip = (InetAddress) addresses.nextElement();
//                if (ip instanceof Inet4Address) {
//                    if (! ip.isLoopbackAddress()) {
//                        return ip.getHostAddress();
//                    }
//                }
//            }
//        }
//
//        return "";
//    }
//
//    private static String intToIp(int ipInt) {
//        return (ipInt & 0xFF) + "." +
//                ((ipInt >> 8) & 0xFF) + "." +
//                ((ipInt >> 16) & 0xFF) + "." +
//                ((ipInt >> 24) & 0xFF);
//    }
}
