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
 * Define the class structure of Param for parametric sweep
 * to be used by JoinParam. Each Param instance is correlated with
 * one parameter in model. If the DOE requires the modification of 
 * several parameters, define multiple Param instances and use JoinParam
 * to combine them. 
 */
public class Param {
    // Define the class Param with name, unit and value
    // Define the method to return the value
    // Values are read from string by method GenArray
    // Export "name unit values nv"
    private String pname;
    private String punit;
    private double [] dval;
    private int np;
    private String instr;
        
    public String name;
    public String unit;
    public String braunit;
    public double [] values;
    public int nv;
     
    /**
     * Example: 
     *      <pre> Param("AirGap","nm","1:2:5");
     *      </pre>
     * @param paraname COMSOL variable name to be swept
     * @param paraunit unit of the variable
     * @param strval this is generally only one value **/
    public Param (String paraname, String paraunit, String strval) {
        GenArray ga = new GenArray(strval);
        pname = paraname;
        punit = paraunit;
        instr = strval;
        np = ga.getArraylen();
        dval=ga.getArray();
        
        name=pname;
        unit=punit;
        if (unit=="") {
            braunit="";
        }   else {
            braunit="["+unit+"]";
        }
        values=dval;
        nv=np;	
    }
    
    /**
     * @return the generated Param instance
     */
    public Param getParam() {
        return new Param(pname,punit,instr);
    }
    
}
