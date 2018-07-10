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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

public class EnterText extends WebPage
{

	private static final long serialVersionUID = 1L;
	private transient CssElementCombinatorPairsToXpath cssElementCombinatorPairsToXpath=null;
	private String cssSelector = null;
	private String xPath=null;
	private String error=null;
	private Label cssSelectorLabel;
	private Label xpathLabel;
	private Label errorLabel;
	private TextField<?> cssSelectorTextField;


	/**
	 * Constructor.
	 */
	public EnterText()
	{
		cssElementCombinatorPairsToXpath=new CssElementCombinatorPairsToXpath();

		// Add a form to change the message. We don't need to do anything
		// else with this form as the shared model is automatically updated
		// on form submits
		final Form<?> form = new Form<>("form");
		form.add(new AjaxButton("convert", form)
         {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(cssSelectorLabel);
				target.add(xpathLabel);
				target.add(errorLabel);
				target.focusComponent(cssSelectorTextField);
				target.add(form);
				if((xPath==null)&&(error==null)) {
					form.add(AttributeAppender.remove("invalid-xpath"));					
				} else {
					form.add(AttributeAppender.replace("invalid-xpath",(error==null)?"false":"true"));
				}
				super.onSubmit(target);
			}

         });
		PropertyModel<String> cssSelectorInputModel = new PropertyModel<>(this, "cssSelector");		
		cssSelectorTextField = new TextField<>("cssSelectorInput", cssSelectorInputModel);
		cssSelectorTextField.setOutputMarkupId(true);
		form.add(cssSelectorTextField);
		cssSelectorLabel=createLabel("cssSelector", form);
		xpathLabel=createLabel("xPath",form);
		errorLabel=createLabel("error",form);
		
		add(form);
        add(new Label("version", cssElementCombinatorPairsToXpath.getVersionNumber()));
        add(new Label("webversion", CssElementCombinatorPairsToXpath.VERSION_PROPERTIES.getProperty("webpage.version")));


	}

	private Label createLabel(String idAndProperty, Form<?> form) {
		PropertyModel<String> model = new PropertyModel<>(this, idAndProperty);
	    Label label = new Label(idAndProperty, model);
	    label.setOutputMarkupId(true);
		form.add(label);
		return label;
	}
	
	public String getXpath() {
		return xPath;
	}


	public String getCssSelector()
	{
		return cssSelector;
	}
	

	public String getError()
	{
		return error;
	}
	

	public void setCssSelector(String cssSelectorIn)
	{
		error=null;
		//since cssElementCombinstorPairToXPath is a transient, it may be null, even though it was created in the constructor.
		//The GCP is serializing this class, therefore this is required.
		if (cssElementCombinatorPairsToXpath==null) {
			cssElementCombinatorPairsToXpath=new CssElementCombinatorPairsToXpath();
		}
		this.cssSelector = cssSelectorIn;
		try
		{
			xPath=null;
			xPath=cssElementCombinatorPairsToXpath.niceConvertCssSelectorToXpathForOutput(cssSelector);
		}
		catch (NiceCssSelectorStringForOutputException e)
		{
			error=e.getMessage();
		}
		catch (RuntimeException e)
		{
			error="Unexpected Error:  "+e;
			e.printStackTrace();
		}
	}
	
}