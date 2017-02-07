grammar Grammer;

prog
:state* EOF
;

mathexpr
:  mathexpr op=('*'|'/') mathexpr           #MULTDIV
|  mathexpr op=('+'|'-') mathexpr           #ADDSUB
|  mathexpr op=('<'|'>') mathexpr           #GRTLES
|  mathexpr op=('=='|'!=') mathexpr         #COMPARE
|  '(' mathexpr ')'                         #BRACKET
|  INT                                      #INT
|  ID                                       #ID
;

state
: type='int' ID ('=' mathexpr)? ';'         #INIT
| IF '(' mathexpr ')' state* 'fi' ';'       #IF
| LOOP '(' mathexpr ')' state* 'pool' ';'   #LOOP
| PRINT '(' mathexpr ')' ';'                #PRINT
| ID '=' mathexpr ';'                       #ASSIGN
;


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