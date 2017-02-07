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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, INT=9, 
		Var=10, STRING=11, CHAR=12, WS=13, POW=14, MUL=15, DIV=16, MOD=17, ADD=18, 
		SUB=19;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "INT", 
		"Var", "STRING", "CHAR", "WS", "POW", "MUL", "DIV", "MOD", "ADD", "SUB"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'['", "'in'", "'..'", "'|'", "']'", "';'", null, 
		null, null, null, null, "'^'", "'*'", "'/'", "'%'", "'+'", "'-'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, "INT", "Var", "STRING", 
		"CHAR", "WS", "POW", "MUL", "DIV", "MOD", "ADD", "SUB"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\25b\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\7\3\7\3\b\3\b\3\t\3\t\3\n\6\n=\n\n\r\n\16\n>\3\13\3\13\3\13\7\13D\n\13"+
		"\f\13\16\13G\13\13\3\f\6\fJ\n\f\r\f\16\fK\3\r\3\r\3\16\6\16Q\n\16\r\16"+
		"\16\16R\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3"+
		"\24\3\24\2\2\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25\3\2\5\3\2\62;\5\2C\\c|~~\5\2\13"+
		"\f\17\17\"\"f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2\5+\3\2\2\2\7-\3\2\2"+
		"\2\t/\3\2\2\2\13\62\3\2\2\2\r\65\3\2\2\2\17\67\3\2\2\2\219\3\2\2\2\23"+
		"<\3\2\2\2\25@\3\2\2\2\27I\3\2\2\2\31M\3\2\2\2\33P\3\2\2\2\35V\3\2\2\2"+
		"\37X\3\2\2\2!Z\3\2\2\2#\\\3\2\2\2%^\3\2\2\2\'`\3\2\2\2)*\7*\2\2*\4\3\2"+
		"\2\2+,\7+\2\2,\6\3\2\2\2-.\7]\2\2.\b\3\2\2\2/\60\7k\2\2\60\61\7p\2\2\61"+
		"\n\3\2\2\2\62\63\7\60\2\2\63\64\7\60\2\2\64\f\3\2\2\2\65\66\7~\2\2\66"+
		"\16\3\2\2\2\678\7_\2\28\20\3\2\2\29:\7=\2\2:\22\3\2\2\2;=\t\2\2\2<;\3"+
		"\2\2\2=>\3\2\2\2><\3\2\2\2>?\3\2\2\2?\24\3\2\2\2@E\5\31\r\2AD\5\31\r\2"+
		"BD\5\23\n\2CA\3\2\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2F\26\3\2"+
		"\2\2GE\3\2\2\2HJ\5\31\r\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2L\30"+
		"\3\2\2\2MN\t\3\2\2N\32\3\2\2\2OQ\t\4\2\2PO\3\2\2\2QR\3\2\2\2RP\3\2\2\2"+
		"RS\3\2\2\2ST\3\2\2\2TU\b\16\2\2U\34\3\2\2\2VW\7`\2\2W\36\3\2\2\2XY\7,"+
		"\2\2Y \3\2\2\2Z[\7\61\2\2[\"\3\2\2\2\\]\7\'\2\2]$\3\2\2\2^_\7-\2\2_&\3"+
		"\2\2\2`a\7/\2\2a(\3\2\2\2\b\2>CEKR\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}