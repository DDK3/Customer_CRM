package sriyaan.ac;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import sriyaan.ac.Url.FunctionConstant;

public class CustomerCollectionAdd extends AppCompatActivity implements View.OnClickListener {

    private int dYear;
    private int dMonth;
    private int dDay;
    static final int DATE_DIALOG_ID = 1;
    Calendar c;
    EditText date1,amount1,cheque_no1;
    TextView backpress;
    Button submit;

    TextInputLayout amount_input_layout,date1_input_layout;

//    ArrayList<Settergetter> arrayList = new ArrayList<Settergetter>();
//    private RecyclerView mRecyclerView;
//    private CustomerCollectionAddAdapter adapter;

int i,page_id=0;
    UserApi service;
    String user_id,customer_id,remark,amount, pay_type1,cheque_no,pay_type;;

    Spinner spinner;
    private View.OnTouchListener spinnerOnTouch;


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
        setContentView(R.layout.activity_customer_collection_add);
//        setContentView(R.layout.collectiondetailsadd);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        findview();

        date1.setOnClickListener(this);
//        date1.setOnClickListener(new View.OnClickListener() {
//
//            @SuppressWarnings("deprecation")
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                showDialog(DATE_DIALOG_ID);
//            }
//        });
        setIconInDate();
        error();
        setSpinner();



//        spinner.setOnTouchListener(spinnerOnTouch);
//
//       spinnerOnTouch = new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    pay_type1 = spinner.getSelectedItem().toString();
//
//                    if(pay_type1.equals("By Cheque")) {
//
//                        cheque_no1.setVisibility(View.VISIBLE);
//                        requestFocus(cheque_no1);
//                    }
//
//                }
//                return false;
//            }
//        };



        submit.setOnClickListener(this);

//                    pay_type1 = spinner.getSelectedItem().toString();
//
//                        if(pay_type1.equals("By Cheque")) {
//
//                            cheque_no1.setVisibility(View.VISIBLE);
//                            requestFocus(cheque_no1);}
        backpress.setOnClickListener(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    /* Initialize recyclerview */
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter = new CustomerCollectionAddAdapter(CustomerCollectionAdd.this, arrayList);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(adapter);

//        banner();
//        prepareMovieData();
    }



    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.area1, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
     spinner.setAdapter(adapter);


    }

    private void error() {
        amount1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        date1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                date1_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    private void findview() {

        amount_input_layout = (TextInputLayout) findViewById(R.id.amount_input_layout);
        date1_input_layout = (TextInputLayout) findViewById(R.id.date1_input_layout);

        spinner = (Spinner) findViewById(R.id.spinner_service_type);

        amount1 = (EditText)findViewById(R.id.amount);
        date1 = (EditText)findViewById(R.id.date1);
        submit = (Button)findViewById(R.id.submit);

        backpress = (TextView)findViewById(R.id.back1);

        cheque_no1 = (EditText)findViewById(R.id.cheque);

        Typeface type = Typeface.createFromAsset(CustomerCollectionAdd.this.getAssets(),"fontawesome-webfont.ttf");
        backpress.setTypeface(type);
    }

    private void setIconInDate() {
        // TODO Auto-generated method stub
        try {
            Drawable datePickerIcon = getResources().getDrawable(
                    R.drawable.calender);

            date1.setCompoundDrawablesWithIntrinsicBounds(null, null,
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

            date1.setText(new StringBuilder()
                    .append(dDay).append("-").append(pad(dMonth + 1)).append("-").append(dYear));

        }
    };

    private static String pad(int cc) {
        if (cc >= 10)
            return String.valueOf(cc);
        else
            return "0" + String.valueOf(cc);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.back1:
                             finish();
                             break;

            case R.id.date1:

                            // TODO Auto-generated method stub
                            showDialog(DATE_DIALOG_ID);
                            break;
            case R.id.submit:


                if (validation())
                {
                    pay_type1 = spinner.getSelectedItem().toString();
                    if(pay_type1.equals("Payment Process")) {

                        Toast.makeText(CustomerCollectionAdd.this,"Please select the vaild Payment Process",Toast.LENGTH_SHORT).show();

                    }else if(pay_type1.equals("By Cheque")) {

                        cheque_no = cheque_no1.getText().toString();

                        if(cheque_no.equals("")){

                        cheque_no1.setVisibility(View.VISIBLE);
                        requestFocus(cheque_no1);
                        }else
                        {add_collection();}

                    }else {add_collection();}
                }

        }
    }

    public void getspiner() {
        pay_type1 = spinner.getSelectedItem().toString();
        if(pay_type1.equals("Payment Process")) {

            Toast.makeText(CustomerCollectionAdd.this,"Please select the vaild Payment Process",Toast.LENGTH_SHORT).show();

        }

        if(pay_type1.equals("By Cheque")) {

            cheque_no1.setVisibility(View.VISIBLE);
            requestFocus(cheque_no1);

        }else  if(pay_type1.equals("By Cash")) {

            cheque_no1.setVisibility(View.GONE);

        }
    }

    boolean validation() {
        String username = amount1.getText().toString();
        String mobile = date1.getText().toString();

        if (username.equals("")) {

            amount_input_layout.setError("Please enter a amount");
            requestFocus(amount1);
            return false;
        }

        else if(mobile.equals("")) {
            //etpassword.requestFocus();

            date1_input_layout.setError("Please enter date");
            requestFocus(date1);
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

    void add_collection()
{

        FunctionConstant.loading(CustomerCollectionAdd.this);

    page_id =+1;
    api();

    Intent intent=getIntent();
    if (intent != null) {
        customer_id = intent.getStringExtra("cust_id");

    }

    if(pay_type1.equals("By Cheque")){
        pay_type = "1";
    }else{
        pay_type = "2";
    }

    SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
    user_id=sharedPreferences.getString("id", "");
    String method="addcollection";

    amount = amount1.getText().toString();
    remark = date1.getText().toString();
//    cheque_no = cheque_no1.getText().toString();


    Call<JsonObject> call = service.addcollection(user_id,customer_id,method,pay_type,amount,cheque_no,remark);

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
                        Toast.makeText(CustomerCollectionAdd.this,"Collection Added", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CustomerCollectionAdd.this,CustomerCollectionDetails.class);
                        intent.putExtra("cust_id",customer_id);
                        startActivity(intent);

                        amount1.setText("");
                        date1.setText("");
                        finish();
                    } else {

//                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                        Toast.makeText(CustomerCollectionAdd.this, "Failed", Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CustomerCollectionAdd.this, "Total Failed", Toast.LENGTH_LONG).show();
                }
                FunctionConstant.progressDialog.dismiss();

            } else {
                Toast.makeText(CustomerCollectionAdd.this, "Server issue", Toast.LENGTH_SHORT).show();

            }
            FunctionConstant.progressDialog.dismiss();
        }

        @Override
        public void onFailure(Throwable t) {

        }
    });


}
}
