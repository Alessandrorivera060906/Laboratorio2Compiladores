import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class Main {

    static class Variable {
        String nombre;
        String tipo;
        String valor;

        Variable(String nombre, String tipo, String valor) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.valor = valor;
        }
    }

    static Map<String, Variable> tablaSimbolos = new LinkedHashMap<>();
    static boolean errorLexico = false;
    static boolean errorSintactico = false;

    public static void main(String[] args) throws Exception {

        String[] pruebas = {
            // Entrada 1
            """
            x = true
            y = false
            x and y and not ( x )
            """,

            // Entrada 2
            """
            x = 1
            y = 0
            z = 20
            x and not ( y ) and z
            """,

            // Entrada 3
            """
            x = 1
            y = true
            x and y
            """,

            // Entrada 4
            """
            x = "Hola"
            x and x
            """,

            // Entrada 5: error sintactico
            """
            x = true
            y = false
            x and and y
            """,

            // Entrada 6: error lexico
            """
            x = true
            y = false
            x @ y
            """
        };

        for (int i = 0; i < pruebas.length; i++) {
            System.out.println("\n==============================");
            System.out.println("ENTRADA " + (i + 1));
            System.out.println("==============================");

            analizar(pruebas[i]);
        }
    }

    public static void analizar(String entrada) {
        tablaSimbolos.clear();
        errorLexico = false;
        errorSintactico = false;

        CharStream input = CharStreams.fromString(entrada);
        MiniLenguajeLexer lexer = new MiniLenguajeLexer(input);

        lexer.removeErrorListeners();

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniLenguajeParser parser = new MiniLenguajeParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(
                    Recognizer<?, ?> recognizer,
                    Object offendingSymbol,
                    int line,
                    int charPositionInLine,
                    String msg,
                    RecognitionException e) {

                errorSintactico = true;
                System.out.println(
                    "Error sintactico en linea " + line +
                    ", columna " + charPositionInLine +
                    ": " + msg
                );
            }
        });

        MiniLenguajeParser.ProgramaContext arbol = parser.programa();

        revisarErroresLexicos(tokens);

        if (errorLexico || errorSintactico) {
            return;
        }

        procesarDeclaraciones(arbol);
        imprimirTabla();
        evaluarExpresion(arbol.expresion());
    }

    public static void revisarErroresLexicos(CommonTokenStream tokens) {
        tokens.fill();

        for (Token token : tokens.getTokens()) {
            if (token.getType() == MiniLenguajeLexer.ERROR) {
                errorLexico = true;
                System.out.println("Error lexico: simbolo no valido '" + token.getText() + "'");
            }
        }
    }

    public static void procesarDeclaraciones(MiniLenguajeParser.ProgramaContext programa) {
        for (MiniLenguajeParser.DeclaracionContext declaracion : programa.declaracion()) {
            String nombre = declaracion.ID().getText();
            String valor = declaracion.valor().getStart().getText();
            String tipo = obtenerTipo(valor);

            tablaSimbolos.put(nombre, new Variable(nombre, tipo, valor));
        }
    }

    public static String obtenerTipo(String valor) {
        if (valor.equals("true") || valor.equals("false")) {
            return "boolean";
        }

        if (valor.matches("[0-9]+")) {
            return "int";
        }

        if (valor.startsWith("\"") && valor.endsWith("\"")) {
            return "string";
        }

        return "desconocido";
    }

    public static void imprimirTabla() {
        System.out.println("VARIABLE | TIPO | VALOR");

        for (Variable variable : tablaSimbolos.values()) {
            System.out.println(variable.nombre + " | " + variable.tipo + " | " + variable.valor);
        }
    }

    public static void evaluarExpresion(MiniLenguajeParser.ExpresionContext expresion) {
        String tipoBase = null;
        boolean resultado = true;

        for (MiniLenguajeParser.FactorContext factor : expresion.factor()) {
            String nombreVariable;
            boolean negar = false;

            if (factor.NOT() != null) {
                nombreVariable = factor.ID().getText();
                negar = true;
            } else {
                nombreVariable = factor.ID().getText();
            }

            if (!tablaSimbolos.containsKey(nombreVariable)) {
                System.out.println("Error semantico: La variable '" + nombreVariable + "' no ha sido declarada.");
                return;
            }

            Variable variable = tablaSimbolos.get(nombreVariable);

            if (variable.tipo.equals("string")) {
                System.out.println("Error semantico: Las variables de tipo string no estan permitidas en expresiones booleanas.");
                return;
            }

            if (tipoBase == null) {
                tipoBase = variable.tipo;
            } else if (!tipoBase.equals(variable.tipo)) {
                System.out.println("Error semantico: No se pueden combinar variables de distintos tipos en una misma expresion.");
                return;
            }

            boolean valorBooleano = convertirABooleano(variable);

            if (negar) {
                valorBooleano = !valorBooleano;
            }

            resultado = resultado && valorBooleano;
        }

        System.out.println("Resultado de la expresion: " + resultado);
    }

    public static boolean convertirABooleano(Variable variable) {
        if (variable.tipo.equals("boolean")) {
            return Boolean.parseBoolean(variable.valor);
        }

        if (variable.tipo.equals("int")) {
            return !variable.valor.equals("0");
        }

        return false;
    }
}