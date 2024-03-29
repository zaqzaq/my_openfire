package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class server_002dlocale_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write("\r\n\r\n");
  // Get parameters //
    String localeCode = ParamUtils.getParameter(request,"localeCode");
    String timeZoneID = ParamUtils.getParameter(request,"timeZoneID");
    boolean save = request.getParameter("save") != null;

    // TODO: We're not displaying this error ever.
    Map<String,String> errors = new HashMap<String,String>();
    if (save) {
        // Set the timezeone
        try {
            TimeZone tz = TimeZone.getTimeZone(timeZoneID);
            JiveGlobals.setTimeZone(tz);
            // Log the event
            webManager.logEvent("updated time zone to "+tz.getID(), tz.toString());
        }
        catch (Exception e) {
            Log.error(e);
        }
        if (localeCode != null) {
            Locale newLocale = LocaleUtils.localeCodeToLocale(localeCode.trim());
            if (newLocale == null) {
                errors.put("localeCode","");
            }
            else {
                JiveGlobals.setLocale(newLocale);
                // Log the event
                webManager.logEvent("updated locale to "+newLocale.getDisplayName(), null);
                response.sendRedirect("server-locale.jsp?success=true");
                return;
            }
        }
    }

    Locale locale = JiveGlobals.getLocale();

    // Get the time zone list.
    String[][] timeZones = LocaleUtils.getTimeZoneList();

    // Get the current time zone.
    TimeZone timeZone = JiveGlobals.getTimeZone();

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n        <meta name=\"pageID\" content=\"server-locale\"/>\r\n        <meta name=\"helpPage\" content=\"edit_server_properties.html\"/>\r\n    </head>\r\n    <body>\r\n\r\n<p>\r\n");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n\r\n<!-- BEGIN locale settings -->\r\n<form action=\"server-locale.jsp\" method=\"post\" name=\"sform\">\r\n\t<div class=\"jive-contentBoxHeader\">\r\n\t\t");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\r\n\t</div>\r\n\t<div class=\"jive-contentBox\">\r\n\t\t<p>\r\n        <b>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write(":</b> ");
      out.print( locale.getDisplayName(locale) );
      out.write(" /\r\n            ");
      out.print( LocaleUtils.getTimeZoneName(JiveGlobals.getTimeZone().getID(), locale) );
      out.write("\r\n        </p>\r\n\r\n        ");
  boolean usingPreset = false;
            Locale[] locales = Locale.getAvailableLocales();
            for (Locale locale1 : locales) {
                usingPreset = locale1.equals(locale);
                if (usingPreset) {
                    break;
                }
            }
        
      out.write("\r\n\r\n        <p><b>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write(":</b></p>\r\n\r\n        <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\r\n        <tbody>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"cs_CZ\" ");
      out.print( ("cs_CZ".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc01\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc01\">Czech (cs_CZ)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"de\" ");
      out.print( ("de".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc02\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc02\">Deutsch (de)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"en\" ");
      out.print( ("en".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc03\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc03\">English (en)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"es\" ");
      out.print( ("es".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc04\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc04\">Espa&ntilde;ol (es)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"fr\" ");
      out.print( ("fr".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc05\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc05\">Fran&ccedil;ais (fr)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"nl\" ");
      out.print( ("nl".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc06\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc06\">Nederlands (nl)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"pl_PL\" ");
      out.print( ("pl_PL".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc07\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc07\">Polski (pl_PL)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"pt_BR\" ");
      out.print( ("pt_BR".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc08\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc08\">Portugu&ecirc;s Brasileiro (pt_BR)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"ru_RU\" ");
      out.print( ("ru_RU".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc09\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc09\">&#x420;&#x443;&#x441;&#x441;&#x43A;&#x438;&#x439; (ru_RU)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"sk\" ");
      out.print( ("sk".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc10\" />\r\n                </td>\r\n                <td colspan=\"2\">\r\n                    <label for=\"loc10\">Sloven&#269;ina (sk)</label>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"radio\" name=\"localeCode\" value=\"zh_CN\" ");
      out.print( ("zh_CN".equals(locale.toString()) ? "checked" : "") );
      out.write("\r\n                     id=\"loc11\" />\r\n                </td>\r\n                <td>\r\n                    <a href=\"#\" onclick=\"document.sform.localeCode[1].checked=true; return false;\"><img src=\"images/language_zh_CN.gif\" border=\"0\" alt=\"\" /></a>\r\n                </td>\r\n                <td>\r\n                    <label for=\"loc11\">Simplified Chinese (zh_CN)</label>\r\n                </td>\r\n            </tr>\r\n        </tbody>\r\n        </table>\r\n\r\n        <br>\r\n\r\n        <p><b>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(":</b></p>\r\n\r\n        <select size=\"1\" name=\"timeZoneID\">\r\n        ");
 for (String[] timeZone1 : timeZones) {
            String selected = "";
            if (timeZone.getID().equals(timeZone1[0].trim())) {
                selected = " selected";
            }
        
      out.write("\r\n            <option value=\"");
      out.print( timeZone1[0] );
      out.write('"');
      out.print( selected );
      out.write('>');
      out.print( timeZone1[1] );
      out.write("\r\n                ");
  } 
      out.write("\r\n        </select>\r\n\t</div>\r\n<input type=\"submit\" name=\"save\" value=\"");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\">\r\n</form>\r\n<!-- END locale settings -->\r\n\r\n\r\n</body>\r\n</html>");
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
    _jspx_th_fmt_message_0.setKey("locale.title");
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
    _jspx_th_fmt_message_1.setKey("locale.title.info");
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
    _jspx_th_fmt_message_2.setKey("locale.system.set");
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
    _jspx_th_fmt_message_3.setKey("locale.current");
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
    _jspx_th_fmt_message_4.setKey("language.choose");
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
    _jspx_th_fmt_message_5.setKey("timezone.choose");
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
    _jspx_th_fmt_message_6.setKey("global.save_settings");
    int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
    if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
    return false;
  }
}
