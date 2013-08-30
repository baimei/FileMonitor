package EmaisOperate;

import java.io.IOException;

import javax.mail.MessagingException;

public class mailtest {

	public static void main(String[] args){  
        //这个类主要是设置邮件  
 /*    MailSenderInfo mailInfo = new MailSenderInfo();   
     mailInfo.setMailServerHost("smtp.163.com");   
     mailInfo.setMailServerPort("25");   
     mailInfo.setValidate(true);   
     mailInfo.setUserName("xudaxia855866@163.com");   
     mailInfo.setPassword("09170114");//您的邮箱密码   
     mailInfo.setFromAddress("xudaxia855866@163.com");   
     mailInfo.setToAddress("xudaxia855866@126.com");   
     mailInfo.setSubject("设置邮箱标题");   
     mailInfo.setContent("设置邮箱内容");   
        //这个类主要来发送邮件  
     SimpleMailSender sms = new SimpleMailSender();  
         sms.sendTextMail(mailInfo);//发送文体格式   
         sms.sendHtmlMail(mailInfo);//发送html格式  
   */
		MailReceiverInfo mailReceiverInfo=new MailReceiverInfo();
		mailReceiverInfo.setMailServerHost("pop3.126.com");
		//mailReceiverInfo.setMailServerHost("pop.163.com");
		mailReceiverInfo.setMailServerPort("110");
		mailReceiverInfo.setUserName("xudaxia855866@126.com");
		mailReceiverInfo.setPassword("09170114");
		simpleMailReceiver mailReceiver=new simpleMailReceiver();
		try {
			mailReceiver.receiveMail(mailReceiverInfo);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
