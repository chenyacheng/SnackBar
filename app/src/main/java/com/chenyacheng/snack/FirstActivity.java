package com.chenyacheng.snack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chenyacheng.snackbar.SnackBarHelper;

/**
 * @author Administrator
 * @date 2019/07/12
 */
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button btnFirstClose = findViewById(R.id.btn_first_close);
        btnFirstClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarHelper.finish(FirstActivity.this, "已关闭FirstActivity页面");
            }
        });
    }
}
