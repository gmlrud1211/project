//package com.example.db_14.travelplanner;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
///**
// * Created by a0104 on 2017-04-25
// */
//
//public class PlanAdapter extends BaseExpandableListAdapter {
//    Context mContext;
//    ArrayList<SightData> msightData;
//
//    public PlanAdapter(Context context, ArrayList<SightData> sight) {
//        mContext = context;
//        msightData = sight;
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        // TODO Auto-generated method stub
//        return msightData.get(groupPosition).sight.get(childPosition);
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        // TODO Auto-generated method stub
//        return childPosition;
//    }
//
//    //ChildView에 데이터 뿌리기
//    @Override
//    public View getChildView(int groupPosition, int childPosition,
//                             boolean isLastChild, View convertView, ViewGroup parent) {
//        View view;
//        if(convertView == null) {
//            view = getChildGenericView();
//        } else {
//            view = convertView;
//        }
//
//        TextView text = (TextView)view.findViewById(android.R.id.text1);
//        text.setText(msightData.get(groupPosition).sight.get(childPosition));
//        return view;
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return msightData.get(groupPosition).sight.size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return msightData.get(groupPosition);
//    }
//
//    @Override
//    public int getGroupCount() {
//        return msightData.size();
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    //GroupView에 데이터 뿌리
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded,
//                             View convertView, ViewGroup parent) {
//
//        View view;
//        if(convertView == null) {
//            view = getParentGenericView();
//        } else {
//            view = convertView;
//        }
//
//        TextView text = (TextView)view.findViewById(R.id.plan_days);
//        text.setText(msightData.get(groupPosition).day+"일차");
//        return view;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public boolean areAllItemsEnabled() {
//        // TODO Auto-generated method stub
//        return super.areAllItemsEnabled();
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    //Child의 View의 XML을 생성
//    public View getChildGenericView() {
//        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
//        view.setBackgroundColor(Color.DKGRAY);
//        return view;
//    }
//
//    //Parent(Group)의 View의 XML을 생성
//    public View getParentGenericView() {
//        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.planview_item, null);
//        return view;
//    }
//}