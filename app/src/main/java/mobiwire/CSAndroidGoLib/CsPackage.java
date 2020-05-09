package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;
import java.util.List;

public class CsPackage {
    public static void removeApp(String packageName) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.removeApp(packageName);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("remove Package :");
            sb.append(packageName);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void installApp(String path, String packageName) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.installApp(path, packageName);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("install Package :");
            sb.append(path);
            sb.append(" / ");
            sb.append(packageName);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void updateApp(String path, String packageName) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.updateApp(path, packageName);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("update Package :");
            sb.append(path);
            sb.append(" / ");
            sb.append(packageName);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getPackageList() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            List<String> str = iMyAidlInterface.getPackageList();
            Log.e(AndroidGoCSApi.TAG, "get list package ");
            for (int i = 0; i < str.size(); i++) {
                String str2 = AndroidGoCSApi.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("get list package : ");
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

    public static void hideApp(String packageName) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.hideApp(packageName);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("hide Package :");
            sb.append(packageName);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void showApp(String packageName) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.showApp(packageName);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("show Package :");
            sb.append(packageName);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getMainMenuAPPList() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            List<String> str = iMyAidlInterface.getMainMenuAPPList();
            Log.e(AndroidGoCSApi.TAG, "get list Main Menu package ");
            for (int i = 0; i < str.size(); i++) {
                String str2 = AndroidGoCSApi.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("get list Main Menu package : ");
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
