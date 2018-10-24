package id.group1.vpnaccountmaker.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.group1.vpnaccountmaker.R;
import id.group1.vpnaccountmaker.model.ServerModel;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.MyViewHolder> {
    private Context context;
    private List<ServerModel> myServers;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
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
        public ServerAdapter(List<ServerModel> myServers) {
            this.myServers = myServers;
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

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            ServerModel server = myServers.get(position);
            Uri uri = Uri.parse(server.getImg());

            holder.text_server.setText(server.getName_server());
            holder.text_location.setText(server.getLocation());
            holder.text_port.setText(server.getPort());
            holder.text_acc.setText(""+server.getAcc_remaining()+" Account");
            holder.img_flag.setImageURI(uri);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return myServers.size();
        }
}
