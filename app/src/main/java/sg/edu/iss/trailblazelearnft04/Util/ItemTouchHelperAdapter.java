package sg.edu.iss.trailblazelearnft04.Util;

/**
 * Created by Neelam on 3/15/2018.
 */



public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
