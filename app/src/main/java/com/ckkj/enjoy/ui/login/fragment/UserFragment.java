package com.ckkj.enjoy.ui.login.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ckkj.enjoy.R;
import com.ckkj.enjoy.app.AppApplication;
import com.ckkj.enjoy.base.BaseFragment;
import com.ckkj.enjoy.ui.MainActivity;
import com.ckkj.enjoy.ui.login.SignUpActivity;
import com.ckkj.enjoy.ui.login.presenter.LoginPresenter;
import com.ckkj.enjoy.ui.login.view.Login;
import com.ckkj.enjoy.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/13.
 */

public class UserFragment extends BaseFragment implements Login {


    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.tv_signup)
    TextView tvSignup;
    Unbinder unbinder;
    private ProgressDialog mProgressDialog;
    private LoginPresenter presenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login_usernameandpassword;
    }

    @Override
    protected void initView() {
        presenter = new LoginPresenter(this);
    }


    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("正在验证...");
        mProgressDialog.show();

        String userword = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        presenter.login(userword, password);

      /*  if(userword.equals("17875057401")&&password.equals("123456")){
            SPUtils.setSharedBooleanData(getActivity(),"islogin",true);
            startActivity(new Intent(AppApplication.getAppContext(), MainActivity.class));
            getActivity().finish();
        }
*/

    }

    /**
     * 邮箱，密码是否格式正确
     *
     * @return
     */
    public boolean validate() {
        boolean valid = true;

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (email.isEmpty() || !Patterns.PHONE.matcher(email).matches()) {
            inputEmail.setError("请输入有效的手机号码");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("密码长度在4-10位之间");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }


    /**
     * 格式错误导致登录失败逻辑
     */
    public void onLoginFailed() {
        Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录成功
     */
    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
    }

    @Override
    public void getMessage(String msg) {
        if (msg.equals("登录成功")) {
            SPUtils.setSharedBooleanData(getActivity(), "islogin", true);
            startActivity(new Intent(AppApplication.getAppContext(), MainActivity.class));
            getActivity().finish();
        } else {
            mProgressDialog.dismiss();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            btnLogin.setEnabled(true);
        }
    }




    @OnClick({R.id.btn_login, R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_signup:
                startActivity(SignUpActivity.class);
                break;
        }
    }


}
