package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.cityselectbean.AddressTag;
import com.bibinet.biunion.project.bean.cityselectbean.City;
import com.bibinet.biunion.project.bean.cityselectbean.County;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.RoutineUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴昊 on 2017-5-10.
 */

public class SelectAddressAdapter extends MultipleBaseAdapter{
    private Context mContext;
    private List<AddressTag> addressTagList;
    private List<Object> dataList;
    private List<City> searchList;

    private List<Integer> tagPostionList;
    public SelectAddressAdapter(Context mContext, List<AddressTag> addressTagList, OnSelectAddressAdapter onSelectAddressAdapter) {
        this.mContext = mContext;
        this.addressTagList = addressTagList;
        this.onSelectAddressAdapter = onSelectAddressAdapter;

        searchList = new ArrayList<City>();
        dataList = new ArrayList<Object>();
        tagPostionList = new ArrayList<Integer>();
        for(int i=0 ; i<this.addressTagList.size() ; i++){
            //过滤掉没有尺寸的标签
            if(this.addressTagList.get(i).getCityList().size()<=0){
                continue;
            }
            tagPostionList.add(dataList.size());
            AddressTag addressTag = this.addressTagList.get(i);
            //编号 A,B,C,热门
            dataList.add(addressTag.getName());
            //内容
            dataList.addAll(addressTag.getCityList());
        }
    }

    public List<Integer> getTagPostionList() {
        return tagPostionList;
    }

    private final int TYPE_TAG = 0;
    private final int TYPE_CITY = 1;
    private final int TYPE_COUNTY = 2;
    private int [] layoutId = {R.layout.adapter_select_address_tag, R.layout.adapter_select_address_city, R.layout.adapter_select_address_county};
    private int [] typeId = {TYPE_TAG, TYPE_CITY, TYPE_COUNTY};
    @Override
    public int getCount() {
        if(isSearch){
            return Common.isListEmpty(searchList) ? 0 : searchList.size();
        }
        return Common.isListEmpty(dataList) ? 0 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isSearch){
            City c = searchList.get(position);
            if(c instanceof County){
                return TYPE_COUNTY;
            }else{
                return TYPE_CITY;
            }
        }else{
            Object d = dataList.get(position);
            if(d instanceof String){
                return TYPE_TAG;
            }
            return TYPE_CITY;
        }

    }

    private boolean isSearch = false;

    @Override
    protected Context getContext() {
        return mContext;
    }

    @Override
    protected void initValue(int position, int currentType, Object holder) {
        //根据是否有关键字判断走哪一套数据集合 全部 或者 搜索
        switch (currentType){
            case TYPE_TAG:
                ViewHolderTag tag = (ViewHolderTag) holder;
                String name = (String) dataList.get(position);
                tag.name.setText(name);
                break;
            case TYPE_CITY:
                ViewHolderCity city = (ViewHolderCity) holder;
                City cityD = null;
                if(isSearch){
                    cityD = (City) searchList.get(position);
                }else{
                    cityD = (City) dataList.get(position);
                }
                String nameC = cityD.getName();
                city.name.setText(nameC);
                //点击监听
                final City cityDF = cityD;
                city.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSelectAddressAdapter.onAddressClick(cityDF);
                    }
                });
                break;
            case TYPE_COUNTY:
                ViewHolderCounty countyV = (ViewHolderCounty) holder;
                final County county = (County) searchList.get(position);
                countyV.cityName.setText(county.getParentCity());
                countyV.countyName.setText(county.getName());
                //点击监听
                countyV.mainV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSelectAddressAdapter.onAddressClick(county);
                    }
                });
                break;
        }
    }
    private OnSelectAddressAdapter onSelectAddressAdapter;
    public interface OnSelectAddressAdapter{
        void onAddressClick(City city);
    }
    @Override
    protected Object initView(int currentType, View view) {
        Object o = null;
        switch (currentType){
            case TYPE_TAG:
                o = new ViewHolderTag();
                ViewHolderTag tag = (ViewHolderTag) o;
                tag.name = (TextView) view.findViewById(R.id.ada_select_address_tag_name);
                break;
            case TYPE_CITY:
                o = new ViewHolderCity();
                ViewHolderCity city = (ViewHolderCity) o;
                city.name = (TextView) view.findViewById(R.id.ada_select_address_city_name);
                break;
            case TYPE_COUNTY:
                o = new ViewHolderCounty();
                ViewHolderCounty couny = (ViewHolderCounty) o;
                couny.cityName = (TextView) view.findViewById(R.id.ada_select_address_city_name);
                couny.countyName = (TextView) view.findViewById(R.id.ada_select_address_county_name);
                couny.mainV = view.findViewById(R.id.ada_select_address_county_main);
                break;
        }
        return o;
    }

    @Override
    protected int[] getLayoutId() {
        return layoutId;
    }

    @Override
    public int[] getItemViewTypeId() {
        return typeId;
    }

    public void search(String key) {
        if(key.equals("")){
            isSearch = false;
            notifyDataSetChanged();
            return;
        }else{
            isSearch = true;
        }
        //根据key过滤 不过滤热门城市 直接跳过
        searchList.clear();
        //不从0开始 跳过热门城市
        for(int i=addressTagList.get(0).getCityList().size()+1 ; i<dataList.size() ; i++){
            Object o = dataList.get(i);
            if(o instanceof City){
                City c = (City) o;
                boolean isInclude = RoutineUtils.isInclude(c.getName(), key);
                if(isInclude){
                    searchList.add(c);
                }
            }
        }
        //刷新适配器
        notifyDataSetChanged();
    }

    private class ViewHolderTag{
        TextView name;
    }
    private class ViewHolderCity{
        TextView name;
    }
    private class ViewHolderCounty{
        TextView countyName, cityName;
        View mainV;
    }
}
