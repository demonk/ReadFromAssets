package cn.demonk.readassets.zip.util;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Created by ligs on 11/16/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class ZipUtilTest {

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(Mockito.any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                return !(a != null && a.length() > 0);
            }
        });
    }

    @Test
    public void testReadLocalFile() throws Exception {
        Path path = new Path("/media/Data/Study/ReadFromAssets/resource/text.txt");

        InputStream input = ZipUtil.readLocalFile(path);

        if (input != null) {
            BufferedInputStream bf = new BufferedInputStream(input);

            byte[] buffer = new byte[64];
            int size;
            StringBuilder sb = new StringBuilder();
            while ((size = bf.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, size));
            }

            StreamUtil.close(input);
            System.out.println(sb);
        }

    }
}