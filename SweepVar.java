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
 * Read in.txt with format and return baseline comsol, sweep type and
 * parameter name. Use SweepParse to transfer param to this class.
 */
public class SweepVar {
    /* Read in.txt with format and return baseline comsol, sweep type and
    parameter name*. Use SweepParse to transfer param to main. */
    private String inputtxt;
    private int nparam;
    private String baseline;
    private String folder;
    private String mode;
    private String [] pname;
    private String [] unit;
    private Param [] param;
    
    /**
     * @param input name of the input.txt file (omitting .txt postfix).
     */
    public SweepVar(String input) {
        inputtxt = input+".txt";
        SweepParse sp=new SweepParse(input);
        nparam = sp.countParam();
        pname = new String[nparam];
        unit = new String[nparam];
        String [] strval = new String[nparam];
        param = new Param[nparam];
        
        int ii=0;
        String s1;
        String [] inputLines = new String[nparam*3+3]; 
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputtxt));
            while((s1=br.readLine()) != null) {	
                inputLines[ii]=s1;
                System.out.println(s1);
                ii++;
            }
            br.close();	
        } catch (Exception e){
            e.printStackTrace();
        }
        baseline=inputLines[0];
        folder=inputLines[1];
        mode=inputLines[2];
        ii=0;
        while(ii<nparam) {
            pname[ii] = inputLines[ii*3+3];
            if (inputLines[ii*3+4].indexOf("1") == 0) {
                unit[ii]="";
            }
                else {
                unit[ii] = inputLines[ii*3+4];
            }
            strval[ii] = inputLines[ii*3+5];
            param[ii] = new Param(pname[ii],unit[ii],strval[ii]);
            ii++;
        }        
    }
    
    /**
     * @return folder in string
     */
    public String getFolder() {
        return folder;
    }
    
    /**
     * @return name of the baseline model
     */
    public String getBase() {
        return baseline;
    }
    
    /**
     * @return sweepmode defined in input
     */
    public String getMode() {
        return mode;
    }
    
    /**
     * @return parameter number in integer
     */
    public int countParam() {
        return nparam;
    }
    
    /**
     * @return the Param array
     */
    public Param [] arrParam() {
        return param;
    }
    
}
    
    
    
