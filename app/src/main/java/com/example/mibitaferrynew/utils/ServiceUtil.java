package com.example.mibitaferrynew.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;

import com.mediatek.settings.service.CSAndoridGo;

import java.util.List;

public class ServiceUtil {
    private static final String TAG = "ServiceUtil";
    private static ServiceConnection connection;
    private static CSAndoridGo iMyAidlInterface;

    public static void bindRemoteService(Context context){
        final Intent intent = new Intent();
        intent.setAction("com.mediatek.settings.MyService.action"); //若修改了清单文件，一定要重启手机！
        final Intent service = new Intent(createExplicitFromImplicitIntent(context,intent));
        if (null == connection) {
            Log.e(TAG, "创建conn并连接");
            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Log.e(TAG, "Connected!!! connection = " + connection); //iBinder里有Student对象
                    iMyAidlInterface = CSAndoridGo.Stub.asInterface(service);
                }
                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                }
            };
            context.bindService(service, connection, Context.BIND_AUTO_CREATE);
        } else {
            Log.e(TAG, "已经连接上了");
        }
    }

    public static void unbindRemoteService(Context context) {
        if (null != connection) {
            Log.e(TAG, "断开连接 connection = null");
            context.unbindService(connection);
            connection = null;
            iMyAidlInterface = null;
        } else {
            Log.e(TAG, "已经断开了连接");
        }
    }

    public static CSAndoridGo getiMyAidlInterface() {
        return iMyAidlInterface;
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
