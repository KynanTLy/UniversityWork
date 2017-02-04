import org.antlr.v4.runtime.tree.TerminalNode;
import org.stringtemplate.v4.*;
import java.util.*;

import static java.lang.Integer.max;

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
        System.out.print("[");
        if(value.size() != 0){
            for(int i = 0; i < value.size()-1; ++i){
                System.out.print(value.get(i));
                System.out.print(' ');
            }
        System.out.print(value.get(value.size()-1));
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
        for(int i = 0; i<size; ++i){
            temp.add(value);
        }
        return temp;
    }//end

    public ArrayList<Integer> vecExtend(ArrayList<Integer> value, int size, int typeCase){
        ArrayList<Integer> temp = value;
        switch (typeCase){
            //Division Case
            case 1:
                //System.out.println("ArrayBefore: " + value);
                //System.out.println("Size before: " + value.size());
                for(int i = value.size(); i<size; ++i){
                    temp.add(1);
                }
            default:
                for(int i = value.size(); i<size; ++i){
                    temp.add(0);
                }
        }
        //System.out.println("ArrayAfter: " + temp);
        return temp;
    }//end vecExtend

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
        //System.out.println("Scopetable of parent before if: " + scope.symbolTable.get("x").getValue());
        scope = new Scope(scope);
        int cond = (int) visit(ctx.mathexpr());
        if(cond != 0){
            for (GrammerParser.StateContext state : ctx.state()) {
                this.visit(state);
            }//end for loop
        }//end if
        //System.out.println("Scopetable of child: " + scope.symbolTable);
        scope = scope.enclosingScope;
        //System.out.println("Scopetable of parent: " + scope.symbolTable.get("x").getValue());
        return 0;
    }//end visitIF

    /**visitLOOP*/
    public Integer visitLOOP(GrammerParser.LOOPContext ctx){
        scope = new Scope(scope);
        while((int)visit(ctx.mathexpr()) > 0) {
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
        if(ctx.mathexpr() == null){
            PrintInt((int)visit(ctx.mathexpr()));
        } else {
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
        }
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
            this.scope.define(id, "vector", null);
        } else {
            //System.out.println("IN vector assign");
            Object value = visit(ctx.mathexpr());
            //System.out.println("Scope symbol table: " + scope.symbolTable);
            //System.out.println("Value: " + value);
            this.scope.define(id, "vector", value);
        }
        return 0;
    }//end visitINITV

    /**Init a INT*/
    public Integer visitINITI(GrammerParser.INITIContext ctx) {
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
        int size, size2;
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
                    size2 = ((ArrayList<Integer>) right).size();
                    if(size > size2){
                        right = vecExtend(((ArrayList<Integer>) right), size, 2);
                    } else if (size2 > size){
                        left = vecExtend(((ArrayList<Integer>) left), size2, 2);
                    }
                    for(int i = 0; i< max(size, size2); ++i){
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
                    size = ((ArrayList<Integer>) left).size();
                    right = intPromotion((int) right, size);
                    for(int i = 0; i< size; ++i){
                        if((((ArrayList<Integer>) right).get(i)) == 0){
                            throw new ArithmeticException("Divided by 0");
                        }//end divide by zero exception
                        returnVec.add((((ArrayList<Integer>) left).get(i)) / (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
                default:
                    size = ((ArrayList<Integer>) left).size();
                    size2 = ((ArrayList<Integer>) right).size();
                    if(size > size2){
                        right = vecExtend(((ArrayList<Integer>) right), size, 1);
                    } else if (size2 > size){
                        left = vecExtend(((ArrayList<Integer>) left), size2, 1);
                    }
                    //System.out.println("Size of Left : " + size + "Size of Right : " + size2);
                    //System.out.println("Size of LeftA : " + ((ArrayList<Integer>) left).size() + "Size of RightA : " + ((ArrayList<Integer>) right).size());
                    for(int i = 0; i< max(size, size2); ++i){
                        if((((ArrayList<Integer>) right).get(i)) == 0){
                            throw new ArithmeticException("Divided by 0");
                        }//end divide by zero exception
                        //System.out.println("Left : " + ((ArrayList<Integer>) left).get(i) + "SRight : " + ((ArrayList<Integer>) right).get(i));
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
        int size, size2;
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
                    size2 = ((ArrayList<Integer>) right).size();
                    if(size > size2){
                        right = vecExtend(((ArrayList<Integer>) right), size, 2);
                    } else if (size2 > size){
                        left = vecExtend(((ArrayList<Integer>) left), size2, 2);
                    }
                    for(int i = 0; i< max(size, size2); ++i){
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
                    size2 = ((ArrayList<Integer>) right).size();
                    if(size > size2){
                        right = vecExtend(((ArrayList<Integer>) right), size, 2);
                    } else if (size2 > size){
                        left = vecExtend(((ArrayList<Integer>) left), size2, 2);
                    }
                    for (int i = 0; i < max(size, size2); ++i) {
                        returnVec.add((((ArrayList<Integer>) left).get(i)) - (((ArrayList<Integer>) right).get(i)));
                    }
                    return returnVec;
            }//end Swtichcase
        }//end if - or +
    }//end visitADDSUB

    /**Greater Less than Rule*/
    public Integer visitGRTLES(GrammerParser.GRTLESContext ctx){
        //System.out.println("HELL0 WOLRD");
        //System.out.println("ctx.mathexpr" + visit(ctx.mathexpr(0)));
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


    /** RANGE Rule*/
    public Object visitRANGE(GrammerParser.RANGEContext ctx){
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
    public Object visitINDEX(GrammerParser.INDEXContext ctx){
        if(visit(ctx.mathexpr(1)) instanceof Integer){
            int index = (int)visit(ctx.mathexpr(1));
            ArrayList<Integer> array = (ArrayList<Integer>)visit(ctx.mathexpr(0));
            if(index >= array.size()){
                return 0;
            } else {
                return array.get(index);
            }
        } else {
            ArrayList<Integer> leftarray = (ArrayList<Integer>)visit(ctx.mathexpr(0));
            ArrayList<Integer> indexarray =  (ArrayList<Integer>) visit(ctx.mathexpr(1));
            ArrayList<Integer> returnarray =  new ArrayList<Integer>();
            for (int i = 0; i < indexarray.size(); ++i){
                if(i >= leftarray.size()){
                    returnarray.add(0);
                } else {
                    returnarray.add(leftarray.get(indexarray.get(i)));
                }
            }//end for
            return  returnarray;
        }
    }//end visitINDEX

    /** Vector Index Rule*/
    public Integer visitVECINDEX(GrammerParser.VECINDEXContext ctx){
        int index = (int)visit(ctx.mathexpr());
        ArrayList<Integer> array = (ArrayList<Integer>)visit(ctx.vecexpr());
        if(index >= array.size()){
            return 0;
        } else {
            return array.get(index);
        }
    }//end visitIndex

    /**Return ID Rule*/
    public Object visitREID(GrammerParser.REIDContext ctx){
        //System.out.println(scope.symbolTable);
        //System.out.println(ctx.ID().getText().toString() + " is the ID");
        if (scope.resolve(ctx.ID().getText()) != null){
            Object returnVal = scope.resolve(ctx.ID().getText()).getValue();
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
        this.scope = new Scope(this.scope);
        if(scope.resolve(ctx.ID().getText()) == null) {
            this.scope.define(ctx.ID().getText(), "int", null);
        }
        ArrayList<Integer> range = ((ArrayList<Integer>)visit(ctx.mathexpr(0)));
        int size = range.size();
        //System.out.println("RANGE: " + range);
        //System.out.println("size: " + size);
        for(int i = 0; i<size; ++i){
            this.scope.define(ctx.ID().getText(), "int", range.get(i));
            if((int)visit(ctx.mathexpr(1)) == 1){
                filter.add(range.get(i));
            }
        }//end forloop
        this.scope = this.scope.getEnclosingScope();
        return filter;
    }//end visitFILTER

    /**Generator Rule*/
    public ArrayList<Integer> visitGEN(GrammerParser.GENContext ctx){
        //System.out.println("DID I EVEN GET HERE?");
        ArrayList<Integer> generator = new ArrayList<Integer>();
        this.scope = new Scope(this.scope);
        if(scope.resolve(ctx.ID().getText()) == null) {
            this.scope.define(ctx.ID().getText(), "int", null);
        }
        //System.out.println("Scope table in gen: " + scope.symbolTable);
        ArrayList<Integer> range = ((ArrayList<Integer>)visit(ctx.mathexpr(0)));
        int size = range.size();
        //System.out.println("Size is: " + size);
        for(int i = 0; i<size; ++i){
            //generator.add((int)(this.scope.resolve(ctx.ID().getText().toString())).getValue());
            this.scope.define(ctx.ID().getText(), "int", range.get(i));
            //System.out.println("i is currently : " + scope.resolve(ctx.ID().getText().toString()).getValue());
            //generator.add((int)(this.scope.resolve(ctx.ID().getText().toString())).getValue());
            generator.add(((int)visit(ctx.mathexpr(1))));
        }//end forloop
        //System.out.println("scope enclose: " + scope.getEnclosingScope());
        //System.out.println("this scope enlose: " + this.scope.getEnclosingScope());
        this.scope = this.scope.getEnclosingScope();
        //System.out.println("Scope table out gen: " + scope.symbolTable);
        //System.out.println("Return: " + generator );
        return generator;
    }//end visitGEN

    //==========END VECTOR AND INT EXPR========//



}//end EvalVisitor



