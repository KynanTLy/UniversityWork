import SymbolManager.Scope;
import SymbolManager.TupleArg;
import SymbolManager.TupleField;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.*;
import java.util.regex.Pattern;

public class LLVMVisitor extends GrammarBaseVisitor<Object> {

    Scope scope = new Scope();

    Stack<Integer> breakIndex = new Stack<Integer>();
    Stack<Integer> breakCount = new Stack<Integer>();
    Stack<Integer> ContIndex = new Stack<Integer>();
    Stack<Integer> ContCount = new Stack<Integer>();

    //=====Register Counter======//
    int RegCounter = 1;
    int StrCounter = 1;
    int MethodCounter = 1;

    //======Return Type==========//
    String type = "";

    //======Var need for Parameter handle=======//
    int ParamType = 0;
    String ParamID;
    ArrayList<String> ParamList;
    ArrayList<String> ParamTypeList;
    String specifiersType;
    String currentVisitMedthodName;
    Boolean checkingForType = false;
    Map<String, Integer> copies = new HashMap<String, Integer>();
    Boolean isVectorParam = false;


    ST st;
    STGroup template = new STGroupFile("src/LLVM.stg");
    ArrayList<ST> prog = new ArrayList<ST>();
    ArrayList<ST> stringLit = new ArrayList<ST>();
    ArrayList<ST> declareStructs = new ArrayList<ST>();

    ArrayList<ST> declareGlobals = new ArrayList<>();
    ArrayList<ST> initGlobals = new ArrayList<ST>();


    String nullOrIdenity = "null";


    private class VisitorState {
        private int regCounter;
        private int progPosition;

        private VisitorState(int r, int p) {
            regCounter = r;
            progPosition = p;
        }

        public int getRegCounter() {
            return regCounter;
        }

        public int getProgPosition() {
            return progPosition;
        }
    }

    public VisitorState GET_VISITOR_STATE() {
        return new VisitorState(RegCounter, prog.size());
    }

    public void REWIND_TO_STATE(VisitorState state) {
        int beforeReg = state.getRegCounter();
        int beforePos = state.getProgPosition();

        int after = prog.size();
        RegCounter = beforeReg;
        for (int i = after - 1; i >= beforePos; i--) {
            prog.remove(i);
        }
    }

    //==========STATEMENT OVERRIDE===============//
    public String visitProg(GrammarParser.ProgContext ctx) {
        //scope = new Scope(scope);
        visitChildren(ctx);

        st = template.getInstanceOf("StartProgOverhead");
        System.out.println(st.render());


        for (int i = 0; i <= declareStructs.size() - 1; ++i) {
            System.out.println(declareStructs.get(i).render());
        }

        System.out.println("");
        st = template.getInstanceOf("main");
        System.out.println(st.render());

        for (int i = 0; i <= stringLit.size() - 1; ++i) {
            System.out.println(stringLit.get(i).render());
        }//end for loop prog

        System.out.println("");

        //Init Globals
        for (int i = 0; i <= declareGlobals.size() - 1; ++i) {
            System.out.println(declareGlobals.get(i).render());
        }
        System.out.println("");
        st = template.getInstanceOf("InitGlobalStart");
        System.out.println(st.render());
        for (int i = 0; i <= initGlobals.size() - 1; ++i) {
            String str = initGlobals.get(i).render().replace("%g0", "@g0"); //Very Dangerous Globals Hack
            System.out.println(str);
        }
        st = template.getInstanceOf("InitGlobalEnd");
        System.out.println(st.render());
        //End Init Globals

        System.out.println("");

        for (int i = 0; i <= prog.size() - 1; ++i) {
            String str = prog.get(i).render().replace("%g0", "@g0"); //Very Dangerous Globals Hack
            System.out.println(str);
        }//end for loop prog

        System.out.println("");

        st = template.getInstanceOf("EndProgOverhead");
        System.out.println(st.render());

        return null;
    }//END VISITPROG

    public String visitPRODDUNFORWARDDECLARE(GrammarParser.PRODDUNFORWARDDECLAREContext ctx) {
        ParamList = new ArrayList<String>();
        ParamTypeList = new ArrayList<String>();
        visit(ctx.argumentProList());
        String methodName = ctx.ID().getText();
        if (copies.get(methodName) == null) {
            //Has not been declared yet
            if (ctx.TYPE() == null) {
                //System.out.println(" declare void type");
                scope.addToScope("m_0" + methodName, "void", ParamList, ParamTypeList, true);
            } else {
                //System.out.println("declare not void type");
                scope.addToScope("m_0" + methodName, ctx.TYPE().getText(), ParamList, ParamTypeList, true);
            }
            copies.put(methodName, 0);
        } else {
            //There exist a method with the same name
            copies.put(methodName, copies.get(methodName) + 1);
            if (ctx.TYPE() == null) {
                scope.addToScope("m_" + copies.get(methodName) + methodName, "void", ParamList, ParamTypeList, true);
            } else {
                scope.addToScope("m_" + copies.get(methodName) + methodName, ctx.TYPE().getText(), ParamList, ParamTypeList, true);
            }
        }
        return null;
    }


    public String visitProfunOneLine(GrammarParser.ProfunOneLineContext ctx) {
        String type = (String) visit(ctx.mathexpr());
        st = template.getInstanceOf("ReturnState");
        st.add("val", RegCounter - 1);
        st.add("type", Helper.convertType(type));
        prog.add(st);
        RegCounter++;
        return null;
    }

    //HELPER
    public boolean checkAllcopyForwardDeclare(String name, ArrayList<String> paramList) {
        int iteration = copies.get(name);
        //Check every copy that exist and see if it has been forward declared and it matches my paramlist
        for (int i = 0; i <= iteration; i++) {
            if (scope.beenForwardDeclare("m_" + i + name) && scope.isMatch("m_" + i + name, paramList)) {
                return true;
            }
        }
        return false;
    }


    //===========PROCEDURES OVERRIDE=============//
    public String visitPROFUNDECLARE(GrammarParser.PROFUNDECLAREContext ctx) {

        type = "";
        RegCounter = 1;
        int temp = 0;

        st = template.getInstanceOf("MethodDeclare");

        ParamList = new ArrayList<String>();
        ParamTypeList = new ArrayList<String>();
        visit(ctx.argumentProList());

        if (ctx.TYPE() == null) {
            st.add("type", "void");
            type = "void";
        } else {
            type = ctx.TYPE().getText();
            st.add("type", Helper.convertType(type));
        }

        if (scope.resolveName("m_0" + ctx.ID().getText()) == null) {
            //First time seeing this method
            //System.out.println("A" + ParamList);
            //System.out.println("B" + ParamTypeList);
            scope.addToScope("m_0" + ctx.ID().getText(), type, ParamList, ParamTypeList, false);
            copies.put(ctx.ID().getText(), 0);
        } else {
            //Check if there exist a forward declaration of it
            if (!checkAllcopyForwardDeclare(ctx.ID().getText(), ParamTypeList)) {
                //It must be a method overload since there is not forward declaration
                temp = copies.get(ctx.ID().getText()) + 1;
                copies.put(ctx.ID().getText(), temp);
                scope.addToScope("m_" + (temp) + ctx.ID().getText(), type, ParamList, ParamTypeList, false);
            } else {
                for (int i = 0; i <= copies.get(ctx.ID().getText()); i++) {
                    if (scope.beenForwardDeclare("m_" + i + ctx.ID().getText()) && scope.isMatch("m_" + i + ctx.ID().getText(), ParamTypeList)) {
                        temp = i;
                        break;
                    }
                }
            }
        }

        st.add("parameter", visit(ctx.argumentProList()));
        st.add("Mname", "m_" + temp + ctx.ID().getText());

        prog.add(st);
        visitFunctionblock(ctx.functionblock(), ctx.ID().getText().equals("main"));

        return null;
    }//end VISITPRODELCARE


    public String visitEMPARGLPRO(GrammarParser.EMPARGLPROContext ctx) {
        return null;
    }//end VISITEMPARGLPRO

    public String visitARGLPRO(GrammarParser.ARGLPROContext ctx) {
        return (String) visit(ctx.argumentPro());

    }//end VISITARGLPRO


    //Parse argument into proper format
    public String visitARGUMENTPRO(GrammarParser.ARGUMENTPROContext ctx) {
        scope = new Scope(scope);
        String parameter = "";
        parameter = parameter.concat((String) visit(ctx.arguementElement(0)));
        for (int i = 1; i < ctx.arguementElement().size(); ++i) {
            parameter = parameter.concat(", " + (String) visit(ctx.arguementElement(i)));
        }
        return parameter;
    }

    public String visitARGMUMENTELEMENT(GrammarParser.ARGMUMENTELEMENTContext ctx) {
        String parameter = "";
        String specifier;
        boolean vector = false;
        if (ctx.VECTOR() != null) {
            parameter = parameter.concat(Helper.convertType(ctx.TYPE().getText()) + "** ");
        } else {
            parameter = parameter.concat(Helper.convertType(ctx.TYPE().getText()) + "* ");
        }
        if (ctx.SPECIFIERS() == null) {
            specifier = "const";
        } else {
            specifier = ctx.SPECIFIERS().getText();
        }
        if (ctx.VECTOR() != null) {
            vector = true;
        }
        parameterDeclareHelper(ctx.TYPE().getText(), ctx.ID().getText(), specifier, vector);
        parameter = parameter.concat("%" + getLLVMVarName(scope, ctx.ID().getText()) + " ");

        return parameter;

    }

    public void parameterDeclareHelper(String type, String name, String specifiers, Boolean vector) {
        //String scopedName;
        if (vector) {
            switch (Helper.assignType(type)) {
                case 0:
                    scope.addToScope(name, "vector_integer", specifiers);
                    ParamList.add(specifiers);
                    ParamTypeList.add("vector_integer");
                    break;
                case 1:
                    scope.addToScope(name, "vector_real", specifiers);
                    ParamList.add(specifiers);
                    ParamTypeList.add("vector_real");
                    break;
                case 2:
                    scope.addToScope(name, "vector_boolean", specifiers);
                    ParamList.add(specifiers);
                    ParamTypeList.add("vector_boolean");
                    break;

                default:
                    throw new Error("Undefined type");
            }
        } else {
            switch (Helper.assignType(type)) {
                case 0:
                    scope.addToScope(name, "integer", specifiers);
                    ParamList.add(specifiers);
                    ParamTypeList.add("integer");
                    break;
                case 1:
                    scope.addToScope(name, "real", specifiers);
                    ParamList.add(specifiers);
                    ParamTypeList.add("real");
                    break;
                case 2:
                    scope.addToScope(name, "boolean", specifiers);
                    ParamList.add(specifiers);
                    ParamTypeList.add("boolean");
                    break;
                case 3:
                    scope.addToScope(name, "character", specifiers);
                    ParamList.add(specifiers);
                    ParamTypeList.add("character");
                    break;
                default:
                    throw new Error("Undefined type");
            }
        }
    }


    //visit a block which needs a new scope
    public String visitBLOCK(GrammarParser.BLOCKContext ctx) {
        scope = new Scope(scope);
        visitChildren(ctx);
        scope = scope.getParent();
        return null;
    }

    //Prints the {} for functions
    public String visitFunctionblock(GrammarParser.FunctionblockContext ctx, boolean isMain) {
        scope = new Scope(scope);
        st = template.getInstanceOf("BlockStart");
        prog.add(st);


        visitChildren(ctx);
        if (type.equals("void")) {
            st = template.getInstanceOf("ReturnVoid");
            prog.add(st);
        }
        st = template.getInstanceOf("Unreachable");
        prog.add(st);
        st = template.getInstanceOf("BlockEnd");
        prog.add(st);
        return null;
    }

    //Return statements
    public String visitRETURNSTATEMENT(GrammarParser.RETURNSTATEMENTContext ctx) {

        String type = (String) visit(ctx.mathexpr());
        st = template.getInstanceOf("ReturnState");
        st.add("val", RegCounter - 1);
        st.add("type", Helper.convertType(type));
        prog.add(st);
        RegCounter++;
        return null;
    }

    public String visitParameterArg(GrammarParser.ParameterArgContext ctx) {
        String type;
        if (ctx.mathexpr() == null) {
            if (checkingForType) {
                return (String) visit(ctx.paraID());
            }
            if (specifiersType.equals("var")) {
                ParamType = 0;
                ParamID = ctx.paraID().getText();
                type = scope.getLLVMType(ParamID);
                return type;
            } else {
                ParamType = 1;
                type = scope.getLLVMType(ctx.paraID().getText());
                visit(ctx.paraID());
                return type;
            }
        } else {
            if (checkingForType) {
                return stripVectorExtra((String) visit(ctx.mathexpr()));
            }
            ParamType = 1;
            type = ((String) visit(ctx.mathexpr()));
            return stripVectorExtra(type);
        }
    }

    public String stripVectorExtra(String word) {
        String vectorType = null;
        if (word.contains("_")) {
            vectorType = word.split("_")[1];
        }

        word = word.split("_")[0];
        if (type.equals("vector")) {
            return "vector_" + vectorType;
        } else {
            return word;
        }
    }

    public String visitParaID(GrammarParser.ParaIDContext ctx){
        String userDefinedName = ctx.ID().getText();
        String type = scope.getLLVMType(userDefinedName);
        String scopedName = getLLVMVarName(scope, userDefinedName);
        prog.add(Assign_ST_Helper.loadVar(type, RegCounter,scopedName));
        RegCounter++;
        return type;
    }

    //HEALPER
    public Integer SelectCorrectMethod(String name, ArrayList<String> paramList){
        //System.out.println("What is the name " + name );
        //System.out.println("What is the method param " + paramList);
        for(int i = 0; i <= copies.get(name); i++){
            if(scope.isMatch("m_" + i + name, paramList)){
                return i;
            }
        }
        return -1;
    }

    public String visitPROCALL(GrammarParser.PROCALLContext ctx){
        currentVisitMedthodName = "m_0" + ctx.ID().getText();
        if(ctx.parameterArg(0) == null){
            st = template.getInstanceOf("ProCall");
            st.add("name", "m_0" + ctx.ID().getText());
            st.add("type", Helper.convertType(scope.getLLVMType(currentVisitMedthodName)));
            st.add("parameter", "");
            prog.add(st);
        } else {
            isVectorParam = false;
            //Find out which copy we are dealing with
            ArrayList<String> methodParamType = new ArrayList<String>();
            checkingForType = true;
            for(int k = 0; k < ctx.parameterArg().size(); k++){
                methodParamType.add((String) visit(ctx.parameterArg(k)));
            }
            checkingForType = false;

            int methodNum = SelectCorrectMethod(ctx.ID().getText(), methodParamType);
             //System.out.println("What is the method number " + methodNum);
            currentVisitMedthodName = "m_" + methodNum + ctx.ID().getText();
             //System.out.println("What is the method name " + currentVisitMedthodName);
            specifiersType = scope.getParameterSpecifiers(currentVisitMedthodName, 0);
            String para = "";

            String type = (String) visit(ctx.parameterArg(0));
            String methodCallType = scope.getParameterType(currentVisitMedthodName, 0);
            if(ParamType == 1) {

                convertToPointer(type, methodCallType);
                if(isVectorParam){
                    para = para.concat(Helper.convertType(methodCallType) + "* %" + (RegCounter - 2) + " ");
                } else {
                    para = para.concat(Helper.convertType(methodCallType) + "* %" + (RegCounter - 1) + " ");
                }
            } else {
                String scopedName = getLLVMVarName(scope, ParamID);
                para = para.concat(Helper.convertType(methodCallType) + "* %" + scopedName + " ");
            }
            for(int i = 1; i < ctx.parameterArg().size(); ++i){
                isVectorParam =false;
                specifiersType = scope.getParameterSpecifiers(currentVisitMedthodName, i);
                methodCallType = scope.getParameterType(currentVisitMedthodName, i);
                type = (String) visit(ctx.parameterArg(i));
                if(ParamType == 1) {

                    convertToPointer(type, methodCallType);
                    if(isVectorParam){
                        para = para.concat("," + Helper.convertType(methodCallType) + "* %" + (RegCounter - 2) + " ");
                    } else {
                        para = para.concat("," + Helper.convertType(methodCallType) + "* %" + (RegCounter - 1) + " ");
                    }
                } else {
                    String scopedName = getLLVMVarName(scope, ParamID);
                    para = para.concat("," + Helper.convertType(methodCallType) + "* %" + scopedName + " ");
                }
            }
            st = template.getInstanceOf("ProCall");
            st.add("name", currentVisitMedthodName);
            st.add("type", Helper.convertType(scope.getLLVMType(currentVisitMedthodName)));
            st.add("parameter", para);
            prog.add(st);
        }

        return scope.getLLVMType(currentVisitMedthodName);


    }

    public String visitMATHFUNCTIONR(GrammarParser.MATHFUNCTIONRContext ctx){
        currentVisitMedthodName = "m_0" + ctx.ID().getText();
        if(ctx.parameterArg(0) == null){
            st = template.getInstanceOf("MethodCall");
            st.add("name", currentVisitMedthodName);
            st.add("type", Helper.convertType(scope.getLLVMType(currentVisitMedthodName)));
            st.add("regC", RegCounter);
            st.add("parameter", "");
            prog.add(st);
            RegCounter++;
        } else {
            isVectorParam =false;
            //Find out which copy we are dealing with
            ArrayList<String> methodParamType = new ArrayList<String>();
            checkingForType = true;
            for(int k = 0; k < ctx.parameterArg().size(); k++){
                methodParamType.add((String) visit(ctx.parameterArg(k)));
            }
            checkingForType = false;

            //System.out.println("What is the method number " + methodParamType);
            int methodNum = SelectCorrectMethod(ctx.ID().getText(), methodParamType);
            //System.out.println("What is the method number " + methodNum);
            currentVisitMedthodName = "m_" + methodNum + ctx.ID().getText();
            //System.out.println("What is the method name " + currentVisitMedthodName);
            specifiersType = scope.getParameterSpecifiers(currentVisitMedthodName, 0);
            String para = "";

            String type = (String) visit(ctx.parameterArg(0));
            String methodCallType = scope.getParameterType(currentVisitMedthodName, 0);
            if(ParamType == 1) {

                convertToPointer(type, methodCallType);
                if(isVectorParam){
                    para = para.concat(Helper.convertType(methodCallType) + "* %" + (RegCounter - 2) + " ");
                } else {
                    para = para.concat(Helper.convertType(methodCallType) + "* %" + (RegCounter - 1) + " ");
                }
            } else {
                String scopedName = getLLVMVarName(scope, ParamID);
                para = para.concat(Helper.convertType(methodCallType) + "* %" + scopedName + " ");
            }
            for(int i = 1; i < ctx.parameterArg().size(); ++i){
                specifiersType = scope.getParameterSpecifiers(currentVisitMedthodName, i);
                methodCallType = scope.getParameterType(currentVisitMedthodName, i);
                isVectorParam =false;
                type = (String) visit(ctx.parameterArg(i));
                if(ParamType == 1) {
                    convertToPointer(type, methodCallType);
                    if(isVectorParam){
                        para = para.concat("," + Helper.convertType(methodCallType) + "* %" + (RegCounter - 2) + " ");
                    } else {
                        para = para.concat("," + Helper.convertType(methodCallType) + "* %" + (RegCounter - 1) + " ");
                    }
                } else {
                    String scopedName = getLLVMVarName(scope, ParamID);
                    para = para.concat("," + Helper.convertType(methodCallType) + "* %" + scopedName + " ");
                }
            }
            st = template.getInstanceOf("MethodCall");
            st.add("name", currentVisitMedthodName);
            st.add("type", Helper.convertType(scope.getLLVMType(currentVisitMedthodName)));
            st.add("regC", RegCounter);
            st.add("parameter", para);
            prog.add(st);
            RegCounter++;
        }

        return scope.getLLVMType(currentVisitMedthodName);
    }

    public void convertToPointer(String type, String paramTYPE){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        //String scopedName;
        switch (type){
            case "integer":
                if(paramTYPE.equals("real")){
                    prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter-1, "integer", "real"));
                    RegCounter++;
                    prog.add( Assign_ST_Helper.declareREAL(String.valueOf(RegCounter)));
                    prog.add(Assign_ST_Helper.assignREAL(String.valueOf(RegCounter), RegCounter-1));
                    RegCounter++;
                    return;
                }
                prog.add(Assign_ST_Helper.declareINT(String.valueOf(RegCounter)));
                prog.add (Assign_ST_Helper.assignINT(String.valueOf(RegCounter), RegCounter-1));
                RegCounter++;
                return;
            case "real":
                prog.add( Assign_ST_Helper.declareREAL(String.valueOf(RegCounter)));
                prog.add(Assign_ST_Helper.assignREAL(String.valueOf(RegCounter), RegCounter-1));
                RegCounter++;
                return;
            case "boolean":
                prog.add( Assign_ST_Helper.declareBOOL(String.valueOf(RegCounter)));
                prog.add(Assign_ST_Helper.assignBOOL(String.valueOf(RegCounter), RegCounter-1));
                RegCounter++;
                return;
            case "character":
                prog.add( Assign_ST_Helper.declareBOOL(String.valueOf(RegCounter)));
                prog.add(Assign_ST_Helper.assignBOOL(String.valueOf(RegCounter), RegCounter-1));
                RegCounter++;
                return;
            case "vector":
                isVectorParam = true;
                switch(vectorType) {
                    case "integer":
                        st = template.getInstanceOf("DeclareIntegerVector");
                        st.add("name", RegCounter++);
                        prog.add(st);
                        st = template.getInstanceOf("AllocateIntegerVector");
                        st.add("regC", RegCounter++);
                        st.add("size", "0");
                        prog.add(st);
                        st = template.getInstanceOf("StoreIntegerVector");
                        st.add("oldReg", RegCounter - 1);
                        st.add("regC", RegCounter - 2);
                        prog.add(st);
                        return;
                    case "null":  //If empty, not if null
                        st = template.getInstanceOf("DeclareIntegerVector");
                        st.add("name", RegCounter++);
                        prog.add(st);
                        st = template.getInstanceOf("AllocateIntegerVector");
                        st.add("regC", RegCounter++);
                        st.add("size", "0");
                        prog.add(st);
                        st = template.getInstanceOf("StoreIntegerVector");
                        st.add("oldReg", RegCounter - 1);
                        st.add("regC", RegCounter - 2);
                        prog.add(st);
                        return;
                    case "real":
                        int regd = RegCounter;
                        st = template.getInstanceOf("DeclareRealVector");
                        st.add("name", RegCounter++);
                        prog.add(st);
                        st = template.getInstanceOf("AllocateRealVector");
                        st.add("regC", RegCounter++);
                        st.add("size", "0");
                        prog.add(st);
                        st = template.getInstanceOf("StoreRealVector");
                        st.add("oldReg", RegCounter-1);
                        st.add("regC", RegCounter - 2);
                        prog.add(st);

                        return;
                    case "character":
                        st = template.getInstanceOf("DeclareCharacterVector");
                        st.add("name", RegCounter++);
                        prog.add(st);
                        st = template.getInstanceOf("AllocateCharacterVector");
                        st.add("regC", RegCounter++);
                        st.add("size", "0");
                        prog.add(st);
                        st = template.getInstanceOf("StoreCharacterVector");
                        st.add("oldReg", RegCounter-1);
                        st.add("regC", RegCounter - 2);
                        prog.add(st);
                        return;
                    case "boolean":
                        st = template.getInstanceOf("DeclareBooleanVector");
                        st.add("name", RegCounter++);
                        prog.add(st);
                        st = template.getInstanceOf("AllocateBooleanVector");
                        st.add("regC", RegCounter++);
                        st.add("size", "0");
                        prog.add(st);
                        st = template.getInstanceOf("StoreBooleanVector");
                        st.add("oldReg", RegCounter-1);
                        st.add("regC", RegCounter - 2);
                        prog.add(st);
                        return;
                    default:
                        throw new Error("Undefined type");
                }
            default:
                throw new Error("Undefined type");
        }

    }


    //===========END PROCEDURES OVERRIDE=============//

    //===========IF AND LOOPS=========================//
    /**If Statement*/
    public String visitIF(GrammarParser.IFContext ctx){

        //TODO this worrys me

        String type = (String) visit(ctx.mathexpr());

        if(!type.equals("booleanC")) {
            prog.add(MathHelper.convertBoolToLLWM(RegCounter, RegCounter - 1));
            RegCounter++;
        }
        int regCmp = RegCounter -1;
        int placement = prog.size();
        int regC = RegCounter;
        st = template.getInstanceOf("IFHOLDER");
        prog.add(st);

        scope = new Scope(scope);
        RegCounter++;

        visit(ctx.ifloopblock());
        scope = scope.getParent();

        st = template.getInstanceOf("IF");
        st.add("regCmp", regCmp);
        st.add("regC", regC);
        st.add("regE", RegCounter);
        prog.set(placement, st);

        st = template.getInstanceOf("ENDIF");
        st.add("regE", RegCounter);
        prog.add(st);
        RegCounter++;

        return null;
    }

    //if else
    public String visitIFELSE(GrammarParser.IFELSEContext ctx){

        String type = (String) visit(ctx.mathexpr());

        if(!type.equals("booleanC")) {
            prog.add(MathHelper.convertBoolToLLWM(RegCounter, RegCounter - 1));
            RegCounter++;
        }

        int regCmp = RegCounter -1;
        int placement1 = prog.size();
        int regIf = RegCounter;

        st = template.getInstanceOf("IFHOLDER");
        prog.add(st);
        RegCounter++;

        scope = new Scope(scope);
        visit(ctx.ifloopblock(0));
        scope = scope.getParent();

        int placement2 = prog.size();
        int regElse = RegCounter;
        st = template.getInstanceOf("IFHOLDER");
        prog.add(st);
        RegCounter++;



        scope = new Scope(scope);
        visit(ctx.ifloopblock(1));
        scope = scope.getParent();


        st = template.getInstanceOf("IFELSE");
        st.add("regCmp", regCmp);
        st.add("regIf", regIf);
        st.add("regElse", regElse);
        prog.set(placement1, st);

        st = template.getInstanceOf("ENDIFELSE");
        st.add("regEnd", RegCounter);
        prog.set(placement2, st);

        st = template.getInstanceOf("ENDELSE");
        st.add("regC", RegCounter);
        prog.add(st);
        RegCounter++;



        return null;
    }

    //do while loop
    public String visitBACKLOOP(GrammarParser.BACKLOOPContext ctx){
        scope = new Scope(scope);
        ContCount.push(0);
        breakCount.push(0);

        int regLS = RegCounter;
        int placement1 = prog.size();
        st = template.getInstanceOf("LOOPHOLDER");
        RegCounter++;
        prog.add(st);

        visit(ctx.ifloopblock());


        st = template.getInstanceOf("LOOP");
        st.add("regC", regLS);
        prog.set(placement1, st);

        int numCont = ContCount.peek();
        ContCount.pop();
        for(int i = 0; i < numCont; ++i ){
            st = template.getInstanceOf("BREAKCONT");
            st.add("reg", RegCounter);
            prog.set(ContIndex.peek(), st );
            ContIndex.pop();
        }

        st = template.getInstanceOf("ENDBACKLOOP");
        st.add("regC", RegCounter);
        prog.add(st);
        RegCounter++;

        scope = scope.getParent();

        String type = (String) visit(ctx.mathexpr());
        if(!type.equals("booleanC")) {
            prog.add(MathHelper.convertBoolToLLWM(RegCounter, RegCounter - 1));
            RegCounter++;
        }

        int numbreak = breakCount.peek();
        breakCount.pop();
        for(int i = 0; i < numbreak; ++i ){
            ST st = template.getInstanceOf("BREAKCONT");
            st.add("reg",RegCounter);
            prog.set(breakIndex.peek(), st);
            breakIndex.pop();
        }

        st = template.getInstanceOf("CONDBACKLOOP");
        st.add("regP", RegCounter-1);
        st.add("regLS", regLS);
        st.add("regLE", RegCounter);
        prog.add(st);

        RegCounter++;

        return "null";
    }

    //Loops that run forever
    public String visitINDEFLOOP(GrammarParser.INDEFLOOPContext ctx){

        scope = new Scope(scope);
        ContCount.push(0);
        breakCount.push(0);

        int regLS = RegCounter;
        st = template.getInstanceOf("LOOP");
        st.add("regC", RegCounter);
        prog.add(st);
        RegCounter++;

        visit(ctx.ifloopblock());

        st = template.getInstanceOf("ENDLOOP");
        st.add("regLS", regLS);
        prog.add(st);


        int numCont = ContCount.peek();
        ContCount.pop();
        for(int i = 0; i < numCont; ++i ){
            st = template.getInstanceOf("BREAKCONT");
            st.add("reg", regLS);
            prog.set(ContIndex.peek(), st );
            ContIndex.pop();
        }

        int numbreak = breakCount.peek();
        breakCount.pop();
        for(int i = 0; i < numbreak; ++i ){
            ST st = template.getInstanceOf("BREAKCONT");
            st.add("reg",RegCounter);
            prog.set(breakIndex.peek(), st);
            breakIndex.pop();
        }
        RegCounter++;

        return "null";
    }

    //normal while loop
    public String visitFRONTLOOP(GrammarParser.FRONTLOOPContext ctx){

        scope = new Scope(scope);
        ContCount.push(0);
        breakCount.push(0);

        int regLS = RegCounter;
        int placement1 = prog.size();
        st = template.getInstanceOf("LOOPHOLDER");
        RegCounter++;
        prog.add(st);

        String type = (String) visit(ctx.mathexpr());
        if(!type.equals("booleanC")) {
            prog.add(MathHelper.convertBoolToLLWM(RegCounter, RegCounter - 1));
            RegCounter++;
        }
        //prog.add(MathHelper.convertBoolToLLWM(RegCounter, RegCounter-1)) ;
        int regP = RegCounter-1;
        //RegCounter++;

        int placement2 = prog.size();
        int regC =RegCounter;
        RegCounter++;
        st = template.getInstanceOf("LOOPHOLDER");
        prog.add(st);

        visit(ctx.ifloopblock());


        st = template.getInstanceOf("LOOP");
        st.add("regC", regLS);
        prog.set(placement1, st);


        int numCont = ContCount.peek();
        ContCount.pop();
        for(int i = 0; i < numCont; ++i ){
            st = template.getInstanceOf("BREAKCONT");
            st.add("reg", regLS);
            prog.set(ContIndex.peek(), st );
            ContIndex.pop();
        }


        st = template.getInstanceOf("CONDLOOP");
        st.add("regP", regP);
        st.add("regC", regC);
        st.add("regLE", RegCounter);
        prog.set(placement2, st);


        int numbreak = breakCount.peek();
        breakCount.pop();
        for(int i = 0; i < numbreak; ++i ){
            ST st = template.getInstanceOf("BREAKCONT");
            st.add("reg",RegCounter);
            prog.set(breakIndex.peek(), st);
            breakIndex.pop();
        }

        st = template.getInstanceOf("ENDLOOP");
        st.add("regLS", regLS);
        prog.add(st);
        RegCounter++;

        scope = scope.getParent();
        return null;
    }

    public String visitBREAKCONTINUE(GrammarParser.BREAKCONTINUEContext ctx){
        if(ctx.cond.getText().equals("break")){
            int temp = breakCount.peek();
            breakCount.pop();
            breakCount.push(temp+1);
            breakIndex.push(prog.size());
        } else {
            int temp = ContCount.peek();
            ContCount.pop();
            ContCount.push(temp+1);
            ContIndex.push(prog.size());
        }
        st = template.getInstanceOf("HOLDER");
        prog.add(st);
        RegCounter++;
        return null;
    }

    //===========END IF AND LOOPS=========================//

    //=============ASSIGNMENT OVERRIDE===========//
    public String visitBASICASSIGN(GrammarParser.BASICASSIGNContext ctx){

        if(scope.scopeNumber == 0) {
            return visitGlobalBASICASSIGN(ctx);
        }

        String type = ctx.TYPE().getText();
        String userDefinedName = ctx.ID().getText();

        return assign(userDefinedName, type, ctx.mathexpr());

    }//end VISITBASICASSIGN

    public String assign(String userDefinedName, String type, GrammarParser.MathexprContext ctx) {
        String scopedName;
        String exprType;
        String promote;
        if(ctx != null) {
            exprType = checkifBoolC((String) visit(ctx));
            switch (Helper.assignType(type)) {
                case 0:
                    scope.addToScope(userDefinedName, "integer");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareINT(scopedName));
                    prog.add(Assign_ST_Helper.assignINT(scopedName, RegCounter - 1));

                    return "integer";
                case 1:
                    if (exprType.equals("null")) {
                        promote = MathHelper.typeTier(type, exprType);
                        if (promote.equals("right")) {
                            prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter - 1, exprType, type));
                            RegCounter++;
                        }
                    } else if (exprType.equals("integer")) {
                        prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter - 1, "integer", "real"));
                        RegCounter++;
                    }
                    scope.addToScope(userDefinedName, "real");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareREAL(scopedName));
                    prog.add(Assign_ST_Helper.assignREAL(scopedName, RegCounter - 1));
                    return "real";
                case 2:
                    if (exprType.equals("null")) {
                        promote = MathHelper.typeTier(type, exprType);
                        if (promote.equals("right")) {
                            prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter - 1, exprType, type));
                            RegCounter++;
                        }
                    }
                    scope.addToScope(userDefinedName, "boolean");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareBOOL(scopedName));
                    prog.add(Assign_ST_Helper.assignBOOL(scopedName, RegCounter - 1));
                    return "boolean";
                case 3:
                    if (exprType.equals("null")) {
                        promote = MathHelper.typeTier(type, exprType);
                        if (promote.equals("right")) {
                            prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter - 1, exprType, type));
                            RegCounter++;
                        }
                    }
                    scope.addToScope(userDefinedName, "character");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareBOOL(scopedName));
                    prog.add(Assign_ST_Helper.assignBOOL(scopedName, RegCounter - 1));

                    return "character";
                default:
                    throw new Error("Undefined type");
            }
        } else {
            switch (Helper.assignType(type)) {
                case 0:
                    scope.addToScope(userDefinedName, "integer");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareINT(scopedName));
                    return "integer";
                case 1:
                    scope.addToScope(userDefinedName, "real");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareREAL(scopedName));
                    return "real";
                case 2:
                    scope.addToScope(userDefinedName, "boolean");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareBOOL(scopedName));
                    return "boolean";
                case 3:
                    scope.addToScope(userDefinedName, "character");
                    scopedName = getLLVMVarName(scope, userDefinedName);
                    prog.add(Assign_ST_Helper.declareBOOL(scopedName));
                    return "character";
                default:
                    throw new Error("Undefined type");
            }
        }
    }

    public String visitGlobalBASICASSIGN(GrammarParser.BASICASSIGNContext ctx){
        String type = ctx.TYPE().getText();
        String userDefinedName = ctx.ID().getText();

        return globalAssign(userDefinedName, type, ctx.mathexpr());
    }


    // Assignments with only specifiers and no type defined
    @Override
    public String visitVAGUEASSIGN(GrammarParser.VAGUEASSIGNContext ctx) {
        if(scope.scopeNumber == 0) {
            return visitGlobalVAGUEASSIGN(ctx);
        }

        String type = (String) visit(ctx.mathexpr());
        String userDefinedName = ctx.ID().getText();

        return assign(userDefinedName,type,ctx.mathexpr());
    }


    public String visitGlobalVAGUEASSIGN(GrammarParser.VAGUEASSIGNContext ctx){

        VisitorState before = GET_VISITOR_STATE();

        String type = (String) visit(ctx.mathexpr());
        
        REWIND_TO_STATE(before);

        String userDefinedName = ctx.ID().getText();

        return globalAssign(userDefinedName,type,ctx.mathexpr());
    }

    public String globalAssign(String userDefinedName, String type, GrammarParser.MathexprContext mathExpr) {
        String scopedName;

        switch (Helper.assignType(type)){
            case 0:
                scope.addToScope(userDefinedName, "integer");
                scopedName = getLLVMVarName(scope, userDefinedName);
                st = template.getInstanceOf("GlobalDeclareInt");
                st.add("name", scopedName);
                declareGlobals.add(st);
                if(mathExpr != null){
                    int before  = prog.size();
                    visit(mathExpr);
                    int after = prog.size();
                    for(int i = before; i < after; i++) {
                        initGlobals.add(prog.get(i));
                    }
                    for(int i = after-1; i >= before; i--) {
                        prog.remove(i);
                    }
                    initGlobals.add(Assign_ST_Helper.assignINT(scopedName, RegCounter-1));
                }
                return "integer";
            case 1:
                scope.addToScope(userDefinedName, "real");
                scopedName = getLLVMVarName(scope, userDefinedName);
                st = template.getInstanceOf("GlobalDeclareReal");
                st.add("name", scopedName);
                declareGlobals.add(st);
                if(mathExpr != null){
                    int before  = prog.size();
                    visit(mathExpr);
                    int after = prog.size();
                    for(int i = before; i < after; i++) {
                        initGlobals.add(prog.get(i));
                    }
                    for(int i = after-1; i >= before; i--) {
                        prog.remove(i);
                    }
                    initGlobals.add(Assign_ST_Helper.assignREAL(scopedName, RegCounter-1));
                }
                return "real";
            case 2:
                scope.addToScope(userDefinedName, "boolean");
                scopedName = getLLVMVarName(scope, userDefinedName);
                st = template.getInstanceOf("GlobalDeclareBoolean");
                st.add("name", scopedName);
                declareGlobals.add(st);
                if(mathExpr != null){
                    int before  = prog.size();
                    visit(mathExpr);
                    int after = prog.size();
                    for(int i = before; i < after; i++) {
                        initGlobals.add(prog.get(i));
                    }
                    for(int i = after-1; i >= before; i--) {
                        prog.remove(i);
                    }
                    initGlobals.add(Assign_ST_Helper.assignBOOL(scopedName, RegCounter-1));
                }
                return "boolean";
            case 3:
                scope.addToScope(userDefinedName, "character");
                scopedName = getLLVMVarName(scope, userDefinedName);
                st = template.getInstanceOf("GlobalDeclareChar");
                st.add("name", scopedName);
                declareGlobals.add(st);
                if(mathExpr != null){
                    int before  = prog.size();
                    visit(mathExpr);
                    int after = prog.size();
                    for(int i = before; i < after; i++) {
                        initGlobals.add(prog.get(i));
                    }
                    for(int i = after-1; i >= before; i--) {
                        prog.remove(i);
                    }
                    initGlobals.add(Assign_ST_Helper.assignCHAR(scopedName, RegCounter-1));
                }
                return "character";
            default:
                throw new Error("Undefined type");
        }
    }

    // Re-assign a var
    @Override
    public String visitREDEFASSIGN(GrammarParser.REDEFASSIGNContext ctx) {
        String userDefinedName = ctx.ID().getText();
        String scopedName = getLLVMVarName(scope, userDefinedName);
        String type = scope.getLLVMType(userDefinedName);
        // need to store type of math expr here for possible type promotion or whatever
        String exprType = checkifBoolC((String) visit(ctx.mathexpr()));
        String promote;

        String vecType = null;
        //System.out.println(exprType);
        if (exprType.equals("null")){
            promote = MathHelper.typeTier(type, exprType);
            if (promote.equals("right")) {
                prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter-1, exprType, type));
                RegCounter++;
            }
        }

        if (type.split("_")[0].equals("vector")) {
            vecType = type.split("_")[1];
            type = "vector";
        }

        switch(type) {
            case "integer":
                prog.add(Assign_ST_Helper.assignINT(scopedName, RegCounter-1));
                return "integer";
            case "real":
                prog.add(Assign_ST_Helper.assignREAL(scopedName, RegCounter-1));
                return "real";
            case "boolean":
                prog.add(Assign_ST_Helper.assignBOOL(scopedName, RegCounter-1));
                return "boolean";
            case "character":
                prog.add(Assign_ST_Helper.assignCHAR(scopedName, RegCounter-1));
                return "character";
            case "vector":
                switch(vecType) {
                    case "integer":
                        prog.add(Assign_ST_Helper.assignIntVec(scopedName, RegCounter-1));
                        return "vector_integer";
                    case "real":
                        prog.add(Assign_ST_Helper.assignRealVec(scopedName, RegCounter-1));
                        return "vector_real";
                    case "character":
                        prog.add(Assign_ST_Helper.assignCharVec(scopedName, RegCounter-1));
                        return "vector_character";
                    case "boolean":
                        prog.add(Assign_ST_Helper.assignBoolVec(scopedName, RegCounter-1));
                        return "vector_boolean";
                    default:
                        throw new Error("Unknown vector type");
                }
            default:
                throw new Error("Undefined type");
        }
    }

    // Assign stdin
    @Override
    public String visitASSIGNSTDIN(GrammarParser.ASSIGNSTDINContext ctx) {
        String type = ctx.TYPE().getText();
        String userDefinedName = ctx.ID().getText();
        String scopedName;
        switch(Helper.assignType(type)){
            case 0:
                scope.addToScope(userDefinedName, "integer");
                scopedName = getLLVMVarName(scope, userDefinedName);
                prog.add(Assign_ST_Helper.declareINT(scopedName));
                // get user input
                st = template.getInstanceOf("ReadInt");
                st.add("varName", scopedName);
                prog.add(st);
                return "integer";
            case 1:
                scope.addToScope(userDefinedName, "real");
                scopedName = getLLVMVarName(scope, userDefinedName);
                prog.add(Assign_ST_Helper.declareREAL(scopedName));
                // get user input
                st = template.getInstanceOf("ReadFloat");
                st.add("varName", scopedName);
                prog.add(st);
                return "real";
            case 2:
                scope.addToScope(userDefinedName, "boolean");
                scopedName = getLLVMVarName(scope, userDefinedName);
                prog.add(Assign_ST_Helper.declareBOOL(scopedName));
                // get user input
                st = template.getInstanceOf("ReadBool");
                st.add("varName", scopedName);
                prog.add(st);
                return "boolean";
            case 3:
                scope.addToScope(userDefinedName, "character");
                scopedName = getLLVMVarName(scope, userDefinedName);
                prog.add(Assign_ST_Helper.declareCHAR(scopedName));
                // get user input
                st = template.getInstanceOf("ReadChar");
                st.add("varName", scopedName);
                prog.add(st);
                return "character";
            default:
                throw new Error("Undefined type");
        }
    }


    // Assign chars (they're different than basic assign because not math expressions)
    public String visitCHARASSIGN(GrammarParser.CHARASSIGNContext ctx) {
        String type = ctx.TYPE().getText();
        String userDefinedName = ctx.ID().getText();
        String scopedName;

        scope.addToScope(userDefinedName, "character");
        scopedName = getLLVMVarName(scope, userDefinedName);
        prog.add(Assign_ST_Helper.declareCHAR(scopedName));
        if (ctx.CHAR() != null) {
            prog.add(Assign_ST_Helper.loadIntForChar(ctx.CHAR().getText(), RegCounter));
            RegCounter++;
            prog.add(Assign_ST_Helper.assignCHAR(scopedName, RegCounter - 1));
        } else if (ctx.NULLDENT() != null) {
            if (ctx.NULLDENT().getText().equals("null")) {
                visitNULL(null);
            } else {
                visitIDENTITY(null);
            }
            String exprType = "null";
            String promote = MathHelper.typeTier(type, exprType);
            if (promote.equals("right")) {
                prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter-1, exprType, type));
                RegCounter++;
            }
            prog.add(Assign_ST_Helper.assignCHAR(scopedName, RegCounter - 1));
        }
        return "character";
    }

    @Override
    public String visitMATHINTERVAL(GrammarParser.MATHINTERVALContext ctx) {
        visit(ctx.mathexpr(0));
        int here = RegCounter;
        visit(ctx.mathexpr(1));

        st = template.getInstanceOf("DeclareInterval");
        st.add("a",here-1);
        st.add("b", RegCounter-1);
        st.add("name", RegCounter++);
        st.add("t1",RegCounter++);
        st.add("regC",RegCounter++);
        prog.add(st);

        return "interval_integer";
    }


    //===========Vectors==========//

    @Override
    public String visitVECTORASSIGN(GrammarParser.VECTORASSIGNContext ctx) {
        // get type of vector
        // Put in scope some way so that we know both vector and internal type
        String scopedName;
        String userDefinedName = ctx.ID().toString();
        String vectorType = "unknown";
        String size = "unknown";
        if (ctx.TYPE() != null) {
            vectorType = ctx.TYPE().getText();
        }

        String primaryType = "unknown";
        if(ctx.primaryType != null) {
            primaryType = ctx.primaryType.getText();
        }

        boolean isNull = false;
        boolean isIdentity = false;
        //Infertype from rhs
        GrammarParser.MathexprContext rhs = ctx.value;
        if(rhs != null) {
            VisitorState before = GET_VISITOR_STATE();

            String result = (String) visit(rhs); // Pure Visit
            String results[] = result.split("_");

            REWIND_TO_STATE(before);

            String infType;
            if(results.length >= 2) {
                if(primaryType.equals("unknown")) {
                    primaryType = results[0];
                }
                infType = results[1];
                if(results.length == 3 && results[2].equals("0")) {
                    infType = "empty";
                }
            } else {
                infType = results[0];
            }

            if(infType.equals("null")) {
                if(nullOrIdenity.equals("null")) {
                    isNull = true;
                } else {
                    isIdentity = true;
                }
            } else if (vectorType.equals("unknown")) {
                vectorType = infType;
            }

            //Sanity Check
            if (!vectorType.equals(infType) && !infType.equals("empty") && !isNull && !isIdentity) {
                throw new Error("Vector Infered Type != Type");
            }
        }

        // Now that we know the type, add to scope
        scope.addToScope(userDefinedName, primaryType + "_" + vectorType);
        scopedName = getLLVMVarName(scope, userDefinedName);

        switch(vectorType) {
            case "integer":
                st = template.getInstanceOf("DeclareIntegerVector");
                st.add("name", scopedName);
                prog.add(st);
                break;
            case "real":
                st = template.getInstanceOf("DeclareRealVector");
                st.add("name", scopedName);
                prog.add(st);
                break;
            case "null":
                st = template.getInstanceOf("DeclareIntegerVector");
                st.add("name", scopedName);
                prog.add(st);
                break;
            case "character":
                st = template.getInstanceOf("DeclareCharacterVector");
                st.add("name", scopedName);
                prog.add(st);
                break;
            case "boolean":
                st = template.getInstanceOf("DeclareBooleanVector");
                st.add("name", scopedName);
                prog.add(st);
                break;
            default:
                throw new Error("No type");
        }


        String identity = "";
        if(isIdentity) {
            identity = "Identity";
        }

        if(ctx.size != null) {


            visit(ctx.size);



            int sizeReg = RegCounter-1;
            switch(vectorType) {
                case "integer":
                    st = template.getInstanceOf("DeclareIntegerVector");
                    st.add("name", RegCounter++);
                    prog.add(st);
                    st = template.getInstanceOf("AllocateIntegerVector"+identity);
                    st.add("regC", RegCounter++);
                    st.add("size", "%" + sizeReg);
                    prog.add(st);
                    st = template.getInstanceOf("StoreIntegerVector");
                    st.add("oldReg", RegCounter-1);
                    st.add("regC", scopedName);
                    prog.add(st);
                    break;
                case "real":
                    st = template.getInstanceOf("DeclareRealVector");
                    st.add("name", RegCounter++);
                    prog.add(st);
                    st = template.getInstanceOf("AllocateRealVector"+identity);
                    st.add("regC", RegCounter++);
                    st.add("size", "%" + sizeReg);
                    prog.add(st);
                    st = template.getInstanceOf("StoreRealVector");
                    st.add("oldReg", RegCounter-1);
                    st.add("regC", scopedName);
                    prog.add(st);
                    break;
                case "character":
                    st = template.getInstanceOf("DeclareCharacterVector");
                    st.add("name", RegCounter++);
                    prog.add(st);
                    st = template.getInstanceOf("AllocateCharacterVector"+identity);
                    st.add("regC", RegCounter++);
                    st.add("size", "%" + sizeReg);
                    prog.add(st);
                    st = template.getInstanceOf("StoreCharacterVector");
                    st.add("oldReg", RegCounter-1);
                    st.add("regC", scopedName);
                    prog.add(st);
                    break;
                case "boolean":
                    st = template.getInstanceOf("DeclareBooleanVector");
                    st.add("name", RegCounter++);
                    prog.add(st);
                    st = template.getInstanceOf("AllocateBooleanVector"+identity);
                    st.add("regC", RegCounter++);
                    st.add("size", "%" + sizeReg);
                    prog.add(st);
                    st = template.getInstanceOf("StoreBooleanVector");
                    st.add("oldReg", RegCounter-1);
                    st.add("regC", scopedName);
                    prog.add(st);
                    break;
                default:
                    throw new Error("No type");
            }
        }


        if(ctx.value != null && !isNull) {
            if(!isIdentity) {
                visit(ctx.value);
            }
            // Move the resulting vector into our user-defined variable
            switch(vectorType) {
                case "integer":
                    st = template.getInstanceOf("StoreIntegerVector");
                    break;
                case "real":
                    st = template.getInstanceOf("StoreRealVector");
                    break;
                case "null":
                    st = template.getInstanceOf("StoreIntegerVector");
                    break;
                case "character":
                    st = template.getInstanceOf("StoreCharacterVector");
                    break;
                case "boolean":
                    st = template.getInstanceOf("StoreBooleanVector");
                    break;
                default:
                    throw new Error("No type");
            }

            st.add("oldReg", RegCounter-1);
            st.add("regC", scopedName);
            prog.add(st);
        }

        return (primaryType + "_" +  vectorType);
    }

    @Override
    public String visitVECTORATOM(GrammarParser.VECTORATOMContext ctx) {
        String returntype = "unknown";
        String lhs = (String) visit(ctx.mathexpr(0));
        String results[] = lhs.split("_");
        String lhstypevector = results[0];
        String lhstype = results[1];

//        if(lhstype.equals("matrix")) {
//            lhstype2 = results[2];
//        }

        int here = RegCounter;

        String rhs = (String) visit(ctx.mathexpr(1));
        String rhstype = rhs.split("_")[0];

        switch (rhstype) {
            case "integer": //Indexing with Integer

                switch (lhstype) {
                    case "integer":
                        st = template.getInstanceOf("IndexIntegerVectorWithInteger");
                        break;
                    case "real":
                        st = template.getInstanceOf("IndexRealVectorWithInteger");
                        break;
                    case "character":
                        st = template.getInstanceOf("IndexCharacterVectorWithInteger");
                        break;
                    case "boolean":
                        st = template.getInstanceOf("IndexBooleanVectorWithInteger");
                        break;
                    default:
                        throw new Error("Undefined Type");
                }

                st.add("oldReg",here-1);
                st.add("pos",RegCounter-1);
                st.add("t1",RegCounter++);
                st.add("regC",RegCounter++);

                prog.add(st);

                returntype = lhstype;
                break;
            case "vector": //Indexing with Vector

                switch (lhstype) {
                    case "integer":
                        st = template.getInstanceOf("IndexIntegerVectorWithVector");
                        //st.add("regV", vectorToIndex);
                        st.add("regV", here-1);
                        st.add("regI", RegCounter-1);
                        //st.add("regI", vectorOfIndexes);
                        st.add("regC", RegCounter);
                        RegCounter++;
                        prog.add(st);
                        break;
                    case "real":
                        st = template.getInstanceOf("IndexRealVectorWithVector");
                        //st.add("regV", vectorToIndex);
                        st.add("regV", here-1);
                        st.add("regI", RegCounter-1);
                        //st.add("regI", vectorOfIndexes);
                        st.add("regC", RegCounter);
                        RegCounter++;
                        prog.add(st);
                        break;
                    case "character":
                        st = template.getInstanceOf("IndexCharacterVectorWithVector");
                        //st.add("regV", vectorToIndex);
                        st.add("regV", here-1);
                        st.add("regI", RegCounter-1);
                        //st.add("regI", vectorOfIndexes);
                        st.add("regC", RegCounter);
                        RegCounter++;
                        prog.add(st);
                        break;
                    case "boolean":
                        st = template.getInstanceOf("IndexBooleanVectorWithVector");
                        //st.add("regV", vectorToIndex);
                        st.add("regV", here-1);
                        st.add("regI", RegCounter-1);
                        //st.add("regI", vectorOfIndexes);
                        st.add("regC", RegCounter);
                        RegCounter++;
                        prog.add(st);
                        break;
                    default:
                        throw new Error("Undefined Type");
                }

                returntype = "vector_" + lhstype;
                break;
            default:
                throw new Error("Unknown Index Type");
        }

        return returntype;
    }


    @Override
    public String visitVectorLit(GrammarParser.VectorLitContext ctx) {
        // Determine size of vector
        String vecType = "null";
        int vecSize = 0;

        String result;
        String Promote;

        String exprType;

        // Iterate through expressions and determine size and type dynamically
        for (GrammarParser.MathexprContext expr : ctx.mathexpr()) {
            vecSize += 1;

            VisitorState before = GET_VISITOR_STATE();

            result = (String) visit(expr);

            REWIND_TO_STATE(before);

            Promote = MathHelper.typeTier(vecType,result);
            if (Promote.equals("left") || vecType.equals("null")){
                vecType = result;
            }
        }

        String isMatrix[] = vecType.split("_");
        if(isMatrix[0].equals("vector")) {
            return visitMatrixLit(ctx);
        }


        // malloc a vector, and store the pointer in register
        switch(vecType) {
            case "integer":
                st = template.getInstanceOf("DeclareIntegerVector");
                st.add("name", RegCounter++);
                prog.add(st);
                st = template.getInstanceOf("AllocateIntegerVector");
                st.add("regC", RegCounter++);
                st.add("size", vecSize);
                prog.add(st);
                st = template.getInstanceOf("StoreIntegerVector");
                st.add("oldReg", RegCounter-1);
                st.add("regC", RegCounter-2);
                prog.add(st);
                break;
            case "null":  //If empty, not if null
                st = template.getInstanceOf("DeclareIntegerVector");
                st.add("name", RegCounter++);
                prog.add(st);
                st = template.getInstanceOf("AllocateIntegerVector");
                st.add("regC", RegCounter++);
                st.add("size", vecSize);
                prog.add(st);
                st = template.getInstanceOf("StoreIntegerVector");
                st.add("oldReg", RegCounter-1);
                st.add("regC", RegCounter-2);
                prog.add(st);
                break;
            case "real":
                st = template.getInstanceOf("DeclareRealVector");
                st.add("name", RegCounter++);
                prog.add(st);
                st = template.getInstanceOf("AllocateRealVector");
                st.add("regC", RegCounter++);
                st.add("size", vecSize);
                prog.add(st);
                st = template.getInstanceOf("StoreRealVector");
                st.add("oldReg", RegCounter-1);
                st.add("regC", RegCounter-2);
                prog.add(st);
                break;
            case "character":
                st = template.getInstanceOf("DeclareCharacterVector");
                st.add("name", RegCounter++);
                prog.add(st);
                st = template.getInstanceOf("AllocateCharacterVector");
                st.add("regC", RegCounter++);
                st.add("size", vecSize);
                prog.add(st);
                st = template.getInstanceOf("StoreCharacterVector");
                st.add("oldReg", RegCounter-1);
                st.add("regC", RegCounter-2);
                prog.add(st);
                break;
            case "boolean":
                st = template.getInstanceOf("DeclareBooleanVector");
                st.add("name", RegCounter++);
                prog.add(st);
                st = template.getInstanceOf("AllocateBooleanVector");
                st.add("regC", RegCounter++);
                st.add("size", vecSize);
                prog.add(st);
                st = template.getInstanceOf("StoreBooleanVector");
                st.add("oldReg", RegCounter-1);
                st.add("regC", RegCounter-2);
                prog.add(st);
                break;
            default:
                throw new Error("Undefined type");
        }

        // For each mathexpr, evaluate and assign the next position in the vector
        int vecReg = RegCounter - 1;
        int position = 1;
        for (GrammarParser.MathexprContext expr : ctx.mathexpr()) {
            exprType = (String)visit(expr);

            if (!exprType.equals(vecType)) {
                // typecast
            }

            switch(vecType) {
                case "integer":
                    st = template.getInstanceOf("StoreIntegerInVector");
                    break;
                case "real":
                    st = template.getInstanceOf("StoreRealInVector");
                    break;
                case "character":
                    st = template.getInstanceOf("StoreCharacterInVector");
                    break;
                case "boolean":
                    st = template.getInstanceOf("StoreBooleanInVector");
                    break;
                default:
                    throw new Error("Undefined type");
            }
            st.add("vecReg", vecReg);
            st.add("regC", RegCounter-1);
            st.add("position", position);
            prog.add(st);
            position += 1;
        }


        switch(vecType) {
            case "integer":
                st = template.getInstanceOf("LoadIntegerVector");
                break;
            case "real":
                st = template.getInstanceOf("LoadRealVector");
                break;
            case "character":
                st = template.getInstanceOf("LoadCharacterVector");
                break;
            case "boolean":
                st = template.getInstanceOf("LoadBooleanVector");
                break;
            case "null":
                st = template.getInstanceOf("LoadIntegerVector");
                break;
            default:
                throw new Error("Undefined type");
        }
        st.add("regC", RegCounter++);
        st.add("name", vecReg-1);
        prog.add(st);



//        // Need make sure vector point is in last declared register
//        st = template.getInstanceOf("StoreVectorInNewReg");
//        st.add("oldReg", RegCounter - 1);
//        st.add("regC", RegCounter);
//        prog.add(st);
        // RegCounter++;
        return ("vector" + "_" + vecType + "_" + Integer.toString(vecSize));
    }

    @Override
    public String visitMATRIXDECLARE(GrammarParser.MATRIXDECLAREContext ctx) {
        String scopedName;
        String userDefinedName = ctx.ID().toString();
        String matrixType;
        String size = "unknown";
        if (ctx.TYPE() != null) {
            matrixType = ctx.TYPE().getText();
        } else {
            matrixType = "null";
        }

        // Now that we know the type, add to scope
        scope.addToScope(userDefinedName, "matrix_" + matrixType);
        scopedName = getLLVMVarName(scope, userDefinedName);


        String rows;
        String cols;

        VisitorState before = GET_VISITOR_STATE();

        String visitDetails[] = ((String)visit(ctx.mathexpr(0))).split("_"); //Pure Visit
        rows = visitDetails[2];
        cols = visitDetails[3];
        matrixType = visitDetails[1];

        REWIND_TO_STATE(before);

        switch(matrixType) {
            case "integer":
                st = template.getInstanceOf("DeclareIntegerMatrix");
                st.add("name",scopedName);
                prog.add(st);
                st = template.getInstanceOf("AllocateIntegerMatrix");
                st.add("oldReg", scopedName);
                st.add("regC", RegCounter++);
                st.add("rows", rows);
                st.add("cols", cols);
                st.add("t1", RegCounter++);
                st.add("t2", RegCounter++);
                st.add("t3", RegCounter++);
                st.add("t4", RegCounter++);
                st.add("t5", RegCounter++);
                prog.add(st);
                break;
            default:
                System.out.println("Erererer");
                throw new Error("Undefined Matrix Type");
        }

        visit(ctx.mathexpr(0));

        switch(matrixType) {
            case "integer":
                st = template.getInstanceOf("StoreIntegerMatrix");
                st.add("name",scopedName);
                st.add("oldReg",RegCounter-1);
                st.add("regC",RegCounter++);
                prog.add(st);
                break;
            default:
                System.out.println("Erererer");
                throw new Error("Undefined Matrix Type");
        }


        return "matrix";
    }

    public String visitMatrixLit(GrammarParser.VectorLitContext ctx) {
        int rows = 0;
        String cols = "???";
        String matrixType = "???";

        for (GrammarParser.MathexprContext expr : ctx.mathexpr()) {
            rows++;

            VisitorState before = GET_VISITOR_STATE();

            String visitDetails[] = ((String)visit(expr)).split("_");
            cols = visitDetails[2];
            matrixType = visitDetails[1];

            REWIND_TO_STATE(before);
        }

        int matReg = RegCounter;
        switch(matrixType) {
            case "integer":
                st = template.getInstanceOf("DeclareIntegerMatrix");
                st.add("name", RegCounter++);
                prog.add(st);
                st = template.getInstanceOf("AllocateIntegerMatrix");
                st.add("oldReg", RegCounter-1);
                st.add("regC", RegCounter++);
                st.add("rows", rows);
                st.add("cols", cols);
                st.add("t1", RegCounter++);
                st.add("t2", RegCounter++);
                st.add("t3", RegCounter++);
                st.add("t4", RegCounter++);
                st.add("t5", RegCounter++);
                prog.add(st);
                break;
            case "real":
                st = template.getInstanceOf("AllocateRealVector");
                st.add("regC", RegCounter++);
                st.add("rows", rows);
                st.add("cols", cols);
                prog.add(st);
                break;
            case "null":
                st = template.getInstanceOf("AllocateIntegerVector");
                st.add("regC", RegCounter++);
                st.add("rows", rows);
                st.add("cols", cols);
                prog.add(st);
                break;
            case "character":
                st = template.getInstanceOf("AllocateCharacterVector");
                st.add("regC", RegCounter++);
                st.add("rows", rows);
                st.add("cols", cols);
                prog.add(st);
                break;
            case "boolean":
                st = template.getInstanceOf("AllocateBoolVector");
                st.add("regC", RegCounter++);
                st.add("rows", rows);
                st.add("cols", cols);
                prog.add(st);
                break;
            default:
                throw new Error("Undefined type");
        }

        int i = 1;
        for (GrammarParser.MathexprContext expr : ctx.mathexpr()) {

            visit(expr);
            st = template.getInstanceOf("StoreIntegerVectorInMatrix");
            st.add("regC", RegCounter-1);
            st.add("matReg",matReg);
            st.add("position",i);
            prog.add(st);
            i++;
        }

        st = template.getInstanceOf("LoadIntegerMatrix");
        st.add("regC", RegCounter++);
        st.add("name", matReg);
        prog.add(st);

        return "matrix" + "_" + matrixType + "_" + Integer.toString(rows) + "_" + cols;
    }

    // Typecasting
    @Override
    public String visitMATHCAST(GrammarParser.MATHCASTContext ctx) {
        String type = ctx.TYPE().getText();

        String exprType = checkifBoolC((String) visit(ctx.mathexpr()));
        //System.out.println("What are we doing " + type + ' ' + exprType);
        if (type.equals(exprType)) {
            return type;
        }
        prog.add(MathHelper.ChoosePromote(RegCounter, RegCounter-1, exprType, type));
        RegCounter++;

        return type;
    }
    //=============END ASSIGNMENT OVERRIDE===========//

    //=============TUPLES===========//
    @Override
    public String visitVAGUETUPLEASSIGN(GrammarParser.VAGUETUPLEASSIGNContext ctx) {
        String scopedName;
        String name = ctx.ID().toString();

        List<String> types = visitTupleLit(ctx.tupleLit());
        List<String> ids = new ArrayList<>();
        List<String> LLVMTypes = new ArrayList<String>();

        Integer index = 1;
        for (String t : types) {
            String in = Helper.convertType(t);
            LLVMTypes.add(in);
            ids.add(index.toString());
            index++;
        }

        Map<String, TupleField> m = new HashMap<>();
        for (int i = 0; i < types.size(); i++) {
            String fieldname = ids.get(i);
            String type = types.get(i);
            TupleField tf = new TupleField(i,type);
            m.put(fieldname, tf);
        }

        scope.addToScope(name, "tuple", m);
        scopedName = getLLVMVarName(scope, name);

        st = template.getInstanceOf("DeclareTupleStruct");
        st.add("name", scopedName);
        st.add("types", LLVMTypes);
        declareStructs.add(st);

        st = template.getInstanceOf("InitTuple");
        st.add("name", scopedName);
        prog.add(st);

        visitTupleLit(ctx.tupleLit(), scopedName);

        return "tuple";
    }

    @Override
    public String visitTUPLEASSIGN(GrammarParser.TUPLEASSIGNContext ctx) {
        String scopedName;
        String name = ctx.ID().toString();

        List<String> LLVMTypes = new ArrayList<String>();
        List<TupleArg> lt = visitTupleArgs(ctx.tupleArgs());
        List<String> ids = new ArrayList<>();

        Integer index = 1;
        for (TupleArg t : lt) {
            String in = Helper.convertType(t.getType());
            LLVMTypes.add(in);
            if(!t.getId().equals("")) {
                ids.add(t.getId());
            } else {
                ids.add(index.toString());
            }
            index++;
        }

        Map<String, TupleField> m = new HashMap<>();
        for (int i = 0; i < lt.size(); i++) {
            String fieldname = ids.get(i);
            String type = lt.get(i).getType();
            TupleField tf = new TupleField(i,type);
            m.put(fieldname, tf);
        }

        scope.addToScope(name, "tuple", m);
        scopedName = getLLVMVarName(scope, name);

        st = template.getInstanceOf("DeclareTupleStruct");
        st.add("name", scopedName);
        st.add("types", LLVMTypes);
        declareStructs.add(st);

        st = template.getInstanceOf("InitTuple");
        st.add("name", scopedName);
        prog.add(st);

        visitTupleLit(ctx.tupleLit(), scopedName);
        return "tuple";
    }

    @Override
    public List<TupleArg> visitTupleArgs(GrammarParser.TupleArgsContext ctx) {
        List<TupleArg> l = new ArrayList<TupleArg>();
        List<GrammarParser.TupleSignContext> t = ctx.tupleSign();
        for (GrammarParser.TupleSignContext tSign : t) {
            l.add((TupleArg) visit(tSign));
        }
        return l;
    }

    @Override
    public TupleArg visitTupleSign(GrammarParser.TupleSignContext ctx) {
        TupleArg t;
        String type = ctx.TYPE().toString();
        if(type.equals("integer")) {
            type = "integer";
        }
        if (ctx.ID() != null) {
            t = new TupleArg(type, ctx.ID().toString());
        } else {
            t = new TupleArg(type);
        }
        return t;
    }

    public List<String> visitTupleLit(GrammarParser.TupleLitContext ctx) {
        List<GrammarParser.MathexprContext> lm = ctx.mathexpr();
        List<String> types = new ArrayList<String>();
        int index = 0;
        for ( GrammarParser.MathexprContext m : lm) {
            String type = (String) visit(m);
            types.add(type);
        }
        return types;
    }

    public List<String> visitTupleLit(GrammarParser.TupleLitContext ctx, String name) {
        List<GrammarParser.MathexprContext> lm = ctx.mathexpr();

        List<String> types = new ArrayList<String>();
        int index = 0;
        for ( GrammarParser.MathexprContext m : lm) {
            String type = (String) visit(m);

            switch (type) {
                case "integer":
                    st = template.getInstanceOf("AssignIntTuple");
                    st.add("regC", RegCounter);
                    st.add("regV", RegCounter - 1);
                    st.add("name", name);
                    st.add("index", index);
                    RegCounter++;
                    prog.add(st);
                    break;
                case "real":
                    st = template.getInstanceOf("AssignFloatTuple");
                    st.add("regC", RegCounter);
                    st.add("regV", RegCounter - 1);
                    st.add("name", name);
                    st.add("index", index);
                    RegCounter++;
                    prog.add(st);
                    break;
                case "boolean":
                    st = template.getInstanceOf("AssignBoolTuple");
                    st.add("regC", RegCounter);
                    st.add("regV", RegCounter - 1);
                    st.add("name", name);
                    st.add("index", index);
                    RegCounter++;
                    prog.add(st);
                    break;
                case "character":
                    st = template.getInstanceOf("AssignCharTuple");
                    st.add("regC", RegCounter);
                    st.add("regV", RegCounter - 1);
                    st.add("name", name);
                    st.add("index", index);
                    RegCounter++;
                    prog.add(st);

                    break;
                default:
                    throw new Error("Undefined type");
            }
            types.add(type);
            index++;
        }
        return types;
    }

    @Override
    public String visitTUPLEACCESS(GrammarParser.TUPLEACCESSContext ctx)  {
        String[] taccess = ctx.TUPLEACCESS().toString().split(Pattern.quote("."));
        String name = taccess[0];
        String field = taccess[1];
        String type = scope.getTupleFieldType(name,field);
        Integer index = scope.getTupleFieldIndex(name,field);
        String scopedName = getLLVMVarName(scope, name);

        switch(type) {
            case "integer":
                st = template.getInstanceOf("LoadIntFromStruct");
                st.add("t0",RegCounter++);
                st.add("t1", RegCounter++);
                st.add("regC", RegCounter++);
                st.add("name", scopedName);
                st.add("index",index);
                prog.add(st);
                break;
            case "real":
                st = template.getInstanceOf("LoadFloatFromStruct");
                st.add("t0",RegCounter++);
                st.add("t1", RegCounter++);
                st.add("regC", RegCounter++);
                st.add("name", scopedName);
                st.add("index",index);
                prog.add(st);
                break;
            case "boolean":
                st = template.getInstanceOf("LoadBoolFromStruct");
                st.add("t0",RegCounter++);
                st.add("t1", RegCounter++);
                st.add("regC", RegCounter++);
                st.add("name", scopedName);
                st.add("index",index);
                prog.add(st);
                break;
            case "character":
                st = template.getInstanceOf("LoadCharFromStruct");
                st.add("t0",RegCounter++);
                st.add("t1", RegCounter++);
                st.add("regC", RegCounter++);
                st.add("name", scopedName);
                st.add("index",index);
                prog.add(st);
                break;
            default:
                throw new Error("Undefined type");
        }

        return type;
    }

    @Override
    public String visitTUPLEFIELDASSIGN(GrammarParser.TUPLEFIELDASSIGNContext ctx) {
        String[] taccess = ctx.TUPLEACCESS().toString().split(Pattern.quote("."));
        String name = taccess[0];
        String field = taccess[1];
        String type = scope.getTupleFieldType(name,field);
        Integer index = scope.getTupleFieldIndex(name,field);
        String scopedName = getLLVMVarName(scope, name);
        visit(ctx.mathexpr());
        switch (type) {
            case "integer":
                st = template.getInstanceOf("AssignIntTuple");
                st.add("regC", RegCounter);
                st.add("regV", RegCounter - 1);
                st.add("name", scopedName);
                st.add("index", index);
                RegCounter++;
                prog.add(st);
                break;
            case "real":
                st = template.getInstanceOf("AssignFloatTuple");
                st.add("regC", RegCounter);
                st.add("regV", RegCounter - 1);
                st.add("name", scopedName);
                st.add("index", index);
                RegCounter++;
                prog.add(st);
                break;
            case "boolean":
                st = template.getInstanceOf("AssignBoolTuple");
                st.add("regC", RegCounter);
                st.add("regV", RegCounter - 1);
                st.add("name", scopedName);
                st.add("index", index);
                RegCounter++;
                prog.add(st);
                break;
            case "character":
                st = template.getInstanceOf("AssignCharTuple");
                st.add("regC", RegCounter);
                st.add("regV", RegCounter - 1);
                st.add("name", scopedName);
                st.add("index", index);
                RegCounter++;
                prog.add(st);
                break;
            default:
                throw new Error("Undefined type");
        }
        return type;
    }

    //=============TUPLES END===========//


    //==============MATH==========================//

    // Add and Sub
    public String visitMATHAS(GrammarParser.MATHASContext ctx){
        String left = (String) visit(ctx.mathexpr(0));
        int leftReg = RegCounter-1;
        String right = (String) visit(ctx.mathexpr(1));
        int rightReg = RegCounter-1;
        //System.out.println("THIS IS WHAT GOT TO ADD " + left  + " " + right + " " + leftReg + " " + rightReg);
        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType = returnTargetType(Promote,left,right);
        //System.out.println("THIS IS WHAT GOT TO END BEFORE STRING TEMPLATE " + returnType + " " + regNeed.get(0) + " " + regNeed.get(1));
        if(ctx.op.getType() == GrammarParser.ADD){
            prog.add(MathHelper.ChooseAdd(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
            RegCounter++;
        } else {
            prog.add(MathHelper.ChooseSub(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
            RegCounter++;
        }
        return returnType;
    }

    // Multiply and divide
    public String visitMATHMDM(GrammarParser.MATHMDMContext ctx){
        String left =  (String) visit(ctx.mathexpr(0));
        int leftReg = RegCounter-1;
        String right = (String) visit(ctx.mathexpr(1));
        int rightReg = RegCounter-1;
        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType =returnTargetType(Promote,left,right);

        if(ctx.op.getType() == GrammarParser.MUL){
            prog.add(MathHelper.ChooseMul(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
            RegCounter++;
        } else if (ctx.op.getType() == GrammarParser.DIV){
            prog.add(MathHelper.ChooseDiv(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
            RegCounter++;
        } else {
            prog.add(MathHelper.ChooseMod(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
            RegCounter++;
        }

        return returnType;
    }

    // Exponents
    public String visitMATHEXPON(GrammarParser.MATHEXPONContext ctx){
        String left = (String) visit(ctx.mathexpr(0));
        int leftReg = RegCounter-1;
        String right = (String) visit(ctx.mathexpr(1));
        int rightReg = RegCounter-1;
        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType =returnTargetType(Promote,left,right);

        prog.add(MathHelper.ChooseExpon(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
        RegCounter++;
        return returnType;

    }

    public String visitMATHCONCAT(GrammarParser.MATHCONCATContext ctx) {
        String left = (String) visit(ctx.mathexpr(0));
        int leftReg = RegCounter-1;
        String right = (String) visit(ctx.mathexpr(1));
        int rightReg = RegCounter-1;
        //System.out.println("THIS IS WHAT GOT TO ADD " + left  + " " + right + " " + leftReg + " " + rightReg);
        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType = returnTargetType(Promote,left,right);
        //System.out.println("THIS IS WHAT GOT TO END BEFORE STRING TEMPLATE " + returnType + " " + regNeed.get(0) + " " + regNeed.get(1));
        prog.add(MathHelper.ChooseConcat(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
        RegCounter++;
        return returnType;
    }

    public String visitMATHDOT(GrammarParser.MATHDOTContext ctx) {
        String left = (String) visit(ctx.mathexpr(0));
        int leftReg = RegCounter-1;
        String right = (String) visit(ctx.mathexpr(1));
        int rightReg = RegCounter-1;
        //System.out.println("THIS IS WHAT GOT TO ADD " + left  + " " + right + " " + leftReg + " " + rightReg);
        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType = returnTargetType(Promote,left,right);
        //System.out.println("THIS IS WHAT GOT TO END BEFORE STRING TEMPLATE " + returnType + " " + regNeed.get(0) + " " + regNeed.get(1));
        prog.add(MathHelper.ChooseDot(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
        RegCounter++;
        return returnType.split("_")[1];
    }

    // >, <, >=. <=
    public String visitMATHCOMARE(GrammarParser.MATHCOMAREContext ctx){
        String left = checkifBoolC((String) visit(ctx.mathexpr(0)));
        int leftReg = RegCounter-1;
        String right = checkifBoolC((String) visit(ctx.mathexpr(1)));
        int rightReg = RegCounter-1;
        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType =returnTargetType(Promote,left,right);

        switch (ctx.op.getType()){
            case GrammarParser.LES:
                prog.add(MathCMPHelper.ChooseLes(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
                break;
            case GrammarParser.LESEQ:
                prog.add(MathCMPHelper.ChooseLeq(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
                break;
            case GrammarParser.GRT:
                prog.add(MathCMPHelper.ChooseGre(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
                break;
            case GrammarParser.GRTEQ:
                prog.add(MathCMPHelper.ChooseGeq(RegCounter, regNeed.get(0),regNeed.get(1), returnType));
                break;
        }
        RegCounter++;
        String other = null;
        if (returnType.contains("_")) {
            //System.out.println("GOT HERE WITH" + returnType);
            other = returnType.split("_")[0];
            other = other + "_boolean";
        }
        if(other != null){
            return other;
        } else {
            return "booleanC";
        }
    }

    // ==, !=
    public String visitMATHEQNEQ(GrammarParser.MATHEQNEQContext ctx){
        String left = checkifBoolC((String) visit(ctx.mathexpr(0)));
        int leftReg = RegCounter-1;
        String right = checkifBoolC((String) visit(ctx.mathexpr(1)));
        int rightReg = RegCounter-1;
        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType =returnTargetType(Promote,left,right);
        if (ctx.op.getType() == GrammarParser.EQU ){
            prog.add(MathCMPHelper.ChooseEq(RegCounter, regNeed.get(0), regNeed.get(1), returnType));
        } else {
            prog.add(MathCMPHelper.ChooseNeq(RegCounter, regNeed.get(0), regNeed.get(1), returnType));
        }
        RegCounter++;
        String other = null;
        if (returnType.contains("_")) {
            //System.out.println("GOT HERE WITH" + returnType);
            other = returnType.split("_")[0];
        }

        if (other == null) {
            if (returnType.equals("boolean")) {
                return "boolean";
            }
            return "booleanC";
        } else {
            //System.out.println("GOT HERE WITH" + Type);
            return "boolean";
        }
    }

    @Override public String visitBUILTINLENGTH(GrammarParser.BUILTINLENGTHContext ctx) {
        // Visit the expression to load the vector into the regCounter register
        String vectorType = (String)visit(ctx.mathexpr());
        if (!vectorType.split("_")[0].equals("vector")) {
            throw new Error("Cannot call length on a non-vector");
        }
        vectorType = vectorType.split("_")[1];
        switch(vectorType) {
            case "integer":
                st = template.getInstanceOf("GetIntVectorLength");
                st.add("regV", RegCounter-1);
                st.add("regC", RegCounter);
                RegCounter++;
                prog.add(st);
                return "integer";
            case "real":
                st = template.getInstanceOf("GetRealVectorLength");
                st.add("regV", RegCounter-1);
                st.add("regC", RegCounter);
                RegCounter++;
                prog.add(st);
                return "integer";
            case "character":
                st = template.getInstanceOf("GetCharVectorLength");
                st.add("regV", RegCounter-1);
                st.add("regC", RegCounter);
                RegCounter++;
                prog.add(st);
                return "integer";
            case "boolean":
                st = template.getInstanceOf("GetBoolVectorLength");
                st.add("regV", RegCounter-1);
                st.add("regC", RegCounter);
                RegCounter++;
                prog.add(st);
                return "integer";
            default:
                throw new Error("Invalid vector type for length invocation");
        }
        // Switch statement for each type of vector
            // invoke new c function which returns the int value of 0 element of vector
    }



    //=============MATH HELPER FUNCTIONS THAT DEAL WITH PROMOTIONS AND RETURNING PROMOTED TYPE==========//

    public String checkifBoolC(String type){
        if(type.equals("booleanC")){
            prog.add(MathHelper.convertBoolC(RegCounter));
            RegCounter++;
            return "boolean";
        } else {
            return type;
        }
    }

    // type promotion helper function
    public String returnTargetType(String Promote, String left, String right){
        if(Promote.equals("left")){
            return right;
        } else if (Promote.equals("right")){
            return left;
        } else {
            return left;
        }
    }

    // type promotion helper
    public ArrayList<Integer> typePromotion(String Promote, int regL, int regR, String left, String right){
        ArrayList<Integer> returnReg = new ArrayList<Integer>();
        returnReg.add(regL);
        returnReg.add(regR);
        //System.out.println(Promote + ' ' +  left + ' ' + right);
        if(!Promote.equals("match")){
            if(Promote.equals("left")){
                prog.add(MathHelper.ChoosePromote(RegCounter, regL, left, right));
                returnReg.set(0, RegCounter);
                RegCounter++;
            } else {
                prog.add(MathHelper.ChoosePromote(RegCounter, regR, right, left));
                returnReg.set(1, RegCounter);
                RegCounter++;
            }
        }
        return returnReg;
    }

    //=======END MATH HELPER FUNCTIONS THAT DEAL WITH PROMOTIONS AND RETURNING PROMOTED TYPE==========//

    //===================MATH ATOM=====================//
    @Override
    public String visitMATHATOM(GrammarParser.MATHATOMContext ctx) {
        return (String) visit(ctx.atom());

    }

    @Override
    public String visitINT(GrammarParser.INTContext ctx){
        st = template.getInstanceOf("AtomInt");
        st.add("regC", RegCounter);
        st.add("val", Helper.formatNumber(ctx.INT().getText()));
        RegCounter++;
        prog.add(st);
        return "integer";
    }

    @Override
    public String visitNULL(GrammarParser.NULLContext ctx){
        st = template.getInstanceOf("AtomInt");
        st.add("regC", RegCounter);
        st.add("val", "0");
        RegCounter++;
        prog.add(st);

        nullOrIdenity = "null";
        return "null";
    }

    @Override
    public String visitIDENTITY(GrammarParser.IDENTITYContext ctx){
        st = template.getInstanceOf("AtomInt");
        st.add("regC", RegCounter);
        st.add("val", "1");
        RegCounter++;
        prog.add(st);
        // null and identity have same type, so this is ok
        nullOrIdenity = "identity";
        return "null";
    }

    @Override
    public String visitREAL(GrammarParser.REALContext ctx){
        String realString = ctx.REAL().getText();
        int size = realString.length()+1;

        st = template.getInstanceOf("StringLit");
        st.add("stringNum", StrCounter);
        StrCounter++;
        st.add("size", size);
        st.add("stringLit", realString);
        stringLit.add(st);

        st = template.getInstanceOf("AtomReal");
        st.add("stringNum", StrCounter -1);
        st.add("size", size);
        st.add("regC", RegCounter);
        RegCounter++;
        st.add("regC2", RegCounter);
        RegCounter++;
        prog.add(st);

        return "real";
    }

    @Override
    public String visitCHAR(GrammarParser.CHARContext ctx){
        st = template.getInstanceOf("AtomChar");
        st.add("regC", RegCounter);
        char val = Helper.getEscapedChars(ctx.CHAR().getText());
        st.add("val", (int)val);
        prog.add(st);
        RegCounter++;
        return "character";
    }


    @Override
    public String visitBOOL(GrammarParser.BOOLContext ctx){
        st = template.getInstanceOf("AtomBool");
        st.add("regC", RegCounter);
        if(ctx.getText().equals("true")){
            st.add("val", 1);
        } else {
            st.add("val", 0);
        }
        prog.add(st);
        RegCounter++;
        return "boolean";
    }

    @Override public String visitMATHVECTORLIT(GrammarParser.MATHVECTORLITContext ctx) {
        return (String) visit(ctx.vectorLit());
    }




    @Override
    public String visitID(GrammarParser.IDContext ctx) {
        String userDefinedName = ctx.ID().getText();
        String type = scope.getLLVMType(userDefinedName);
        String scopedName = getLLVMVarName(scope, userDefinedName);
        // System.out.println("User Name: " + userDefinedName);
        // System.out.println("Type: " + type);
//        boolean isGlobal = scope.getScopeNumber(userDefinedName).equals(0);
//
//        if(isGlobal) {
//            visitGlobalID(type, scopedName);
//            return type;
//        }

        //MOVE ALL THE STUFF FROM VISIT ID INTO ASSIGN_ST_HELPER

        prog.add(Assign_ST_Helper.loadVar(type, RegCounter,scopedName));
        RegCounter++;
        //System.out.println("THE TYPE OF THE VISIT " + type);

        return type;
    }


    //===============END MATH ATOM=====================//


    //================PRINTING FUNCTIONS================//
    @Override
    public String visitPRINTEXPR (GrammarParser.PRINTEXPRContext ctx) {
            String type = (String) visit(ctx.mathexpr());
            String vecType = null;
            if (type.contains("_")) {
                if (type.split("_")[0].equals("vector")) {
                    vecType = type.split("_")[1];
                }
                type = type.split("_")[0];
            }
            switch (type){
                case "integer":
                    st = template.getInstanceOf("PrintInt");
                    st.add("regC", RegCounter);
                    st.add("regV", RegCounter - 1);
                    RegCounter++;
                    prog.add(st);
                    break;
                case "real":
                    st = template.getInstanceOf("PrintReal");
                    st.add("regC", RegCounter-1);
                    prog.add(st);
                    break;
                case "boolean":
                    st = template.getInstanceOf("PrintBool");
                    st.add("regC", RegCounter-1);
                    prog.add(st);
                    break;
                case "booleanC":
                    prog.add(MathHelper.convertBoolC(RegCounter));
                    RegCounter++;
                    st = template.getInstanceOf("PrintBool");
                    st.add("regC", RegCounter-1);
                    prog.add(st);
                    break;
                case "character":
                    st = template.getInstanceOf("PrintChar");
                    st.add("regC", RegCounter-1);
                    prog.add(st);
                    break;
                case "vector":
                    switch(vecType) {
                        case "integer":
                            st = template.getInstanceOf("PrintIntegerVector");
                            break;
                        case "null":
                            st = template.getInstanceOf("PrintIntegerVector");
                            break;
                        case "real":
                            st = template.getInstanceOf("PrintRealVector");
                            break;
                        case "character":
                            st = template.getInstanceOf("PrintCharacterVector");
                            break;
                        case "boolean":
                            st = template.getInstanceOf("PrintBooleanVector");
                            break;
                        default:
                            throw new Error("Undefined type");
                    }
                    st.add("regC", RegCounter-1);
                    prog.add(st);
                    break;
                case "interval":
                    st = template.getInstanceOf("PrintIntegerVector");
                    st.add("regC", RegCounter-1);
                    prog.add(st);
                    break;
                default:
                    throw new Error("Undefined type");
            }
        return null;
    }



    // Print chars
    @Override
    public String visitPRINTSTRINGCHAR(GrammarParser.PRINTSTRINGCHARContext ctx) {
        /* this needs to be gutted and replaced. Thought this was for printing
            string/char from var. actually for printing  'C' -> out
            */
        if (ctx.STRING() == null) {
            char charval = Helper.getEscapedChars(ctx.CHAR().getText());
            st = template.getInstanceOf("AtomChar");
            st.add("regC", RegCounter);
            RegCounter++;
            st.add("val", (int)charval);
            prog.add(st);
            st = template.getInstanceOf("PrintChar");
            st.add("regC", RegCounter-1);
            prog.add(st);
            return "character";
        } else {
            return "string";
        }
    }

    //================PRINTING FUNCTIONS================//

    //============INPUT OUTPUT STREAM===================//
    @Override
    public String visitSTDIN(GrammarParser.STDINContext ctx) {
        // Get the type
        String userDefinedName = ctx.ID().getText();
        String scopedName = getLLVMVarName(scope, userDefinedName);
        String type = scope.getLLVMType(userDefinedName);

        switch(type) {
            case "integer":
                st = template.getInstanceOf("ReadInt");
                st.add("varName", scopedName);
                prog.add(st);
                break;
            case "real":
                st = template.getInstanceOf("ReadFloat");
                st.add("varName", scopedName);
                prog.add(st);
                break;
            case "character":
                st = template.getInstanceOf("ReadChar");
                st.add("varName", scopedName);
                prog.add(st);
                break;
            case "boolean":
                st = template.getInstanceOf("ReadBool");
                st.add("varName", scopedName);
                prog.add(st);
                break;
            default:
                throw new Error("Undefined type");
        }
        return type;
    }
    //========END INPUT OUTPUT STREAM===================//


    //==================LOGICAL OPERAIONS=================//
    public String visitMATHAND(GrammarParser.MATHANDContext ctx){
        String left = checkifBoolC((String) visit(ctx.mathexpr(0)));
        int leftReg = RegCounter-1;
        String right = checkifBoolC((String) visit(ctx.mathexpr(1)));
        int rightReg = RegCounter-1;

        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left, right);
        String returnType = returnTargetType(Promote,left,right);

        prog.add(MathLogicalHelper.ChooseAND(RegCounter, regNeed.get(0), regNeed.get(1), returnType));
        RegCounter++;
        return returnType;
    }

    public String visitMATHORXOR(GrammarParser.MATHORXORContext ctx){
        String left = checkifBoolC((String) visit(ctx.mathexpr(0)));
        int leftReg = RegCounter-1;
        String right = checkifBoolC((String) visit(ctx.mathexpr(1)));
        int rightReg = RegCounter-1;

        String Promote = MathHelper.typeTier(left,right);
        ArrayList<Integer> regNeed = typePromotion(Promote, leftReg, rightReg, left,right);
        String returnType = returnTargetType(Promote,left,right);

        if(ctx.op.getType() == GrammarParser.OR){
            prog.add(MathLogicalHelper.ChooseOR(RegCounter,regNeed.get(0), regNeed.get(1), returnType));
        } else {
            prog.add(MathLogicalHelper.ChooseXOR(RegCounter, regNeed.get(0), regNeed.get(1), returnType));
        }

        RegCounter++;
        return returnType;
    }

    public String visitMATHUNARY(GrammarParser.MATHUNARYContext ctx){
        String type = checkifBoolC((String) visit(ctx.mathexpr()));
        if (ctx.op.getType() == GrammarParser.NOT){
            prog.add(MathLogicalHelper.ChooseNOT(RegCounter,  RegCounter-1, type));
            RegCounter++;
        } else if (ctx.op.getText().equals("-")){
            prog.add( MathCMPHelper.ChooseUnaryN(RegCounter, type));
            RegCounter++;
        } else {
            prog.add( MathCMPHelper.ChooseUnaryP(RegCounter, type));
            RegCounter++;
        }
        return type;
    }

    public String visitBMATHEXPR(GrammarParser.BMATHEXPRContext ctx) {
        return (String) visit(ctx.mathexpr());
    }

    private String getLLVMVarName(Scope scope, String userDefinedName) {
        if(scope.getScopeNumber(userDefinedName).equals(0)) {
            return "g" + scope.getScopeNumber(userDefinedName) + "_" + userDefinedName;
        }
        return "s" + scope.getScopeNumber(userDefinedName) + "_" + userDefinedName;
    }

    //==============END LOGICAL OPERAIONS=================//

    //===============DON'T NEED STUFF UNDER HERE UNTIL LATER===========//
    public String visitSTRING(GrammarParser.STRINGContext ctx){
        st = template.getInstanceOf("AtomString");
        st.add("regC", RegCounter);
        st.add("size", ctx.STRING().getText().length()+1);
        st.add("stringNum", StrCounter);
        prog.add(st);
        st = template.getInstanceOf("StringLit");
        st.add("stringNum", StrCounter);
        st.add("size", ctx.STRING().getText().length()+1);
        st.add("stringLit", ctx.STRING().getText());
        RegCounter++;
        StrCounter++;
        stringLit.add(st);
        return "string";
    }


}//END LLVMVISITOR

    
