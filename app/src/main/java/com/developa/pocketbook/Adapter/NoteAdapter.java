package com.developa.pocketbook.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.R;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteHolder> {

    private OnItemClickListener mListener;

    public NoteAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getTitle().equals(t1.getTitle()) && note.getBody().equals(t1.getBody())
                    && note.getTimeStamp().equals(t1.getTimeStamp());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item,viewGroup,false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note currentNote = getItem(i);

        noteHolder.noteImage.setText(currentNote.getTitle());
        noteHolder.noteTitle.setText(currentNote.getTitle());
        noteHolder.noteBody.setText(currentNote.getBody());
        noteHolder.noteTimeStamp.setText(currentNote.getTimeStamp());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int randomColor = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder().buildRound("",randomColor);
        noteHolder.noteImage.setBackgroundDrawable(drawable);

    }

    public Note getNoteAt(int position){
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView noteImage;
        private TextView noteTitle;
        private TextView noteBody;
        private TextView noteTimeStamp;

        NoteHolder(@NonNull View itemView) {
            super(itemView);

            noteImage = itemView.findViewById(R.id.note_image);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteBody = itemView.findViewById(R.id.note_body);
            noteTimeStamp = itemView.findViewById(R.id.note_time_stamp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION)
                        mListener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
