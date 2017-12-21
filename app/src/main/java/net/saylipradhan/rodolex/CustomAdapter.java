package net.saylipradhan.rodolex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sayli on 12/20/2017.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String,String>> myData;
    HashMap<String, String> current;

    public CustomAdapter(Context context, ArrayList<HashMap<String, String>> myData) {
        this.context = context;
        this.myData = myData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        current = myData.get(position);
        holder.name.setText("Name: " + current.get("firstName") + current.get("lastName"));
        holder.company.setText("Company: " + current.get("company"));
        holder.email.setText("Email: " + current.get("email"));
        holder.startDate.setText("Start Date: " + current.get("startDate"));
        holder.bio.setText(current.get("bio"));

        Glide.with(context).load(current.get("avatar")).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, email, company, startDate, bio;
        public ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            email = (TextView)itemView.findViewById(R.id.email);
            company = (TextView)itemView.findViewById(R.id.company);
            startDate = (TextView)itemView.findViewById(R.id.startDate);
            bio = (TextView)itemView.findViewById(R.id.bio);
            avatar = (ImageView)itemView.findViewById(R.id.avatar);
        }
    }
}
