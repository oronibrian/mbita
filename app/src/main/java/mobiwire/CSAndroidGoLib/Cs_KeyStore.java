package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class Cs_KeyStore {
    public static void addKeystoreToList(byte[] keystore) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.addKeystoreToList(keystore);
            Log.e(AndroidGoCSApi.TAG, "add keystore ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void resetListKey() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.resetListKey();
            Log.e(AndroidGoCSApi.TAG, "reset keystore ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static boolean doesKeyExist(byte[] keystore) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.doesKeyExist(keystore);
            Log.e(AndroidGoCSApi.TAG, "does Key Exist");
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
