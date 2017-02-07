import org.stringtemplate.v4.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class EvalVisitor extends GrammerBaseVisitor<Integer> {
    //==========DATA==========//
    Map<String, Integer> memory = new HashMap<String, Integer>();
    STGroup template;
    int mode;
    ST st;

    //Variables to store/maintain stringtemplate
    //and keep track of register usage
    ArrayList<ST> variable;
    ArrayList<ST> prog;
    ArrayList<String> type;
    Stack<Integer> thestack;

    //Variables used for jump/loop/if management
    int loopCounter = 0;
    int ifCounter = 0;
    ArrayList<Integer> ifEnd;
    ArrayList<Integer> loopEnd;

    //==========CONSTRUCTOR==========//

    //Init Arraylist/stack
    public EvalVisitor(){
        variable = new ArrayList<ST>();
        prog = new ArrayList<ST>();
        type = new ArrayList<String>();
        thestack = new Stack<Integer>();
        ifEnd= new ArrayList<Integer>();
        loopEnd = new ArrayList<Integer>();
    }//end constructor

    //==========HELPER FUNCTIONS==========//

    //Sets the string template with A,B,stack register
    //This is mostly here to clean up code in override function
    public void setABSTACK(int jump){
        if(jump == 1){
            st.add("A", Convert(thestack.size()));
            st.add("B", Convert(thestack.size()+1));
            if (type.get(type.size()-1).equals("if")) {
                st.add("type", "if");
                st.add("typenum", ifCounter);
            } else {
                st.add("type", "loopEnd");
                st.add("typenum", loopCounter);
            }//end if loop or if label
            prog.add(st);
        } else {
            st.add("A", Convert(thestack.size()));
            st.add("B", Convert(thestack.size()+1));
            st.add("stack", Convert(thestack.size()));
            prog.add(st);
            thestack.push(1);
        }
    }//end setABSTACK

    //Change template group to the correct type
    public void setTemplate() {
        mode = Main.getMode();
        switch (mode) {
            case 1:
                template = new STGroupFile("src/MIPS.stg");
                break;
            case 2:
                template = new STGroupFile("src/x86.stg");
                break;
            case 3:
                template = new STGroupFile("src/ARM.stg");
                //Push 4 onto to stack to preserve R0-R3
                thestack.push(1);
                thestack.push(1);
                thestack.push(1);
                thestack.push(1);
                break;
            default:
                break;
        }//end switch case
    }//end setTemplate

    //Convert to proper format
    public String Convert(int stackNum){
        if (mode == 2){
            switch(stackNum){
                case 0:
                    return "EAX";
                case 1:
                    return "EBX";
                case 2:
                    return "ECX";
                default:
                    return "EDX";
            }//end switch
        } else {
            return Integer.toString(stackNum);
        }//end if statement
    }//end Convert

    //==========OVERRIDES FUNCTION OF GRAMMAR BASE VISITOR==========//

    @Override
    /**Evaluate the program*/
    public Integer visitProg(GrammerParser.ProgContext ctx){
        setTemplate();
        visitChildren(ctx);
        if (mode != 0) {

            //Prints the "data" string template
            st = template.getInstanceOf("StartData");
            System.out.println(st.render());
            for (int i = 0; i <= variable.size() - 1; ++i) {
                System.out.println(variable.get(i).render());
            }//end for loop data

            //Prints the "program" string templates
            st = template.getInstanceOf("StartText");
            System.out.println(st.render());
            for (int i = 0; i <= prog.size() - 1; ++i) {
                System.out.println(prog.get(i).render());
            }//end for loop prog

            st = template.getInstanceOf("Exit");
            System.out.println(st.render());
        }
        if (mode == 2){
            //If it was ARM pop the extra value from the stack
            for (int i = 0; i < thestack.size()-1; ++i){
                thestack.pop();
            }//end for loop
        }//end if ARM
        return 0;
    }//end visitProg

    /**Init a variable*/
    public Integer visitINIT(GrammerParser.INITContext ctx){
        String id = ctx.ID().getText();
        int value;
        if(id.matches("if|fi|loop|pool|int|print")){
            throw new IllegalArgumentException("That word is reserved");
        }
        if (mode == 0) {
            if (ctx.mathexpr() == null) {
                memory.put(id, null);
            } else {
                value = visit(ctx.mathexpr());
                memory.put(id, value);
            }//end if
        } else {
            //By default all variables are declared with 0 at start of program
            memory.put(id, null);
            st = template.getInstanceOf("InitVal");
            st.add("varName", id);
            st.add("val", 0);
            variable.add(st);
            if (ctx.mathexpr() != null) {
                visit(ctx.mathexpr());
                st = template.getInstanceOf("StoreVar");
                st.add("stack", Convert(thestack.size()-1));
                st.add("varName", id);
                prog.add(st);
                memory.put(id,thestack.peek());
                thestack.pop();
            }//end inner if
        }//end if
        return 0;
    }//end visitINIT

    /**Assign a variable*/
    public Integer visitASSIGN(GrammerParser.ASSIGNContext ctx){
        String id = ctx.ID().getText();
        int value = visit(ctx.mathexpr());
        if (mode == 0){
            if (memory.containsKey(id)){
                memory.put(id, value);
            } else {
                throw new IllegalArgumentException("Variable was not declared");
            }//end if variable not declared
        } else {
            if (memory.containsKey(id)){
                st = template.getInstanceOf("StoreVar");
                st.add("stack", Convert(thestack.size()-1));
                st.add("varName", id);
                prog.add(st);
                memory.put(id, thestack.peek());
                thestack.pop();
            } else {
                throw new IllegalArgumentException("Variable was not declared");
            }//end if for assembly
        }//end if statement
        return 0;
    }//end visitASSIGN

    /**visitLOOP*/
    public Integer visitLOOP(GrammerParser.LOOPContext ctx){
        if (mode == 0){
            while(this.visit(ctx.mathexpr()) != 0) {
                for (GrammerParser.StateContext state : ctx.state()) {
                    //System.out.println(state.getText());
                    this.visit(state);
                }//end for loop
            }//end while loop
        } else {
            //Keep track of the loop for labeling
            loopCounter += 1;
            loopEnd.add(loopCounter);
            type.add("loop");
            st = template.getInstanceOf("StartLoop");
            st.add("Loopnum", loopCounter);
            prog.add(st);

            //Visit the inside of the loop
            visit(ctx.mathexpr());
            for (GrammerParser.StateContext state : ctx.state()) {
                this.visit(state);
            }//end for loop

            //Label the end/jump loop
            st = template.getInstanceOf("ReLoop");
            st.add("Loopnum", loopEnd.get(loopEnd.size()-1));
            prog.add(st);
            st = template.getInstanceOf("EndLoop");
            st.add("Loopnum", loopEnd.get(loopEnd.size()-1));
            prog.add(st);
            loopEnd.remove(loopEnd.size()-1);

        }//end if
        return 0;
    }//end visitLOOP

    /**visitPRINT*/
    public Integer visitPRINT(GrammerParser.PRINTContext ctx){
        int expression =  visit(ctx.mathexpr());
        if (mode == 0){
            System.out.print(expression + "\n");
        } else {
            st = template.getInstanceOf("Print");
            st.add("stack", Convert(thestack.size()-1));
            prog.add(st);
            thestack.pop();
        }//end if
        return 0;
    }//end visitPRINT

    /**If Statement*/
    public Integer visitIF(GrammerParser.IFContext ctx){
        //System.out.println(ctx.mathexpr().getText());
        ifCounter += 1;
        ifEnd.add(ifCounter);
        type.add("if");
        int cond = visit(ctx.mathexpr());
        if (mode == 0){
            if(cond != 0){
                for (GrammerParser.StateContext state : ctx.state()) {
                    this.visit(state);
                }//end for loop
            }//end if
            return 0;
        } else {
            for (GrammerParser.StateContext state : ctx.state()) {
                this.visit(state);
            }//end for

            //Keep track end of if labels
            type.remove(type.size()-1);
            st = template.getInstanceOf("EndIf");
            st.add("ifnum", ifEnd.get(ifEnd.size()-1));
            ifEnd.remove(ifEnd.size()-1);
            prog.add(st);
        }
        return 0;

    }//end visitIF

    /** MUL/DIV Rule*/
    public Integer visitMULTDIV(GrammerParser.MULTDIVContext ctx) {
        int left = visit(ctx.mathexpr(0));
        int right = visit(ctx.mathexpr(1));
        if (mode == 0){
            if(ctx.op.getType() == GrammerParser.MUL){
                return left*right;
            } else {
                if(right ==0 ){
                    throw new ArithmeticException("Divided by 0");
                }//end divide by zero exception
                return left/right;
            }//end if
        } else {
            //Pop the 2 value on the stack (the left and right of the expression)
            thestack.pop();
            thestack.pop();
            if(ctx.op.getType() == GrammerParser.MUL){
                st = template.getInstanceOf("Mul");
                setABSTACK(0);
            } else {
                if(right == 0 ){
                    throw new ArithmeticException("Divided by 0");
                }//end divide by 0
                st = template.getInstanceOf("Div");
                setABSTACK(0);
            }//end if MUL/DIV
            return 0;
        }//end if
    }//end visitMULTDIV

    /**Greater Less than Rule*/
    public Integer visitGRTLES(GrammerParser.GRTLESContext ctx){
        int left = visit(ctx.mathexpr(0));
        int right = visit(ctx.mathexpr(1));
        if (mode == 0){
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
        } else {
            //Pop the 2 value on the stack (the left and right of the expression)
            thestack.pop();
            thestack.pop();
            if(ctx.op.getType() == GrammerParser.LES) {
                st = template.getInstanceOf("Less");
                setABSTACK(1);
            } else {
                st = template.getInstanceOf("Great");
                setABSTACK(1);
            }//end if < or >
            return 0;
        }//end if
    }//end visitGRTLES

    /**COMPARE RULE*/
    public Integer visitCOMPARE(GrammerParser.COMPAREContext ctx) {
        int left = visit(ctx.mathexpr(0));
        int right = visit(ctx.mathexpr(1));
        if (mode == 0){
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
        } else {
            //Pop the 2 value on the stack (the left and right of the expression)
            thestack.pop();
            thestack.pop();
            if (ctx.op.getType() == GrammerParser.EQU) {
                st = template.getInstanceOf("Equal");
                setABSTACK(1);
            } else {
                st = template.getInstanceOf("NotEqual");
                setABSTACK(1);
            }//end if == or !=
        }//end if
        return 0;
    }//end visitCOMPARE

    /** ADD/SUB Rule*/
    public Integer visitADDSUB(GrammerParser.ADDSUBContext ctx) {
        int left = visit(ctx.mathexpr(0));
        int right = visit(ctx.mathexpr(1));
        if (mode == 0) {
            if (ctx.op.getType() == GrammerParser.ADD) {
                return left + right;
            } else {
                return left - right;
            }//end if - or +
        } else {
            thestack.pop();
            thestack.pop();
            if (ctx.op.getType() == GrammerParser.ADD) {
                //The Add statement
                st = template.getInstanceOf("Add");
                setABSTACK(0);
            } else {
                //The sub statement
                st = template.getInstanceOf("Sub");
                setABSTACK(0);
            }//end if + or -
            return 0;
        }//end if
    }//end visitADDSUB

    /** '(' EXPR ')' Rule*/
    public Integer visitBRACKET(GrammerParser.BRACKETContext ctx) {
        return visit(ctx.mathexpr());
    }//end visitBracket

    /** INT Rule*/
    public Integer visitINT(GrammerParser.INTContext ctx) {
        if (mode != 0){
            thestack.push(Integer.valueOf(ctx.INT().getText()));
            st = template.getInstanceOf("Int");
            st.add("stack", Convert(thestack.size() - 1));
            st.add("val", thestack.peek());
            prog.add(st);
        }//end if not interpret
        return Integer.valueOf(ctx.INT().getText());
    }//end visitInt

    /** VAR Rule*/
    public Integer visitID(GrammerParser.IDContext ctx)  {
        String id = ctx.ID().getText();
        if (mode == 0){
            if(memory.containsKey(id)){
                return memory.get(id);
            } else {
                throw new IllegalArgumentException("Variable not declared");
            }//end if
        } else {
            if(memory.containsKey(id)){
                thestack.push(memory.get(id));
                st = template.getInstanceOf("LoadVar");
                st.add("stack", Convert(thestack.size()-1));
                st.add("varName", id);
                prog.add(st);
                return 0;
            } else {
                throw new IllegalArgumentException("Variable not declared");
            }//end if
        }//end if
    }//end visitVar
}//end EvalVisitor

