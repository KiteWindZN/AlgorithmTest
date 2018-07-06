package PinYinShiBie;

import java.util.List;

public class ChineseSentence {

	private String content;
	private List<Lexeme> sentenceUnits;
	private SentenceType sentenceType;
	private static final String SUSPECTED_PINYIN_REGEX  = "[\\u4e00-\\u9fa5]|(sh|ch|zh|[^aoeiuv])?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?";
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Lexeme> getSentenceUnits() {
		return sentenceUnits;
	}

	public void setSentenceUnits(List<Lexeme> sentenceUnits) {
		this.sentenceUnits = sentenceUnits;
	}

	public SentenceType getSentenceType() {
		return sentenceType;
	}

	public void setSentenceType(SentenceType sentenceType) {
		this.sentenceType = sentenceType;
	}

	
	public void initSentenceType(){
		sentenceType=SentenceType.CHINESE_SENTENCE;
		for(Lexeme lexeme:sentenceUnits){
			if(lexeme.getLexemeType()==LexemeType.ACRONYM){
				sentenceType=SentenceType.ACRONYM_SENTENCE;
				break;
			}else if(lexeme.getLexemeType()==LexemeType.WHOLE&& sentenceType==SentenceType.CHINESE_SENTENCE){
				sentenceType=SentenceType.WHOLE_SENTENCE;
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
