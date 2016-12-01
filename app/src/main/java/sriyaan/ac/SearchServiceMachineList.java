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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class SearchServiceMachineList extends AppCompatActivity implements View.OnClickListener {

    TextView txt_search,txt_leftarrow;
    EditText edit_search;
    TextView gonetxt;
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

    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
    private RecyclerView mRecyclerView;
    private ServiceMachineListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_search_service_machine_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findview();

        txt_leftarrow.setOnClickListener(this);

        getall_machine();

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = edit_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);


            }

            @Override
            public void afterTextChanged(Editable s) {

//                String text = edit_search.getText().toString().toLowerCase(Locale.getDefault());
//                adapter.filter(text);


                if (edit_search.getText().toString().length() >= 0) {
                    gonetxt.setVisibility(View.GONE);
                } else {
                    gonetxt.setVisibility(View.VISIBLE);
                    arrayList.clear();
                    mRecyclerView.clearFocus();

                }
            }
        });


    }

    private void findview() {
        txt_search=(TextView)findViewById(R.id.txt_search);
        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        gonetxt=(TextView)findViewById(R.id.gonetxt);

        Typeface type = Typeface.createFromAsset(SearchServiceMachineList.this.getAssets(),"fontawesome-webfont.ttf");

        txt_search.setTypeface(type);
        txt_leftarrow.setTypeface(type);

        edit_search = (EditText)findViewById(R.id.edtsearch);

    }

    void getall_machine()
    {

        FunctionConstant.loading(SearchServiceMachineList.this);
        api();

        Intent intent = getIntent();
        if(intent != null){
            cust_id = intent.getStringExtra("cust_id");
        }
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
                            adapter = new ServiceMachineListAdapter(SearchServiceMachineList.this, arrayList);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(adapter);


                        }

                        else {

                            Toast.makeText(SearchServiceMachineList.this, "Failed", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SearchServiceMachineList.this, "Total Failed", Toast.LENGTH_LONG).show();
                    }
                    FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(SearchServiceMachineList.this, "Server issue", Toast.LENGTH_SHORT).show();

                }
                FunctionConstant.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


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
}
