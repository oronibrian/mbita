package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.APN;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;
import java.util.List;

public class CsApn {
    public static APN getAPN(int slotId) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            APN str = iMyAidlInterface.getAPN(slotId);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("get Apn : ");
            sb.append(slotId);
            sb.append(" = ");
            sb.append(str.toString());
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<APN> getApnList(int slotId) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            List<APN> str = iMyAidlInterface.getApnList(slotId);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("list Apn : ");
            sb.append(slotId);
            Log.e(str2, sb.toString());
            for (int i = 0; i < str.size(); i++) {
                String str3 = AndroidGoCSApi.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("list Apn : ");
                sb2.append(str.get(i));
                sb2.append(" = ");
                sb2.append(((APN) str.get(i)).toString());
                Log.e(str3, sb2.toString());
            }
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateApn(APN newApn, boolean force) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.updateApn(newApn, force);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("update Apn : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeAPN(String apnKey) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.removeAPN(apnKey);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("remove Apn : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean restoreDefault(String apnKey) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.restoreDefault();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("restore Default : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void getOperator(int slotId) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
            } else {
                iMyAidlInterface.getOperator(slotId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static boolean addApn(APN newApn, boolean force) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.addApn(newApn, force);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("add Apn : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getMccMnc(int slotId) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            String str = iMyAidlInterface.getMccMnc(slotId);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("mcc & mnc : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
