grammar Grammer;

prog
:generator* EOF
;

mathexpr
:   <assoc=right> mathexpr op='^' mathexpr  #POWER
|  mathexpr op=('*'|'/'|'%') mathexpr       #MULTDIV
|  mathexpr op=('+'|'-') mathexpr           #ADDSUB
|  '(' mathexpr ')'                         #BRACKET
|  '-'INT                                   #NEGINT
|  INT                                      #INT
|  Var                                      #Var
;

generator
:'[' Var 'in' min=INT '..' max=INT '|' mathexpr ']' ';' #Gen
;


INT: [0-9]+;
Var: CHAR(CHAR|INT)*;
STRING: CHAR+;
CHAR: [a-z|A-Z];
WS: (' '|'\r'|'\n'|'\t')+ -> skip;
POW : '^';
MUL : '*';
DIV : '/';
MOD : '%';
ADD : '+';
SUB : '-';