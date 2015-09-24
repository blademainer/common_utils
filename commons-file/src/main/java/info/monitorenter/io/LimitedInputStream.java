/*
 * LimitedInputStream.java, an InputStream wrapper that limits the bytes to read from the wrapped
 * stream.
 *
 * Copyright 2011 (C) Achim Westermann, 
 * created on Nov 27, 2011 6:07:13 PM.
 *
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 * 
 * The contents of this collection are subject to the Mozilla Public License Version 
 * 1.1 (the "License"); you may not use this file except in compliance with 
 * the License. You may obtain a copy of the License at 
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * 
 * The Original Code is the cpDetector code in [sub] packages info.monitorenter and 
 * cpdetector. 
 * 
 * The Initial Developer of the Original Code is
 * Achim Westermann <achim.westermann@gmx.de>.
 * 
 * Portions created by the Initial Developer are Copyright (c) 2007 
 * the Initial Developer. All Rights Reserved.
 * 
 * Contributor(s):
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 * 
 * ***** END LICENSE BLOCK *****
 *
 * If you modify or optimize the code in a useful way please let me know.
 * Achim.Westermann@gmx.de
 *
 */
package info.monitorenter.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class LimitedInputStream extends FilterInputStream {

  
  protected int m_amountOfBytesReadable;

  
  public LimitedInputStream(final InputStream in, final int limit) {
    super(in);
    this.m_amountOfBytesReadable = limit;
  }

  
  @Override
  public int available() throws IOException {
    int result;
    
    if (this.m_amountOfBytesReadable == 0) {
      result = 0; // EOF
    } else {
      result = super.available();
      if (this.m_amountOfBytesReadable < result) {
        result = this.m_amountOfBytesReadable;
      }
    }
    return result;
  }

  
  @Override
  public int read() throws IOException {

    int result;
    if (this.m_amountOfBytesReadable == 0) {
      result = -1; // EOF
    } else {
      result = super.read();
      if (result >= 0) {
        this.m_amountOfBytesReadable--;
      }
    }
    return result;
  }

  
  @Override
  public int read(final byte b[], final int off, final int len) throws IOException {

    int result;
    int bytesToRead = len;
    if (this.m_amountOfBytesReadable == 0) {
      result = -1; // EOF
    } else{
      if (this.m_amountOfBytesReadable < len) {
        bytesToRead = this.m_amountOfBytesReadable; // limit
      }
      result = super.read(b, off, bytesToRead);
      if (result > 0) {
        this.m_amountOfBytesReadable -= result;
      }
    }
    return result;
  }

  @Override
  public long skip(final long howManyBytes) throws IOException {

    long result;
    long bytesToSkip = howManyBytes;
    if (this.m_amountOfBytesReadable == 0) {
      result = 0; // EOF
    } else {
      if (this.m_amountOfBytesReadable < howManyBytes) {
        bytesToSkip = this.m_amountOfBytesReadable;
      }
      result = super.skip(howManyBytes);
      this.m_amountOfBytesReadable -= result;
    }
    return result;
  }

}
