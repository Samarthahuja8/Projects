package com.app.farmerswikki.util.custom;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.app.farmerswikki.model.map.pojo.LatLngModel;
import com.app.farmerswikki.R;
import com.app.farmerswikki.model.map.view.fragment.FragmentMap;
import com.app.farmerswikki.model.state_wise_info.pojo.StateWiseDataResponse;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin1 on 7/28/2016.
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<LatLngModel.StateLatLngList> resultList;
    Typeface myTypeface;

    public AutoCompleteAdapter(Context context, List<LatLngModel.StateLatLngList> objects) {
        mContext = context;
        resultList = objects;
        filter = new FilterClass();
        myTypeface = Typeface.createFromAsset(mContext.getAssets(), "alice_reg.ttf");
    }


    @Override
    public int getCount() {
        if (resultList != null)
            return resultList.size();
        else
            return 0;
    }

    @Override
    public LatLngModel.StateLatLngList getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.auto_complete_text_view_adapter, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.locationName)).setText(getItem(position).name);
        ((TextView) convertView.findViewById(R.id.locationName)).setTypeface(myTypeface);
        ((TextView) convertView.findViewById(R.id.capital)).setText(getItem(position).capital);
        ((TextView) convertView.findViewById(R.id.capital)).setTypeface(myTypeface);


        return convertView;

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private FilterClass filter;
    List<LatLngModel.StateLatLngList> orig;

    public class FilterClass extends Filter {
        public FilterClass() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults oReturn = new FilterResults();
            ArrayList<LatLngModel.StateLatLngList> results = new ArrayList<LatLngModel.StateLatLngList>();
            if (orig == null)
                orig = resultList;

            if (constraint != null) {
                if (orig != null && orig.size() > 0) {
                    for (LatLngModel.StateLatLngList resultList : orig) {
                        String containesValue = String.valueOf(constraint).toLowerCase();
                        String locationName = resultList.name.toLowerCase();
                        String capital = resultList.capital.toLowerCase();
                        if (locationName.contains(containesValue) || capital.contains(containesValue)) {
                            results.add(resultList);
                        }

                    }
                }
                oReturn.values = results;
            }
            return oReturn;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            resultList = (ArrayList<LatLngModel.StateLatLngList>) results.values;
            notifyDataSetChanged();
        }
    }



}


