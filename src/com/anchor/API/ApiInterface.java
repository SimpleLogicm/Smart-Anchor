package com.anchor.API;

import com.anchor.model.ImagesModel;
import com.anchor.model.InvoiceResponse;
import com.anchor.model.Videoaddresponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
//            @Path("filter") String filter
//&filter_value={filter}
//imei_no={imei_no}&email={email}&customer_code={customer_code}

    @GET("invoices")
    Call<InvoiceResponse> getInvoices(
            @Query("imei_no") String imei,
            @Query("email") String email,
            @Query("customer_code") String customer_code,
            @Query("filter_value") String filter,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date
    );

    @GET("new_launches")
    Call<ImagesModel> getNewLaunches(
            @Query("imei_no") String imei


    );

    @GET("advertisements/send_advertisements")
    Call<Videoaddresponse> getVideoAdd(
            @Query("imei_no") String imei
    );

    @POST("dealer_users/registration")
    @FormUrlEncoded
    Call<Videoaddresponse> saveUser(@Field("customer_code") String dealer_code,
                                    @Field("email") String email_id,
                                    @Field("mobile_number") String mobile_number);
}
