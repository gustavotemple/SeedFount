package com.fount.seed.attendance;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.fount.seed.R;
import com.fount.seed.utils.Constants;
import com.fount.seed.database.firebase.FirebaseUtils;
import com.fount.seed.wrappers.ClassDate;
import com.google.firebase.database.DatabaseReference;

public class ClassDateListAdapter
        extends FirebaseRecyclerAdapter<ClassDate, ClassDateListAdapter.ViewHolder> {

    private static final String TAG = ClassDateListAdapter.class.getSimpleName();

    public ClassDateListAdapter(@NonNull final DatabaseReference databaseReference) {
        super(ClassDate.class,
                R.layout.listitem_date,
                ViewHolder.class,
                databaseReference);
    }

    /**
     * ViewHolder
     */
    @SuppressWarnings("WeakerAccess")
    public static final class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView date;
        public final ImageView arrowPicture;

        public ViewHolder(final View parentView) {
            super(parentView);
            date = parentView.findViewById(R.id.class_date);
            arrowPicture = parentView.findViewById(R.id.arrow_picture);
        }
    }

    @Override
    protected void populateViewHolder(final ViewHolder holder,
                                      final ClassDate data,
                                      final int position) {
        Log.i(TAG, "populateViewHolder");

        final Typeface type = Typeface.createFromAsset(holder.itemView.getContext().getAssets(),
                Constants.FONT);

        holder.date.setText(FirebaseUtils.DateGenerator.formatDate(data.getDate()));
        holder.date.setTypeface(type);

        holder.arrowPicture.setOnClickListener(v -> {
            Log.i(TAG, "Date: " + data.getDate());

            Intent intent = new Intent(v.getContext(), StudentAttendanceActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_DATE, data);

            v.getContext().startActivity(intent);
        });
    }
}
