import java.text.DecimalFormat;

public class Transform {

	public String transform(int n) {
		String str = "";
		while (n > 0) {
			int tmp = n % 2;
			str = tmp + str;
			n = n / 2;
		}
		return str;
	}

	public String buMa(String str) {
		String str1 = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1') {
				str1 += '0';
			} else {
				str1 += '1';
			}
		}
		for (int i = str1.length(); i < 32; i++) {
			str1 = '1' + str1;
		}
		int ch = 1;
		str = "";
		for (int i = str1.length() - 1; i >= 0; i--) {
			int tmp = str1.charAt(i) - '0';

			str += (ch + tmp) % 2;
			ch = (ch + tmp) / 2;
		}
		return str;
	}

	public int NumberOf1(int n) {

		String str = "";
		if (n >= 0) {
			str = transform(n);
		} else {
			if (n == -2147483648)
				return 1;
			else
				n = (-1) * n;
			str = transform(n);
			str = buMa(str);
		}
		int res = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1')
				res++;
		}
		return res;
	}

	public String multi_str(String str1, String str2) {
		String str = "";
		for (int i = 0; i < str1.length(); i++) {
			int tmp = str1.charAt(i) - '0';
			String tmp_s = multisingle(str2, tmp);
			if (str.length() != 0)
				str = str + '0';
			str = Add_str(str, tmp_s);
		}
		return str;
	}

	public String multisingle(String str1, int num) {
		String str = "";
		int ch = 0;
		for (int i = str1.length() - 1; i >= 0; i--) {
			int tmp = str1.charAt(i) - '0';
			tmp = tmp * num + ch;
			ch = tmp / 10;
			tmp = tmp % 10;
			str = tmp + str;
		}
		if (ch != 0)
			str = ch + str;
		return str;
	}

	public String Add_str(String str1, String str2) {
		int ch = 0;
		String str = "";
		if (str1.length() < str2.length()) {
			String tmp = str1;
			str1 = str2;
			str2 = tmp;
		}
		int j = str1.length() - 1;
		for (int i = str2.length() - 1; i >= 0; i--, j--) {
			int tmp = str1.charAt(j) + str2.charAt(i) - '0' - '0' + ch;
			ch = tmp / 10;
			tmp = tmp % 10;
			str = tmp + str;
		}
		for (; j >= 0; j--) {
			int tmp = str1.charAt(j) - '0' + ch;
			ch = tmp / 10;
			tmp = tmp % 10;
			str = tmp + str;
		}
		if (ch != 0)
			str = ch + str;
		return str;
	}

	public String delete_zero(String str) {
		int start = 0;
		int end = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '0') {
				start = i;
				break;
			}
		}
		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.charAt(i) != '0') {
				end = i + 1;
				break;
			}
		}
		str = str.substring(start, end);
		return str;
	}

	public String Add_point(String str, int len) {
		if (str.length() <= len) {
			for (int i = str.length(); i <= len; i++) {
				str = '0' + str;
			}
		}
		int mid = str.length() - len;
		String str1 = str.substring(0, mid);
		String str2 = str.substring(mid, str.length());

		str = str1 + "." + str2;

		return str;

	}

	public double Power(double base, int exponent) {
		String res_s = "";
		double res_num = 0;
		String str = "" + base;
		str = delete_zero(str);

		int flag=1;
		if(exponent<0){
			flag=-1;
			exponent*=-1;
		}
		int mid = str.lastIndexOf('.');

		String[] nums = new String[2];
		nums[0] = str.substring(0, mid);
		nums[1] = str.substring(mid + 1, str.length());
		int len_point = 0;

		len_point = nums[1].length() * exponent;
		str = nums[0] + nums[1];

		res_s = str;
		for (int i = 0; i < exponent - 1; i++) {
			res_s = multi_str(res_s, str);
		}
		res_s = Add_point(res_s, len_point);

		res_num = Double.parseDouble(res_s);

		DecimalFormat formater = new DecimalFormat();
		formater.setMinimumFractionDigits(5);
        
		if(flag==-1)
			return (double)1/res_num;
		return res_num;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Transform obj = new Transform();
		// int res = obj.NumberOf1(-2147483648);
		// System.out.println(res);
		double d = obj.Power(16, 2);
		
		System.out.println(d);
	}

}
