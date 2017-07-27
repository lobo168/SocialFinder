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

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by Lobo on 2017-07-10.
 */
public class BrowseAdapter extends RealmBaseAdapter<Name> {

    Realm realm;
    private ArrayList<Name> mList;
    Context context;

    private static class ViewHolder {
        TextView firstName;
        TextView lastName;
        Button likeButton;
    }

    // Get Activity context, realm instance and populate list
    public BrowseAdapter(OrderedRealmCollection<Name> realmResults, Context context) {
        super(realmResults);
        this.realm = Realm.getDefaultInstance();
        this.mList = new ArrayList(realm.where(Name.class).findAll());
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listview_browse, null);

            // Bind TextViews
            viewHolder.firstName = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.lastName = (TextView) convertView.findViewById(R.id.lastname);

            // Bind button and set onClickListener
            viewHolder.likeButton = (Button) convertView.findViewById(R.id.likeButton);
            viewHolder.likeButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(context, mList.get(position).getFirst() + " added to favourites", Toast.LENGTH_SHORT).show();

                    Name name = realm.where(Name.class)
                            .equalTo("first", mList.get(position).getFirst())
                            .findFirst();

                    // Check if person is already in favourites, if not update record
                    if (mList.get(position).isFavourite() == false) {
                        realm.beginTransaction();
                        name.setFavourite(true);
                        realm.copyToRealmOrUpdate(name);
                        realm.commitTransaction();
                    }
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (adapterData != null) {
            viewHolder.firstName.setText(mList.get(position).getFirst());
            viewHolder.lastName.setText(mList.get(position).getLast());
        }

        return convertView;
    }
}
