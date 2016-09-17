package cn.demonk.readassets.zip.util;

import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by ligs on 11/15/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class StringUtilsTest {

    @Test
    public void testMatch() throws Exception {

        PowerMockito.mockStatic(TextUtils.class);

        String src = "abcdefghijklmnopqrstuvwxyz[];',./1234567890-=";
        String dst = "def";
        int start = 3;

        assert StringUtils.match(src, start, dst);

        dst="deh";
        assert !StringUtils.match(src, start, dst);

        dst="0-=";
        start=42;
        assert StringUtils.match(src, start, dst);
    }
}