package com.anchor.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.helper.GlideApp;
import com.anchor.imageadapters.Image;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;




public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Image> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;


    static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        lblCount = v.findViewById(R.id.lbl_count);
        lblTitle = v.findViewById(R.id.title);
        lblDate = v.findViewById(R.id.date);

        images = (ArrayList<Image>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + images.size());

        Image image = images.get(position);
        lblTitle.setText(image.getName());
        lblDate.setText(image.getTimestamp());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = view.findViewById(R.id.image_preview);

            TextView pdf_name = view.findViewById(R.id.pdf_name);

            //PDFView pDFView = (PDFView) view.findViewById(R.id.GG_pdfView);

             final Image image = images.get(position);

            String type = image.getType();

            if(type.equalsIgnoreCase("application/pdf"))
            {
                //  pDFView.setVisibility(View.VISIBLE);
                // imageViewPreview.setVisibility(View.GONE);
//                pDFView.fromUri(Uri.parse(image.getFile_path()))
//                        .enableSwipe(true)
//                        .enableSwipe(true)
//                        .enableDoubletap(true)
//                        .defaultPage(0)
//                        .enableAnnotationRendering(true)
//                        .load();
                pdf_name.setVisibility(View.VISIBLE);
                imageViewPreview.setVisibility(View.GONE);
                pdf_name.setText(image.getName()+" \n"+"PDF");

                pdf_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(image.getLarge()))
                        {
                            try {
                                Intent target = new Intent(Intent.ACTION_VIEW);
                                target.setDataAndType(Uri.parse(image.getLarge()), "application/pdf");
                                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                Intent intent = Intent.createChooser(target, "Open File");

                                getActivity().startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                // Instruct the user to install a PDF reader here, or something
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "File path is incorrect." , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else
            {
                //pDFView.setVisibility(View.GONE);
                pdf_name.setVisibility(View.GONE);
                imageViewPreview.setVisibility(View.VISIBLE);

                GlideApp.with(getActivity()).load(image.getLarge())
                        .thumbnail(0.5f)
                        .error(R.drawable.imgnot_found)
                        //.crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageViewPreview);
            }



            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
