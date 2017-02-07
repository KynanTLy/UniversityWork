/**
 * Created by kynan on 9/13/16.
 */


import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends GrammerBaseVisitor<Integer> {
    Map<String, Integer> memory = new HashMap<String, Integer>();

    @Override
    /** Generator Rule*/
    public Integer visitGen(GrammerParser.GenContext ctx) {

        if (ctx.Var() == null){
            return 0;
        }
        //Parse out the tokens to be used
        String var = ctx.Var().getText();
        int min = Integer.valueOf(ctx.min.getText());
        int max = Integer.valueOf(ctx.max.getText());
        int result;

        //Evaluate the mathexpr given the range and variables
        for(int i = min; i <= max; i++){
            memory.put(var, i);
            result = visit(ctx.mathexpr());
            if (i == max){
                System.out.print(result + "\n");
            } else {
                System.out.print(result + " ");
            }
        }//end for loop

        return 0;
    }//end VisitPower

    /** Power Rule*/
    public Integer visitPOWER(GrammerParser.POWERContext ctx) {
        int left = visit(ctx.mathexpr(0));
        int right = visit(ctx.mathexpr(1));
        return (int)(Math.pow((double)left, (double)right));
    }//end VisitPower

    /** MUL/DIV/MOD Rule*/
    public Integer visitMULTDIV(GrammerParser.MULTDIVContext ctx) {
        int left = visit(ctx.mathexpr(0));
        int right = visit(ctx.mathexpr(1));
        if(ctx.op.getType() == GrammerParser.MUL){
            return left*right;
        } else if (ctx.op.getType() == GrammerParser.DIV){
            if(right ==0 ){
                throw new ArithmeticException("Divided by 0");
            }
            return left/right;
        } else {
            return left%right;
        }//end if
    }//end visitMULTDIV

    /** ADD/SUB Rule*/
    public Integer visitADDSUB(GrammerParser.ADDSUBContext ctx) {
        int left = visit(ctx.mathexpr(0));
        int right = visit(ctx.mathexpr(1));
        if(ctx.op.getType() == GrammerParser.ADD){
            return left+right;
        } else  {
            return left-right;
        }//end if
    }//end visitMULTDIV

    /** '(' EXPR ')' Rule*/
    public Integer visitBRACKET(GrammerParser.BRACKETContext ctx) {
        return visit(ctx.mathexpr());
    }//end visitBracket

    /** INT Rule*/
    public Integer visitINT(GrammerParser.INTContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }//end visitInt

    /** NEGINT Rule*/
    public Integer visitNEGINT(GrammerParser.NEGINTContext ctx) {
        return (Integer.valueOf(ctx.INT().getText())* -1);
    }//end visitInt

    /** VAR Rule*/
    public Integer visitVar(GrammerParser.VarContext ctx) {
        String var = ctx.Var().getText();
        if(memory.containsKey(var)) return memory.get(var);
        return 0;
    }//end visitVar

}//end EvalVisitor
