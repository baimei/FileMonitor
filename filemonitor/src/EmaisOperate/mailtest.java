package EmaisOperate;

import java.io.IOException;

import javax.mail.MessagingException;

public class mailtest {

	public static void main(String[] args){  
        //�������Ҫ�������ʼ�  
 /*    MailSenderInfo mailInfo = new MailSenderInfo();   
     mailInfo.setMailServerHost("smtp.163.com");   
     mailInfo.setMailServerPort("25");   
     mailInfo.setValidate(true);   
     mailInfo.setUserName("xudaxia855866@163.com");   
     mailInfo.setPassword("09170114");//������������   
     mailInfo.setFromAddress("xudaxia855866@163.com");   
     mailInfo.setToAddress("xudaxia855866@126.com");   
     mailInfo.setSubject("�����������");   
     mailInfo.setContent("������������");   
        //�������Ҫ�������ʼ�  
     SimpleMailSender sms = new SimpleMailSender();  
         sms.sendTextMail(mailInfo);//���������ʽ   
         sms.sendHtmlMail(mailInfo);//����html��ʽ  
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
