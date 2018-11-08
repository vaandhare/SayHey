package in.indekode.sayhey.Fragments;


import in.indekode.sayhey.Notifications.MyResponse;
import in.indekode.sayhey.Notifications.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAbk4sqbo:APA91bEnXwNIwbq2PTm60rcSUEihUxJL2OK37ESMeywm-_4IjOGT5Rc1g6-cKdiFTuoUJna-jLnBNxIN1glodi0FuAhzFAPLFzx6eQxtBt3ybDLGMZPom_Krort3mEL73e1RBv7TyZng"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}