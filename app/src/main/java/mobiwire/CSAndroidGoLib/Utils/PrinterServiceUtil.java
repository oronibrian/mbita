package mobiwire.CSAndroidGoLib.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.sagereal.printer.PrinterInterface;
import com.sagereal.printer.PrinterInterface.Stub;

public class PrinterServiceUtil {
    private static final String TAG = "PrinterServiceUtil";
    public static PrinterInterface atService;

    public static void bindService(Context mContext) {
        mContext.bindService(getPrintIntent(), new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                Log.e(PrinterServiceUtil.TAG, "aidl connect fail");
                PrinterServiceUtil.atService = null;
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(PrinterServiceUtil.TAG, "aidl connect success");
                PrinterServiceUtil.atService = Stub.asInterface(service);
            }
        }, 1);
    }

    public static PrinterInterface getPrinterService() {
        return atService;
    }

    public static Intent getPrintIntent() {
        Intent aidlIntent = new Intent();
        aidlIntent.setAction("sagereal.intent.action.START_PRINTER_SERVICE_AIDL");
        aidlIntent.setPackage("com.sagereal.printer");
        return aidlIntent;
    }
}
