package com.ssnn.dujiaok.web.velocity.layout;

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.VelocityResult;
import org.apache.struts2.views.JspSupportServlet;
import org.apache.struts2.views.velocity.VelocityManager;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 由于struts2中VelocityResult 不支持layout，这里参照VelocityLayoutServlet重写该Result
 * @author langben 2011-12-23
 *
 */
public class VelocityLayoutResult extends VelocityResult {

	private static final long serialVersionUID = 6020934292083047099L;

	private static final Logger LOG = LoggerFactory
			.getLogger(VelocityLayoutResult.class);

	public static String KEY_SCREEN_CONTENT = "screen_content";
	public static String KEY_LAYOUT = "layout";
	public static final String PROPERTY_DEFAULT_LAYOUT = "tools.view.servlet.layout.default.template";
	public static final String PROPERTY_LAYOUT_DIR = "tools.view.servlet.layout.directory";
	public static final String PROPERTY_INPUT_ENCODING = "input.encoding";
	public static final String PROPERTY_OUTPUT_ENCODING = "output.encoding";
	public static final String PROPERTY_CONTENT_TYPE = "default.contentType";

	protected VelocityManager velocityManager;
	protected String defaultLayout;
	protected String layoutDir;
	protected String inputEncoding;
	protected String outputEncoding;
	protected String contentType;

	@Override
	public void setVelocityManager(VelocityManager mgr) {
		this.velocityManager = mgr;
		super.setVelocityManager(mgr);
	}

	@Override
	public void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		ValueStack stack = ActionContext.getContext().getValueStack();

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JspFactory jspFactory = null;
		ServletContext servletContext = ServletActionContext
				.getServletContext();
		Servlet servlet = JspSupportServlet.jspSupportServlet;

		velocityManager.init(servletContext);

		boolean usedJspFactory = false;
		PageContext pageContext = (PageContext) ActionContext.getContext().get(
				ServletActionContext.PAGE_CONTEXT);

		if (pageContext == null && servlet != null) {
			jspFactory = JspFactory.getDefaultFactory();
			pageContext = jspFactory.getPageContext(servlet, request, response,
					null, true, 8192, true);
			ActionContext.getContext().put(ServletActionContext.PAGE_CONTEXT,
					pageContext);
			usedJspFactory = true;
		}

		try {
			String encoding = getEncoding(finalLocation);

			String contentType = getContentType(finalLocation);

			if (encoding != null) {
				contentType = contentType + ";charset=" + encoding;
			}

			Template t = getTemplate(stack,
					velocityManager.getVelocityEngine(), invocation,
					finalLocation, encoding);

			Context context = createContext(velocityManager, stack, request,
					response, finalLocation);
			StringWriter stringWriter = new StringWriter();
			t.merge(context, stringWriter);
			context.put(KEY_SCREEN_CONTENT, stringWriter.toString());

			Object obj = context.get(KEY_LAYOUT);
			String layout = (obj == null) ? null : obj.toString();

			defaultLayout = (String) velocityManager.getVelocityEngine()
					.getProperty(PROPERTY_DEFAULT_LAYOUT);
			layoutDir = (String) velocityManager.getVelocityEngine()
					.getProperty(PROPERTY_LAYOUT_DIR);
			if (!layoutDir.endsWith("/")) {
				layoutDir += '/';
			}

			if (!layoutDir.startsWith("/")) {
				layoutDir = "/" + layoutDir;
			}

			defaultLayout = layoutDir + defaultLayout;

			if (layout == null) {
				layout = defaultLayout;
			} else {
				layout = layoutDir + layout;
			}

			Template layoutVm = null;
			try {
				inputEncoding = (String) velocityManager.getVelocityEngine()
						.getProperty(PROPERTY_INPUT_ENCODING);
				layoutVm = getTemplate(stack,
						velocityManager.getVelocityEngine(), invocation,
						layout, inputEncoding);
			} catch (Exception e) {
				LOG.error("VelocityLayoutResult: Can't load layout \"" + layout
						+ "\": " + e);
			}

			if (!layout.equals(defaultLayout)) {
				layoutVm = getTemplate(stack,
						velocityManager.getVelocityEngine(), invocation,
						layout, inputEncoding);
			}

			outputEncoding = (String) velocityManager.getVelocityEngine()
					.getProperty(PROPERTY_OUTPUT_ENCODING);
			Writer writer = new OutputStreamWriter(response.getOutputStream(),
					outputEncoding);
			response.setContentType(contentType);

			layoutVm.merge(context, writer);
			writer.flush();
		} catch (Exception e) {
			LOG.error("Unable to render Velocity Template, '" + finalLocation
					+ "'", e);
			throw e;
		} finally {
			if (usedJspFactory) {
				jspFactory.releasePageContext(pageContext);
			}

		}
	}

}
