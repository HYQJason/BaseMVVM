package com.xbmm.http.convert;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.xbmm.http.base.BaseResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by jason on 17/5/11
 */
public class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "CustomGsonResponseBodyC";
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();

        Log.d(TAG, "convert:============= "+response);
        try {

            Map map =    gson.fromJson(response,Map.class);
          //  BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
            if (!map.containsKey("data")){
                map.put("data",new HashMap());
            }
            Log.d(TAG, "convert: =========map====="+map.get("data"));
            String toJson = gson.toJson(map);
            Log.d(TAG, "convert: =========toJson====="+toJson);
            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;

            InputStream inputStream = new ByteArrayInputStream(toJson.getBytes());
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = gson.newJsonReader(reader);
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }




    }
}