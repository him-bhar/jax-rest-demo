package com.himanshu.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Media {
	private byte[] data;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	private String fileName;
}
