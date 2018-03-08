package com.tecelm.hasna.mostsarredgithubrepos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Admin on 27/02/2018.
 */

public class CustomerRepoAdapter extends RecyclerView.Adapter<CustomerRepoAdapter.MyViewHolder>{

    private Context mContext;
    private List<Repository> repoList = new ArrayList<Repository>();
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public CustomerRepoAdapter(Context mContext, List<Repository> repoList) {
        this.mContext = mContext;
        this.repoList = repoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout item xml and pass it to View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // set the data in items

        Repository repository = repoList.get(position);
        holder.name.setText(repository.getReponame());
        holder.desc.setText(repository.getDescription());
        holder.owner.setText(repository.getOwnername());
        holder.stars.setText(format(repository.getStarscount()));
        Glide.with(holder.avatar.getContext()).load(repository.getAvatarurl()).centerCrop().into(holder.avatar);
    }

    @Override
    public int getItemCount() {

        return repoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's

        private TextView name;
        private TextView owner;
        private TextView desc;
        private TextView stars;
        private ImageView avatar;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.reponame);
            owner= (TextView) itemView.findViewById(R.id.ownername);
            desc = (TextView) itemView.findViewById(R.id.description);
            stars = (TextView)itemView.findViewById(R.id.numberstars);
            avatar = (ImageView)itemView.findViewById(R.id.img);

        }
    }

    public static String format(long value) {

        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
