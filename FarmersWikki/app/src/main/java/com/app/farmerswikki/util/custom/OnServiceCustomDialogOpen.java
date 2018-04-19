package com.app.farmerswikki.util.custom;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.model.dashboard.view.activity.DashBoardActivity;
import com.app.farmerswikki.util.data.UtilityOfActivity;

import org.w3c.dom.Text;

/**
 * Created by Orbitsys on 9/21/15.
 */
public class OnServiceCustomDialogOpen extends Activity {


    TextView txtTitle, txtMessage;
    Button btnPositive, btnNegative;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_two_buttons);
        ViewPagerNotificationHandler.isNotificationFragmentActive=true;
        setWindowParams();
        setView();
        txtTitle.setText(getResources().getString(R.string.new_notification_title));
        txtMessage.setText(getResources().getString(R.string.new_notification_subject));
        btnPositive.setText(getResources().getString(R.string.leave));
        btnNegative.setText(getResources().getString(R.string.refresh));

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OnServiceCustomDialogOpen.this,DashBoardActivity.class);
                intent.putExtra("isNotificationGenerated", "Y");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setWindowParams() {

        WindowManager.LayoutParams lparams = new WindowManager.LayoutParams();

        lparams.dimAmount = 0;

        lparams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ,
                PixelFormat.TRANSPARENT);


        lparams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |

                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;


        getWindow().setAttributes(lparams);
    }


    public void setView() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        btnPositive = (Button) findViewById(R.id.btnPositive);
        btnNegative = (Button) findViewById(R.id.btnNegative);
    }




}

