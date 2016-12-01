package sriyaan.ac;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class ExpandList extends AppCompatActivity implements View.OnClickListener {

    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;


    TextView txt_search,txt_leftarrow;
    EditText edit_search;
    TextView gonetxt;

    String user_id;
    int  page_id=0;
    UserApi service;
    void api()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant_url.commonurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserApi.class);
    }


    ArrayList<Group> list = new ArrayList<Group>();
    ArrayList<Child> ch_list;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    CustomExpandableListAdapter adapter;

//    ArrayList<HashMap<String, String>> expandableListTitle = new ArrayList<HashMap<String, String>>();
//    HashMap<String, String> expandableListDetail = new HashMap<String, String>();
////
    ArrayList<String> expandableListTitle;
//    HashMap<String, List<String>> expandableListDetail=new HashMap<String, List<String>>();
//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_expandlist);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        findview();

        getall_customer();

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
                    list.clear();
                    expandableListView.clearFocus();

                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(ExpandList.this);
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
    }


    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
//        if (interstitial.isLoaded()) {
            interstitial.show();
//        }
    }


    private void findview() {
        txt_search=(TextView)findViewById(R.id.txt_search);
        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow);
        gonetxt=(TextView)findViewById(R.id.gonetxt);

        Typeface type = Typeface.createFromAsset(ExpandList.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
        txt_search.setTypeface(type);
        txt_leftarrow.setOnClickListener(this);


        edit_search = (EditText)findViewById(R.id.edtsearch);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_leftarrow:
                finish();
                break;

        }
    }


    void getall_customer()
    {

        FunctionConstant.loading(ExpandList.this);


        api();
        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        user_id=sharedPreferences.getString("id", "");
        String method="getallcustomer";
        page_id =+1;

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
                            for ( int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                               // Settergetter setter_getter=new Settergetter();

                                Group gru = new Group();
                                ch_list = new ArrayList<Child>();
                                HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
                                 gru.setName(jsonObject.getString("cust_name"));

                                Child ch = new Child();
                                ch.setName("Customer Profile");
                                ch.setMachine("Machine Details");
                                ch.setCustid(jsonObject.getString("id"));
                                ch_list.add(ch);
//                                List<String> First = new ArrayList<String>();
//                                First.add("Customer View1");
//                                First.add("Machine Details1");
//
//                                String cust_name=jsonObject.getString("cust_name");
//                                expandableListDetail.put(cust_name, String.valueOf(First));

                                gru.setItems(ch_list);
                                list.add(gru);

//                                expandableListTitle.add(expandableListDetail);
                               // setter_getter.setCust_name(jsonObject.getString("cust_name"));
                              //  expandableListDetail.add(setter_getter);

                            }
//                        adapter = new ServiceScheduleAdapter(ServiceSchedule.this, arrayList);
//                        mRecyclerView.setAdapter(adapter);


                          // expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
//                            expandableListAdapter = new CustomExpandableListAdapter(ExpandList.this, expandableListTitle,expandableListDetail);
                            adapter = new CustomExpandableListAdapter(ExpandList.this, list);

                            expandableListView.setAdapter(adapter);
                        }

                        else {

                            Toast.makeText(ExpandList.this, "Failed", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ExpandList.this, "Customer list is empty", Toast.LENGTH_LONG).show();
                    }
                FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(ExpandList.this, "Server issue", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }


}

//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
//
//
//                Intent intent;
//                switch (groupPosition)
//                {
//                    case 0 :
//                        switch(childPosition)
//                        {
//                            case 0 :
//                                intent = new Intent(ExpandList.this, CustomerProfile.class);
//                            startActivity(intent);
//                                break;
//                            case 1:
//                                intent = new Intent(ExpandList.this, MachineList.class);
//                                startActivity(intent);
//                                break;
//                        }
//                        break;
//                    case 1:
//                        switch(childPosition)
//                        {
//                            case 0 :
//                                intent = new Intent(ExpandList.this, CustomerProfile.class);
//                                startActivity(intent);
//                                break;
//                            case 1:
//                                intent = new Intent(ExpandList.this, MachineList.class);
//                                startActivity(intent);
//                                break;
//                        }
//                        break;
//                    case 2:
//                        switch(childPosition)
//                        {
//                            case 0 :
//                                intent = new Intent(ExpandList.this, CustomerProfile.class);
//                                startActivity(intent);
//                                break;
//                            case 1:
//                                intent = new Intent(ExpandList.this, MachineList.class);
//                                startActivity(intent);
//                                break;
//                        }
//                        break;
//                    case 3:
//                        switch(childPosition)
//                        {
//                            case 0 :
//                                intent = new Intent(ExpandList.this, CustomerProfile.class);
//                                startActivity(intent);
//                                break;
//                            case 1:
//                                intent = new Intent(ExpandList.this, MachineList.class);
//                                startActivity(intent);
//                                break;
//                        }
//                        break;
//                }
//
//                return true;
//
//
////                final String selected = (String) expandableListAdapter.getChild(
////                        groupPosition, childPosition);
////
////                // Switch case to open selected child element activity on child element selection.
////
////                Intent intent;
////                switch(selected){
////                    case "Customer View1":
////                        intent = new Intent(ExpandList.this, CustomerProfile.class);
////                        startActivity(intent);
////                        break;
////
////                    case "Machine Details1":
////                        intent = new Intent(ExpandList.this, CustomerMachineDetails.class);
////                        startActivity(intent);
////                        break;
////
////                    case "Customer View12":
////                        intent = new Intent(ExpandList.this, CustomerProfile.class);
////                        startActivity(intent);
////                        break;
////
////                    case "Machine Details2":
////                        intent = new Intent(ExpandList.this, CustomerMachineDetails.class);
////                        startActivity(intent);
////                        break;
////                    case "Customer View3":
////                        intent = new Intent(ExpandList.this, CustomerProfile.class);
////                        startActivity(intent);
////                        break;
////
////                    case "Machine Details3":
////                        intent = new Intent(ExpandList.this, CustomerMachineDetails.class);
////                        startActivity(intent);
////                        break;
////                }
////                return true;
////
////                Intent intent = new Intent(getApplicationContext(),CustomerProfile.class);
////                startActivity(intent);
//
//            }
//        });