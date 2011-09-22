/*
 * This program is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License, version 2 as published by the Free Software 
 * Foundation.
 *
 * You should have received a copy of the GNU General Public License along with this 
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/gpl-2.0.html 
 * or from the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */
package org.pentaho.platform.repository2.unified.jcr.sejcr;

import javax.jcr.Credentials;
import javax.jcr.SimpleCredentials;

import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.springframework.util.Assert;

/**
 * A {@link CredentialsStrategy} that creates credentials from the current {@link IPentahoSession}.
 * 
 * @author mlowery
 */
public class PentahoSessionCredentialsStrategy implements CredentialsStrategy {

  // ~ Static fields/initializers ======================================================================================

  private static final String ATTR_PRE_AUTHENTICATION_TOKEN = "pre_authentication_token"; //$NON-NLS-1$

  private static final char[] PASSWORD = "ignored".toCharArray(); //$NON-NLS-1$

  // ~ Instance fields =================================================================================================

  private String preAuthenticationToken;

  // ~ Constructors ====================================================================================================

  public PentahoSessionCredentialsStrategy(final String preAuthenticationToken) {
    super();
    Assert.hasText(preAuthenticationToken);
    this.preAuthenticationToken = preAuthenticationToken;
  }

  // ~ Methods =========================================================================================================

  public Credentials getCredentials() {
    String username = getUsername();
    SimpleCredentials creds = new SimpleCredentials(username, PASSWORD);
    creds.setAttribute(ATTR_PRE_AUTHENTICATION_TOKEN, preAuthenticationToken);
    return creds;
  }

  private String getUsername() {
    IPentahoSession pentahoSession = PentahoSessionHolder.getSession();
    Assert.state(pentahoSession != null, "this method cannot be called with a null IPentahoSession");
    return pentahoSession.getName();
  }
}