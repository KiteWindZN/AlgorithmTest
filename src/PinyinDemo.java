import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinDemo {
	

	public static String ToPinyin(String chinese){
		String pinyin="";
		char[] newchar= chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for(int i=0;i<newchar.length;i++){
			if(newchar[i]>128){
				try {
					pinyin+=PinyinHelper.toHanyuPinyinStringArray(newchar[i], defaultFormat)[0];
					//pinyin +=" ";
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				pinyin+=newchar[i];
			}
			//pinyin +=" ";
		}
		return pinyin.substring(0, pinyin.length());
			
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PinyinDemo obj=new PinyinDemo();
		String str=obj.ToPinyin("长时间acd");
		System.out.println(str);

	}

}
