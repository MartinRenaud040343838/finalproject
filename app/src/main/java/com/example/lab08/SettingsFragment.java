package com.example.lab08;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {


    private UserSettings settings;


    Context context = getActivity();



    private TextView tv_date;
    private Button btn_date;
    private Button btn_loadimage;

    private ListView listView;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private ImageView ivShowImage;

    private ProgressBar progressBar;

    public String userDate;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor



    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_settings, container, false);


        btn_date = root.findViewById(R.id.btn_date);
        btn_loadimage = root.findViewById(R.id.button2);
        tv_date = root.findViewById(R.id.tv_date);
        //ivShowImage = root.findViewById(R.id.imageView);
        listView = root.findViewById(R.id.listViewH);
        //progressBar=(ProgressBar)root.findViewById(R.id.progressBar);





        btn_date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Calendar kal = Calendar.getInstance();
                int year = kal.get(Calendar.YEAR);
                int month = kal.get(Calendar.MONTH);
                int day = kal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);


                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month +1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy): " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                userDate = date;
                tv_date.setText(date);

            }
        };


        // start of LOAD IMAGE button
        btn_loadimage.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {


                //Toast.makeText(getActivity(), "Fetching Nasa image download", Toast.LENGTH_SHORT).show();
                //String API_KEY = "apod?api_key=jeADHwHp60mEX1frcE913FAnV5c2FyGrA3Hmcd95";




                getNasaImages();
                saveData();




            }




                });


return root;




    } //end button call

    private void saveData() {


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int newHighScore = 0;
        editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
        editor.apply();


    }


    private void loadData() {


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);


    }


    public void getNasaImages() {

        Toast.makeText(getActivity(), "Callng getNasaImages Method", Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);


        Call<List<GetSetApi>> call = api.getNasaImages();


        call.enqueue(new Callback<List<GetSetApi>>() {
            @Override




            public void onResponse(@NonNull Call<List<GetSetApi>> call, @NonNull Response<List<GetSetApi>> response) {

                List<GetSetApi> nasaList = response.body();



                //Create string array for the ListView
                String[] nasaimages = new String[nasaList.size()];

                //looping through all the JSON data and inserting the dates inside the string array
                for(int i = 0; i < nasaList.size(); i++) {
                    nasaimages[i] = nasaList.get(i).getDate();

                }

                //displaying the string array into listview
                listView.setAdapter(
                        new ArrayAdapter<String>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                nasaimages)
                );



            }








            @Override
            public void onFailure(Call<List<GetSetApi>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }


    private void loadSharedPreferences()
    {

    }




}

