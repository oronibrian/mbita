package mobiwire.CSAndroidGoLib;

import android.content.Context;

import com.mobiwire.CSAndroidGoLib.CsSimSlot;
import com.mobiwire.CSAndroidGoLib.Utils.PrinterServiceUtil;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class AndroidGoCSApi {
    public static String TAG = "AndroidGoCSApi";
    public static CsSimSlot simSlot;

    public AndroidGoCSApi(Context mContext) {
        ServiceUtil.bindRemoteService(mContext);
        PrinterServiceUtil.bindService(mContext);
    }
}
