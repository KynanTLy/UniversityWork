grammar Grammar;

//==========FRAGMENTS==========//
fragment DIGIT: [0-9];
fragment ALPHA: [a-zA-Z];
fragment DOT: '.';

//==========COMMENTS==========//
BlockComment:   '/*' .*? '*/'-> skip;
LineComment:   '//' ~[\r\n]*-> skip;

//==========MATH_OPERATIONS==========//
MUL :   '*';
DIV :   '/';
MOD :   '%';
ADD :   '+';
SUB :   '-';
EXPON : '^';
LES :   '<';
LESEQ:  '<=';
GRT :   '>';
GRTEQ:  '>=';
EQU :   '==';
NEQ :   '!=';
NOT :   'not';
AND :   'and';
OR  :   'or';
XOR :   'xor';
CONCAT:   '||';


//=========ASSIGNMENT===============//
SPECIFIERS : ('var'|'const');
TYPE : ('integer'|'real'|'integer interval'|'boolean'|'string'|'character');
IO: ('std_output()'|'std_input()');

//=========STATEMENT_TYPE==========//
IF  : 'if';
LOOP: 'loop';
PRINT: 'print';
VECTOR: 'vector';
WS: (' '|'\r'|'\n'|'\t')+ -> skip;

//==========DATA_TYPES========//
BOOL : 'true'|'false';
ID: (ALPHA|'_') (ALPHA|INT|'_')*; //TODO include other symbols
INT: DIGIT (DIGIT|'_')*;


REAL: (INT|'.') ('.'|'_'|INT)* ('e' ('_')* ('+'|'-')?)? ('_'|INT)* ('.')? INT*;
STRING: '"'(SPECIALCHAR|.)*? '"';
CHAR: '\'' (SPECIALCHAR|.)?? '\'';

NULLDENT: 'null'
| 'identity'
;


//=========SPECIAL_CHARACTERS========//
SPECIALCHAR : (BELL|BACKSLASH|BACKSLASH|LINEFEED|CARRIAGERETURN|TAB|BACKSLASH|APOSTROPHE|QUOTATIONMARK|NULL);
BELL:						'\\a';
BACKSPACE :					'\\b';
LINEFEED :					'\\n';
CARRIAGERETURN :			'\\r';
TAB :					    '\\t';
BACKSLASH :					'\\\\';
APOSTROPHE :				'\\’';
QUOTATIONMARK :				'\\"';
NULL :					    '\\0';

//==========STATEMENT PARSER===================//
prog
:state* EOF
;

state
: block
| assignment
| profunc
| loopstate
| ifstate
| comment
| stream
| returnstate
;

block
: '{' state* '}'                                                        #BLOCK
;

ifloopblock
: '{' state* '}'
| state
;

functionblock
: '{' state* '}'
| state
| profunOneLine
;

profunOneLine
: '=' mathexpr ';'
;

ifstate
: 'if' '('? mathexpr ')'? ifloopblock                                   #IF
| 'if' '('? mathexpr ')'? ifloopblock 'else' ifloopblock                #IFELSE
;

loopstate
: 'loop' ifloopblock                                                    #INDEFLOOP
| 'loop' 'while' mathexpr ifloopblock                                   #FRONTLOOP
| 'loop' ifloopblock 'while' mathexpr ';'                               #BACKLOOP
| cond=('break'|'continue') ';'                                         #BREAKCONTINUE
;

stream
: (mathexpr)'->' (ID|IO) ';'                                                      #PRINTEXPR
| (STRING|CHAR) '->' (ID|IO) ';'                                                  #PRINTSTRINGCHAR
| ID  '<-' (mathexpr| type=(STRING|CHAR) ) ';'                               #STDIN
| SPECIFIERS? TYPE ID '<-' (mathexpr| type=(STRING|CHAR) ) ';'               #ASSIGNSTDIN
;


//========FUNCTION RELATED PARSER==============//


profunc
: method=('procedure'|'function') ID argumentProList ('returns' (TYPE|tupleReturn))? functionblock                 #PROFUNDECLARE
| method=('procedure'|'function') ID argumentProList ('returns' (TYPE|tupleReturn))? ';'                           #PRODDUNFORWARDDECLARE
| 'call' ID '(' parameterArg? (',' parameterArg)* ')' ';'                                            #PROCALL
;

returnstate
: 'return' mathexpr ';'                                                         #RETURNSTATEMENT
;

argumentProList
: '(' ')'                           #EMPARGLPRO
| '(' argumentPro ')'               #ARGLPRO
;

argumentPro
: arguementElement? (',' arguementElement)*                                            #ARGUMENTPRO
;

arguementElement
: SPECIFIERS? TYPE (VECTOR)? ID                                                         #ARGMUMENTELEMENT
| 'tuple' '(' tupleArgs ')' ID                                                          #ARGMUMENTTUPLE
;

tupleReturn
: 'tuple' '(' tupleArgs ')'
;

//==========DECLARE PARSER==================//

//==========ASSIGNEMENT PARSER==================//

assignment
: SPECIFIERS ID '=' mathexpr ';'                                                        #VAGUEASSIGN
| ID '=' mathexpr ';'                                                                   #REDEFASSIGN
| SPECIFIERS ID '=' IO ';'                                                              #IOASSIGN
| SPECIFIERS ID '=' tupleLit ';'                                                        #VAGUETUPLEASSIGN
| SPECIFIERS? TYPE ID ('=' mathexpr)? ';'                                               #BASICASSIGN
| TYPE ID ('=' STRING)? ';'                                                             #STRINGASSIGN
| TYPE ID ('=' (CHAR|NULLDENT))? ';'                                                    #CHARASSIGN
| 'tuple' '(' tupleArgs ')' ID '=' tupleLit ';'                                         #TUPLEASSIGN
| SPECIFIERS? TYPE? primaryType=('vector'|'interval')? ID (('[' size=mathexpr ']')|('[*]'))? ('=' (value=mathexpr))? ';'     #VECTORASSIGN
| TUPLEACCESS '=' mathexpr ';'                                                                      #TUPLEFIELDASSIGN
| SPECIFIERS? TYPE? 'matrix'  ID ('[' (mathexpr|'*') ',' (mathexpr|'*') ']')? ('=' (mathexpr))? ';' #MATRIXDECLARE
| ID '[' mathexpr ']' '=' mathexpr ';'                                                         #VECTORINDEXASSIGN
;

parameterArg
: (paraID|mathexpr)
;

paraID
: ID
;
//=========MATH RELATED PARSER====================//

mathexpr
:  '(' mathexpr ')'                                                                     #BMATHEXPR
|  <assoc=right> op=('+'|'-'|'not') mathexpr                                            #MATHUNARY
|  <assoc=right> mathexpr  op='^'  mathexpr                                             #MATHEXPON
|  mathexpr  op='**' mathexpr                                                           #MATHDOT
|  mathexpr  op=('*'|'/'|'%') mathexpr                                                  #MATHMDM
|  mathexpr  op=('+'|'-') mathexpr                                                      #MATHAS
|  mathexpr  op=('<'|'<='|'>'|'>=') mathexpr                                            #MATHCOMARE
|  mathexpr  op=('=='|'!=') mathexpr                                                    #MATHEQNEQ
|  mathexpr  op='and' mathexpr                                                          #MATHAND
|  mathexpr  op=('or'|'xor') mathexpr                                                   #MATHORXOR
|  <assoc=right> mathexpr op='||' mathexpr                                              #MATHCONCAT
|  ('not'|'+'|'-')*  ID '(' parameterArg? (',' parameterArg)* ')'                       #MATHFUNCTIONR
|  'as' '<' TYPE '>' '(' mathexpr ')'                                                   #MATHCAST
|  builtinmethod                                                                        #BUILTIN
|  mathexpr '[' mathexpr ']'                                                            #VECTORATOM
|  vectorLit                                                                            #MATHVECTORLIT
// Preprocessing replaces .. with $$
|  mathexpr '$$' mathexpr                                                               #MATHINTERVAL
|  atom                                                                                 #MATHATOM

;

builtinmethod
: 'length' '(' mathexpr ')'                                                            #BUILTINLENGTH
| 'rows' '(' mathexpr ')'                                                              #BUILTINROWS
| 'columns' '(' mathexpr ')'                                                           #BUILTINCOLS
| 'reverse' '(' mathexpr ')'                                                           #BUILTINREV
| 'strean_state' '(' ID ')'                                                            #BUILTINSTATE
;

//==========ATOM/DATA TYPE=============//

//vectorLit
//: '[' ']' #emptyVectorLit
//| '[' (mathexpr) (',' mathexpr)* ']' #nonEmptyVectorLit
////LATER TO INCLUDE RANGE ETC
//;

vectorLit
: '[' ((mathexpr|vectorLit) (',' mathexpr|vectorLit)*)* ']'
;


//matrixLit
//: '[' (vectorLit) (',' vectorLit)* ']'
//;

tupleArgs
:  tupleSign (',' ( tupleSign ))+
;

tupleSign
: TYPE ('[' mathexpr ']')? (ID)?
;

tupleLit
: '(' (mathexpr) (',' (mathexpr))+ ')'
;

TUPLEACCESS
: ID '.' INT
| ID '.' ID;


atom
: INT           #INT
| TUPLEACCESS   #TUPLEACCESS
| REAL          #REAL
| '(' atom ')'  #BATOM
| ID            #ID
| BOOL          #BOOL
| 'null'        #NULL
| 'identity'    #IDENTITY
| STRING        #STRING
| CHAR          #CHAR
;


//==========COMMENTS=========//
comment
: BlockComment
| LineComment
;

