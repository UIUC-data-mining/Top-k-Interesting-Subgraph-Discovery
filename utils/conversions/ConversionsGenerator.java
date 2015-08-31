/**
 * Java Unit Conversion  Library (jucl)
 * Copyright (c) 2000-2003 jucl team
 *
 * jucl is free software; you can distribute itself under the
 * terms of the BSD-style license received along with the jucl
 * distribution.
 */
package utils.conversions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
/**
 * Java Unit Conversion  Library (jucl)
 * Copyright (c) 2000-2003 jucl team
 *
 * jucl is free software; you can distribute itself under the
 * terms of the BSD-style license received along with the jucl
 * distribution.
 */
import java.util.Properties;

/**
 * Utility class generating simple linear conversion utility
 * classes from given resource files.<br>
 *
 * <em>Usage:</em><br>
 * java [classpath] net.wimpi.conversion.Conversion <propertyfile> <srcpath><p>
 *
 * Yet all generation is done by the build file, once this class has been
 * compiled. (see target generate).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class ConversionsGenerator {


  /**
   * This turns the class into an application that can be executed on the
   * commandline. <br>It serves the purpose of machine generating further
   * conversion utility classes from Properties like text files.
   *
   * @param args String array containing proper parameters.
   */
  public static void main(String[] args) {
    try {
      if (args.length < 1) {
        System.out.println("Usage java net.wimpi.conversion.Conversion <propertyfile> <srcpath>");
        System.exit(1);
      }


      File inf = new File(args[0]);
      StringBuffer sbuf = new StringBuffer(5000);
      String outfile = new StringBuffer(args[1])
          .append("/")
          .append(inf.getName())
          .append("Conversions.java")
          .toString();
      //System.out.println("\nOut: "+outfile+"\n");
      Properties conv = new Properties();
      conv.load(new FileInputStream(inf));

      sbuf.append("package net.wimpi.conversion;\n\n");
      sbuf.append(classDocs(inf.getName()));
      sbuf.append("public class ").append(inf.getName()).append("Conversions {\n\n");
      String convconst = "";
      for (Enumeration enum = conv.propertyNames(); enum.hasMoreElements();) {
        convconst = (String) enum.nextElement();
        String type = conv.getProperty(convconst).substring(0,2);

        //2SI single
        sbuf.append(singleDocs(convconst, false));
        sbuf.append(INDENT)
            .append("public static final double ")
            .append(convconst)
            .append("2SI(double d) {\n");
        sbuf.append(INDENT).append(INDENT);
        sbuf.append("return d")
            .append(type.charAt(0))
            .append("SI_")
            .append(convconst.toUpperCase())
            .append(";\n");

        sbuf.append(INDENT).append("}//").append(convconst).append("2SI\n\n\n");

        //SI2 single
        sbuf.append(singleDocs(convconst, true));
        sbuf.append(INDENT)
            .append("public static final double ")
            .append("SI2")
            .append(convconst)
            .append("(double d) {\n");
        sbuf.append(INDENT).append(INDENT);
        sbuf.append("return d")
            .append(type.charAt(1))
            .append("SI_")
            .append(convconst.toUpperCase())
            .append(";\n");
        sbuf.append(INDENT).append("}//SI2").append(convconst).append("\n\n\n");

        //2SI array
        sbuf.append(arrayDocs(convconst, false));
        sbuf.append(INDENT)
            .append("public static final double[] ")
            .append(convconst)
            .append("2SI(double[] da) {\n");
        sbuf.append(INDENT).append(INDENT);
        sbuf.append("for (int n = 0; n < da.length; n++) {\n")
            .append(INDENT).append(INDENT).append(INDENT)
            .append("da[n] ")
            .append(type.charAt(0))
            .append("= ")
            .append("SI_")
            .append(convconst.toUpperCase())
            .append(";\n")
            .append(INDENT).append(INDENT)
            .append("}\n");
        sbuf.append(INDENT).append(INDENT)
            .append("return da;\n");
        sbuf.append(INDENT)
            .append("}//")
            .append(convconst)
            .append("2SI\n\n\n");

        //SI2 array
        sbuf.append(arrayDocs(convconst, true));
        sbuf.append(INDENT)
            .append("public static final double[] ")
            .append("SI2")
            .append(convconst)
            .append("(double[] da) {\n");
        sbuf.append(INDENT).append(INDENT);
        sbuf.append("for (int n = 0; n < da.length; n++) {\n")
            .append(INDENT).append(INDENT).append(INDENT)
            .append("da[n] ")
            .append(type.charAt(1))
            .append("= ")
            .append("SI_")
            .append(convconst.toUpperCase())
            .append(";\n")
            .append(INDENT).append(INDENT)
            .append("}\n");
        sbuf.append(INDENT).append(INDENT)
            .append("return da;\n");
        sbuf.append(INDENT).append("}//SI2").append(convconst).append("\n\n\n");
      }
      sbuf.append("\n\n");
      for (Enumeration enum = conv.propertyNames(); enum.hasMoreElements();) {
        convconst = (String) enum.nextElement();
        String factor = conv.getProperty(convconst);
        factor=factor.substring(2,factor.length());

        sbuf.append(INDENT).append("/**\n")
            .append(INDENT).append(" *  Constant conversion factor\n")
            .append(INDENT).append(" */\n");
        sbuf.append(INDENT).append(" public static final double SI_")
            .append(convconst.toUpperCase())
            .append("=")
            .append(factor)
            .append(";\n");
      }

      sbuf.append("\n\n }//class ").append(inf.getName()).append("Conversions\n\n");

      File outf = new File(outfile);
      PrintWriter pw = new PrintWriter(new FileOutputStream(outf));
      pw.print(sbuf.toString());
      pw.close();

    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println(args[0] + "<Param1::Param2>" + args[1] + "\n\n");
    }
  }//main


  private static String singleDocs(String unit, boolean fromSI) {
    return new StringBuffer()
        .append(INDENT).append("/**\n")
        .append(INDENT).append(" *")
        .append(" Utility method converting a double value\n")
        .append(INDENT).append(" * ")
        .append(((fromSI)? "from SI or SI derived to ":"from "))
        .append(unit)
        .append(((fromSI)? ".\n":" to the SI or SI derived unit.\n"))
        .append(INDENT).append(" *\n")
        .append(INDENT).append(" * @param d double value to be converted.\n")
        .append(INDENT).append(" * @return double containing the converted value.\n")
        .append(INDENT).append(" */\n")
        .toString();
  }//singleDocs

  private static String arrayDocs(String unit, boolean fromSI) {
    return new StringBuffer()
        .append(INDENT).append("/**\n")
        .append(INDENT).append(" *")
        .append(" Utility method converting an array of double values\n")
        .append(INDENT).append(" * ")
        .append(((fromSI)? "from SI or SI derived to ":"from "))
        .append(unit)
        .append(((fromSI)? ".\n":" to the SI or SI derived unit.\n"))
        .append(INDENT).append(" * <br>Note that the array passed in as parameter is the target.\n")
        .append(INDENT).append(" * The reference to this array is only returned for convenience reasons.\n")
        .append(INDENT).append(" *\n")
        .append(INDENT).append(" * @param da Array of double values to be converted.\n")
        .append(INDENT).append(" * @return Reference to the same array now containing converted values.\n")
        .append(INDENT).append(" */\n")
        .toString();
  }//singleDocs

  private static String classDocs(String groupname) {
    return new StringBuffer()
        .append("/**\n")
        .append(" * Utility class containing class operations for converting\n")
        .append(" * between SI (or SI derived standard units) and other known\n")
        .append(" * units of ").append(groupname).append(".\n")
        .append(" *\n")
        .append(" * <br>Note that this class is machine generated, using conversion factors\n")
        .append(" * from a text file (in standard Properties format).\n")
        .append(" *\n")
        .append(" * @author Dieter Wimberger\n")
        .append(" * @version 1.0\n")
        .append(" */\n")
        .toString();

  }//classDocs


  private static final String INDENT = "  ";

}//Conversion