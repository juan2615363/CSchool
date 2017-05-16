package cn.songguohulian.cschool.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.litesuits.orm.db.annotation.Check;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.step.config.Constant;
import cn.songguohulian.cschool.step.service.StepService;
import cn.songguohulian.cschool.step.utils.SharedPreferencesUtils;
import cn.songguohulian.cschool.step.utils.StepCountModeDispatcher;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.view.StepArcView;


public class RunActivity extends Activity implements Handler.Callback, View.OnClickListener {
    private TextView tv_data;
    private StepArcView cc;
    private TextView tv_set;
    private TextView tv_isSupport;
    private Handler delayHandler;
    private SharedPreferencesUtils sp;

    @Bind(R.id.run_banner)
    public Banner banner;


    private void assignViews() {
        tv_data = (TextView) findViewById(R.id.tv_data);
        cc = (StepArcView) findViewById(R.id.cc);
        tv_set = (TextView) findViewById(R.id.tv_set);
        tv_isSupport = (TextView) findViewById(R.id.tv_isSupport);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 沉浸式状态栏
         *
         * */
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_run);

        ButterKnife.bind(this);
        initBanner();
        assignViews();
        initData();
        addListener();
    }

    private void initBanner() {

        //如果你想用自己项目的图片加载,那么----->自定义图片加载框架
        List<String> imageUris = new ArrayList<>();

        // 添加图片
        imageUris.add(MyContants.BannerOne);
        imageUris.add(MyContants.BannerTwo);

        banner.setDelayTime(5000);
        banner.setImages(imageUris, new OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                Glide.with(getApplicationContext())
                        .load(url)
                        .into(view);
            }
        });

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getApplicationContext(), (position - 1) + "  ", Toast.LENGTH_SHORT).show();
                switch ((position - 1)) {
                    case 0:


                }
            }

        });
    }

    private void initData() {
        sp = new SharedPreferencesUtils(this);
        String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
        cc.setCurrentCount(Integer.parseInt(planWalk_QTY), 0);
        if (StepCountModeDispatcher.isSupportStepCountSensor(this)) {
            tv_isSupport.setText("记步服务运行中....");
            delayHandler = new Handler(this);
            setupService();
        } else {
            tv_isSupport.setText("该设备不支持计步,请检查您的手机是否具备传感器!");
        }
    }


    private void addListener() {
        tv_set.setOnClickListener(this);
        tv_data.setOnClickListener(this);
    }


    private boolean isBind = false;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Messenger messenger;

    /**
     * 从service服务中拿到步数
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.MSG_FROM_SERVER:
                String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
                cc.setCurrentCount(Integer.parseInt(planWalk_QTY), msg.getData().getInt("step"));
                break;
        }
        return false;
    }

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);


    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set:
                startActivity(new Intent(this, SetPlanActivity.class));
                break;
            case R.id.tv_data:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
        }
    }
}


