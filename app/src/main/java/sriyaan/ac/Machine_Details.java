package sriyaan.ac;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Calendar;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class Machine_Details extends AppCompatActivity {

    private int dYear;
    private int dMonth;
    private int dDay;
    static final int DATE_DIALOG_ID = 1;
    Calendar c;


    Button Subimit;
    EditText Machine_name,Machine_type,make_date,machine_detail;
    TextView txt_leftarrow;
    TextInputLayout machine_name_input_layout,machine_type_input_layout,make_date_input_layout,machine_details_input_layout;

    UserApi service;
    String user_id,next_page,cust_id;

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
        setContentView(R.layout.activity_machine__details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findview();


        make_date.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);
            }
        });
        setIconInDate();

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



        Subimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error();
                if (validation())
                {
                    addmachine_details();
                }
            }
        });

    }

    boolean validation() {
        String username = Machine_name.getText().toString();
        String mobile = Machine_type.getText().toString();
        String date5 = make_date.getText().toString();
        String city = machine_detail.getText().toString();

        if (username.equals("")) {

            machine_name_input_layout.setError("Please enter a machine name");
            requestFocus(Machine_name);
            return false;
        }

        else if(mobile.equals("")) {
            //etpassword.requestFocus();

            machine_type_input_layout.setError("Please enter machine type");
            requestFocus(Machine_type);
            return false;
        }

        else if(date5.equals("")) {
            //etpassword.requestFocus();

            make_date_input_layout.setError("Please enter making date");
            requestFocus(make_date);
            return false;
        }

        else if (city.equals("")) {
            //etpassword.requestFocus();

            machine_details_input_layout.setError("please enter machine details");
            requestFocus(machine_detail);
            return false;
        }
        else {
            return true;
        }
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void error() {
        Machine_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               machine_name_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Machine_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                machine_type_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        make_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                make_date_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        machine_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                machine_details_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void findview() {

        machine_name_input_layout = (TextInputLayout) findViewById(R.id.machine_name_input_layout);
        machine_type_input_layout = (TextInputLayout) findViewById(R.id.machine_type_input_layout);
        make_date_input_layout = (TextInputLayout) findViewById(R.id.making_date_input_layout);
        machine_details_input_layout = (TextInputLayout) findViewById(R.id.machine_details_input_layout);

        Machine_name = (EditText)findViewById(R.id.machine_name);
        Machine_type = (EditText)findViewById(R.id.machine_type);
        make_date = (EditText)findViewById(R.id.making_date);
        machine_detail = (EditText)findViewById(R.id.machine_details);

        Subimit = (Button)findViewById(R.id.submit_machine_details);

        txt_leftarrow=(TextView)findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(Machine_Details.this.getAssets(),"fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);
    }


    private void setIconInDate() {
        // TODO Auto-generated method stub
        try {
            Drawable datePickerIcon = getResources().getDrawable(
                    R.drawable.calender);

            make_date.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    datePickerIcon, null);

            c = Calendar.getInstance();

            int dY = c.get(Calendar.YEAR);
            dYear = dY - 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dDateSetListener, dYear, dMonth,
                        dDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub

            dYear = year;
            dMonth = monthOfYear;
            dDay = dayOfMonth;

            make_date.setText(new StringBuilder()
                    .append(dDay).append("-").append(pad(dMonth + 1)).append("-").append(dYear));

        }
    };

    private static String pad(int cc) {
        if (cc >= 10)
            return String.valueOf(cc);
        else
            return "0" + String.valueOf(cc);
    }




    private void addmachine_details() {

        FunctionConstant.loading(Machine_Details.this);


        api();

        Intent intent=getIntent();
        if (intent != null) {
            cust_id = intent.getStringExtra("cust_id");
            next_page = intent.getStringExtra("next_page");

        }


        String machine_name = Machine_name.getText().toString();
        String machine_type = Machine_type.getText().toString();
        String making_date = make_date.getText().toString();
        String machine_details = machine_detail.getText().toString();

        SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
        user_id=sharedPreferences.getString("id", "");


        String method = "addmachinedetails";
        Call<JsonObject> call = service.addmachinedetails(machine_name,machine_type,making_date,
                machine_details,method,cust_id,user_id);

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

                           String machine_id = object.getString("id");



                           Toast.makeText(Machine_Details.this,status, Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(Login.this, Start.class));

                           Intent intent = new Intent(Machine_Details.this,Service_Type.class);
                           intent.putExtra("cust_id", cust_id);
                           intent.putExtra("machine_id", machine_id);
                           intent.putExtra("next_page",next_page);
                           startActivity(intent);

                           Machine_name.setText("");
                           Machine_type.setText("");
                           make_date.setText("");
                           machine_detail.setText("");
finish();

                       } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                           Toast.makeText(Machine_Details.this, "Failed", Toast.LENGTH_LONG).show();
                       }

                   } catch (Exception e) {
                       e.printStackTrace();
                       Toast.makeText(Machine_Details.this, " Data not add", Toast.LENGTH_SHORT).show();

                   }
               }
                else {
                   Toast.makeText(Machine_Details.this, "Server issue", Toast.LENGTH_SHORT).show();

               }

                FunctionConstant.progressDialog.dismiss();


            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }


}
