package mobiwire.CSAndroidGoLib.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;
import com.mediatek.engineermode.atservice.IATService;
import com.mediatek.engineermode.atservice.IATService.Stub;
import java.util.List;

public class ATServiceUtils {
    private static final String TAG = "ATServiceUtils";
    public static IATService atService;
    /* access modifiers changed from: private */
    public static ServiceConnection conn;

    public static void bindService(Context context) {
        Intent service = new Intent(createExplicitFromImplicitIntent(context, new Intent("com.sagereal.atcmd.service.ACTION")));
        if (conn == null) {
            Log.e(TAG, "创建conn并连接");
            conn = new ServiceConnection() {
                public void onServiceConnected(ComponentName name, IBinder service) {
                    String str = ATServiceUtils.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Connected!!! conn = ");
                    sb.append(ATServiceUtils.conn);
                    Log.e(str, sb.toString());
                    ATServiceUtils.atService = Stub.asInterface(service);
                }

                public void onServiceDisconnected(ComponentName name) {
                }
            };
            context.bindService(service, conn, 1);
            return;
        }
        Log.e(TAG, "已经连接上了");
    }

    public static void unBindService(Context context) {
        if (conn != null) {
            Log.e(TAG, "断开连接 connection = null");
            context.unbindService(conn);
            conn = null;
            atService = null;
            return;
        }
        Log.e(TAG, "已经断开了连接");
    }

    public static IATService getATService() {
        return atService;
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentServices(implicitIntent, 0);
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = (ResolveInfo) resolveInfo.get(0);
        ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
}
