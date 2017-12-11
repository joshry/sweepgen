package sweepgen;

import java.util.*;
import java.lang.Number.*;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.*;

/**
 * Read number of parameter (variables) from input.txt
 */
public class SweepParse {
    /* Read number of parameter (variables) from input.txt*/
    private String inputtxt;
    private int nparam;
    // private String mode;
    // private String baseline;
    // private String [] param;
    
    /**
     * Specify the input.txt. Do not add post-fix.
     */
    public SweepParse(String input) {
        inputtxt = input+".txt";
    }
        
    /**
     * @return Param number
     */
    public int countParam() {
        int ii=0;
        String s1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputtxt));
            while((s1=br.readLine()) != null) {	
                ii++;				
            }
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        nparam=(ii-3)/3;
        return nparam;
    }
        
}
