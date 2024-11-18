package edu.uncc.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.databinding.ForecastListItemBinding;
import edu.uncc.weatherapp.databinding.FragmentWeatherForecastBinding;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.Forecast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WeatherForecastFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private final String TAG = "forecast";
    private City mCity;
    private final OkHttpClient client = new OkHttpClient();
    String forecastUrl;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentWeatherForecastBinding binding;
    ArrayList<Forecast> mForecasts = new ArrayList<>();
    ForecastsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }

        Request request1 = new Request.Builder()
                .url("https://api.weather.gov/points/" + mCity.getLat() + ","+ mCity.getLng())
                .build();

        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONObject properties = json.getJSONObject("properties");
                        forecastUrl = properties.getString("forecast");
                        Log.d(TAG, forecastUrl);

                        Request request2 = new Request.Builder()
                                .url(forecastUrl)
                                .build();

                        client.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                if(response.isSuccessful()) {
                                    try {
                                        JSONObject json = new JSONObject(response.body().string());
                                        JSONObject properties = json.getJSONObject("properties");
                                        JSONArray forecasts = properties.getJSONArray("periods");

                                        for(int i = 0; i < forecasts.length(); i++){
                                            JSONObject forecastJsonObject = forecasts.getJSONObject(i);
                                            Forecast forecast = new Forecast();
                                            forecast.setStartTime(forecastJsonObject.getString("startTime"));
                                            forecast.setTemperature(forecastJsonObject.getInt("temperature"));
                                            JSONObject precipitation = forecastJsonObject.getJSONObject("probabilityOfPrecipitation");
                                            if (!precipitation.isNull("value")) {
                                                forecast.setHumidity(precipitation.getInt("value"));
                                            } else {
                                                forecast.setHumidity(0);
                                            }
                                            forecast.setWindSpeed(forecastJsonObject.getString("windSpeed"));
                                            forecast.setShortForecast(forecastJsonObject.getString("shortForecast"));
                                            forecast.setIconUrl(forecastJsonObject.getString("icon"));
                                            mForecasts.add(forecast);
                                            if(getActivity() != null){
                                                getActivity().runOnUiThread(()->adapter.notifyDataSetChanged());
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ResponseBody responseBody = response.body();
                                    String body = responseBody.string();
                                    Log.d(TAG, "onResponse: " + body);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d(TAG, "onResponse: " + body);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ForecastsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.textViewCityName.setText(mCity.toString());
    }

    class ForecastsAdapter extends RecyclerView.Adapter<ForecastsAdapter.ForecastsViewHolder> {
        @NonNull
        @Override
        public ForecastsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForecastListItemBinding itemBinding = ForecastListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ForecastsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastsViewHolder holder, int position) {
            holder.setupUI(mForecasts.get(position));
        }

        @Override
        public int getItemCount() {
            return mForecasts.size();
        }

        class ForecastsViewHolder extends RecyclerView.ViewHolder {
            ForecastListItemBinding itemBinding;
            Forecast mForecast;

            public ForecastsViewHolder(ForecastListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(Forecast forecast){
                mForecast = forecast;
                itemBinding.textViewDateTime.setText(mForecast.getStartTime());
                itemBinding.textViewTemperature.setText(mForecast.getTemperature() + " F");
                itemBinding.textViewHumidity.setText("Precipitation: " + mForecast.getHumidity() + "%");
                itemBinding.textViewWindSpeed.setText("Wind Speed: " + mForecast.getWindSpeed());
                itemBinding.textViewForecast.setText(mForecast.getShortForecast());
                Picasso.get().load(mForecast.getIconUrl()).into(itemBinding.imageView);
            }
        }
    }
}