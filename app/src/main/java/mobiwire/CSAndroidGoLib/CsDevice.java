package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mediatek.settings.service.DeviceInfo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class CsDevice {
    public static DeviceInfo getDeviceInformation(int securityType, String ssid, String key) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            DeviceInfo str = iMyAidlInterface.getDeviceInformation();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("device information : ");
            sb.append(str.toString());
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
