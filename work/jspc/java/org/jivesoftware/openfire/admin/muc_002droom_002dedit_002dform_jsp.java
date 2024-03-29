package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import java.text.DateFormat;
import java.util.*;
import org.jivesoftware.openfire.muc.MUCRoom;
import org.jivesoftware.openfire.forms.spi.*;
import org.jivesoftware.openfire.forms.*;
import org.dom4j.Element;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Message;
import org.xmpp.packet.JID;
import gnu.inet.encoding.Stringprep;
import gnu.inet.encoding.StringprepException;
import java.net.URLEncoder;
import org.jivesoftware.openfire.muc.NotAllowedException;
import org.jivesoftware.openfire.muc.MultiUserChatService;

public final class muc_002droom_002dedit_002dform_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
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
 webManager.init(request, response, session, application, out); 
      out.write("\r\n\r\n");
  // Get parameters
    boolean create = ParamUtils.getBooleanParameter(request,"create");
    boolean save = ParamUtils.getBooleanParameter(request,"save");
    boolean success = ParamUtils.getBooleanParameter(request,"success");
    boolean addsuccess = ParamUtils.getBooleanParameter(request,"addsuccess");
    String roomName = ParamUtils.getParameter(request,"roomName");
    String mucName = ParamUtils.getParameter(request,"mucName");
    String roomJIDStr = ParamUtils.getParameter(request,"roomJID");
    JID roomJID = null;
    if (roomName != null && mucName != null) {
        roomJID = new JID(roomName, mucName, null);
    }
    else if (roomJIDStr != null) {
        roomJID = new JID(roomJIDStr);
        roomName = roomJID.getNode();
        mucName = roomJID.getDomain();
    }
    String naturalName = ParamUtils.getParameter(request,"roomconfig_roomname");
    String description = ParamUtils.getParameter(request,"roomconfig_roomdesc");
    String maxUsers = ParamUtils.getParameter(request, "roomconfig_maxusers");
    String broadcastModerator = ParamUtils.getParameter(request, "roomconfig_presencebroadcast");
    String broadcastParticipant = ParamUtils.getParameter(request, "roomconfig_presencebroadcast2");
    String broadcastVisitor = ParamUtils.getParameter(request, "roomconfig_presencebroadcast3");
    String password = ParamUtils.getParameter(request, "roomconfig_roomsecret");
    String confirmPassword = ParamUtils.getParameter(request, "roomconfig_roomsecret2");
    String whois = ParamUtils.getParameter(request, "roomconfig_whois");
    String publicRoom = ParamUtils.getParameter(request, "roomconfig_publicroom");
    String persistentRoom = ParamUtils.getParameter(request, "roomconfig_persistentroom");
    String moderatedRoom = ParamUtils.getParameter(request, "roomconfig_moderatedroom");
    String membersOnly = ParamUtils.getParameter(request, "roomconfig_membersonly");
    String allowInvites = ParamUtils.getParameter(request, "roomconfig_allowinvites");
    String changeSubject = ParamUtils.getParameter(request, "roomconfig_changesubject");
    String enableLog = ParamUtils.getParameter(request, "roomconfig_enablelogging");
    String reservedNick = ParamUtils.getParameter(request, "roomconfig_reservednick");
    String canChangeNick = ParamUtils.getParameter(request, "roomconfig_canchangenick");
    String registrationEnabled = ParamUtils.getParameter(request, "roomconfig_registration");
    String roomSubject = ParamUtils.getParameter(request, "room_topic", true);

    if (webManager.getMultiUserChatManager().getMultiUserChatServicesCount() < 1) {
        // No services exist, so redirect to where one can configure the services
        response.sendRedirect("muc-service-summary.jsp");
        return;
    }

    // Handle a cancel
    if (request.getParameter("cancel") != null) {
        response.sendRedirect("muc-room-summary.jsp?roomJID="+URLEncoder.encode(roomJID.toBareJID(), "UTF-8"));
        return;
    }

    // Load the room object
    MUCRoom room = null;
    if (!create) {
        room = webManager.getMultiUserChatManager().getMultiUserChatService(roomJID).getChatRoom(roomName);

        if (room == null) {
            // The requested room name does not exist so return to the list of the existing rooms
            response.sendRedirect("muc-room-summary.jsp?roomJID="+URLEncoder.encode(roomJID.toBareJID(), "UTF-8"));
            return;
        }
    }

    // Handle an save
    Map<String, String> errors = new HashMap<String, String>();
    if (save) {
        // do validation

        if (naturalName == null) {
            errors.put("roomconfig_roomname","roomconfig_roomname");
        }
        if (description == null) {
            errors.put("roomconfig_roomdesc","roomconfig_roomdesc");
        }
        if (maxUsers == null) {
            errors.put("roomconfig_maxusers","roomconfig_maxusers");
        }
        if (password != null && !password.equals(confirmPassword)) {
            errors.put("roomconfig_roomsecret2","roomconfig_roomsecret2");
        }
        if (whois == null) {
            errors.put("roomconfig_whois","roomconfig_whois");
        }
        if (create && errors.size() == 0) {
            if (roomName == null || roomName.contains("@")) {
                errors.put("roomName","roomName");
            }
            else {
                // Check that the room name is a valid node
                try {
                    roomName = Stringprep.nodeprep(roomName);
                }
                catch (StringprepException e) {
                    errors.put("roomName","roomName");
                }
            }

            if (errors.size() == 0) {
                // Check that the requested room ID is available
                room = webManager.getMultiUserChatManager().getMultiUserChatService(roomJID).getChatRoom(roomName);
                if (room != null) {
                    errors.put("room_already_exists", "room_already_exists");
                }
                else {
                    // Try to create a new room
                    JID address = new JID(webManager.getUser().getUsername(), webManager.getServerInfo().getXMPPDomain(), null);
                    try {
                        room = webManager.getMultiUserChatManager().getMultiUserChatService(roomJID).getChatRoom(roomName, address);
                        // Check if the room was created concurrently by another user
                        if (!room.getOwners().contains(address.asBareJID())) {
                            errors.put("room_already_exists", "room_already_exists");
                        }
                    }
                    catch (NotAllowedException e) {
                        // This user is not allowed to create rooms
                        errors.put("not_enough_permissions", "not_enough_permissions");
                    }
                }
            }
        }

        if (errors.size() == 0) {
            // Set the new configuration sending an IQ packet with an dataform
            FormField field;
            XDataFormImpl dataForm = new XDataFormImpl(DataForm.TYPE_SUBMIT);

            field = new XFormFieldImpl("FORM_TYPE");
            field.setType(FormField.TYPE_HIDDEN);
            field.addValue("http://jabber.org/protocol/muc#roomconfig");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_roomname");
            field.addValue(naturalName);
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_roomdesc");
            field.addValue(description);
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_changesubject");
            field.addValue((changeSubject == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_maxusers");
            field.addValue(maxUsers);
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_presencebroadcast");
            if (broadcastModerator != null) {
                field.addValue("moderator");
            }
            if (broadcastParticipant != null) {
                field.addValue("participant");
            }
            if (broadcastVisitor != null) {
                field.addValue("visitor");
            }
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_publicroom");
            field.addValue((publicRoom == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_persistentroom");
            field.addValue((persistentRoom == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_moderatedroom");
            field.addValue((moderatedRoom == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_membersonly");
            field.addValue((membersOnly == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_allowinvites");
            field.addValue((allowInvites == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_passwordprotectedroom");
            field.addValue((password == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_roomsecret");
            field.addValue(password);
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_whois");
            field.addValue(whois);
            dataForm.addField(field);

            field = new XFormFieldImpl("muc#roomconfig_enablelogging");
            field.addValue((enableLog == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("x-muc#roomconfig_reservednick");
            field.addValue((reservedNick == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("x-muc#roomconfig_canchangenick");
            field.addValue((canChangeNick == null) ? "0": "1");
            dataForm.addField(field);

            field = new XFormFieldImpl("x-muc#roomconfig_registration");
            field.addValue((registrationEnabled == null) ? "0": "1");
            dataForm.addField(field);

            // Keep the existing list of admins
            field = new XFormFieldImpl("muc#roomconfig_roomadmins");
            for (JID jid : room.getAdmins()) {
                field.addValue(jid.toString());
            }
            dataForm.addField(field);

            // Keep the existing list of owners
            field = new XFormFieldImpl("muc#roomconfig_roomowners");
            for (JID jid : room.getOwners()) {
                field.addValue(jid.toString());
            }
            dataForm.addField(field);

            // update subject before sending IQ (to include subject with cluster update)
            if (roomSubject != null) {
                // Change the subject of the room by sending a new message
                Message message = new Message();
                message.setType(Message.Type.groupchat);
                message.setSubject(roomSubject);
                message.setFrom(room.getRole().getRoleAddress());
                message.setTo(room.getRole().getRoleAddress());
                message.setID("local-only");
                room.changeSubject(message, room.getRole());
            }

            // Create an IQ packet and set the dataform as the main fragment
            IQ iq = new IQ(IQ.Type.set);
            Element element = iq.setChildElement("query", "http://jabber.org/protocol/muc#owner");
            element.add(dataForm.asXMLElement());
            // Send the IQ packet that will modify the room's configuration
            room.getIQOwnerHandler().handleIQ(iq, room.getRole());

            // Changes good, so redirect
            String params;
            if (create) {
                params = "addsuccess=true&roomJID=" + URLEncoder.encode(roomJID.toBareJID(), "UTF-8");
                // Log the event
                webManager.logEvent("created new MUC room "+roomName, "subject = "+roomSubject+"\nroomdesc = "+description+"\nroomname = "+naturalName+"\nmaxusers = "+maxUsers);
            }
            else {
                params = "success=true&roomJID=" + URLEncoder.encode(roomJID.toBareJID(), "UTF-8");
                // Log the event
                webManager.logEvent("updated MUC room "+roomName, "subject = "+roomSubject+"\nroomdesc = "+description+"\nroomname = "+naturalName+"\nmaxusers = "+maxUsers);
            }
            response.sendRedirect("muc-room-edit-form.jsp?" + params);
            return;
        }
    }
    else {
        if (create) {
            // TODO Make this default values configurable (see JM-79)
            maxUsers = "30";
            broadcastModerator = "true";
            broadcastParticipant = "true";
            broadcastVisitor = "true";
            whois = "moderator";
            publicRoom = "true";
            // Rooms created from the admin console are always persistent
            persistentRoom = "true";
            canChangeNick = "true";
            registrationEnabled = "true";
        }
        else {
            naturalName = room.getNaturalLanguageName();
            description = room.getDescription();
            roomSubject = room.getSubject();
            maxUsers = Integer.toString(room.getMaxUsers());
            broadcastModerator = Boolean.toString(room.canBroadcastPresence("moderator"));
            broadcastParticipant = Boolean.toString(room.canBroadcastPresence("participant"));
            broadcastVisitor = Boolean.toString(room.canBroadcastPresence("visitor"));
            password = room.getPassword();
            confirmPassword = room.getPassword();
            whois = (room.canAnyoneDiscoverJID() ? "anyone" : "moderator");
            publicRoom = Boolean.toString(room.isPublicRoom());
            persistentRoom = Boolean.toString(room.isPersistent());
            moderatedRoom = Boolean.toString(room.isModerated());
            membersOnly = Boolean.toString(room.isMembersOnly());
            allowInvites = Boolean.toString(room.canOccupantsInvite());
            changeSubject = Boolean.toString(room.canOccupantsChangeSubject());
            enableLog = Boolean.toString(room.isLogEnabled());
            reservedNick = Boolean.toString(room.isLoginRestrictedToNickname());
            canChangeNick = Boolean.toString(room.canChangeNickname());
            registrationEnabled = Boolean.toString(room.isRegistrationEnabled());
        }
    }
    // Formatter for dates
    DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
    roomName = roomName == null ? "" : roomName;

      out.write("\r\n\r\n<html>\r\n<head>\r\n");
 if (create) { 
      out.write("\r\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n<meta name=\"pageID\" content=\"muc-room-create\"/>\r\n");
 } else { 
      out.write("\r\n<title>");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</title>\r\n<meta name=\"subPageID\" content=\"muc-room-edit-form\"/>\r\n");
 } 
      out.write("\r\n<meta name=\"extraParams\" content=\"");
      out.print( "roomJID="+(roomJID != null ? URLEncoder.encode(roomJID.toBareJID(), "UTF-8") : "")+"&create="+create );
      out.write("\"/>\r\n<meta name=\"helpPage\" content=\"view_group_chat_room_summary.html\"/>\r\n</head>\r\n<body>\r\n\r\n");
  if (!errors.isEmpty()) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/></td>\r\n            <td class=\"jive-icon-label\">\r\n\r\n            ");
 if (errors.get("roomconfig_roomname") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("roomconfig_roomdesc") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("roomconfig_maxusers") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("roomconfig_roomsecret2") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("roomconfig_whois") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("roomName") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("room_already_exists") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("not_enough_permissions") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } else if (errors.get("room_topic") != null) { 
      out.write("\r\n                ");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\r\n            ");
 } 
      out.write("\r\n            </td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div><br>\r\n\r\n");
  } else if (success || addsuccess) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        <td class=\"jive-icon-label\">\r\n        ");
  if (success) { 
      out.write("\r\n\r\n        ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\r\n\r\n        ");
  } else if (addsuccess) { 
      out.write("\r\n\r\n        ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\r\n\r\n        ");
  } 
      out.write("\r\n        </td></tr>\r\n    </tbody>\r\n    </table>\r\n    </div><br>\r\n\r\n");
  } 
      out.write("\r\n\r\n");
  if (!create) { 
      out.write("\r\n    <p>\r\n    ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\r\n    </p>\r\n    <div class=\"jive-table\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <thead>\r\n        <tr>\r\n            <th scope=\"col\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</th>\r\n            <th scope=\"col\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</th>\r\n            <th scope=\"col\">");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("</th>\r\n            <th scope=\"col\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</th>\r\n        </tr>\r\n    </thead>\r\n    <tbody>\r\n        <tr>\r\n            <td>");
      out.print( room.getName() );
      out.write("</td>\r\n            ");
 if (room.getOccupantsCount() == 0) { 
      out.write("\r\n            <td>");
      out.print( room.getOccupantsCount() );
      out.write(' ');
      out.write('/');
      out.write(' ');
      out.print( room.getMaxUsers() );
      out.write("</td>\r\n            ");
 } else { 
      out.write("\r\n            <td><a href=\"muc-room-occupants.jsp?roomJID=");
      out.print( URLEncoder.encode(roomJID.toBareJID(), "UTF-8"));
      out.write('"');
      out.write('>');
      out.print( room.getOccupantsCount() );
      out.write(' ');
      out.write('/');
      out.write(' ');
      out.print( room.getMaxUsers() );
      out.write("</a></td>\r\n            ");
 } 
      out.write("\r\n            <td>");
      out.print( dateFormatter.format(room.getCreationDate()) );
      out.write("</td>\r\n            <td>");
      out.print( dateFormatter.format(room.getModificationDate()) );
      out.write("</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n    <br>\r\n    <p>");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</p>\r\n");
  } else { 
      out.write("\r\n    <p>");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</p>\r\n");
  } 
      out.write("\r\n<form action=\"muc-room-edit-form.jsp\">\r\n");
 if (!create) { 
      out.write("\r\n    <input type=\"hidden\" name=\"roomJID\" value=\"");
      out.print( roomJID.toBareJID() );
      out.write("\">\r\n");
 } 
      out.write("\r\n<input type=\"hidden\" name=\"save\" value=\"true\">\r\n<input type=\"hidden\" name=\"create\" value=\"");
      out.print( create );
      out.write("\">\r\n<input type=\"hidden\" name=\"roomconfig_persistentroom\" value=\"");
      out.print( persistentRoom );
      out.write("\">\r\n\r\n    <table width=\"100%\" border=\"0\"> <tr>\r\n         <td width=\"70%\">\r\n            <table width=\"100%\" border=\"0\">\r\n                <tbody>\r\n                ");
 if (create) { 
      out.write("\r\n                <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><input type=\"text\" name=\"roomName\" value=\"");
      out.print( roomName );
      out.write("\">\r\n                        ");
 if (webManager.getMultiUserChatManager().getMultiUserChatServicesCount() > 1) { 
      out.write("\r\n                        @<select name=\"mucName\">\r\n                        ");
 for (MultiUserChatService service : webManager.getMultiUserChatManager().getMultiUserChatServices()) { 
      out.write("\r\n                        ");
      if (service.isHidden()) continue; 
      out.write("\r\n                        <option value=\"");
      out.print( service.getServiceDomain() );
      out.write('"');
      out.print( service.getServiceDomain().equals(mucName) ? " selected='selected'" : "" );
      out.write('>');
      out.print( service.getServiceDomain() );
      out.write("</option>\r\n                        ");
 } 
      out.write("\r\n                        </select>\r\n                        ");
 } else { 
      out.write("\r\n                        @");

                            // We only have one service, none-the-less, we have to run through the list to get the first
                            for (MultiUserChatService service : webManager.getMultiUserChatManager().getMultiUserChatServices()) {
                                if (service.isHidden()) {
                                    // Private and hidden, skip it.
                                    continue;
                                }
                                out.print("<input type='hidden' name='mucName' value='"+service.getServiceDomain()+"'/>"+service.getServiceDomain());
                                break;
                            }
                        
      out.write("\r\n                        ");
 } 
      out.write("\r\n                    </td>\r\n                </tr>\r\n                ");
 } else { 
      out.write("\r\n                <tr>\r\n                   <td>");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write(":</td>\r\n                   <td>");
      out.print( roomJID.getDomain() );
      out.write("</td>\r\n               </tr>\r\n                ");
 } 
      out.write("\r\n                 <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><input type=\"text\" name=\"roomconfig_roomname\" value=\"");
      out.print( (naturalName == null ? "" : naturalName) );
      out.write("\">\r\n                    </td>\r\n                </tr>\r\n                 <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><input name=\"roomconfig_roomdesc\" value=\"");
      out.print( (description == null ? "" : description) );
      out.write("\" type=\"text\" size=\"40\">\r\n                    </td>\r\n                </tr>\r\n                 <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><input name=\"room_topic\" value=\"");
      out.print( (roomSubject == null ? "" : roomSubject) );
      out.write("\" type=\"text\" size=\"40\">\r\n                    </td>\r\n                </tr>\r\n                 <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><select name=\"roomconfig_maxusers\">\r\n                            <option value=\"10\" ");
 if ("10".equals(maxUsers)) out.write("selected");
      out.write(">10</option>\r\n                            <option value=\"20\" ");
 if ("20".equals(maxUsers)) out.write("selected");
      out.write(">20</option>\r\n                            <option value=\"30\" ");
 if ("30".equals(maxUsers)) out.write("selected");
      out.write(">30</option>\r\n                            <option value=\"40\" ");
 if ("40".equals(maxUsers)) out.write("selected");
      out.write(">40</option>\r\n                            <option value=\"50\" ");
 if ("50".equals(maxUsers)) out.write("selected");
      out.write(">50</option>\r\n                            <option value=\"0\" ");
 if ("0".equals(maxUsers)) out.write("selected");
      out.write('>');
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</option>\r\n                        </select>\r\n                    </td>\r\n                </tr>\r\n                 <tr>\r\n                    <td valign=\"top\">");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><fieldset>\r\n                        <input name=\"roomconfig_presencebroadcast\" type=\"checkbox\" value=\"true\" id=\"moderator\" ");
 if ("true".equals(broadcastModerator)) out.write("checked");
      out.write(">\r\n                        <LABEL FOR=\"moderator\">");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("</LABEL>\r\n                        <input name=\"roomconfig_presencebroadcast2\" type=\"checkbox\" value=\"true\" id=\"participant\" ");
 if ("true".equals(broadcastParticipant)) out.write("checked");
      out.write(">\r\n                        <LABEL FOR=\"participant\">");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("</LABEL>\r\n                        <input name=\"roomconfig_presencebroadcast3\" type=\"checkbox\" value=\"true\" id=\"visitor\" ");
 if ("true".equals(broadcastVisitor)) out.write("checked");
      out.write(">\r\n                        <LABEL FOR=\"visitor\">");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("</LABEL>\r\n                        </fieldset></td>\r\n                </tr>\r\n                 <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><input type=\"password\" name=\"roomconfig_roomsecret\" ");
 if(password != null) { 
      out.write(" value=\"");
      out.print( password );
      out.write('"');
      out.write(' ');
 } 
      out.write("></td>\r\n                </tr>\r\n                 <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><input type=\"password\" name=\"roomconfig_roomsecret2\" ");
 if(confirmPassword != null) { 
      out.write(" value=\"");
      out.print( confirmPassword );
      out.write('"');
      out.write(' ');
 } 
      out.write(">\r\n                    </td>\r\n                </tr>\r\n                 <tr>\r\n                    <td>");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write(":</td>\r\n                    <td><select name=\"roomconfig_whois\">\r\n                            <option value=\"moderator\" ");
 if ("moderator".equals(whois)) out.write("selected");
      out.write('>');
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("</option>\r\n                            <option value=\"anyone\" ");
 if ("anyone".equals(whois)) out.write("selected");
      out.write('>');
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write("</option>\r\n                        </select>\r\n                    </td>\r\n                 </tr>\r\n         </tbody>\r\n         </table>\r\n\r\n         </td>\r\n        <td width=\"30%\" valign=\"top\" >\r\n        <fieldset>\r\n        <legend>");
      if (_jspx_meth_fmt_message_36(_jspx_page_context))
        return;
      out.write("</legend>\r\n        <table width=\"100%\"  border=\"0\">\r\n        <tbody>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_publicroom\" value=\"true\" id=\"public\" ");
 if ("true".equals(publicRoom)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"public\">");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_moderatedroom\" value=\"true\" id=\"moderated\" ");
 if ("true".equals(moderatedRoom)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"moderated\">");
      if (_jspx_meth_fmt_message_38(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_membersonly\" value=\"true\" id=\"membersOnly\" ");
 if ("true".equals(membersOnly)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"membersOnly\">");
      if (_jspx_meth_fmt_message_39(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_allowinvites\" value=\"true\" id=\"allowinvites\" ");
 if ("true".equals(allowInvites)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"allowinvites\">");
      if (_jspx_meth_fmt_message_40(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_changesubject\" value=\"true\" id=\"changesubject\" ");
 if ("true".equals(changeSubject)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"changesubject\">");
      if (_jspx_meth_fmt_message_41(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_reservednick\" value=\"true\" id=\"reservednick\" ");
 if ("true".equals(reservedNick)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"reservednick\">");
      if (_jspx_meth_fmt_message_42(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_canchangenick\" value=\"true\" id=\"canchangenick\" ");
 if ("true".equals(canChangeNick)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"canchangenick\">");
      if (_jspx_meth_fmt_message_43(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_registration\" value=\"true\" id=\"registration\" ");
 if ("true".equals(registrationEnabled)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"registration\">");
      if (_jspx_meth_fmt_message_44(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n            <tr>\r\n                <td><input type=\"checkbox\" name=\"roomconfig_enablelogging\" value=\"true\" id=\"enablelogging\" ");
 if ("true".equals(enableLog)) out.write("checked");
      out.write(">\r\n                    <LABEL FOR=\"enablelogging\">");
      if (_jspx_meth_fmt_message_45(_jspx_page_context))
        return;
      out.write("</LABEL></td>\r\n            </tr>\r\n        </tbody>\r\n        </table>\r\n        </fieldset>\r\n        </tr>\r\n         <tr align=\"center\">\r\n            <td colspan=\"2\"><input type=\"submit\" name=\"Submit\" value=\"");
      if (_jspx_meth_fmt_message_46(_jspx_page_context))
        return;
      out.write("\">\r\n            <input type=\"submit\" name=\"cancel\" value=\"");
      if (_jspx_meth_fmt_message_47(_jspx_page_context))
        return;
      out.write("\"></td>\r\n        </tr>\r\n    </table>\r\n</form>\r\n\r\n    </body>\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("muc.room.edit.form.create.title");
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
    _jspx_th_fmt_message_1.setKey("muc.room.edit.form.edit.title");
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
    _jspx_th_fmt_message_2.setKey("muc.room.edit.form.valid_hint_name");
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
    _jspx_th_fmt_message_3.setKey("muc.room.edit.form.valid_hint_description");
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
    _jspx_th_fmt_message_4.setKey("muc.room.edit.form.valid_hint_max_room");
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
    _jspx_th_fmt_message_5.setKey("muc.room.edit.form.new_password");
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
    _jspx_th_fmt_message_6.setKey("muc.room.edit.form.role");
    int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
    if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
    return false;
  }

  private boolean _jspx_meth_fmt_message_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_7 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_7.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_7.setParent(null);
    _jspx_th_fmt_message_7.setKey("muc.room.edit.form.valid_hint");
    int _jspx_eval_fmt_message_7 = _jspx_th_fmt_message_7.doStartTag();
    if (_jspx_th_fmt_message_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
    return false;
  }

  private boolean _jspx_meth_fmt_message_8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_8 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_8.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_8.setParent(null);
    _jspx_th_fmt_message_8.setKey("muc.room.edit.form.error_created_id");
    int _jspx_eval_fmt_message_8 = _jspx_th_fmt_message_8.doStartTag();
    if (_jspx_th_fmt_message_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_8);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_8);
    return false;
  }

  private boolean _jspx_meth_fmt_message_9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_9 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_9.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_9.setParent(null);
    _jspx_th_fmt_message_9.setKey("muc.room.edit.form.error_created_privileges");
    int _jspx_eval_fmt_message_9 = _jspx_th_fmt_message_9.doStartTag();
    if (_jspx_th_fmt_message_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
    return false;
  }

  private boolean _jspx_meth_fmt_message_10(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_10 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_10.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_10.setParent(null);
    _jspx_th_fmt_message_10.setKey("muc.room.edit.form.valid_hint_subject");
    int _jspx_eval_fmt_message_10 = _jspx_th_fmt_message_10.doStartTag();
    if (_jspx_th_fmt_message_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
    return false;
  }

  private boolean _jspx_meth_fmt_message_11(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_11 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_11.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_11.setParent(null);
    _jspx_th_fmt_message_11.setKey("muc.room.edit.form.edited");
    int _jspx_eval_fmt_message_11 = _jspx_th_fmt_message_11.doStartTag();
    if (_jspx_th_fmt_message_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
    return false;
  }

  private boolean _jspx_meth_fmt_message_12(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_12 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_12.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_12.setParent(null);
    _jspx_th_fmt_message_12.setKey("muc.room.edit.form.created");
    int _jspx_eval_fmt_message_12 = _jspx_th_fmt_message_12.doStartTag();
    if (_jspx_th_fmt_message_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
    return false;
  }

  private boolean _jspx_meth_fmt_message_13(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_13 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_13.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_13.setParent(null);
    _jspx_th_fmt_message_13.setKey("muc.room.edit.form.info");
    int _jspx_eval_fmt_message_13 = _jspx_th_fmt_message_13.doStartTag();
    if (_jspx_th_fmt_message_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_13);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_13);
    return false;
  }

  private boolean _jspx_meth_fmt_message_14(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_14 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_14.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_14.setParent(null);
    _jspx_th_fmt_message_14.setKey("muc.room.edit.form.room_id");
    int _jspx_eval_fmt_message_14 = _jspx_th_fmt_message_14.doStartTag();
    if (_jspx_th_fmt_message_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
    return false;
  }

  private boolean _jspx_meth_fmt_message_15(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_15 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_15.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_15.setParent(null);
    _jspx_th_fmt_message_15.setKey("muc.room.edit.form.users");
    int _jspx_eval_fmt_message_15 = _jspx_th_fmt_message_15.doStartTag();
    if (_jspx_th_fmt_message_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
    return false;
  }

  private boolean _jspx_meth_fmt_message_16(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_16 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_16.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_16.setParent(null);
    _jspx_th_fmt_message_16.setKey("muc.room.edit.form.on");
    int _jspx_eval_fmt_message_16 = _jspx_th_fmt_message_16.doStartTag();
    if (_jspx_th_fmt_message_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
    return false;
  }

  private boolean _jspx_meth_fmt_message_17(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_17 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_17.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_17.setParent(null);
    _jspx_th_fmt_message_17.setKey("muc.room.edit.form.modified");
    int _jspx_eval_fmt_message_17 = _jspx_th_fmt_message_17.doStartTag();
    if (_jspx_th_fmt_message_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_17);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_17);
    return false;
  }

  private boolean _jspx_meth_fmt_message_18(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_18 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_18.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_18.setParent(null);
    _jspx_th_fmt_message_18.setKey("muc.room.edit.form.change_room");
    int _jspx_eval_fmt_message_18 = _jspx_th_fmt_message_18.doStartTag();
    if (_jspx_th_fmt_message_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
    return false;
  }

  private boolean _jspx_meth_fmt_message_19(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_19 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_19.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_19.setParent(null);
    _jspx_th_fmt_message_19.setKey("muc.room.edit.form.persistent_room");
    int _jspx_eval_fmt_message_19 = _jspx_th_fmt_message_19.doStartTag();
    if (_jspx_th_fmt_message_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
    return false;
  }

  private boolean _jspx_meth_fmt_message_20(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_20 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_20.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_20.setParent(null);
    _jspx_th_fmt_message_20.setKey("muc.room.edit.form.room_id");
    int _jspx_eval_fmt_message_20 = _jspx_th_fmt_message_20.doStartTag();
    if (_jspx_th_fmt_message_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_20);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_20);
    return false;
  }

  private boolean _jspx_meth_fmt_message_21(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_21 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_21.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_21.setParent(null);
    _jspx_th_fmt_message_21.setKey("muc.room.edit.form.service");
    int _jspx_eval_fmt_message_21 = _jspx_th_fmt_message_21.doStartTag();
    if (_jspx_th_fmt_message_21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_21);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_21);
    return false;
  }

  private boolean _jspx_meth_fmt_message_22(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_22 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_22.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_22.setParent(null);
    _jspx_th_fmt_message_22.setKey("muc.room.edit.form.room_name");
    int _jspx_eval_fmt_message_22 = _jspx_th_fmt_message_22.doStartTag();
    if (_jspx_th_fmt_message_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
    return false;
  }

  private boolean _jspx_meth_fmt_message_23(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_23 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_23.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_23.setParent(null);
    _jspx_th_fmt_message_23.setKey("muc.room.edit.form.description");
    int _jspx_eval_fmt_message_23 = _jspx_th_fmt_message_23.doStartTag();
    if (_jspx_th_fmt_message_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
    return false;
  }

  private boolean _jspx_meth_fmt_message_24(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_24 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_24.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_24.setParent(null);
    _jspx_th_fmt_message_24.setKey("muc.room.edit.form.topic");
    int _jspx_eval_fmt_message_24 = _jspx_th_fmt_message_24.doStartTag();
    if (_jspx_th_fmt_message_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
    return false;
  }

  private boolean _jspx_meth_fmt_message_25(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_25 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_25.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_25.setParent(null);
    _jspx_th_fmt_message_25.setKey("muc.room.edit.form.max_room");
    int _jspx_eval_fmt_message_25 = _jspx_th_fmt_message_25.doStartTag();
    if (_jspx_th_fmt_message_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
    return false;
  }

  private boolean _jspx_meth_fmt_message_26(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_26 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_26.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_26.setParent(null);
    _jspx_th_fmt_message_26.setKey("muc.room.edit.form.none");
    int _jspx_eval_fmt_message_26 = _jspx_th_fmt_message_26.doStartTag();
    if (_jspx_th_fmt_message_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
    return false;
  }

  private boolean _jspx_meth_fmt_message_27(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_27 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_27.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_27.setParent(null);
    _jspx_th_fmt_message_27.setKey("muc.room.edit.form.broadcast");
    int _jspx_eval_fmt_message_27 = _jspx_th_fmt_message_27.doStartTag();
    if (_jspx_th_fmt_message_27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
    return false;
  }

  private boolean _jspx_meth_fmt_message_28(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_28 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_28.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_28.setParent(null);
    _jspx_th_fmt_message_28.setKey("muc.room.edit.form.moderator");
    int _jspx_eval_fmt_message_28 = _jspx_th_fmt_message_28.doStartTag();
    if (_jspx_th_fmt_message_28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_28);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_28);
    return false;
  }

  private boolean _jspx_meth_fmt_message_29(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_29 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_29.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_29.setParent(null);
    _jspx_th_fmt_message_29.setKey("muc.room.edit.form.participant");
    int _jspx_eval_fmt_message_29 = _jspx_th_fmt_message_29.doStartTag();
    if (_jspx_th_fmt_message_29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
    return false;
  }

  private boolean _jspx_meth_fmt_message_30(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_30 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_30.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_30.setParent(null);
    _jspx_th_fmt_message_30.setKey("muc.room.edit.form.visitor");
    int _jspx_eval_fmt_message_30 = _jspx_th_fmt_message_30.doStartTag();
    if (_jspx_th_fmt_message_30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_30);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_30);
    return false;
  }

  private boolean _jspx_meth_fmt_message_31(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_31 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_31.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_31.setParent(null);
    _jspx_th_fmt_message_31.setKey("muc.room.edit.form.required_password");
    int _jspx_eval_fmt_message_31 = _jspx_th_fmt_message_31.doStartTag();
    if (_jspx_th_fmt_message_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
    return false;
  }

  private boolean _jspx_meth_fmt_message_32(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_32 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_32.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_32.setParent(null);
    _jspx_th_fmt_message_32.setKey("muc.room.edit.form.confirm_password");
    int _jspx_eval_fmt_message_32 = _jspx_th_fmt_message_32.doStartTag();
    if (_jspx_th_fmt_message_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
    return false;
  }

  private boolean _jspx_meth_fmt_message_33(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_33 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_33.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_33.setParent(null);
    _jspx_th_fmt_message_33.setKey("muc.room.edit.form.discover_jid");
    int _jspx_eval_fmt_message_33 = _jspx_th_fmt_message_33.doStartTag();
    if (_jspx_th_fmt_message_33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
    return false;
  }

  private boolean _jspx_meth_fmt_message_34(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_34 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_34.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_34.setParent(null);
    _jspx_th_fmt_message_34.setKey("muc.room.edit.form.moderator");
    int _jspx_eval_fmt_message_34 = _jspx_th_fmt_message_34.doStartTag();
    if (_jspx_th_fmt_message_34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
    return false;
  }

  private boolean _jspx_meth_fmt_message_35(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_35 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_35.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_35.setParent(null);
    _jspx_th_fmt_message_35.setKey("muc.room.edit.form.anyone");
    int _jspx_eval_fmt_message_35 = _jspx_th_fmt_message_35.doStartTag();
    if (_jspx_th_fmt_message_35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
    return false;
  }

  private boolean _jspx_meth_fmt_message_36(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_36 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_36.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_36.setParent(null);
    _jspx_th_fmt_message_36.setKey("muc.room.edit.form.room_options");
    int _jspx_eval_fmt_message_36 = _jspx_th_fmt_message_36.doStartTag();
    if (_jspx_th_fmt_message_36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
    return false;
  }

  private boolean _jspx_meth_fmt_message_37(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_37 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_37.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_37.setParent(null);
    _jspx_th_fmt_message_37.setKey("muc.room.edit.form.list_room");
    int _jspx_eval_fmt_message_37 = _jspx_th_fmt_message_37.doStartTag();
    if (_jspx_th_fmt_message_37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
    return false;
  }

  private boolean _jspx_meth_fmt_message_38(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_38 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_38.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_38.setParent(null);
    _jspx_th_fmt_message_38.setKey("muc.room.edit.form.room_moderated");
    int _jspx_eval_fmt_message_38 = _jspx_th_fmt_message_38.doStartTag();
    if (_jspx_th_fmt_message_38.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
    return false;
  }

  private boolean _jspx_meth_fmt_message_39(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_39 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_39.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_39.setParent(null);
    _jspx_th_fmt_message_39.setKey("muc.room.edit.form.moderated_member_only");
    int _jspx_eval_fmt_message_39 = _jspx_th_fmt_message_39.doStartTag();
    if (_jspx_th_fmt_message_39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
    return false;
  }

  private boolean _jspx_meth_fmt_message_40(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_40 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_40.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_40.setParent(null);
    _jspx_th_fmt_message_40.setKey("muc.room.edit.form.invite_other");
    int _jspx_eval_fmt_message_40 = _jspx_th_fmt_message_40.doStartTag();
    if (_jspx_th_fmt_message_40.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
    return false;
  }

  private boolean _jspx_meth_fmt_message_41(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_41 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_41.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_41.setParent(null);
    _jspx_th_fmt_message_41.setKey("muc.room.edit.form.change_subject");
    int _jspx_eval_fmt_message_41 = _jspx_th_fmt_message_41.doStartTag();
    if (_jspx_th_fmt_message_41.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
    return false;
  }

  private boolean _jspx_meth_fmt_message_42(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_42 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_42.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_42.setParent(null);
    _jspx_th_fmt_message_42.setKey("muc.room.edit.form.reservednick");
    int _jspx_eval_fmt_message_42 = _jspx_th_fmt_message_42.doStartTag();
    if (_jspx_th_fmt_message_42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
    return false;
  }

  private boolean _jspx_meth_fmt_message_43(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_43 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_43.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_43.setParent(null);
    _jspx_th_fmt_message_43.setKey("muc.room.edit.form.canchangenick");
    int _jspx_eval_fmt_message_43 = _jspx_th_fmt_message_43.doStartTag();
    if (_jspx_th_fmt_message_43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
    return false;
  }

  private boolean _jspx_meth_fmt_message_44(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_44 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_44.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_44.setParent(null);
    _jspx_th_fmt_message_44.setKey("muc.room.edit.form.registration");
    int _jspx_eval_fmt_message_44 = _jspx_th_fmt_message_44.doStartTag();
    if (_jspx_th_fmt_message_44.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_44);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_44);
    return false;
  }

  private boolean _jspx_meth_fmt_message_45(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_45 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_45.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_45.setParent(null);
    _jspx_th_fmt_message_45.setKey("muc.room.edit.form.log");
    int _jspx_eval_fmt_message_45 = _jspx_th_fmt_message_45.doStartTag();
    if (_jspx_th_fmt_message_45.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_45);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_45);
    return false;
  }

  private boolean _jspx_meth_fmt_message_46(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_46 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_46.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_46.setParent(null);
    _jspx_th_fmt_message_46.setKey("global.save_changes");
    int _jspx_eval_fmt_message_46 = _jspx_th_fmt_message_46.doStartTag();
    if (_jspx_th_fmt_message_46.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_46);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_46);
    return false;
  }

  private boolean _jspx_meth_fmt_message_47(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_47 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_47.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_47.setParent(null);
    _jspx_th_fmt_message_47.setKey("global.cancel");
    int _jspx_eval_fmt_message_47 = _jspx_th_fmt_message_47.doStartTag();
    if (_jspx_th_fmt_message_47.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_47);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_47);
    return false;
  }
}
