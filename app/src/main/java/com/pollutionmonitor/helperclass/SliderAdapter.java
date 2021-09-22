package com.pollutionmonitor.helperclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.pollutionmonitor.R;

public class SliderAdapter extends PagerAdapter {
    Context Context;
    LayoutInflater layoutInflater ;

    public SliderAdapter(Context Context) {
        this.Context = Context;
    }

    int images[] = {
            R.drawable.slideone,
            R.drawable.slidetwo,
            R.drawable.sliderthree
    };
    int headings[] = {
            R.string.first_onboard_h,
            R.string.second_onboard_h,
            R.string.third_onboard_h
    };
    int dis[] = {
            R.string.first_onboard_p,
            R.string.second_onboard_p,
            R.string.third_onboard_p
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==(ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout , container,false);

        ImageView imageview = view.findViewById(R.id.imageView);
        TextView heading = view.findViewById(R.id.textView3);
        TextView des = view.findViewById(R.id.textView4);

        imageview.setImageResource(images[position]);
        heading.setText(headings[position]);
        des.setText(dis[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
