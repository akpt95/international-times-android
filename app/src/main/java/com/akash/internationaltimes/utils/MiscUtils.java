package com.akash.internationaltimes.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MiscUtils {
    private static final String TAG = MiscUtils.class.getSimpleName();

    public static String loadJSONFromAsset(Context context, String filePath) {
        Log.v(TAG, " In loadJSONFromAsset()");
        String json = null;
        if (filePath == null) {
            return json;
        }
        try {
            InputStream inputStream = context.getResources().getAssets().open(filePath);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.e(TAG, " In loadJSONFromAsset() - Exception : "+ex);
            return null;
        }
        return json;
    }

    @Nullable
    public static <T> Object convertToModel(@Nullable String jsonString, Class<T> tClass) throws
            IOException {
        Log.v(TAG, " In convertToModel()");

        if (!TextUtils.isEmpty(jsonString)) {

            Gson gson = new Gson();
            return gson.fromJson(jsonString, tClass);

        }
        return null;
    }

    public static void hideKeyboard(Context context, View field) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
        }
    }
}
