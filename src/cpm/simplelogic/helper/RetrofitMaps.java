package cpm.simplelogic.helper;

/**
 * Created by admin on 30-01-2018.
 */

import com.anchor.model.DistanceData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by navneet on 17/7/16.
 */
public interface RetrofitMaps {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("api/directions/json?key=AIzaSyCmTw_j7_8d6BzZuBWcA_K8_97ykIQqoaE")
    Call<DistanceData> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);

}
