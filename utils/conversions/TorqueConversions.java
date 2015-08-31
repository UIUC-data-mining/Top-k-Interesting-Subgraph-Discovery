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
 * units of Torque.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class TorqueConversions {

  /**
   * Utility method converting a double value
   * from kgfxm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kgfxm2SI(double d) {
    return d*SI_KGFXM;
  }//kgfxm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kgfxm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kgfxm(double d) {
    return d/SI_KGFXM;
  }//SI2kgfxm


  /**
   * Utility method converting an array of double values
   * from kgfxm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kgfxm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KGFXM;
    }
    return da;
  }//kgfxm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kgfxm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kgfxm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KGFXM;
    }
    return da;
  }//SI2kgfxm


  /**
   * Utility method converting a double value
   * from ftxklbf to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ftxklbf2SI(double d) {
    return d*SI_FTXKLBF;
  }//ftxklbf2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ftxklbf.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ftxklbf(double d) {
    return d/SI_FTXKLBF;
  }//SI2ftxklbf


  /**
   * Utility method converting an array of double values
   * from ftxklbf to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ftxklbf2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_FTXKLBF;
    }
    return da;
  }//ftxklbf2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ftxklbf.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ftxklbf(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_FTXKLBF;
    }
    return da;
  }//SI2ftxklbf


  /**
   * Utility method converting a double value
   * from kNm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kNm2SI(double d) {
    return d*SI_KNM;
  }//kNm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kNm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kNm(double d) {
    return d/SI_KNM;
  }//SI2kNm


  /**
   * Utility method converting an array of double values
   * from kNm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kNm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KNM;
    }
    return da;
  }//kNm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kNm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kNm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KNM;
    }
    return da;
  }//SI2kNm


  /**
   * Utility method converting a double value
   * from ftxlbf to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ftxlbf2SI(double d) {
    return d*SI_FTXLBF;
  }//ftxlbf2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ftxlbf.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ftxlbf(double d) {
    return d/SI_FTXLBF;
  }//SI2ftxlbf


  /**
   * Utility method converting an array of double values
   * from ftxlbf to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ftxlbf2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_FTXLBF;
    }
    return da;
  }//ftxlbf2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ftxlbf.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ftxlbf(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_FTXLBF;
    }
    return da;
  }//SI2ftxlbf


  /**
   * Utility method converting a double value
   * from dynexcm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double dynexcm2SI(double d) {
    return d*SI_DYNEXCM;
  }//dynexcm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to dynexcm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2dynexcm(double d) {
    return d/SI_DYNEXCM;
  }//SI2dynexcm


  /**
   * Utility method converting an array of double values
   * from dynexcm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] dynexcm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_DYNEXCM;
    }
    return da;
  }//dynexcm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to dynexcm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2dynexcm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_DYNEXCM;
    }
    return da;
  }//SI2dynexcm




  /**
   *  Constant conversion factor
   */
   public static final double SI_KGFXM=9.80665;
  /**
   *  Constant conversion factor
   */
   public static final double SI_FTXKLBF=1355.817948;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KNM=1000;
  /**
   *  Constant conversion factor
   */
   public static final double SI_FTXLBF=1.355817948;
  /**
   *  Constant conversion factor
   */
   public static final double SI_DYNEXCM=0.0000001;


 }//class TorqueConversions

