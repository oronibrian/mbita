package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class CsReboot {
    public static void shutDown() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.shutDown();
            Log.e(AndroidGoCSApi.TAG, "shut down");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void sofewareReboot() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.sofewareReboot();
            Log.e(AndroidGoCSApi.TAG, "reboot");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void factoryReset() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.factoryReset();
            Log.e(AndroidGoCSApi.TAG, "factory reset");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void destory() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.destory();
            Log.e(AndroidGoCSApi.TAG, "destory");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
