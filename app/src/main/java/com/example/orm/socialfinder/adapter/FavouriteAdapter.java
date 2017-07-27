package com.example.orm.socialfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orm.socialfinder.R;
import com.example.orm.socialfinder.realm.Name;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by Lobo on 2017-07-17.
 */
public class FavouriteAdapter extends RealmBaseAdapter<Name> {


    Realm realm;
    private List<Name> mList;
    Context context;

    private static class FavouriteHolder {
        TextView firstName;
        TextView lastName;
        Button likeButton;
    }

    // Get Activity context, realm instance and populate list
    public FavouriteAdapter(OrderedRealmCollection<Name> realmResults, Context context) {
        super(realmResults);
        this.realm = Realm.getDefaultInstance();
        this.mList = new ArrayList(realm.where(Name.class).equalTo("isFavourite", true).findAll());
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final FavouriteAdapter.FavouriteHolder viewHolder;
        if (convertView == null) {
            viewHolder = new FavouriteAdapter.FavouriteHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listview_favourite, null);

            viewHolder.firstName = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.lastName = (TextView) convertView.findViewById(R.id.lastname);

            viewHolder.likeButton = (Button) convertView.findViewById(R.id.dislikeButton);
            viewHolder.likeButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(context, mList.get(position).getFirst() + " deleted from favourites", Toast.LENGTH_SHORT).show();

                    Name name = realm.where(Name.class)
                            .equalTo("first", mList.get(position).getFirst())
                            .findFirst();

                    // Check if person is already in favourites, delete if true
                    if (mList.get(position).isFavourite() == true) {
                        realm.beginTransaction();
                        name.setFavourite(false);
                        realm.copyToRealmOrUpdate(name);
                        realm.commitTransaction();
                    }
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FavouriteAdapter.FavouriteHolder) convertView.getTag();
        }
        if (adapterData != null) {
            viewHolder.firstName.setText(mList.get(position).getFirst());
            viewHolder.lastName.setText(mList.get(position).getLast());
        }
        return convertView;
    }
}
