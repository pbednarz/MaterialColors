package pl.solaris.materialcolors.model;

import java.io.Serializable;

/**
 * Created by pbednarz on 2014-05-15.
 */
public class NavigationDrawerItem implements Serializable {
    private String title;
    private int color;
    private boolean isAllItem;

    public NavigationDrawerItem(String title, int color, boolean isAllItem) {
        this.title = title;
        this.color = color;
        this.isAllItem = isAllItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
