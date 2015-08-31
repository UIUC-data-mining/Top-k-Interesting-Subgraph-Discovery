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
 * units of PlainAngle.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class PlainAngleConversions {

  /**
   * Utility method converting a double value
   * from rad to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double rad2SI(double d) {
    return d*SI_RAD;
  }//rad2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to rad.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2rad(double d) {
    return d/SI_RAD;
  }//SI2rad


  /**
   * Utility method converting an array of double values
   * from rad to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] rad2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_RAD;
    }
    return da;
  }//rad2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to rad.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2rad(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_RAD;
    }
    return da;
  }//SI2rad




  /**
   *  Constant conversion factor
   */
   public static final double SI_RAD=1.74532925199e-2;


 }//class PlainAngleConversions

