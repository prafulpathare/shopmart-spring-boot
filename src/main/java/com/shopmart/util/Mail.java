package com.shopmart.util;

import java.util.List;
import java.util.Map;

public class Mail {

    private String mailTo, subject, templateName;
    private List<Object> attachments;
    private Map<String, Object> props;

    public Mail() {}

	public Mail(String mailTo, String templateName, String subject, List<Object> attachments, Map<String, Object> props) {
		this.mailTo = mailTo;
		this.subject = subject;
		this.attachments = attachments;
		this.props = props;
		this.templateName = templateName;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
