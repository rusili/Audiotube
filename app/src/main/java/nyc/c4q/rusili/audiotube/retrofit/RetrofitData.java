package nyc.c4q.rusili.audiotube.retrofit;

import android.util.Log;

import nyc.c4q.rusili.audiotube.retrofit.JSON.JSONResponse;
import nyc.c4q.rusili.audiotube.service.ForegroundService;
import nyc.c4q.rusili.audiotube.utility.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitData {
    //https://www.googleapis.com/youtube/v3/videos?id=9JJmHYZQci4&key=AIzaSyDckagymH1dcK_WWeCyD908ix1OHPZbDpY&part=snippet,contentDetails

    private String TAG = "RetrofitData: ";
    private final String key = Constants.DEVELOPER_KEY;
    private String part = "snippet,contentDetails";

    public RetrofitData () {
    }

    public void getInfo (final String videoIDParam, final ForegroundService foregroundService) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_YOUTUBE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceData serviceData = retrofit.create(ServiceData.class);
        Call <JSONResponse> getVideoData = serviceData.getVideoData(videoIDParam, key, part);
        getVideoData.enqueue(new Callback <JSONResponse>() {
            @Override
            public void onResponse (Call <JSONResponse> call, Response <JSONResponse> response) {
                Log.d(TAG + "Url", call.request().url().toString());
                JSONResponse jsonResponse = response.body();
                foregroundService.onNetworkResponse(videoIDParam, jsonResponse);
                Log.d(TAG + "Response", jsonResponse.toString());
            }

            @Override
            public void onFailure (Call <JSONResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}
