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

    public static List<String> getTwoStikerPack() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ONE_STIKERS_PACK; i++) {
            if (i <= 10)
                list.add("stiker_1_0" + i);
            else
                list.add("stiker_1_" + i);
        }
        return list;
    }

    public static List<String> getThreeStikerPack() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ONE_STIKERS_PACK; i++) {
            if (i <= 10)
                list.add("stiker_2_0" + i);
            else
                list.add("stiker_2_" + i);
        }
        return list;
    }

    public static List<String> getFourStikerPack() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ONE_STIKERS_PACK; i++) {
            if (i <= 10)
                list.add("stiker_3_0" + i);
            else
                list.add("stiker_3_" + i);
        }
        return list;
    }

    public static List<String> getFiveStikerPack() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ONE_STIKERS_PACK; i++) {
            if (i <= 10)
                list.add("stiker_4_0" + i);
            else
                list.add("stiker_4_" + i);
        }
        return list;
    }
}
