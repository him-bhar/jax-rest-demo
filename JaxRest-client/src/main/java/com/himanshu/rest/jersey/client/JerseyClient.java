package com.himanshu.rest.jersey.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

import com.himanshu.rest.beans.Media;
import com.himanshu.rest.beans.Person;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

public class JerseyClient {
	public static void main(String[] args) {
		new JerseyClient().getMediaObject();
	}
	
	public void get() {
		Client client = Client.create();
		WebResource r = client.resource("http://localhost:8090/JaxRest-webapp/contacts/xml");
		Person person = r.accept(MediaType.APPLICATION_XML).get(Person.class);
		System.out.println(person.getName());
		client.destroy();
	}
	
	public void post() {
		Client client = Client.create();
		WebResource r = client.resource("http://localhost:8090/JaxRest-webapp/contacts/post");
		Person person = new Person();
		person.setId(10);
		person.setName("HuUULLLA");
		//r.entity(person, MediaType.APPLICATION_XML);
		String response = r.accept(MediaType.TEXT_PLAIN).post(String.class, person);
		System.out.println(response);
		client.destroy();
	}
	
	public void getFile() {
		Client client = Client.create();
		WebResource r = client.resource("http://localhost:8090/JaxRest-webapp/contacts/file");
		try {
			byte[] is = r.accept(MediaType.APPLICATION_OCTET_STREAM).get(byte[].class);
			String path = new File(".").getCanonicalPath();
			path = path+File.separator+"files"+File.separator+"DL_Californification.mp3";
			byte[] rawData = Base64.decode(is);
			File f = new File(path);
			OutputStream os = new FileOutputStream(f);
			IOUtils.write(rawData, os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//while (is.read())
		client.destroy();
	}
	
	public void getMediaObject() {
		Client client = Client.create();
		WebResource r = client.resource("http://localhost:8090/JaxRest-webapp/contacts/fileXML");
		try {
			Media m = r.accept(MediaType.APPLICATION_XML).get(Media.class);
			String path = new File(".").getCanonicalPath();
			path = path+File.separator+"files"+File.separator+m.getFileName();
			byte[] rawData = Base64.decode(m.getData());
			File f = new File(path);
			OutputStream os = new FileOutputStream(f);
			IOUtils.write(rawData, os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//while (is.read())
		client.destroy();
	}
}
