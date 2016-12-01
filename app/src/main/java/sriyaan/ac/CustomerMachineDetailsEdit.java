package sriyaan.ac;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

public class CustomerMachineDetailsEdit extends AppCompatActivity {

    private int dYear;
    private int dMonth;
    private int dDay;
    static final int DATE_DIALOG_ID = 1;
    Calendar c;

    AlertDialog.Builder alert;
    String service_type5,service_type6;

    EditText machine_name2,machine_type2,making_date2,machine_details2,service_type2,yearly_service2;
    TextView txt_leftarrow;
    TextInputLayout machine_name_input_layout,machine_type_input_layout,make_date_input_layout,machine_details_input_layout;
    String id,user_id,cust_id,machine_name1,machine_type1,making_date1,machine_details1,service_type1,service_schedule1;

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
        setContentView(R.layout.activity_customer_machine_details_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    error();
                    if (validation())
                    {
                        edit_customer();
                    }


                }
            });
        }

        findview();

        making_date2.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);
            }
        });
        setIconInDate();

        service_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectchecksbox();
            }
        });

        yearly_service2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectchecksbox2();
            }
        });

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

            machine_name1 =  intent.getStringExtra("machine_name1");
            machine_type1 = intent.getStringExtra("machine_type1");
            making_date1 =  intent.getStringExtra("making_date1");
            machine_details1 =  intent.getStringExtra("machine_details1");
            service_type1 =  intent.getStringExtra("service_type1");
            service_schedule1 =  intent.getStringExtra("service_schedule1");
            id = intent.getStringExtra("machine_id");
            cust_id = intent.getStringExtra("cust_id");
        }

        setname();
    }

    private void setname() {

        machine_name2.setText(machine_name1);
        machine_type2.setText(machine_type1);
        making_date2.setText(making_date1);
        machine_details2.setText(machine_details1);
        service_type2.setText(service_type1);
        yearly_service2.setText(service_schedule1);

    }

    private void findview() {

        machine_name_input_layout = (TextInputLayout) findViewById(R.id.machine_name_input_layout);
        machine_type_input_layout = (TextInputLayout) findViewById(R.id.machine_type_input_layout);
        make_date_input_layout = (TextInputLayout) findViewById(R.id.making_date_input_layout);
        machine_details_input_layout = (TextInputLayout) findViewById(R.id.machine_details_input_layout);


        machine_name2 = (EditText) findViewById(R.id.machine_name);
        machine_type2 = (EditText) findViewById(R.id.machine_type);
        making_date2 = (EditText) findViewById(R.id.making_date);
        machine_details2 = (EditText) findViewById(R.id.machine_details);
        service_type2 = (EditText) findViewById(R.id.service_type);
        yearly_service2 = (EditText) findViewById(R.id.yearly_service);

        txt_leftarrow = (TextView) findViewById(R.id.txt_leftarrow1);
        Typeface type = Typeface.createFromAsset(CustomerMachineDetailsEdit.this.getAssets(), "fontawesome-webfont.ttf");

        txt_leftarrow.setTypeface(type);

        alert = new AlertDialog.Builder(this);

    }

    private void error() {
        machine_name2.addTextChangedListener(new TextWatcher() {
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

        machine_type2.addTextChangedListener(new TextWatcher() {
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

        making_date2.addTextChangedListener(new TextWatcher() {
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

        machine_details2.addTextChangedListener(new TextWatcher() {
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


    boolean validation() {
        String username = machine_details2.getText().toString();
        String mobile = machine_type2.getText().toString();
        String date5 = making_date2.getText().toString();
        String city = machine_details2.getText().toString();

        if (username.equals("")) {

            machine_name_input_layout.setError("Please enter a machine name");
            requestFocus(machine_details2);
            return false;
        }

        else if(mobile.equals("")) {
            //etpassword.requestFocus();

            machine_type_input_layout.setError("Please enter machine type");
            requestFocus(machine_type2);
            return false;
        }

        else if(date5.equals("")) {
            //etpassword.requestFocus();

            make_date_input_layout.setError("Please enter making date");
            requestFocus(making_date2);
            return false;
        }

        else if (city.equals("")) {
            //etpassword.requestFocus();

            machine_details_input_layout.setError("please enter machine details");
            requestFocus(machine_details2);
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



    private void setIconInDate() {
        // TODO Auto-generated method stub
        try {
            Drawable datePickerIcon = getResources().getDrawable(
                    R.drawable.calender);

            making_date2.setCompoundDrawablesWithIntrinsicBounds(null, null,
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

            making_date2.setText(new StringBuilder()
                    .append(dDay).append("-").append(pad(dMonth + 1)).append("-").append(dYear));

        }
    };

    private static String pad(int cc) {
        if (cc >= 10)
            return String.valueOf(cc);
        else
            return "0" + String.valueOf(cc);
    }


    private void selectchecksbox() {

//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerMachineDetailsEdit.this);
        final Dialog alertDialog = new Dialog(CustomerMachineDetailsEdit.this);
        alertDialog.setContentView(R.layout.service_type);
        alertDialog.setTitle("Select Service Type");

//       final RadioButton amc = new RadioButton (CustomerMachineDetailsEdit.this);
//        final RadioButton cmc = new RadioButton (CustomerMachineDetailsEdit.this);
//        final RadioButton individual = new RadioButton (CustomerMachineDetailsEdit.this);
        final RadioButton AMC = (RadioButton) alertDialog.findViewById(R.id.amc);
        final RadioButton CMC = (RadioButton) alertDialog.findViewById(R.id.cmc);
        final RadioButton Individual = (RadioButton) alertDialog.findViewById(R.id.individual);
        Button btnDisplay = (Button) alertDialog.findViewById(R.id.done);

        if (btnDisplay != null) {
            btnDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(AMC.isChecked()) {
                        service_type5 = "AMC";
                    }
                    else if (CMC.isChecked()){
                        service_type5 = "CMC";
                    }
                    else if (Individual.isChecked()) {
                        service_type5 = "Individual";
                    }
                    else
                    { Toast.makeText(CustomerMachineDetailsEdit.this,"Please click vaild check box",Toast.LENGTH_SHORT).show();}

                    service_type2.setText(service_type5);
                    Toast.makeText(CustomerMachineDetailsEdit.this,service_type5,Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }

            });
        }


        alertDialog.show();
    }


    private void selectchecksbox2() {

//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerMachineDetailsEdit.this);
        final Dialog alertDialog = new Dialog(CustomerMachineDetailsEdit.this);
        alertDialog.setContentView(R.layout.yearly_services);
        alertDialog.setTitle("Select Yearly Services");

//       final RadioButton amc = new RadioButton (CustomerMachineDetailsEdit.this);
//        final RadioButton cmc = new RadioButton (CustomerMachineDetailsEdit.this);
//        final RadioButton individual = new RadioButton (CustomerMachineDetailsEdit.this);
        final RadioButton AMC1 = (RadioButton) alertDialog.findViewById(R.id.amc1);
        final RadioButton CMC1 = (RadioButton) alertDialog.findViewById(R.id.cmc1);
        final RadioButton Individual1 = (RadioButton) alertDialog.findViewById(R.id.individual1);
        Button btnDisplay1 = (Button) alertDialog.findViewById(R.id.done1);

        if (btnDisplay1 != null) {
            btnDisplay1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(AMC1.isChecked()) {
                        service_type6 = "2";
                    }
                    else if (CMC1.isChecked()){
                        service_type6 = "4";
                    }
                    else if (Individual1.isChecked()) {
                        service_type6 = "6";
                    }
                    else
                    { Toast.makeText(CustomerMachineDetailsEdit.this,"Please click vaild check box",Toast.LENGTH_SHORT).show();}

                    yearly_service2.setText(service_type6);
//                    Toast.makeText(CustomerMachineDetailsEdit.this,service_type6,Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }

            });
        }


        alertDialog.show();
    }


    private void edit_customer() {

        FunctionConstant.loading(CustomerMachineDetailsEdit.this);

        api();

//        Intent intent = getIntent();
//        if(intent != null){
//            id = intent.getStringExtra("cust_id");
//        }


        String  machine_name = machine_name2.getText().toString();
        String  machine_type = machine_type2.getText().toString();
        String  making_date = making_date2.getText().toString();
        String machine_details = machine_details2.getText().toString();
        String service_type = service_type2.getText().toString();
        String service_schedule = yearly_service2.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("AC",0);
        user_id = sharedPreferences.getString("id","");

        String method = "updatecustmachineservice";
        Call<JsonObject> call = service.updatecustmachineservice(machine_name,machine_type,making_date,
                machine_details,service_type,service_schedule,method,cust_id,user_id,id);

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

//                            JSONObject object = jObject.getJSONObject("userdata");
//
//                            String cust_id = object.getString("id");



                            Toast.makeText(CustomerMachineDetailsEdit.this,status, Toast.LENGTH_LONG).show();
//
//                            Intent intent = new Intent(CustomerMachineDetailsEdit.this,CustomerMachineDetails.class);
//                            intent.putExtra("machine_id", id);
//                            intent.putExtra("cust_id", cust_id);
//                            startActivity(intent);

                            finish();


                        } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(CustomerMachineDetailsEdit.this, status, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CustomerMachineDetailsEdit.this, " Data not update", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(CustomerMachineDetailsEdit.this, " Server issue", Toast.LENGTH_SHORT).show();

                }


                FunctionConstant.progressDialog.dismiss();

            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }


}
