package com.boss.testoverwatchherocompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    List<Hero> listOfHeroes;
    LayoutInflater layoutInflater;
    MainActivity mainActivity;

    public RecyclerViewAdapter(Context applicationContext, List<Hero> listOfHeroes, MainActivity mainActivity)
    {
        this.listOfHeroes = listOfHeroes;
        this.layoutInflater = (LayoutInflater.from(applicationContext));
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.hero,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.heroName.setText((listOfHeroes.get(position).heroName));
        holder.heroImage.setImageResource(listOfHeroes.get(position).heroImage);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int firstVisiblePosition = mainActivity.gridLayoutManager.findFirstVisibleItemPosition();
                mainActivity.firstVisibleItem = firstVisiblePosition;
                mainActivity.firstVisibleItemOffset = mainActivity.gridLayoutManager.findViewByPosition(firstVisiblePosition).getTop();
                mainActivity.displayHeroInformation(listOfHeroes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfHeroes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView heroName;
        ImageView heroImage;
        View container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            heroName = itemView.findViewById(R.id.gridViewHeroNameTextView);
            heroImage = itemView.findViewById(R.id.gridViewHeroImageImageView);
        }
    }
}
