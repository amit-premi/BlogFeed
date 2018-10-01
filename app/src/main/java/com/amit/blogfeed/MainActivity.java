package com.amit.blogfeed;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.amit.blogfeed.apiutils.RetrofitClient;
import com.amit.blogfeed.pojomodel.BlogModel;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Amit");

        RetrofitClient.getInstance().getBlogFeeds()
                .enqueue(new Callback<BlogModel>() {
                    @Override
                    public void onResponse(Call<BlogModel> call, Response<BlogModel> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(MainActivity.this,"Service Success: "+response.body().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BlogModel> call, Throwable t) {

                    }
                });
    }
}
