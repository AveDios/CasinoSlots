package ISlots;

import java.util.HashMap;
import java.util.Map;

public interface ISlots {
    int[] slotSize = {0};
    Map<String,double[]> symbolData = new HashMap<>();

    void setSlotSize(int value);

    void game();

}
