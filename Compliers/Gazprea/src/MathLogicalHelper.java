import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Created by kynan on 11/3/16.
 */
public class MathLogicalHelper {

    //===============LOGICAL OPERATION SELECTOR====================//
    public static ST ChooseOR(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }
        type = type.split("_")[0];
        switch (type){
            case "boolean":
                return OR(regC, regL, regR);
            case "vector":
                return VOR(regC, regL, regR);
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseXOR(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "boolean":
                return XOR(regC, regL, regR);
            case "vector":
                return VXOR(regC, regL, regR);
            default:
                throw new Error("No Type Found");
        }
    }


    public static ST ChooseAND(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "boolean":
                return AND(regC, regL, regR);
            case "vector":
                return VAND(regC, regL, regR);
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseNOT(int regC, int regV, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }
        type = type.split("_")[0];
        switch (type){
            case "boolean":
                return NOT(regC, regV);
            case "vector":
                return VNOT(regC, regV);
            default:
                throw new Error("No Type Found");
        }
    }



    //=============TEMPLATES FOR EACH LOGICAL OPERATION=============//
    public static ST VXOR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BVectorXOR");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST VOR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BVectorOR");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST VAND(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BVectorAND");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST VNOT(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BVectorNOT");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

    public static ST XOR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BXOR");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST OR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BOR");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST AND(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BAND");
        st.add("regC", regC);
        st.add("regL", regL);
        st.add("regR", regR);
        return st;
    }

    public static ST NOT(int regC, int regV){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BNOT");
        st.add("regC", regC);
        st.add("regV", regV);
        return st;
    }

}
