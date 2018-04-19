package com.app.farmerswikki.model.dashboard.view.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.farmerswikki.R;

import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.model.map.view.fragment.FragmentMap;
import com.app.farmerswikki.model.nav_drawer.adapter.RecyclerViewDataSheetOptionsAdapter;
import com.app.farmerswikki.model.nav_drawer.view.fragment.NavDrawerFragmentDataSheetOptions;
import com.app.farmerswikki.util.custom.TextViewBold;
import com.app.farmerswikki.util.custom.TextViewRegular;
import com.app.farmerswikki.util.data.AppCommonObject;
import com.app.farmerswikki.util.data.PrefrenceFile;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;
import com.google.firebase.messaging.FirebaseMessaging;


public class DashBoardActivity extends AppCompatActivity implements RecyclerViewDataSheetOptionsAdapter.OnClickNavItems {

    DrawerLayout drawerLayout;
    View view;
    Toolbar toolbar;
    ImageView image_nav;
    LinearLayout nav_linear_layout;
    RelativeLayout relative_layout;
    TextView textToolName;
    Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) DashBoardActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_main, null);
        setContentView(view);
        initialiseFCM();
        //Typeface typeface= Typeface.createFromAsset(getAssets(),"FFF_Tusj.ttf");
        relative_layout = (RelativeLayout) findViewById(R.id.relative_layout);
        textToolName = (TextView) findViewById(R.id.textToolName);
        textToolName.setText(AppCommonObject.getInstance().getUserName());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        image_nav = (ImageView) findViewById(R.id.image_nav);
        nav_linear_layout = (LinearLayout) findViewById(R.id.nav_linear_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout.requestLayout();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        nav_linear_layout.bringToFront();
        typeface= Typeface.createFromAsset(getAssets(),"alice_reg.ttf");


        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        setToolbarDataDescription();
        image_nav.setColorFilter(ContextCompat.getColor(DashBoardActivity.this, R.color.bluish_brown), PorterDuff.Mode.SRC_ATOP);

        image_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animation);

                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)&&PrefrenceFile.getInstance().getString("isLogin").equalsIgnoreCase("Y")) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    drawerLayout.requestLayout();
                    nav_linear_layout.bringToFront();
                    image_nav.setRotation(90);
                    changeNotificationCount(drawerLayout);
                } else if(PrefrenceFile.getInstance().getString("isLogin").equalsIgnoreCase("Y")){
                    image_nav.setRotation(180);
                    drawerLayout.closeDrawers();

                }
                else if(PrefrenceFile.getInstance().getString("isLogin").equalsIgnoreCase("N")){
                    showSnackBar("SignIn to Enjoy the Application");
                    UtilityOfActivity.manageHandler(DashBoardActivity.this,800);
                }
            }
        });


            if (getIntent().getExtras()!= null) {
                if (getIntent().getExtras().getString("isNotificationGenerated") != null) {
                    {
                        Fragment fragment = new ViewPagerNotificationHandler();
                        UtilityOfActivity.moveFragment(fragment, "viewpagernotification", R.id.container, DashBoardActivity.this);
                    }
                }

            }
            else {
                UtilityOfActivity.moveFragment(new FragmentMap(), "fragmentMap", R.id.container, DashBoardActivity.this);
            }


    }


    public void setToolbarDataDescription(){
      UtilityOfActivity.moveFragment(new NavDrawerFragmentDataSheetOptions(),"FragmentNavDrawer", R.id.nav_frame_container, DashBoardActivity.this);

    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        if (count > 0) {
            if (manager.getBackStackEntryAt(count - 1).getName().equalsIgnoreCase("fragmentMap")) {
                if(!checkIfNavOpen()){
                    finish();
                }
            } else if (manager.getBackStackEntryAt(count - 1).getName().equalsIgnoreCase("viewpagernotification")) {
                if(!checkIfNavOpen()){
                    if(PrefrenceFile.getInstance().getString("isLogin").equalsIgnoreCase("Y")) {
                        UtilityOfActivity.moveFragment(new FragmentMap(), "fragmentMap", R.id.container, DashBoardActivity.this);
                    }
                    else if(PrefrenceFile.getInstance().getString("isLogin").equalsIgnoreCase("N")){
                        showSnackBar("SignIn to Enjoy the Application");
                        UtilityOfActivity.manageHandler(DashBoardActivity.this,800);
                    }
                }
            }
            else {
                if(!checkIfNavOpen()) {
                    String tag = manager.getBackStackEntryAt(count - 1).getName();
                    manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
           //     manager.popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }

    public boolean checkIfNavOpen(){
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            image_nav.setRotation(180);
            drawerLayout.closeDrawers();
            return true;
        }
        else{
            return false;
        }





    }

    @Override
    public void onNavItemClick(Context context) {
        ((DashBoardActivity)context).checkIfNavOpen();
    }

    //String json = UtilityOfActivity.loadJSONFromAsset("latlnglist.json", DashBoardActivity.this);
    //latLngModel = new GsonBuilder().create().fromJson(json, LatLngModel.class);


   public void initialiseFCM(){
       FirebaseMessaging.getInstance().subscribeToTopic("AgricultureNewsUpdates");
   }


    public void showSnackBar(String msg)
    {
        Snackbar snackbar = Snackbar
                .make(relative_layout, msg, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(DashBoardActivity.this,R.color.bluish_brown));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(DashBoardActivity.this,R.color.white));
        textView.setTypeface(typeface);
        snackbar.show();
    }


    public void changeNotificationCount(DrawerLayout drawerLayout){

        RecyclerView recyclerView=(RecyclerView) drawerLayout.findViewById(R.id.recyclerview);

        for(int count=0;count<recyclerView.getChildCount();count++){
            if(recyclerView.getChildAt(count) instanceof LinearLayout) {
                LinearLayout linearLayout = ((LinearLayout) recyclerView.getChildAt(count));
                if(((TextViewRegular)linearLayout.findViewById(R.id.item)).getText().toString().equalsIgnoreCase("News Updates")){
                    DatabaseHandler db=new DatabaseHandler(DashBoardActivity.this);
                    ((TextViewBold)linearLayout.findViewById(R.id.notificationCountTv)).setText(String.valueOf(db.getNotificationsCount()));
                }
            }
        }
    }

}