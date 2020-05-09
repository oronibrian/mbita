package mobiwire.CSAndroidGoLib;

import android.os.RemoteException;
import android.util.Log;
import com.mediatek.settings.service.CSAndoridGo;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.Utils.ServiceUtil;

public class CsOperation {
    public static String[] getLngList() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return null;
            }
            String[] str = iMyAidlInterface.getList();
            Log.e(AndroidGoCSApi.TAG, "get lng list");
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setLanguage(int i) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.getLanguages(i);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set language : ");
            sb.append(i);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void setSysDate(int year, int month, int day) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.setSysDate(year, month, day);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set system date : ");
            sb.append(year);
            sb.append(" - ");
            sb.append(month);
            sb.append(" - ");
            sb.append(day);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void setSysTime(int hour, int minute) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.setSysTime(hour, minute);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set system time : ");
            sb.append(hour);
            sb.append(" - ");
            sb.append(minute);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void setSysZone(String zone) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.setSysZone(zone);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set system zone : ");
            sb.append(zone);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void setTimeFormat(int format) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.setTimeFormat(format);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("set time format : ");
            sb.append(format);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static boolean changeWallpager(String path) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.changeWallpager(path);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("change wallpaper : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeBootAnimation(String path, String name) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.changeBootAnimation(path, name);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("change boot animation : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeShutAnimation(String path, String name) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return false;
            }
            boolean str = iMyAidlInterface.changeShutAnimation(path, name);
            String str2 = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("change shut animation : ");
            sb.append(str);
            Log.e(str2, sb.toString());
            return str;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showStatusbar() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.showStatusbar();
            Log.e(AndroidGoCSApi.TAG, "show Status bar");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void hideStatusbar() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.hideStatusbar();
            Log.e(AndroidGoCSApi.TAG, "hide Status bar");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void blockPanel(boolean status) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.blockPanel(status);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("block Panel");
            sb.append(status);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void hideSettings(int[] lines) {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.hideSettings(lines);
            String str = AndroidGoCSApi.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("hide Settings ");
            sb.append(lines.length);
            Log.e(str, sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void resetSettings() {
        try {
            CSAndoridGo iMyAidlInterface = ServiceUtil.getiMyAidlInterface();
            if (iMyAidlInterface == null) {
                Log.e(AndroidGoCSApi.TAG, "service is KO");
                return;
            }
            iMyAidlInterface.resetSettings();
            Log.e(AndroidGoCSApi.TAG, "reset Settings: ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
