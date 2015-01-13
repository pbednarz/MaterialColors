package pl.solaris.materialcolors.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.solaris.materialcolors.R;
import pl.solaris.materialcolors.model.MaterialColor;
import pl.solaris.materialcolors.utils.ColorUtils;
import pl.solaris.materialcolors.utils.Utils;

/**
 * Created by solaris on 2014-08-23.
 */
public class ColorListAdapter extends ArrayAdapter<MaterialColor> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<MaterialColor> colors;
    private int lastPosition = -1;
    private PopupMenu mPopupMenu;

    public ColorListAdapter(Context context, int resource, ArrayList<MaterialColor> objects) {
        super(context, resource, objects);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        colors = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        if (rowView == null) {
            rowView = mLayoutInflater.inflate(R.layout.list_item_color, null);
            holder = new ViewHolder();
            ButterKnife.inject(holder, rowView);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

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
                mPopupMenu = new PopupMenu(view.getContext(), view);
                mPopupMenu.getMenuInflater().inflate(R.menu.color, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new ColorMenuClickListener(view.getContext(), item.getHex()));
                mPopupMenu.show();
            }
        });

        holder.cardView.setCardBackgroundColor(item.getColor());
        Animation animation = AnimationUtils.loadAnimation(holder.cardView.getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        holder.cardView.startAnimation(animation);
        lastPosition = position;
        return rowView;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public long getItemId(int position) {
        return colors.get(position).getColor();
    }

    public void setColors(ArrayList<MaterialColor> colors) {
        this.colors = colors;
    }

    public void resetPosition() {
        lastPosition = -1;
    }

    static class ViewHolder {

        @InjectView(R.id.title_tv)
        public TextView tvName;

        @InjectView(R.id.description_tv)
        public TextView tvDescription;

        @InjectView(R.id.menu_overflow)
        public ImageButton imButtonOverflow;

        @InjectView(R.id.card_view)
        public CardView cardView;
    }

    private class ColorMenuClickListener implements PopupMenu.OnMenuItemClickListener {

        private String mHex;
        private Context mContext;

        private ColorMenuClickListener(Context mContext, String mHex) {
            this.mContext = mContext;
            this.mHex = mHex;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_copy) {
                Utils.copyToClipboard(mContext, mHex);
                Toast.makeText(mContext, mHex + " copied!", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
}
