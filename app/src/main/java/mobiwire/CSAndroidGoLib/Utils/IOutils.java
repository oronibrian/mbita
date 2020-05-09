package mobiwire.CSAndroidGoLib.Utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOutils {
    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int read = is.read(buffer);
            int ch = read;
            if (read != -1) {
                bytestream.write(buffer, 0, ch);
            } else {
                byte[] data = bytestream.toByteArray();
                bytestream.close();
                return data;
            }
        }
    }

    public static InputStream bitmap2inputstream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static byte[] bitmap2byteArrays(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0053, code lost:
        if (r0 != null) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0055, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0079, code lost:
        if (r0 == null) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007c, code lost:
        r3 = new java.lang.StringBuilder();
        r3.append("read partition , finally result is： ");
        r3.append(r2);
        android.util.Log.e("yanyongyong777", r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0092, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String readPrinterStatus(String r9) throws IOException {
        /*
            r0 = 0
            r1 = 0
            r2 = r1
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x005b }
            r3.<init>(r9)     // Catch:{ Exception -> 0x005b }
            boolean r4 = r3.exists()     // Catch:{ Exception -> 0x005b }
            if (r4 != 0) goto L_0x002b
            java.lang.String r4 = "yanyongyong777"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005b }
            r5.<init>()     // Catch:{ Exception -> 0x005b }
            java.lang.String r6 = "read partition,The current path does not exist. path is : "
            r5.append(r6)     // Catch:{ Exception -> 0x005b }
            r5.append(r9)     // Catch:{ Exception -> 0x005b }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x005b }
            android.util.Log.e(r4, r5)     // Catch:{ Exception -> 0x005b }
            if (r0 == 0) goto L_0x002a
            r0.close()
        L_0x002a:
            return r1
        L_0x002b:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x005b }
            r1.<init>(r3)     // Catch:{ Exception -> 0x005b }
            r0 = r1
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x005b }
            r4 = 0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005b }
            r5.<init>()     // Catch:{ Exception -> 0x005b }
        L_0x003b:
            int r6 = r0.read(r1)     // Catch:{ Exception -> 0x005b }
            r4 = r6
            if (r6 <= 0) goto L_0x004e
            java.lang.String r6 = new java.lang.String     // Catch:{ Exception -> 0x005b }
            r7 = 0
            java.lang.String r8 = "utf-8"
            r6.<init>(r1, r7, r4, r8)     // Catch:{ Exception -> 0x005b }
            r5.append(r6)     // Catch:{ Exception -> 0x005b }
            goto L_0x003b
        L_0x004e:
            java.lang.String r6 = r5.toString()     // Catch:{ Exception -> 0x005b }
            r2 = r6
            if (r0 == 0) goto L_0x007c
        L_0x0055:
            r0.close()
            goto L_0x007c
        L_0x0059:
            r1 = move-exception
            goto L_0x0093
        L_0x005b:
            r1 = move-exception
            java.lang.String r3 = "yanyongyong"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0059 }
            r4.<init>()     // Catch:{ all -> 0x0059 }
            java.lang.String r5 = "IOException      "
            r4.append(r5)     // Catch:{ all -> 0x0059 }
            java.lang.String r5 = r1.getMessage()     // Catch:{ all -> 0x0059 }
            r4.append(r5)     // Catch:{ all -> 0x0059 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0059 }
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x0059 }
            r1.printStackTrace()     // Catch:{ all -> 0x0059 }
            if (r0 == 0) goto L_0x007c
            goto L_0x0055
        L_0x007c:
            java.lang.String r1 = "yanyongyong777"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "read partition , finally result is： "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r1, r3)
            return r2
        L_0x0093:
            if (r0 == 0) goto L_0x0098
            r0.close()
        L_0x0098:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiwire.CSAndroidGoLib.Utils.IOutils.readPrinterStatus(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005f, code lost:
        if (r0 != null) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0061, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0085, code lost:
        if (r0 == null) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0088, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append("read partition , finally result is： ");
        r5.append(r4);
        android.util.Log.e("yanyongyong777", r5.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009e, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getPrinterStatus() throws IOException {
        /*
            r0 = 0
            r1 = 0
            java.lang.String r2 = "/proc/printer"
            r3 = -1
            r4 = r3
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0067 }
            r5.<init>(r2)     // Catch:{ Exception -> 0x0067 }
            boolean r6 = r5.exists()     // Catch:{ Exception -> 0x0067 }
            if (r6 != 0) goto L_0x002e
            java.lang.String r6 = "yanyongyong777"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0067 }
            r7.<init>()     // Catch:{ Exception -> 0x0067 }
            java.lang.String r8 = "read partition,The current path does not exist. path is : "
            r7.append(r8)     // Catch:{ Exception -> 0x0067 }
            r7.append(r2)     // Catch:{ Exception -> 0x0067 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0067 }
            android.util.Log.e(r6, r7)     // Catch:{ Exception -> 0x0067 }
            if (r0 == 0) goto L_0x002d
            r0.close()
        L_0x002d:
            return r3
        L_0x002e:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0067 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x0067 }
            r0 = r3
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0067 }
            r6 = 0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0067 }
            r7.<init>()     // Catch:{ Exception -> 0x0067 }
        L_0x003e:
            int r8 = r0.read(r3)     // Catch:{ Exception -> 0x0067 }
            r6 = r8
            if (r8 <= 0) goto L_0x0051
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x0067 }
            r9 = 0
            java.lang.String r10 = "utf-8"
            r8.<init>(r3, r9, r6, r10)     // Catch:{ Exception -> 0x0067 }
            r7.append(r8)     // Catch:{ Exception -> 0x0067 }
            goto L_0x003e
        L_0x0051:
            java.lang.String r8 = r7.toString()     // Catch:{ Exception -> 0x0067 }
            r1 = r8
            java.lang.String r8 = r1.trim()     // Catch:{ Exception -> 0x0067 }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ Exception -> 0x0067 }
            r4 = r8
            if (r0 == 0) goto L_0x0088
        L_0x0061:
            r0.close()
            goto L_0x0088
        L_0x0065:
            r3 = move-exception
            goto L_0x009f
        L_0x0067:
            r3 = move-exception
            java.lang.String r5 = "yanyongyong"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0065 }
            r6.<init>()     // Catch:{ all -> 0x0065 }
            java.lang.String r7 = "IOException      "
            r6.append(r7)     // Catch:{ all -> 0x0065 }
            java.lang.String r7 = r3.getMessage()     // Catch:{ all -> 0x0065 }
            r6.append(r7)     // Catch:{ all -> 0x0065 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0065 }
            android.util.Log.e(r5, r6)     // Catch:{ all -> 0x0065 }
            r3.printStackTrace()     // Catch:{ all -> 0x0065 }
            if (r0 == 0) goto L_0x0088
            goto L_0x0061
        L_0x0088:
            java.lang.String r3 = "yanyongyong777"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "read partition , finally result is： "
            r5.append(r6)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            android.util.Log.e(r3, r5)
            return r4
        L_0x009f:
            if (r0 == 0) goto L_0x00a4
            r0.close()
        L_0x00a4:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiwire.CSAndroidGoLib.Utils.IOutils.getPrinterStatus():int");
    }
}
