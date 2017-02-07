package SymbolManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianzhi on 11/8/16.
 */
public class Symbol {
    public String type;
    public String name;
    public Integer counter;
    public Scope scope;
    public int scopeNumber;
    public ArrayList<String> parameter= new ArrayList<String>();
    public ArrayList<String> parameterType= new ArrayList<String>();
    public String specifiers;
    public boolean forwardDeclare;

    public Symbol(String name) { this.name = name; }

    public String getName() { return name; }

    public boolean getForward(){return forwardDeclare;}

    public Symbol ( String name_, String type_) {
        type = type_;
        name = name_;
    }

    public Symbol ( String name_, String type_, String specifiers_) {
        type = type_;
        name = name_;
        specifiers = specifiers_;
    }

    public Symbol ( String name_, String type_, ArrayList<String> parameter_, ArrayList<String> parameterType_, boolean forwardDeclare_) {
        type = type_;
        name = name_;
        for(int i = 0; i < parameter_.size(); i++){
            parameter.add(parameter_.get(i));
        }
        forwardDeclare = forwardDeclare_;
        for(int i = 0; i < parameter_.size(); i++){
            parameterType.add(parameterType_.get(i));
        }
    }

    Map<String, TupleField> tuplefields= new HashMap<String, TupleField>();
    public Symbol ( String name_, String type_,  Map<String, TupleField> tuplefields_) {
        type = type_;
        name = name_;
        tuplefields = tuplefields_;
    }
}