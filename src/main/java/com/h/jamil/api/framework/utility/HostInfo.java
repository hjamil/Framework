package com.h.jamil.api.framework.utility;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class HostInfo {

    public static String getHostAddress() {

        String ipAddr = "";
        NetworkInterface netInt;

        try {
            ipAddr = InetAddress.getLocalHost().getHostAddress();
            return ipAddr;
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public static String getHostAddressForLog() {

        String ipAddr = getHostAddress();
        if (ipAddr != null) {
            return "[" + ipAddr + "]";
        } else {
            return "[UNKNOWN CURRENT HOST]";
        }
    }

    public static String getHostName() {

        String hostName = "";
        NetworkInterface netInt;

        try {
            hostName = InetAddress.getLocalHost().getHostName();
            return hostName;
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public static String getHostNameForLog() {

        String hostName = getHostName();
        if (hostName != null) {
            return "[" + hostName + "]";
        } else {
            return "[UNKNOWN CURRENT HOST]";
        }
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
