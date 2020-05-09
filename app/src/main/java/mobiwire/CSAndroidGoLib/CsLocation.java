package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class CsLocation {
    public static int getCurLocationMode() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return -1;
            }
            int str = iMyAidlInterface.getCurLocationMode();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("get current location mode : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void setLocationMode(int mode) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.setLocationMode(mode);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set mode location : ");
            sb.append(mode);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String getLocation() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            String str = iMyAidlInterface.getLocation();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("get location  : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAddress() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            String str = iMyAidlInterface.getAddress();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("get address  : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void enableLocation() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableLocation();
            Log.e(AndroidGoCSApi.TAG, "enable location ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disableLocation() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableLocation();
            Log.e(AndroidGoCSApi.TAG, "disable location ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
