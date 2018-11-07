package in.indekode.sayhey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.indekode.sayhey.MessageActivity;
import in.indekode.sayhey.Model.User;
import in.indekode.sayhey.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    boolean isChat;

    public UserAdapter(Context context, List<User> users, boolean isChat) {
        this.mContext = context;
        this.mUsers = users;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_items, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User user = mUsers.get(i);
        viewHolder.username.setText(user.getUsername());
        if ( user.getImageURL().equals("default")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher_round);
        }else{
            Glide.with(mContext).load(user.getImageURL()).into(viewHolder.profile_image);
        }

        if ( isChat){
            if (user.getStatus().equals("Online")){
                viewHolder.Img_on.setVisibility(View.VISIBLE);
                viewHolder.Img_off.setVisibility(View.GONE);
            }else {
                viewHolder.Img_on.setVisibility(View.GONE);
                viewHolder.Img_off.setVisibility(View.VISIBLE);
            }
        }else{
            viewHolder.Img_on.setVisibility(View.GONE);
            viewHolder.Img_off.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image, Img_on, Img_off;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            Img_on = itemView.findViewById(R.id.img_on);
            Img_off = itemView.findViewById(R.id.img_off);

        }
    }

}
