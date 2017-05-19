package gr.ihu.lab.ihuweather_01;

import android.icu.text.SimpleDateFormat;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*** Created by cathr on 19/5/2017. ***/

public class WeatherParser {

  private static final String LOG_TAG = WeatherParser.class.getName();


    public static String[] parseWeatherFromJSON(int numDays, String jsonWeather) throws JSONException {


        String[] results = new String[numDays];


        String OWM_List = "list";
        String OWM_Weather = "weather";
        String OWM_Temp = "temp";
        String OWM_Max = "max";
        String OWM_Min = "min";
        String OWM_description = "main";



        JSONObject forecastObj = new JSONObject(jsonWeather);

        JSONArray weatherArray = forecastObj.getJSONArray(OWM_List);

        Time dayTime = new Time(); //android.text.format.Time
        dayTime.setToNow();
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
        dayTime = new Time();

        for(int i=0; i<numDays; i++){
            String day, description, minmax;


            JSONObject dayForecast = weatherArray.getJSONObject(i);
            long dateTime;
            dateTime = dayTime.setJulianDay(julianStartDay + i);
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd, MMM");
            day = sdf.format(dateTime);

            //Log.i(LOG_TAG,day);

            JSONObject o = dayForecast.getJSONArray(OWM_Weather).getJSONObject(0);
            description = o.getString(OWM_description);

            JSONObject tempObj = dayForecast.getJSONObject(OWM_Temp);
            double max = tempObj.getDouble(OWM_Max);
            double min = tempObj.getDouble(OWM_Min);


            minmax = Math.round(min)+"-"+Math.round(max);

            results[i] = day + " " + description+ " " +minmax;

        }

        for(int i=0; i<numDays; i++){
            Log.i(LOG_TAG,"Forecast Entry:" + results[i]);


        }


        return results;
    }





















}
