/* ================================================================== *
    
    Universidade Federal de Sao Carlos - UFSCar, Sorocaba

    Disciplina: Compiladores
    Prof. Tiemi Christine Sakata

    Compilador 0-3

    Autor: Giulianno Raphael Sbrugnera RA: 408093
    Autor: Matheus Casarin Paez        RA: 438308
 
 * ================================================================== */

import ast.*;
import lexer.*;

import java.io.*;
import java.util.*;

public class Compiler {

	private Lexer lexer;
    private char []input;
    private Hashtable<Character, Variable> symbolTable;
	
    public Program compile(char []p_input, FileOutputStream outputStream) {
        input = p_input;
 		symbolTable = new Hashtable<Character, Variable>();
 		lexer = new Lexer(input);
 		
 		lookForVariables();

        lexer.nextToken();
        
//        System.out.println("Compile method, after calling lookForVariables method.");
// 		System.out.println("Token: " + lexer.token);
//        System.out.println("input[" + lexer.tokenPos + "]: " + input[lexer.tokenPos]);

        Program e = program();
        
        if (lexer.tokenPos > input.length) {
            lexer.error("Final do arquivo não definido corretamente.");
        }

        return e;
    }
    
    // Metodo que percorre toda a entrada. Quando encontra uma variavel,
    // ela eh adicionada a tabela hash de variaveis.
    // Ao final do metodo, a posicao do token eh setada para zero.
    private void lookForVariables() {
    	Variable variable = null;
    	boolean var;
    	
    	while (lexer.tokenPos < input.length) {
    		var = true;
    		lexer.nextToken();
    		
    		// Necessario verificar se a letra nao faz parte do comando
			// 'write'. Pela gramatica, uma variavel eh composta por
    		// apenas uma letra, logo nao deve haver letras antes ou
    		// depois da letra da variavel.
    		if (Character.isLetter(lexer.token)) {
    			if (lexer.tokenPos - 2 >= 0) {
    				if (Character.isLetter(input[lexer.tokenPos - 2])) {
    					var = false;
    				}
    			}
    			if (Character.isLetter(input[lexer.tokenPos])) {
    				var = false;
    			}
    			
    			if (var) {
					// variavel
    				variable = new Variable(lexer.token);
    				symbolTable.put(lexer.token, variable);
    			}
    		}
    	}
    	
    	lexer.tokenPos = 0;
    }

    // Program ::= {Line}
    private Program program() {
        Line line = null;
        ArrayList lines = null;
        
//    	while (((input[lexer.tokenPos - 1] == 'w') && 
//    			(input[lexer.tokenPos] == 'r') && 
//    			(input[lexer.tokenPos + 1] == 'i') && 
//    			(input[lexer.tokenPos + 2] == 't') && 
//    			(input[lexer.tokenPos + 3] == 'e')) || 
//    			(Character.isLetter(lexer.token))) {
        while (input[lexer.tokenPos] != '\0') {
//    		System.out.println("Program method.");
    		line = line();

            if (lines == null) {
                lines = new ArrayList<Line>();
            }
            
            lines.add(line);

//            System.out.println("Token: " + lexer.token);
//            System.out.println("input[" + lexer.tokenPos + "]: " + input[lexer.tokenPos]);
            
            if (lexer.token == ';') {
//            	System.out.println("nextToken chamado.");
                lexer.nextToken();
            }
//            if (lexer.token == '\n') {
//            	System.out.println("TOKEN COM \\N!!!");
//            }
//            System.out.println("Token: " + lexer.token);
//            System.out.println("input[" + lexer.tokenPos + "]: " + input[lexer.tokenPos]);

            if (lexer.tokenPos >= input.length) {
//            	System.out.println("BLABLABLA.\n");
                break;
            }
        }

    	return new Program(lines, symbolTable);
    }

    // Line ::= (Write | Atrib) ';'
    private Line line() {
        Write write = null;
        Atrib atrib = null;

//        System.out.println("Line method.\n");
        if ((input[lexer.tokenPos - 1] == 'w') && (input[lexer.tokenPos] == 'r') && (input[lexer.tokenPos + 1] == 'i') && (input[lexer.tokenPos + 2] == 't') && (input[lexer.tokenPos + 3] == 'e')) {
            // comando write
            write = write();

            if (lexer.token != ';') {
                lexer.error("';' esperado.");
            }
        }
        else {
            if (Character.isLetter(lexer.token)) {
                // atribuicao
                atrib = atrib();

                if (lexer.token != ';') {
                    lexer.error("';' esperado.");
                }
            }
            else {
                lexer.error("Variável esperada na atribuição.");
            }
        }

        return new Line(write, atrib);
    }

    // Write ::= 'Write' '(' expr ')'
    private Write write() {
        Expr expr = null;
        
        lexer.nextToken();
        lexer.nextToken();
        lexer.nextToken();
        lexer.nextToken();
        lexer.nextToken();

        if (lexer.token == '(') {
            lexer.nextToken();

            if ((Character.isLetter(lexer.token)) || (Character.isDigit(lexer.token))) {
                expr = expr();
            }
            else {
                lexer.error("Caractere inválido. Variável ou número esperado.");
            }

            if (lexer.token != ')') {
                lexer.error("')' esperado.");
            }
            
            lexer.nextToken();

            return new Write(expr);
        }
        else {
            lexer.error("'(' esperado.");
        }

        return new Write(expr);
    }

    // Atrib ::= var '=' expr
    private Atrib atrib() {
        char var = 0;
        Expr expr = null;

        if (Character.isLetter(lexer.token)) {
            var = var();
        }
        else {
            lexer.error("Variável esperada na atribuição.");
        }

        if (lexer.token == '=') {
            lexer.nextToken();
        }
        else {
            lexer.error("'=' esperado.");
        }

        if ((Character.isLetter(lexer.token)) || (Character.isDigit(lexer.token))) {
            expr = expr();
        }
        else {
            lexer.error("Caractere inválido. Variável ou número esperado.");
        }

        return new Atrib(var, expr);
    }

    // expr ::= t ei
    private Expr expr() {
        T t = null;
        Ei ei = null;

        if ((Character.isLetter(lexer.token)) || (Character.isDigit(lexer.token))) {
            t = t();
        }
        else {
            lexer.error("Caractere inválido. Variável ou número esperado.");
        }

        if (lexer.token == '+') {
            ei = ei();
        }

        return new Expr(t, ei);
    }

    // ei ::= '+' t ei |
    private Ei ei() {
        T t = null;
        Ei ei = null;

        if (lexer.token == '+') {
            lexer.nextToken();

            if ((Character.isLetter(lexer.token)) || (Character.isDigit(lexer.token))) {
                t = t();
            }
            else {
                lexer.error("Caractere inválido. Variável ou número esperado.");
            }

            if (lexer.token == '+') {
                ei = ei();
            }
        }

        return new Ei(t, ei);
    }

    // t ::= f ti
    private T t() {
        F f = null;
        Ti ti = null;

        if ((Character.isLetter(lexer.token)) || (Character.isDigit(lexer.token))) {
            f = f();
        }
        else {
            lexer.error("Caractere inválido. Variável ou número esperado.");
        }

        if (lexer.token == '*') {
            ti = ti();
        }

        return new T(f, ti);
    }

    // ti ::= '*' f ti |
    private Ti ti() {
        F f = null;
        Ti ti = null;

        if (lexer.token == '*') {
            lexer.nextToken();

            if ((Character.isLetter(lexer.token)) || (Character.isDigit(lexer.token))) {
                f = f();
            }
            else {
                lexer.error("Caractere inválido. Variável ou número esperado.");
            }

            if (lexer.token == '*') {
                ti = ti();
            }
        }

        return new Ti(f, ti);
    }

    // f ::= number | var
    private F f() {
        ArrayList<Character> number = null;
        char var = 0;

        if (Character.isDigit(lexer.token)) {
            number = number();
        }
        else {
            if (Character.isLetter(lexer.token)) {
                var = var();
            }
            else {
                lexer.error("Variável esperada.");
            }
        }

        return new F(number, var);
    }

    // number ::= digit {[digit]}
    private ArrayList<Character> number() {
        ArrayList<Character> number = null;

        if (Character.isDigit(lexer.token)) {
            number = new ArrayList<Character>();
            number.add(digit());
        }
        else {
            lexer.error("Número esperado.");
        }

        while (Character.isDigit(lexer.token)) {
            number.add(digit());
        }

        return number;
    }

    // digit ::= '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'
    private char digit() {
        char aux = 0;

        if (Character.isDigit(lexer.token)) {
            aux = lexer.token;
            lexer.flag = 1;
            lexer.nextToken();
        }
        else {
            lexer.error("Número esperado.");
        }

        return aux;
    }

    // var ::= 'a' | ... | 'z'
    private char var() {
        char aux = 0;

        if (Character.isLetter(lexer.token)) {
            aux = lexer.token;
            lexer.nextToken();
        }
        else {
            lexer.error("Letra esperada.");
        }
        
        return aux;
    }      
}
