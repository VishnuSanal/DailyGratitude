package phone.vishnu.dailygratitude;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends ListAdapter<Gratitude, RecyclerViewAdapter.GratitudeHolder> {

    public RecyclerViewAdapter() {
        super(new DiffUtil.ItemCallback<Gratitude>() {
            @Override
            public boolean areItemsTheSame(@NonNull Gratitude oldItem, @NonNull Gratitude newItem) {
                return oldItem.get_id() == newItem.get_id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Gratitude oldItem, @NonNull Gratitude newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getDescription().equals(newItem.getDescription());
            }
        });
    }

    @NonNull
    @Override
    public GratitudeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new GratitudeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GratitudeHolder holder, int position) {
//        holder.setIsRecyclable(false);

        Gratitude currentGratitude = getItem(position);
        holder.titleTV.setText(currentGratitude.getTitle());
        holder.descriptionTV.setText(currentGratitude.getDescription());
        holder.addedTV.setText(currentGratitude.getDateAdded());
    }

    class GratitudeHolder extends RecyclerView.ViewHolder {
        private TextView titleTV, descriptionTV, addedTV;

        public GratitudeHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.todoTitle);
            descriptionTV = itemView.findViewById(R.id.todoDescription);
            addedTV = itemView.findViewById(R.id.addedTV);
        }
    }
}
