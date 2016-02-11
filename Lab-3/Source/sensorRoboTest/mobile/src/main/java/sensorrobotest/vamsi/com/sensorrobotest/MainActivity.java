package sensorrobotest.vamsi.com.sensorrobotest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    SensorManager sensorManager;
    Sensor mStepCounter, mHeartActivity;
    TextView heart, step;

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mHeartActivity = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mHeartActivity, SensorManager.SENSOR_DELAY_NORMAL);

        heart = (TextView) findViewById(R.id.heart);
        step = (TextView) findViewById(R.id.steps);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == mStepCounter)
            step.setText("" + event.values[0]);
        if (event.sensor == mHeartActivity)
            heart.setText("" + event.values[0]);
            TextView x = (TextView) findViewById(R.id.heart);
            String Sharex=heart.getText().toString();



            int notificationId = 200;
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MainActivity.this)
                    .setSmallIcon(R.drawable.notificationicon)
                    .setContentTitle("Heart Pulse Rate of Patient A124:")
                    .setContentText(Sharex);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);

            notificationManagerCompat.notify(notificationId, notificationBuilder.build());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mHeartActivity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
