package sriyaan.ac;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

public class CustomerCollectionListSearch extends AppCompatActivity {

    TextView txt_search,txt_leftarrow;
    EditText edit_search;
    TextView gonetxt;

    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
    private RecyclerView mRecyclerView;
    private CustomerCollectionListAdapter adapter;


    String user_id;
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
        setContentView(R.layout.activity_customer_collection_list_search);

        findview();
        /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getall_customer();

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

        Typeface type = Typeface.createFromAsset(CustomerCollectionListSearch.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
        txt_search.setTypeface(type);



        edit_search = (EditText)findViewById(R.id.edtsearch);

    }

    void getall_customer()
    {

        FunctionConstant.loading(CustomerCollectionListSearch.this);


        api();
        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        user_id=sharedPreferences.getString("id", "");
        String method="getallcustomer";

        page_id =+1;

        Call<JsonObject> call = service.getallcustomer(user_id,method);

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

                                setter_getter.setCust_id(jsonObject.getString("id"));
                                setter_getter.setCust_name(jsonObject.getString("cust_name"));
                                arrayList.add(setter_getter);

                            }
//                        adapter = new ServiceScheduleAdapter(ServiceSchedule.this, arrayList);
//                        mRecyclerView.setAdapter(adapter);
                            adapter = new CustomerCollectionListAdapter(CustomerCollectionListSearch.this, arrayList);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(adapter);

                        }

                        else {

                            Toast.makeText(CustomerCollectionListSearch.this, "Failed", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CustomerCollectionListSearch.this, "Total Failed", Toast.LENGTH_LONG).show();
                    }
                FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(CustomerCollectionListSearch.this, "Server issue", Toast.LENGTH_SHORT).show();

                }
                FunctionConstant.progressDialog.dismiss();


            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }
}
