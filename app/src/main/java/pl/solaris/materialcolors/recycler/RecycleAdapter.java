package pl.solaris.materialcolors.recycler;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import pl.solaris.materialcolors.R;
import pl.solaris.materialcolors.model.MaterialColor;
import pl.solaris.materialcolors.utils.ColorUtils;
import pl.solaris.materialcolors.utils.Utils;

/**
 * PGS-Software
 * Created by pbednarz on 2015-01-05.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<MaterialColor> colors;
    private PopupMenu mPopupMenu;
    private int lastPosition = -1;

    public RecycleAdapter(List<MaterialColor> colors) {
        this.colors = colors;
    }

    public void setColors(List<MaterialColor> colors) {
        this.colors = colors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_color, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MaterialColor item = colors.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDescription.setText(item.getHex());

        int color = item.getColor();
        holder.tvName.setTextColor(ColorUtils.getTextColorForBackground(color, ColorUtils.MIN_CONTRAST_TITLE_TEXT));
        holder.tvDescription.setTextColor(ColorUtils.getTextColorForBackground(color, ColorUtils.MIN_CONTRAST_TITLE_TEXT));

        holder.imButtonOverflow.setTag(position);
        holder.imButtonOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                MaterialColor item = colors.get(position);
                if (mPopupMenu != null) {
                    mPopupMenu.dismiss();
                }
                mPopupMenu = new PopupMenu(holder.llTop.getContext(), view);
                mPopupMenu.getMenuInflater().inflate(R.menu.color, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new ColorMenuClickListener(item.getHex(), holder.llTop.getContext()));
                mPopupMenu.show();
            }
        });


        holder.llTop.setCardBackgroundColor(item.getColor());
        Animation animation = AnimationUtils.loadAnimation(holder.llTop.getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        holder.rowView.startAnimation(animation);
        lastPosition = position;
    }


    @Override
    public int getItemCount() {
        return colors.size();
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDescription;
        public ImageButton imButtonOverflow;
        public CardView llTop;
        public View rowView;

        public ViewHolder(View itemView) {
            super(itemView);
            rowView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            imButtonOverflow = (ImageButton) itemView.findViewById(R.id.menu_overflow);
            llTop = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    private class ColorMenuClickListener implements PopupMenu.OnMenuItemClickListener {

        private String hex;
        private Context context;

        private ColorMenuClickListener(String hex, Context context) {
            this.hex = hex;
            this.context = context;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_copy) {
                Utils.copyToClipboard(context, hex);
                return true;
            }
            return false;
        }
    }

}
