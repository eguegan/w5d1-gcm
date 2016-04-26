package com.example.admin.w5d1gcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTAG_";

    private static final String PUBLISH_KEY = "213970602985";
    private static final String SUBSCRIBER_KEY = "AIzaSyAdeoWNZMwc9unyDW2YaghXD8Fqb1LyUVM";

    private Pubnub mPubNub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPubNub = new Pubnub(PUBLISH_KEY, SUBSCRIBER_KEY);

        try {
            mPubNub.subscribe("my_channel", new Callback() {
                        @Override
                        public void connectCallback(String channel, Object message) {
                            mPubNub.publish("my_channel", "Hello from the PubNub Java SDK", new Callback() {
                            });
                        }

                        @Override
                        public void successCallback(String channel, Object message) {
                            Log.d(TAG, "Subscribe: successCallback: " + channel + " " + message);
                        }
                    }
            );
        } catch (PubnubException e) {
            Log.e(TAG, "onCreate: " + e.toString(), e);
        }
    }

    public void senNotification(View view) {
        mPubNub.publish("my_channel", "Hello world", false, new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
                Log.d(TAG, "Publish: successCallback: " + channel + " " + message);
            }

            @Override
            public void errorCallback(String channel, PubnubError error) {
                super.errorCallback(channel, error);
                Log.e(TAG, "errorCallback: " + channel + " " + error.toString());
            }
        });
    }
}
