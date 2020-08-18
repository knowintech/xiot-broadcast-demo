package com.kowin.iot.udp.broadcastdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";
    private static final String LOCALHOST = "127.0.0.1";

    public static String getLocalhost(Context context) {
        return LOCALHOST;
    }

    public static String getHostAddress(Context context) {
        if (context == null) {
            Log.e(TAG, "context is null");
            return LOCALHOST;
        }

        return getHostAddress((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
    }

    public static String getHostAddress(ConnectivityManager manager) {
        if (manager == null) {
            Log.e(TAG, "manager is null");
            return LOCALHOST;
        }

        Network network = manager.getActiveNetwork();
        if (network == null) {
            Log.i(TAG, "getActiveNetwork is null");
            return LOCALHOST;
        }

        LinkProperties properties = manager.getLinkProperties(network);
        if (properties == null) {
            Log.e(TAG, "getLinkProperties is null");
            return LOCALHOST;
        }

        NetworkType type = getNetworkType(manager, network);
        if (type == NetworkType.WAN) {
            Log.i(TAG, "active network is wan!");
            return LOCALHOST;
        }

        return getHostAddress(manager, network);
    }

    public static Map<Long, NetworkType> getAvailableNetworks(ConnectivityManager manager) {
        Map<Long, NetworkType> networks = new HashMap<>();

        Network network = manager.getActiveNetwork();
        if (network != null) {
            NetworkType type = getNetworkType(manager, network);
            if (type == NetworkType.WIFI || type == NetworkType.LAN) {
                networks.put(network.getNetworkHandle(), type);
            }
        }

        return networks;
    }

    public static NetworkType getNetworkType(ConnectivityManager manager, Network network) {
        LinkProperties properties = manager.getLinkProperties(network);
        if (properties == null) {
            Log.d(TAG, "properties is null");
            return NetworkType.UNDEFINED;
        }

        String interfaceName = properties.getInterfaceName();
        if (interfaceName == null) {
            return NetworkType.UNDEFINED;
        }

        switch (interfaceName) {
            case "wlan0":
                return NetworkType.WIFI;

            case "eth0":
                return NetworkType.LAN;

            case "eth1":
                return NetworkType.WAN;

            default:
                return NetworkType.UNDEFINED;
        }
    }

    public static String getHostAddress(ConnectivityManager manager, Network network) {
        LinkProperties properties = manager.getLinkProperties(network);
        if (properties == null) {
            return null;
        }

        for (LinkAddress linkAddress : properties.getLinkAddresses()) {
            InetAddress address = linkAddress.getAddress();
            if (address instanceof Inet4Address) {
                return address.getHostName();
            }
        }

        return null;
    }

    public static String getBroadcastAddress(String ip) {
        String[] v = ip.split("\\.");
        if (v.length == 4) {
            v[3] = "255";
        }

        return StringUtils.join(".", v);
    }

//    public static String getNetworkTypeName(int type) {
//        switch (type) {
//            case NetworkCapabilities.TRANSPORT_CELLULAR:
//                return "cellular";
//
//            case NetworkCapabilities.TRANSPORT_WIFI:
//                return "wifi";
//
//            case NetworkCapabilities.TRANSPORT_BLUETOOTH:
//                return "bluetooth";
//
//            case NetworkCapabilities.TRANSPORT_ETHERNET:
//                return "ethernet";
//
//            case NetworkCapabilities.TRANSPORT_VPN:
//                return "vpn";
//
//            case NetworkCapabilities.TRANSPORT_WIFI_AWARE:
//                return "wifi-aware";
//
//            case NetworkCapabilities.TRANSPORT_LOWPAN:
//                return "lowpan";
//
//            default:
//                return "unknown";
//        }
//    }
//
//    public static void showAllNetworks(Context context) {
//        Log.d(TAG, "== showAllNetworks ==");
//
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (manager == null) {
//            Log.d(TAG, "ConnectivityManager is null");
//            return;
//        }
//
//        for (Network network : manager.getAllNetworks()) {
//            showNetwork(manager, network);
//        }
//
//        Log.d(TAG, "getActiveNetwork: ");
//        showNetwork(manager, manager.getActiveNetwork());
//    }
//
//    public static void showNetwork(ConnectivityManager manager, Network network) {
//        if (network == null) {
//            Log.d(TAG, "network is null");
//            return;
//        }
//
//        Log.d(TAG, "-----------------------------------------------------");
//        Log.d(TAG, "network: " + network.toString());
//
//        NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);
//        if (capabilities != null) {
//            Log.d(TAG, "  NetworkCapabilities.TRANSPORT_CELLULAR: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
//            Log.d(TAG, "  NetworkCapabilities.TRANSPORT_WIFI: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
//            Log.d(TAG, "  NetworkCapabilities.TRANSPORT_BLUETOOTH: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
//            Log.d(TAG, "  NetworkCapabilities.TRANSPORT_ETHERNET: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
//            Log.d(TAG, "  NetworkCapabilities.TRANSPORT_VPN: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN));
////            Log.d(TAG, "  NetworkCapabilities.TRANSPORT_WIFI_AWARE: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE));
////            Log.d(TAG, "  NetworkCapabilities.TRANSPORT_LOWPAN: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN));
//
//            Log.d(TAG, "  capabilities: " + capabilities.toString());
//
////            TransportInfo transportInfo = capabilities.getTransportInfo();
////            if (transportInfo != null) {
////                Log.d(TAG, "  TransportInfo: " + transportInfo.getClass().getSimpleName());
////            }
//        }
//
//        LinkProperties properties = manager.getLinkProperties(network);
//        if (properties != null) {
//            Log.d(TAG, "=> properties is: " + properties.toString());
//            Log.d(TAG, "=> properties.getInterfaceName: " + properties.getInterfaceName());
//            for (LinkAddress linkAddress : properties.getLinkAddresses()) {
//                Log.d(TAG, "  => linkAddress is: " + linkAddress.toString());
//                Log.d(TAG, "  => linkAddress.getAddress().getHostAddress() is: " + linkAddress.getAddress().getHostAddress());
////                Log.d(TAG, "  => linkAddress.getAddress().getCanonicalHostName() is: " + linkAddress.getAddress().getCanonicalHostName());
////                Log.d(TAG, "  => linkAddress.getAddress().getHostName() is: " + linkAddress.getAddress().getHostName());
//            }
//        }
//    }
}
