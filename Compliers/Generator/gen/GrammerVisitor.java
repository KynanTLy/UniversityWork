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
	 * Visit a parse tree produced by the {@code BRACKET}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBRACKET(GrammerParser.BRACKETContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Var}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(GrammerParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADDSUB}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADDSUB(GrammerParser.ADDSUBContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NEGINT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNEGINT(GrammerParser.NEGINTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code POWER}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPOWER(GrammerParser.POWERContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INT}
	 * labeled alternative in {@link GrammerParser#mathexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINT(GrammerParser.INTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Gen}
	 * labeled alternative in {@link GrammerParser#generator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGen(GrammerParser.GenContext ctx);
}