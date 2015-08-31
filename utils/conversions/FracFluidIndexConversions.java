/**
 * Java Unit Conversion  Library (jucl)
 * Copyright (c) 2000-2003 jucl team
 *
 * jucl is free software; you can distribute itself under the
 * terms of the BSD-style license received along with the jucl
 * distribution.
 */
package utils.conversions;

/**
 * Utility class containing class operations for converting
 * between SI (or SI derived standard units) and other known
 * units of FracFluidIndex.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class FracFluidIndexConversions {

  /**
   * Utility method converting a double value
   * from lbfsecpersquareft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double lbfsecpersquareft2SI(double d) {
    return d*SI_LBFSECPERSQUAREFT;
  }//lbfsecpersquareft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to lbfsecpersquareft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2lbfsecpersquareft(double d) {
    return d/SI_LBFSECPERSQUAREFT;
  }//SI2lbfsecpersquareft


  /**
   * Utility method converting an array of double values
   * from lbfsecpersquareft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] lbfsecpersquareft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_LBFSECPERSQUAREFT;
    }
    return da;
  }//lbfsecpersquareft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to lbfsecpersquareft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2lbfsecpersquareft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_LBFSECPERSQUAREFT;
    }
    return da;
  }//SI2lbfsecpersquareft


  /**
   * Utility method converting a double value
   * from barsec to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double barsec2SI(double d) {
    return d*SI_BARSEC;
  }//barsec2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to barsec.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2barsec(double d) {
    return d/SI_BARSEC;
  }//SI2barsec


  /**
   * Utility method converting an array of double values
   * from barsec to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] barsec2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_BARSEC;
    }
    return da;
  }//barsec2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to barsec.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2barsec(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_BARSEC;
    }
    return da;
  }//SI2barsec




  /**
   *  Constant conversion factor
   */
   public static final double SI_LBFSECPERSQUAREFT=47.88026;
  /**
   *  Constant conversion factor
   */
   public static final double SI_BARSEC=100000;


 }//class FracFluidIndexConversions

