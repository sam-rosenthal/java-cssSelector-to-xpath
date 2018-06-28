/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sam.rosenthal.wicket.javacssselectortoxpath;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorStringSplitterException;

public class EnterText extends WebPage
{

	private static final long serialVersionUID = 1L;
	private transient CssElementCombinatorPairsToXpath cssElementCombinatorPairsToXpath=null;
	private String cssSelector = null;
	private String xPath=null;

	/**
	 * Constructor.
	 */
	public EnterText()
	{
		// This model references the page's message property and is
		// shared by the label and form component
		PropertyModel<String> xPathModel = new PropertyModel<>(this, "xpath");

		// The label displays the currently set message
		add(new Label("msg", xPathModel));

		// Add a form to change the message. We don't need to do anything
		// else with this form as the shared model is automatically updated
		// on form submits
		Form<?> form = new Form<>("form");
		PropertyModel<String> messageModel = new PropertyModel<>(this, "cssSelector");
		form.add(new TextField<>("msgInput", messageModel));
		add(form);
	}
	
	public String getXpath() {
		return xPath;
	}


	public String getCssSelector()
	{
		return cssSelector;
	}


	public void setCssSelector(String cssSelectorIn) throws CssSelectorStringSplitterException
	{
		if (cssElementCombinatorPairsToXpath==null) {
			cssElementCombinatorPairsToXpath=new CssElementCombinatorPairsToXpath();
		}
		this.cssSelector = cssSelectorIn;
		try
		{
			xPath=cssElementCombinatorPairsToXpath.convertCssSelectorStringToXpathString(cssSelector);
		}
		catch (CssSelectorStringSplitterException e)
		{
			xPath="INVALID CSS SELECTOR INPUT";
		}
	}
}