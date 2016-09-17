package cn.demonk.readassets.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.demonk.readassets.zip.util.Check;

/**
 * Created by ligs on 9/16/16.
 */
public class Path {

    private StringBuilder mContainer = null;
    private static final int DEFAULT_SIZE = 16;

    public Path() {
        mContainer = new StringBuilder();
    }

    public Path(String path) {
        if (!TextUtils.isEmpty(path)) {
            mContainer = new StringBuilder(path.length() + DEFAULT_SIZE);
        }
    }

    public Path join(String path) {

        return this;
    }

    private static Pattern sPathPattern = null;
    private static boolean accept(String path) {
        Check.d(TextUtils.isEmpty(path), "path cannot be empty");

        if (sPathPattern == null) {
            String patternStr = "(([a-zA-Z]+://)|/)?((\\w+)/?)+($/)?";
            sPathPattern = Pattern.compile(patternStr);
        }

        Matcher matcher = sPathPattern.matcher(path);
        return matcher.matches();
    }
}
