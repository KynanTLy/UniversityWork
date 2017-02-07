import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Created by kynan on 11/3/16.
 */
public class MathCMPHelper {

    public static ST ChooseLes(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "integer":
                return (LessII(regC,regL,regR));
            case "real":
                return (LessRR(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (VLESII(regC, regL, regR));
                    case "real":
                        return (VLESFF(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");

        }
    }

    public static ST ChooseGre(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "integer":
                return (GreII(regC,regL,regR));
            case "real":
                return (GreRR(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (VGRTII(regC, regL, regR));
                    case "real":
                        return (VGRTFF(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");

        }
    }

    public static ST ChooseNeq(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "integer":
                return (NeqII(regC,regL,regR));
            case "real":
                return (NeqRR(regC,regL,regR));
            case "boolean":
                return (BoolNEQ(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (VNEQII(regC, regL, regR));
                    case "real":
                        return (VNEQFF(regC, regL, regR));
                    case "boolean":
                        return (VNEQBool(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }

    }

    public static ST ChooseEq(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "integer":
                return (EqII(regC,regL,regR));
            case "real":
                return (EqRR(regC,regL,regR));
            case "boolean":
                return (BoolEQ(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (VEQII(regC, regL, regR));
                    case "real":
                        return (VEQFF(regC, regL, regR));
                    case "boolean":
                        return (VEQBool(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseGeq(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "integer":
                return (GeqII(regC,regL,regR));
            case "real":
                return (GeqRR(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (VGEQII(regC, regL, regR));
                    case "real":
                        return (VGEQFF(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseLeq(int regC, int regL, int regR, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];
        switch (type){
            case "integer":
                return (LeqII(regC,regL,regR));
            case "real":
                return (LeqRR(regC,regL,regR));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (VLEQII(regC, regL, regR));
                    case "real":
                        return (VLEQFF(regC, regL, regR));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseUnaryP(int regC, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "integer":
                return (UnpII(regC));
            case "real":
                return (UnpRR(regC));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (UnPIntegerVectors(regC));
                    case "real":
                        return (UnPRealVectors(regC));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    public static ST ChooseUnaryN(int regC, String type){
        String vectorType = null;
        if (type.contains("_")) {
            vectorType = type.split("_")[1];
        }

        type = type.split("_")[0];

        switch (type){
            case "integer":
                return (UnnII(regC));
            case "real":
                return (UnnRR(regC));
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return (UnNIntegerVectors(regC));
                    case "real":
                        return (UnNRealVectors(regC));
                    default:
                        throw new Error("No Type Found");
                }
            default:
                throw new Error("No Type Found");
        }
    }

    //===============COMPARASION==============================/

    public static ST VLESFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("FVectorLES");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VGRTFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("FVectorGRT");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VGEQFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("FVectorGRTEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VLEQFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("FVectorLESEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VLESII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IVectorLES");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VGRTII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IVectorGRT");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VGEQII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IVectorGRTEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VLEQII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IVectorLESEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VEQBool(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BoolVectorsEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VNEQBool(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BoolVectorsNEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VEQII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IntegerVectorsEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VEQFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RealVectorsEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VNEQII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IntegerVectorsNEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST VNEQFF(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RealVectorsNEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST UnpRR(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RUNARYPLUS");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST UnnRR(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RUNARYNEG");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST UnpII(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IUNARYPLUS");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST UnNIntegerVectors(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("UnNIntegerVectors");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST UnNRealVectors(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("UnNRealVectors");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST UnPIntegerVectors(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("UnPIntegerVectors");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST UnPRealVectors(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("UnPRealVectors");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST UnnII(int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IUNARYNEG");
        st.add("regC", regC);
        st.add("regP", regC-1);
        return st;
    }

    public static ST BoolEQ(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST BoolNEQ(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("BNEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST LessII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ILES");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST GreII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IGRE");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST NeqII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("INEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST EqII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST GeqII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("IGEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST LeqII(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("ILEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST LessRR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RLES");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST GreRR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RGRE");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST NeqRR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RNEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST EqRR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("REQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST GeqRR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RGEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

    public static ST LeqRR(int regC, int regL, int regR){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("RLEQ");
        st.add("regL", regL);
        st.add("regR", regR);
        st.add("regC", regC);
        return st;
    }

}
