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
 * units of Power.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class PowerConversions {

  /**
   * Utility method converting a double value
   * from W to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double W2SI(double d) {
    return d*SI_W;
  }//W2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to W.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2W(double d) {
    return d/SI_W;
  }//SI2W


  /**
   * Utility method converting an array of double values
   * from W to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] W2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_W;
    }
    return da;
  }//W2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to W.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2W(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_W;
    }
    return da;
  }//SI2W


  /**
   * Utility method converting a double value
   * from hp to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double hp2SI(double d) {
    return d*SI_HP;
  }//hp2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to hp.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2hp(double d) {
    return d/SI_HP;
  }//SI2hp


  /**
   * Utility method converting an array of double values
   * from hp to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] hp2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_HP;
    }
    return da;
  }//hp2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to hp.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2hp(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_HP;
    }
    return da;
  }//SI2hp




  /**
   *  Constant conversion factor
   */
   public static final double SI_W=0.001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_HP=1.34102209;


 }//class PowerConversions

