package com.developa.pocketbook.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.Model.Reminder;
import com.developa.pocketbook.R;

public class ReminderAdapter extends ListAdapter<Reminder,ReminderAdapter.ReminderHolder> {

    public ReminderAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Reminder> DIFF_CALLBACK = new DiffUtil.ItemCallback<Reminder>() {
        @Override
        public boolean areItemsTheSame(@NonNull Reminder reminder, @NonNull Reminder t2) {
            return reminder.getId() == t2.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reminder reminder, @NonNull Reminder t2) {
            return reminder.getBody().equals(t2.getBody());
        }
    };

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reminder_item,viewGroup,false);
        return new ReminderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder reminderHolder, int i) {
        Reminder currentReminder = getItem(i);

        reminderHolder.reminderBody.setText(currentReminder.getBody());
        reminderHolder.reminderTimeStamp.setText(currentReminder.getNotifyDate() + " " + currentReminder.getNotifyTime());

    }

    public Reminder getReminderAt(int position){
        return getItem(position);
    }

    class ReminderHolder extends RecyclerView.ViewHolder {

        private TextView reminderBody;
        private TextView reminderTimeStamp;

        public ReminderHolder(@NonNull View itemView) {
            super(itemView);

            reminderBody = itemView.findViewById(R.id.reminder_body);
            reminderTimeStamp = itemView.findViewById(R.id.reminder_time_stamp);
        }
    }
}
