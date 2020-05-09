package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class CsStorage {
    public static void enableStorage() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableStorage();
            Log.e(AndroidGoCSApi.TAG, "enable Storage ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disableStorage() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableStorage();
            Log.e(AndroidGoCSApi.TAG, "disable Storage ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void enableUnknownSource() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableUnknownSource();
            Log.e(AndroidGoCSApi.TAG, "enable Unknown source ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disableUnknownSource() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableUnknownSource();
            Log.e(AndroidGoCSApi.TAG, "disable Unknown source ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void enableAdb() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableAdb();
            Log.e(AndroidGoCSApi.TAG, "enable adb ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disableAdb() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableAdb();
            Log.e(AndroidGoCSApi.TAG, "disable adb ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void enableMtp() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enableMtp();
            Log.e(AndroidGoCSApi.TAG, "enable Mtp ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disableMtp() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disableMtp();
            Log.e(AndroidGoCSApi.TAG, "disable Mtp ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void enablePtp() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.enablePtp();
            Log.e(AndroidGoCSApi.TAG, "enable Ptp ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disablePtp() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.disablePtp();
            Log.e(AndroidGoCSApi.TAG, "disable Ptp ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
