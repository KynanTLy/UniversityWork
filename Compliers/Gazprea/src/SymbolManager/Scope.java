package SymbolManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TN on 2016-10-08.
 */
public class Scope {

    private Map<String, Symbol> vars = new HashMap<String, Symbol>();
    private Scope parent;
    public static int scopeNumber = 0;


    public Scope() {
        scopeNumber = 0;
    }

    public Scope(Scope scope) {
        parent = scope;
        vars = new HashMap<String, Symbol>();
        scopeNumber++;
    }

    //=============SCOPE HELPER FUNCTIONS=====================//

    public Scope getParent() {
        return parent;
    }

    public boolean isGlobalScope() {
        return parent == null;
    }

    public void addToScope(String name, String type) {
        Symbol var = new Symbol(name, type);
        var.scope = this;
        var.scopeNumber = scopeNumber;
        vars.put(name, var);
    }

    public void addToScope(String name, String type, String specifiers) {
        Symbol var = new Symbol(name, type, specifiers);
        var.scope = this;
        var.scopeNumber = scopeNumber;
        vars.put(name, var);
    }

    public void addToScope(String name, String type, ArrayList<String> parameter,ArrayList<String> parametertype,  boolean forwardDeclare) {
        Symbol var = new Symbol(name, type, parameter,parametertype, forwardDeclare);
        var.scope = this;
        var.scopeNumber = scopeNumber;
        vars.put(name, var);
    }

     public void addToScope(String name, String type,  Map<String, TupleField> tuplefields) {
        Symbol var = new Symbol(name, type, tuplefields);
        var.scopeNumber = scopeNumber;
        vars.put(name, var);
    }

    public String getParameterSpecifiers(String name, Integer index){
        Symbol tmp = resolveName(name);
        return tmp.parameter.get(index);
    }

    public String getParameterType(String name, Integer index){
        Symbol tmp = resolveName(name);
        return tmp.parameterType.get(index);
    }

    public void printPara(String name){
        Symbol tmp = resolveName(name);
        for (int i = 0; i < tmp.parameter.size(); i++){
            System.out.println("Index " + i + " " + tmp.parameter.get(i));
        }
    }

    public boolean inCurrentScope(String name) {
        return vars.containsKey(name);
    }

    public Symbol resolveName(String name) {
       Symbol s = vars.get(name); // look in this scope
        if (s != null) return s; // return it if in this scope
        if ( parent != null ) { // have an enclosing scope?
            return parent.resolveName(name); // check enclosing scope
        }
        return null;
    }

    public boolean beenForwardDeclare(String name){
        Symbol s = vars.get(name); // look in this scope
        if (s != null){
            if(s.getForward()){
                return true;
            } else {
                return false;
            }
        }
        if ( parent != null ) { // have an enclosing scope?
            return parent.beenForwardDeclare(name); // check enclosing scope
        }
        return false;
    }

    public boolean isMatch(String name, ArrayList<String> parameter){
        Symbol s = vars.get(name); // look in this scope
        if (s != null){
            //System.out.println("Found the symbol name " + s.getName());
            //System.out.println("What is the parameter of s" + s.parameterType );
            //System.out.println("What is the parameter given " + parameter);
            if((s.parameterType.toString().contentEquals(parameter.toString()))){
                return true;
            } else {
                if(s.parameterType.size() != parameter.size()){
                    return false;
                }
                for(int i = 0; i < s.parameterType.size(); i++){
                    if(!(s.parameterType.get(i).equals(parameter.get(i))) ){
                        if(parameter.get(i).equals("integer") && s.parameterType.get(i).equals("real")){
                            continue;
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        if ( parent != null ) { // have an enclosing scope?
            return parent.isMatch(name, parameter); // check enclosing scope
        }
        return false;
    }


    public Symbol getSymbol(String name) {
        Symbol tmp = resolveName(name);
        return tmp;
    }

    public String getLLVMName (String name) {
        Symbol tmp = resolveName(name);
        return tmp.name;
    }

    public String getLLVMType (String name) {
        Symbol tmp = resolveName(name);
        return tmp.type;
    }

    public Map<String, TupleField> getTupleFields (String name) {
        Symbol tmp = resolveName(name);
        return tmp.tuplefields;
    }

    public Integer getTupleFieldIndex (String name, String fieldname) {
        Symbol tmp = resolveName(name);
        return tmp.tuplefields.get(fieldname).index;
    }

    public String getTupleFieldType (String name, String fieldname) {
        Symbol tmp = resolveName(name);
        return tmp.tuplefields.get(fieldname).type;
    }

    public Integer getScopeNumber(String name) {
        if(inCurrentScope(name)) {
            return vars.get(name).scopeNumber;
        }
        if ( parent != null) {
            return parent.getScopeNumber(name);
        }
        return null;
    }

    public Integer getVarCounter(String name) {
        Symbol tmp = resolveName(name);
        return tmp.counter;
    }
    //=============END SCOPE HELPER FUNCTIONS=====================//
}