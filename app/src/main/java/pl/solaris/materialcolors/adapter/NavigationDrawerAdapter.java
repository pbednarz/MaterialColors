package pl.solaris.materialcolors.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.solaris.materialcolors.R;
import pl.solaris.materialcolors.model.MaterialPallete;
import pl.solaris.materialcolors.utils.ColorUtils;
import pl.solaris.materialcolors.widget.CircleTextView;

/**
 * Created by pbednarz on 23.01.14.
 */

public class NavigationDrawerAdapter extends ArrayAdapter<MaterialPallete> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<MaterialPallete> items = new ArrayList<MaterialPallete>();

    public NavigationDrawerAdapter(Context context, ArrayList<MaterialPallete> items) {
        super(context, R.layout.list_item_drawer, items);
        this.items = items;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        RowViewHolder holder;
        if (rowView == null) {
            rowView = mLayoutInflater.inflate(R.layout.list_item_drawer, null);
            holder = new RowViewHolder();
            ButterKnife.inject(holder, rowView);
            rowView.setTag(holder);
        } else {
            holder = (RowViewHolder) rowView.getTag();
        }

        MaterialPallete item = items.get(position);
        holder.tv.setColor(item.getColor());
        holder.tv.setText(item.getTitle().substring(0, 1));
        holder.tv.setTextColor(ColorUtils.getTextColorForBackground(item.getColor(), ColorUtils.MIN_CONTRAST_TITLE_TEXT));
        holder.text.setText(item.getTitle());
        return rowView;
    }

    static class RowViewHolder {

        @InjectView(R.id.letter)
        public CircleTextView tv;

        @InjectView(R.id.text)
        public TextView text;
    }
}