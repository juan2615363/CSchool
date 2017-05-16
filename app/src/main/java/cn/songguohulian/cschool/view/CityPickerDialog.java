package cn.songguohulian.cschool.view;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;




import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.adapter.ProvinceAdapter;
import cn.songguohulian.cschool.mode.MSharePreferences;
import cn.songguohulian.cschool.mode.ProvinceModel;
import cn.songguohulian.cschool.service.Tools;
import cn.songguohulian.cschool.service.XmlParserHandler;

public class CityPickerDialog extends Dialog implements
        View.OnClickListener {
    // private Context mContext;

    private TextView title;

    private ListView provinceListview;
    private Button btnCancel;
    private Button btnOk;
    // private Dialog customDialog;
    private MSharePreferences sharePreferences;
    private OnCityPikerListener mlistener;
    private List<ProvinceModel> provinces;
    private ProvinceAdapter provinceAdapter;
    private ProvinceAdapter cityAdapter;

    public interface OnCityPikerListener {
        void onCityPicker(String province, String city);
    }

    public CityPickerDialog(Context context,
            OnCityPikerListener onCityPickerListener) {
        // super(context,R.style.customdialog);
        // TODO Auto-generated constructor stub
        this(context, R.style.customdialog, onCityPickerListener);
    }

    public CityPickerDialog(Context context, int theme,
            OnCityPikerListener onCityPickerListener) {
        super(context, theme);
        mlistener = onCityPickerListener;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.province_listview, null);
        title = (TextView) view.findViewById(R.id.title);
        provinceListview = (ListView) view.findViewById(R.id.provinceList);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        setContentView(view);
        setCancelable(true);

        sharePreferences = MSharePreferences.getInstance();
        sharePreferences.getSharedPreferences(getContext());
        initProvinceDatas();
        initProvince();
        initDialogSize();

    }

    private boolean isCity;

    public void initProvince() {
        title.setText(R.string.province);
        provinceAdapter = new ProvinceAdapter(getContext(), provinces);
        provinceListview.setAdapter(provinceAdapter);
        provinceListview.setSelection(sharePreferences.getInt(
                Tools.KEY_PROVINCE, 0));
        isCity = true;
    }

    public void initDialogSize() {

        Window dialogWindow = getWindow();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = LayoutParams.WRAP_CONTENT;
        p.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(p);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.btnCancel:
            dismiss();
            break;
        case R.id.btnOk:
            int provincePosition = provinceAdapter.getSelectedIndex();
            String province = provinces.get(provincePosition).getName();
            String city;
            if (isCity) {

                title.setText(R.string.city);
                cityAdapter = new ProvinceAdapter(getContext(), provinces.get(
                        provincePosition).getCityList(), false);
                provinceListview.setAdapter(cityAdapter);
                isCity = false;
            } else {
                city = provinces.get(provincePosition).getCityList()
                        .get(cityAdapter.getSelectedIndex()).getName();
                sharePreferences.putInt(Tools.KEY_PROVINCE, provincePosition);
                mlistener.onCityPicker(province, city);

                dismiss();
            }

            break;
        default:
            break;
        }

    }

    public void initProvinceDatas() {

        AssetManager asset = getContext().getAssets();

        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinces = handler.getDataList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
