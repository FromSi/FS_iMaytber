package kz.sgq.fs_imaytber.util;


import java.util.ArrayList;
import java.util.List;

public class Stikers {
    private static final int ONE_STIKERS_PACK = 16;

    public int getOneStikersPack() {
        return ONE_STIKERS_PACK;
    }

    public static List<String> getOneStikerPack() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ONE_STIKERS_PACK; i++) {
            if (i <= 10)
                list.add("stiker_0_0" + i);
            else
                list.add("stiker_0_" + i);
        }
        return list;
    }
}
