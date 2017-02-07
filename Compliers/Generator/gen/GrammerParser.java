// Generated from src/Grammer.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, INT=9, 
		Var=10, STRING=11, CHAR=12, WS=13, POW=14, MUL=15, DIV=16, MOD=17, ADD=18, 
		SUB=19;
	public static final int
		RULE_prog = 0, RULE_mathexpr = 1, RULE_generator = 2;
	public static final String[] ruleNames = {
		"prog", "mathexpr", "generator"
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

	@Override
	public String getGrammarFileName() { return "Grammer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(GrammerParser.EOF, 0); }
		public List<GeneratorContext> generator() {
			return getRuleContexts(GeneratorContext.class);
		}
		public GeneratorContext generator(int i) {
			return getRuleContext(GeneratorContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(9);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(6);
				generator();
				}
				}
				setState(11);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(12);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MathexprContext extends ParserRuleContext {
		public MathexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mathexpr; }
	 
		public MathexprContext() { }
		public void copyFrom(MathexprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MULTDIVContext extends MathexprContext {
		public Token op;
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public MULTDIVContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterMULTDIV(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitMULTDIV(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitMULTDIV(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BRACKETContext extends MathexprContext {
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public BRACKETContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterBRACKET(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitBRACKET(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitBRACKET(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarContext extends MathexprContext {
		public TerminalNode Var() { return getToken(GrammerParser.Var, 0); }
		public VarContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ADDSUBContext extends MathexprContext {
		public Token op;
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public ADDSUBContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterADDSUB(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitADDSUB(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitADDSUB(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NEGINTContext extends MathexprContext {
		public TerminalNode INT() { return getToken(GrammerParser.INT, 0); }
		public NEGINTContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterNEGINT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitNEGINT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitNEGINT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class POWERContext extends MathexprContext {
		public Token op;
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public POWERContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterPOWER(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitPOWER(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitPOWER(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INTContext extends MathexprContext {
		public TerminalNode INT() { return getToken(GrammerParser.INT, 0); }
		public INTContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterINT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitINT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitINT(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MathexprContext mathexpr() throws RecognitionException {
		return mathexpr(0);
	}

	private MathexprContext mathexpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MathexprContext _localctx = new MathexprContext(_ctx, _parentState);
		MathexprContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_mathexpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			switch (_input.LA(1)) {
			case T__0:
				{
				_localctx = new BRACKETContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(15);
				match(T__0);
				setState(16);
				mathexpr(0);
				setState(17);
				match(T__1);
				}
				break;
			case SUB:
				{
				_localctx = new NEGINTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(19);
				match(SUB);
				setState(20);
				match(INT);
				}
				break;
			case INT:
				{
				_localctx = new INTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(21);
				match(INT);
				}
				break;
			case Var:
				{
				_localctx = new VarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(22);
				match(Var);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(36);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(34);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new POWERContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(25);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(26);
						((POWERContext)_localctx).op = match(POW);
						setState(27);
						mathexpr(7);
						}
						break;
					case 2:
						{
						_localctx = new MULTDIVContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(28);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(29);
						((MULTDIVContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((MULTDIVContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(30);
						mathexpr(7);
						}
						break;
					case 3:
						{
						_localctx = new ADDSUBContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(31);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(32);
						((ADDSUBContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((ADDSUBContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(33);
						mathexpr(6);
						}
						break;
					}
					} 
				}
				setState(38);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class GeneratorContext extends ParserRuleContext {
		public GeneratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generator; }
	 
		public GeneratorContext() { }
		public void copyFrom(GeneratorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class GenContext extends GeneratorContext {
		public Token min;
		public Token max;
		public TerminalNode Var() { return getToken(GrammerParser.Var, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public List<TerminalNode> INT() { return getTokens(GrammerParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(GrammerParser.INT, i);
		}
		public GenContext(GeneratorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterGen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitGen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitGen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeneratorContext generator() throws RecognitionException {
		GeneratorContext _localctx = new GeneratorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_generator);
		try {
			_localctx = new GenContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(T__2);
			setState(40);
			match(Var);
			setState(41);
			match(T__3);
			setState(42);
			((GenContext)_localctx).min = match(INT);
			setState(43);
			match(T__4);
			setState(44);
			((GenContext)_localctx).max = match(INT);
			setState(45);
			match(T__5);
			setState(46);
			mathexpr(0);
			setState(47);
			match(T__6);
			setState(48);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return mathexpr_sempred((MathexprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean mathexpr_sempred(MathexprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\25\65\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\3\2\7\2\n\n\2\f\2\16\2\r\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\5\3\32\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3%\n"+
		"\3\f\3\16\3(\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\2\3"+
		"\4\5\2\4\6\2\4\3\2\21\23\3\2\24\258\2\13\3\2\2\2\4\31\3\2\2\2\6)\3\2\2"+
		"\2\b\n\5\6\4\2\t\b\3\2\2\2\n\r\3\2\2\2\13\t\3\2\2\2\13\f\3\2\2\2\f\16"+
		"\3\2\2\2\r\13\3\2\2\2\16\17\7\2\2\3\17\3\3\2\2\2\20\21\b\3\1\2\21\22\7"+
		"\3\2\2\22\23\5\4\3\2\23\24\7\4\2\2\24\32\3\2\2\2\25\26\7\25\2\2\26\32"+
		"\7\13\2\2\27\32\7\13\2\2\30\32\7\f\2\2\31\20\3\2\2\2\31\25\3\2\2\2\31"+
		"\27\3\2\2\2\31\30\3\2\2\2\32&\3\2\2\2\33\34\f\t\2\2\34\35\7\20\2\2\35"+
		"%\5\4\3\t\36\37\f\b\2\2\37 \t\2\2\2 %\5\4\3\t!\"\f\7\2\2\"#\t\3\2\2#%"+
		"\5\4\3\b$\33\3\2\2\2$\36\3\2\2\2$!\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2"+
		"\2\2\'\5\3\2\2\2(&\3\2\2\2)*\7\5\2\2*+\7\f\2\2+,\7\6\2\2,-\7\13\2\2-."+
		"\7\7\2\2./\7\13\2\2/\60\7\b\2\2\60\61\5\4\3\2\61\62\7\t\2\2\62\63\7\n"+
		"\2\2\63\7\3\2\2\2\6\13\31$&";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}