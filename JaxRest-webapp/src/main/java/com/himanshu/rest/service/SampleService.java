package com.himanshu.rest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.himanshu.rest.beans.Media;
import com.himanshu.rest.beans.Person;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.multipart.FormDataParam;

@Path(value="/contacts")
public class SampleService {
	@GET
	public String testMethod () {
		System.out.println("Hello world");
		return "";
	}
	
	@GET
	@Path(value="/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Person testXMLReturn () {
		Person person = new Person();
		person.setId(1);
		person.setName("Himanshu Bhardwaj");
		return person;
	}
	
	@POST
	@Path(value="/post")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String getXML(Person person) {
		System.out.println("person id="+person.getId()+"  person name="+person.getName());
		return "hello world";
	}
	
	@GET
	@Path(value="/file")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] testFileReturn () {
		String path;
		InputStream is = null;
		try {
			path = new File(".").getCanonicalPath();
			path = path+File.separator+"files"+File.separator+"Californication.mp3";
			File f = new File(path);
			is = new FileInputStream(f);
			byte[] byteArr = IOUtils.toByteArray(is);
			byte[] base64 = Base64.encode(byteArr);
			return base64;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path(value="/fileXML")
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_XML)
	public Media testFileXML () {
		String path;
		InputStream is = null;
		try {
			path = new File(".").getCanonicalPath();
			path = path+File.separator+"files"+File.separator+"Californication.mp3";
			File f = new File(path);
			is = new FileInputStream(f);
			byte[] byteArr = IOUtils.toByteArray(is);
			byte[] base64 = Base64.encode(byteArr);
			Media m = new Media();
			m.setData(base64);
			m.setFileName("New-File.mp3");
			return m;
			//return base64;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@POST
	@Path("/file/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
		String output = null;
		try {
			String path = new File(".").getCanonicalPath();
			path = path+File.separator+"files"+File.separator;
			String uploadedFileLocation = path + fileDetail.getFileName();
	 
			// save it
			writeToFile(uploadedInputStream, uploadedFileLocation);
	 
			output = "File uploaded to : " + uploadedFileLocation;
		} catch (Exception e) {
			e.printStackTrace();
			output = "File uploading failed";
		}
		return Response.status(200).entity(output).build();
 
	}
 
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {
 
		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
 
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
 
			e.printStackTrace();
		}
 
	}
}
