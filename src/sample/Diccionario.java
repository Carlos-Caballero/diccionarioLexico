package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Diccionario {
    Hashtable<String,String> dic = new Hashtable<>();
    ArrayList<String> lista = new ArrayList<>();

    public Diccionario(){
        dic.put("(","Simbolo de agrupacion (apertura)");
        dic.put(")","Simbolo de agrupacion (cierre)");
        dic.put("{","Simbolo de agrupacion (apertura)");
        dic.put("}","Simbolo de agrupacion (cierre)");
        dic.put("<","Simbolo relacional (menor que)");
        dic.put(">","Simbolo relacional (mayor que)");
        dic.put("==","Simbolo relacional (igual a)");
        dic.put("!=","Simbolo relacional (diferente de)");
        dic.put("&","Operador logico (and)");
        dic.put("|","Operador logico (or)");
        dic.put(";","Simbolo de terminacion de linea de codigo");
        dic.put("!","Simbolo de negacion");
        dic.put("<=","Simbolo relacional (menor o igual que)");
        dic.put(">=","Simbolo relacional (mayor o igual que)");
        dic.put("/","Operador matematico (division)");
        dic.put("*","Operador matematico (multiplicacion)");
        dic.put("-","Operador matematico (resta)");
        dic.put("+","Operador matematico (suma)");
        dic.put("=","Simbolo de asignacion");

        lista.add(" ");
        lista.add("\"");
        lista.add("\\+");
        lista.add("\\(");
        lista.add("\\)");
        lista.add("\\{");
        lista.add("\\}");
        lista.add("\\<");
        lista.add("\\>");
        lista.add("\\&");
        lista.add("\\|");
        lista.add("\\;");
        lista.add("\\!");
        lista.add("\\/");
        lista.add("\\*");
        lista.add("\\-");
        lista.add("\\+");
        lista.add("\\=");
    };




    public ArrayList<String> getStaticKeys(){
        ArrayList<String> lista = new ArrayList<>();

        Enumeration<String> elements = dic.keys();

        while(elements.hasMoreElements()) {
            lista.add(elements.nextElement());
        }

        return lista;
    }

    public ArrayList<String> getDescriptions(){
        ArrayList<String> lista = new ArrayList<>();

        Enumeration<String> elements = dic.elements();

        while(elements.hasMoreElements()) {
            lista.add(elements.nextElement());
        }

        lista.add("Entero");
        lista.add("Double");
        lista.add("Comentario");
        lista.add("Variable");
        lista.add("Cadena");

        return lista;
    }

    public String getDescriptionOf(String entrada){
        String valor = dic.get(entrada);
        boolean existe = false;
        //valida que exista la entrada
        if (dic.containsKey(entrada)) return dic.get(entrada);

        //valida que sea entero
        if (entrada.matches("[0-9]+")) return "Entero";

        //valida que sea double
        if(isValidDouble(entrada)) return "Double";


        //valida que sea comentario
        Pattern p = Pattern.compile("^//");
        Matcher m = p.matcher(entrada);
        if (m.find()) return "Comentario";

        //valida que sea variable
        boolean inicio = false;
        p = Pattern.compile("^[A-Za-z]+");
        m = p.matcher(entrada);
        if (m.find()) inicio = true;
        //p = Pattern.compile(" ");
        //m = p.matcher(entrada);
        int contador = 0;
        boolean simbolo = false;
        while (contador < lista.size()){
            p = Pattern.compile(lista.get(contador));
            m = p.matcher(entrada);
            if(m.find()){
                simbolo = true;
                contador = lista.size();
            }
            contador++;
        }

        if (inicio && !simbolo) return "Variable";

        //valida que sea string
        p = Pattern.compile("^\"");
        m = p.matcher(entrada);
        if (m.find()){
            int ultimo = entrada.length()-1;
            if(entrada.charAt(ultimo)=='\"' && entrada.length()>=2) return "Cadena";
        };

        return null;
    }

    public boolean existe(String entrada){
        String valor = dic.get(entrada);
        boolean existe = false;
        //valida que exista la entrada
        if (dic.containsKey(entrada)) return true;

        //valida que sea entero
        if (entrada.matches("[0-9]+")) return true;

        //valida que sea double
        if(isValidDouble(entrada)) return true;

        //valida que sea comentario
        Pattern p = Pattern.compile("^//");
        Matcher m = p.matcher(entrada);
        if (m.find()) return true;

        //valida que sea variable
        boolean inicio = false;
        p = Pattern.compile("^[A-Za-z]+");
        m = p.matcher(entrada);
        if (m.find()) inicio = true;
        //p = Pattern.compile(" ");
        //m = p.matcher(entrada);
        int contador = 0;
        boolean simbolo = false;
        while (contador < lista.size()){
            p = Pattern.compile(lista.get(contador));
            m = p.matcher(entrada);
            if(m.find()){
                simbolo = true;
                contador = lista.size();
            }
            contador++;
        }

        if (inicio && !simbolo) return true;

        //valida que sea string
        boolean fin;
        p = Pattern.compile("^\"");
        m = p.matcher(entrada);
        if (m.find()){
            int ultimo = entrada.length()-1;
            if(entrada.charAt(ultimo)=='\"' && entrada.length()>=2) return true;
        };

        return existe;
    }

    private static boolean isValidDouble(String s) {
        final String Digits     = "(\\p{Digit}+)";
        final String HexDigits  = "(\\p{XDigit}+)";
        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp        = "[eE][+-]?"+Digits;
        final String fpRegex    =
                ("[\\x00-\\x20]*"+  // Optional leading "whitespace"
                        "[+-]?(" + // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix
                        //
                        // Since this method allows integer-only strings as input
                        // in addition to strings of floating-point literals, the
                        // two sub-patterns below are simplifications of the grammar
                        // productions from section 3.10.2 of
                        // The Java Language Specification.

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.("+Digits+")("+Exp+")?)|"+

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        return Pattern.matches(fpRegex, s);
    }

}
