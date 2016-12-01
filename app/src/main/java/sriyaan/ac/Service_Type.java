package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class Service_Type extends AppCompatActivity implements View.OnClickListener {

     RadioButton CMC, AMC, Individual;
     Button btnDisplay;
    String Amc_result,Cmc_result,Individual_result,result1;
    Spinner spinner;
    TextView txt_leftarrow;

    UserApi service;
    String user_id;
    String customer_id;
    String machine_id,service_type,service_schedule;

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
        setContentView(R.layout.activity_service__type);

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


//
        btnDisplay.setOnClickListener(this);

        setSpinner(); ///it's call two time after aadding data for refresh

    }

    private void setSpinner() {

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.area, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    private void findview() {
        AMC = (RadioButton) findViewById(R.id.amc);
        CMC = (RadioButton) findViewById(R.id.cmc);
        Individual = (RadioButton) findViewById(R.id.individual);
        btnDisplay = (Button) findViewById(R.id.done);

        spinner = (Spinner) findViewById(R.id.spinner_service_type);

        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(Service_Type.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.amc :
                Toast.makeText(Service_Type.this,"you choose EMC !!",Toast.LENGTH_LONG).show();
                break;
            case R.id.cmc :
                Toast.makeText(Service_Type.this,"you choose CMC !!",Toast.LENGTH_LONG).show();
                break;
            case R.id.individual :
                Toast.makeText(Service_Type.this,"you choose Individual !!",Toast.LENGTH_LONG).show();
                break;
            case R.id.done :



                if(AMC.isChecked()) {
                    service_type = "AMC";
                }
                else if (CMC.isChecked()){
                    service_type = "CMC";
                }
               else if (Individual.isChecked()) {
                    service_type = "Individual";
                }
                else
                { Toast.makeText(Service_Type.this,"Please click vaild check box",Toast.LENGTH_SHORT).show();}

                service_schedule = spinner.getSelectedItem().toString();
                if (service_type != null) {


                    if(service_schedule.equals("Yearly Service")) {

                        Toast.makeText(Service_Type.this,"Please select the vaild yearly service",Toast.LENGTH_SHORT).show();

                    }else {
                      add_service();
//
//                        String next_page = "0";
//                        Intent intent = getIntent();
//                        next_page = intent.getStringExtra("next_page");
//                        if(next_page.equals("1"))
//                        { add_service();}else {add_service1();}
                    }
                }
//                add_service();
                break;
        }
    }

    private void add_service() {

        FunctionConstant.loading(Service_Type.this);

        api();


        Intent intent = getIntent();
        if (intent != null) {
            customer_id = intent.getStringExtra("cust_id");
            machine_id = intent.getStringExtra("machine_id");

        }

        SharedPreferences sharedPreferences = getSharedPreferences("AC", 0);
            user_id = sharedPreferences.getString("id", "");


            String method = "addservice";
            Call<JsonObject> call = service.addservice(service_type, service_schedule, method,
                    customer_id, user_id, machine_id);

//
            call.enqueue(new retrofit.Callback<JsonObject>() {
                @Override
                public void onResponse(Response<JsonObject> response, Retrofit retrofit) {


                    if (response.isSuccess()) {
                        try {
                            // JsonArray jsonArray = new JsonArray(response.body().toString());
                            JSONObject jObject = new JSONObject(response.body().toString());
                            String code = jObject.getString("error_code");
                            String status = jObject.getString("error_msg");

                            if (code.equals("0")) {


                                Toast.makeText(Service_Type.this, status, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Service_Type.this, ExpandList.class);
                                startActivity(intent);

                                finish();


                            } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                                Toast.makeText(Service_Type.this, "Failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Service_Type.this, " Data not add", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(Service_Type.this, " Server issue", Toast.LENGTH_SHORT).show();

                    }

                    FunctionConstant.progressDialog.dismiss();

                }

                @Override
                public void onFailure(Throwable t) {

                }

            });


        }

    private void add_service1() {

        FunctionConstant.loading(Service_Type.this);

        api();


        Intent intent = getIntent();
        if (intent != null) {
            customer_id = intent.getStringExtra("cust_id");
            machine_id = intent.getStringExtra("machine_id");

        }

        SharedPreferences sharedPreferences = getSharedPreferences("AC", 0);
        user_id = sharedPreferences.getString("id", "");


        String method = "addservice";
        Call<JsonObject> call = service.addservice(service_type, service_schedule, method,
                customer_id, user_id, machine_id);

//
        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {


                if (response.isSuccess()) {
                    try {
                        // JsonArray jsonArray = new JsonArray(response.body().toString());
                        JSONObject jObject = new JSONObject(response.body().toString());
                        String code = jObject.getString("error_code");
                        String status = jObject.getString("error_msg");

                        if (code.equals("0")) {


                            Toast.makeText(Service_Type.this, status, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Service_Type.this, ExpandList.class);
                            startActivity(intent);

                            finish();


                        } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(Service_Type.this, "Failed", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Service_Type.this, " Data not add", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Service_Type.this, " Server issue", Toast.LENGTH_SHORT).show();

                }

                FunctionConstant.progressDialog.dismiss();

            }

            @Override
            public void onFailure(Throwable t) {

            }

        });


    }

}
