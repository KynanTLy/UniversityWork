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
	 * Visit a parse tree produced by the {@code MULTDIV}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMULTDIV(GrammerParser.MULTDIVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GRTLES}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGRTLES(GrammerParser.GRTLESContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BRACKET}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBRACKET(GrammerParser.BRACKETContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADDSUB(GrammerParser.ADDSUBContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COMPARE}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOMPARE(GrammerParser.COMPAREContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ID}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitID(GrammerParser.IDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINT(GrammerParser.INTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INIT}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINIT(GrammerParser.INITContext ctx);
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
	 * Visit a parse tree produced by the {@code ASSIGN}
	 * labeled alternative in {@link GrammerParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGN(GrammerParser.ASSIGNContext ctx);
}