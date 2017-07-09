package com.example.p.cityguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by P on 03-01-2017.
 */
public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.ViewHold> {

    ArrayList<BusinessData> data = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    String category;

    public ViewListAdapter(Context c, String temp) {
        context = c;
        category = temp;
        inflater = LayoutInflater.from(c);
        Log.i("Constructor", "Called");
    }

    public void setData(BusinessData temp) {
        data.add(temp);
        notifyItemRangeChanged(0, data.size());
        notifyDataSetChanged();
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.singlerow_business_list, parent, false);
        ViewHold temp = new ViewHold(v);
        Log.i("OnView", "Called");
        return temp;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, int position) {
        BusinessData temp = data.get(position);
        holder.textViewName.setText(temp.getName());
        holder.textViewAddress.setText(temp.getAddress());

        String url = temp.getProfilephoto();

        ImageLoader imageLoader = SingletonRequest.getInstance(context.getApplicationContext()).getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error in Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.i("OnBind", "Called");
    }

    @Override
    public int getItemCount() {
        Log.i("OnItem", "Called");
        return data.size();

    }

    class ViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewName, textViewAddress;
        RelativeLayout relativeLayout;
        ImageButton fav, phone;

        public ViewHold(View itemView) {
            super(itemView);
            Log.i("ViewHolder", "Called");
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.mainView);
            imageView = (ImageView) itemView.findViewById(R.id.singleRowImage);
            textViewName = (TextView) itemView.findViewById(R.id.businessName);
            textViewAddress = (TextView) itemView.findViewById(R.id.address);
            fav = (ImageButton) itemView.findViewById(R.id.fav);
            phone = (ImageButton) itemView.findViewById(R.id.phone);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag().equals("not")) {
                        fav.setImageResource(R.drawable.favselected);
                        fav.setTag("yes");
                    } else {
                        fav.setImageResource(R.drawable.favorite);
                        fav.setTag("not");
                    }
                }
            });

            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusinessData temp = data.get(getAdapterPosition());
                    String contact = temp.getMobile();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contact));
                    context.startActivity(intent);
                }
            });


            relativeLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            BusinessData temp = data.get(getAdapterPosition());
            Intent intent = new Intent(context, BusinessDetail.class);
            intent.putExtra("data", temp);
            context.startActivity(intent);
        }
    }
}
