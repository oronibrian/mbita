package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class CsData {
    public static boolean setDefaultSimOne() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.setDefaultSimOne();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set default Sim 1 : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setDefaultSimTwo() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.setDefaultSimTwo();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set default Sim 2 : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disableData() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableData();
            Log.e(AndroidGoCSApi.TAG, "disable data");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void enableData() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableData();
            Log.e(AndroidGoCSApi.TAG, "enable data");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String getDataStatus() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            String str = iMyAidlInterface.getDataStatus();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("data status : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
