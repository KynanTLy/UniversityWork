import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;


public class MathHelper {

    //==============DETERMINE WHICH SIDE NEEDS TO BE PROMOTED===========//
    public static String typeTier(String a, String b){
        if(a.contains("_")) {
            a = a.split("_")[1];
        }

        if(b.contains("_")) {
            b = b.split("_")[1];
        }


        if(a.equals(b)){
            return "match";
        } else if (a.equals("real")&& b.equals("integer")){
            return "right";
        } else if (a.equals("integer")&& b.equals("real")){
            return "left";
        } else if (a.equals("null") && b.equals("real")) {
            return "left";
        } else if (a.equals("real") && b.equals("null")) {
            return "right";
        } else if (a.equals("null") && b.equals("character")) {
            return "left";
        } else if (a.equals("character") && b.equals("null")) {
            return "right";
        } else if (a.equals("null") && b.equals("boolean")) {
            return "left";
        } else if (a.equals("boolean") && b.equals("null")) {
            return "right";
        } else if (a.equals("null") && b.equals("integer")) {
            return "left";
        } else if (a.equals("integer") && b.equals("null")) {
            return "right";
        }

        throw new Error("DIDN'T IMPLEMENT YET");
    }

    //=============CHOOSE YOUR OWN PROMOTION===============//
    public static ST ChoosePromote(int regC, int regV, String source, String target){
        switch (source){
            case "integer":
                switch (target){
                    case "real":
                        return (ProIR(regC,regV));
                    case "boolean":
                        return (ProIB(regC, regV));
                    case "character":
                        return (ProIC(regC, regV));
                    default:
                        throw new Error("Invalid int to");
                }//end INT -> SOMETHING
            case "null":
                switch(target) {
                    case "real":
                        return (ProNR(regC, regV));
                    case "integer":
                        return (ProNI(regC, regV));
                    case "character":
                        return (ProNC(regC, regV));
                    case "boolean":
                        return (ProNB(regC, regV));
                    default:
                        throw new Error("Invalid null to");
                }
            case "boolean":
                switch(target) {
                    case "integer":
                        return (ProBI(regC, regV));
                    case "real" :
                        return (ProBR(regC, regV));
                    case "character":
                        return (ProBC(regC, regV));
                    default:
                        throw new Error("Invalid boolean to");
                }
            case "real":
                switch(target) {
                    case "integer":
                        return (ProRI(regC, regV));
                    default:
                        throw new Error("Invalid real to");
                }
            case "character":
                switch(target) {
                    case "boolean":
                        return (ProCB(regC, regV));
                    case "integer" :
                        return (ProCI(regC, regV));
                    case "real":
                        return (ProCR(regC, regV));
                    default:
                        throw new Error("Invalid character to");
                }

        }
        throw new Error("lol what");
    }

    public static ST convertBoolC(int RegCounter){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BoolConvertilToi8");
        st.add("regC", RegCounter);
        st.add("regV", RegCounter-1);
        return st;
    }

    //=============END CHOOSE YOUR OWN PROMOTION===============//

    //=============CHOOSE YOUR OWN MATH EQUATION==============//

    public static ST ChooseAdd(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "integer":
                return (AddII(regC,regL,regR));
            case "real":
                return (AddFF(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (AddIntegerVectors(regC, regL, regR));
                    case "real":
                        return (AddRealVectors(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");

        }
    }

    public static ST ChooseSub(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "integer":
                return (SubII(regC,regL,regR));
            case "real":
                return (SubFF(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (SubIntegerVectors(regC, regL, regR));
                    case "real":
                        return (SubRealVectors(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");

        }
    }

    public static ST ChooseMul(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "integer":
                return (MulII(regC,regL,regR));
            case "real":
                return (MulFF(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (MulIntegerVectors(regC, regL, regR));
                    case "real":
                        return (MulRealVectors(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseDiv(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "integer":
                return (DivII(regC,regL,regR));
            case "real":
                return (DivFF(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (DivIntegerVectors(regC, regL, regR));
                    case "real":
                        return (DivRealVectors(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseExpon(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type) {
            case "integer":
                return (ExponII(regC, regL, regR));
            case "real":
                return (ExponFF(regC, regL, regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (ExponIntegerVectors(regC, regL, regR));
                    case "real":
                        return (ExponRealVectors(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseMod(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "integer":
                return (ModII(regC,regL,regR));
            case "real":
                return (ModFF(regC, regL, regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (ModIntegerVectors(regC, regL, regR));
                    case "real":
                        return (ModRealVectors(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseConcat(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (ConcatIntegerVectors(regC, regL, regR));
                    case "real":
                        return (ConcatRealVectors(regC, regL, regR));
                    case "character":
                        return (ConcatCharacterVectors(regC, regL, regR));
                    case "boolean":
                        return (ConcatBooleanVectors(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseDot(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (DotIntegerVector(regC, regL, regR));
                    case "real":
                        return (DotRealVector(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    //=============END CHOOSE YOUR OWN MATH EQUATION==============//

    //=============TEMPLATES FOR EACH MATH OPERATION==============//
    //II MEANS INTS
    //FF MEANS FLOATS

    public static ST ModII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ModII");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ModFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ModFF");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;

    }

    public static ST ExponII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ExponII");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ExponFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ExponFF");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;

    }

    public static ST AddII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AddII");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST AddFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AddFF");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    // Add two integer vectors together, and store the resulting vector in regC
    public static ST AddIntegerVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AddIntegerVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST SubIntegerVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("SubIntegerVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST MulIntegerVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("MulIntegerVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST DivIntegerVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DivIntegerVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ExponIntegerVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ExponIntegerVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ModIntegerVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ModIntegerVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    // Add two integer vectors together, and store the resulting vector in regC
    public static ST AddRealVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AddRealVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST SubRealVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("SubRealVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST MulRealVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("MulRealVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST DivRealVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DivRealVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ExponRealVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ExponRealVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ModRealVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ModRealVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ConcatIntegerVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ConcatIntegerVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ConcatRealVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ConcatRealVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ConcatCharacterVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ConcatCharacterVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST ConcatBooleanVectors(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ConcatBooleanVectors");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST DotIntegerVector(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DotIntegerVector");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST DotRealVector(int regC, int regL, int regR) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DotRealVector");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST SubII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("SubII");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST SubFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("SubFF");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST MulII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("MulII");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST MulFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("MulFF");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST DivII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DivII");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST DivFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DivFF");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    //=============END TEMPLATES FOR EACH MATH OPERATION==============//


    //===============BOOL CONVERSION FOR IF/LOOP===============//

    public static ST convertBoolToLLWM(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("CHECKBOOL");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;

    }

    public static ST convertIntToBool(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("CHECKINT");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    //==========END BOOL CONVERSION FOR IF/LOOP===============//

    //==========PROMOTION TEMPLATES========//
    public static ST ProIR(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProIR");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProNR(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProNR");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProNI(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProNI");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProNC(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProNC");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProNB(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProNB");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProIB(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProIB");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProIC(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProIC");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProBI(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProBI");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProBR(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProBR");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProBC(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProBC");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProRI(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProRI");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProCB(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProCB");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProCI(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProCI");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST ProCR(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ProCR");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    //==========END PROMOTION TEMPLATES========//

}
