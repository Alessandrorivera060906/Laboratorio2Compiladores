grammar MiniLenguaje;

programa
    : declaracion* expresion EOF
    ;

declaracion
    : ID IGUAL valor
    ;

valor
    : TRUE
    | FALSE
    | NUMERO
    | CADENA
    ;

expresion
    : factor (AND factor)*
    ;

factor
    : ID
    | NOT PAREN_IZQ ID PAREN_DER
    ;

TRUE : 'true';
FALSE : 'false';
AND : 'and';
NOT : 'not';
IGUAL : '=';
PAREN_IZQ : '(';
PAREN_DER : ')';

NUMERO : [0-9]+;
CADENA : '"' (~["\r\n])* '"';
ID : [a-zA-Z_][a-zA-Z0-9_]*;

WS : [ \t\r\n]+ -> skip;

ERROR : .;