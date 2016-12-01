package sriyaan.ac;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Dipesh on 14-Jul-16.
 */
public interface UserApi {


    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> login(@Field("email") String username,
                           @Field("password") String password,
                           @Field("method") String method

    );


    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getallcustomer(

            @Field("user_id") String user_id,
            @Field("method") String method
//            @Field("page_id") int page_id
    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getallcustomer1(

            @Field("user_id") String user_id,
            @Field("method") String method

    );


    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getallmachine(

            @Field("user_id") String user_id,
            @Field("method") String method,
            @Field("cust_id") String cust_id
//            @Field("page_id") int page_id
    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> addcustomer(
                    @Field("cust_name") String cust_name,
                    @Field("cust_mobile_no") String cust_mobile_no,
                    @Field("cust_email_id") String cust_email_id,
                    @Field("cust_city") String cust_city,
                    @Field("cust_area") String cust_area,
                    @Field("cust_address") String cust_address,
                    @Field("method") String method,
                    @Field("user_id") String user_id
            );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> editcustomer(
            @Field("cust_name") String cust_name,
            @Field("cust_mobile_no") String cust_mobile_no,
            @Field("cust_email_id") String cust_email_id,
            @Field("cust_city") String cust_city,
            @Field("cust_area") String cust_area,
            @Field("cust_address") String cust_address,
            @Field("method") String method,
            @Field("user_id") String user_id,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> addmachinedetails(
            @Field("machine_name") String machine_name,
            @Field("machine_type") String machine_type,
            @Field("making_date") String making_date,
            @Field("machine_details") String machine_details,
            @Field("method") String method,
            @Field("cust_id") String cust_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> addservice(
            @Field("service_type") String machine_type,
            @Field("service_schedule") String making_date,
            @Field("method") String machine_details,
            @Field("customer_id") String method,
            @Field("user_id") String cust_id,
            @Field("machine_id") String user_id
    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> addcollection(

            @Field("user_id") String user_id,
            @Field("customer_id") String customer_id,
            @Field("method") String method,
            @Field("pay_type") String pay_type,
            @Field("amount") String amount,
            @Field("cheque_no") String cheque_no,
            @Field("remark") String remark

    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getallcollection(

            @Field("user_id") String user_id,
            @Field("customer_id") String customer_id,
            @Field("method") String method
//            @Field("page_id") int page_id
    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getcustomerbyid(

            @Field("id") String id,
            @Field("method") String method

    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getallservice(

            @Field("machine_id") String machine_id,
            @Field("method") String method

    );


    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> updateservice(

            @Field("machine_id") String machine_id,
            @Field("method") String method,
            @Field("schedule_id") String schedule_id,
            @Field("status_check") String status_check

    );


    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getcustmashineservice(
            @Field("method")String method,
            @Field("id") String id,
            @Field("cust_id") String cust_id

    );


    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> updatecustmachineservice(
            @Field("machine_name") String machine_name,
            @Field("machine_type") String machine_type,
            @Field("making_date") String making_date,
            @Field("machine_details") String machine_details,
            @Field("service_type") String service_type,
            @Field("service_schedule") String service_schedule,
            @Field("method") String method,
            @Field("cust_id") String cust_id,
            @Field("user_id") String user_id,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> getuserdata(

            @Field("id") String id,
            @Field("method") String method

    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> edituser(

            @Field("id") String id,
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("firm_name") String firm_name,
            @Field("address") String address,
            @Field("method") String method

    );

    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> edituserpassword(

            @Field("id") String id,
            @Field("password") String password,
            @Field("oldpassword") String oldpassword,
            @Field("method") String method

    );


    @FormUrlEncoded
    @POST("webservices.php")
    Call<JsonObject> adduser(

            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password,
            @Field("firm_name") String firm_name,
            @Field("address") String address,
            @Field("method") String method

    );

}
