package EmaisOperate;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class simpleMailReceiver {
	public void receiveMail(MailReceiverInfo mailReceiverInfo) throws MessagingException, IOException{
		Properties props=mailReceiverInfo.getProperties();
		Session session=Session.getDefaultInstance(props,null);
		Store store=session.getStore("pop3");
		store.connect(mailReceiverInfo.getMailServerHost(),mailReceiverInfo.getUserName(),mailReceiverInfo.getPassword());
		Folder folder=store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message message[]=folder.getMessages();
		for(int i=0;i<2;i++){
			System.out.println("µÚ"+i+"·âÓÊ¼þ:");
			System.out.println("From:"+message[i].getFrom()[0]);
			System.out.println("Subject:"+message[i].getSubject());
			System.out.println("Content:"+message[i].getContent());
		}
		folder.close(false); 
		store.close();

	}

}
