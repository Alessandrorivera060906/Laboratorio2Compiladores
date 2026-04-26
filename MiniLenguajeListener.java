// Generated from MiniLenguaje.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniLenguajeParser}.
 */
public interface MiniLenguajeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniLenguajeParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(MiniLenguajeParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLenguajeParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(MiniLenguajeParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLenguajeParser#declaracion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracion(MiniLenguajeParser.DeclaracionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLenguajeParser#declaracion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracion(MiniLenguajeParser.DeclaracionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLenguajeParser#valor}.
	 * @param ctx the parse tree
	 */
	void enterValor(MiniLenguajeParser.ValorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLenguajeParser#valor}.
	 * @param ctx the parse tree
	 */
	void exitValor(MiniLenguajeParser.ValorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLenguajeParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(MiniLenguajeParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLenguajeParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(MiniLenguajeParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLenguajeParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(MiniLenguajeParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLenguajeParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(MiniLenguajeParser.FactorContext ctx);
}