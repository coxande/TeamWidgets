package possebom.com.teamswidgets.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import possebom.com.teamswidgets.DetailActivity;
import possebom.com.teamswidgets.MainActivity;
import possebom.com.teamswidgets.R;
import possebom.com.teamswidgets.controller.TWController;
import possebom.com.teamswidgets.model.Match;
import possebom.com.teamswidgets.model.Team;
import possebom.com.teamswidgets.util.Log;

/**
 * Created by alexandre on 01/11/14.
 */
public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private Team team;
    private int rowLayout;
    private DetailActivity mAct;

    public MatchesAdapter(int rowLayout, DetailActivity act) {
        this.rowLayout = rowLayout;
        this.mAct = act;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Context context = viewHolder.textViewDate.getContext();
        final Match match = team.getMatches().get(i);
        viewHolder.textViewDate.setText(match.getDateFormatted());
        setIcon(viewHolder.textViewDate, Iconify.IconValue.fa_calendar);

        viewHolder.textViewTimeRemain.setText(match.getTimeRemaining(context));
        setIcon(viewHolder.textViewTimeRemain, Iconify.IconValue.fa_clock_o);

        viewHolder.textViewLeague.setText(match.getLeague());
        setIcon(viewHolder.textViewLeague, Iconify.IconValue.fa_trophy);
        if(match.getTransmission().isEmpty()){
            viewHolder.textViewTransmission.setVisibility(View.GONE);
        }else {
            viewHolder.textViewTransmission.setText(match.getTransmission());
            setIcon(viewHolder.textViewTransmission, Iconify.IconValue.fa_eye);
        }
        viewHolder.textViewPlace.setText(match.getPlace());
        setIcon(viewHolder.textViewPlace, Iconify.IconValue.fa_location_arrow);

        String url01;
        String url02;

        Team opponent = TWController.INSTANCE.getDao().getTeamByName(match.getOpponent());
        String urlOpponent = null;
        if (opponent != null) {
            urlOpponent = opponent.getImgUrl();
        }

        if (match.getHome()) {
            url01 = team.getImgUrl();
            url02 = urlOpponent;
        } else {
            url01 = urlOpponent;
            url02 = team.getImgUrl();
        }

        Picasso.with(context)
                .load(url01)
                .placeholder(R.drawable.generic_team)
                .into(viewHolder.imageView01);


        Picasso.with(context)
                .load(url02)
                .placeholder(R.drawable.generic_team)
                .into(viewHolder.imageView02);
    }

    private void setIcon(TextView textview, Iconify.IconValue iconId) {
        final Drawable drawable = new IconDrawable(textview.getContext(), iconId).colorRes(R.color.secondary).sizeDp(20);
        textview.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public int getItemCount() {
        return team == null ? 0 : team.getMatches().size();
    }

    public void setTeam(final Team team) {
        this.team = team;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate;
        public TextView textViewTimeRemain;
        public TextView textViewLeague;
        public TextView textViewTransmission;
        public TextView textViewPlace;
        public ImageView imageView01;
        public ImageView imageView02;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView01 = (ImageView) itemView.findViewById(R.id.imageView01);
            imageView02 = (ImageView) itemView.findViewById(R.id.imageView02);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewTimeRemain = (TextView) itemView.findViewById(R.id.textViewTimeRemain);
            textViewLeague = (TextView) itemView.findViewById(R.id.textViewLeague);
            textViewTransmission = (TextView) itemView.findViewById(R.id.textViewTransmission);
            textViewPlace = (TextView) itemView.findViewById(R.id.textViewPlace);
        }

    }
}
