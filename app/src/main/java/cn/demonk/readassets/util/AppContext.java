package cn.demonk.readassets.util;

import android.content.Context;
import android.content.res.AssetManager;

import cn.demonk.readassets.zip.util.Check;

/**
 * Created by ligs on 9/16/16.
 */
public class AppContext {

    private static Context mContext = null;

    public static final void initContext(Context ctx) {
        mContext = ctx;
    }

    public static final AssetManager getAssetManager() {
        Check.d(mContext != null, "ctx cannot null");
        if (mContext != null) {
            return mContext.getAssets();
        }

        return null;
    }
}
