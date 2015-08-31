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
 * units of Permeability.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class PermeabilityConversions {

  /**
   * Utility method converting a double value
   * from darcy to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double darcy2SI(double d) {
    return d*SI_DARCY;
  }//darcy2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to darcy.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2darcy(double d) {
    return d/SI_DARCY;
  }//SI2darcy


  /**
   * Utility method converting an array of double values
   * from darcy to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] darcy2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_DARCY;
    }
    return da;
  }//darcy2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to darcy.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2darcy(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_DARCY;
    }
    return da;
  }//SI2darcy


  /**
   * Utility method converting a double value
   * from sqm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double sqm2SI(double d) {
    return d*SI_SQM;
  }//sqm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to sqm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2sqm(double d) {
    return d/SI_SQM;
  }//SI2sqm


  /**
   * Utility method converting an array of double values
   * from sqm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] sqm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_SQM;
    }
    return da;
  }//sqm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to sqm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2sqm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_SQM;
    }
    return da;
  }//SI2sqm


  /**
   * Utility method converting a double value
   * from mdarcy to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double mdarcy2SI(double d) {
    return d*SI_MDARCY;
  }//mdarcy2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to mdarcy.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2mdarcy(double d) {
    return d/SI_MDARCY;
  }//SI2mdarcy


  /**
   * Utility method converting an array of double values
   * from mdarcy to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] mdarcy2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MDARCY;
    }
    return da;
  }//mdarcy2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to mdarcy.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2mdarcy(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MDARCY;
    }
    return da;
  }//SI2mdarcy




  /**
   *  Constant conversion factor
   */
   public static final double SI_DARCY=0.9869233;
  /**
   *  Constant conversion factor
   */
   public static final double SI_SQM=1e12;
  /**
   *  Constant conversion factor
   */
   public static final double SI_MDARCY=0.0009869233;


 }//class PermeabilityConversions

