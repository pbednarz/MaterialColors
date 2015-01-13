package pl.solaris.materialcolors.model;

import java.io.Serializable;

/**
 * Created by solaris on 2014-08-31.
 */
public class MaterialColor implements Serializable {
    String name;
    String hex;
    int color;
    int colorResource;
    boolean isOverflowTextVisible = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }

    public boolean isOverflowTextVisible() {
        return isOverflowTextVisible;
    }

    public void setOverflowTextVisible(boolean isOverflowTextVisible) {
        this.isOverflowTextVisible = isOverflowTextVisible;
    }
}
