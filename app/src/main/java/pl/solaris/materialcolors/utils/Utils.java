package pl.solaris.materialcolors.utils;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

import pl.solaris.materialcolors.R;
import pl.solaris.materialcolors.model.MaterialPallete;

/**
 * Created by solaris on 2014-08-31.
 */
public class Utils {

    public static ArrayList<MaterialPallete> getColorsModels(Context context) {
        ArrayList<MaterialPallete> colors = new ArrayList<MaterialPallete>();
        Resources res = context.getResources();
        MaterialPallete all = new MaterialPallete(context.getString(R.string.all), context.getString(R.color.primary), R.color.primary, R.color.primary_dark, res.obtainTypedArray(R.array.all), res);
        MaterialPallete red = new MaterialPallete(context.getString(R.string.red), context.getString(R.color.red_500), R.color.red_500, R.color.red_700, res.obtainTypedArray(R.array.red), res);
        MaterialPallete pink = new MaterialPallete(context.getString(R.string.pink), context.getString(R.color.pink_500), R.color.pink_500, R.color.pink_700, res.obtainTypedArray(R.array.pink), res);
        MaterialPallete purple = new MaterialPallete(context.getString(R.string.purple), context.getString(R.color.purple_500), R.color.purple_500, R.color.purple_700, res.obtainTypedArray(R.array.purple), res);
        MaterialPallete dark_purple = new MaterialPallete(context.getString(R.string.dark_purple), context.getString(R.color.dark_purple_500), R.color.dark_purple_500, R.color.dark_purple_700, res.obtainTypedArray(R.array.dark_purple), res);
        MaterialPallete indigo = new MaterialPallete(context.getString(R.string.indigo), context.getString(R.color.indigo_500), R.color.indigo_500, R.color.indigo_700, res.obtainTypedArray(R.array.indigo), res);
        MaterialPallete blue = new MaterialPallete(context.getString(R.string.blue), context.getString(R.color.blue_500), R.color.blue_500, R.color.blue_700, res.obtainTypedArray(R.array.blue), res);
        MaterialPallete light_blue = new MaterialPallete(context.getString(R.string.light_blue), context.getString(R.color.light_blue_500), R.color.light_blue_500, R.color.light_blue_700, res.obtainTypedArray(R.array.light_blue), res);
        MaterialPallete cyan = new MaterialPallete(context.getString(R.string.cyan), context.getString(R.color.cyan_500), R.color.cyan_500, R.color.cyan_700, res.obtainTypedArray(R.array.cyan), res);
        MaterialPallete teal = new MaterialPallete(context.getString(R.string.teal), context.getString(R.color.teal_500), R.color.teal_500, R.color.teal_700, res.obtainTypedArray(R.array.teal), res);
        MaterialPallete green = new MaterialPallete(context.getString(R.string.green), context.getString(R.color.green_500), R.color.green_500, R.color.green_700, res.obtainTypedArray(R.array.green), res);
        MaterialPallete light_green = new MaterialPallete(context.getString(R.string.light_green), context.getString(R.color.light_green_500), R.color.light_green_500, R.color.light_green_700, res.obtainTypedArray(R.array.light_green), res);
        MaterialPallete lime = new MaterialPallete(context.getString(R.string.lime), context.getString(R.color.lime_500), R.color.lime_500, R.color.lime_700, res.obtainTypedArray(R.array.lime), res);
        MaterialPallete yellow = new MaterialPallete(context.getString(R.string.yellow), context.getString(R.color.yellow_500), R.color.yellow_500, R.color.yellow_700, res.obtainTypedArray(R.array.yellow), res);
        MaterialPallete amber = new MaterialPallete(context.getString(R.string.amber), context.getString(R.color.amber_500), R.color.amber_500, R.color.amber_700, res.obtainTypedArray(R.array.amber), res);
        MaterialPallete orange = new MaterialPallete(context.getString(R.string.orange), context.getString(R.color.orange_500), R.color.orange_500, R.color.orange_700, res.obtainTypedArray(R.array.orange), res);
        MaterialPallete deep_orange = new MaterialPallete(context.getString(R.string.deep_orange), context.getString(R.color.deep_orange_500), R.color.deep_orange_500, R.color.deep_orange_700, res.obtainTypedArray(R.array.deep_orange), res);
        MaterialPallete brown = new MaterialPallete(context.getString(R.string.brown), context.getString(R.color.brown_500), R.color.brown_500, R.color.brown_700, res.obtainTypedArray(R.array.brown), res);
        MaterialPallete grey = new MaterialPallete(context.getString(R.string.grey), context.getString(R.color.grey_500), R.color.grey_500, R.color.grey_700, res.obtainTypedArray(R.array.grey), res);
        MaterialPallete blue_grey = new MaterialPallete(context.getString(R.string.blue_grey), context.getString(R.color.blue_grey_500), R.color.blue_grey_500, R.color.blue_grey_700, res.obtainTypedArray(R.array.blue_grey), res);

        colors.add(all);
        colors.add(red);
        colors.add(pink);
        colors.add(purple);
        colors.add(dark_purple);
        colors.add(indigo);
        colors.add(blue);
        colors.add(light_blue);
        colors.add(cyan);
        colors.add(teal);
        colors.add(green);
        colors.add(light_green);
        colors.add(lime);
        colors.add(yellow);
        colors.add(amber);
        colors.add(orange);
        colors.add(deep_orange);
        colors.add(brown);
        colors.add(grey);
        colors.add(blue_grey);
        return colors;
    }

    public static String uppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    public static void copyToClipboard(Context context, String value) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(value);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Color", value);
            clipboard.setPrimaryClip(clip);
        }
    }
}
