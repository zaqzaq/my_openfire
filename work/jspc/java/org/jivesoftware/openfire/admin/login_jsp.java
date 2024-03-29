package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.admin.AdminConsole;
import org.jivesoftware.openfire.admin.AdminManager;
import org.jivesoftware.openfire.clearspace.ClearspaceManager;
import org.jivesoftware.openfire.cluster.ClusterManager;
import org.jivesoftware.openfire.container.AdminConsolePlugin;
import org.xmpp.packet.JID;
import org.jivesoftware.openfire.auth.*;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.util.*;
import org.jivesoftware.admin.LoginLimitManager;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    static String go(String url) {
        if (url == null) {
            return "index.jsp";
        }
        else {
            return url;
        }
    }

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_fmt_message_key_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');
      org.jivesoftware.util.WebManager admin = null;
      synchronized (_jspx_page_context) {
        admin = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("admin", PageContext.PAGE_SCOPE);
        if (admin == null){
          admin = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("admin", admin, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');
 admin.init(request, response, session, application, out ); 
      out.write("\r\n\r\n");
      out.write("\r\n\r\n");
      out.write('\r');
      out.write('\n');

    if (admin.isSetupMode()) {
        response.sendRedirect("setup/index.jsp");
        return;
    }

      out.write("\r\n\r\n");
 // get parameters
    String username = ParamUtils.getParameter(request, "username");

    String password = ParamUtils.getParameter(request, "password");
    String url = ParamUtils.getParameter(request, "url");
    url = org.jivesoftware.util.StringUtils.escapeHTMLTags(url);

    // SSO between cluster nodes
    String secret = ParamUtils.getParameter(request, "secret");
    String nodeID = ParamUtils.getParameter(request, "nodeID");
    String nonce = ParamUtils.getParameter(request, "nonce");

    // The user auth token:
    AuthToken authToken = null;

    // Check the request/response for a login token

    Map<String, String> errors = new HashMap<String, String>();

    if (ParamUtils.getBooleanParameter(request, "login")) {
        String loginUsername = username;
        if (loginUsername != null) {
            loginUsername = JID.escapeNode(loginUsername);
        }
        try {
            if (secret != null && nodeID != null) {
                if (StringUtils.hash(AdminConsolePlugin.secret).equals(secret) && ClusterManager.isClusterMember(Base64.decode(nodeID, Base64.URL_SAFE))) {
                    authToken = new AuthToken(loginUsername);
                }
                else if ("clearspace".equals(nodeID) && ClearspaceManager.isEnabled()) {
                    ClearspaceManager csmanager = ClearspaceManager.getInstance();
                    String sharedSecret = csmanager.getSharedSecret();
                    if (nonce == null || sharedSecret == null || !csmanager.isValidNonce(nonce) ||
                            !StringUtils.hash(loginUsername + ":" + sharedSecret + ":" + nonce).equals(secret)) {
                        throw new UnauthorizedException("SSO failed. Invalid secret was provided");
                    }
                    authToken = new AuthToken(loginUsername);
                }
                else {
                    throw new UnauthorizedException("SSO failed. Invalid secret or node ID was provided");
                }
            }
            else {
                // Check that a username was provided before trying to verify credentials
                if (loginUsername != null) {
                    if (LoginLimitManager.getInstance().hasHitConnectionLimit(loginUsername, request.getRemoteAddr())) {
                        throw new UnauthorizedException("User '" + loginUsername +"' or address '" + request.getRemoteAddr() + "' has his login attempt limit.");
                    }
                    if (!AdminManager.getInstance().isUserAdmin(loginUsername, true)) {
                        throw new UnauthorizedException("User '" + loginUsername + "' not allowed to login.");
                    }
                    authToken = AuthFactory.authenticate(loginUsername, password);
                }
                else {
                    errors.put("unauthorized", LocaleUtils.getLocalizedString("login.failed.unauthorized"));
                }
            }
            if (errors.isEmpty()) {
                LoginLimitManager.getInstance().recordSuccessfulAttempt(loginUsername, request.getRemoteAddr());
                session.setAttribute("jive.admin.authToken", authToken);
                response.sendRedirect(go(url));
                return;
            }
        }
        catch (ConnectionException ue) {
            Log.debug(ue);
            if (ClearspaceManager.isEnabled()) {
                if (session.getAttribute("prelogin.setup.error.firstTime.connection") != null) {
                    session.removeAttribute("prelogin.setup.error.firstTime.connection");
                    session.setAttribute("prelogin.setup.error", "prelogin.setup.error.clearspace.connection");
                    session.setAttribute("prelogin.setup.sidebar", "true");
                    session.setAttribute("prelogin.setup.sidebar.title", "prelogin.setup.sidebar.title.clearspace");
                    session.setAttribute("prelogin.setup.sidebar.link", "clearspace-integration-prelogin.jsp");
                    response.sendRedirect(go("setup/clearspace-integration-prelogin.jsp"));
                } else {
                   session.setAttribute("prelogin.setup.error.firstTime.connection", true);
                   errors.put("connection", LocaleUtils.getLocalizedString("login.failed.connection.clearspace"));
                }
            } else {
                errors.put("connection", LocaleUtils.getLocalizedString("login.failed.connection"));
            }
        }
        catch (InternalUnauthenticatedException ue) {
            Log.debug(ue);
            if (ClearspaceManager.isEnabled()) {
                if (session.getAttribute("prelogin.setup.error.firstTime.sharedsecret") != null) {
                    session.removeAttribute("prelogin.setup.error.firstTime.sharedsecret");
                    session.setAttribute("prelogin.setup.error", "prelogin.setup.error.clearspace.sharedsecret");
                    session.setAttribute("prelogin.setup.sidebar", "true");
                    session.setAttribute("prelogin.setup.sidebar.title", "prelogin.setup.sidebar.title.clearspace");
                    session.setAttribute("prelogin.setup.sidebar.link", "clearspace-integration-prelogin.jsp");
                    response.sendRedirect(go("setup/clearspace-integration-prelogin.jsp"));
                } else {
                   session.setAttribute("prelogin.setup.error.firstTime.sharedsecret", true); 
                   errors.put("authentication", LocaleUtils.getLocalizedString("login.failed.authentication.clearspace"));
                }
            } else {
                errors.put("authentication", LocaleUtils.getLocalizedString("login.failed.authentication"));
            }
        }
        catch (UnauthorizedException ue) {
            Log.debug(ue);
            LoginLimitManager.getInstance().recordFailedAttempt(username, request.getRemoteAddr());
            errors.put("unauthorized", LocaleUtils.getLocalizedString("login.failed.unauthorized"));
        }
    }

    // Escape HTML tags in username to prevent cross-site scripting attacks. This
    // is necessary because we display the username in the page below.
    username = org.jivesoftware.util.StringUtils.escapeHTMLTags(username);


      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n\r\n<html>\r\n<head>\r\n\t<title>");
      out.print( AdminConsole.getAppName() );
      out.write(' ');
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n\t<script language=\"JavaScript\" type=\"text/javascript\">\r\n\t\t<!--\r\n\t\t// break out of frames\r\n\t\tif (self.parent.frames.length != 0) {\r\n\t\t\tself.parent.location=document.location;\r\n\t\t}\r\n        function updateFields(el) {\r\n            if (el.checked) {\r\n                document.loginForm.username.disabled = true;\r\n                document.loginForm.password.disabled = true;\r\n            }\r\n            else {\r\n                document.loginForm.username.disabled = false;\r\n                document.loginForm.password.disabled = false;\r\n                document.loginForm.username.focus();\r\n            }\r\n        }\r\n\t\t//-->\r\n\t</script>\r\n    <link rel=\"stylesheet\" href=\"style/global.css\" type=\"text/css\">\r\n    <link rel=\"stylesheet\" href=\"style/login.css\" type=\"text/css\">\r\n</head>\r\n\r\n<body>\r\n\r\n<form action=\"login.jsp\" name=\"loginForm\" method=\"post\">\r\n\r\n");
  if (url != null) { try { 
      out.write("\r\n\r\n    <input type=\"hidden\" name=\"url\" value=\"");
      out.print( url );
      out.write("\">\r\n\r\n");
  } catch (Exception e) { Log.error(e); } } 
      out.write("\r\n\r\n<input type=\"hidden\" name=\"login\" value=\"true\">\r\n\r\n<div align=\"center\">\r\n    <!-- BEGIN login box -->\r\n    <div id=\"jive-loginBox\">\r\n        \r\n        <div align=\"center\" id=\"jive-loginTable\">\r\n\r\n            <span id=\"jive-login-header\" style=\"background: transparent url(images/login_logo.gif) no-repeat left; padding: 29px 0 10px 205px;\">\r\n            ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n\r\n            <div style=\"text-align: center; width: 380px;\">\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\r\n                <tr>\r\n                    <td align=\"right\" class=\"loginFormTable\">\r\n\r\n                        <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\">\r\n                        <noscript>\r\n                            <tr>\r\n                                <td colspan=\"3\">\r\n                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                                    <tr valign=\"top\">\r\n                                        <td><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\" vspace=\"2\"></td>\r\n                                        <td><div class=\"jive-error-text\" style=\"padding-left:5px; color:#cc0000;\">");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</div></td>\r\n                                    </tr>\r\n                                    </table>\r\n                                </td>\r\n                            </tr>\r\n                        </noscript>\r\n                        ");
  if (errors.size() > 0) { 
      out.write("\r\n                            <tr>\r\n                                <td colspan=\"3\">\r\n                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                                        ");
 for (String error:errors.values()) { 
      out.write("\r\n                                    <tr valign=\"top\">\r\n                                        <td><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\" vspace=\"2\"></td>\r\n                                        <td><div class=\"jive-error-text\" style=\"padding-left:5px; color:#cc0000;\">");
      out.print( error);
      out.write("</div></td>\r\n                                    </tr>\r\n                                        ");
 } 
      out.write("\r\n                                    </table>\r\n                                </td>\r\n                            </tr>\r\n                        ");
  } 
      out.write("\r\n                        <tr>\r\n                            <td><input type=\"text\" name=\"username\" size=\"15\" maxlength=\"50\" id=\"u01\" value=\"");
      out.print( (username != null ? StringUtils.removeXSSCharacters(username) : "") );
      out.write("\"></td>\r\n                            <td><input type=\"password\" name=\"password\" size=\"15\" maxlength=\"50\" id=\"p01\"></td>\r\n                            <td align=\"center\"><input type=\"submit\" value=\"&nbsp; ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write(" &nbsp;\"></td>\r\n                        </tr>\r\n                        <tr valign=\"top\">\r\n                            <td class=\"jive-login-label\"><label for=\"u01\">");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</label></td>\r\n                            <td class=\"jive-login-label\"><label for=\"p01\">");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</label></td>\r\n                            <td>&nbsp;</td>\r\n                        </tr>\r\n                        </table>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td align=\"right\">\r\n                        <div align=\"right\" id=\"jive-loginVersion\">\r\n                        ");
      out.print( AdminConsole.getAppName() );
      out.write(',');
      out.write(' ');
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write(':');
      out.write(' ');
      out.print( AdminConsole.getVersionString() );
      out.write("\r\n                        </div>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n            </div>\r\n        </div>\r\n\r\n    </div>\r\n    <!-- END login box -->\r\n</div>\r\n\r\n</form>\r\n\r\n<script language=\"JavaScript\" type=\"text/javascript\">\r\n<!--\r\n    if (document.loginForm.username.value == '')  {\r\n\t    document.loginForm.username.focus();\r\n    } else {\r\n        document.loginForm.password.focus();\r\n    }\r\n//-->\r\n</script>\r\n\r\n</body>\r\n</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_fmt_message_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_0 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_0.setParent(null);
    _jspx_th_fmt_message_0.setKey("login.title");
    int _jspx_eval_fmt_message_0 = _jspx_th_fmt_message_0.doStartTag();
    if (_jspx_th_fmt_message_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
    return false;
  }

  private boolean _jspx_meth_fmt_message_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_1 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_1.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_1.setParent(null);
    _jspx_th_fmt_message_1.setKey("admin.console");
    int _jspx_eval_fmt_message_1 = _jspx_th_fmt_message_1.doStartTag();
    if (_jspx_th_fmt_message_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
    return false;
  }

  private boolean _jspx_meth_fmt_message_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_2 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_2.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_2.setParent(null);
    _jspx_th_fmt_message_2.setKey("login.error");
    int _jspx_eval_fmt_message_2 = _jspx_th_fmt_message_2.doStartTag();
    if (_jspx_th_fmt_message_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
    return false;
  }

  private boolean _jspx_meth_fmt_message_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_3 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_3.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_3.setParent(null);
    _jspx_th_fmt_message_3.setKey("login.login");
    int _jspx_eval_fmt_message_3 = _jspx_th_fmt_message_3.doStartTag();
    if (_jspx_th_fmt_message_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
    return false;
  }

  private boolean _jspx_meth_fmt_message_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_4 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_4.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_4.setParent(null);
    _jspx_th_fmt_message_4.setKey("login.username");
    int _jspx_eval_fmt_message_4 = _jspx_th_fmt_message_4.doStartTag();
    if (_jspx_th_fmt_message_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
    return false;
  }

  private boolean _jspx_meth_fmt_message_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_5 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_5.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_5.setParent(null);
    _jspx_th_fmt_message_5.setKey("login.password");
    int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
    if (_jspx_th_fmt_message_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
    return false;
  }

  private boolean _jspx_meth_fmt_message_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_6 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_6.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_6.setParent(null);
    _jspx_th_fmt_message_6.setKey("login.version");
    int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
    if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
    return false;
  }
}
