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

public class ServiceMachineList extends AppCompatActivity {


    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
    private RecyclerView mRecyclerView;
    private ServiceMachineListAdapter adapter;

    TextView txt_leftarrow;
    String user_id, cust_id;
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
        setContentView(R.layout.activity_service_machine_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(intent != null){
            cust_id = intent.getStringExtra("cust_id");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(ServiceMachineList.this,SearchServiceMachineList.class);
                intent.putExtra("cust_id",cust_id);
                startActivity(intent);
            }
        });
      /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        getall_machine();
//        banner();
//        prepareMovieData();
    }

    private void findview() {
        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(ServiceMachineList.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }


    void getall_machine()
{

        FunctionConstant.loading(ServiceMachineList.this);
    api();


    SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
    user_id=sharedPreferences.getString("id", "");
    String method="getallmachine";
    page_id =+1;

    Call<JsonObject> call = service.getallmachine(user_id,method,cust_id);

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
                        final JSONArray jsonArray = jObject.getJSONArray("data");
                        for (i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Settergetter setter_getter=new Settergetter();
                            setter_getter.setMachine_name(jsonObject.getString("machine_name"));
                            setter_getter.setMachine_id(jsonObject.getString("id"));
                            arrayList.add(setter_getter);

                        }
//                        adapter = new ServiceScheduleAdapter(ServiceSchedule.this, arrayList);
//                        mRecyclerView.setAdapter(adapter);
                        adapter = new ServiceMachineListAdapter(ServiceMachineList.this, arrayList);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(adapter);


                    }

                    else {

                        Toast.makeText(ServiceMachineList.this, "Failed", Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ServiceMachineList.this, "Total Failed", Toast.LENGTH_LONG).show();
                }
                FunctionConstant.progressDialog.dismiss();

            } else {
                Toast.makeText(ServiceMachineList.this, "Server issue", Toast.LENGTH_SHORT).show();

            }
            FunctionConstant.progressDialog.dismiss();
        }

        @Override
        public void onFailure(Throwable t) {

        }
    });


}
}
