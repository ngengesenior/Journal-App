package com.example.ngenge.journal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ngenge.journal.R;
import com.example.ngenge.journal.room.JournalEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.JournalViewHolder> {

    private List<JournalEntry> journalEntryList;

    final private ItemClickListener mListener;

    public interface ItemClickListener{
        void onItemClicked(int position);
    }

    public JournalRecyclerAdapter(ItemClickListener listener)
    {
        mListener = listener;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journal_item_layout, parent, false);

        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {

        ColorGenerator generator = ColorGenerator.MATERIAL;
        if (journalEntryList != null) {
            JournalEntry currentEntry = journalEntryList.get(position);
            holder.textViewTitle.setText(currentEntry.getTitle());
            holder.textViewDate.setText(currentEntry.getDate().toString());
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(currentEntry.getTitle().substring(0, 1),
                            generator.getRandomColor());
            holder.journal_icon.setImageDrawable(drawable);
        } else {
            holder.textViewTitle.setText("No Journal");
        }
    }

    public List<JournalEntry> getJournalEntryList() {
        return journalEntryList;
    }

    @Override
    public int getItemCount() {
        if (journalEntryList != null) {
            return journalEntryList.size();
        }

        return 0;
    }

    public void setJournalEntryList(List<JournalEntry> journalEntryList) {
        this.journalEntryList = journalEntryList;
        notifyDataSetChanged();
    }

    public JournalEntry getItemAtPosition(int pos)
    {
        return journalEntryList.get(pos);
    }


    public class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.journal_title)
        TextView textViewTitle;

        @BindView(R.id.journal_date)
        TextView textViewDate;

        @BindView(R.id.journal_icon)
        ImageView journal_icon;

        public JournalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            mListener.onItemClicked(position);
        }
    }
}
