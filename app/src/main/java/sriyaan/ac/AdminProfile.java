package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class AdminProfile extends AppCompatActivity {

    TextView txt_leftarrow, user_name,mobile_number,user_email,user_firm,address;

    UserApi service;
    String id,user_id, name,mobile,email,firmname,user_address;

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
        setContentView(R.layout.activity_admin_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findview();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AdminProfile.this, EditAdminProfile.class));

                Intent intent = new Intent(AdminProfile.this,EditAdminProfile.class);
                intent.putExtra("name1", name);
                intent.putExtra("mobile", mobile);
                intent.putExtra("email1", email);
                intent.putExtra("firmname", firmname);
                intent.putExtra("user_address1", user_address);
                startActivity(intent);

            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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


    }

    private void findview() {

        user_name = (TextView) findViewById(R.id.name);
        mobile_number = (TextView) findViewById(R.id.number);
        user_email = (TextView) findViewById(R.id.email);
        user_firm = (TextView) findViewById(R.id.firmname);
        address = (TextView) findViewById(R.id.address1);


        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(AdminProfile.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }


    void get_userdata()
    {

        FunctionConstant.loading(AdminProfile.this);
        api();



        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        id=sharedPreferences.getString("id", "");

        String method="getuserdata";


        Call<JsonObject> call = service.getuserdata(id, method);

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                if(response.isSuccess())
                {

                    try {
                        JSONObject jObject = new JSONObject(response.body().toString());
                        String code = jObject.getString("error_code");
                        String  status = jObject.getString("error_msg");
                        if(code.equals("0"))
                        {
                            final JSONArray jsonArray = jObject.getJSONArray("userdata");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                name = jsonObject.getString("name");
                                mobile = jsonObject.getString("mobile");
                                email = jsonObject.getString("email");
                                firmname = jsonObject.getString("firm_name");
                               user_address = jsonObject.getString("address");

                            }
                            user_name.setText(name);
                            mobile_number.setText(mobile);
                            user_email.setText(email);
                            user_firm.setText(firmname);
                            address.setText(user_address);


                        }
                        else {

                            Toast.makeText(AdminProfile.this, "Failed", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AdminProfile.this, "Total Failed", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(AdminProfile.this, "Server issue", Toast.LENGTH_SHORT).show();

                }

                FunctionConstant.progressDialog.dismiss();

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        get_userdata();
    }
}
