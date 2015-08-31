package utils.conversions;

/**
 * Java Unit Conversion  Library (jucl)
 * Copyright (c) 2000-2003 jucl team
 *
 * jucl is free software; you can distribute itself under the
 * terms of the BSD-style license received along with the jucl
 * distribution.
 */

/**
 * Utility class containing class operations for converting
 * between SI (or SI derived standard units) and other known
 * units of Area.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class AreaConversions {

  /**
   * Utility method converting a double value
   * from acre to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double acre2SI(double d) {
    return d*SI_ACRE;
  }//acre2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to acre.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2acre(double d) {
    return d/SI_ACRE;
  }//SI2acre


  /**
   * Utility method converting an array of double values
   * from acre to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] acre2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_ACRE;
    }
    return da;
  }//acre2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to acre.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2acre(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_ACRE;
    }
    return da;
  }//SI2acre


  /**
   * Utility method converting a double value
   * from sqmm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double sqmm2SI(double d) {
    return d*SI_SQMM;
  }//sqmm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to sqmm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2sqmm(double d) {
    return d/SI_SQMM;
  }//SI2sqmm


  /**
   * Utility method converting an array of double values
   * from sqmm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] sqmm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_SQMM;
    }
    return da;
  }//sqmm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to sqmm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2sqmm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_SQMM;
    }
    return da;
  }//SI2sqmm


  /**
   * Utility method converting a double value
   * from sqft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double sqft2SI(double d) {
    return d*SI_SQFT;
  }//sqft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to sqft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2sqft(double d) {
    return d/SI_SQFT;
  }//SI2sqft


  /**
   * Utility method converting an array of double values
   * from sqft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] sqft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_SQFT;
    }
    return da;
  }//sqft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to sqft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2sqft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_SQFT;
    }
    return da;
  }//SI2sqft


  /**
   * Utility method converting a double value
   * from sqcm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double sqcm2SI(double d) {
    return d*SI_SQCM;
  }//sqcm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to sqcm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2sqcm(double d) {
    return d/SI_SQCM;
  }//SI2sqcm


  /**
   * Utility method converting an array of double values
   * from sqcm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] sqcm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_SQCM;
    }
    return da;
  }//sqcm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to sqcm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2sqcm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_SQCM;
    }
    return da;
  }//SI2sqcm


  /**
   * Utility method converting a double value
   * from sqin to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double sqin2SI(double d) {
    return d*SI_SQIN;
  }//sqin2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to sqin.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2sqin(double d) {
    return d/SI_SQIN;
  }//SI2sqin


  /**
   * Utility method converting an array of double values
   * from sqin to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] sqin2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_SQIN;
    }
    return da;
  }//sqin2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to sqin.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2sqin(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_SQIN;
    }
    return da;
  }//SI2sqin




  /**
   *  Constant conversion factor
   */
   public static final double SI_ACRE=0.00024710;
  /**
   *  Constant conversion factor
   */
   public static final double SI_SQMM=0.000001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_SQFT=10.764;
  /**
   *  Constant conversion factor
   */
   public static final double SI_SQCM=0.0001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_SQIN=0.00064516;


 }//class AreaConversions

