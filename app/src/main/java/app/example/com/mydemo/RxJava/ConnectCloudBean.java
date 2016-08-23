package app.example.com.mydemo.RxJava;

/**
 * Created by Administrator on 2016/8/23.
 */

public class ConnectCloudBean {

    /**
     * error : 200
     * msg :
     * data : {"connuid":"cf01ee67c1e78f07b1c73b5c5fb32972","key":"nxeco_2922"}
     */

    private int error;
    private String msg;
    /**
     * connuid : cf01ee67c1e78f07b1c73b5c5fb32972
     * key : nxeco_2922
     */

    private DataBean data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String connuid;
        private String key;

        public String getConnuid() {
            return connuid;
        }

        public void setConnuid(String connuid) {
            this.connuid = connuid;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
