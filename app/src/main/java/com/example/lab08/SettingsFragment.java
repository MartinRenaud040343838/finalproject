package com.example.lab08;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {



    private TextView tv_date;
    private Button btn_date;
    private Button btn_loadimage;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private ImageView ivShowImage;




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
        ivShowImage = root.findViewById(R.id.imageView);

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
        tv_date.setText(date);

    }
};


        // start of LOAD IMAGE button
        btn_loadimage.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                //Toast.makeText(getActivity(), "Image button test", Toast.LENGTH_SHORT).show();

                new ImageDownloader().execute("https://www.nasa.gov/wp-content/uploads/2023/09/53210646183_c08c1305c8_o.jpg");
            }

            class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

                HttpURLConnection httpURLConnection;

                @Override
                protected Bitmap doInBackground(String... strings) {

                    try {
                        URL url = new URL(strings[0]);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        Bitmap temp = BitmapFactory.decodeStream(inputStream);
                        return temp;

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        //httpURLConnection.disconnect();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if(bitmap != null) {
                        ivShowImage.setImageBitmap(bitmap);
                        Toast.makeText(getActivity(), "Image download successful", Toast.LENGTH_SHORT).show();
                     } else {
                        Toast.makeText(getActivity(), "Image download ERROR", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }
            }



        }); //end of LOAD IMAGE button





        return root;

    }





}