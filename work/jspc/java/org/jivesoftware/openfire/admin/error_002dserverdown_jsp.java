package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.admin.AdminConsole;
import org.jivesoftware.util.LocaleUtils;

public final class error_002dserverdown_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write(' ');
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      org.jivesoftware.admin.AdminPageBean pageinfo = null;
      synchronized (request) {
        pageinfo = (org.jivesoftware.admin.AdminPageBean) _jspx_page_context.getAttribute("pageinfo", PageContext.REQUEST_SCOPE);
        if (pageinfo == null){
          pageinfo = new org.jivesoftware.admin.AdminPageBean();
          _jspx_page_context.setAttribute("pageinfo", pageinfo, PageContext.REQUEST_SCOPE);
        }
      }
      out.write("\r\n\r\n");
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
  String path = request.getContextPath();

    // Title of this page
    String title = AdminConsole.getAppName() + " " +LocaleUtils.getLocalizedString("error.serverdown.title");
    pageinfo.setTitle(title);

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n\r\n<html>\r\n<head>\r\n <title>");
      out.print( AdminConsole.getAppName() );
      out.write(' ');
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.print( (pageinfo.getTitle() != null ? (": "+pageinfo.getTitle()) : "") );
      out.write("</title>\r\n <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n <link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print( path );
      out.write("/style/global.css\">\r\n</head>\r\n\r\n<body>\r\n\r\n<div id=\"jive-header\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n<tbody>\r\n    <tr>\r\n     <td>\r\n         <img src=\"");
      out.print( path );
      out.write('/');
      out.print( AdminConsole.getLogoImage() );
      out.write("\" border=\"0\" alt=\"");
      out.print( AdminConsole.getAppName() );
      out.write(' ');
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\">\r\n     </td>\r\n     <td align=\"right\">\r\n         <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n         <tr>\r\n             <td>&nbsp;</td>\r\n             <td class=\"info\">\r\n                 <nobr>");
      out.print( AdminConsole.getAppName() );
      out.write(' ');
      out.print( AdminConsole.getVersionString() );
      out.write("</nobr>\r\n             </td>\r\n         </tr>\r\n         </table>\r\n     </td>\r\n    </tr>\r\n</tbody>\r\n</table>\r\n</div>\r\n\r\n<div id=\"jive-main\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n<tbody>\r\n    <tr valign=\"top\">\r\n        <td width=\"1%\">\r\n            <div id=\"jive-sidebar\">\r\n                <img src=\"");
      out.print( path );
      out.write("/images/blank.gif\" width=\"5\" height=\"1\" border=\"0\" alt=\"\">\r\n            </div>\r\n        </td>\r\n        <td width=\"99%\" id=\"jive-content\">\r\n\r\n        <div id=\"jive-title\">\r\n            ");
      out.print( title );
      out.write("\r\n        </div>\r\n\r\n        <p>\r\n        ");
      out.print( AdminConsole.getAppName() );
      out.write(' ');
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\r\n        </p>\r\n\r\n        <ol>\r\n            <li>\r\n                ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\r\n            </li>\r\n            <li>\r\n                <a href=\"index.jsp\">");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</a>.\r\n            </li>\r\n        </ol>\r\n\r\n        </td>\r\n    </tr>\r\n</tbody>\r\n</table>\r\n</div>\r\n\r\n</body>\r\n</html>");
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
    _jspx_th_fmt_message_0.setKey("error.serverdown.admin_console");
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
    _jspx_th_fmt_message_1.setKey("error.serverdown.admin_console");
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
    _jspx_th_fmt_message_2.setKey("error.serverdown.is_down");
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
    _jspx_th_fmt_message_3.setKey("error.serverdown.start");
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
    _jspx_th_fmt_message_4.setKey("error.serverdown.login");
    int _jspx_eval_fmt_message_4 = _jspx_th_fmt_message_4.doStartTag();
    if (_jspx_th_fmt_message_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
    return false;
  }
}
