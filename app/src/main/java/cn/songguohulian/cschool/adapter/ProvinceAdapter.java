package cn.songguohulian.cschool.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;






import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.mode.CityModel;
import cn.songguohulian.cschool.mode.MSharePreferences;
import cn.songguohulian.cschool.mode.ProvinceModel;
import cn.songguohulian.cschool.service.Tools;

public class ProvinceAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
    private int index = -1;
    private int selectedIndex = 0;
    private boolean flag = true;
    // 用于记录每个Radiobutton的状态，并保证只可选一个
    private HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    private List<ProvinceModel> provinces;

    private List<CityModel> cities;
    private boolean defaultSelectedPosition = true;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public ProvinceAdapter(Context context, List<ProvinceModel> provinces) {
        this.context = context;
        this.provinces = provinces;

        this.mInflater = LayoutInflater.from(context);

    }

    public ProvinceAdapter(Context context, List<CityModel> cities, boolean flag) {
        this.context = context;
        this.cities = cities;
        this.flag = flag;
        this.mInflater = LayoutInflater.from(context);

    }

    // public void selectedIndex(int position){
    // this.selectedIndex = position;
    // notifyDataSetChanged();
    // }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return flag ? provinces.size() : cities.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return flag ? provinces.get(position) : cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        MSharePreferences sharePreferences = MSharePreferences
                .getInstance();
        sharePreferences.getSharedPreferences(context);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.province_adapter, null);
            // viewHolder.ProvinceRadioGroup = (RadioGroup)
            // convertView.findViewById(R.id.ProinceParent);
            viewHolder.ProvinceItem = (RadioButton) convertView
                    .findViewById(R.id.province_choice);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (flag) {
            viewHolder.ProvinceItem.setText(provinces.get(position).getName());
        } else {
            viewHolder.ProvinceItem.setText(cities.get(position).getName());
        }

        viewHolder.ProvinceItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一个被选中,但是这个方法每次都要便利，不是太好，以后再找个适合的
                for (String key : states.keySet()) {
                    states.put(key, false);

                }
                defaultSelectedPosition = false;
                states.put(String.valueOf(position),
                        viewHolder.ProvinceItem.isChecked());
                ProvinceAdapter.this.notifyDataSetChanged();

            }
        });

        boolean res = false;
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else {

            selectedIndex = position;
            res = true;
        }
        viewHolder.ProvinceItem.setChecked(res);
        // if(selectedIndex == position){
        // viewHolder.ProvinceItem.setChecked(true);
        //
        // }else{
        // viewHolder.ProvinceItem.setChecked(false);
        // }
        
        if (flag) {
            if (position == sharePreferences.getInt(
                    Tools.KEY_PROVINCE, 0)
                    && defaultSelectedPosition) {
                selectedIndex = position;
                viewHolder.ProvinceItem.setChecked(true);
            }
        } else {
            if (position == 0 && defaultSelectedPosition) {
                viewHolder.ProvinceItem.setChecked(true);

            }
        }
        return convertView;
    }

    class ViewHolder {
        private RadioButton ProvinceItem;
        private RadioGroup ProvinceRadioGroup;
    }
}
