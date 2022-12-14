/*
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page https://github.com/happyfish100/fastdfs for more detail.
*/

package org.csource.common;

/**
 * My Exception
 *
 * @author Happy Fish / YuQing
 * @version Version 1.0
 */
public class FdfsException extends Exception {

  public FdfsException(String s, Exception e) {
  }

  public FdfsException(String message) {
    super(message);
  }
}
