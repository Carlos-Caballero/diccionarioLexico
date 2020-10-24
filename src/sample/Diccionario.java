package sample;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Diccionario {
    Hashtable<String,String> dic = new Hashtable<>();

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
    }

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
        p = Pattern.compile("^[A-Za-z]+");
        m = p.matcher(entrada);
        if (m.find()){
            if(entrada.matches("[a-zA-Z0-9]+")) return "Variable";
        }

        //valida que sea string
        p = Pattern.compile("^\"");
        m = p.matcher(entrada);
        if (m.find()){
            int ultimo = entrada.length()-1;
            if(entrada.charAt(ultimo)=='\"' && entrada.length()>=2) return "Cadena";
        }
        return null;
    }

    public boolean existe(String entrada){
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
        p = Pattern.compile("^[A-Za-z]+");
        m = p.matcher(entrada);
        if (m.find()){
            if(entrada.matches("[a-zA-Z0-9]+")) return true;
        }

        //valida que sea string
        p = Pattern.compile("^\"");
        m = p.matcher(entrada);
        if (m.find()){
            int ultimo = entrada.length()-1;
            if(entrada.charAt(ultimo)=='\"' && entrada.length()>=2) return true;
        }

        return false;
    }

    private static boolean isValidDouble(String s) {
        final String Digits     = "(\\p{Digit}+)";
        final String HexDigits  = "(\\p{XDigit}+)";
        final String Exp        = "[eE][+-]?"+Digits;
        final String fpRegex    =
                ("[\\x00-\\x20]*"+
                        "[+-]?(" +
                        "NaN|" +
                        "Infinity|" +
                        "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+
                        "(\\.("+Digits+")("+Exp+")?)|"+
                        "((" +
                        "(0[xX]" + HexDigits + "(\\.)?)|" +
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +
                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");
        return Pattern.matches(fpRegex, s);
    }
}