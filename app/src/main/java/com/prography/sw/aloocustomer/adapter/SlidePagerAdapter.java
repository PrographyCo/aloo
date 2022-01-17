package com.prography.sw.aloocustomer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.model.Offer;
import com.prography.sw.aloocustomer.model.slide;
import com.prography.sw.aloocustomer.util.AppUtils;

import java.util.List;

public class SlidePagerAdapter extends PagerAdapter {

    private List<String> mList;
    private List<Offer> mListOffer;

    public SlidePagerAdapter(List<String> mList) {

        this.mList = mList;
    }

    public SlidePagerAdapter(List<Offer> ListOffer, String type) {

        this.mListOffer = ListOffer;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item, null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        if (mList != null) {
            AppUtils.initGlide(container.getContext()).load(mList.get(position)).into(slideImg);
        } else if (mListOffer != null) {
            AppUtils.initGlide(container.getContext()).load(mListOffer.get(position).getImg()).into(slideImg);
        }


        container.addView(slideLayout);
        return slideLayout;


    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else if (mListOffer != null) {
            return mListOffer.size();
        } else {
            return -1;
        }

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}