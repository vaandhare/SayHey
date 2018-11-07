package in.indekode.sayhey.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import in.indekode.sayhey.Model.Chat;
import in.indekode.sayhey.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int MSG_TYPE_LEFT =0;
    private static final int MSG_TYPE_RIGHT =1;
    private Context mContext;
    private List<Chat> mChats;
    private String imageURL;

    private FirebaseUser mFirebaseUser;

    public MessageAdapter(Context context, List<Chat> chats, String imageURL) {
        this.mContext = context;
        this.mChats = chats;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        Chat chat = mChats.get(i);

        viewHolder.show_msg.setText(chat.getMessage());

        if ( imageURL.equals("default")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(imageURL).into(viewHolder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView show_msg;
        public ImageView profile_image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_msg = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }

    @Override
    public int getItemViewType(int position) {
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChats.get(position).getSender().equals(mFirebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else
            return MSG_TYPE_LEFT;
    }
}
