package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
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

public class EditAdminProfile extends AppCompatActivity {

    EditText username,usermobile,useremail,userfirmname,user_address;
    TextView txt_leftarrow;
    TextInputLayout Name_text_input_layout,Mobile_number_input_layout,Email_id_input_layout,Firm_name_input_layout,Address_input_layout;

    UserApi service;
    String id,user_id, name,mobile,email,firm_name,address;


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
        setContentView(R.layout.activity_edit_admin_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if(intent != null) {
            name = intent.getStringExtra("name1");
            mobile = intent.getStringExtra("mobile");
            email = intent.getStringExtra("email1");
            firm_name = intent.getStringExtra("firmname");
            address = intent.getStringExtra("user_address1");
        }



        findview();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    erorr();
                    if (validation()) {
                        edit_user();
                    }

                }
            });
        }



        txt_leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.txt_leftarrow1:
                        finish();
                        break;

                }
            }
        });

        setname();


    }


    private void findview() {

        Name_text_input_layout = (TextInputLayout) findViewById(R.id.customer_name_input_layout);
        Mobile_number_input_layout = (TextInputLayout) findViewById(R.id.mobile_number_input_layout);
        Email_id_input_layout = (TextInputLayout) findViewById(R.id.email_id_input_layout);
       Firm_name_input_layout = (TextInputLayout) findViewById(R.id.firm_input_layout);
//        Area_input_layout = (TextInputLayout) findViewById(R.id.area_input_layout);
        Address_input_layout = (TextInputLayout) findViewById(R.id.address_input_layout);

        username = (EditText) findViewById(R.id.usrename);
        usermobile = (EditText) findViewById(R.id.mob);
        useremail = (EditText) findViewById(R.id.email_id);
        userfirmname = (EditText) findViewById(R.id.firm);
        user_address = (EditText) findViewById(R.id.address1);


        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(EditAdminProfile.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }

    private void setname() {

        username.setText(name);
        usermobile.setText(mobile);
        useremail.setText(email);
        userfirmname.setText(firm_name);
        user_address.setText(address);
    }

    private void erorr() {

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Name_text_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        usermobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Mobile_number_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        useremail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Email_id_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userfirmname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Firm_name_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        user_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Address_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    boolean validation() {
        String username1 = username.getText().toString();
        String mobile6 = usermobile.getText().toString();
        String email = useremail.getText().toString();
        String firmname = userfirmname.getText().toString();
        String address = user_address.getText().toString();


        if (username1.equals("")) {

            Name_text_input_layout.setError("Please enter a name");
            requestFocus(username);
            return false;
        }
        else if(mobile6.equals("")) {
            //etpassword.requestFocus();

            Mobile_number_input_layout.setError("Please enter mobile number");
            requestFocus(usermobile);
            return false;
        }

        else if(!(mobile6.length()==10)) {
            //etpassword.requestFocus();

            Mobile_number_input_layout.setError("Please enter vaild mobile number");
            requestFocus(usermobile);
            return false;
        }

        else if(email.equals("")) {
            //etpassword.requestFocus();

            Email_id_input_layout.setError("Please enter mobile number");
            requestFocus(useremail);
            return false;
        }

        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //etpassword.requestFocus();

            Email_id_input_layout.setError("Please enter vaild email id");
            requestFocus(useremail);
            return false;

        }

        else if(firmname.equals("")) {
            //etpassword.requestFocus();

            Firm_name_input_layout.setError("Please enter firm name");
            requestFocus(userfirmname);
            return false;
        }

        else if (address.equals("")) {
            //etpassword.requestFocus();

            Address_input_layout.setError("Please enter a address");
            requestFocus(user_address);
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


    private void edit_user() {

        FunctionConstant.loading(EditAdminProfile.this);
        api();

//        Intent intent = getIntent();
//        if(intent != null){
//            id = intent.getStringExtra("cust_id");
//        }

        name = username.getText().toString();
        mobile = usermobile.getText().toString();
        email = useremail.getText().toString();
        firm_name = userfirmname.getText().toString();
        address = user_address.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("AC",0);
        id = sharedPreferences.getString("id","");

        String method = "edituser";
        Call<JsonObject> call = service.edituser(id,name,mobile,email,firm_name,address,method);

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

                            finish();
                            Toast.makeText(EditAdminProfile.this,status, Toast.LENGTH_SHORT).show();

                        } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(EditAdminProfile.this, status, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(EditAdminProfile.this, " Data not add", Toast.LENGTH_SHORT).show();

                    }

                    FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(EditAdminProfile.this, " Server issue", Toast.LENGTH_SHORT).show();

                }
                FunctionConstant.progressDialog.dismiss();




            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }


}
