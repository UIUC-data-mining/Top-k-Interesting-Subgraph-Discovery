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
 * units of Velocity.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class VelocityConversions {

  /**
   * Utility method converting a double value
   * from milesperh to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double milesperh2SI(double d) {
    return d*SI_MILESPERH;
  }//milesperh2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to milesperh.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2milesperh(double d) {
    return d/SI_MILESPERH;
  }//SI2milesperh


  /**
   * Utility method converting an array of double values
   * from milesperh to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] milesperh2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MILESPERH;
    }
    return da;
  }//milesperh2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to milesperh.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2milesperh(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MILESPERH;
    }
    return da;
  }//SI2milesperh


  /**
   * Utility method converting a double value
   * from ftperh to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ftperh2SI(double d) {
    return d*SI_FTPERH;
  }//ftperh2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ftperh.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ftperh(double d) {
    return d/SI_FTPERH;
  }//SI2ftperh


  /**
   * Utility method converting an array of double values
   * from ftperh to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ftperh2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_FTPERH;
    }
    return da;
  }//ftperh2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ftperh.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ftperh(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_FTPERH;
    }
    return da;
  }//SI2ftperh


  /**
   * Utility method converting a double value
   * from mpermin to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double mpermin2SI(double d) {
    return d*SI_MPERMIN;
  }//mpermin2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to mpermin.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2mpermin(double d) {
    return d/SI_MPERMIN;
  }//SI2mpermin


  /**
   * Utility method converting an array of double values
   * from mpermin to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] mpermin2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MPERMIN;
    }
    return da;
  }//mpermin2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to mpermin.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2mpermin(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MPERMIN;
    }
    return da;
  }//SI2mpermin


  /**
   * Utility method converting a double value
   * from mperh to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double mperh2SI(double d) {
    return d*SI_MPERH;
  }//mperh2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to mperh.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2mperh(double d) {
    return d/SI_MPERH;
  }//SI2mperh


  /**
   * Utility method converting an array of double values
   * from mperh to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] mperh2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MPERH;
    }
    return da;
  }//mperh2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to mperh.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2mperh(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MPERH;
    }
    return da;
  }//SI2mperh


  /**
   * Utility method converting a double value
   * from ftpermin to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ftpermin2SI(double d) {
    return d*SI_FTPERMIN;
  }//ftpermin2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ftpermin.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ftpermin(double d) {
    return d/SI_FTPERMIN;
  }//SI2ftpermin


  /**
   * Utility method converting an array of double values
   * from ftpermin to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ftpermin2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_FTPERMIN;
    }
    return da;
  }//ftpermin2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ftpermin.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ftpermin(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_FTPERMIN;
    }
    return da;
  }//SI2ftpermin


  /**
   * Utility method converting a double value
   * from ftpersec to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ftpersec2SI(double d) {
    return d*SI_FTPERSEC;
  }//ftpersec2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ftpersec.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ftpersec(double d) {
    return d/SI_FTPERSEC;
  }//SI2ftpersec


  /**
   * Utility method converting an array of double values
   * from ftpersec to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ftpersec2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_FTPERSEC;
    }
    return da;
  }//ftpersec2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ftpersec.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ftpersec(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_FTPERSEC;
    }
    return da;
  }//SI2ftpersec


  /**
   * Utility method converting a double value
   * from kmperh to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double kmperh2SI(double d) {
    return d*SI_KMPERH;
  }//kmperh2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to kmperh.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2kmperh(double d) {
    return d/SI_KMPERH;
  }//SI2kmperh


  /**
   * Utility method converting an array of double values
   * from kmperh to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] kmperh2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KMPERH;
    }
    return da;
  }//kmperh2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to kmperh.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2kmperh(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KMPERH;
    }
    return da;
  }//SI2kmperh




  /**
   *  Constant conversion factor
   */
   public static final double SI_MILESPERH=0.4470400000109;
  /**
   *  Constant conversion factor
   */
   public static final double SI_FTPERH=0.00008466667;
  /**
   *  Constant conversion factor
   */
   public static final double SI_MPERMIN=0.01666666666667;
  /**
   *  Constant conversion factor
   */
   public static final double SI_MPERH=0.0002777777778;
  /**
   *  Constant conversion factor
   */
   public static final double SI_FTPERMIN=0.00508;
  /**
   *  Constant conversion factor
   */
   public static final double SI_FTPERSEC=0.3048;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KMPERH=0.27778;


 }//class VelocityConversions

