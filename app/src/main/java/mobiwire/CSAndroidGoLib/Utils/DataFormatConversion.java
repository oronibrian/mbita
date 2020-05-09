package mobiwire.CSAndroidGoLib.Utils;

import java.io.IOException;

public class DataFormatConversion {
    public static byte[] writeWord(int value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> 8) & 255)};
    }

    public static byte[] writeDword(long value) throws IOException {
        return new byte[]{(byte) ((int) (value & 255)), (byte) ((int) ((value >> 8) & 255)), (byte) ((int) ((value >> 16) & 255)), (byte) ((int) (255 & (value >> 24)))};
    }

    public static byte[] writeLong(long value) throws IOException {
        return new byte[]{(byte) ((int) (value & 255)), (byte) ((int) ((value >> 8) & 255)), (byte) ((int) ((value >> 16) & 255)), (byte) ((int) (255 & (value >> 24)))};
    }
}
