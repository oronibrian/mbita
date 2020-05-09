package mobiwire.CSAndroidGoLib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.mediatek.engineermode.atservice.IATService;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ATServiceUtils;

public class CSAtCommand {
    private static MyBC myBC;

    static class MyBC extends BroadcastReceiver {
        MyBC() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("my.at.command.bc.ACTION".equals(intent.getAction())) {
                String result = intent.getStringExtra("RESULT_CODE");
                String str = AndroidGoCSApi.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onReceive: result = ");
                sb.append(result);
                Log.e(str, sb.toString());
                String str2 = AndroidGoCSApi.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("at command result : ");
                sb2.append(result);
                Log.e(str2, sb2.toString());
                return;
            }
            Log.e(AndroidGoCSApi.TAG, "at command result : action error!");
        }
    }

    public static void getATCmd(String atCmdStr) {
        try {
            IATService atService = ATServiceUtils.getATService();
            if (atService == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            String atCmdStr2 = atCmdStr.toUpperCase();
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("at_cmd = ");
            sb.append(atCmdStr2);
            Log.e(str, sb.toString());
            String at_cmd2 = getCmd2(atCmdStr2);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("AT+");
            sb2.append(atCmdStr2);
            String atCmdStr3 = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("+");
            sb3.append(at_cmd2);
            String at_cmd22 = sb3.toString();
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("will get at cmd : at_cmd = ");
            sb4.append(atCmdStr3);
            sb4.append(", at_cmd2 = ");
            sb4.append(at_cmd22);
            Log.e(str2, sb4.toString());
            atService.getATCmd(atCmdStr3, at_cmd22);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void registerReceiver(Context context) {
        myBC = new MyBC();
        context.registerReceiver(myBC, new IntentFilter("my.at.command.bc.ACTION"));
    }

    public static void unregisterReceiver(Context context) {
        if (myBC != null) {
            context.unregisterReceiver(myBC);
            myBC = null;
        }
    }

    private static String getCmd2(String at_cmd) {
        if (TextUtils.isEmpty(at_cmd)) {
            return null;
        }
        int str_end = 0;
        for (int i = 0; i < at_cmd.length(); i++) {
            char ch = at_cmd.charAt(i);
            if (ch < 'A' || ch > 'Z') {
                break;
            }
            str_end = i;
        }
        String at_cmd2 = at_cmd.substring(0, str_end + 1);
        String str = AndroidGoCSApi.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getCmd2: str_end =");
        sb.append(str_end);
        sb.append(", at_cmd2 = ");
        sb.append(at_cmd2);
        Log.e(str, sb.toString());
        return at_cmd2;
    }
}
