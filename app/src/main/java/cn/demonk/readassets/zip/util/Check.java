package cn.demonk.readassets.zip.util;

/**
 * Created by ligs on 11/16/16.
 */
public class Check {

    private static boolean switcher = true;


    public static final void open() {
        switcher = true;
    }

    public static final void close() {
        switcher = false;
    }

    public static final void d(boolean valid, String msg) {
        if (switcher) {
            assert valid : msg;
        }
    }

    public static final void d(boolean valid) {
        d(valid, "");
    }
}
