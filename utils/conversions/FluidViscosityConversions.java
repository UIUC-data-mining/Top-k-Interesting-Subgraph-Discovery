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
 * units of FluidViscosity.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class FluidViscosityConversions {

  /**
   * Utility method converting a double value
   * from mPas to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double mPas2SI(double d) {
    return d*SI_MPAS;
  }//mPas2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to mPas.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2mPas(double d) {
    return d/SI_MPAS;
  }//SI2mPas


  /**
   * Utility method converting an array of double values
   * from mPas to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] mPas2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MPAS;
    }
    return da;
  }//mPas2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to mPas.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2mPas(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MPAS;
    }
    return da;
  }//SI2mPas


  /**
   * Utility method converting a double value
   * from cp to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double cp2SI(double d) {
    return d*SI_CP;
  }//cp2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to cp.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2cp(double d) {
    return d/SI_CP;
  }//SI2cp


  /**
   * Utility method converting an array of double values
   * from cp to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] cp2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_CP;
    }
    return da;
  }//cp2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to cp.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2cp(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_CP;
    }
    return da;
  }//SI2cp


  /**
   * Utility method converting a double value
   * from p to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double p2SI(double d) {
    return d*SI_P;
  }//p2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to p.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2p(double d) {
    return d/SI_P;
  }//SI2p


  /**
   * Utility method converting an array of double values
   * from p to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] p2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_P;
    }
    return da;
  }//p2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to p.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2p(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_P;
    }
    return da;
  }//SI2p




  /**
   *  Constant conversion factor
   */
   public static final double SI_MPAS=0.001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_CP=0.001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_P=0.1;


 }//class FluidViscosityConversions

