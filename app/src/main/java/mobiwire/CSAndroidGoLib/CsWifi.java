package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;
import java.util.List;

public class CsWifi {
    public static void enableWifi() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableWifi();
            Log.e(AndroidGoCSApi.TAG, "enable wifi");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disableWifi() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableWifi();
            Log.e(AndroidGoCSApi.TAG, "disable wifi");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String getWifiStatus() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            String str = iMyAidlInterface.getWifiStatus();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("wifi status : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void disableHotspot() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableHotspot();
            Log.e(AndroidGoCSApi.TAG, "disable hotspot wifi");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void enableHotspot() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableHotspot();
            Log.e(AndroidGoCSApi.TAG, "enable hotspot wifi");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void editHotspot(String name, String password) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.editHotspot(name, password);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("edit hotspot wifi : ");
            sb.append(name);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static boolean connectToWiFi(int securityType, String ssid, String key) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.connectToWiFi(securityType, ssid, key);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("connect to wifi : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getListWifi(int securityType, String ssid, String key) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            List<String> str = iMyAidlInterface.getListWifi();
            Log.e(AndroidGoCSApi.TAG, "get list wifi ");
            for (int i = 0; i < str.size(); i++) {
                String str2 = AndroidGoCSApi.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("list wifi : ");
                sb.append((String) str.get(i));
                sb.append(" = ");
                sb.append((String) str.get(i));
                Log.e(str2, sb.toString());
            }
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
