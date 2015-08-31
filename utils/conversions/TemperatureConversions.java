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
 * units of Temperature.
 *
 * <br>Note that this class is not and should not be machine generated.
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class TemperatureConversions {

  /**
   * Utility method converting a double value
   * from R to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double R2SI(double d) {
    return (SI_F * d) - SI_K;
  }//R2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to R.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2R(double d) {
    return 1.8 * (d + SI_K);
  }//SI2R


  /**
   * Utility method converting an array of double values
   * from R to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] R2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] = (SI_F * da[n]) - SI_K;
    }
    return da;
  }//R2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to R.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2R(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] = 1.8 * (da[n] + SI_K);
    }
    return da;
  }//SI2R


  /**
   * Utility method converting a double value
   * from K to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double K2SI(double d) {
    return d - SI_K;
  }//K2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to K.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2K(double d) {
    return d + SI_K;
  }//SI2K


  /**
   * Utility method converting an array of double values
   * from K to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] K2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] -= SI_K;
    }
    return da;
  }//K2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to K.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2K(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] += SI_K;
    }
    return da;
  }//SI2K


  /**
   * Utility method converting a double value
   * from F to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double F2SI(double d) {
    return (d - 32) * SI_F;
  }//F2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to F.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2F(double d) {
    return (d * 1.8) + 32;
  }//SI2F


  /**
   * Utility method converting an array of double values
   * from F to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] F2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] = (da[n] - 32) * SI_F;
    }
    return da;
  }//F2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to F.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2F(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] = (da[n] * 1.8) + 32;
    }
    return da;
  }//SI2F


  /**
   *  Constant conversion factor
   */
  public static final double SI_K = 273.15;
  /**
   *  Constant conversion factor
   */
  public static final double SI_F = 0.555555555555556;


}//class TemperatureConversions

