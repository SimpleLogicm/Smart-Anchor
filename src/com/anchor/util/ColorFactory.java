package com.anchor.util;

import android.graphics.Color;

class ColorFactory {

    static final int RANDOM = 0;
    static final int PURE_CYAN = 1;
    static final int PURE_TEAL = 2;
    private static final String BG_COLOR_ALPHA = "33";
    private static final String BD_COLOR_ALPHA = "88";
    private static final String RED = "F44336";
    private static final String LIGHTBLUE = "03A9F4";
    private static final String AMBER = "FFC107";
    private static final String ORANGE = "FF9800";
    private static final String YELLOW = "FFEB3B";
    private static final String LIME = "CDDC39";
    private static final String BLUE = "2196F3";
    private static final String INDIGO = "3F51B5";
    private static final String LIGHTGREEN = "8BC34A";
    private static final String GREY = "9E9E9E";
    private static final String DEEPPURPLE = "673AB7";
    private static final String TEAL = "009688";
    private static final String CYAN = "00BCD4";
    private static final int SHARP666666 = Color.parseColor("#FF666666");
    private static final int SHARP727272 = Color.parseColor("#FF727272");
    private static final String[] COLORS = new String[]{RED, LIGHTBLUE, AMBER, ORANGE, YELLOW,
            LIME, BLUE, INDIGO, LIGHTGREEN, GREY, DEEPPURPLE, TEAL, CYAN};

    static int[] onRandomBuild() {
        int random = (int) (Math.random() * COLORS.length);
        int bgColor = Color.parseColor("#" + BG_COLOR_ALPHA + COLORS[random]);
        int bdColor = Color.parseColor("#" + BD_COLOR_ALPHA + COLORS[random]);
        return new int[]{bgColor, bdColor, SHARP666666};
    }

    static int[] onPureBuild(PURE_COLOR type) {
        String color = type == PURE_COLOR.CYAN ? CYAN : TEAL;
        int bgColor = Color.parseColor("#" + BG_COLOR_ALPHA + color);
        int bdColor = Color.parseColor("#" + BD_COLOR_ALPHA + color);
        return new int[]{bgColor, bdColor, SHARP727272};
    }

    public enum PURE_COLOR {CYAN, TEAL}
}