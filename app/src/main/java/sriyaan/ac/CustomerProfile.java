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

public class CustomerProfile extends AppCompatActivity {

       TextView txt_leftarrow, customer_name,customer_mobile,customer_email,customer_city,customer_area,customer_address;

    UserApi service;
    String id,user_id, cust_id,cust_name,cust_mobile_no,cust_city,cust_email_id,cust_area,cust_address;

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
        setContentView(R.layout.activity_customer_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        findview();
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




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                startActivity(new Intent(CustomerProfile.this, CustomerView.class));
                Intent intent = new Intent(CustomerProfile.this,CustomerView.class);
                            intent.putExtra("cust_id", cust_id);
                intent.putExtra("cust_name", cust_name);
                intent.putExtra("cust_mobile_no", cust_mobile_no);
                intent.putExtra("cust_city", cust_city);
                intent.putExtra("cust_email_id", cust_email_id);
                intent.putExtra("cust_area", cust_area);
                intent.putExtra("cust_address", cust_address);

                            startActivity(intent);

            }
        });


    }

    private void findview() {
        customer_name = (TextView) findViewById(R.id.customer_name);
        customer_mobile = (TextView) findViewById(R.id.mobile_number);
        customer_email = (TextView) findViewById(R.id.email_id);
        customer_city = (TextView) findViewById(R.id.city);
        customer_area = (TextView) findViewById(R.id.area);
        customer_address = (TextView) findViewById(R.id.address);

        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(CustomerProfile.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }


    private void get_customerinfo() {

        FunctionConstant.loading(CustomerProfile.this);

        api();

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("cust_id");
        }


        String method = "getcustomerbyid";
        Call<JsonObject> call = service.getcustomerbyid(id,method);

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

                            JSONObject object = jObject.getJSONObject("userdata");
                             cust_id = object.getString("id");
                             cust_name = object.getString("cust_name");
                             cust_mobile_no = object.getString("cust_mobile_no");
                             cust_city = object.getString("cust_city");
                            cust_email_id = object.getString("cust_email_id");
                            cust_area = object.getString("cust_area");
                            cust_address = object.getString("cust_address");
//                            String cust_mobile_no = customer_mobile.getText().toString();
//                            String cust_email_id = customer_email.getText().toString();
//                            String cust_city = customer_city.getText().toString();
//                            String cust_area = customer_area.getText().toString();
//                            String cust_address = customer_address.getText().toString();

                            customer_name.setText(cust_name);
                            customer_mobile.setText(cust_mobile_no);
                            customer_email.setText(cust_email_id);
                            customer_city.setText(cust_city);
                            customer_area.setText(cust_area);
                            customer_address.setText(cust_address);

//                            Toast.makeText(CustomerProfile.this,status, Toast.LENGTH_LONG).show();


//                        startActivity(new Intent(Login.this, Start.class));
//
//




                        } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(CustomerProfile.this, status, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CustomerProfile.this, " Data not add", Toast.LENGTH_SHORT).show();

                    }


                    FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(CustomerProfile.this, " Server issue", Toast.LENGTH_SHORT).show();

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
        get_customerinfo();
    }
}
