package sriyaan.ac;

import android.content.SharedPreferences;
import android.support.v7.widget.DefaultItemAnimator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Dipesh on 12-Jul-16.
 */



public class ExpandableListDataPump {

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


    public  HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();



        List<String> First = new ArrayList<String>();
        First.add("Customer View1");
        First.add("Machine Details1");


        List<String> Second = new ArrayList<String>();
        Second.add("Customer View2");
        Second.add("Machine Details2");


        List<String> Third = new ArrayList<String>();
        Third.add("Customer View3");
        Third.add("Machine Details3");

        List<String> Fourth = new ArrayList<String>();
        Fourth.add("Customer View4");
        Fourth.add("Machine Details4");

        expandableListDetail.put("Customer Fourth", Fourth);
        expandableListDetail.put("Customer Third", Third);
        expandableListDetail.put("Customer Second", Second);
        expandableListDetail.put("Customer First", First);
        return expandableListDetail;

    }




}
