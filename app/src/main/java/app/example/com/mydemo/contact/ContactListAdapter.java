package app.example.com.mydemo.contact;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import app.example.com.mydemo.R;

public class ContactListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ContactBean> list;
	private HashMap<String, Integer> alphaIndexer; // 字母索引
	private String[] sections; // 存储每个章节
	private Context ctx;

	public ContactListAdapter(Context context, List<ContactBean> list,
			QuickAlphabeticBar alpha) {
		this.ctx = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.alphaIndexer = new HashMap<String, Integer>();
		this.sections = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			// 得到字母
			String name = getAlpha(list.get(i).getSortKey());
			if (!alphaIndexer.containsKey(name)) {
				alphaIndexer.put(name, i);
			}
		}

		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);

		alpha.setAlphaIndexer(alphaIndexer);

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void remove(int position) {
		list.remove(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.contact_list_item, null);
			holder = new ViewHolder();
			holder.quickContactBadge = (ImageView) convertView
					.findViewById(R.id.qcb);
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView) convertView.findViewById(R.id.name);

//			holder.number = (TextView) convertView.findViewById(R.id.number);
			holder.isSelect = (CheckBox) convertView.findViewById(R.id.contact_select_checkbox);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ContactBean contact = list.get(position);
		String name = contact.getDesplayName();
		String number = contact.getPhoneNum();
		holder.name.setText(name);
//		holder.number.setText(number);
//		holder.quickContactBadge.assignContactUri(ContactsContract.Contacts.getLookupUri(
//				contact.getContactId(), contact.getLookUpKey()));
//		if (0 == contact.getPhotoId()) {
//			holder.quickContactBadge.setImageResource(R.mipmap.touxiang);
//		} else {
//			Uri uri = ContentUris.withAppendedId(
//					ContactsContract.Contacts.CONTENT_URI,
//					contact.getContactId());
//			InputStream input = ContactsContract.Contacts
//					.openContactPhotoInputStream(ctx.getContentResolver(), uri);
//			Bitmap contactPhoto = BitmapFactory.decodeStream(input);
//			holder.quickContactBadge.setImageBitmap(contactPhoto);
//		}
		// 当前字母
		String currentStr = getAlpha(contact.getSortKey());
		// 前面的字母
		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(
				position - 1).getSortKey()) : " ";

		if (!previewStr.equals(currentStr)) {
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else {
			holder.alpha.setVisibility(View.GONE);
		}

		holder.isSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d("ContactListAdapter", "isChecked:" + isChecked + "  position: "+position);
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		ImageView quickContactBadge;
		TextView alpha;
		TextView name;
		TextView number;
		CheckBox isSelect;
	}

	/**
	 * ��ȡ����ĸ
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// ������ʽƥ��
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // ��Сд��ĸת��Ϊ��д
		} else {
			return "#";
		}
	}
}
