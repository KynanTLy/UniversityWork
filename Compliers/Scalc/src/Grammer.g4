grammar Grammer;

prog
:state* EOF
;

state
: IF '(' mathexpr ')' state* 'fi' ';'           #IF
| LOOP '(' mathexpr ')' state* 'pool' ';'       #LOOP
| PRINT '(' mathexpr ')' ';'                    #PRINT
| assignment                                    #ASSIGNMENT
| ID '=' mathexpr ';'                           #ASSIGN
;

mathexpr
:  mathexpr op=('*'|'/') mathexpr               #MULTDIV
|  mathexpr op=('+'|'-') mathexpr               #ADDSUB
|  mathexpr '[' mathexpr ']'                    #INDEX
|  '(' mathexpr ')'                             #BRACKETME
|  intexpr                                      #MEINT
|  vecexpr                                      #MEVEC
|  mathexpr '..' mathexpr                       #RANGE
|  ID                                           #REID
;

compare
:  mathexpr op=('<'|'>') mathexpr               #GRTLES
|  mathexpr op=('=='|'!=') mathexpr             #EQNEQ
;

intexpr
:  '(' INT ')'                                  #BRACKETINT
|  INT                                          #INT
;



vecexpr
: genfil                                        #GENFIL
| '(' vector ')'                                #BRACKETVEC
| vector                                        #VEC
;

assignment
: typecast='vector' ID ('=' mathexpr)? ';'       #INITV
| typecast='int' ID ('=' mathexpr)? ';'          #INITI
;


genfil
: '[' ID 'in' mathexpr '&' compare ']'          #FILTER
| '[' ID 'in' mathexpr '|' mathexpr ']'         #GEN
;

vector
: '[' INT* ']';

MUL : '*';
DIV : '/';
ADD : '+';
SUB : '-';
LES : '<';
GRT : '>';
EQU : '==';
NEQ : '!=';
IF  : 'if';
LOOP: 'loop';
PRINT: 'print';
INT: [0-9]+;
ID: CHAR(CHAR|INT)*;
STRING: CHAR+;
CHAR: [a-z|A-Z];
WS: (' '|'\r'|'\n'|'\t')+ -> skip;

/*
:  vcompare                                     #VCOMPARE
|  icompare                                     #ICOMPARE
;

vcompare
:  vecexpr op=('<'|'>') vecexpr                 #VGRTLES
|  vecexpr op=('=='|'!=') vecexpr               #VEQNEQ
;

icompare
:  intexpr op=('<'|'>') intexpr                 #IGRTLES
|  intexpr op=('=='|'!=')intexpr                #IEQNEQ
;
*/