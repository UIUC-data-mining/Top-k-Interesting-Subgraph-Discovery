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
 * units of Length.
 *
 * <br>Note that this class is machine generated, using conversion factors
 * from a text file (in standard Properties format).
 *
 * @author Dieter Wimberger
 * @version 1.0
 */
public class LengthConversions {

  /**
   * Utility method converting a double value
   * from cm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double cm2SI(double d) {
    return d*SI_CM;
  }//cm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to cm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2cm(double d) {
    return d/SI_CM;
  }//SI2cm


  /**
   * Utility method converting an array of double values
   * from cm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] cm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_CM;
    }
    return da;
  }//cm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to cm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2cm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_CM;
    }
    return da;
  }//SI2cm


  /**
   * Utility method converting a double value
   * from in to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double in2SI(double d) {
    return d*SI_IN;
  }//in2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to in.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2in(double d) {
    return d/SI_IN;
  }//SI2in


  /**
   * Utility method converting an array of double values
   * from in to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] in2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_IN;
    }
    return da;
  }//in2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to in.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2in(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_IN;
    }
    return da;
  }//SI2in


  /**
   * Utility method converting a double value
   * from nautmile to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double nautmile2SI(double d) {
    return d*SI_NAUTMILE;
  }//nautmile2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to nautmile.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2nautmile(double d) {
    return d/SI_NAUTMILE;
  }//SI2nautmile


  /**
   * Utility method converting an array of double values
   * from nautmile to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] nautmile2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_NAUTMILE;
    }
    return da;
  }//nautmile2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to nautmile.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2nautmile(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_NAUTMILE;
    }
    return da;
  }//SI2nautmile


  /**
   * Utility method converting a double value
   * from km to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double km2SI(double d) {
    return d*SI_KM;
  }//km2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to km.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2km(double d) {
    return d/SI_KM;
  }//SI2km


  /**
   * Utility method converting an array of double values
   * from km to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] km2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_KM;
    }
    return da;
  }//km2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to km.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2km(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_KM;
    }
    return da;
  }//SI2km


  /**
   * Utility method converting a double value
   * from mile to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double mile2SI(double d) {
    return d*SI_MILE;
  }//mile2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to mile.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2mile(double d) {
    return d/SI_MILE;
  }//SI2mile


  /**
   * Utility method converting an array of double values
   * from mile to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] mile2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MILE;
    }
    return da;
  }//mile2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to mile.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2mile(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MILE;
    }
    return da;
  }//SI2mile


  /**
   * Utility method converting a double value
   * from one32in to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double one32in2SI(double d) {
    return d*SI_ONE32IN;
  }//one32in2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to one32in.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2one32in(double d) {
    return d/SI_ONE32IN;
  }//SI2one32in


  /**
   * Utility method converting an array of double values
   * from one32in to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] one32in2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_ONE32IN;
    }
    return da;
  }//one32in2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to one32in.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2one32in(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_ONE32IN;
    }
    return da;
  }//SI2one32in


  /**
   * Utility method converting a double value
   * from mm to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double mm2SI(double d) {
    return d*SI_MM;
  }//mm2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to mm.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2mm(double d) {
    return d/SI_MM;
  }//SI2mm


  /**
   * Utility method converting an array of double values
   * from mm to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] mm2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_MM;
    }
    return da;
  }//mm2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to mm.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2mm(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_MM;
    }
    return da;
  }//SI2mm


  /**
   * Utility method converting a double value
   * from ft to the SI or SI derived unit.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double ft2SI(double d) {
    return d*SI_FT;
  }//ft2SI


  /**
   * Utility method converting a double value
   * from SI or SI derived to ft.
   *
   * @param d double value to be converted.
   * @return double containing the converted value.
   */
  public static final double SI2ft(double d) {
    return d/SI_FT;
  }//SI2ft


  /**
   * Utility method converting an array of double values
   * from ft to the SI or SI derived unit.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] ft2SI(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] *= SI_FT;
    }
    return da;
  }//ft2SI


  /**
   * Utility method converting an array of double values
   * from SI or SI derived to ft.
   * <br>Note that the array passed in as parameter is the target.
   * The reference to this array is only returned for convenience reasons.
   *
   * @param da Array of double values to be converted.
   * @return Reference to the same array now containing converted values.
   */
  public static final double[] SI2ft(double[] da) {
    for (int n = 0; n < da.length; n++) {
      da[n] /= SI_FT;
    }
    return da;
  }//SI2ft




  /**
   *  Constant conversion factor
   */
   public static final double SI_CM=0.01;
  /**
   *  Constant conversion factor
   */
   public static final double SI_IN=0.0254;
  /**
   *  Constant conversion factor
   */
   public static final double SI_NAUTMILE=1852;
  /**
   *  Constant conversion factor
   */
   public static final double SI_KM=1000;
  /**
   *  Constant conversion factor
   */
   public static final double SI_MILE=1609.344;
  /**
   *  Constant conversion factor
   */
   public static final double SI_ONE32IN=0.00079375;
  /**
   *  Constant conversion factor
   */
   public static final double SI_MM=0.001;
  /**
   *  Constant conversion factor
   */
   public static final double SI_FT=0.3048;


 }//class LengthConversions

