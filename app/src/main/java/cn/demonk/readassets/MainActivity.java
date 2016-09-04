package cn.demonk.readassets;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_read)
    Button mBtnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mBtnRead.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read:
                readTask();
                break;
        }
    }

    private void readTask() {
        ThreadPool.instance().post(new Runnable() {
            @Override
            public void run() {
                AssetManager am = MainActivity.this.getAssets();
                try {
                    InputStream input = am.open("0");
                    byte[] buf = new byte[512];
                    BufferedInputStream in = new BufferedInputStream(input);

                    zipTask(in);
//                    int size = in.read(buf);
//
//                    StringBuilder sb = new StringBuilder(size);
//                    for (int i = 0; i < size; i++) {
//                        int code = buf[i];
//                        sb.append(String.format("%x", buf[i]));
//                        sb.append(" ");
//                    }
//
////                    String content = new String(buf, 0, size);
////
//                    Log.e("demonk", "c=" + sb.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void zipTask(InputStream in) throws IOException {
        ZipInputStream input = new ZipInputStream(in);


        ZipEntry entry = null;
        while ((entry = input.getNextEntry()) != null) {
            Log.e("demonk", "zipName=" + entry.getName());

            byte[] buf = new byte[1024];
            int size = input.read(buf);

            String content = new String(buf, 0, size);
            Log.e("demonk", "c=" + content.toString());
        }

//        StringBuilder sb = new StringBuilder(size);
//        for (int i = 0; i < size; i++) {
//            int code = buf[i];
//            sb.append(String.format("%x", buf[i]));
//            sb.append(" ");
//        }
//        Log.e("demonk", "z=" + sb.toString());

    }

}

