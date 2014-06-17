package com.cortalconsors.mvc.navi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cortalconsors.mvc.controller.*;


/**
 *	Der Controller
 */
public class FrontController extends HttpServlet
{
	
	private static final String LAYOUT_INDEX = "/WEB-INF/jsp/navi/index.jsp";
	private static final String LAYOUT_HOME = "/WEB-INF/jsp/navi/indexHome.jsp";
	
	private Map<String, Controller> controller;
	
	@Override
	public void init() throws ServletException {
		controller = new HashMap<String, Controller>();
	}
	
	
	
	public void doGet(HttpServletRequest request,
					  HttpServletResponse response)
		throws IOException, ServletException
	{
		StringBuffer meldung = new StringBuffer();
		
		// /mvc/function/login.do ohne /mvc und .do ==> /function/login
		String navi = request.getRequestURI().substring( // /mvc/function/login.do
				request.getContextPath().length(),		// /mvc
				request.getRequestURI().length() -3);	// .do

		try
		{
			// hole den Controller
			Controller c = controller.get(navi);
			// gibt es einen Controller für die Uri?
			if (c != null)
			{
				String neueNavi = c.execute(request, response, meldung);
				if (neueNavi != null) {
					navi = neueNavi;
				}
			} else {
				meldung.append(navi+" nicht gefunden!");
			}
		}
		catch (Exception e)
		{
			meldung.append(e.toString());
			e.printStackTrace();
			request.setAttribute("ex", e);
		}

		request.setAttribute("meldung", meldung.toString());

		// Die URL für das Fragment
		request.setAttribute("dieUrl", "/WEB-INF/jsp"+navi+".jsp");
		
		RequestDispatcher rd;
		if (request.getSession().getAttribute("user") != (null)) {
			rd = request.getRequestDispatcher(LAYOUT_HOME);
		} else {
			rd = request.getRequestDispatcher(LAYOUT_INDEX);
		}
		rd.forward(request, response);
	}


	
	public void doPost(HttpServletRequest request,
					  HttpServletResponse response)
		throws IOException, ServletException
	{
		doGet(request, response);
	}
}
