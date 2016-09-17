package cn.demonk.readassets.zip.util;

import android.text.TextUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Iterator;

/**
 * Created by ligs on 11/15/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class PathTest {

    private String src;

    private Path testPath = null;

    private String testStr[] = {
            "zip://ab/cd/ef/",
            "zip://ab/cd/ef",
            "/ab/cd/ef/",
            "/ab/cd/ef",
            "file://ab/cd/ef",
            "file//ab/cd/ef",
            "file:/ab/cd/ef",
            "cd://ab/cd/ef",
            "zip/ab/cd/ef"
    };

    private String fullPath[] = {
            "/ab/cd/ef",
            "/ab/cd/ef",
            "/ab/cd/ef",
            "/ab/cd/ef",
            "/ab/cd/ef",
            "/file/ab/cd/ef",
            "/file:/ab/cd/ef",
            "/ab/cd/ef",
            "/zip/ab/cd/ef"
    };

    private String parent[] = {
            "zip:///ab/cd",
            "zip:///ab/cd",
            "/ab/cd",
            "/ab/cd",
            "file:///ab/cd",
            "/file/ab/cd",
            "/file:/ab/cd",
            "cd:///ab/cd",
            "/zip/ab/cd"
    };

    private String schemes[] = {
            "zip://",
            "zip://",
            "",
            "",
            "file://",
            "",
            "",
            "cd://",
            ""
    };

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

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testContruct() throws Exception {
        for (int i = 0; i < testStr.length; i++) {
            src = testStr[i];
            testPath = new Path(src);

//            System.out.println(testPath);
            assert testPath != null;
        }
    }

    @Test
    public void testScheme() throws Exception {
        for (int i = 0; i < testStr.length; i++) {
            src = testStr[i];
            testPath = new Path(src);

            String scheme = testPath.getScheme();

//            System.out.println(i + ":" + scheme);
            assert scheme.equals(schemes[i]);
        }
    }

    @Test
    public void testParent() throws Exception {
        for (int i = 0; i < testStr.length; i++) {
            src = testStr[i];
            testPath = new Path(src);

            Path parentPath = testPath.getParent();

//            System.out.println(i + ":" + parentPath);

            assert parentPath.toString().equals(parent[i]);
        }
    }

    @Test
    public void testJoin() throws Exception {

        Path path = new Path();
        path.join("/ab/cd").join("ef///gf").join("jk");

//        System.out.println(path);
        assert path.toString().equals("/ab/cd/ef/gf/jk");

        path = new Path("///ab/cd/").join("/ef/gh").join("///jk");
        assert path.toString().equals("/ab/cd/ef/gh/jk");

        path = new Path("zip://");
        path.join("ab/cd/").join("ef/gh");

        assert path.toString().equals("zip:///ab/cd/ef/gh");

    }

    @Test
    public void testIterator() throws Exception {
//        Path path = new Path("zip://ab/cd/ef/gh");

        Path path = new Path("/ab/cd/ef/gh/");

        String[] pairs = {"ab", "cd", "ef", "gh"};
        Iterator<String> iterator = path.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String next = iterator.next();
            assert pairs[i].equals(next) : String.format("%d has problems: %s != %s", i, next, pairs[i]);
            i++;
        }
    }

    @Test
    public void testFullPath() throws Exception {
        for (int i = 0; i < testStr.length; i++) {
            src = testStr[i];
            testPath = new Path(src);

            String fp = testPath.getFullPath();

            System.out.println(i + ":" + fp);

            assert fp.toString().equals(fullPath[i]);
        }
    }
}