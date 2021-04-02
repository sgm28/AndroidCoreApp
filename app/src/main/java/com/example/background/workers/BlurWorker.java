package com.example.background.workers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.background.R;

public class BlurWorker extends Worker {

    public BlurWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams)

    {
        super(appContext, workerParams);

    }

    private static final String TAG = BlurWorker.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {

        Context applicationContext = getApplicationContext();

        try {

            Bitmap picture = BitmapFactory.decodeResource(
                    applicationContext.getResources(), R.drawable.test
            );

            // Blur the bitmap
            Bitmap output = WorkerUtils.blurBitmap(picture, applicationContext);

            Uri outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output);

            WorkerUtils.makeStatusNotification("Ouput is " + outputUri.toString(), applicationContext);

            return Result.success();
        } catch (Throwable throwable)
        {
            Log.e(TAG, "Error applying blur", throwable);
            return Result.failure();
        }

    }
}