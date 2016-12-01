package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class Login extends AppCompatActivity {

    Button Login;
    EditText etemail,etpassword;
    TextView forgot_pass;
    TextInputLayout mail_text_input_layout,password_text_input_layout;

    UserApi service;

    void api()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant_url.commonurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserApi.class);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        findview();




        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                erorr();
                if (validation())
                {

                    LoginProcess();
                    }

//                Intent intent = new Intent(Login.this,MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void erorr() {

        etemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mail_text_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_text_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void findview() {
        etemail = (EditText)findViewById(R.id.usrename);
        etpassword = (EditText)findViewById(R.id.password);
        Login = (Button)findViewById(R.id.login);
        mail_text_input_layout=(TextInputLayout)findViewById(R.id.mail_text_input_layout);
        password_text_input_layout=(TextInputLayout)findViewById(R.id.password_text_input_layout);

//        forgot_pass = (TextView)findViewById(R.id.t1);
    }


    boolean validation() {
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        if ((email.equals("")) &&(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {

            mail_text_input_layout.setError("Please enter valid email id");
            requestFocus(etemail);
            return false;
        } else if (password.equals("")) {
            //etpassword.requestFocus();

            password_text_input_layout.setError("Please enter valid password");
            requestFocus(etpassword);
            return false;
        } else {
            return true;
        }
    }
//    private static boolean isValidEmail(String email) {
//        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }

//    private boolean isValidMail(String email2)
//    {
//        boolean check;
//        Pattern p;
//        Matcher m;
//
//        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//        p = Pattern.compile(EMAIL_STRING);
//
//        m = p.matcher(email2);
//        check = m.matches();
//
//        if(!check)
//        {
//            username.setError("Not Valid Email");
//        }
//        return check;
//    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    private void LoginProcess() {

        FunctionConstant.loading(Login.this);


        api();

        final String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
//        if (email.equals("Email")||password.equals("Password")){
//
//            Toast.makeText(Login.this, "please Enter the User Name Or Password", Toast.LENGTH_SHORT).show();
//        }

//        int inst_id = 1;
        String method = "userlogin";
        Call<JsonObject> call = service.login(email,password,method);

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                if(response.isSuccess())
                {

                try {
                    // JsonArray jsonArray = new JsonArray(response.body().toString());
                    JSONObject jObject = new JSONObject(response.body().toString());
                    String code = jObject.getString("error_code");
                    String status = jObject.getString("error_msg");

                    if (code.equals("0")) {

                        JSONObject object = jObject.getJSONObject("userdetail");

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String email = object.getString("email");
                        String password = object.getString("password");

                        SharedPreferences sharedPreferences = getSharedPreferences("AC", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", id);
                        editor.putString("name", name);
//                        editor.putString("std_name", std_name);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.commit();

                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(Login.this, Start.class));

                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                        //                        Toast.makeText(Login.this, status, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Login Failed !!!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this, "Please enter valid email & password", Toast.LENGTH_LONG).show();
                }

                }else{
                    Toast.makeText(Login.this, "Server issue", Toast.LENGTH_SHORT).show();
                }

                FunctionConstant.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }
}
