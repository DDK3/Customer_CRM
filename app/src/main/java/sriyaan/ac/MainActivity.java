package sriyaan.ac;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.w3c.dom.Text;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

//    InterstitialAd mInterstitialAd;
//    Button newgame;

    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;


    String password,id, oldpass1,confpass,oldpassword;
    UserApi service;
    void api()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant_url.commonurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserApi.class);
    }


    private static long back_pressed;
    AlertDialog.Builder alert;
    ImageView Add_customer,Customer_list,Service_shedule,Comming_soon;

    TextView username,useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        String id=sharedPreferences.getString("id", "");
        String name=sharedPreferences.getString("name", "");
        String email=sharedPreferences.getString("email", "");
        String password=sharedPreferences.getString("password", "");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/


        username = (TextView)header.findViewById(R.id.nav_username);
        useremail = (TextView)header.findViewById(R.id.nav_useremail);

        username.setText(name);
        useremail.setText(email);



        initialize();

        Customer_list.setOnClickListener(this);
        Add_customer.setOnClickListener(this);
        Service_shedule.setOnClickListener(this);
        Comming_soon.setOnClickListener(this);




//        newgame = (Button) findViewById(R.id.newgame_button);

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-6039345424470635/2205459704");
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                requestNewInterstitial();
////                beginPlayingGame();
//            }
//        });
//
//        requestNewInterstitial();
//        mInterstitialAd.show();
////        newgame.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (mInterstitialAd.isLoaded()) {
////                    mInterstitialAd.show();
////                }
////            }
////        });
//
//
//        AdView adView = (AdView) findViewById(R.id.adView);
////        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.SMART_BANNER);
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice("44D37DE7CBE250220552E8913F75DAE4").build();
//
//        adView.loadAd(adRequest);



        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

// Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);
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


//    private void requestNewInterstitial() {
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
//                .build();
//
//        mInterstitialAd.loadAd(adRequest);
//    }

    private void initialize() {

        Add_customer = (ImageView) findViewById(R.id.add_customer);
        Customer_list = (ImageView) findViewById(R.id.customer_list);
        Service_shedule = (ImageView)findViewById(R.id.service_shedule);
        Comming_soon = (ImageView)findViewById(R.id.collection);

        alert = new AlertDialog.Builder(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Please Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, AdminProfile.class));

            // Handle the camera action
        } else if (id == R.id.nav_change_password) {

//            startActivity(new Intent(MainActivity.this, EditAdminProfile.class));
            changepassword();

        } else if (id == R.id.nav_logout) {
//            Toast.makeText(MainActivity.this,"We are working on",Toast.LENGTH_LONG).show();
            Logout();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changepassword() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Change Password");
        final EditText oldPass = new EditText(MainActivity.this);
        final EditText newPass = new EditText(MainActivity.this);
        final EditText confirmPass = new EditText(MainActivity.this);


        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(MainActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        confpass = confirmPass.getText().toString();
                        oldpassword = oldPass.getText().toString();
                         password = newPass.getText().toString();
if (!(password.equals("")|| confpass.equals(""))) {
    if (confpass.equals(password)) {

        edituser_password();
    } else {
        Toast.makeText(MainActivity.this, "password not match", Toast.LENGTH_LONG).show();
    }
}
                        Toast.makeText(MainActivity.this, "please enter new password", Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }

    public  void Logout(){
        alert.setTitle("Logout");
        alert.setMessage("Do you want to logout ?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finishAffinity();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){


            case R.id.customer_list: /** AlerDialog when click on Exit */

//                    mInterstitialAd.show();

                 intent = new Intent(this, ExpandList.class);
                this.startActivity(intent);
                break;

            case R.id.add_customer: /** Start a new Activity MyCards.java */
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
                 intent = new Intent(this, AddCustomer.class);
                this.startActivity(intent);
                break;

            case R.id.service_shedule: /** AlerDialog when click on Exit */
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
                intent = new Intent(this, ServiceSchedule.class);
                this.startActivity(intent);
                break;

            case R.id.collection: /** AlerDialog when click on Exit */
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
                intent = new Intent(this, CustomerCollectionList.class);
                this.startActivity(intent);
                break;

        }
    }

    private void edituser_password() {

        FunctionConstant.loading(MainActivity.this);
        api();

//        Intent intent = getIntent();
//        if(intent != null){
//            id = intent.getStringExtra("cust_id");
//        }

//        name = username.getText().toString();
//        email = useremail.getText().toString();
//        password = user_address.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("AC",0);
        id = sharedPreferences.getString("id","");

        String method = "edituserpassword";
        Call<JsonObject> call = service.edituserpassword(id,password,oldpassword,method);

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


                            Toast.makeText(MainActivity.this,status, Toast.LENGTH_SHORT).show();

                        } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, status, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, " Data not add", Toast.LENGTH_SHORT).show();

                    }

                    FunctionConstant.progressDialog.dismiss();

                } else {
                    Toast.makeText(MainActivity.this, " Server issue", Toast.LENGTH_SHORT).show();

                }
                FunctionConstant.progressDialog.dismiss();




            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }


}
