/**
 * Created by kynan on 10/7/16.
 */


import java.util.*;

public class Scope {

    Scope enclosingScope;
    protected Map<String, Symbol> symbolTable;


    public Scope(Scope enclosingScope) {
        this.enclosingScope = enclosingScope;
        this.symbolTable =  new HashMap<String, Symbol>();
    }

    public void define( String name, String type, Object value) {
        Symbol symbol = new Symbol(name, type ,value);
        symbolTable.put(symbol.getName(), symbol);
    }

    public Symbol resolve(String name) {
        Symbol symbol = symbolTable.get(name);
        if (symbol != null) return symbol;
        if (enclosingScope != null) return enclosingScope.resolve(name);
        return null; // not found
    }

    public void setVar(String name, Object value){
        Symbol symbol = symbolTable.get(name);
        if (symbol != null){
            symbol.setValue(value);
            symbolTable.put(symbol.getName(), symbol);
        } else if (enclosingScope != null) {
            enclosingScope.setVar(name, value);
        }
    }//end setVar

    /** Where to look next for symbols */
    public Scope getEnclosingScope() {
        return enclosingScope;
    }


}


