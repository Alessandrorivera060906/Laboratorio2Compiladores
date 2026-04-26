# Laboratorio 2 - Compiladores y ANTLR

Laboratorio realizado para el curso de Compiladores.

**Nombre:** Javier Alessandro Rivera Lemus  
**Carnet:** 1241224  

---

## Descripcion del laboratorio

Este proyecto implementa un compilador utilizando Java y ANTLR.

El lenguaje permite:

- Declarar variables.
- Manejar variables de tipo `int`, `boolean` y `string`.
- Generar una tabla de simbolos.
- Evaluar expresiones booleanas.
- Detectar errores lexicos.
- Detectar errores sintacticos.
- Detectar errores semanticos.

---

## Gramatica

La gramatica inicial del problema era:

```txt
S' -> E
E  -> E and F
E  -> F
F  -> id
F  -> not ( id )
```

---

## Gramatica extendida

Para resolver el laboratorio, la gramatica fue extendida para permitir declaraciones de variables y valores de distintos tipos.

```txt
programa    -> declaracion* expresion EOF

declaracion -> ID = valor

valor       -> true
valor       -> false
valor       -> NUMERO
valor       -> CADENA

expresion   -> factor (and factor)*

factor      -> ID
factor      -> not ( ID )
```

---

## Archivo ANTLR utilizado

La gramatica se transformo a un archivo valido de ANTLR llamado:

```txt
MiniLenguaje.g4
```

Contenido principal de la gramatica:

```antlr
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
```

---

## Archivos principales del laboratorio

```txt
Main.java
MiniLenguaje.g4
MiniLenguajeLexer.java
MiniLenguajeParser.java
MiniLenguajeListener.java
MiniLenguajeBaseListener.java
```

### Descripcion de archivos

| Archivo | Descripcion |
|---|---|
| `Main.java` | Programa principal. Ejecuta las pruebas, genera la tabla de simbolos, evalua expresiones y muestra errores. |
| `MiniLenguaje.g4` | Archivo de gramatica ANTLR. |
| `MiniLenguajeLexer.java` | Archivo generado por ANTLR para el analisis lexico. |
| `MiniLenguajeParser.java` | Archivo generado por ANTLR para el analisis sintactico. |
| `MiniLenguajeListener.java` | Listener generado por ANTLR. |
| `MiniLenguajeBaseListener.java` | Implementacion base del listener generada por ANTLR. |

---

## Tipos de datos soportados

El lenguaje soporta tres tipos de datos:

```txt
int
boolean
string
```

### Reglas de evaluacion

- Los valores `true` y `false` son tratados como booleanos.
- Los valores enteros pueden ser evaluados como booleanos.
- El numero `0` equivale a `false`.
- Cualquier numero diferente de `0` equivale a `true`.
- Las variables de tipo `string` no pueden participar en expresiones booleanas.
- No se pueden combinar variables de distinto tipo en una misma expresion booleana.

---

## Entradas de prueba

### Entrada 1

```txt
x = true
y = false
x and y and not ( x )
```

Salida esperada:

```txt
VARIABLE | TIPO | VALOR
x | boolean | true
y | boolean | false
Resultado de la expresion: false
```

---

### Entrada 2

```txt
x = 1
y = 0
z = 20
x and not ( y ) and z
```

Salida esperada:

```txt
VARIABLE | TIPO | VALOR
x | int | 1
y | int | 0
z | int | 20
Resultado de la expresion: true
```

---

### Entrada 3

```txt
x = 1
y = true
x and y
```

Salida esperada:

```txt
VARIABLE | TIPO | VALOR
x | int | 1
y | boolean | true
Error semantico: No se pueden combinar variables de distintos tipos en una misma expresion.
```

---

### Entrada 4

```txt
x = "Hola"
x and x
```

Salida esperada:

```txt
VARIABLE | TIPO | VALOR
x | string | "Hola"
Error semantico: Las variables de tipo string no estan permitidas en expresiones booleanas.
```

---

### Entrada 5 - Error sintactico

```txt
x = true
y = false
x and and y
```

Salida esperada:

```txt
Error sintactico en linea 3, columna 6: extraneous input 'and' expecting {'not', ID}
```

---

### Entrada 6 - Error lexico

```txt
x = true
y = false
x @ y
```

Salida esperada:

```txt
Error lexico: simbolo no valido '@'
```

---

## Comandos utilizados

### Generar archivos de ANTLR

```bash
antlr4 MiniLenguaje.g4
```

Este comando genera los archivos necesarios para el lexer, parser y listener.

---

### Compilar el laboratorio

```bash
javac -cp ".;$antlr" *.java
```

---

### Ejecutar el laboratorio

```bash
java -cp ".;$antlr" Main
```

---


