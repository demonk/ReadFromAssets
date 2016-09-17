package cn.demonk.readassets.zip.util;

import android.text.TextUtils;

/**
 * Created by ligs on 11/15/16.
 */
public class StringUtils {

    /**
     * dst与src部分匹配，匹配范围从src的start位置开始
     *
     * @param src
     *          需要对比的源字段串
     * @param start
     *          对比开始的位置（基于src,从0开始）
     * @param dst
     *          需要对比的位置
     * @return 是否存在匹配
     */
    public static boolean match(String src, int start, String dst) {

        if (!(TextUtils.isEmpty(src) || TextUtils.isEmpty(dst))) {
            int end = start + dst.length();
            if (src.length() >= start && src.length() >= end) {
                char[] dstArray = dst.toCharArray();
                int i, j;
                for (i = start, j = 0; i < end && src.charAt(i) == dstArray[j]; i++, j++) ;
                if (i == end)
                    return true;
            }
        }
        return false;
    }
}
