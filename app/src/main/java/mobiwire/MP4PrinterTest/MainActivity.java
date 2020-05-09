package mobiwire.MP4PrinterTest;

import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.CsPrinter;

public class MainActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0323R.layout.activity_main);
        new AndroidGoCSApi(this);
    }

    public void print(View view) {
        CsPrinter printer = new CsPrinter();
        printer.addTextToPrint("موبيواير", null, 20, true, false, 0);
        printer.addBitmapFromRawToPrint(this, C0323R.raw.etranetuttr_logo);
        CsPrinter csPrinter = printer;
        csPrinter.addTextToPrint("LOTTO", null, 50, true, false, 1);
        csPrinter.addTextToPrint("TEST LOTTO", null, 30, true, false, 1);
        csPrinter.addTextToPrint("Terminal Id", "100000", 25, true, false, 1);
        csPrinter.addTextToPrint("Draw No", "528 09/08/00", 25, true, false, 1);
        csPrinter.addTextToPrint("Draw Time", "03:30 PM", 25, true, false, 1);
        csPrinter.addTextToPrint("Validity", "01/01/00", 25, true, false, 1);
        csPrinter.addTextToPrint("------------", null, 25, true, false, 1);
        csPrinter.addTextToPrint("P2 AT N50", null, 25, true, false, 0);
        csPrinter.addTextToPrint("01 02 03 04", null, 25, true, false, 0);
        csPrinter.addTextToPrint("التكامل", "PHP300", 25, true, false, 1);
        csPrinter.addTextToPrint("TID :100000TID :100000", null, 25, true, false, 0);
        csPrinter.addTextToPrint("WELCOME", null, 25, true, false, 0);
        csPrinter.addTextToPrint("TEST PROMO", null, 25, true, false, 0);
        printer.print(this);
    }
}
