/*
 *  Copyright (C) 2000-2004 Roger Butenuth, Andreas Gampe,
 *                          Stefan Goetz, Sebastian Pappert,
 *                          Klaas Prause, Enno Rehling,
 *                          Sebastian Tusk, Ulrich Kuester,
 *                          Ilja Pavkovic
 *
 * This file is part of the Eressea Java Code Base, see the
 * file LICENSING for the licensing information applying to
 * this file.
 *
 */

package magellan.library.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.UIManager;

import magellan.library.utils.logging.Logger;

/**
 * This class provides static functions for jvm specific bullshit (e.g. changed focus system from
 * jvm 1.3.x to 1.4.x It also checks for necessary systems, e.g. XML
 * 
 * @deprecated All this stuff should go away.
 */
@Deprecated
public class JVMUtilities {
  private static final Logger log = Logger.getInstance(JVMUtilities.class);

  /**
   * this is a helper function to catch a jvm 1.4.2_01 bug
   */
  public static final Color getTreeSelectionBorderColor() {
    try {
      return (Color) UIManager.getDefaults().get("Tree.selectionBorderColor");
    } catch (ClassCastException cce) {
      JVMUtilities.log.warn("JVM 1.4.2_01 bug! Switching to color black!");

      if (JVMUtilities.log.isDebugEnabled()) {
        JVMUtilities.log.debug("JVM 1.4.2_01 bug: class "
            + UIManager.getDefaults().get("Tree.selectionBorderColor").getClass().getName()
            + " instead of Color!");
      }

      return Color.black;
    }
  }

  /**
   * Request the focus in the current window.
   * 
   * @deprecated Since project default was Java 8 just use aObj.requestFocusInWindow();
   */
  @Deprecated
  public static final boolean requestFocusInWindow(Component aObj) {
    aObj.requestFocusInWindow();
    return false;
  }

  /**
   * This function calls Component.setFocusableWindowState (in java &ge; 1.4) to keep GUI consistent
   * with java 1.3.x
   * 
   * @deprecated Since project default was Java 8 just use the method.
   */
  @Deprecated
  public static final void setFocusableWindowState(Window aObj, boolean aFlag) {
    aObj.setFocusableWindowState(aFlag);
  }

  /** DOCUMENT-ME */
  public static final int UNKNOWN = -1;

  /** DOCUMENT-ME */
  public static final int NORMAL = 0;

  /** DOCUMENT-ME */
  public static final int ICONIFIED = 1;

  /** DOCUMENT-ME */
  public static final int MAXIMIZED_HORIZ = 2;

  /** DOCUMENT-ME */
  public static final int MAXIMIZED_VERT = 4;

  /** DOCUMENT-ME */
  public static final int MAXIMIZED_BOTH = JVMUtilities.MAXIMIZED_VERT
      | JVMUtilities.MAXIMIZED_HORIZ;

  /**
   * This function calls Frame.getExtendedState (in java &ge; 1.4) to keep GUI consistent with java
   * 1.3.x
   * 
   * @deprecated Since project default was Java 8 just use the method.
   */
  @Deprecated
  public static final int getExtendedState(Frame aObj) {
    return aObj.getExtendedState();
  }

  /**
   * This function calls Frame.setExtendedState (in java &ge; 1.4) to keep GUI consistent with java
   * 1.3.x
   * 
   * @deprecated Since project default was Java 8 just use the method.
   */
  @Deprecated
  public static final boolean setExtendedState(Frame aObj, int state) {
    if (state == JVMUtilities.UNKNOWN)
      return false;
    aObj.setExtendedState(state);
    return true;
  }

  /** 'true' iff the current runtime version is 1.2 or later */
  public static final boolean JRE_1_2_PLUS = JVMUtilities.checkForJRE_1_2_PLUS();

  /** 'true' iff the current runtime version is 1.3 or later */
  public static final boolean JRE_1_3_PLUS = JVMUtilities.checkForJRE_1_3_PLUS();

  /** 'true' iff the current runtime version is 1.4 or later */
  public static final boolean JRE_1_4_PLUS = JVMUtilities.checkForJRE_1_4_PLUS();

  static {
    if (JVMUtilities.log.isDebugEnabled()) {
      JVMUtilities.log.debug("Check for JRE: JRE_1_2_PLUS: " + JVMUtilities.JRE_1_2_PLUS);
      JVMUtilities.log.debug("Check for JRE: JRE_1_3_PLUS: " + JVMUtilities.JRE_1_3_PLUS);
      JVMUtilities.log.debug("Check for JRE: JRE_1_4_PLUS: " + JVMUtilities.JRE_1_4_PLUS);
      JVMUtilities.log.debug("Check for JRE done");
    }
  }

  private static boolean checkForJRE_1_4_PLUS() {
    try {
      // this would be a test without Class.forName, we are too stupid so we use the Class.forName
      // test
      // " ".subSequence (0, 0);
      String.class.getMethod("subSequence", new Class[] { Integer.TYPE, Integer.TYPE });

      return true;
    } catch (Throwable ignore) {
      return false;
    }
  }

  private static boolean checkForJRE_1_2_PLUS() {
    return (SecurityManager.class.getModifiers() & 0x0400) == 0;
  }

  private static boolean checkForJRE_1_3_PLUS() {
    try {
      // this would be a test without Class.forName, we are too stupid so we use the Class.forName
      // test
      Class.forName("java.lang.StrictMath");

      // StrictMath.abs (1.0);
      return true;
    } catch (Throwable ignore) {
      return false;
    }
  }

  /** !null iff the current system has a functional xml parser */
  public static final String XML_PARSER_FOUND = JVMUtilities.checkForXML_Parser();

  /**
   * Returns the String of a found (and tested) xml parser
   */
  private static String checkForXML_Parser() {
    return null;
  }
}
