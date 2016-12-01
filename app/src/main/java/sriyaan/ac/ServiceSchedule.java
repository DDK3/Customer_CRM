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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
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

public class ServiceSchedule extends AppCompatActivity implements View.OnClickListener {

    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;

    EditText  edit_search;
    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
    private RecyclerView mRecyclerView;
    private ServiceScheduleAdapter adapter;

    TextView txt_leftarrow;
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
        setContentView(R.layout.activity_service_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AdRequest adRequest = new AdRequest.Builder().build();
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(ServiceSchedule.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivity(new Intent(ServiceSchedule.this, SerachServiceSchedule.class));
            }
        });


        findview();

        txt_leftarrow.setOnClickListener(this);

        /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getall_customer();


    }

    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void findview() {
        txt_leftarrow = (TextView) findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(ServiceSchedule.this.getAssets(), "fontawesome-webfont.ttf");

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

    void getall_customer()
    {

        FunctionConstant.loading(ServiceSchedule.this);


        api();
        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        user_id=sharedPreferences.getString("id", "");
        String method="getallcustomer";
//        page_id =+1;

        Call<JsonObject> call = service.getallcustomer(user_id, method);

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
                                setter_getter.setCust_name(jsonObject.getString("cust_name"));
                                setter_getter.setCust_id(jsonObject.getString("id"));
                                arrayList.add(setter_getter);

                            }
//                        adapter = new ServiceScheduleAdapter(ServiceSchedule.this, arrayList);
//                        mRecyclerView.setAdapter(adapter);
                            adapter = new ServiceScheduleAdapter(ServiceSchedule.this, arrayList);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(adapter);

                        }

                        else {

                            Toast.makeText(ServiceSchedule.this, "Failed", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ServiceSchedule.this, "Total Failed", Toast.LENGTH_LONG).show();
                    }
                FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(ServiceSchedule.this, "Server issue", Toast.LENGTH_SHORT).show();

                }
                FunctionConstant.progressDialog.dismiss();

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }

}
