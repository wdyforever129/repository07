package com.RegxRelvoser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class rfff {
    public static void main(String[] args) {
        List<Map<String, String>> mapList1 = new ArrayList<Map<String, String>>();
        List<Map<String, String>> mapList2 = new ArrayList<Map<String, String>>();
        for (Map<String, String> map1 : mapList1) {
            Iterator<String> iterator1 = map1.keySet().iterator();
            List<String> list1 = new ArrayList<String>();
            while (iterator1.hasNext()) {
                String str1 = iterator1.next();
                list1.add(str1);
            }
            for (Map<String, String> map2 : mapList2) {
                Iterator<String> iterator2 = map2.keySet().iterator();
                List<String> list2 = new ArrayList<String>();
                while (iterator2.hasNext()) {
                    String str2 = iterator2.next();
                    list2.add(str2);
                }
                for (String aa : list1) {
                    for (String bb : list2) {
                        if (aa.equals(bb)) {
                            String cc = map1.get(aa) + map2.get(bb);//这就是两个list中的map的key相同，value相加，你看看行了没
                        }
                    }
                }
            }
        }
    }


}
