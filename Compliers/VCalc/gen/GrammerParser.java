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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, MUL=15, DIV=16, ADD=17, 
		SUB=18, LES=19, GRT=20, EQU=21, NEQ=22, IF=23, LOOP=24, PRINT=25, INT=26, 
		ID=27, STRING=28, CHAR=29, WS=30;
	public static final int
		RULE_prog = 0, RULE_state = 1, RULE_mathexpr = 2, RULE_intexpr = 3, RULE_vecexpr = 4, 
		RULE_assignment = 5, RULE_genfil = 6, RULE_vector = 7;
	public static final String[] ruleNames = {
		"prog", "state", "mathexpr", "intexpr", "vecexpr", "assignment", "genfil", 
		"vector"
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
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << IF) | (1L << LOOP) | (1L << PRINT) | (1L << ID))) != 0)) {
				{
				{
				setState(16);
				state();
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22);
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
	public static class ASSIGNMENTContext extends StateContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ASSIGNMENTContext(StateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterASSIGNMENT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitASSIGNMENT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitASSIGNMENT(this);
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
		enterRule(_localctx, 2, RULE_state);
		int _la;
		try {
			setState(62);
			switch (_input.LA(1)) {
			case IF:
				_localctx = new IFContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				match(IF);
				setState(25);
				match(T__0);
				setState(26);
				mathexpr(0);
				setState(27);
				match(T__1);
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << IF) | (1L << LOOP) | (1L << PRINT) | (1L << ID))) != 0)) {
					{
					{
					setState(28);
					state();
					}
					}
					setState(33);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(34);
				match(T__2);
				setState(35);
				match(T__3);
				}
				break;
			case LOOP:
				_localctx = new LOOPContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(37);
				match(LOOP);
				setState(38);
				match(T__0);
				setState(39);
				mathexpr(0);
				setState(40);
				match(T__1);
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << IF) | (1L << LOOP) | (1L << PRINT) | (1L << ID))) != 0)) {
					{
					{
					setState(41);
					state();
					}
					}
					setState(46);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(47);
				match(T__4);
				setState(48);
				match(T__3);
				}
				break;
			case PRINT:
				_localctx = new PRINTContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(50);
				match(PRINT);
				setState(51);
				match(T__0);
				setState(52);
				mathexpr(0);
				setState(53);
				match(T__1);
				setState(54);
				match(T__3);
				}
				break;
			case T__9:
			case T__10:
				_localctx = new ASSIGNMENTContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(56);
				assignment();
				}
				break;
			case ID:
				_localctx = new ASSIGNContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(57);
				match(ID);
				setState(58);
				match(T__5);
				setState(59);
				mathexpr(0);
				setState(60);
				match(T__3);
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
	public static class BRACKETMEContext extends MathexprContext {
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public BRACKETMEContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterBRACKETME(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitBRACKETME(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitBRACKETME(this);
			else return visitor.visitChildren(this);
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
	public static class MEVECContext extends MathexprContext {
		public VecexprContext vecexpr() {
			return getRuleContext(VecexprContext.class,0);
		}
		public MEVECContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterMEVEC(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitMEVEC(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitMEVEC(this);
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
	public static class REIDContext extends MathexprContext {
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public REIDContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterREID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitREID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitREID(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MEINTContext extends MathexprContext {
		public IntexprContext intexpr() {
			return getRuleContext(IntexprContext.class,0);
		}
		public MEINTContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterMEINT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitMEINT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitMEINT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INDEXContext extends MathexprContext {
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public INDEXContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterINDEX(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitINDEX(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitINDEX(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EQNEQContext extends MathexprContext {
		public Token op;
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public EQNEQContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterEQNEQ(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitEQNEQ(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitEQNEQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RANGEContext extends MathexprContext {
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public RANGEContext(MathexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterRANGE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitRANGE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitRANGE(this);
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
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_mathexpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				_localctx = new BRACKETMEContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(65);
				match(T__0);
				setState(66);
				mathexpr(0);
				setState(67);
				match(T__1);
				}
				break;
			case 2:
				{
				_localctx = new MEVECContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(69);
				vecexpr(0);
				}
				break;
			case 3:
				{
				_localctx = new REIDContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(70);
				match(ID);
				}
				break;
			case 4:
				{
				_localctx = new MEINTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(71);
				intexpr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(96);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(94);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new RANGEContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(74);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(75);
						match(T__6);
						setState(76);
						mathexpr(11);
						}
						break;
					case 2:
						{
						_localctx = new MULTDIVContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(77);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(78);
						((MULTDIVContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((MULTDIVContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(79);
						mathexpr(9);
						}
						break;
					case 3:
						{
						_localctx = new ADDSUBContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(80);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(81);
						((ADDSUBContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((ADDSUBContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(82);
						mathexpr(8);
						}
						break;
					case 4:
						{
						_localctx = new GRTLESContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(83);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(84);
						((GRTLESContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LES || _la==GRT) ) {
							((GRTLESContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(85);
						mathexpr(7);
						}
						break;
					case 5:
						{
						_localctx = new EQNEQContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(86);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(87);
						((EQNEQContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQU || _la==NEQ) ) {
							((EQNEQContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(88);
						mathexpr(6);
						}
						break;
					case 6:
						{
						_localctx = new INDEXContext(new MathexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathexpr);
						setState(89);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(90);
						match(T__7);
						setState(91);
						mathexpr(0);
						setState(92);
						match(T__8);
						}
						break;
					}
					} 
				}
				setState(98);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
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

	public static class IntexprContext extends ParserRuleContext {
		public IntexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intexpr; }
	 
		public IntexprContext() { }
		public void copyFrom(IntexprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BRACKETINTContext extends IntexprContext {
		public TerminalNode INT() { return getToken(GrammerParser.INT, 0); }
		public BRACKETINTContext(IntexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterBRACKETINT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitBRACKETINT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitBRACKETINT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INTContext extends IntexprContext {
		public TerminalNode INT() { return getToken(GrammerParser.INT, 0); }
		public INTContext(IntexprContext ctx) { copyFrom(ctx); }
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

	public final IntexprContext intexpr() throws RecognitionException {
		IntexprContext _localctx = new IntexprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_intexpr);
		try {
			setState(103);
			switch (_input.LA(1)) {
			case T__0:
				_localctx = new BRACKETINTContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(99);
				match(T__0);
				setState(100);
				match(INT);
				setState(101);
				match(T__1);
				}
				break;
			case INT:
				_localctx = new INTContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				match(INT);
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

	public static class VecexprContext extends ParserRuleContext {
		public VecexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vecexpr; }
	 
		public VecexprContext() { }
		public void copyFrom(VecexprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class GENFILContext extends VecexprContext {
		public GenfilContext genfil() {
			return getRuleContext(GenfilContext.class,0);
		}
		public GENFILContext(VecexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterGENFIL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitGENFIL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitGENFIL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VECContext extends VecexprContext {
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public VECContext(VecexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterVEC(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitVEC(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitVEC(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BRACKETVECContext extends VecexprContext {
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public BRACKETVECContext(VecexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterBRACKETVEC(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitBRACKETVEC(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitBRACKETVEC(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VECINDEXContext extends VecexprContext {
		public VecexprContext vecexpr() {
			return getRuleContext(VecexprContext.class,0);
		}
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public VECINDEXContext(VecexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterVECINDEX(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitVECINDEX(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitVECINDEX(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VecexprContext vecexpr() throws RecognitionException {
		return vecexpr(0);
	}

	private VecexprContext vecexpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		VecexprContext _localctx = new VecexprContext(_ctx, _parentState);
		VecexprContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_vecexpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new GENFILContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(106);
				genfil();
				}
				break;
			case 2:
				{
				_localctx = new BRACKETVECContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(107);
				match(T__0);
				setState(108);
				vector();
				setState(109);
				match(T__1);
				}
				break;
			case 3:
				{
				_localctx = new VECContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(111);
				vector();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VECINDEXContext(new VecexprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_vecexpr);
					setState(114);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(115);
					match(T__7);
					setState(116);
					mathexpr(0);
					setState(117);
					match(T__8);
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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

	public static class AssignmentContext extends ParserRuleContext {
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	 
		public AssignmentContext() { }
		public void copyFrom(AssignmentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class INITIContext extends AssignmentContext {
		public Token typecast;
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public INITIContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterINITI(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitINITI(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitINITI(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INITVContext extends AssignmentContext {
		public Token typecast;
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public MathexprContext mathexpr() {
			return getRuleContext(MathexprContext.class,0);
		}
		public INITVContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterINITV(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitINITV(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitINITV(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignment);
		int _la;
		try {
			setState(138);
			switch (_input.LA(1)) {
			case T__9:
				_localctx = new INITVContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				((INITVContext)_localctx).typecast = match(T__9);
				setState(125);
				match(ID);
				setState(128);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(126);
					match(T__5);
					setState(127);
					mathexpr(0);
					}
				}

				setState(130);
				match(T__3);
				}
				break;
			case T__10:
				_localctx = new INITIContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				((INITIContext)_localctx).typecast = match(T__10);
				setState(132);
				match(ID);
				setState(135);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(133);
					match(T__5);
					setState(134);
					mathexpr(0);
					}
				}

				setState(137);
				match(T__3);
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

	public static class GenfilContext extends ParserRuleContext {
		public GenfilContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_genfil; }
	 
		public GenfilContext() { }
		public void copyFrom(GenfilContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class GENContext extends GenfilContext {
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public GENContext(GenfilContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterGEN(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitGEN(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitGEN(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FILTERContext extends GenfilContext {
		public TerminalNode ID() { return getToken(GrammerParser.ID, 0); }
		public List<MathexprContext> mathexpr() {
			return getRuleContexts(MathexprContext.class);
		}
		public MathexprContext mathexpr(int i) {
			return getRuleContext(MathexprContext.class,i);
		}
		public FILTERContext(GenfilContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterFILTER(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitFILTER(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitFILTER(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GenfilContext genfil() throws RecognitionException {
		GenfilContext _localctx = new GenfilContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_genfil);
		try {
			setState(156);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new FILTERContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(140);
				match(T__7);
				setState(141);
				match(ID);
				setState(142);
				match(T__11);
				setState(143);
				mathexpr(0);
				setState(144);
				match(T__12);
				setState(145);
				mathexpr(0);
				setState(146);
				match(T__8);
				}
				break;
			case 2:
				_localctx = new GENContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(148);
				match(T__7);
				setState(149);
				match(ID);
				setState(150);
				match(T__11);
				setState(151);
				mathexpr(0);
				setState(152);
				match(T__13);
				setState(153);
				mathexpr(0);
				setState(154);
				match(T__8);
				}
				break;
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

	public static class VectorContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(GrammerParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(GrammerParser.INT, i);
		}
		public VectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterVector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitVector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitVector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VectorContext vector() throws RecognitionException {
		VectorContext _localctx = new VectorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_vector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(T__7);
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INT) {
				{
				{
				setState(159);
				match(INT);
				}
				}
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(165);
			match(T__8);
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
		case 2:
			return mathexpr_sempred((MathexprContext)_localctx, predIndex);
		case 4:
			return vecexpr_sempred((VecexprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean mathexpr_sempred(MathexprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 8);
		case 2:
			return precpred(_ctx, 7);
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 9);
		}
		return true;
	}
	private boolean vecexpr_sempred(VecexprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3 \u00aa\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\7\2\24\n\2"+
		"\f\2\16\2\27\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\7\3 \n\3\f\3\16\3#\13\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3-\n\3\f\3\16\3\60\13\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3A\n\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\5\4K\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4a\n\4\f\4\16\4d\13\4\3\5\3"+
		"\5\3\5\3\5\5\5j\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6s\n\6\3\6\3\6\3\6\3"+
		"\6\3\6\7\6z\n\6\f\6\16\6}\13\6\3\7\3\7\3\7\3\7\5\7\u0083\n\7\3\7\3\7\3"+
		"\7\3\7\3\7\5\7\u008a\n\7\3\7\5\7\u008d\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u009f\n\b\3\t\3\t\7\t\u00a3\n"+
		"\t\f\t\16\t\u00a6\13\t\3\t\3\t\3\t\2\4\6\n\n\2\4\6\b\n\f\16\20\2\6\3\2"+
		"\21\22\3\2\23\24\3\2\25\26\3\2\27\30\u00ba\2\25\3\2\2\2\4@\3\2\2\2\6J"+
		"\3\2\2\2\bi\3\2\2\2\nr\3\2\2\2\f\u008c\3\2\2\2\16\u009e\3\2\2\2\20\u00a0"+
		"\3\2\2\2\22\24\5\4\3\2\23\22\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26"+
		"\3\2\2\2\26\30\3\2\2\2\27\25\3\2\2\2\30\31\7\2\2\3\31\3\3\2\2\2\32\33"+
		"\7\31\2\2\33\34\7\3\2\2\34\35\5\6\4\2\35!\7\4\2\2\36 \5\4\3\2\37\36\3"+
		"\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"$\3\2\2\2#!\3\2\2\2$%\7\5\2\2"+
		"%&\7\6\2\2&A\3\2\2\2\'(\7\32\2\2()\7\3\2\2)*\5\6\4\2*.\7\4\2\2+-\5\4\3"+
		"\2,+\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\61\3\2\2\2\60.\3\2\2\2\61"+
		"\62\7\7\2\2\62\63\7\6\2\2\63A\3\2\2\2\64\65\7\33\2\2\65\66\7\3\2\2\66"+
		"\67\5\6\4\2\678\7\4\2\289\7\6\2\29A\3\2\2\2:A\5\f\7\2;<\7\35\2\2<=\7\b"+
		"\2\2=>\5\6\4\2>?\7\6\2\2?A\3\2\2\2@\32\3\2\2\2@\'\3\2\2\2@\64\3\2\2\2"+
		"@:\3\2\2\2@;\3\2\2\2A\5\3\2\2\2BC\b\4\1\2CD\7\3\2\2DE\5\6\4\2EF\7\4\2"+
		"\2FK\3\2\2\2GK\5\n\6\2HK\7\35\2\2IK\5\b\5\2JB\3\2\2\2JG\3\2\2\2JH\3\2"+
		"\2\2JI\3\2\2\2Kb\3\2\2\2LM\f\f\2\2MN\7\t\2\2Na\5\6\4\rOP\f\n\2\2PQ\t\2"+
		"\2\2Qa\5\6\4\13RS\f\t\2\2ST\t\3\2\2Ta\5\6\4\nUV\f\b\2\2VW\t\4\2\2Wa\5"+
		"\6\4\tXY\f\7\2\2YZ\t\5\2\2Za\5\6\4\b[\\\f\13\2\2\\]\7\n\2\2]^\5\6\4\2"+
		"^_\7\13\2\2_a\3\2\2\2`L\3\2\2\2`O\3\2\2\2`R\3\2\2\2`U\3\2\2\2`X\3\2\2"+
		"\2`[\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\7\3\2\2\2db\3\2\2\2ef\7\3"+
		"\2\2fg\7\34\2\2gj\7\4\2\2hj\7\34\2\2ie\3\2\2\2ih\3\2\2\2j\t\3\2\2\2kl"+
		"\b\6\1\2ls\5\16\b\2mn\7\3\2\2no\5\20\t\2op\7\4\2\2ps\3\2\2\2qs\5\20\t"+
		"\2rk\3\2\2\2rm\3\2\2\2rq\3\2\2\2s{\3\2\2\2tu\f\5\2\2uv\7\n\2\2vw\5\6\4"+
		"\2wx\7\13\2\2xz\3\2\2\2yt\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\13\3"+
		"\2\2\2}{\3\2\2\2~\177\7\f\2\2\177\u0082\7\35\2\2\u0080\u0081\7\b\2\2\u0081"+
		"\u0083\5\6\4\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2"+
		"\2\2\u0084\u008d\7\6\2\2\u0085\u0086\7\r\2\2\u0086\u0089\7\35\2\2\u0087"+
		"\u0088\7\b\2\2\u0088\u008a\5\6\4\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2"+
		"\2\2\u008a\u008b\3\2\2\2\u008b\u008d\7\6\2\2\u008c~\3\2\2\2\u008c\u0085"+
		"\3\2\2\2\u008d\r\3\2\2\2\u008e\u008f\7\n\2\2\u008f\u0090\7\35\2\2\u0090"+
		"\u0091\7\16\2\2\u0091\u0092\5\6\4\2\u0092\u0093\7\17\2\2\u0093\u0094\5"+
		"\6\4\2\u0094\u0095\7\13\2\2\u0095\u009f\3\2\2\2\u0096\u0097\7\n\2\2\u0097"+
		"\u0098\7\35\2\2\u0098\u0099\7\16\2\2\u0099\u009a\5\6\4\2\u009a\u009b\7"+
		"\20\2\2\u009b\u009c\5\6\4\2\u009c\u009d\7\13\2\2\u009d\u009f\3\2\2\2\u009e"+
		"\u008e\3\2\2\2\u009e\u0096\3\2\2\2\u009f\17\3\2\2\2\u00a0\u00a4\7\n\2"+
		"\2\u00a1\u00a3\7\34\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2"+
		"\2\2\u00a7\u00a8\7\13\2\2\u00a8\21\3\2\2\2\21\25!.@J`bir{\u0082\u0089"+
		"\u008c\u009e\u00a4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}