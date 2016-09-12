package cn.uc.gamesdk.utils.text;

/**
 * Created by ligs on 8/31/16.
 */
public class Path {

    private static final char SEPARATOR = '/';

    private StringBuilder mPathContainer;

    private int index = 0;//当前最后分隔符的位置

    public Path() {
        mPathContainer = new StringBuilder();
    }

    public Path(String path) {
        this();
        join(path);
    }

    public Path getParent() {
        ///避免多线程问题
        return new Path(toString().substring(0, index));
    }

    public Path join(String path) {
        path = trim(path);

        String[] parts = path.split(String.valueOf(SEPARATOR));
        for (String part : parts) {
            append(part);
        }
        return this;
    }

    public Path clone() {
        return new Path(toString());
    }

    private void append(String str) {
        if (mPathContainer.length() > 0 ||
                (mPathContainer.length() == 0 && StringUtil.isNotEmpty(str) && SEPARATOR == str.charAt(0))) {
            insertSeparator();
        }

        mPathContainer.append(str);
    }

    //过滤前后斜杠，中间多余斜杠
    private String trim(String path) {
        int start = 0;
        int end = path.length() - 1;

        char c = '\0';
        while (start <= end && (((c = path.charAt(start)) == SEPARATOR) || c == ' ')) {
            start++;
        }

        while (end >= start && (((c = path.charAt(end)) == SEPARATOR) || c == ' ')) {
            end--;
        }

        //不可使用缓存，trim有可能多线程，保证无副作用
        StringBuilder sb = new StringBuilder(end - start);
        int index = start;
        while (start <= end) {
            c = path.charAt(start);
            if (c != SEPARATOR) {
                if (index != start) {
                    sb.append(SEPARATOR);
                }
                sb.append(c);
                index = ++start;
            } else {
                start++;
            }
        }

        return sb.toString();
    }

    private void insertSeparator() {
        mPathContainer.append(SEPARATOR);
        index = mPathContainer.length();
    }

    public String toString() {
        return mPathContainer.toString();
    }

}
