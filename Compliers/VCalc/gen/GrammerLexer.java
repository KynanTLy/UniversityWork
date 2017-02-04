// Generated from src/Grammer.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammerLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, MUL=15, DIV=16, ADD=17, 
		SUB=18, LES=19, GRT=20, EQU=21, NEQ=22, IF=23, LOOP=24, PRINT=25, INT=26, 
		ID=27, STRING=28, CHAR=29, WS=30;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "MUL", "DIV", "ADD", "SUB", 
		"LES", "GRT", "EQU", "NEQ", "IF", "LOOP", "PRINT", "INT", "ID", "STRING", 
		"CHAR", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'fi'", "';'", "'pool'", "'='", "'..'", "'['", "']'", 
		"'vector'", "'int'", "'in'", "'&'", "'|'", "'*'", "'/'", "'+'", "'-'", 
		"'<'", "'>'", "'=='", "'!='", "'if'", "'loop'", "'print'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "MUL", "DIV", "ADD", "SUB", "LES", "GRT", "EQU", "NEQ", 
		"IF", "LOOP", "PRINT", "INT", "ID", "STRING", "CHAR", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GrammerLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2 \u00a3\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t"+
		"\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3"+
		"\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\33\6\33\u008a\n\33\r\33"+
		"\16\33\u008b\3\34\3\34\3\34\7\34\u0091\n\34\f\34\16\34\u0094\13\34\3\35"+
		"\6\35\u0097\n\35\r\35\16\35\u0098\3\36\3\36\3\37\6\37\u009e\n\37\r\37"+
		"\16\37\u009f\3\37\3\37\2\2 \3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= \3\2\5\3\2\62;\5\2C\\c|~~\5\2\13\f\17\17\""+
		"\"\u00a7\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\3?\3\2\2\2\5A\3\2\2\2\7C\3\2\2\2\tF\3\2\2\2"+
		"\13H\3\2\2\2\rM\3\2\2\2\17O\3\2\2\2\21R\3\2\2\2\23T\3\2\2\2\25V\3\2\2"+
		"\2\27]\3\2\2\2\31a\3\2\2\2\33d\3\2\2\2\35f\3\2\2\2\37h\3\2\2\2!j\3\2\2"+
		"\2#l\3\2\2\2%n\3\2\2\2\'p\3\2\2\2)r\3\2\2\2+t\3\2\2\2-w\3\2\2\2/z\3\2"+
		"\2\2\61}\3\2\2\2\63\u0082\3\2\2\2\65\u0089\3\2\2\2\67\u008d\3\2\2\29\u0096"+
		"\3\2\2\2;\u009a\3\2\2\2=\u009d\3\2\2\2?@\7*\2\2@\4\3\2\2\2AB\7+\2\2B\6"+
		"\3\2\2\2CD\7h\2\2DE\7k\2\2E\b\3\2\2\2FG\7=\2\2G\n\3\2\2\2HI\7r\2\2IJ\7"+
		"q\2\2JK\7q\2\2KL\7n\2\2L\f\3\2\2\2MN\7?\2\2N\16\3\2\2\2OP\7\60\2\2PQ\7"+
		"\60\2\2Q\20\3\2\2\2RS\7]\2\2S\22\3\2\2\2TU\7_\2\2U\24\3\2\2\2VW\7x\2\2"+
		"WX\7g\2\2XY\7e\2\2YZ\7v\2\2Z[\7q\2\2[\\\7t\2\2\\\26\3\2\2\2]^\7k\2\2^"+
		"_\7p\2\2_`\7v\2\2`\30\3\2\2\2ab\7k\2\2bc\7p\2\2c\32\3\2\2\2de\7(\2\2e"+
		"\34\3\2\2\2fg\7~\2\2g\36\3\2\2\2hi\7,\2\2i \3\2\2\2jk\7\61\2\2k\"\3\2"+
		"\2\2lm\7-\2\2m$\3\2\2\2no\7/\2\2o&\3\2\2\2pq\7>\2\2q(\3\2\2\2rs\7@\2\2"+
		"s*\3\2\2\2tu\7?\2\2uv\7?\2\2v,\3\2\2\2wx\7#\2\2xy\7?\2\2y.\3\2\2\2z{\7"+
		"k\2\2{|\7h\2\2|\60\3\2\2\2}~\7n\2\2~\177\7q\2\2\177\u0080\7q\2\2\u0080"+
		"\u0081\7r\2\2\u0081\62\3\2\2\2\u0082\u0083\7r\2\2\u0083\u0084\7t\2\2\u0084"+
		"\u0085\7k\2\2\u0085\u0086\7p\2\2\u0086\u0087\7v\2\2\u0087\64\3\2\2\2\u0088"+
		"\u008a\t\2\2\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089\3\2"+
		"\2\2\u008b\u008c\3\2\2\2\u008c\66\3\2\2\2\u008d\u0092\5;\36\2\u008e\u0091"+
		"\5;\36\2\u008f\u0091\5\65\33\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2"+
		"\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u00938\3"+
		"\2\2\2\u0094\u0092\3\2\2\2\u0095\u0097\5;\36\2\u0096\u0095\3\2\2\2\u0097"+
		"\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099:\3\2\2\2"+
		"\u009a\u009b\t\3\2\2\u009b<\3\2\2\2\u009c\u009e\t\4\2\2\u009d\u009c\3"+
		"\2\2\2\u009e\u009f\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0"+
		"\u00a1\3\2\2\2\u00a1\u00a2\b\37\2\2\u00a2>\3\2\2\2\b\2\u008b\u0090\u0092"+
		"\u0098\u009f\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}