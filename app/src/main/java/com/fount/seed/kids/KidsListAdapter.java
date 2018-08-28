package com.fount.seed.kids;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.fount.seed.R;
import com.fount.seed.register.UpdateKidActivity;
import com.fount.seed.utils.Constants;
import com.fount.seed.database.firebase.FirebaseUtils;
import com.fount.seed.wrappers.KidWrapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class KidsListAdapter
        extends FirebaseRecyclerAdapter<KidWrapper, KidsListAdapter.ViewHolder> {

    private static final String TAG = KidsListAdapter.class.getSimpleName();
    private final DatabaseReference databaseReference;

    KidsListAdapter(final DatabaseReference databaseReference,
                    final Query query) {
        super(KidWrapper.class,
                R.layout.listitem_kid,
                ViewHolder.class,
                query);
        this.databaseReference = databaseReference;
    }

    KidsListAdapter(final DatabaseReference databaseReference) {
        super(KidWrapper.class,
                R.layout.listitem_kid,
                ViewHolder.class,
                databaseReference);
        this.databaseReference = databaseReference;
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

        final Typeface type = Typeface.createFromAsset(holder.itemView.getContext().getAssets(),
                Constants.FONT);

        holder.kidName.setText(data.getKidName());
        holder.kidName.setTypeface(type);

        holder.classRoom.setText(FirebaseUtils.DateGenerator.formatDate(data.getClassRoom()));
        holder.classRoom.setTypeface(type);

        if (data.getClassRoom().contains(Constants.CHALET)
                && FirebaseUtils.DateGenerator.getPeriod().contains(Constants.AM)) {
            FirebaseUtils.getInstance().setLetter(data, Constants.L, holder.checkBoxL);
            FirebaseUtils.getInstance().setLetter(data, Constants.V, holder.checkBoxV);
            holder.checkBoxL.setVisibility(View.VISIBLE);
            holder.checkBoxV.setVisibility(View.VISIBLE);
            holder.checkBoxL.setTypeface(type);
            holder.checkBoxV.setTypeface(type);
        }

        FirebaseUtils.getInstance().setLetter(data, Constants.P, holder.checkBoxP);
        holder.checkBoxP.setTypeface(type);

        if (data.getGender() == KidWrapper.BOY) {
            holder.kidPicture.setImageResource(R.mipmap.ic_boy);
        } else if (data.getGender() == KidWrapper.GIRL) {
            holder.kidPicture.setImageResource(R.mipmap.ic_girl);
        }

        holder.arrowPicture.setOnClickListener(view -> {
            Log.i(TAG, "Selecting kid: " + data.getKidName());

            Intent intent = new Intent(view.getContext(), UpdateKidActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_KID, data);
            ((Activity) view.getContext()).startActivityForResult(intent, Constants.UPDATE);
        });

        holder.checkBoxP.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "Selecting kid: " + data.getKidName());

            FirebaseUtils.getInstance().saveStudentAttendance(data, Constants.P, isChecked);
        });

        holder.checkBoxL.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "Selecting kid: " + data.getKidName());

            FirebaseUtils.getInstance().saveStudentAttendance(data, Constants.L, isChecked);
        });

        holder.checkBoxV.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "Selecting kid: " + data.getKidName());

            FirebaseUtils.getInstance().saveStudentAttendance(data, Constants.V, isChecked);
        });
    }

    /**
     * add
     *
     * @param kid KidWrapper
     */
    void add(@NonNull final KidWrapper kid) {
        FirebaseUtils.getInstance().saveKid(kid, databaseReference);
        notifyDataSetChanged();
    }

    /**
     * remove
     *
     * @param kid KidWrapper
     */
    void remove(@NonNull final KidWrapper kid) {
        FirebaseUtils.getInstance().deleteKid(kid, databaseReference);
        notifyDataSetChanged();
    }

    /**
     * replace
     *
     * @param kid KidWrapper
     */
    void replace(@NonNull final KidWrapper kid) {
        FirebaseUtils.getInstance().updateKid(kid, databaseReference);
        notifyDataSetChanged();
    }
}
