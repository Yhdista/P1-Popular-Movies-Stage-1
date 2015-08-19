package com.yhdista.nanodegree.p1.oodesign;

/**
 * Helper enum class for sorting movies
 */
public enum SortItems {

    TITLE(0, "title"), POPULARITY(1, "popularity"), HIGHEST_RATED(2, "highest-rated"), LOWES_RATED(3, "lowest-rated");

    private final int position;
    private final String name;

    SortItems(int value, String name) {
        this.position = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public static String[] getSortItemsNames() {
        String[] names = new String[SortItems.values().length];
        int i = 0;
        for (SortItems item : SortItems.values()) {
            names[i++] = item.name;
        }
        return names;
    }


}
