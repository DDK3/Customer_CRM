package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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

public class CustomerMachineDetails extends AppCompatActivity implements View.OnClickListener {

    TextView txt_leftarrow,machine_name,machine_type,making_date,machine_details,service_type,yearly_service;
    String id, cust_id,machine_name1,machine_type1,making_date1,machine_details1,service_type1,service_schedule1;

    int i, page_id=0;
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
        setContentView(R.layout.activity_customer_machine_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("machine_id");
            cust_id = intent.getStringExtra("cust_id");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                startActivity(new Intent(CustomerMachineDetails.this, CustomerMachineDetailsEdit.class));
                Intent intent = new Intent(CustomerMachineDetails.this,CustomerMachineDetailsEdit.class);
                intent.putExtra("machine_name1", machine_name1);
                intent.putExtra("machine_type1", machine_type1);
                intent.putExtra("making_date1", making_date1);
                intent.putExtra("machine_details1", machine_details1);
                intent.putExtra("service_type1", service_type1);
                intent.putExtra("service_schedule1", service_schedule1);
                intent.putExtra("machine_id", id);
                intent.putExtra("cust_id", cust_id);

                startActivity(intent);

            }
        });

        findview();
        txt_leftarrow.setOnClickListener(this);

    }

    private void findview() {

        machine_name = (TextView) findViewById(R.id.machine_name);
        machine_type = (TextView) findViewById(R.id.machine_type);
        making_date = (TextView) findViewById(R.id.making_date);
        machine_details = (TextView) findViewById(R.id.machine_details);
        service_type = (TextView) findViewById(R.id.service_type);
        yearly_service = (TextView) findViewById(R.id.yearly_service);

        txt_leftarrow = (TextView) findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(CustomerMachineDetails.this.getAssets(), "fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.txt_leftarrow1:
                finish();
                break;


        }

    }


    private void get_custmashineservice() {

        FunctionConstant.loading(CustomerMachineDetails.this);

        api();

        String method ="getcustmachineservice";
        Call<JsonObject> call =service.getcustmashineservice(method,id,cust_id);

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
//                            cust_id = object.getString("id");
                              machine_name1 = object.getString("machine_name");
                              machine_type1 = object.getString("machine_type");
                              making_date1 = object.getString("making_date");
                               machine_details1 = object.getString("machine_details");
                             service_type1 = object.getString("service_type");
                              service_schedule1 = object.getString("service_schedule");
//                            String cust_mobile_no = customer_mobile.getText().toString();
//                            String cust_email_id = customer_email.getText().toString();
//                            String cust_city = customer_city.getText().toString();
//                            String cust_area = customer_area.getText().toString();
//                            String cust_address = customer_address.getText().toString();

                            machine_name.setText(machine_name1);
                            machine_type.setText(machine_type1);
                            making_date.setText(making_date1);
                            machine_details.setText(machine_details1);
                            service_type.setText(service_type1);
                            yearly_service.setText(service_schedule1);

//                            Toast.makeText(CustomerMachineDetails.this,status, Toast.LENGTH_LONG).show();

//                        startActivity(new Intent(Login.this, Start.class));
//
//




                        } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(CustomerMachineDetails.this, status, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CustomerMachineDetails.this, " Data not add", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(CustomerMachineDetails.this, " Server issue", Toast.LENGTH_SHORT).show();

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
        get_custmashineservice();
    }
}
