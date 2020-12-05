package org.apache.commons.mail;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;



public class EmailTest {

	private static String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	private static String[] BAD_ARRAY = {};

	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception{
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception{}
	
	//Tests begin here
	
	@Test (expected = EmailException.class )
	public void testAddBccNull() throws Exception{
		email.addBcc(BAD_ARRAY);
	}
	
	@Test
	public void testAddBcc() throws Exception{
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
	}
	
	
	@Test 
	public void testAddCc() throws Exception{
		email.addCc(TEST_EMAILS[0]);
		assertEquals(1, email.getCcAddresses().size());
	}
	
	@Test
	public void testAddHeader() throws Exception{
		String name = "TestHeader";
		String value = "1";
		email.addHeader(name, value);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddHeaderNullVal() throws Exception{
		String name = "TestHeader";
		email.addHeader(name, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddHeaderNullName() throws Exception{
		String value = "1";
		email.addHeader(null, value);
	}
	
	@Test
	public void testAddReplyTo() throws Exception{
		email.addReplyTo(TEST_EMAILS[0], "Test Reply");
		assertEquals(1, email.getReplyToAddresses().size());
	}
	
	@Test
	public void testBuildMimeMessage() throws Exception{
		String hostname = "Test HostName";
		email.setHostName(hostname);
		email.setFrom(TEST_EMAILS[0]);
		email.addReplyTo(TEST_EMAILS[0], "Test Reply");
		email.setTo(email.getReplyToAddresses());
		email.addBcc(TEST_EMAILS);
		email.addCc(TEST_EMAILS);
		String name = "TestHeader";
		String value = "1";
		email.addHeader(name, value);
		email.setSubject("Test Subject");
		email.setCharset("US-ASCII");
		email.buildMimeMessage();
	}
	
	@Test (expected = IllegalStateException.class)
	public void testBuildMimeMessageRepeat() throws Exception{
		String hostname = "Test HostName";
		email.setHostName(hostname);
		email.setFrom(TEST_EMAILS[0]);
		email.addReplyTo(TEST_EMAILS[0], "Test Reply");
		email.setTo(email.getReplyToAddresses());
		email.buildMimeMessage();
		email.buildMimeMessage();
	}
	
	@Test (expected = EmailException.class)
	public void testBuildMimeMessageNoTo() throws Exception{
		String hostname = "Test HostName";
		email.setHostName(hostname);
		email.setFrom(TEST_EMAILS[0]);
		email.addReplyTo(TEST_EMAILS[0], "Test Reply");
		//email.setTo(email.getReplyToAddresses());
		email.buildMimeMessage();
	}
	
	@Test (expected = EmailException.class)
	public void testBuildMimeMessageNoFrom() throws Exception{
		String hostname = "Test HostName";
		email.setHostName(hostname);
		//email.setFrom(TEST_EMAILS[0]);
		email.addReplyTo(TEST_EMAILS[0], "Test Reply");
		email.setTo(email.getReplyToAddresses());
		email.buildMimeMessage();
	}
	
	@Test
	public void testGetHostName() throws Exception{
		String hostname = "Test HostName";
		email.setHostName(hostname);
		assertEquals(hostname, email.getHostName());
	}
	
	@Test
	public void testGetMailSession() throws Exception{
		String hostname = "Test HostName";
		email.setHostName(hostname);
		email.setBounceAddress(TEST_EMAILS[1]);
		email.setAuthentication("userName", "password");
		email.getMailSession();
	}
	
	@Test
	public void testGetSentDateNull() throws Exception{
		email.getSentDate();
	}
	
	@Test
	public void testGetSentDate() throws Exception{
		Date today = new Date();
		email.setSentDate(today);
		email.getSentDate();
	}
	
	@Test
	public void testGetSocketConnectionTimeout() throws Exception{
		email.getSocketConnectionTimeout();
	}
	
	@Test
	public void testSetFrom() throws Exception{
		email.setFrom(TEST_EMAILS[0]);
		assertEquals(TEST_EMAILS[0], email.getFromAddress().toString());
	}
	
	
}
