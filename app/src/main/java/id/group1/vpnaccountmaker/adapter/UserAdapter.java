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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context context;
    private List<User> myUsers;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView nameUser,username;
        private CircleImageView img_user;
        public MyViewHolder(View v) {
            super(v);
            nameUser = itemView.findViewById(R.id.nameUser);
            username = itemView.findViewById(R.id.username);
            img_user = itemView.findViewById(R.id.image_user);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(List<User> myUsers, Context context) {
        this.myUsers = myUsers;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.nameUser.setText(myUsers.get(position).getName());
        holder.username.setText(myUsers.get(position).getUsername());
        if (myUsers.get(position).getPhoto() != null) {
            Glide.with(holder.itemView.getContext()).load(ApiClient.BASE_URL+"uploads/users/"+myUsers.get
                    (position).getPhoto())
                    .into(holder.img_user);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.logo).into(holder
                    .img_user);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myUsers.size();
    }
}
