package com.demo.michel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomImageAdapter extends BaseAdapter {
    List<GetSet> data;
    Context c;
    ViewHolder v;

    static class ViewHolder {
        ImageView imageView;
        TextView label, parcelName;
        ImageButton clickImage, removeImage;
    }

    public CustomImageAdapter(List<GetSet> getData, Context context) {
        data = getData;
        c = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } else {
            view = convertView;
        }
        v = new ViewHolder();
        v.clickImage = (ImageButton) view.findViewById(R.id.capture);
        v.removeImage = (ImageButton) view.findViewById(R.id.cancel);
        v.parcelName = (TextView) view.findViewById(R.id.parcelName);
        v.label = (TextView) view.findViewById(R.id.imageFor);
        v.imageView = (ImageView) view.findViewById(R.id.imgPrv);

        //set data in listview
        final GetSet dataSet = (GetSet) data.get(position);
        dataSet.setListItemPosition(position);
        if (!dataSet.isHaveImage()) {
            Bitmap icon = BitmapFactory.decodeResource(c.getResources(), R.mipmap.ic_launcher);
            v.imageView.setImageBitmap(icon);
        } else {
            v.imageView.setImageBitmap(dataSet.getImage());

        }
        v.parcelName.setText(dataSet.getSubtext());
        v.label.setText(dataSet.getSubtext());
        if (dataSet.isStatus()) {
            v.clickImage.setVisibility(View.VISIBLE);
            v.removeImage.setVisibility(View.GONE);

        } else {
            v.removeImage.setVisibility(View.VISIBLE);
            v.clickImage.setVisibility(View.GONE);
        }
        v.clickImage.setFocusable(false);
        v.removeImage.setFocusable(false);
        v.clickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call parent method of activity to click image
                ((MainActivity) c).captureImage(dataSet.getListItemPosition(), dataSet.getLable() + "" + dataSet.getSubtext());
            }
        });
        v.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSet.setStatus(true);
                dataSet.setHaveImage(false);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    /**
     * @param position Get position of of object
     * @param imageSrc set image in imageView
     */
    public void setImageInItem(int position, Bitmap imageSrc, String imagePath) {
        GetSet dataSet = (GetSet) data.get(position);
        dataSet.setImage(imageSrc);
        dataSet.setStatus(false);
        dataSet.setHaveImage(true);
        notifyDataSetChanged();
    }
}
