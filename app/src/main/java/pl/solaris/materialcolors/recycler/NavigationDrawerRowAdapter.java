package pl.solaris.materialcolors.recycler;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.solaris.materialcolors.R;
import pl.solaris.materialcolors.model.MaterialPallete;
import pl.solaris.materialcolors.utils.ColorUtils;
import pl.solaris.materialcolors.widget.CircleTextView;

/**
 * Created by pbednarz on 23.01.14.
 */

public class NavigationDrawerRowAdapter extends ArrayAdapter<MaterialPallete> {

    Activity activity;
    Resources resources;
    private ArrayList<MaterialPallete> items = new ArrayList<MaterialPallete>();

    public NavigationDrawerRowAdapter(Activity actv, ArrayList<MaterialPallete> items) {
        super(actv, R.layout.list_item_drawer, items);
        this.items = items;
        this.activity = actv;
        this.resources = activity.getResources();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_drawer, null);
            RowViewHolder viewHolder = new RowViewHolder();
            viewHolder.tv = (CircleTextView) rowView.findViewById(R.id.letter);
            viewHolder.text = (TextView) rowView.findViewById(R.id.text);
            rowView.setTag(viewHolder);
        }

        RowViewHolder holder = (RowViewHolder) rowView.getTag();
        MaterialPallete item = items.get(position);
        holder.tv.setColor(item.getColor());
        holder.tv.setText(item.getTitle().substring(0, 1));
        holder.tv.setTextColor(ColorUtils.getTextColorForBackground(item.getColor(), ColorUtils.MIN_CONTRAST_TITLE_TEXT));
        holder.text.setText(item.getTitle());
        return rowView;
    }

    static class RowViewHolder {
        public CircleTextView tv;
        public TextView text;
    }
}