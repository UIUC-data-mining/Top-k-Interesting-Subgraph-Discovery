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
 * units of BuildUpDropOffRate.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class BuildUpDropOffRateConversions {

  /**
   * Utility method converting a double value
   * from degperft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double degperft2SI(double d) {
    return d*SI_DEGPERFT;
  }//degperft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to degperft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2degperft(double d) {
    return d/SI_DEGPERFT;
  }//SI2degperft


  /**
   * Utility method converting an array of double values
   * from degperft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] degperft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_DEGPERFT;
    }
    return da;
  }//degperft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to degperft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2degperft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_DEGPERFT;
    }
    return da;
  }//SI2degperft


  /**
   * Utility method converting a double value
   * from degper100ft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double degper100ft2SI(double d) {
    return d*SI_DEGPER100FT;
  }//degper100ft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to degper100ft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2degper100ft(double d) {
    return d/SI_DEGPER100FT;
  }//SI2degper100ft


  /**
   * Utility method converting an array of double values
   * from degper100ft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] degper100ft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_DEGPER100FT;
    }
    return da;
  }//degper100ft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to degper100ft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2degper100ft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_DEGPER100FT;
    }
    return da;
  }//SI2degper100ft


  /**
   * Utility method converting a double value
   * from degper30m to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double degper30m2SI(double d) {
    return d*SI_DEGPER30M;
  }//degper30m2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to degper30m.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2degper30m(double d) {
    return d/SI_DEGPER30M;
  }//SI2degper30m


  /**
   * Utility method converting an array of double values
   * from degper30m to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] degper30m2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_DEGPER30M;
    }
    return da;
  }//degper30m2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to degper30m.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2degper30m(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_DEGPER30M;
    }
    return da;
  }//SI2degper30m




  /**
   *  Constant conversion factor
   */
   public static final double SI_DEGPERFT=3.280839895;
  /**
   *  Constant conversion factor
   */
   public static final double SI_DEGPER100FT=0.03280839895;
  /**
   *  Constant conversion factor
   */
   public static final double SI_DEGPER30M=0.03333333333;


 }//class BuildUpDropOffRateConversions

