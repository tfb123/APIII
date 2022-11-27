package com.example.apiii.Utilities;

import android.util.Log;

import com.example.apiii.parsers.FloatRatesXmlParser;
import com.example.apiii.parsers.MeteoLtJsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiDataReader {
    public static String getValuesFromApi(String apiCode) throws IOException{
        InputStream apiContentStream = null;
        String result = "";
        try{
            switch ( apiCode){
                case Constants.METEOLT_API_URL:
                    Log.e("efg", "here1");
                    apiContentStream = downloadUrlContent(Constants.METEOLT_API_URL);
                    Log.e("efg", "here2");
                    result = MeteoLtJsonParser.getKaunasWeatherForecast(apiContentStream);
                    Log.e("efg", "here3");

                    break;
                case Constants.FLOARATES_API_URL:
                    apiContentStream = downloadUrlContent(Constants.FLOARATES_API_URL);
                    result = FloatRatesXmlParser.getCurrencyRatesBaseUsd(apiContentStream);
                    break;
                default:
            }
        }
        finally {
            if (apiContentStream != null){
                apiContentStream.close();
            }
        }
        return result;
    }


    private static InputStream downloadUrlContent(String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}