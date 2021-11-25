package com.chenyacheng.snack;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chenyacheng.snackbar.SnackBar;
import com.chenyacheng.snackbar.SnackBarBuilder;
import com.chenyacheng.snackbar.SnackBarHelper;

/**
 * @author chenyacheng
 * @date 2020/03/31
 */
public class SnackBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        final Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarBuilder.getInstance().builder(button1, R.layout.snack_bar, 0, "短--视图view", SnackBar.LENGTH_SHORT);
            }
        });

        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarBuilder.getInstance().builder(SnackBarActivity.this, R.layout.snack_bar, 0, "长--activity", SnackBar.LENGTH_LONG);
            }
        });

        final Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SnackBarActivity.this, R.style.base_dialog);
                dialog.setContentView(R.layout.dialog);
                Button button = dialog.findViewById(R.id.btn_dialog);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackBarBuilder.getInstance().builder(dialog, R.layout.snack_bar, 0, "短--dialog", SnackBar.LENGTH_SHORT);
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        SnackBarBuilder.getInstance().hideView();
                    }
                });
                dialog.show();
            }
        });

        final Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popWindows = View.inflate(SnackBarActivity.this, R.layout.snack_pop_windows, null);
                final PopupWindow popupWindow = new PopupWindow(popWindows, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.update();
                popupWindow.setWidth(button4.getWidth());
                popupWindow.showAsDropDown(button4, 1, 1);
                TextView textView = popWindows.findViewById(R.id.textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackBarBuilder.getInstance().builder(popupWindow, R.layout.snack_bar, 0, "长--popupWindow", SnackBar.LENGTH_LONG);
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        SnackBarBuilder.getInstance().hideView();
                    }
                });
            }
        });

        final Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarHelper.startActivity(SnackBarActivity.this, new Intent(SnackBarActivity.this, FirstActivity.class));
            }
        });

        final Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarHelper.startActivityForResult(SnackBarActivity.this, new Intent(SnackBarActivity.this, SecondActivity.class), 10);
            }
        });

        final Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarBuilder.getInstance().builder(button7, R.layout.custom_snack_bar, R.drawable.right_icon, "自定义视图", SnackBar.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == 11) {
                    Log.v("resultCode", "11");
                }
                break;
            default:
                break;
        }
    }
}
