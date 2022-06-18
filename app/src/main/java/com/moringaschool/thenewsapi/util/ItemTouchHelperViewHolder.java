package com.moringaschool.thenewsapi.util;

public interface ItemTouchHelperViewHolder {
    //will handle updating the appearance of a selected item while the user is dragging-and-dropping it.
    void onItemSelected();

    //will remove the 'selected' state (and therefore the corresponding changes in appearance) from an item.
    void onItemClear();
}
