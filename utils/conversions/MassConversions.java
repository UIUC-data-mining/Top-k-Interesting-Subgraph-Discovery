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
 * units of Mass.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class MassConversions {

  /**
   * Utility method converting a double value
   * from shortton to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double shortton2SI(double d) {
    return d*SI_SHORTTON;
  }//shortton2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to shortton.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2shortton(double d) {
    return d/SI_SHORTTON;
  }//SI2shortton


  /**
   * Utility method converting an array of double values
   * from shortton to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] shortton2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_SHORTTON;
    }
    return da;
  }//shortton2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to shortton.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2shortton(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_SHORTTON;
    }
    return da;
  }//SI2shortton


  /**
   * Utility method converting a double value
   * from klbm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double klbm2SI(double d) {
    return d*SI_KLBM;
  }//klbm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to klbm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2klbm(double d) {
    return d/SI_KLBM;
  }//SI2klbm


  /**
   * Utility method converting an array of double values
   * from klbm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] klbm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KLBM;
    }
    return da;
  }//klbm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to klbm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2klbm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KLBM;
    }
    return da;
  }//SI2klbm


  /**
   * Utility method converting a double value
   * from lbm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double lbm2SI(double d) {
    return d*SI_LBM;
  }//lbm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to lbm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2lbm(double d) {
    return d/SI_LBM;
  }//SI2lbm


  /**
   * Utility method converting an array of double values
   * from lbm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] lbm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_LBM;
    }
    return da;
  }//lbm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to lbm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2lbm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_LBM;
    }
    return da;
  }//SI2lbm


  /**
   * Utility method converting a double value
   * from metricton to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double metricton2SI(double d) {
    return d*SI_METRICTON;
  }//metricton2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to metricton.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2metricton(double d) {
    return d/SI_METRICTON;
  }//SI2metricton


  /**
   * Utility method converting an array of double values
   * from metricton to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] metricton2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_METRICTON;
    }
    return da;
  }//metricton2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to metricton.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2metricton(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_METRICTON;
    }
    return da;
  }//SI2metricton


  /**
   * Utility method converting a double value
   * from longton to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double longton2SI(double d) {
    return d*SI_LONGTON;
  }//longton2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to longton.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2longton(double d) {
    return d/SI_LONGTON;
  }//SI2longton


  /**
   * Utility method converting an array of double values
   * from longton to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] longton2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_LONGTON;
    }
    return da;
  }//longton2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to longton.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2longton(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_LONGTON;
    }
    return da;
  }//SI2longton




  /**
   *  Constant conversion factor
   */
   public static final double SI_SHORTTON=907.18474;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KLBM=453.59237;
  /**
   *  Constant conversion factor
   */
   public static final double SI_LBM=0.45359237;
  /**
   *  Constant conversion factor
   */
   public static final double SI_METRICTON=1000;
  /**
   *  Constant conversion factor
   */
   public static final double SI_LONGTON=1016.0469088;


 }//class MassConversions

