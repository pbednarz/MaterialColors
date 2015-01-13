package pl.solaris.materialcolors.model;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import pl.solaris.materialcolors.utils.Utils;


/**
 * Created by solaris on 2014-08-31.
 */
public class MaterialPallete implements Serializable {
    private String title;
    private String value;
    private int color;
    private int colorDark;
    private int colorResource;
    private int colorDarkResource;
    private ArrayList<MaterialColor> colors;

    public MaterialPallete(String title, String value, int colorResource, int colorDarkResource, TypedArray ids, Resources res) {
        this.title = title;
        this.value = value;
        this.color = res.getColor(colorResource);
        this.colorResource = colorResource;
        this.colorDark = res.getColor(colorDarkResource);
        this.colorDarkResource = colorDarkResource;
        this.colors = new ArrayList<MaterialColor>(ids.length());
        for (int i = 0; i < ids.length(); i++) {
            MaterialColor materialColor = new MaterialColor();
            materialColor.setColorResource(ids.getResourceId(i, -1));
            materialColor.setHex(ids.getString(i));
            String colorName = res.getResourceEntryName(ids.getResourceId(i, -1)).replace("_", " ");
            materialColor.setName(Utils.uppercaseFirstLetters(colorName));
            materialColor.setColor(ids.getColor(i, -1));
            colors.add(materialColor);
        }
        ids.recycle();
        Collections.reverse(colors);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<MaterialColor> getColors() {
        return colors;
    }

    public void setColors(ArrayList<MaterialColor> colors) {
        this.colors = colors;
    }

    public int getColorDark() {
        return colorDark;
    }

    public void setColorDark(int colorDark) {
        this.colorDark = colorDark;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }

    public int getColorDarkResource() {
        return colorDarkResource;
    }

    public void setColorDarkResource(int colorDarkResource) {
        this.colorDarkResource = colorDarkResource;
    }
}
