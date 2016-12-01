package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class AddCustomer extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;

    EditText customer_name,customer_mobile,customer_email,customer_city,customer_area,customer_address;
    Button Subimit;
    TextView txt_leftarrow;
    TextInputLayout Name_text_input_layout,Mobile_number_input_layout,Email_id_input_layout,City_input_layout,Area_input_layout,Address_input_layout;

    UserApi service;
    String user_id;

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
        setContentView(R.layout.activity_add_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(AddCustomer.this);
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



        erorr();
        Subimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation())
                {

                    addcustomer();
                }
            }
        });

    }

    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


    private void findview() {

        Name_text_input_layout = (TextInputLayout) findViewById(R.id.customer_name_input_layout);
        Mobile_number_input_layout = (TextInputLayout) findViewById(R.id.mobile_number_input_layout);
        Email_id_input_layout = (TextInputLayout) findViewById(R.id.email_id_input_layout);
        City_input_layout = (TextInputLayout) findViewById(R.id.city_input_layout);
        Area_input_layout = (TextInputLayout) findViewById(R.id.area_input_layout);
        Address_input_layout = (TextInputLayout) findViewById(R.id.address_input_layout);

        customer_name = (EditText)findViewById(R.id.customer_name);
        customer_mobile = (EditText)findViewById(R.id.mobile_number);
        customer_email = (EditText)findViewById(R.id.email_id);
        customer_city = (EditText)findViewById(R.id.city);
        customer_area = (EditText)findViewById(R.id.area);
        customer_address = (EditText)findViewById(R.id.address);
        Subimit = (Button)findViewById(R.id.submit);

        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(AddCustomer.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }


    private void erorr() {

        customer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Name_text_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customer_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Mobile_number_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customer_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Email_id_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customer_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                City_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customer_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Area_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customer_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Address_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }




    boolean validation() {
        String username = customer_name.getText().toString();
        String mobile = customer_mobile.getText().toString();
        String city = customer_city.getText().toString();
        String area = customer_area.getText().toString();
        String address = customer_address.getText().toString();
        String email = customer_email.getText().toString();

        if (username.equals("")) {

            Name_text_input_layout.setError("Please enter a name");
            requestFocus(customer_name);
            return false;
        }

        else if(mobile.equals("")) {
            //etpassword.requestFocus();

            Mobile_number_input_layout.setError("Please enter mobile number");
            requestFocus(customer_mobile);
            return false;
        }

        else if(!(mobile.length()==10)) {
            //etpassword.requestFocus();

            Mobile_number_input_layout.setError("Please enter vaild mobile number");
            requestFocus(customer_mobile);
            return false;
        }

        else if ((email.toString().length()>0) &&(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            //etpassword.requestFocus();

            Email_id_input_layout.setError("Please enter vaild email id");
            requestFocus(customer_email);
            return false;
        }

        else if (city.equals("")) {
            //etpassword.requestFocus();

            City_input_layout.setError("Please enter a city name");
            requestFocus(customer_city);
            return false;
        }  else if (area.equals("")) {
            //etpassword.requestFocus();

            Area_input_layout.setError("Please enter a area name");
            requestFocus(customer_area);
            return false;
        } else if (address.equals("")) {
            //etpassword.requestFocus();

            Address_input_layout.setError("Please enter a address");
            requestFocus(customer_address);
            return false;
        }else {
            return true;
        }
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    private void addcustomer() {

        FunctionConstant.loading(AddCustomer.this);

        api();

        String cust_name = customer_name.getText().toString();
        String cust_mobile_no = customer_mobile.getText().toString();
        String cust_email_id = customer_email.getText().toString();
        String cust_city = customer_city.getText().toString();
        String cust_area = customer_area.getText().toString();
        String cust_address = customer_address.getText().toString();

        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        user_id=sharedPreferences.getString("id", "");


        String method = "addcustomer";
        Call<JsonObject> call = service.addcustomer(cust_name,cust_mobile_no,cust_email_id,
                cust_city,cust_area,cust_address,method,user_id);

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

                        String cust_id = object.getString("id");



                        Toast.makeText(AddCustomer.this,status, Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(Login.this, Start.class));

                        Intent intent = new Intent(AddCustomer.this,Machine_Details.class);
                        intent.putExtra("cust_id", cust_id);
                        startActivity(intent);

                        customer_name.setText("");
                        customer_mobile.setText("");
                        customer_email.setText("");
                        customer_city.setText("");
                        customer_area.setText("");
                        customer_address.setText("");
finish();


                    } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                        Toast.makeText(AddCustomer.this, status, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddCustomer.this, " Data not add", Toast.LENGTH_SHORT).show();

                }

                } else {
                    Toast.makeText(AddCustomer.this, " Server issue", Toast.LENGTH_SHORT).show();

                }


                FunctionConstant.progressDialog.dismiss();


            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }

}
