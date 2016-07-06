package app.example.com.mydemo.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;


@ContentView(R.layout.activity_wifi)
public class WifiSettings extends BaseActivity {

    @ViewInject(R.id.switch_btn)
    Switch switch_btn;

    @ViewInject(R.id.wifi_list)
    ListView wifi_lv;

    private WifiManager mWifiManager;
    private List<WifiConfiguration> wifiConfigList;
    private IntentFilter mIntentFilter;
    private List<ScanResult> wifiResultList;
    private WifiListAdapter arrayWifiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        mIntentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(mReceiver, mIntentFilter);

        wifiConfigList = mWifiManager.getConfiguredNetworks();
        if (wifiConfigList != null)
            Log.d("WifiSettings", "wifiConfigList.size():" + wifiConfigList.size());
        if (wifiResultList == null) {
            wifiResultList = new ArrayList<ScanResult>();
        }

        arrayWifiAdapter = new WifiListAdapter(this, wifiResultList, mWifiManager);
        wifi_lv.setAdapter(arrayWifiAdapter);

        ListOnItemClickListener wifiListListener = new ListOnItemClickListener();
        wifi_lv.setOnItemClickListener(wifiListListener);

        int wifiApState = mWifiManager.getWifiState();
        Log.d("WifiSettings", "wifiApState:" + wifiApState);
        if (wifiApState == WifiManager.WIFI_STATE_ENABLED || wifiApState == WifiManager.WIFI_STATE_ENABLING) {
            switch_btn.setChecked(true);
            wifi_lv.setVisibility(View.VISIBLE);
        } else {
            switch_btn.setChecked(false);
            wifi_lv.setVisibility(View.INVISIBLE);
        }

        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(WifiSettings.this, "isChecked", Toast.LENGTH_SHORT).show();
                    if (!mWifiManager.isWifiEnabled()) {
                        mWifiManager.setWifiEnabled(true);
                        mWifiManager.startScan();
                    }
                    wifi_lv.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(WifiSettings.this, "disChecked", Toast.LENGTH_SHORT).show();
                    if (mWifiManager.isWifiEnabled())
                        mWifiManager.setWifiEnabled(false);
                    wifi_lv.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action) ||
                    WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action) ||
                    WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {

                arrayWifiAdapter.notifyDataSetChanged();

            } else if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
                Log.e("WifiSettings", "Scan result is avalible");
                List<ScanResult> scanResults = mWifiManager.getScanResults();
                if (wifiResultList == null) {
                    wifiResultList = new ArrayList<ScanResult>();
                }
                wifiResultList.clear();
                wifiResultList.addAll(scanResults);

                arrayWifiAdapter.notifyDataSetChanged();
            }
        }
    };


    public class WifiListAdapter extends BaseAdapter {

        List<ScanResult> listScan;
        WifiManager mWifiManager;
        private LayoutInflater mInflater;

        public WifiListAdapter(Context context, List<ScanResult> listScan, WifiManager mWifiManager) {
            this.listScan = listScan;
            this.mWifiManager = mWifiManager;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            if (listScan != null) {
                return listScan.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return listScan.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.wifi_lsit_item, null);
                holder = new ViewHolder();
                holder.ssid = (TextView) convertView
                        .findViewById(R.id.ssid);
                holder.connect_state = (TextView) convertView
                        .findViewById(R.id.connect_state);
                holder.wifi_icon = (ImageView) convertView
                        .findViewById(R.id.wifi_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ScanResult sr = listScan.get(position);
            holder.ssid.setText(sr.SSID);
//			if(listScan.get(position).SSID.equals(localWifiUtils.getConnectedSSID())) {
            if (mWifiManager.getConnectionInfo() != null) {
                if (mWifiManager.getConnectionInfo().getSSID().contains(sr.SSID)) {
                    holder.connect_state.setVisibility(View.VISIBLE);
                    holder.connect_state.setText("Connected");
                } else {
                    holder.connect_state.setVisibility(View.GONE);
                }
            }


            holder.wifi_icon.getDrawable().setLevel(Math.abs(listScan.get(position).level));
            return convertView;
        }

        class ViewHolder {
            private TextView ssid;
            private TextView connect_state;
            private ImageView wifi_icon;
        }

    }


    class ListOnItemClickListener implements AdapterView.OnItemClickListener {
        private String wifiPassword;

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {


            final ScanResult sr = (ScanResult) arrayWifiAdapter.getItem(arg2);

            String wifiItem = sr.SSID;// 获得选中的设备
            Log.d("ListOnItemClickListener", wifiItem);
            int wifiItemId  = IsConfiguration(sr.SSID);
            Log.d("ListOnItemClickListener", "wifiItemId:" + wifiItemId);
            if (wifiItemId > -1) {
                mWifiManager.enableNetwork(wifiItemId, true);
                arrayWifiAdapter.notifyDataSetChanged();
            }
            else {
                WifiPswDialog pswDialog = new WifiPswDialog(WifiSettings.this,
                        new WifiPswDialog.OnCustomDialogListener() {
                            @Override
                            public void back(String str) {
                                wifiPassword = str;
                                if (wifiPassword != null) {

                                    int netId = addNetWorks(sr.SSID, wifiPassword);
                                    Log.d("ListOnItemClickListener", "netId:" + netId);
                                    if (netId > -1) {
                                        wifiConfigList = mWifiManager.getConfiguredNetworks();
                                        if (wifiConfigList != null) {
                                            for(WifiConfiguration wc : wifiConfigList) {
                                                Log.d("ListOnItemClickListener", "WifiConfiguration----" +wc.SSID);
                                            }
                                            mWifiManager.enableNetwork(wifiConfigList.get(0).networkId, true);
                                            mWifiManager.saveConfiguration();
                                        }
                                        arrayWifiAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(WifiSettings.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                                        arrayWifiAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Toast.makeText(WifiSettings.this, "Password is error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                pswDialog.show();
            }
        }
    }


    int addNetWorks(String ssid, String pwd) {
        WifiConfiguration wifiCong = new WifiConfiguration();
        wifiCong.SSID = "\"" + ssid + "\"";
        wifiCong.preSharedKey = "\"" + pwd + "\"";
        wifiCong.hiddenSSID = false;
        wifiCong.status = WifiConfiguration.Status.ENABLED;
        return mWifiManager.addNetwork(wifiCong);
    }


    public int IsConfiguration(String SSID){
        if (wifiConfigList == null) {
            wifiConfigList = mWifiManager.getConfiguredNetworks();
        }

        if (wifiConfigList != null) {
            for(int i = 0; i < wifiConfigList.size(); i++){
                if(wifiConfigList.get(i).SSID.contains(SSID)){
                    return wifiConfigList.get(i).networkId;
                }
            }
        }
        return -1;
    }

}
