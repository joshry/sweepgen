/* Comsol sweep generation, Ruoxi 04/01/2014 */
/* Updated 08.08/2014 */
// 1. Copy the template file to any folder with the comsol baseline called example1.mph.
// 2. Touch a text named input.txt to describe the sweep
// 3. The input format is:
// example1 ... name of the comsol baseline
// sweep1 ... name of the folder storing all modeling
// all ... ("all" or "none", all means ALL the composition of parameter)
// tDLC ... param 1
// um ... unit of param 1, if unitless, enter 1 here
// 0.002:0.002:0.004 ... param 1. use either a1:a2:a3 or a1 a2 a3. the latter will be read as 3 cases.
// tCOC ... param 2
// um .. unit of param 2, if unitless, enter 1
// 0.005 ... see above
// Steps:
// 1. comsol compile the .java file
// 2. comsol batch the .class file
// code will generate a directory under current directory named sweep, 
// with a sweep.txt file for the actual parameter that will be run.
// Followed by all the individual .mph with updated parameter.
// 3. create a .sh to submit the jobs


import com.comsol.model.*;
import com.comsol.model.util.*;
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

public class preCOMSOL {
    //String input="input";
    /* unless you want to hack the code everytime please leave this as "input" */  
    static String input="input";
    
    static String sweepfolder,mphname,fullwd,fullsweepfile;
    static String runningmode;
    static int nparam,nmodel;
    static sweepgen.Param[] allparam;
    static int[][] lut; 
    
    public static void main(String [] args) {
        // Parse input file and generate Param
        sweepgen.SweepVar sv = new sweepgen.SweepVar(input);
            sweepfolder=sv.getFolder();
            mphname=sv.getBase();
            runningmode=sv.getMode();
            //one: loop with pair; all:loop with all composition
            nparam=sv.countParam();
            allparam = sv.arrParam();
            System.out.println(allparam[0].name);
        // Create folder and sweep file    
        sweepgen.SweepFolder sf = new sweepgen.SweepFolder(sweepfolder);
            sf.createFolder();
            sf.createOutTxt();
            System.out.println("The current working path is:");
            System.out.println(sf.getPath());
            fullwd = sf.getPath();
            fullsweepfile = sf.getSweepPath();
            System.out.println(fullsweepfile);           
        // Join the parameter and generate LUT
        sweepgen.JoinParam jp = new sweepgen.JoinParam(allparam,runningmode);
            lut=jp.getCombine();
            nmodel=jp.getSweepcount();
            System.out.println("The total number of models to run is:");
            System.out.println(nmodel);
        
        run();
    }	

    public static Model run(){
        Model model = ModelUtil.create("Model");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fullsweepfile));
            model = ModelUtil.load("Model",mphname);
            for (int ii=0; ii<nmodel; ii++){
                String filename=mphname;
                String dirfilename=mphname;
                for (int jj=0; jj<nparam; jj++){
                    int ind=lut[ii][jj];
                    double val[]=allparam[jj].values; // param values array
                    String strval=String.valueOf(val[ind])+allparam[jj].braunit; // strings for param change
                    filename = filename+"_"+allparam[jj].name+"_"+String.valueOf(val[ind]);
                    dirfilename = fullwd+"/"+sweepfolder+"/"+filename+".mph";
                    model.param().set(allparam[jj].name, strval); //replace
                    // System.out.println(allparam[jj].values[0]);
                }
                System.out.println(filename);
                bw.write(filename);
                bw.newLine();
                model.save(dirfilename);
            }
            ModelUtil.remove("Model");
            bw.close();
        }	catch (Exception e) {
            System.err.println("Error: "+ e.getMessage());
        }
    return model;
    }

}
