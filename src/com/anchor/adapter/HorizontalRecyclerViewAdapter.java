package com.anchor.adapter;

/*
 * Created by Sambhaji Karad on 05-Jan-18
 * Mobile 9423476192
 * Email sambhaji2134@gmail.com/
*/

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.anchor.activities.R;
import com.anchor.helper.GlideApp;
import com.anchor.interfaces.Customer_S_Interface;
import com.anchor.model.ImageModel;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;

import br.com.felix.imagezoom.ImageZoom;


public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MessageViewHolder> {

    private ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();
    private Context context;
    private Customer_S_Interface customer_s_interface;

    public HorizontalRecyclerViewAdapter(ArrayList<ImageModel> horizontalList, Context context, Customer_S_Interface customer_s_interface) {
        this.imageModelArrayList = horizontalList;
        this.context = context;
        this.customer_s_interface = customer_s_interface;
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final ImageModel model = imageModelArrayList.get(position);
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;

        //messageViewHolder.imageView.setImageResource(model.getImagePath());

        File file = new File(model.getImagePath());
        Uri imageUri = Uri.fromFile(file);

        GlideApp.with(context)
                .load(model.getImagePath())
                .thumbnail(0.5f)
                //.centerCrop()
                .placeholder(R.drawable.img_not_found)
                .error(R.drawable.img_not_found)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(messageViewHolder.imageView);

        messageViewHolder.imageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  File file= new File(model.getImagePath());
                Uri uri = Uri.parse(model.getImagePath());
                File fdelete = new File(uri.getPath());
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        System.out.println("file Deleted :" );
                        Toast.makeText(context, "file Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("file not Deleted :");
                    }
                }
                imageModelArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, imageModelArrayList.size());

                int count = customer_s_interface.image_Count_n();
                customer_s_interface.image_Count_Flag(--count);


            }
        });
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        ImageZoom imageView;
        Button imageView_close;

        private MessageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView_s);
            imageView_close = view.findViewById(R.id.imageView_close);
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MessageViewHolder(itemView);
    }
}
