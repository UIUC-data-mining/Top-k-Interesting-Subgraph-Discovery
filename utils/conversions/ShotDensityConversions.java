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
 * units of ShotDensity.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class ShotDensityConversions {

  /**
   * Utility method converting a double value
   * from perft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double perft2SI(double d) {
    return d*SI_PERFT;
  }//perft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to perft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2perft(double d) {
    return d/SI_PERFT;
  }//SI2perft


  /**
   * Utility method converting an array of double values
   * from perft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] perft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_PERFT;
    }
    return da;
  }//perft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to perft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2perft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_PERFT;
    }
    return da;
  }//SI2perft


  /**
   * Utility method converting a double value
   * from SGper1000m to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SGper1000m2SI(double d) {
    return d*SI_SGPER1000M;
  }//SGper1000m2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to SGper1000m.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2SGper1000m(double d) {
    return d/SI_SGPER1000M;
  }//SI2SGper1000m


  /**
   * Utility method converting an array of double values
   * from SGper1000m to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SGper1000m2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_SGPER1000M;
    }
    return da;
  }//SGper1000m2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to SGper1000m.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2SGper1000m(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_SGPER1000M;
    }
    return da;
  }//SI2SGper1000m




  /**
   *  Constant conversion factor
   */
   public static final double SI_PERFT=3.280839895;
  /**
   *  Constant conversion factor
   */
   public static final double SI_SGPER1000M=0.001;


 }//class ShotDensityConversions

