package app.example.com.mydemo.database;

import android.content.ContentValues;

public final class ScheduleData {
	
	public String lable;
	
	public String start_time;
//	public String end_time;
	public int daysOfWeek;
	
	public int _id;
	public int zone;
	public int interval;
	public int timeId;
	public int groupId;

	/**
	 * 0:uploaded
	 * 1:not upload yet
	 */
	public boolean isUpload;
	
	/**
	 * 0:unable
	 * 1:enable
	 */
	public boolean enable;
	
	/**
	 * Implementation state of schedule
	 * 0:Not implemented;
	 * 1:Only start implemented;
	 * 2:Only stop implemented;
	 * 3:Both of start and stop implemented;
	 */
	public int state;
	
	public static final String ID = "_id";
	public static final String ZONE = "zone";
	public static final String START_TIME = "start_time";
	public static final String END_TIME = "end_time";
	public static final String REPEAT = "repeat";
	public static final String INTERVAL = "interval";
	public static final String TIME_ID = "time_id";
	public static final String GROUP_ID = "group_id";
	public static final String IS_UPLOAD = "isUpload";
	public static final String ENABLE = "enable";
	public static final String LABLE = "lable";
	public static final String STATE = "state";
	
	public ScheduleData(){
//		daysOfWeek = new DaysOfWeek(0);
	}
	
	public ContentValues getContentValues(){
		 ContentValues cv = new ContentValues();
		 cv.put(ZONE, zone);
		 cv.put(START_TIME, start_time);
		 cv.put(INTERVAL, interval);
		 cv.put(REPEAT, daysOfWeek);
		 cv.put(TIME_ID, timeId);
		 cv.put(GROUP_ID, groupId);
		 cv.put(IS_UPLOAD, isUpload);
		 cv.put(ENABLE, enable);
		 cv.put(LABLE, lable);
		 
		return cv;
		 
	 }
	
//	/*
//     * Days of week code as a single int.
//     * 0x00: no day
//     * 0x01: Monday
//     * 0x02: Tuesday
//     * 0x04: Wednesday
//     * 0x08: Thursday
//     * 0x10: Friday
//     * 0x20: Saturday
//     * 0x40: Sunday
//     */
//    public static final class DaysOfWeek {
//
//        private static int[] DAY_MAP = new int[] {
//            Calendar.MONDAY,
//            Calendar.TUESDAY,
//            Calendar.WEDNESDAY,
//            Calendar.THURSDAY,
//            Calendar.FRIDAY,
//            Calendar.SATURDAY,
//            Calendar.SUNDAY,
//        };
//
//        // Bitmask of all repeating days
//        private int mDays;
//
//        public DaysOfWeek(int days) {
//            mDays = days;
//        }
//
//        public String toString(Context context, boolean showNever) {
//            StringBuilder ret = new StringBuilder();
//
//            // no days
//            if (mDays == 0) {
//                return showNever ?
//                        context.getText(R.string.never).toString() : "";
//            }
//
//            // every day
//            if (mDays == 0x7f) {
//                return context.getText(R.string.every_day).toString();
//            }
//
//            // count selected days
//            int dayCount = 0, days = mDays;
//            while (days > 0) {
//                if ((days & 1) == 1) dayCount++;
//                days >>= 1;
//            }
//
//            // short or long form?
//            DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
//            String[] dayList = (dayCount > 1) ?
//                    dfs.getShortWeekdays() :
//                    dfs.getWeekdays();
//
//            // selected days
//            for (int i = 0; i < 7; i++) {
//                if ((mDays & (1 << i)) != 0) {
//                    ret.append(dayList[DAY_MAP[i]]);
//                    dayCount -= 1;
//                    if (dayCount > 0) ret.append(
//                            context.getText(R.string.day_concat));
//                }
//            }
//            return ret.toString();
//        }
//
//        private boolean isSet(int day) {
//            return ((mDays & (1 << day)) > 0);
//        }
//
//        public void set(int day, boolean set) {
//            if (set) {
//                mDays |= (1 << day);
//            } else {
//                mDays &= ~(1 << day);
//            }
//        }
//
//        public void set(DaysOfWeek dow) {
//            mDays = dow.mDays;
//        }
//
//    	// Returns if today is set.
//        public boolean isToday(int calendarDayOfWeek){
//
//        	for(int i = 0; i < 7; i++){
//        		if(calendarDayOfWeek == DAY_MAP[i]){
//        			return isSet(i);
//        		}
//        	}
//
//			return false;
//        }
//
//        public int getCoded() {
//            return mDays;
//        }
//
//        public String toNumberString() {
//        	StringBuilder ret = new StringBuilder();
//
//        	int dayCount = 0, days = mDays;
//            while (days > 0) {
//                if ((days & 1) == 1) dayCount++;
//                days >>= 1;
//            }
//        	 // selected days
//            for (int i = 0; i < 7; i++) {
//                if ((mDays & (1 << i)) != 0) {
//                	if( i != 6) {
//                		ret.append(""+ (i+1));
//                	}
//                	else {
//                		ret.append(""+ 0);
//                	}
//                    dayCount -= 1;
//                    if (dayCount > 0) ret.append(",");
//                }
//            }
//
//			return ret.toString();
//        }
//
//        // Returns days of week encoded in an array of booleans.
//        public boolean[] getBooleanArray() {
//            boolean[] ret = new boolean[7];
//            for (int i = 0; i < 7; i++) {
//                ret[i] = isSet(i);
//            }
//            return ret;
//        }
//
//        public boolean isRepeatSet() {
//            return mDays != 0;
//        }
//
//    }
//
//    public static ArrayList<ScheduleData> converCursorToList(Cursor c) {
//		ArrayList<ScheduleData> schList = new ArrayList<ScheduleData>();
//
//		while (c.moveToNext()) {
//			ScheduleData sch = new ScheduleData();
//			sch._id = c.getInt(c.getColumnIndex(ID));
//			sch.zone = c.getInt(c.getColumnIndex(ZONE));
//
//			sch.start_time = c.getString(c
//					.getColumnIndex(START_TIME));
//			sch.interval = c.getInt(c.getColumnIndex(INTERVAL));
//
//			sch.daysOfWeek.set(new DaysOfWeek(c.getInt(c
//					.getColumnIndex(REPEAT))));
//
//			sch.isUpload = (c.getInt(c.getColumnIndex(IS_UPLOAD)) == 1 ? true:false);
//
//			sch.enable = (c.getInt(c.getColumnIndex(ENABLE)) == 1 ? true:false);
//
//			sch.lable = c.getString(c.getColumnIndex(LABLE));
//
//			sch.groupId = c.getInt(c.getColumnIndex(GROUP_ID));
//
//			sch.timeId = c.getInt(c.getColumnIndex(TIME_ID));
//			schList.add(sch);
//		}
//
//		return schList;
//	}
}
