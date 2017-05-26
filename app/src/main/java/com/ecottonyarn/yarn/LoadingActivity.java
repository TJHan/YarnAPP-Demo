package com.ecottonyarn.yarn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        finish();
    }

    private void init() {
        boolean browser = getIntent().getBooleanExtra("browser", false);
        String url = getIntent().getStringExtra("url");
        if (browser) {
            Intent intent = new Intent(LoadingActivity.this, BrowserActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        } else {

        }

    }
}
