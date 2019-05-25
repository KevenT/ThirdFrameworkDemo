package com.zk.tinkerdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.beta.Beta;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvCurrentVersion;
    private Button btnShowToast;
    private Button btnLoadPatch;
    private Button btnKillSelf;
    private Button btnLoadLibrary;
    private Button btnDownloadPatch;
    private Button btnUserPatch;
    private Button btnCheckUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvChannel = findViewById(R.id.tv_channel);
        tvChannel.setText(WalleChannelReader.getChannel(this, "default"));


        TextView tvTag = findViewById(R.id.tv_tag);
        int a= 2;
        tvTag.setText(SampleApplicationLike.channel+ "  fixed "+a);



//        StatService.start(this);
        tvCurrentVersion = (TextView) findViewById(R.id.tvCurrentVersion);
        btnShowToast = (Button) findViewById(R.id.btnShowToast);
        btnShowToast.setOnClickListener(this);
        btnKillSelf = (Button) findViewById(R.id.btnKillSelf);
        btnKillSelf.setOnClickListener(this);
        btnLoadPatch = (Button) findViewById(R.id.btnLoadPatch);
        btnLoadPatch.setOnClickListener(this);
        btnLoadLibrary = (Button) findViewById(R.id.btnLoadLibrary);
        btnLoadLibrary.setOnClickListener(this);
        btnDownloadPatch = (Button) findViewById(R.id.btnDownloadPatch);
        btnDownloadPatch.setOnClickListener(this);
        btnUserPatch = (Button) findViewById(R.id.btnPatchDownloaded);
        btnUserPatch.setOnClickListener(this);
        btnCheckUpgrade = (Button) findViewById(R.id.btnCheckUpgrade);
        btnCheckUpgrade.setOnClickListener(this);

        tvCurrentVersion.setText("当前版本：" + getCurrentVersion(this));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowToast:  // 测试热更新功能
//                testToast();
                break;
            case R.id.btnKillSelf: // 杀死进程
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.btnLoadPatch: // 本地加载补丁测试
                Beta.applyTinkerPatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
                break;
            case R.id.btnLoadLibrary: // 本地加载so库测试
//                TestJNI testJNI = new TestJNI();
//                testJNI.createANativeCrash();
                break;
            case R.id.btnDownloadPatch:
                Beta.downloadPatch();
                break;
            case R.id.btnPatchDownloaded:
                Beta.applyDownloadedPatch();
                break;
            case R.id.btnCheckUpgrade:
                Beta.checkUpgrade();
                break;
        }
    }


    /**
     * 获取当前版本.
     *
     * @param context 上下文对象
     * @return 返回当前版本
     */
    public String getCurrentVersion(Context context) {
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo(this.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            return versionName + "." + versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }
}
