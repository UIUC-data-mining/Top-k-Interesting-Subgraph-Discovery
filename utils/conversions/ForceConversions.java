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
 * units of Force.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class ForceConversions {

  /**
   * Utility method converting a double value
   * from ustonf to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ustonf2SI(double d) {
    return d*SI_USTONF;
  }//ustonf2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ustonf.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ustonf(double d) {
    return d/SI_USTONF;
  }//SI2ustonf


  /**
   * Utility method converting an array of double values
   * from ustonf to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ustonf2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_USTONF;
    }
    return da;
  }//ustonf2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ustonf.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ustonf(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_USTONF;
    }
    return da;
  }//SI2ustonf


  /**
   * Utility method converting a double value
   * from jperm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double jperm2SI(double d) {
    return d*SI_JPERM;
  }//jperm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to jperm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2jperm(double d) {
    return d/SI_JPERM;
  }//SI2jperm


  /**
   * Utility method converting an array of double values
   * from jperm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] jperm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_JPERM;
    }
    return da;
  }//jperm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to jperm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2jperm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_JPERM;
    }
    return da;
  }//SI2jperm


  /**
   * Utility method converting a double value
   * from metrictonf to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double metrictonf2SI(double d) {
    return d*SI_METRICTONF;
  }//metrictonf2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to metrictonf.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2metrictonf(double d) {
    return d/SI_METRICTONF;
  }//SI2metrictonf


  /**
   * Utility method converting an array of double values
   * from metrictonf to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] metrictonf2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_METRICTONF;
    }
    return da;
  }//metrictonf2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to metrictonf.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2metrictonf(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_METRICTONF;
    }
    return da;
  }//SI2metrictonf


  /**
   * Utility method converting a double value
   * from kN to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kN2SI(double d) {
    return d*SI_KN;
  }//kN2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kN.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kN(double d) {
    return d/SI_KN;
  }//SI2kN


  /**
   * Utility method converting an array of double values
   * from kN to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kN2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KN;
    }
    return da;
  }//kN2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kN.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kN(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KN;
    }
    return da;
  }//SI2kN


  /**
   * Utility method converting a double value
   * from daN to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double daN2SI(double d) {
    return d*SI_DAN;
  }//daN2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to daN.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2daN(double d) {
    return d/SI_DAN;
  }//SI2daN


  /**
   * Utility method converting an array of double values
   * from daN to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] daN2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_DAN;
    }
    return da;
  }//daN2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to daN.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2daN(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_DAN;
    }
    return da;
  }//SI2daN


  /**
   * Utility method converting a double value
   * from uktonf to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double uktonf2SI(double d) {
    return d*SI_UKTONF;
  }//uktonf2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to uktonf.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2uktonf(double d) {
    return d/SI_UKTONF;
  }//SI2uktonf


  /**
   * Utility method converting an array of double values
   * from uktonf to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] uktonf2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_UKTONF;
    }
    return da;
  }//uktonf2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to uktonf.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2uktonf(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_UKTONF;
    }
    return da;
  }//SI2uktonf


  /**
   * Utility method converting a double value
   * from kip to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kip2SI(double d) {
    return d*SI_KIP;
  }//kip2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kip.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kip(double d) {
    return d/SI_KIP;
  }//SI2kip


  /**
   * Utility method converting an array of double values
   * from kip to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kip2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KIP;
    }
    return da;
  }//kip2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kip.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kip(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KIP;
    }
    return da;
  }//SI2kip


  /**
   * Utility method converting a double value
   * from lbf to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double lbf2SI(double d) {
    return d*SI_LBF;
  }//lbf2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to lbf.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2lbf(double d) {
    return d/SI_LBF;
  }//SI2lbf


  /**
   * Utility method converting an array of double values
   * from lbf to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] lbf2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_LBF;
    }
    return da;
  }//lbf2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to lbf.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2lbf(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_LBF;
    }
    return da;
  }//SI2lbf


  /**
   * Utility method converting a double value
   * from kgf to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kgf2SI(double d) {
    return d*SI_KGF;
  }//kgf2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kgf.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kgf(double d) {
    return d/SI_KGF;
  }//SI2kgf


  /**
   * Utility method converting an array of double values
   * from kgf to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kgf2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KGF;
    }
    return da;
  }//kgf2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kgf.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kgf(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KGF;
    }
    return da;
  }//SI2kgf




  /**
   *  Constant conversion factor
   */
   public static final double SI_USTONF=8896.443;
  /**
   *  Constant conversion factor
   */
   public static final double SI_JPERM=1;
  /**
   *  Constant conversion factor
   */
   public static final double SI_METRICTONF=9806.65;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KN=1000;
  /**
   *  Constant conversion factor
   */
   public static final double SI_DAN=10;
  /**
   *  Constant conversion factor
   */
   public static final double SI_UKTONF=9964.016;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KIP=4448.222;
  /**
   *  Constant conversion factor
   */
   public static final double SI_LBF=4.448222;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KGF=9.80665;


 }//class ForceConversions

