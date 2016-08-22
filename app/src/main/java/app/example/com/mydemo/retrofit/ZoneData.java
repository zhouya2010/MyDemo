package app.example.com.mydemo.retrofit;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */

public class ZoneData {

    public int error;
    public String msg;
    public List<ZoneInfo> datas;


    /**
     * id : 26381
     * zonename : zone_1
     * num : 1
     * status : 1
     * image :
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String zonename;
        private String num;
        private String status;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getZonename() {
            return zonename;
        }

        public void setZonename(String zonename) {
            this.zonename = zonename;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
