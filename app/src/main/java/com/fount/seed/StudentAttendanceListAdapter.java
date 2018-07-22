package com.fount.seed;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fount.seed.utils.Constants;
import com.fount.seed.wrappers.StudentAttendance;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAttendanceListAdapter
        extends RecyclerView.Adapter<StudentAttendanceListAdapter.ViewHolder>
        implements Filterable {

    private static final String TAG = StudentAttendanceListAdapter.class.getSimpleName();
    private List<StudentAttendance> dataSetOriginal;
    private List<StudentAttendance> dataSetFilter;

    StudentAttendanceListAdapter() {
        this.dataSetOriginal = new ArrayList<>();
        this.dataSetFilter = new ArrayList<>();
    }

    void init(@NonNull final HashMap<String, HashMap<String, Boolean>> studentAttendances) {
        for (Map.Entry<String, HashMap<String, Boolean>> student : studentAttendances.entrySet()) {
            StudentAttendance studentAttendance = new StudentAttendance(student.getKey());
            studentAttendance.setLetters(student.getValue());
            add(studentAttendance);
        }

        sort();
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final ImageView kidPicture;
        private final ImageView arrowPicture;
        private final TextView kidName;
        private final TextView classRoom;
        private final CheckBox checkBoxP;
        private final CheckBox checkBoxL;
        private final CheckBox checkBoxV;

        ViewHolder(final View parentView, final ImageView kidPicture,
                   final ImageView arrowPicture, final TextView kidName,
                   final TextView classRoom, final CheckBox checkBoxP,
                   final CheckBox checkBoxL, final CheckBox checkBoxV,
                   final CardView cardView) {
            super(parentView);
            this.cardView = cardView;
            this.kidPicture = kidPicture;
            this.arrowPicture = arrowPicture;
            this.kidName = kidName;
            this.classRoom = classRoom;
            this.checkBoxP = checkBoxP;
            this.checkBoxL = checkBoxL;
            this.checkBoxV = checkBoxV;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_kid,
                parent,
                false);
        return new ViewHolder(v,
                (ImageView) v.findViewById(R.id.kid_picture),
                (ImageView) v.findViewById(R.id.arrow_picture),
                (TextView) v.findViewById(R.id.kid_name),
                (TextView) v.findViewById(R.id.class_room),
                (CheckBox) v.findViewById(R.id.checkBoxP),
                (CheckBox) v.findViewById(R.id.checkBoxL),
                (CheckBox) v.findViewById(R.id.checkBoxV),
                (CardView) v.findViewById(R.id.card_view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Typeface type = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), Constants.FONT);
        final StudentAttendance data = dataSetFilter.get(position);
        RelativeLayout.LayoutParams params;

        holder.cardView.setVisibility(View.VISIBLE);

        holder.kidName.setText(data.getKidName());
        holder.kidName.setTypeface(type);

        params = (RelativeLayout.LayoutParams) holder.checkBoxP.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_BOTTOM);
        params.removeRule(RelativeLayout.ALIGN_BASELINE);
        params.removeRule(RelativeLayout.END_OF);
        params.addRule(RelativeLayout.START_OF, R.id.checkBoxL);
        holder.checkBoxP.setLayoutParams(params);
        holder.checkBoxP.setEnabled(false);
        holder.checkBoxP.setTypeface(type);
        if (data.getLetters().containsKey(Constants.P)) {
            holder.checkBoxP.setVisibility(View.VISIBLE);
            holder.checkBoxP.setChecked(data.getLetters().get(Constants.P));
        }

        params = (RelativeLayout.LayoutParams) holder.checkBoxL.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_BOTTOM);
        params.removeRule(RelativeLayout.ALIGN_BASELINE);
        params.removeRule(RelativeLayout.END_OF);
        params.addRule(RelativeLayout.START_OF, R.id.checkBoxV);
        holder.checkBoxL.setLayoutParams(params);
        holder.checkBoxL.setEnabled(false);
        holder.checkBoxL.setTypeface(type);
        if (data.getLetters().containsKey(Constants.L)) {
            holder.checkBoxL.setVisibility(View.VISIBLE);
            holder.checkBoxL.setChecked(data.getLetters().get(Constants.L));
        }

        params = (RelativeLayout.LayoutParams) holder.checkBoxV.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_BOTTOM);
        params.removeRule(RelativeLayout.ALIGN_BASELINE);
        params.removeRule(RelativeLayout.END_OF);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        holder.checkBoxV.setLayoutParams(params);
        holder.checkBoxV.setEnabled(false);
        holder.checkBoxV.setTypeface(type);
        if (data.getLetters().containsKey(Constants.V)) {
            holder.checkBoxV.setVisibility(View.VISIBLE);
            holder.checkBoxV.setChecked(data.getLetters().get(Constants.V));
        }

        holder.classRoom.setVisibility(View.GONE);
        holder.kidPicture.setVisibility(View.GONE);
        holder.arrowPicture.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dataSetFilter.size();
    }

    /**
     * add
     *
     * @param kid StudentAttendance
     */
    private void add(StudentAttendance kid) {
        dataSetOriginal.add(kid);
        dataSetFilter.add(kid);
        notifyItemInserted(dataSetFilter.size() - 1);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                final FilterResults results = new FilterResults();
                final List<StudentAttendance> dataSetFilter = new ArrayList<>();

                if (constraint.length() == 0) {
                    dataSetFilter.addAll(dataSetOriginal);
                } else {
                    for (final StudentAttendance kid : dataSetOriginal) {
                        if (StringUtils.containsIgnoreCase(kid.getKidName(),
                                constraint.toString())) {
                            dataSetFilter.add(kid);
                        }
                    }
                }

                results.values = dataSetFilter;
                results.count = dataSetFilter.size();
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(final CharSequence constraint,
                                          final FilterResults results) {
                if (results.values instanceof List) {
                    dataSetFilter = ((List<StudentAttendance>) results.values);
                    sort();
                }
            }
        };
    }

    /**
     * sort
     */
    private void sort() {
        Log.i(TAG, "sort");

        if (dataSetFilter != null
                && dataSetFilter.size() > 1) {
            Collections.sort(dataSetFilter, getComparator());
        }

        notifyDataSetChanged();
    }

    /**
     * getComparator
     *
     * @return Comparator
     */
    private Comparator<StudentAttendance> getComparator() {
        return new Comparator<StudentAttendance>() {
            @Override
            public int compare(StudentAttendance kid1,
                               StudentAttendance kid2) {
                return kid1.toString().compareTo(kid2.toString());
            }
        };
    }
}

