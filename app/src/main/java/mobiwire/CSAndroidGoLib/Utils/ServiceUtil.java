package mobiwire.CSAndroidGoLib.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mediatek.settings.service.CSAndoridGo.Stub;
import java.util.List;

public class ServiceUtil {
    private static final String TAG = "ServiceUtil";
    /* access modifiers changed from: private */
    public static ServiceConnection connection;
    /* access modifiers changed from: private */
    public static CSAndoridGo iMyAidlInterface;

    public static void bindRemoteService(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.mediatek.settings.MyService.action");
        Intent service = new Intent(createExplicitFromImplicitIntent(context, intent));
        if (connection == null) {
            Log.e(TAG, "创建conn并连接");
            connection = new ServiceConnection() {
                public void onServiceConnected(ComponentName name, IBinder service) {
                    String str = ServiceUtil.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Connected!!! connection = ");
                    sb.append(ServiceUtil.connection);
                    Log.e(str, sb.toString());
                    ServiceUtil.iMyAidlInterface = Stub.asInterface(service);
                }

                public void onServiceDisconnected(ComponentName componentName) {
                }
            };
            context.bindService(service, connection, 1);
            return;
        }
        Log.e(TAG, "已经连接上了");
    }

    public static void unbindRemoteService(Context context) {
        if (connection != null) {
            Log.e(TAG, "断开连接 connection = null");
            context.unbindService(connection);
            connection = null;
            iMyAidlInterface = null;
            return;
        }
        Log.e(TAG, "已经断开了连接");
    }

    public static CSAndoridGo getiMyAidlInterface() {
        return iMyAidlInterface;
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
