/* ================================================================== *
    
    Universidade Federal de Sao Carlos - UFSCar, Sorocaba

    Disciplina: Compiladores
    Prof. Tiemi Christine Sakata

    Compilador 0-3

    Autor: Giulianno Raphael Sbrugnera RA: 408093
    Autor: Matheus Casarin Paez        RA: 438308
 
 * ================================================================== */

import AST.*;

public class Main {
    public static void main( String []args ) {
        char []input = "write(10); i = 2 + 5 * 10; j = 32 * i; write (j * 2 + i);".toCharArray();
        
        Compiler compiler = new Compiler();
        
        Program program = compiler.compile(input);
        program.genC();
    }
}