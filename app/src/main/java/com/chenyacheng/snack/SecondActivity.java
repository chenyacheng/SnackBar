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
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button btnSecondClose = findViewById(R.id.btn_second_close);
        btnSecondClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarHelper.setResult(SecondActivity.this, 11, "已关闭SecondActivity页面");
                finish();
            }
        });
    }
}
