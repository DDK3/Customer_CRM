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

public class CustomerCollectionDetails extends AppCompatActivity {

    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
    private RecyclerView mRecyclerView;
    private CustomerCollectionDetailsAdapter adapter;
    TextView txt_leftarrow,totalamount;

    int i,page_id=0;
    UserApi service;
    String user_id,customer_id,total_amount;;


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
//        setContentView(R.layout.collectiondetails);
        setContentView(R.layout.activity_customer_collection_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        if (intent != null) {
            customer_id = intent.getStringExtra("cust_id");

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


                Intent intent = new Intent(CustomerCollectionDetails.this,CustomerCollectionAdd.class);
                intent.putExtra("cust_id",customer_id);
                startActivity(intent);
                finish();

            }
        });    /* Initialize recyclerview */
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

        getall_collection();

//        banner();
//        prepareMovieData();
    }

    private void findview() {
        totalamount = (TextView)findViewById(R.id.totalamount);

        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(CustomerCollectionDetails.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }

//    private void prepareMovieData() {
//        Settergetter movie = new Settergetter("2000 ");
//        arrayList.add(movie);
//
//        movie = new Settergetter("3000");
//        arrayList.add(movie);
//
//        movie = new Settergetter("1200");
//        arrayList.add(movie);
//
//        movie = new Settergetter("3656");
//        arrayList.add(movie);
//
//
//        adapter.notifyDataSetChanged();
//    }

    void getall_collection()
    {

        FunctionConstant.loading(CustomerCollectionDetails.this);

        page_id =+1;
        api();



        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        user_id=sharedPreferences.getString("id", "");
        String method="getallcollection";


        Call<JsonObject> call = service.getallcollection(user_id,customer_id,method);

        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {

                if(response.isSuccess())
                {
                    try {
                        JSONObject jObject = new JSONObject(response.body().toString());
                        String code = jObject.getString("error_code");
                        String  status = jObject.getString("error_msg");
                        total_amount = jObject.getString("total_amount");
                        totalamount.setText(total_amount);
                        if(code.equals("0"))
                        {
                            final JSONArray jsonArray = jObject.getJSONArray("collection_list");
                            for (i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Settergetter setter_getter=new Settergetter();

                                setter_getter.setAmount(jsonObject.getString("amount"));
                                setter_getter.setRemark(jsonObject.getString("remark"));
                                setter_getter.setChequeno(jsonObject.getString("cheque_no"));
                                setter_getter.setPay(jsonObject.getString("pay_type"));
                                arrayList.add(setter_getter);

                            }

                            adapter = new CustomerCollectionDetailsAdapter(CustomerCollectionDetails.this, arrayList);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(adapter);

                        }

                        else {

                            Toast.makeText(CustomerCollectionDetails.this, "Collection List is Empty", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CustomerCollectionDetails.this, "Collection List Empty", Toast.LENGTH_LONG).show();
                    }
                FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(CustomerCollectionDetails.this, "Server issue", Toast.LENGTH_SHORT).show();

                }
                FunctionConstant.progressDialog.dismiss();

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getall_collection();
//    }


}
