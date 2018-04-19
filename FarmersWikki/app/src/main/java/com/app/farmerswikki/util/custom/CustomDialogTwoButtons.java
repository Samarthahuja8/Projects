package com.app.farmerswikki.util.custom;

/**
 * Created by ORBITWS19 on 24-Jul-2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;


/**
 * Created by Orbitsys on 9/21/15.
 */
public class CustomDialogTwoButtons extends Activity
{

    public static void showDialog(final Context context, final ViewPagerNotificationHandler viewPagerNotificationHandler,
                                  final String title, final String message,
                                  final String positiveButton,
                                  final String negativeButton, final NotificationResponse notificationResponse, final int position, final GenrateClassObject genrateClassObject) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.custom_dialog_two_buttons);

        TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
        Button btnPositive = (Button) dialog.findViewById(R.id.btnPositive);
        Button btnNegative = (Button) dialog.findViewById(R.id.btnNegative);

        txtTitle.setText(title);
        txtMessage.setText(message);
        btnPositive.setText(positiveButton);
        btnNegative.setText(negativeButton);

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "Positive ", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }

        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Negative ", Toast.LENGTH_SHORT).show();
                DatabaseHandler databaseHandler=new DatabaseHandler(context);
                databaseHandler.deleteContact(notificationResponse);
                OnNewsDelete onNewsDelete=viewPagerNotificationHandler;
                onNewsDelete.onNewsDelete(position);
                dialog.cancel();
            }
        });

        dialog.show();

    }



    public interface OnNewsDelete{
        public void onNewsDelete(int position);
    }

}


