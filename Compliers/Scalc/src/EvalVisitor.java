import org.antlr.v4.runtime.tree.TerminalNode;
import org.stringtemplate.v4.*;
import java.util.*;

public class EvalVisitor extends GrammerBaseVisitor {
    //==========DATA==========//
    private Scope scope;
    private STGroup template;
    private int mode;
    private ST st;


    public void setTemplate() {
        mode = Main.getMode();
        switch (mode) {
            case 1:
                template = new STGroupFile("src/LLVM.stg");
                break;
            default:
                break;
        }//end switch case
    }//end setTemplate

    public void PrintInt(int value){
        System.out.println(value);
    }//end


    public void PrintVec(ArrayList<Integer> value){
        System.out.print("[ ");
        for(int i = 0; i < value.size(); ++i){
            System.out.print(value.get(i));
            System.out.print(' ');
        }
        System.out.println(']');
    }

    public Integer typeCase(Object left, Object right){
        if(left instanceof Integer){
            if (right instanceof Integer){
                return 1;
            } else {
                return 2;
            }
        } else {
            if (right instanceof Integer){
                return 3;
            } else {
                return 4;
            }
        }//end if
    }//end typeCase

    public ArrayList<Integer> intPromotion(int value, int size){
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(value);
        for(int i = temp.size(); i<size; ++i){
            temp.add(0);
        }
        return temp;
    }//end

    //==========OVERRIDES FUNCTION OF GRAMMAR BASE VISITOR==========//

    @Override
    //========VISIT PROGRAM===========//
    public Integer visitProg(GrammerParser.ProgContext ctx){
        scope = new Scope(null);
        visitChildren(ctx);
        return 0;
    }//end visitProg

    //========START STATE=============//

    /**If Statement*/
    public Integer visitIF(GrammerParser.IFContext ctx){
        //System.out.println(ctx.mathexpr().getText());
        scope = new Scope(scope.enclosingScope);
        int cond = (int) visit(ctx.mathexpr());
        if(cond != 0){
            for (GrammerParser.StateContext state : ctx.state()) {
                this.visit(state);
            }//end for loop
        }//end if
        scope = scope.enclosingScope;
        return 0;
    }//end visitIF

    /**visitLOOP*/
    public Integer visitLOOP(GrammerParser.LOOPContext ctx){
        scope = new Scope(scope.enclosingScope);
        while((int)this.visit(ctx.mathexpr()) != 0) {
            for (GrammerParser.StateContext state : ctx.state()) {
                //System.out.println(state.getText());
                this.visit(state);
            }//end for loop
        }//end while loop
        scope = scope.enclosingScope;
        return 0;
    }//end visitLOOP

    /**visitPRINT*/
    public Integer visitPRINT(GrammerParser.PRINTContext ctx){
        Object temp = visit(ctx.mathexpr());
        if(temp instanceof Symbol){
            String type = ((Symbol) temp).getType();
            if(type.equals("int")){
                int result = (int)((Symbol) temp).getValue();
                PrintInt(result);
            } else {
                PrintVec(( (ArrayList<Integer>) ((Symbol) temp).getValue()));
            }
        } else if(temp instanceof Integer){
            PrintInt((int)temp);
        } else {
            PrintVec((ArrayList<Integer>) temp);
        }//end if
        return 0;
    }//end visitPRINT

    /**Assign a variable*/
    public Integer visitASSIGN(GrammerParser.ASSIGNContext ctx){
        String id = ctx.ID().getText();
        Object value = visit(ctx.mathexpr());
        scope.setVar(id, value);
        return 0;
    }//end visitINIT


    /**Init a Vector*/
    public Integer visitINITV(GrammerParser.INITVContext ctx){
        String id = ctx.ID().getText();
        if(id.matches("if|fi|loop|pool|int|print|vector")){
            throw new IllegalArgumentException("That word is reserved");
        }
        if(ctx.mathexpr() == null){
            scope.define(id, "vector", null);
        } else {
            Object value = visit(ctx.mathexpr());
            scope.define(id, "vector", value);
        }
        return 0;
    }//end visitINITV

    /**Init a INT*/
    public Integer  visitINITI(GrammerParser.INITIContext ctx) {
        String id = ctx.ID().getText();
        if(id.matches("if|fi|loop|pool|int|print|vector")) {
            throw new IllegalArgumentException("That word is reserved");
        }
        if(ctx.mathexpr() == null){
            scope.define(id, "int", null);
        } else {
            Object value = visit(ctx.mathexpr());
            scope.define(id, "int", value);
        }
        return 0;
    }//end visitINITV

    //==========END STATE============//

    //==========START MATHEXPR======//
    /** MUL/DIV Rule*/
    public Object visitMULTDIV(GrammerParser.MULTDIVContext ctx) {
        Object left = visit(ctx.mathexpr(0));
        Object right = visit(ctx.mathexpr(1));
        int size;
        ArrayList<Integer> returnVec = new ArrayList<>();
        if(ctx.op.getType() == GrammerParser.MUL){
            int temp = typeCase(left, right);
            switch (temp){
                case 1:
                    return (int)left * (int)right;
                case 2:
                    size = ((ArrayList<Integer>) right).size();
                    left = intPromotion((int) left, size);
                    for(int i = 0; i< size; ++i){
                        returnVec.add((((ArrayList<Integer>) left).get(i)) * (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                case 3:
                    size = ((ArrayList<Integer>) left).size();
                    right = intPromotion((int) right, size);
                    for(int i = 0; i< size; ++i){
                        returnVec.add((((ArrayList<Integer>) left).get(i)) * (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                default:
                    size = ((ArrayList<Integer>) left).size();
                    for(int i = 0; i< size; ++i){
                        returnVec.add((((ArrayList<Integer>) left).get(i)) * (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
            }//end switchcase
        } else {
            if(right instanceof Integer && (int) right == 0){
                throw new ArithmeticException("Divided by 0");
            }//end divide by zero exception
            int temp = typeCase(left, right);
            switch (temp){
                case 1:
                    return (int)left / (int)right;
                case 2:
                    size = ((ArrayList<Integer>) right).size();
                    left = intPromotion((int) left, size);
                    for(int i = 0; i< size; ++i){
                        if((((ArrayList<Integer>) right).get(i)) == 0){
                            throw new ArithmeticException("Divided by 0");
                        }//end divide by zero exception
                        returnVec.add((((ArrayList<Integer>) left).get(i)) / (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                case 3:
                    //CASE AUTO FAILS AS RIGHT SIDE HAS 0 FOR SURE
                    throw new ArithmeticException("Divided by 0");
                default:
                    size = ((ArrayList<Integer>) left).size();
                    for(int i = 0; i< size; ++i){
                        if((((ArrayList<Integer>) right).get(i)) == 0){
                            throw new ArithmeticException("Divided by 0");
                        }//end divide by zero exception
                        returnVec.add((((ArrayList<Integer>) left).get(i)) / (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
            }//end switchcase
        }//end if
    }//end visitMULTDIV

    /** ADD/SUB Rule*/
    public Object visitADDSUB(GrammerParser.ADDSUBContext ctx) {
        Object left = visit(ctx.mathexpr(0));
        Object right = visit(ctx.mathexpr(1));
        ArrayList<Integer> returnVec = new ArrayList<>();
        int size;
        if (ctx.op.getType() == GrammerParser.ADD) {
            int temp = typeCase(left, right);
            switch (temp){
                case 1:
                    return (int)left + (int)right;
                case 2:
                    size = ((ArrayList<Integer>) right).size();
                    left = intPromotion((int) left, size);
                    for(int i = 0; i< size; ++i){
                        returnVec.add((((ArrayList<Integer>) left).get(i)) + (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                case 3:
                    size = ((ArrayList<Integer>) left).size();
                    right = intPromotion((int) right, size);
                    for(int i = 0; i< size; ++i){
                        returnVec.add((((ArrayList<Integer>) left).get(i)) + (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                default:
                    size = ((ArrayList<Integer>) left).size();
                    for(int i = 0; i< size; ++i){
                        returnVec.add((((ArrayList<Integer>) left).get(i)) + (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
            }//end switchcase
        } else {
            int temp = typeCase(left, right);
            switch (temp) {
                case 1:
                    return (int) left - (int) right;
                case 2:
                    size = ((ArrayList<Integer>) right).size();
                    left = intPromotion((int) left, size);
                    for (int i = 0; i < size; ++i) {
                        returnVec.add((((ArrayList<Integer>) left).get(i)) - (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                case 3:
                    size = ((ArrayList<Integer>) left).size();
                    right = intPromotion((int) right, size);
                    for (int i = 0; i < size; ++i) {
                        returnVec.add((((ArrayList<Integer>) left).get(i)) - (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                default:
                    size = ((ArrayList<Integer>) left).size();
                    for (int i = 0; i < size; ++i) {
                        returnVec.add((((ArrayList<Integer>) left).get(i)) - (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
            }//end Swtichcase
        }//end if - or +
    }//end visitADDSUB

    /**Greater Less than Rule*/
    public Integer visitGRTLES(GrammerParser.GRTLESContext ctx){
        int left = (int)visit(ctx.mathexpr(0));
        int right = (int)visit(ctx.mathexpr(1));
        if(ctx.op.getType() == GrammerParser.LES){
            if (left < right){
                return 1;
            } else {
                return 0;
            }//end if less
        } else {
            if(left > right ){
                return 1;
            } else {
                return 0;
            }//end if greater
        }//end if < or >
    }//end visitGRTLES

    /** Equal or not equal Rule */
    public Integer visitEQNEQ(GrammerParser.EQNEQContext ctx){
        int left = (int)visit(ctx.mathexpr(0));
        int right = (int)visit(ctx.mathexpr(1));
        if(ctx.op.getType() == GrammerParser.EQU){
            if (left == right){
                return 1;
            } else {
                return 0;
            }//end if ==
        } else {
            if(left != right ){
                return 1;
            } else {
                return 0;
            }//end if !=
        }//end if
    }//end visitEQNEQ

    public ArrayList<Integer> visitRANGE(GrammerParser.RANGEContext ctx){
        int left = (int)visit(ctx.mathexpr(0));
        int right = (int)visit(ctx.mathexpr(1));
        ArrayList<Integer> vector = new ArrayList<Integer>();
        for(int i = left; i < right+1; ++i){
            vector.add(i);
        }
        return vector;
    }//end visitRANGE

    /** '(' EXPR ')' Rule*/
    public Object visitBRACKETME(GrammerParser.BRACKETMEContext ctx) {
        return visit(ctx.mathexpr());
    }//end visitBRACKETME

    /** Index Rule */
    public Integer visitINDEX(GrammerParser.INDEXContext ctx){
        return (int)((ArrayList<Integer>)visit(ctx.mathexpr(0))).get(((int)visit(ctx.mathexpr(1))));
    }//end visitINDEX

    /**Return ID Rule*/
    public Object visitREID(GrammerParser.REIDContext ctx){
        System.out.println(scope.symbolTable);
        System.out.println(ctx.ID().getText().toString() + " is the ID");
        if (scope.resolve(ctx.ID().getText().toString()) != null){
            Object returnVal = scope.resolve(ctx.ID().getText().toString()).getValue();
            return returnVal;
        } else {
            throw new IllegalArgumentException("Variable was not declared");
        }
    }//end visitREID

    //==========END MATHEXPR======//

    //==========START VECTOR AND INT EXPR========//
    /** INT Rule*/
    public Integer visitINT(GrammerParser.INTContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }//end visitInt

    /** BRACKET INT Rule*/
    public Integer visitBRACKETINT(GrammerParser.BRACKETINTContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }//end visitVar

    /** Vector Rule*/
    @Override
    public ArrayList<Integer> visitVector(GrammerParser.VectorContext ctx) {
        ArrayList<Integer> vector = new ArrayList<Integer>();
        for(TerminalNode integer : ctx.INT()){
            vector.add(Integer.valueOf(integer.getText()));
        }
        return vector;
    }//end visitVector


    /**Filter Rule */
    public ArrayList<Integer> visitFILTER(GrammerParser.FILTERContext ctx){
        ArrayList<Integer> filter = new ArrayList<Integer>();
        scope = new Scope(scope.enclosingScope);
        scope.define(ctx.ID().getText().toString(), "int", null);
        int size = ((ArrayList<Integer>)visit(ctx.mathexpr())).size();
        for(int i = 0; i<((ArrayList<Integer>)visit(ctx.mathexpr())).get(size-1); ++i){
            if((int)visit(ctx.compare()) == 1){
                filter.add((int)(scope.resolve(ctx.ID().getText().toString())).getValue());
            }
        }//end forloop
        scope = scope.enclosingScope;
        return filter;
    }//end visitFILTER

    /**Generator Rule*/
    public ArrayList<Integer> visitGEN(GrammerParser.GENContext ctx){
        System.out.println("DID I EVEN GET HERE?");
        ArrayList<Integer> generator = new ArrayList<Integer>();
        this.scope = new Scope(this.scope.enclosingScope);
        this.scope.define(ctx.ID().getText().toString(), "int", null);
        //System.out.println(scope.symbolTable.get(ctx.ID().getText().toString()));
        int size = ((ArrayList<Integer>)visit(ctx.mathexpr(0))).size();
        for(int i = 0; i<((ArrayList<Integer>)visit(ctx.mathexpr(0))).get(size-1); ++i){
            generator.add((int)(this.scope.resolve(ctx.ID().getText().toString())).getValue());
            generator.add(((ArrayList<Integer>)visit(ctx.mathexpr(1))).get(i));
        }//end forloop
        this.scope = this.scope.enclosingScope;
        return generator;
    }//end visitGEN

    //==========END VECTOR AND INT EXPR========//



}//end EvalVisitor

