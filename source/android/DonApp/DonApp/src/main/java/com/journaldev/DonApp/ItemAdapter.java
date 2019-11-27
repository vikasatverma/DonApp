package com.journaldev.DonApp;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/** \brief A class to populate the list

 */
public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private List<Item> moviesList = new ArrayList<>();

    public ItemAdapter(@NonNull Context context, @LayoutRes ArrayList<Item> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    /** A function to populate the items in the list
     @param position: Position of item in the listview
     @param convertView: The current view
     @param parent: The viewgroup
     @return Returns the list of item
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Item currentItem = moviesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(currentItem.getmImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentItem.getmName());

        TextView release = (TextView) listItem.findViewById(R.id.textView_release);
        release.setText(currentItem.getmRelease());

        return listItem;
    }
}