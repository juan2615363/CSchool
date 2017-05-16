package cn.songguohulian.cschool.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.base.BaseActivity;
import cn.songguohulian.cschool.utils.EssentialUtils;
import cn.songguohulian.cschool.utils.MyContants;


/**
 * 注册Activity
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private EditText registerMobileEdt;
    private EditText verifyCodeEdt;
    private EditText setPwdEdt;
    private Button getVerifyCodeBtn;
    private Button nextStepBtn;
    private TextView textView;
    private CheckBox checkBox;


    private int i = 60;//倒计时

    private String 手机号;
    private String 密码;

    @Override
    protected void initView() {

        // 使用沉浸式透明标题栏
        沉浸式标题栏();


        setContentView(R.layout.activity_register);

        // 初始化组件
        initComponent();

        // 初始化Mob短信SDK
        cn.smssdk.SMSSDK.initSDK(mContext, MyContants.APPKEY, MyContants.APPAPPSECRET);

        //initSDK方法是短信SDK的入口，需要传递您从MOB应用管理后台中注册的SMSSDK的应用AppKey和AppSecrete，如果填写错误，后续的操作都将不能进行
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        cn.smssdk.SMSSDK.registerEventHandler(eventHandler);

    }

    private void 沉浸式标题栏() {
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
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                getVerifyCodeBtn.setText(i + "s后重发");
            } else if (msg.what == -2) {
                getVerifyCodeBtn.setText("重新发送");
                getVerifyCodeBtn.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("asd", "event=" + event + "  result=" + result + "  ---> result=-1 success , result=0 error");
                if (result == cn.smssdk.SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后转到用户个人信息界面
                    if (event == cn.smssdk.SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "短信验证成功",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(mContext, RegisterUserActivity.class);
                        intent.putExtra("mobileNumber", 手机号);
                        intent.putExtra("password", 密码);
                        startActivity(intent);
                        finish();

                    } else if (event == cn.smssdk.SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else if (result == cn.smssdk.SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Log.e("asd", "des: " + des);
                            Toast.makeText(mContext, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    };

    private void initComponent() {

        registerMobileEdt = (EditText) findViewById(R.id.edt_register_mobile);
        verifyCodeEdt = (EditText) findViewById(R.id.edt_register_verify_code);
        setPwdEdt = (EditText) findViewById(R.id.edt_register_set_pwd);
        getVerifyCodeBtn = (Button) findViewById(R.id.btn_get_rerify_code);
        nextStepBtn = (Button) findViewById(R.id.btn_register_next_step);
        textView = (TextView) findViewById(R.id.tv_duduProtocol);
        checkBox = (CheckBox) findViewById(R.id.register_cb_isagree);
        getVerifyCodeBtn.setOnClickListener(this);
        nextStepBtn.setOnClickListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        /**
         * 判断是否同意用户使用协议
         * */
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    nextStepBtn.setEnabled(true);
                    getVerifyCodeBtn.setEnabled(true);
                    getVerifyCodeBtn.setBackgroundResource(R.color.colorApp);
                    nextStepBtn.setBackgroundResource(R.color.colorApp);
                } else {
                    // 获取验证码和下一步按钮变为不可点击
                    getVerifyCodeBtn.setEnabled(false);
                    getVerifyCodeBtn.setBackgroundResource(R.color.base_color_text_gray);
                    nextStepBtn.setEnabled(false);
                    nextStepBtn.setBackgroundResource(R.color.base_color_text_gray);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

        手机号 = registerMobileEdt.getText().toString().trim();
        String 验证码 = verifyCodeEdt.getText().toString().trim();
        密码 = setPwdEdt.getText().toString().trim();

        switch (view.getId()) {

            case R.id.btn_get_rerify_code:
                getCodeMessage();
                break;
            // 验证验证码是否正确
            case R.id.btn_register_next_step:
                // 1,1 判断手机号是否为空 并且是否符合手机号规则
                if (TextUtils.isEmpty(手机号)) {
                    Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!(EssentialUtils.isMobileNumber(手机号))) {
                    Toast.makeText(mContext, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 1,2判断验证码是否为空
                else if (TextUtils.isEmpty(验证码)) {
                    Toast.makeText(mContext, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 1,3判断密码是否为空 并且是否符号密码的规则
                else if (!(EssentialUtils.isPasswordNumber(密码))) {
                    Toast.makeText(mContext, "密码不符号要求(6-20)", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // 1,4提交验证信息
                    SMSSDK.submitVerificationCode("86", 手机号, 验证码);
                }

                break;

            case R.id.tv_duduProtocol:
                Intent intent = new Intent(mContext, MyWebActivity.class);
                intent.putExtra("URL", MyContants.PROTOCOL);
                intent.putExtra("TITLE", "用户使用协议");
                startActivity(intent);
                break;

        }
    }

    private void getCodeMessage() {

        /**
         * 获取验证码之前先去判断该用户是否已经注册了
         *
         * */
        BmobQuery<BmobUser> query = new BmobQuery<>();
        query.addWhereEqualTo("mobilePhoneNumber", 手机号);
        query.findObjects(this, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                if (list.size() <= 0) {//手机号未注册
                    // 1,1判断手机号是否为空
                    if (TextUtils.isEmpty(手机号)) {
                        Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 判断手机是否符号规则
                    else if (!(EssentialUtils.isMobileNumber(手机号))) {
                        Toast.makeText(mContext, "手机格式不正确", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        SMSSDK.getVerificationCode("86", 手机号);
                        getVerifyCodeBtn.setClickable(false);
                        //开始倒计时
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (; i > 0; i--) {
                                    handler.sendEmptyMessage(-1);
                                    if (i <= 0) {
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                handler.sendEmptyMessage(-2);
                            }
                        }).start();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "该用户已经存在,换个手机号试试吧!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }
}
