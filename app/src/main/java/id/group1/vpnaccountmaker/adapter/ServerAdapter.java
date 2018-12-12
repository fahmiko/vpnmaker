package id.group1.vpnaccountmaker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import id.group1.vpnaccountmaker.R;
import id.group1.vpnaccountmaker.model.*;
import id.group1.vpnaccountmaker.rest.ApiClient;


public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.MyViewHolder> {
    private Context context;
    private List<Server> myServers;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView text_server,text_location,text_acc,text_port;
        private CircleImageView img_flag;
        public MyViewHolder(View v) {
            super(v);
            text_server = itemView.findViewById(R.id.nameServer);
            text_location = itemView.findViewById(R.id.location);
            text_port = itemView.findViewById(R.id.port);
            text_acc = itemView.findViewById(R.id.accRemaining);
            img_flag = itemView.findViewById(R.id.image_flag);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ServerAdapter(List<Server> myServers, Context context) {
        this.myServers = myServers;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ServerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_server, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.text_server.setText(myServers.get(position).getName_server());
        holder.text_location.setText(myServers.get(position).getLocation());
        holder.text_acc.setText(myServers.get(position).getAcc_remaining());
        if (myServers.get(position).getFlag_image() != null) {
            Glide.with(holder.itemView.getContext()).load(ApiClient.BASE_URL+"uploads/"+myServers.get
                    (position).getFlag_image())
                    .into(holder.img_flag);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.logo).into(holder
                    .img_flag);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myServers.size();
    }
}

