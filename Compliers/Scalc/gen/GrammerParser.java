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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, MUL=8, DIV=9, 
		ADD=10, SUB=11, LES=12, GRT=13, EQU=14, NEQ=15, IF=16, LOOP=17, PRINT=18, 
		INT=19, ID=20, STRING=21, CHAR=22, WS=23;
	public static final int
		RULE_prog = 0, RULE_mathexpr = 1, RULE_state = 2;
	public static final String[] ruleNames = {
		"prog", "mathexpr", "state"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'int'", "'='", "';'", "'fi'", "'pool'", "'*'", "'/'", 
		"'+'", "'-'", "'<'", "'>'", "'=='", "'!='", "'if'", "'loop'", "'print'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "MUL", "DIV", "ADD", "SUB", 
		"LES", "GRT", "EQU", "NEQ", "IF", "LOOP", "PRINT", "INT", "ID", "STRING", 
		"CHAR", "WS"
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
		public List<StateContext> state() {
			return getRuleContexts(StateContext.class);
		}
		public StateContext state(int i) {
			return getRuleContext(StateContext.class,i);
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << IF) | (1L << LOOP) | (1L << PRINT) | (1L << ID))) != 0)) {
				{
				{
				setState(6);
				state();
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
	public static class GRTLESContext extends MathexprContext {
		public Token op;
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public GRTLESContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterGRTLES(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitGRTLES(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitGRTLES(this);
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
	public static class COMPAREContext extends MathexprContext {
		public Token op;
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public COMPAREContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterCOMPARE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitCOMPARE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitCOMPARE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IDContext extends MathexprContext {
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public IDContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitID(this);
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
			setState(21);
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
			case INT:
				{
				_localctx = new INTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(19);
				match(INT);
				}
				break;
			case ID:
				{
				_localctx = new IDContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(20);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(37);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(35);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new MULTDIVContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(23);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(24);
						((MULTDIVContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((MULTDIVContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(25);
						mathexpr(8);
						}
						break;
					case 2:
						{
						_localctx = new ADDSUBContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(26);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(27);
						((ADDSUBContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((ADDSUBContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(28);
						mathexpr(7);
						}
						break;
					case 3:
						{
						_localctx = new GRTLESContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(29);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(30);
						((GRTLESContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LES || _la==GRT) ) {
							((GRTLESContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(31);
						mathexpr(6);
						}
						break;
					case 4:
						{
						_localctx = new COMPAREContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(32);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(33);
						((COMPAREContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQU || _la==NEQ) ) {
							((COMPAREContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(34);
						mathexpr(5);
						}
						break;
					}
					} 
				}
				setState(39);
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

	public static class StateContext extends ParserRuleContext {
		public StateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_state; }
	 
		public StateContext() { }
		public void copyFrom(StateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PRINTContext extends StateContext {
		public TerminalNode PRINT() { return getToken(GrammerParser.PRINT, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public PRINTContext(StateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterPRINT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitPRINT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitPRINT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INITContext extends StateContext {
		public Token type;
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public INITContext(StateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterINIT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitINIT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitINIT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LOOPContext extends StateContext {
		public TerminalNode LOOP() { return getToken(GrammerParser.LOOP, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public List<StateContext> state() {
			return getRuleContexts(StateContext.class);
		}
		public StateContext state(int i) {
			return getRuleContext(StateContext.class,i);
		}
		public LOOPContext(StateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterLOOP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitLOOP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitLOOP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ASSIGNContext extends StateContext {
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public ASSIGNContext(StateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterASSIGN(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitASSIGN(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitASSIGN(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IFContext extends StateContext {
		public TerminalNode IF() { return getToken(GrammerParser.IF, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public List<StateContext> state() {
			return getRuleContexts(StateContext.class);
		}
		public StateContext state(int i) {
			return getRuleContext(StateContext.class,i);
		}
		public IFContext(StateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterIF(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitIF(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitIF(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StateContext state() throws RecognitionException {
		StateContext _localctx = new StateContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_state);
		int _la;
		try {
			setState(84);
			switch (_input.LA(1)) {
			case T__2:
				_localctx = new INITContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				((INITContext)_localctx).type = match(T__2);
				setState(41);
				match(ID);
				setState(44);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(42);
					match(T__3);
					setState(43);
					mathexpr(0);
					}
				}

				setState(46);
				match(T__4);
				}
				break;
			case IF:
				_localctx = new IFContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(47);
				match(IF);
				setState(48);
				match(T__0);
				setState(49);
				mathexpr(0);
				setState(50);
				match(T__1);
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << IF) | (1L << LOOP) | (1L << PRINT) | (1L << ID))) != 0)) {
					{
					{
					setState(51);
					state();
					}
					}
					setState(56);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(57);
				match(T__5);
				setState(58);
				match(T__4);
				}
				break;
			case LOOP:
				_localctx = new LOOPContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(60);
				match(LOOP);
				setState(61);
				match(T__0);
				setState(62);
				mathexpr(0);
				setState(63);
				match(T__1);
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << IF) | (1L << LOOP) | (1L << PRINT) | (1L << ID))) != 0)) {
					{
					{
					setState(64);
					state();
					}
					}
					setState(69);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(70);
				match(T__6);
				setState(71);
				match(T__4);
				}
				break;
			case PRINT:
				_localctx = new PRINTContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(73);
				match(PRINT);
				setState(74);
				match(T__0);
				setState(75);
				mathexpr(0);
				setState(76);
				match(T__1);
				setState(77);
				match(T__4);
				}
				break;
			case ID:
				_localctx = new ASSIGNContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(79);
				match(ID);
				setState(80);
				match(T__3);
				setState(81);
				mathexpr(0);
				setState(82);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		case 3:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\31Y\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\3\2\7\2\n\n\2\f\2\16\2\r\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\5\3\30\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3"+
		"&\n\3\f\3\16\3)\13\3\3\4\3\4\3\4\3\4\5\4/\n\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\7\4\67\n\4\f\4\16\4:\13\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4D\n\4\f"+
		"\4\16\4G\13\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\5\4W\n\4\3\4\2\3\4\5\2\4\6\2\6\3\2\n\13\3\2\f\r\3\2\16\17\3\2\20\21c"+
		"\2\13\3\2\2\2\4\27\3\2\2\2\6V\3\2\2\2\b\n\5\6\4\2\t\b\3\2\2\2\n\r\3\2"+
		"\2\2\13\t\3\2\2\2\13\f\3\2\2\2\f\16\3\2\2\2\r\13\3\2\2\2\16\17\7\2\2\3"+
		"\17\3\3\2\2\2\20\21\b\3\1\2\21\22\7\3\2\2\22\23\5\4\3\2\23\24\7\4\2\2"+
		"\24\30\3\2\2\2\25\30\7\25\2\2\26\30\7\26\2\2\27\20\3\2\2\2\27\25\3\2\2"+
		"\2\27\26\3\2\2\2\30\'\3\2\2\2\31\32\f\t\2\2\32\33\t\2\2\2\33&\5\4\3\n"+
		"\34\35\f\b\2\2\35\36\t\3\2\2\36&\5\4\3\t\37 \f\7\2\2 !\t\4\2\2!&\5\4\3"+
		"\b\"#\f\6\2\2#$\t\5\2\2$&\5\4\3\7%\31\3\2\2\2%\34\3\2\2\2%\37\3\2\2\2"+
		"%\"\3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(\5\3\2\2\2)\'\3\2\2\2*+\7"+
		"\5\2\2+.\7\26\2\2,-\7\6\2\2-/\5\4\3\2.,\3\2\2\2./\3\2\2\2/\60\3\2\2\2"+
		"\60W\7\7\2\2\61\62\7\22\2\2\62\63\7\3\2\2\63\64\5\4\3\2\648\7\4\2\2\65"+
		"\67\5\6\4\2\66\65\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2"+
		":8\3\2\2\2;<\7\b\2\2<=\7\7\2\2=W\3\2\2\2>?\7\23\2\2?@\7\3\2\2@A\5\4\3"+
		"\2AE\7\4\2\2BD\5\6\4\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2"+
		"\2GE\3\2\2\2HI\7\t\2\2IJ\7\7\2\2JW\3\2\2\2KL\7\24\2\2LM\7\3\2\2MN\5\4"+
		"\3\2NO\7\4\2\2OP\7\7\2\2PW\3\2\2\2QR\7\26\2\2RS\7\6\2\2ST\5\4\3\2TU\7"+
		"\7\2\2UW\3\2\2\2V*\3\2\2\2V\61\3\2\2\2V>\3\2\2\2VK\3\2\2\2VQ\3\2\2\2W"+
		"\7\3\2\2\2\n\13\27%\'.8EV";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}