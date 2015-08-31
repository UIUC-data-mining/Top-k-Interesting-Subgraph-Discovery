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
 * units of PressureGradient.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class PressureGradientConversions {

  /**
   * Utility method converting a double value
   * from barperm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double barperm2SI(double d) {
    return d*SI_BARPERM;
  }//barperm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to barperm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2barperm(double d) {
    return d/SI_BARPERM;
  }//SI2barperm


  /**
   * Utility method converting an array of double values
   * from barperm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] barperm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_BARPERM;
    }
    return da;
  }//barperm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to barperm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2barperm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_BARPERM;
    }
    return da;
  }//SI2barperm


  /**
   * Utility method converting a double value
   * from psiperft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double psiperft2SI(double d) {
    return d*SI_PSIPERFT;
  }//psiperft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to psiperft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2psiperft(double d) {
    return d/SI_PSIPERFT;
  }//SI2psiperft


  /**
   * Utility method converting an array of double values
   * from psiperft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] psiperft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_PSIPERFT;
    }
    return da;
  }//psiperft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to psiperft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2psiperft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_PSIPERFT;
    }
    return da;
  }//SI2psiperft


  /**
   * Utility method converting a double value
   * from kPaperm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kPaperm2SI(double d) {
    return d*SI_KPAPERM;
  }//kPaperm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kPaperm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kPaperm(double d) {
    return d/SI_KPAPERM;
  }//SI2kPaperm


  /**
   * Utility method converting an array of double values
   * from kPaperm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kPaperm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KPAPERM;
    }
    return da;
  }//kPaperm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kPaperm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kPaperm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KPAPERM;
    }
    return da;
  }//SI2kPaperm




  /**
   *  Constant conversion factor
   */
   public static final double SI_BARPERM=100000;
  /**
   *  Constant conversion factor
   */
   public static final double SI_PSIPERFT=22620.59;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KPAPERM=1000;


 }//class PressureGradientConversions

