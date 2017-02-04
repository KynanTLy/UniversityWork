// Generated from src/Grammer.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammerParser}.
 */
public interface GrammerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammerParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(GrammerParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(GrammerParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MULTDIV}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterMULTDIV(GrammerParser.MULTDIVContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MULTDIV}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitMULTDIV(GrammerParser.MULTDIVContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BRACKET}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterBRACKET(GrammerParser.BRACKETContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BRACKET}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitBRACKET(GrammerParser.BRACKETContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Var}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterVar(GrammerParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Var}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitVar(GrammerParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterADDSUB(GrammerParser.ADDSUBContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitADDSUB(GrammerParser.ADDSUBContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NEGINT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterNEGINT(GrammerParser.NEGINTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NEGINT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitNEGINT(GrammerParser.NEGINTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code POWER}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterPOWER(GrammerParser.POWERContext ctx);
	/**
	 * Exit a parse tree produced by the {@code POWER}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitPOWER(GrammerParser.POWERContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterINT(GrammerParser.INTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitINT(GrammerParser.INTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Gen}
	 * labeled alternative in {@link GrammerParser#generator}.
	 * @param ctx the parse tree
	 */
	void enterGen(GrammerParser.GenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Gen}
	 * labeled alternative in {@link GrammerParser#generator}.
	 * @param ctx the parse tree
	 */
	void exitGen(GrammerParser.GenContext ctx);
}