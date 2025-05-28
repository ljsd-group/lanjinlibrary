package com.lanji.mylibrary.network;

import static com.lanji.mylibrary.utils.Constant.AuthCodeHead;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lanji.mylibrary.dialog.AdvDialog;
import com.lanji.mylibrary.utils.Method;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainDialogRequest {

    private final String url;
    private final Context context;
    private final Object object;
    private final String AuthCode;

    public MainDialogRequest(Builder builder, Context context, Object object, String authCode) {
        this.url = builder.url;
        this.context = context;
        this.object = object;
        AuthCode = authCode;
    }

    public static class Builder {

        private String url;
        private Context context;
        private Object object;
        private  String AuthCode;

        public Builder setAuthCode(String AuthCode) {
            this.AuthCode = AuthCode;
            return this;

        }
        public Builder setContext(Context context) {
            this.context = context;
            return this;

        }

        public Builder setBody(Object object) {
            this.object = object;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;

        }


        public MainDialogRequest build() {

            return new MainDialogRequest(this, context, object,AuthCode);
        }

    }


    public void startRequest() {
        Api apiService = createAPI();
        Call<AdversData> call = apiService.getCategories(url, object);
        call.enqueue(new Callback<AdversData>() {
            @Override
            public void onResponse(Call<AdversData> call, retrofit2.Response<AdversData> response) {
                if (response.isSuccessful()) {
                    AdversData responseHome = response.body();
                    if (responseHome != null && responseHome.code == 200) {
                        if (responseHome.list != null && !responseHome.list.isEmpty()) {
                            List<AdversData.Data> list = responseHome.list;
                            if (list.get(0).showType == 0) {
                                AdvDialog dialog = new AdvDialog(context);
                                dialog.show();
                                dialog.setImage(list.get(0).imgUrl,
                                        list.get(0).jumpUrl,
                                        list.get(0).uploadType);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AdversData> call, Throwable t) {

            }
        });
    }

    private Api createAPI() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        HttpUrl url = request.url();
                        HttpUrl.Builder builder = url.newBuilder();
                        requestBuilder.url(builder.build())
                                .method(request.method(), request.body())
                                .addHeader("version", Method.getVersionName(context))
                                .addHeader("phoneModel", "android")
                                .addHeader("Content-Type", "application/json")
                                .addHeader("deviceNumber", Method.getAndroidId(context))
                                .addHeader("Token",Method.encrypt())
                                .addHeader("countryCode",Method.getCountryCode(context))
                                .addHeader("Auth",AuthCodeHead+AuthCode)
                                .addHeader("appName",Method.getAppName(context));
//                        if (mMapHeader != null) {
//                            for (Map.Entry<String, String> entry : mMapHeader.entrySet()) {
//                                String key = entry.getKey();
//                                String value = entry.getValue();
//                                LogUtils.i("request header:" + key + ":" + value);
//                                requestBuilder.addHeader(entry.getKey(), entry.getValue());
//                            }
//                        }
                        return chain.proceed(requestBuilder.build());
                    }
                })
                .cache(null)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://auth0.ljsdstage.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(Api.class);

    }
}
