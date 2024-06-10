package com.example.medicineapp.networks.interceptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;

import java.io.IOException;
import android.os.Handler;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HandleError implements Interceptor {
    private Context context;
    public HandleError(Context context) {
        this.context = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            handleApiError(response);
        }

        return response;
    }

    private void handleApiError(Response response) {
        if (response.code() == 404) {
            showDialog("There aren't any consequences for your actions");
        } else {
            showDialog("Something went wrong" );
        }
    }

    private void showDialog(String message) {
        Log.e("handle error", message);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Not Found")
                        .setMessage(message) // Set the dynamic message
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle OK button click
                            }
                        });
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Handle Cancel button click
//                            }
//                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
