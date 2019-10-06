package com.developa.pocketbook.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.allyants.notifyme.NotifyMe;
import com.developa.pocketbook.Adapter.ReminderAdapter;
import com.developa.pocketbook.Model.Reminder;
import com.developa.pocketbook.R;
import com.developa.pocketbook.ViewModel.ReminderViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReminderFragment extends Fragment {

    private ReminderViewModel mReminderViewModel;
    private View customWarningToast;
    private View customSuccessToast;
    private Calendar mCalendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reminder_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.reminder_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final ReminderAdapter adapter = new ReminderAdapter();
        recyclerView.setAdapter(adapter);

        mReminderViewModel = ViewModelProviders.of(getActivity()).get(ReminderViewModel.class);
        mReminderViewModel.getAllReminders().observe(getViewLifecycleOwner(), new Observer<List<Reminder>>() {
            @Override
            public void onChanged(@Nullable List<Reminder> reminders) {
                adapter.submitList(reminders);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mReminderViewModel.deleteReminder(adapter.getReminderAt(viewHolder.getAdapterPosition()));
                Snackbar snackbar = Snackbar.make(view.findViewById(R.id.reminder_fragment), "Reminder deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //restore reminder
                    }
                });
                snackbar.show();
            }
        }).attachToRecyclerView(recyclerView);

        customWarningToast = getLayoutInflater().inflate(R.layout.custom_warning_toast, (ViewGroup) view.findViewById(R.id.warning_toast));
        customSuccessToast = getLayoutInflater().inflate(R.layout.custom_success_toast, (ViewGroup) view.findViewById(R.id.success_toast));
        FloatingActionButton addReminder = view.findViewById(R.id.button_add_reminder);
        addReminder.setOnClickListener(newReminderListener);
    }

    private View.OnClickListener newReminderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fireCustomDialog(null);
        }
    };

    private void fireCustomDialog(final Reminder reminder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_edit_reminder);

        TextView reminderTitle = dialog.findViewById(R.id.new_reminder_title);
        final EditText editReminder = dialog.findViewById(R.id.add_reminder_body);
        final TextView reminderDate = dialog.findViewById(R.id.add_reminder_date);
        final TextView reminderTime = dialog.findViewById(R.id.add_reminder_time);
        ImageButton createReminder = dialog.findViewById(R.id.button_new_reminder);
        ImageButton cancelOperation = dialog.findViewById(R.id.button_cancel_reminder);
        final boolean editOperation = reminder != null;

        //default date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        String defaultDate = format.format(date);

        //default time//
        SimpleDateFormat format1 = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
        String defaultTime = format1.format(date);

        reminderDate.setText(defaultDate);
        reminderTime.setText(defaultTime);

       //date picker dialog
        final DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR,year);
                mCalendar.set(Calendar.MONTH,month);
                mCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                String notificationDate = simpleDateFormat.format(mCalendar.getTime());
                reminderDate.setText(notificationDate);
            }
        };

        //time picker dialog
        final TimePickerDialog.OnTimeSetListener timePickerDialog = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                mCalendar.set(Calendar.MINUTE,minute);

                SimpleDateFormat timeFormat = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
                String notificationTime = timeFormat.format(mCalendar.getTime());
                reminderTime.setText(notificationTime);
            }
        };

        reminderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),datePickerDialog,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        reminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(),timePickerDialog,mCalendar.get(Calendar.HOUR_OF_DAY),mCalendar.get(Calendar.MINUTE),false).show();
            }
        });

        if (editOperation) {
            reminderTitle.setText(getString(R.string.edit_reminder_text));
            editReminder.setText(reminder.getBody());
        }

        createReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reminderText = editReminder.getText().toString();
                String notifyDate = reminderDate.getText().toString();
                String notifyTime = reminderTime.getText().toString();

                if (editOperation) {
                    Reminder reminderEdited = new Reminder(reminderText);
                    reminderEdited.setNotifyDate(notifyDate);
                    reminderEdited.setNotifyTime(notifyTime);
                    mReminderViewModel.updateReminder(reminderEdited);
                    sendNotification(reminderEdited);
                } else {
                    if (!reminderText.isEmpty()) {
                        Reminder newReminder = new Reminder(reminderText);
                        newReminder.setNotifyDate(notifyDate);
                        newReminder.setNotifyTime(notifyTime);
                        mReminderViewModel.insertReminer(newReminder);
                        sendNotification(newReminder);

                        Toast toast = Toast.makeText(getActivity(), null, Toast.LENGTH_LONG);
                        toast.setView(customSuccessToast);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getActivity(), null, Toast.LENGTH_LONG);
                        toast.setView(customWarningToast);
                        toast.show();
                    }
                }
                dialog.dismiss();
            }
        });

        cancelOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

  private void sendNotification(Reminder reminder){

      new NotifyMe.Builder(getContext())
              .title("New Pocketbook Notification")
              .content(reminder.getBody())
              .color(255, 0, 0, 255)
              .led_color(0, 0, 0, 0)
              .time(mCalendar)
              .small_icon(R.mipmap.ic_launcher_round)
              .build();
    }
}
