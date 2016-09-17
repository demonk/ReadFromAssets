package cn.demonk.readassets.zip.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.demonk.readassets.BuildConfig;
import cn.demonk.readassets.util.AppContext;

/**
 * Created by ligs on 9/16/16.
 */
@RunWith(MockTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class ZipUtilStreamTest {

    @Before
    public void setUp() throws Exception {
//        PowerMockito.mockStatic(ZipUtil.class);
//        PowerMockito.when(ZipUtil.readFromZip(Mockito.any(InputStream.class), Mockito.any(Path.class))).thenReturn()

        ReflectionHelpers.setStaticField(AppContext.class, "mContext", RuntimeEnvironment.application);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Config(manifest = "src/test/AndroidManifest.xml")
    public void testReadFromAssets() throws Exception {
        Path path = new Path("assets://1.zip");

//        ///test1,read zip
//        InputStream fileInputStream = ZipUtil.read(path);
//        readFromInputStream(fileInputStream, "1.zip");
//        StreamUtil.close(fileInputStream);
//        Assert.assertNotNull(fileInputStream);
//
//        ///test2
//        path = new Path("assets://subfolder/9.zip");
//        fileInputStream = ZipUtil.read(path);
//        Assert.assertNotNull(fileInputStream);
//        readFromInputStream(fileInputStream, "9");
//        StreamUtil.close(fileInputStream);

        ///test3
        path = new Path("assets://2.zip/subfolder/9.zip");
        InputStream fileInputStream = ZipUtil.read(path);
        Assert.assertNotNull(fileInputStream);
        readFromInputStream(fileInputStream, "2");
        StreamUtil.close(fileInputStream);
    }

    @Test
    @Config(manifest = "src/test/AndroidManifest.xml")
    public void testReadFromZip() throws Exception {
        Path path = new Path("assets://2.zip");
        InputStream fileInputStream = ZipUtil.read(path);

        Path zipPath = new Path("zip://subfolder/9.zip/content.txt");
        InputStream subInput = ZipUtil.readFromZip(fileInputStream, zipPath);
        Assert.assertNotNull(subInput);

        readFromInputStream(subInput, "3");
        StreamUtil.close(subInput);
        StreamUtil.close(fileInputStream);
        Assert.assertNotEquals(subInput, fileInputStream);
    }

    @Config(manifest = "src/test/AndroidManifest.xml")
    public void testReadWithPath() throws Exception {
        //read from assets
        Path path = new Path("assets://1.zip");
        InputStream fileInputStream = ZipUtil.readWithPath(path);
        readFromInputStream(fileInputStream, "1");
        StreamUtil.close(fileInputStream);
        Assert.assertNotNull(fileInputStream);

        //read from file
        path = new Path("/media/Data/Study/ReadFromAssets/app/src/test/assets/subfolder/9.zip");
        fileInputStream = ZipUtil.readWithPath(path);
        readFromInputStream(fileInputStream, "1");
        StreamUtil.close(fileInputStream);
        Assert.assertNotNull(fileInputStream);
    }

    private void readFromInputStream(InputStream in, String name) throws IOException {
//        BufferedInputStream input = new BufferedInputStream(in);
        InputStream input = in;
        int size = 0;
        byte[] buffer = new byte[1024];

        File dst = new File("/media/Data/Study/ReadFromAssets/app/src/test/tmp/" + name + ".zip");
        FileOutputStream output = new FileOutputStream(dst);
        while ((size = input.read(buffer)) != -1) {
            output.write(buffer, 0, size);
        }
        StreamUtil.close(output);
    }

    @Config(manifest = "src/test/AndroidManifest.xml")
    public void testMock() throws Exception {
        String packageName = RuntimeEnvironment.application.getPackageName();
        PackageManager packageManager = RuntimeEnvironment.application.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);

        System.out.println(packageInfo.activities);
        System.out.println(packageName);
        System.out.println(packageInfo.versionCode);
        System.out.println(packageInfo.versionName);

        //从assets目录下加载test.prop文件
        InputStream fileInputStream = RuntimeEnvironment.application.getAssets().open("test.prop");
        Properties props = new Properties();
        props.load(fileInputStream);
        String property = props.getProperty("abc");
        System.out.println(property);
    }
}