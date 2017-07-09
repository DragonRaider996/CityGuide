package com.example.p.cityguide;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by P on 31-12-2016.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHold> {

    ArrayList<Data> data = new ArrayList<>();
    int[] images = {R.drawable.restauranthome, R.drawable.hospital, R.drawable.professionals, R.drawable.travel};
    Context context;
    LayoutInflater inflater;
    String restuarant = "586b8264dfdefc0d7f276f98";
    String professional = "586b8264dfdefc0d7f276f99";
    String healthcare = "586b8264dfdefc0d7f276f9a";

    public ViewAdapter(Context con, ArrayList<Data> temp) {
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = con;
        data = temp;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.singlerow, parent, false);
        ViewHold holder = new ViewHold(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        Data temp = data.get(position);
        holder.business.setText(temp.getBusiness());
        holder.category.setText(temp.getCategory());
        holder.image.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView business;
        TextView category;
        RelativeLayout relativeLayout;

        public ViewHold(View itemView) {
            super(itemView);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);
            image = (ImageView) itemView.findViewById(R.id.imageBusiness);
            business = (TextView) itemView.findViewById(R.id.businessName);
            category = (TextView) itemView.findViewById(R.id.category);

            relativeLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Data temp = data.get(getAdapterPosition());
            String name = temp.getBusiness();
            String business[] = context.getResources().getStringArray(R.array.business);

            if (name.equals(business[0])) {
                Intent intent = new Intent(context, BusinessList.class);
                intent.putExtra("category", restuarant);
                context.startActivity(intent);
            } else if (name.equals(business[1])) {
                Intent intent = new Intent(context, BusinessList.class);
                intent.putExtra("category", healthcare);
                context.startActivity(intent);
            } else if (name.equals(business[2])) {
                Intent intent = new Intent(context, BusinessList.class);
                intent.putExtra("category", professional);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Travel", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
