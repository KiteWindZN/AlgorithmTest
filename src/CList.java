import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CList {

	CNode[] list = new CNode[1000];

	public CList() {
		for (int i = 0; i < list.length; i++) {
			list[i] = new CNode("#");
		}
	}

	public void InsertNode(CNode cn) {
		String value = cn.value;
		String pinYin = PinyinDemo.ToPinyin(value);
		int Hash = HashTransform(pinYin);
		CNode tmpN = list[Hash];
		if (tmpN.behind == null) {
			CNode CN = new CNode(pinYin);
			tmpN.behind = CN;
			CN.next = cn;
		} else {
			int flag1 = 0;
			while (tmpN.behind != null) {
				CNode CN = tmpN.behind;

				if (CN.value.equals(pinYin)) {
					flag1 = 1;
					CNode CN2 = CN;
					int flag = 0;
					while (CN2.next != null) {
						if (CN2.value.equals(value)) {
							flag = 1;
							break;
						}
						CN2 = CN2.next;
					}
					if (flag == 0) {
						CN2.next = cn;
						return;
					}
					break;
				}
				tmpN = CN;
			}
			if (flag1 == 0) {
				CNode CN = new CNode(pinYin);
				tmpN.behind = CN;
				CN.next = cn;
			}
		}

	}

	public void findStr(String str) {
		String pinYin = PinyinDemo.ToPinyin(str);
		int Hash = HashTransform(pinYin);
		CNode CN = list[Hash];
		if (CN.behind == null) {
			System.out.println("can not find the String :" + str);
			return;
		}
		CNode tmpN = CN;
		while (tmpN.behind != null) {
			CNode CN1 = tmpN.behind;
			if (CN1.value.equals(pinYin)) {
				CNode CN2 = CN1;
				while (CN2.next != null) {
					System.out.println(CN2.next.value);
					CN2 = CN2.next;

				}
				break;
			}
			tmpN = CN1;
		}
		return;
	}

	public int HashTransform(String pinyin) {
		int res = 0;
		res += pinyin.charAt(0) * 3;
		for (int i = 1; i < pinyin.length(); i++) {
			res += pinyin.charAt(i) * 2;
		}

		res %= 1000;
		return res;
	}

	public String pre_process(String str) {
		String str1 = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ')
				str1 += str.charAt(i);
		}
		return str1;
	}

	public static void main(String[] args) throws IOException {
		CList obj = new CList();
		BufferedReader bf = new BufferedReader(new FileReader("Chinese.txt"));
		String line = bf.readLine();
		while (line != null) {
			String[] strS = line.split(" ");
			for (int i = 0; i < strS.length; i++) {
				CNode cn = new CNode(strS[i]);
				obj.InsertNode(cn);
			}
			line = bf.readLine();
		}
		String Str = "芳华";
		Str=obj.pre_process(Str);
		obj.findStr(Str);
	}
}
