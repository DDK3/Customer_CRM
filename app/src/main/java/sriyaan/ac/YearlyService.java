package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class YearlyService extends AppCompatActivity {

    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
     RecyclerView mRecyclerView;
    YearlyServiceAdapter adapter;

    TextView txt_leftarrow,service_type;
    String machine_id,status_chec;
   public static String status_check="";
   public static String schedule_id="";
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
        setContentView(R.layout.activity_yearly_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateSheduleStatus();
                onRestart();
            }
        });


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
        Intent intent = getIntent();
        if(intent != null){
            machine_id = intent.getStringExtra("machine_id");
        }
        getall_collection();
    }



    private void findview() {

        service_type =(TextView)findViewById(R.id.type);

        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(YearlyService.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }


    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();

        Intent intent = getIntent();
        if(intent != null){
            machine_id = intent.getStringExtra("machine_id");
        }
        Intent i = new Intent(YearlyService.this, YearlyService.class);  //your class
        i.putExtra("machine_id",machine_id);
        startActivity(i);
        finish();

    }

//    private void checkSchduleStatus(String status_check) {
//        CheckBox checkbox =(CheckBox) findViewById(R.id.checkbox);
//        final String[] check = {status_check};
//        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView,
//                                             boolean isChecked) {
//                    if (isChecked) {
//                        // it is check
//                        check[0] = "1";
//                    } else {
//                        // it is unchecked
//                        check[0] ="0";
//                    }
//                }
//            });
//        }

    private void updateSheduleStatus()

    {


       // checkSchduleStatus( status_check);
//        FunctionConstant.loading(Event.this);

//        page_id =+1;
        api();
        String method="updateservice";

        String check=status_check;


        Call<JsonObject> call = service.updateservice(machine_id,method,schedule_id,status_check);

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

                            Toast.makeText(YearlyService.this, status, Toast.LENGTH_LONG).show();
                        }
                        else {

                            Toast.makeText(YearlyService.this, "YearlyService List is Empty", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(YearlyService.this, "YearlyService Empty", Toast.LENGTH_LONG).show();
                    }
//                FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(YearlyService.this, "Server issue", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }


    void getall_collection()
    {

        FunctionConstant.loading(YearlyService.this);

//        page_id =+1;
        api();



//        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
//        user_id=sharedPreferences.getString("id", "");
        String method="getallservice";


        Call<JsonObject> call = service.getallservice(machine_id,method);

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                if(response.isSuccess())
                {
                    try {
                        JSONObject jObject = new JSONObject(response.body().toString());
                        String code = jObject.getString("error_code");
                        String  status = jObject.getString("error_msg");
                        String  service_types = jObject.getString("service_type");
//                        total_amount = jObject.getString("total_amount");
//                        totalamount.setText(total_amount);
                        if(code.equals("0"))
                        {
//                            arrayList.clear();
                            final JSONArray jsonArray = jObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Settergetter setter_getter=new Settergetter();

                                setter_getter.setService_type(jsonObject.getString("service_type"));
                                setter_getter.setService_schedule(jsonObject.getString("service_schedule"));
                                setter_getter.setStatus(jsonObject.getString("status"));
                                setter_getter.setId(jsonObject.getString("id"));
                                setter_getter.setAdd_date(jsonObject.getString("date"));

                                arrayList.add(setter_getter);

                            }

                            adapter = new YearlyServiceAdapter(YearlyService.this, arrayList);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(adapter);

                            service_type.setText(service_types);

                        }

                        else {

                            Toast.makeText(YearlyService.this, status, Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(YearlyService.this, "YearlyService Empty", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(YearlyService.this, "Server issue", Toast.LENGTH_SHORT).show();

                }
                FunctionConstant.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }


}
