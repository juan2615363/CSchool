package cn.songguohulian.cschool.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.base.BaseActivity;
import cn.songguohulian.cschool.bean.CShcoolUser;
import cn.songguohulian.cschool.utils.CSpTools;
import cn.songguohulian.cschool.utils.EssentialUtils;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.view.DiglogAnimation;

/**
 * @author Ziv
 * @data 2017/5/5
 * @time 22:34
 * <p>
 * 登录界面
 * 如果用户登录成功之后 保存登录状态 下次直接进入主界面
 * 在注销页面清除保存状态
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    // 用户名
    @Bind(R.id.edt_login_username)
    public EditText usernameEdt;

    // 密码
    @Bind(R.id.edt_login_password)
    public EditText passwordEdt;

    // 新用户注册
    @Bind(R.id.text_login_register)
    public TextView registerText;

    // 忘记密码
    @Bind(R.id.text_forget_pwd)
    public TextView forgetPwdText;

    // 登录
    @Bind(R.id.btn_login)
    public Button signInBtn;

    // 微信登录
    @Bind(R.id.img_wechat_login)
    public ImageView wechatImg;

    // 微博登录
    @Bind(R.id.img_weibo_login)
    public ImageView weiboImg;

    // QQ登录
    @Bind(R.id.img_qq_login)
    public ImageView qqImg;

    private String userName;
    private String password;
    private Dialog dialog;

    @Override
    protected void initView() {
        // 沉浸式
        steep();
        setContentView(R.layout.activity_login);
        // 绑定当前Activity
        ButterKnife.bind(this);

//        // 获取值
//        String userName = getIntent().getStringExtra("userName");
//        String password = getIntent().getStringExtra("password");
//
//        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
//            LogUtil.e("没有值");
//            // 什么都不执行
//        } else {
//            usernameEdt.setText(userName);
//            passwordEdt.setText(password);
//
//        }
    }

    private void steep() {
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

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        // 设置第三方登录的点击事件
        wechatImg.setOnClickListener(this); // 微信登录
        weiboImg.setOnClickListener(this); // 微博登录
        qqImg.setOnClickListener(this); // QQ登录
        signInBtn.setOnClickListener(this); // 登录
        registerText.setOnClickListener(this); // 注册
        forgetPwdText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (EssentialUtils.isNetworkAvailable(this) && checkInput()) {
                    // 执行登录操作
                    dialog = DiglogAnimation.createLoadingDialog(this, "正在登录中...");
                    dialog.show();
                    BmobUser.loginByAccount(this, userName, password, new LogInListener<CShcoolUser>() {

                        @Override
                        public void done(CShcoolUser cShcoolUser, BmobException e) {
                            if (cShcoolUser != null) {
//                                Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                // 1. 保存用户登录成功的状态
                                CSpTools.putBoolean(mContext, MyContants.ISLOGINSUCCESS, true);

                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (!EssentialUtils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "未连接网络！！", Toast.LENGTH_SHORT).show();
                }
                break;
            // 跳转到注册界面
            case R.id.text_login_register:
                Intent intent_register = new Intent(mContext, RegisterActivity.class);
                startActivity(intent_register);
                finish();
                break;
            // 跳转到忘记密码界面
            case R.id.text_forget_pwd:
                Intent intent_forgetpass = new Intent(mContext, ForgetPassWord.class);
                startActivity(intent_forgetpass);
                finish();
                break;
            case R.id.img_wechat_login:
                Toast.makeText(getApplicationContext(), "微信登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_weibo_login:
                Toast.makeText(getApplicationContext(), "微博登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_qq_login:
                Toast.makeText(getApplicationContext(), "QQ登录", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /**
     * 检查输入
     *
     * @return
     */
    private boolean checkInput() {
        userName = usernameEdt.getText().toString();
        password = passwordEdt.getText().toString();

        if (userName.length() > 0 && password.length() > 0) {
            return true;
        } else if (userName.length() <= 0) {
            Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();

        } else if (password.length() <= 0) {
            Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
