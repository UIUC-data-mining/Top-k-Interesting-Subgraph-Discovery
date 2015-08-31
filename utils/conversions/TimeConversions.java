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
 * units of Time.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class TimeConversions {

  /**
   * Utility method converting a double value
   * from hour to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double hour2SI(double d) {
    return d*SI_HOUR;
  }//hour2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to hour.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2hour(double d) {
    return d/SI_HOUR;
  }//SI2hour


  /**
   * Utility method converting an array of double values
   * from hour to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] hour2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_HOUR;
    }
    return da;
  }//hour2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to hour.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2hour(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_HOUR;
    }
    return da;
  }//SI2hour


  /**
   * Utility method converting a double value
   * from day to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double day2SI(double d) {
    return d*SI_DAY;
  }//day2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to day.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2day(double d) {
    return d/SI_DAY;
  }//SI2day


  /**
   * Utility method converting an array of double values
   * from day to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] day2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_DAY;
    }
    return da;
  }//day2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to day.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2day(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_DAY;
    }
    return da;
  }//SI2day


  /**
   * Utility method converting a double value
   * from min to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double min2SI(double d) {
    return d*SI_MIN;
  }//min2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to min.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2min(double d) {
    return d/SI_MIN;
  }//SI2min


  /**
   * Utility method converting an array of double values
   * from min to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] min2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MIN;
    }
    return da;
  }//min2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to min.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2min(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MIN;
    }
    return da;
  }//SI2min


  /**
   * Utility method converting a double value
   * from week to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double week2SI(double d) {
    return d*SI_WEEK;
  }//week2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to week.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2week(double d) {
    return d/SI_WEEK;
  }//SI2week


  /**
   * Utility method converting an array of double values
   * from week to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] week2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_WEEK;
    }
    return da;
  }//week2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to week.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2week(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_WEEK;
    }
    return da;
  }//SI2week




  /**
   *  Constant conversion factor
   */
   public static final double SI_HOUR=3600;
  /**
   *  Constant conversion factor
   */
   public static final double SI_DAY=86400;
  /**
   *  Constant conversion factor
   */
   public static final double SI_MIN=60;
  /**
   *  Constant conversion factor
   */
   public static final double SI_WEEK=604800;


 }//class TimeConversions

