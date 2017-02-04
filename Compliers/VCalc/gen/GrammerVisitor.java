// Generated from src/Grammer.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammerParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammerVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammerParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(GrammerParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IF}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIF(GrammerParser.IFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LOOP}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLOOP(GrammerParser.LOOPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PRINT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPRINT(GrammerParser.PRINTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ASSIGNMENT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGNMENT(GrammerParser.ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ASSIGN}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGN(GrammerParser.ASSIGNContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BRACKETME}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBRACKETME(GrammerParser.BRACKETMEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MULTDIV}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMULTDIV(GrammerParser.MULTDIVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MEVEC}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMEVEC(GrammerParser.MEVECContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GRTLES}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGRTLES(GrammerParser.GRTLESContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADDSUB(GrammerParser.ADDSUBContext ctx);
	/**
	 * Visit a parse tree produced by the {@code REID}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitREID(GrammerParser.REIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MEINT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMEINT(GrammerParser.MEINTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INDEX}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINDEX(GrammerParser.INDEXContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EQNEQ}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEQNEQ(GrammerParser.EQNEQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RANGE}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRANGE(GrammerParser.RANGEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BRACKETINT}
	 * labeled alternative in {@link GrammerParser#intexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBRACKETINT(GrammerParser.BRACKETINTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INT}
	 * labeled alternative in {@link GrammerParser#intexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINT(GrammerParser.INTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GENFIL}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGENFIL(GrammerParser.GENFILContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VEC}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVEC(GrammerParser.VECContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BRACKETVEC}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBRACKETVEC(GrammerParser.BRACKETVECContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VECINDEX}
	 * labeled alternative in {@link GrammerParser#vecexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVECINDEX(GrammerParser.VECINDEXContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INITV}
	 * labeled alternative in {@link GrammerParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINITV(GrammerParser.INITVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INITI}
	 * labeled alternative in {@link GrammerParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINITI(GrammerParser.INITIContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FILTER}
	 * labeled alternative in {@link GrammerParser#genfil}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFILTER(GrammerParser.FILTERContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GEN}
	 * labeled alternative in {@link GrammerParser#genfil}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGEN(GrammerParser.GENContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#vector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVector(GrammerParser.VectorContext ctx);
}