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
 * units of Volume.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class VolumeConversions {

  /**
   * Utility method converting a double value
   * from l to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double l2SI(double d) {
    return d*SI_L;
  }//l2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to l.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2l(double d) {
    return d/SI_L;
  }//SI2l


  /**
   * Utility method converting an array of double values
   * from l to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] l2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_L;
    }
    return da;
  }//l2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to l.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2l(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_L;
    }
    return da;
  }//SI2l


  /**
   * Utility method converting a double value
   * from bbl to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double bbl2SI(double d) {
    return d*SI_BBL;
  }//bbl2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to bbl.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2bbl(double d) {
    return d/SI_BBL;
  }//SI2bbl


  /**
   * Utility method converting an array of double values
   * from bbl to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] bbl2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_BBL;
    }
    return da;
  }//bbl2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to bbl.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2bbl(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_BBL;
    }
    return da;
  }//SI2bbl


  /**
   * Utility method converting a double value
   * from usgallon to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double usgallon2SI(double d) {
    return d*SI_USGALLON;
  }//usgallon2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to usgallon.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2usgallon(double d) {
    return d/SI_USGALLON;
  }//SI2usgallon


  /**
   * Utility method converting an array of double values
   * from usgallon to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] usgallon2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_USGALLON;
    }
    return da;
  }//usgallon2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to usgallon.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2usgallon(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_USGALLON;
    }
    return da;
  }//SI2usgallon


  /**
   * Utility method converting a double value
   * from cubicft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double cubicft2SI(double d) {
    return d*SI_CUBICFT;
  }//cubicft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to cubicft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2cubicft(double d) {
    return d/SI_CUBICFT;
  }//SI2cubicft


  /**
   * Utility method converting an array of double values
   * from cubicft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] cubicft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_CUBICFT;
    }
    return da;
  }//cubicft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to cubicft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2cubicft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_CUBICFT;
    }
    return da;
  }//SI2cubicft


  /**
   * Utility method converting a double value
   * from cubiccm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double cubiccm2SI(double d) {
    return d*SI_CUBICCM;
  }//cubiccm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to cubiccm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2cubiccm(double d) {
    return d/SI_CUBICCM;
  }//SI2cubiccm


  /**
   * Utility method converting an array of double values
   * from cubiccm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] cubiccm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_CUBICCM;
    }
    return da;
  }//cubiccm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to cubiccm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2cubiccm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_CUBICCM;
    }
    return da;
  }//SI2cubiccm


  /**
   * Utility method converting a double value
   * from ussack to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ussack2SI(double d) {
    return d*SI_USSACK;
  }//ussack2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ussack.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ussack(double d) {
    return d/SI_USSACK;
  }//SI2ussack


  /**
   * Utility method converting an array of double values
   * from ussack to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ussack2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_USSACK;
    }
    return da;
  }//ussack2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ussack.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ussack(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_USSACK;
    }
    return da;
  }//SI2ussack


  /**
   * Utility method converting a double value
   * from ml to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ml2SI(double d) {
    return d*SI_ML;
  }//ml2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ml.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ml(double d) {
    return d/SI_ML;
  }//SI2ml


  /**
   * Utility method converting an array of double values
   * from ml to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ml2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_ML;
    }
    return da;
  }//ml2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ml.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ml(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_ML;
    }
    return da;
  }//SI2ml




  /**
   *  Constant conversion factor
   */
   public static final double SI_L=0.001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_BBL=0.1589872949;
  /**
   *  Constant conversion factor
   */
   public static final double SI_USGALLON=0.0037854;
  /**
   *  Constant conversion factor
   */
   public static final double SI_CUBICFT=0.02831685;
  /**
   *  Constant conversion factor
   */
   public static final double SI_CUBICCM=0.000001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_USSACK=0.02831685;
  /**
   *  Constant conversion factor
   */
   public static final double SI_ML=0.000001;


 }//class VolumeConversions

