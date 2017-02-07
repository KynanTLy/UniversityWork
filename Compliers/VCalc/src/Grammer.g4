grammar Grammer;

prog
:state* EOF
;

state
: IF '(' mathexpr ')' state* 'fi' ';'            #IF
| LOOP '(' mathexpr ')' state* 'pool' ';'        #LOOP
| PRINT '(' mathexpr ')' ';'                    #PRINT
| assignment                                    #ASSIGNMENT
| ID '=' mathexpr ';'                           #ASSIGN
;

mathexpr
:  mathexpr '..' mathexpr                       #RANGE
|  mathexpr '[' mathexpr ']'                    #INDEX
|  mathexpr op=('*'|'/') mathexpr               #MULTDIV
|  mathexpr op=('+'|'-') mathexpr               #ADDSUB
|  mathexpr op=('<'|'>') mathexpr               #GRTLES
|  mathexpr op=('=='|'!=') mathexpr             #EQNEQ
|  '(' mathexpr ')'                             #BRACKETME
|  vecexpr                                      #MEVEC
|  ID                                           #REID
|  intexpr                                      #MEINT
;


intexpr
:  '(' INT ')'                                  #BRACKETINT
|  INT                                          #INT
;



vecexpr
: genfil                                        #GENFIL
| vecexpr '[' mathexpr ']'                      #VECINDEX
| '(' vector ')'                                #BRACKETVEC
| vector                                        #VEC
;

assignment
: typecast='vector' ID ('=' mathexpr)? ';'       #INITV
| typecast='int' ID ('=' mathexpr)? ';'          #INITI
;


genfil
: '[' ID 'in' mathexpr '&' mathexpr ']'          #FILTER
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
