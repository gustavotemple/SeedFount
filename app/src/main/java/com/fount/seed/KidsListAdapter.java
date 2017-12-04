package com.fount.seed;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.FirebaseUtils;
import com.fount.seed.wrappers.KidWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

public class KidsListAdapter
        extends FirebaseRecyclerAdapter<KidWrapper, KidsListAdapter.ViewHolder> {

    private static final String TAG = KidsListAdapter.class.getSimpleName();
    private final Query query;
    private Set<String> kids;

    KidsListAdapter(final Query query) {
        super(KidWrapper.class,
                R.layout.listitem_kid,
                ViewHolder.class,
                FirebaseUtils.getInstance().getDatabaseKids());
        this.query = query;

        if (query != null) {
            kids = new HashSet<>();
        }
    }

    /**
     * ViewHolder
     */
    @SuppressWarnings("WeakerAccess")
    public static final class ViewHolder extends RecyclerView.ViewHolder {

        public final CardView cardView;
        public final ImageView kidPicture;
        public final ImageView arrowPicture;
        public final TextView kidName;
        public final TextView classRoom;
        public final CheckBox checkBoxP;
        public final CheckBox checkBoxL;
        public final CheckBox checkBoxV;

        public ViewHolder(final View parentView) {
            super(parentView);
            cardView = parentView.findViewById(R.id.card_view);
            kidPicture = parentView.findViewById(R.id.kid_picture);
            arrowPicture = parentView.findViewById(R.id.arrow_picture);
            kidName = parentView.findViewById(R.id.kid_name);
            classRoom = parentView.findViewById(R.id.class_room);
            checkBoxP = parentView.findViewById(R.id.checkBoxP);
            checkBoxL = parentView.findViewById(R.id.checkBoxL);
            checkBoxV = parentView.findViewById(R.id.checkBoxV);
        }
    }

    @Override
    protected void populateViewHolder(final ViewHolder holder,
                                      final KidWrapper data,
                                      final int position) {
        Log.i(TAG, "populateViewHolder");

        if (query == null) {
            populateViewHolder(holder, data);
        } else {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final KidWrapper kid = snapshot.getValue(KidWrapper.class);
                        if (kids != null
                                && kid != null
                                && !kids.contains(kid.getUid())) {
                            kids.add(kid.getUid());
                            populateViewHolder(holder, kid);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
    }

    private void populateViewHolder(final ViewHolder holder,
                                    final KidWrapper data) {
        final Typeface type = Typeface.createFromAsset(holder.itemView.getContext().getAssets(),
                Constants.FONT);

        holder.cardView.setVisibility(View.VISIBLE);

        holder.kidName.setText(data.getKidName());
        holder.kidName.setTypeface(type);

        holder.classRoom.setText(FirebaseUtils.DateGenerator.formatDate(data.getClassRoom()));
        holder.classRoom.setTypeface(type);

        holder.checkBoxP.setTypeface(type);
        holder.checkBoxL.setTypeface(type);
        holder.checkBoxV.setTypeface(type);

        if (data.getGender() == KidWrapper.BOY) {
            holder.kidPicture.setImageResource(R.mipmap.ic_boy);
        } else if (data.getGender() == KidWrapper.GIRL) {
            holder.kidPicture.setImageResource(R.mipmap.ic_girl);
        }

        holder.arrowPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Selecting kid: " + data.getKidName());

                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_KID, data);
                v.getContext().startActivity(intent);
            }
        });

        holder.checkBoxP.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                Log.i(TAG, "Selecting kid: " + data.getKidName());

                FirebaseUtils.getInstance().saveStudentAttendance(data, Constants.P, isChecked);
            }
        });

        holder.checkBoxL.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                Log.i(TAG, "Selecting kid: " + data.getKidName());

                FirebaseUtils.getInstance().saveStudentAttendance(data, Constants.L, isChecked);
            }
        });

        holder.checkBoxV.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                Log.i(TAG, "Selecting kid: " + data.getKidName());

                FirebaseUtils.getInstance().saveStudentAttendance(data, Constants.V, isChecked);
            }
        });
    }

    /**
     * add
     *
     * @param kid KidWrapper
     */
    void add(@NonNull final KidWrapper kid) {
        FirebaseUtils.getInstance().saveKid(kid);
        notifyDataSetChanged();
    }

    /**
     * remove
     *
     * @param kid KidWrapper
     */
    void remove(@NonNull final KidWrapper kid) {
        FirebaseUtils.getInstance().deleteKid(kid);
        notifyDataSetChanged();
    }

    /**
     * replace
     *
     * @param kid KidWrapper
     */
    void replace(@NonNull final KidWrapper kid) {
        FirebaseUtils.getInstance().updateKid(kid);
        notifyDataSetChanged();
    }
}
