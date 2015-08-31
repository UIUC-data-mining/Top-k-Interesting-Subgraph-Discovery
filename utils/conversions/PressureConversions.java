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
 * units of Pressure.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class PressureConversions {

  /**
   * Utility method converting a double value
   * from mPa to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double mPa2SI(double d) {
    return d*SI_MPA;
  }//mPa2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to mPa.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2mPa(double d) {
    return d/SI_MPA;
  }//SI2mPa


  /**
   * Utility method converting an array of double values
   * from mPa to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] mPa2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MPA;
    }
    return da;
  }//mPa2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to mPa.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2mPa(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MPA;
    }
    return da;
  }//SI2mPa


  /**
   * Utility method converting a double value
   * from ksi to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ksi2SI(double d) {
    return d*SI_KSI;
  }//ksi2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ksi.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ksi(double d) {
    return d/SI_KSI;
  }//SI2ksi


  /**
   * Utility method converting an array of double values
   * from ksi to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ksi2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KSI;
    }
    return da;
  }//ksi2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ksi.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ksi(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KSI;
    }
    return da;
  }//SI2ksi


  /**
   * Utility method converting a double value
   * from kgfpersqcm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kgfpersqcm2SI(double d) {
    return d*SI_KGFPERSQCM;
  }//kgfpersqcm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kgfpersqcm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kgfpersqcm(double d) {
    return d/SI_KGFPERSQCM;
  }//SI2kgfpersqcm


  /**
   * Utility method converting an array of double values
   * from kgfpersqcm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kgfpersqcm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KGFPERSQCM;
    }
    return da;
  }//kgfpersqcm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kgfpersqcm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kgfpersqcm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KGFPERSQCM;
    }
    return da;
  }//SI2kgfpersqcm


  /**
   * Utility method converting a double value
   * from lbfper100sqft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double lbfper100sqft2SI(double d) {
    return d*SI_LBFPER100SQFT;
  }//lbfper100sqft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to lbfper100sqft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2lbfper100sqft(double d) {
    return d/SI_LBFPER100SQFT;
  }//SI2lbfper100sqft


  /**
   * Utility method converting an array of double values
   * from lbfper100sqft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] lbfper100sqft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_LBFPER100SQFT;
    }
    return da;
  }//lbfper100sqft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to lbfper100sqft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2lbfper100sqft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_LBFPER100SQFT;
    }
    return da;
  }//SI2lbfper100sqft


  /**
   * Utility method converting a double value
   * from psi to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double psi2SI(double d) {
    return d*SI_PSI;
  }//psi2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to psi.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2psi(double d) {
    return d/SI_PSI;
  }//SI2psi


  /**
   * Utility method converting an array of double values
   * from psi to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] psi2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_PSI;
    }
    return da;
  }//psi2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to psi.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2psi(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_PSI;
    }
    return da;
  }//SI2psi


  /**
   * Utility method converting a double value
   * from bar to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double bar2SI(double d) {
    return d*SI_BAR;
  }//bar2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to bar.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2bar(double d) {
    return d/SI_BAR;
  }//SI2bar


  /**
   * Utility method converting an array of double values
   * from bar to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] bar2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_BAR;
    }
    return da;
  }//bar2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to bar.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2bar(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_BAR;
    }
    return da;
  }//SI2bar


  /**
   * Utility method converting a double value
   * from kPa to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kPa2SI(double d) {
    return d*SI_KPA;
  }//kPa2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kPa.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kPa(double d) {
    return d/SI_KPA;
  }//SI2kPa


  /**
   * Utility method converting an array of double values
   * from kPa to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kPa2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KPA;
    }
    return da;
  }//kPa2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kPa.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kPa(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KPA;
    }
    return da;
  }//SI2kPa




  /**
   *  Constant conversion factor
   */
   public static final double SI_MPA=1e6;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KSI=6894757;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KGFPERSQCM=98066.5;
  /**
   *  Constant conversion factor
   */
   public static final double SI_LBFPER100SQFT=0.4788026;
  /**
   *  Constant conversion factor
   */
   public static final double SI_PSI=6894.757;
  /**
   *  Constant conversion factor
   */
   public static final double SI_BAR=1e5;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KPA=1000;


 }//class PressureConversions

