package com.example.admin.customtimepickerdemo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView timeTextView;
    private TimePickerDialog timePickerDialog;
    private Button timeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find our View instances
        timeTextView = findViewById(R.id.time_textview);
        timeButton = findViewById(R.id.time_button);


        // Show a timepicker when the timeButton is clicked
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                /*
                It is recommended to always create a new instance whenever you need to show a Dialog.
                The sample app is reusing them because it is useful when looking for regressions
                during testing
                 */
//                if (timePickerDialog == null) {
//                    timePickerDialog = TimePickerDialog.newInstance(
//                            MainActivity.this,
//                            now.get(Calendar.HOUR_OF_DAY),
//                            now.get(Calendar.MINUTE),
//                            false
//                    );
//                } else {
//                    timePickerDialog.initialize(
//                            MainActivity.this,
//                            now.get(Calendar.HOUR_OF_DAY),
//                            now.get(Calendar.MINUTE),
//                            now.get(Calendar.SECOND),
//                            false
//                    );
//                }

                timePickerDialog = TimePickerDialog.newInstance(
                        MainActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );


                timePickerDialog.setThemeDark(false);
                timePickerDialog.setTitle("TimePicker Title");
                timePickerDialog.setTimeInterval(1, 30, 60);
                timePickerDialog.setAccentColor(Color.parseColor("#9C27B0"));

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                timePickerDialog.show(getFragmentManager(), "Timepickerdialog");
            }
        });
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        updateEndTime(hourOfDay,minute);
    }

    private void updateEndTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(pad(hours)).append(':')
                .append(pad(mins)).append(" ").append(timeSet).toString();

        timeTextView.setText(aTime);
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        if (tpd != null) tpd.setOnTimeSetListener(this);
    }

}
