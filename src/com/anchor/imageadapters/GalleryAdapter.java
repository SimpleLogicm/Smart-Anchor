package com.anchor.imageadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.helper.GlideApp;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Lincoln on 31/03/16.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private List<Image> images;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView pdf_name;
      //  public PDFView pDFView;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
            pdf_name = view.findViewById(R.id.pdf_name);
            //pDFView = (PDFView) view.findViewById(R.id.G_pdfView);
        }
    }


    public GalleryAdapter(Context context, List<Image> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Image image = images.get(position);

        String type = image.getType();

        if(type.equalsIgnoreCase("application/pdf"))
        {
            // holder.pDFView.setVisibility(View.VISIBLE);
            holder.pdf_name.setVisibility(View.VISIBLE);
            holder.thumbnail.setVisibility(View.GONE);
            holder.pdf_name.setText(image.getName()+" \n"+"PDF");

            // holder.pDFView.fromUri(Uri.parse(LaunchesItem.getFile_path()))
            //        .load();
        }
        else
        if(type.equalsIgnoreCase("video/mp4"))
        {
            // holder.pDFView.setVisibility(View.VISIBLE);
            holder.pdf_name.setVisibility(View.VISIBLE);
            holder.thumbnail.setVisibility(View.GONE);
            holder.pdf_name.setText(image.getName()+" \n"+"Video");

            // holder.pDFView.fromUri(Uri.parse(LaunchesItem.getFile_path()))
            //        .load();
        }
        else
        if(type.equalsIgnoreCase("text/plain"))
        {
            // holder.pDFView.setVisibility(View.VISIBLE);
            holder.pdf_name.setVisibility(View.VISIBLE);
            holder.thumbnail.setVisibility(View.GONE);
            holder.pdf_name.setText(image.getName()+" \n"+"CSV");

            // holder.pDFView.fromUri(Uri.parse(LaunchesItem.getFile_path()))
            //        .load();
        }
        else
        if(type.equalsIgnoreCase("audio/mpeg"))
        {
            // holder.pDFView.setVisibility(View.VISIBLE);
            holder.pdf_name.setVisibility(View.VISIBLE);
            holder.thumbnail.setVisibility(View.GONE);
            holder.pdf_name.setText(image.getName()+" \n"+"Audio");

            // holder.pDFView.fromUri(Uri.parse(LaunchesItem.getFile_path()))
            //        .load();
        }
        else
        {
           // holder.pDFView.setVisibility(View.GONE);
            holder.pdf_name.setVisibility(View.GONE);
            holder.thumbnail.setVisibility(View.VISIBLE);

            GlideApp.with(mContext).load(image.getLarge())
                    .thumbnail(0.5f)
                    //.crossFade()
                    .error(R.drawable.imgnot_found)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumbnail);
        }


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}