package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myresto.R;

import java.util.ArrayList;

import model.User;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> userList;

    public UserAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        UserViewHolder mViewHolder = new UserViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = userList.get(position);
       // holder.usertext.setText(currentUser.getUserRestaurant());
      //  holder.imageUser
       // Glide.with(context)
              //  .load(imagePath)
             //   .placeholder(R.drawable.loding)
             //   .into(holder.mImageMovie);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView usertext;
        public ImageView imageUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usertext = itemView.findViewById(R.id.textemployees);
            imageUser = itemView.findViewById(R.id.imageemployees);

        }
    }
}
