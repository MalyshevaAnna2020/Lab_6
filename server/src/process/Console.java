package process;

import spacemarine.SpaceMarine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class Console {

    public Hashtable<String, SpaceMarine> console(String s) {
        Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
        Hashtable<String, SpaceMarine> hashtable = new Hashtable<>();
        List<String> list = new ArrayList<>();
        while(s.contains("\n")) {
            String line = s.substring(0, s.indexOf("\n"));
            if (s.length() > 2) {
                s = s.substring(s.indexOf("\n") + 1);
            }else{break;}
            int equals = line.indexOf("/>");
            if (equals > -1) {
                SpaceMarine spaceMarine = new SpaceMarine();
                spaceMarine.setSpaceMarine(line, subhashtable.size() + 1);
                String key = line.substring(line.indexOf("<") + 1, 
                        line.substring(line.indexOf("<")).indexOf(" ") + line.indexOf("<") + 1).trim();
                if (key.equals("")){
                    key = "id_" + hashtable.size() + 1;
                }
                subhashtable.put(key, spaceMarine);
                list.add(key);
            }
        }

        list.sort(Comparator.naturalOrder());
        for (String key : list) {
            hashtable.put(key, subhashtable.get(key));
        }
        return hashtable;
    }

}
