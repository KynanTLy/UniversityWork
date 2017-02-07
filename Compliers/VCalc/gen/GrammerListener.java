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
	 * Enter a parse tree produced by the {@code ASSIGNMENT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNMENT(GrammerParser.ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNMENT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNMENT(GrammerParser.ASSIGNMENTContext ctx);
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
	/**
	 * Enter a parse tree produced by the {@code BRACKETME}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterBRACKETME(GrammerParser.BRACKETMEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BRACKETME}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitBRACKETME(GrammerParser.BRACKETMEContext ctx);
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
	 * Enter a parse tree produced by the {@code MEVEC}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterMEVEC(GrammerParser.MEVECContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MEVEC}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitMEVEC(GrammerParser.MEVECContext ctx);
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
	 * Enter a parse tree produced by the {@code REID}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterREID(GrammerParser.REIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code REID}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitREID(GrammerParser.REIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MEINT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterMEINT(GrammerParser.MEINTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MEINT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitMEINT(GrammerParser.MEINTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INDEX}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterINDEX(GrammerParser.INDEXContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INDEX}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitINDEX(GrammerParser.INDEXContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EQNEQ}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterEQNEQ(GrammerParser.EQNEQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EQNEQ}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitEQNEQ(GrammerParser.EQNEQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RANGE}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void enterRANGE(GrammerParser.RANGEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RANGE}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 */
	void exitRANGE(GrammerParser.RANGEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BRACKETINT}
	 * labeled alternative in {@link GrammerParser#intexpr}.
	 * @param ctx the parse tree
	 */
	void enterBRACKETINT(GrammerParser.BRACKETINTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BRACKETINT}
	 * labeled alternative in {@link GrammerParser#intexpr}.
	 * @param ctx the parse tree
	 */
	void exitBRACKETINT(GrammerParser.BRACKETINTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INT}
	 * labeled alternative in {@link GrammerParser#intexpr}.
	 * @param ctx the parse tree
	 */
	void enterINT(GrammerParser.INTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INT}
	 * labeled alternative in {@link GrammerParser#intexpr}.
	 * @param ctx the parse tree
	 */
	void exitINT(GrammerParser.INTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GENFIL}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void enterGENFIL(GrammerParser.GENFILContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GENFIL}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void exitGENFIL(GrammerParser.GENFILContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VEC}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void enterVEC(GrammerParser.VECContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VEC}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void exitVEC(GrammerParser.VECContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BRACKETVEC}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void enterBRACKETVEC(GrammerParser.BRACKETVECContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BRACKETVEC}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void exitBRACKETVEC(GrammerParser.BRACKETVECContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VECINDEX}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void enterVECINDEX(GrammerParser.VECINDEXContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VECINDEX}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 */
	void exitVECINDEX(GrammerParser.VECINDEXContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INITV}
	 * labeled alternative in {@link GrammerParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterINITV(GrammerParser.INITVContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INITV}
	 * labeled alternative in {@link GrammerParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitINITV(GrammerParser.INITVContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INITI}
	 * labeled alternative in {@link GrammerParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterINITI(GrammerParser.INITIContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INITI}
	 * labeled alternative in {@link GrammerParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitINITI(GrammerParser.INITIContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FILTER}
	 * labeled alternative in {@link GrammerParser#genfil}.
	 * @param ctx the parse tree
	 */
	void enterFILTER(GrammerParser.FILTERContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FILTER}
	 * labeled alternative in {@link GrammerParser#genfil}.
	 * @param ctx the parse tree
	 */
	void exitFILTER(GrammerParser.FILTERContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GEN}
	 * labeled alternative in {@link GrammerParser#genfil}.
	 * @param ctx the parse tree
	 */
	void enterGEN(GrammerParser.GENContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GEN}
	 * labeled alternative in {@link GrammerParser#genfil}.
	 * @param ctx the parse tree
	 */
	void exitGEN(GrammerParser.GENContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#vector}.
	 * @param ctx the parse tree
	 */
	void enterVector(GrammerParser.VectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#vector}.
	 * @param ctx the parse tree
	 */
	void exitVector(GrammerParser.VectorContext ctx);
}