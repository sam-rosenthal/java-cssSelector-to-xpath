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
	private CssElementCombinatorPairsToXpath cssElementCombinatorPairsToXpath=new CssElementCombinatorPairsToXpath();
	private String message = "[enter a css selector]";

	/**
	 * Constructor.
	 */
	public EnterText()
	{
		// This model references the page's message property and is
		// shared by the label and form component
		PropertyModel<String> messageModel = new PropertyModel<>(this, "message");

		// The label displays the currently set message
		add(new Label("msg", messageModel));

		// Add a form to change the message. We don't need to do anything
		// else with this form as the shared model is automatically updated
		// on form submits
		Form<?> form = new Form("form");
		form.add(new TextField<>("msgInput", messageModel));
		add(form);
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 * @throws CssSelectorStringSplitterException 
	 */
	public void setMessage(String message) throws CssSelectorStringSplitterException
	{
		this.message = cssElementCombinatorPairsToXpath.convertCssSelectorStringToXpathString(message);
	}
}