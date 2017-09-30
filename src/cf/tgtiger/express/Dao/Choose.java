package cf.tgtiger.express.Dao;

import java.util.Calendar;

public class Choose {
//    public static void main(String[] args) {
//		Calendar now = Calendar.getInstance();
//		System.out.println("年: " + now.get(Calendar.YEAR));
//		System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
//		System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
//		System.out.println(decide(9792668, "山西省", "浙江省", 3));
//	}

	public static int decide(String expnum, String sender_province, String receiver_province, int sum) {
		Long num =  (Long.parseLong(expnum)) % 100;
		int sender = 0;
		int rece = 0;
		switch (sender_province) {
		case "北京市":
			sender = 1;
			break;
		case "天津市":
			sender = 2;
			break;
		case "河北省":
			sender = 3;
			break;
		case "山西省":
			sender = 4;
			break;
		case "内蒙古自治区":
			sender = 5;
			break;
		case "辽宁省":
			sender = 6;
			break;
		case "吉林省":
			sender = 7;
			break;
		case "黑龙江省":
			sender = 8;
			break;
		case "上海市":
			sender = 9;
			break;
		case "江苏省":
			sender = 10;
			break;
		case "浙江省":
			sender = 11;
			break;
		case "安徽省":
			sender = 12;
			break;
		case "福建省":
			sender = 13;
			break;
		case "江西省":
			sender = 14;
			break;
		case "山东省":
			sender = 15;
			break;
		case "河南省":
			sender = 16;
			break;
		case "湖北省":
			sender = 17;
			break;
		case "湖南省":
			sender = 18;
			break;
		case "广东省":
			sender = 19;
			break;
		case "广西壮族自治区":
			sender = 20;
			break;
		case "海南省":
			sender = 21;
			break;
		case "重庆市":
			sender = 22;
			break;
		case "四川省":
			sender = 23;
			break;
		case "贵州省":
			sender = 24;
			break;
		case "云南省":
			sender = 25;
			break;
		case "西藏自治区":
			sender = 26;
			break;
		case "陕西省":
			sender = 27;
			break;
		case "甘肃省":
			sender = 28;
			break;
		case "青海省":
			sender = 29;
			break;
		case "宁夏回族自治区":
			sender = 30;
			break;
		case "新疆维吾尔自治区":
			sender = 31;
			break;
		case "香港特别行政区":
			sender = 32;
			break;
		case "澳门特别行政区":
			sender = 33;
			break;
		case "台湾省":
			sender = 34;
			break;
		}

		switch (receiver_province) {
		case "北京市":
			rece = 1;
			break;
		case "天津市":
			rece = 2;
			break;
		case "河北省":
			rece = 3;
			break;
		case "山西省":
			rece = 4;
			break;
		case "内蒙古自治区":
			rece = 5;
			break;
		case "辽宁省":
			rece = 6;
			break;
		case "吉林省":
			rece = 7;
			break;
		case "黑龙江省":
			rece = 8;
			break;
		case "上海市":
			rece = 9;
			break;
		case "江苏省":
			rece = 10;
			break;
		case "浙江省":
			rece = 11;
			break;
		case "安徽省":
			rece = 12;
			break;
		case "福建省":
			rece = 13;
			break;
		case "江西省":
			rece = 14;
			break;
		case "山东省":
			rece = 15;
			break;
		case "河南省":
			rece = 16;
			break;
		case "湖北省":
			rece = 17;
			break;
		case "湖南省":
			rece = 18;
			break;
		case "广东省":
			rece = 19;
			break;
		case "广西壮族自治区":
			rece = 20;
			break;
		case "海南省":
			rece = 21;
			break;
		case "重庆市":
			rece = 22;
			break;
		case "四川省":
			rece = 23;
			break;
		case "贵州省":
			rece = 24;
			break;
		case "云南省":
			rece = 25;
			break;
		case "西藏自治区":
			rece = 26;
			break;
		case "陕西省":
			rece = 27;
			break;
		case "甘肃省":
			rece = 28;
			break;
		case "青海省":
			rece = 29;
			break;
		case "宁夏回族自治区":
			rece = 30;
			break;
		case "新疆维吾尔自治区":
			rece = 31;
			break;
		case "香港特别行政区":
			rece = 32;
			break;
		case "澳门特别行政区":
			rece = 33;
			break;
		case "台湾省":
			rece = 34;
			break;
		}
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);
		int month=now.get(Calendar.MONTH)+1;
		int day=now.get(Calendar.DAY_OF_MONTH);
		return (int) ((year % 100 + month + day + num + sender + rece) % sum);
	}
}
