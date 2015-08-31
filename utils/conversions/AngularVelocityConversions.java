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
 * units of AngularVelocity.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class AngularVelocityConversions {

  /**
   * Utility method converting a double value
   * from radpers to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double radpers2SI(double d) {
    return d*SI_RADPERS;
  }//radpers2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to radpers.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2radpers(double d) {
    return d/SI_RADPERS;
  }//SI2radpers


  /**
   * Utility method converting an array of double values
   * from radpers to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] radpers2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_RADPERS;
    }
    return da;
  }//radpers2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to radpers.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2radpers(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_RADPERS;
    }
    return da;
  }//SI2radpers


  /**
   * Utility method converting a double value
   * from rpm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double rpm2SI(double d) {
    return d*SI_RPM;
  }//rpm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to rpm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2rpm(double d) {
    return d/SI_RPM;
  }//SI2rpm


  /**
   * Utility method converting an array of double values
   * from rpm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] rpm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_RPM;
    }
    return da;
  }//rpm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to rpm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2rpm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_RPM;
    }
    return da;
  }//SI2rpm




  /**
   *  Constant conversion factor
   */
   public static final double SI_RADPERS=1.74532925199e-2;
  /**
   *  Constant conversion factor
   */
   public static final double SI_RPM=0.1047198;


 }//class AngularVelocityConversions

