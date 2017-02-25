package nyc.c4q.rusili.audiotube.retrofit;

import nyc.c4q.rusili.audiotube.retrofit.JSON.JSONResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceData {
    //https://www.googleapis.com/youtube/v3/videos?id=9JJmHYZQci4&key=AIzaSyDckagymH1dcK_WWeCyD908ix1OHPZbDpY&part=snippet,contentDetails

    @GET ("videos")
    Call <JSONResponse> getVideoData (@Query ("id") String id, @Query ("key") String key, @Query ("part") String part);
}
