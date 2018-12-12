package id.group1.vpnaccountmaker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import id.group1.vpnaccountmaker.R;
import id.group1.vpnaccountmaker.model.*;

public class AccAdapter extends RecyclerView.Adapter<AccAdapter.MyViewHolder> {
    private Context context;
    private List<Acc> myAccs;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView id,user,server,active;
        public MyViewHolder(View v) {
            super(v);
            id = itemView.findViewById(R.id.acc_id);
            user = itemView.findViewById(R.id.acc_user);
            server = itemView.findViewById(R.id.acc_server);
            active = itemView.findViewById(R.id.acc_active);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AccAdapter(List<Acc> myAccs, Context context) {
        this.myAccs = myAccs;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AccAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_account, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.id.setText("Id account "+myAccs.get(position).getId());
        holder.user.setText(myAccs.get(position).getUser());
        holder.server.setText(myAccs.get(position).getServer());
        holder.active.setText(myAccs.get(position).getActive());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myAccs.size();
    }
}
