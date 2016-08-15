package app.example.com.mydemo.contact;

import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

@ContentView(R.layout.contact)
public class ContactListActivity extends BaseActivity {

    @ViewInject(R.id.contact_list)
    ListView contactList;

    @ViewInject(R.id.contact_search_btn)
    TextView contact_search_btn;

    private ContactListAdapter adapter;
    private List<ContactBean> list;
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象

    @ViewInject(R.id.fast_scroller)
    private QuickAlphabeticBar alphabeticBar; // 快速索引条
    private Map<Integer, ContactBean> contactIdMap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
        init();

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ContactListActivity", "position:" + position);
                Log.d("ContactListActivity", "id:" + id);
                Log.d("ContactListActivity", list.get(position).getPhoneNum());

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list.get(position).getPhoneNum()));
                if (ActivityCompat.checkSelfPermission(ContactListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
    }




    /**
     * 初始化数据库查询参数
     */
    private void init() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI};
        // 按照sort_key升序查詢
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");

    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                contactIdMap = new HashMap<Integer, ContactBean>();
                list = new ArrayList<ContactBean>();
                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);
                    if (contactIdMap.containsKey(contactId)) {
                        // 无操作
                    } else {
                        // 创建联系人对象
                        ContactBean contact = new ContactBean();
                        contact.setDesplayName(name);
                        contact.setPhoneNum(number);
                        contact.setSortKey(sortKey);
                        contact.setPhotoId(photoId);
                        contact.setLookUpKey(lookUpKey);
                        list.add(contact);

                        contactIdMap.put(contactId, contact);
                    }
                }
                if (list.size() > 0) {
                    setAdapter(list);
                }
            }

            super.onQueryComplete(token, cookie, cursor);
        }
    }

    private void setAdapter(List<ContactBean> list) {
        adapter = new ContactListAdapter(this, list, alphabeticBar);
        contactList.setAdapter(adapter);
        alphabeticBar.init(ContactListActivity.this);
        alphabeticBar.setListView(contactList);
        alphabeticBar.setHight(alphabeticBar.getHeight());
        alphabeticBar.setVisibility(View.VISIBLE);
    }
}
