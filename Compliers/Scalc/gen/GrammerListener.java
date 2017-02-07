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
	 * Enter a parse tree produced by the {@code GRTLES}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterGRTLES(GrammerParser.GRTLESContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GRTLES}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitGRTLES(GrammerParser.GRTLESContext ctx);
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
	 * Enter a parse tree produced by the {@code COMPARE}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterCOMPARE(GrammerParser.COMPAREContext ctx);
	/**
	 * Exit a parse tree produced by the {@code COMPARE}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitCOMPARE(GrammerParser.COMPAREContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ID}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterID(GrammerParser.IDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ID}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitID(GrammerParser.IDContext ctx);
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
	 * Enter a parse tree produced by the {@code INIT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void enterINIT(GrammerParser.INITContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INIT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void exitINIT(GrammerParser.INITContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IF}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void enterIF(GrammerParser.IFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IF}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void exitIF(GrammerParser.IFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LOOP}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void enterLOOP(GrammerParser.LOOPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LOOP}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void exitLOOP(GrammerParser.LOOPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PRINT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void enterPRINT(GrammerParser.PRINTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PRINT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void exitPRINT(GrammerParser.PRINTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGN}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void enterASSIGN(GrammerParser.ASSIGNContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGN}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void exitASSIGN(GrammerParser.ASSIGNContext ctx);
}