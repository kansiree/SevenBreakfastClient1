package th.co.gosoft.storemobile.sevenbreakfastclient.data.bean;

import java.util.List;

/**
 * Created by print on 9/11/2017.
 */

public class mapData extends BaseBean {
    List<MapData> listMap;

    public List<MapData> getListMap() {
        return listMap;
    }

    public void setListMap(List<MapData> listMap) {
        this.listMap = listMap;
    }

    public static class MapData{
        String name;
        String latiture;
        String longtiture;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLatiture() {
            return latiture;
        }

        public void setLatiture(String latiture) {
            this.latiture = latiture;
        }

        public String getLongtiture() {
            return longtiture;
        }

        public void setLongtiture(String longtiture) {
            this.longtiture = longtiture;
        }
    }
}
