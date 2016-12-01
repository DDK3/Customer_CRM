package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class Signup extends AppCompatActivity {

    Button Submit;
    EditText etemail,etpassword,username,mobile1,firm_name1,address1;
    TextInputLayout mail_text_input_layout,password_text_input_layout,name_input_layout,mobile_text_input_layout,firm_name_text_input_layout,address1_text_input_layout;
    TextView Login;

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
        setContentView(R.layout.activity_signup);



        findview();





        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erorr();
                if (validation())
                {
//                    Testing();
                    adduser();
                }

//                Intent intent = new Intent(Login.this,MainActivity.class);
//                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Testing1();
            }
        });
    }

    private void erorr() {

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobile1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mobile_text_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        firm_name1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firm_name_text_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        address1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address1_text_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void findview() {
        username = (EditText)findViewById(R.id.usrename);
        mobile1 = (EditText)findViewById(R.id.mobiile);
        etemail = (EditText)findViewById(R.id.email1);
        etpassword = (EditText)findViewById(R.id.password);
        firm_name1 = (EditText)findViewById(R.id.firm_name);
        address1 = (EditText)findViewById(R.id.address1);
        Submit = (Button)findViewById(R.id.signup);


        name_input_layout=(TextInputLayout)findViewById(R.id.name_input_layout);
        mobile_text_input_layout=(TextInputLayout)findViewById(R.id.mobile_text_input_layout);
        mail_text_input_layout=(TextInputLayout)findViewById(R.id.email_text_input_layout);
        password_text_input_layout=(TextInputLayout)findViewById(R.id.password_text_input_layout);
        firm_name_text_input_layout=(TextInputLayout)findViewById(R.id.firm_name_text_input_layout);
        address1_text_input_layout=(TextInputLayout)findViewById(R.id.address1_text_input_layout);

        Login = (TextView)findViewById(R.id.t1);
    }



    boolean validation() {
        String username6 = username.getText().toString();
        String mobile6 = mobile1.getText().toString();
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        String firmname = firm_name1.getText().toString();
        String address = address1.getText().toString();

        if (username6.equals("")) {

            name_input_layout.setError("Please enter a name");
            requestFocus(username);
            return false;
        }

        else if(mobile6.equals("")) {
            //etpassword.requestFocus();

            mobile_text_input_layout.setError("Please enter mobile number");
            requestFocus(mobile1);
            return false;
        }

        else if(!(mobile6.length()==10)) {
            //etpassword.requestFocus();

            mobile_text_input_layout.setError("Please enter vaild mobile number");
            requestFocus(mobile1);
            return false;
        }

        else if (!email.equals("") &&(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {

            mail_text_input_layout.setError("Please enter valid email id");
            requestFocus(etemail);
            return false;
        } else if (password.equals("")) {
            //etpassword.requestFocus();

            password_text_input_layout.setError("Please enter valid password");
            requestFocus(etpassword);
            return false;
        } else if (firmname.equals("")) {
            //etpassword.requestFocus();

            firm_name_text_input_layout.setError("Please enter a firm name");
            requestFocus(firm_name1);
            return false;
        } else if (address.equals("")) {
            //etpassword.requestFocus();

            address1_text_input_layout.setError("Please enter a address");
            requestFocus(address1);
            return false;
        }else {
            return true;
        }
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void Testing(){
        Intent intent = new Intent(Signup.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void Testing1(){
        Intent intent = new Intent(Signup.this,Login.class);
        startActivity(intent);
        finish();
    }

    private void adduser() {

        FunctionConstant.loading(Signup.this);


        api();

      String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        String name = username.getText().toString();
        String mobile = mobile1.getText().toString();
        String firm_name = firm_name1.getText().toString();
        String address = address1.getText().toString();


//        if (email.equals("Email")||password.equals("Password")){
//
//            Toast.makeText(Login.this, "please Enter the User Name Or Password", Toast.LENGTH_SHORT).show();
//        }

//        int inst_id = 1;
        String method = "adduser";
        Call<JsonObject> call = service.adduser(name,mobile,email,password,firm_name,address,method);

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                try {
                    // JsonArray jsonArray = new JsonArray(response.body().toString());
                    JSONObject jObject = new JSONObject(response.body().toString());
                    String code = jObject.getString("error_code");
                    String status = jObject.getString("error_msg");

                    if (code.equals("0")) {

                        JSONObject object = jObject.getJSONObject("userdata");

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String email = object.getString("email");
//                        String password = object.getString("password");

                        SharedPreferences sharedPreferences = getSharedPreferences("AC", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", id);
                        editor.putString("name", name);
//                        editor.putString("std_name", std_name);
                        editor.putString("email", email);
//                        editor.putString("password", password);
                        editor.commit();

                        Toast.makeText(Signup.this,status , Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(Login.this, Start.class));

                        Intent intent = new Intent(Signup.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

//                        Toast.makeText(Signup.this, "Login Failed", Toast.LENGTH_LONG).show();
                                                Toast.makeText(Signup.this, status, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Signup.this, "Login Failed !!!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup.this, "Please enter valid email & password", Toast.LENGTH_LONG).show();
                }

                FunctionConstant.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }

}
