package EmaisOperate;

import java.util.Properties;

public class MailReceiverInfo {
	 // �����ʼ��ķ�������IP�Ͷ˿�   
    private String mailServerHost="pop.126.com ";   
    private String mailServerPort = "110";   
    // ��½�ʼ����ͷ��������û���������   
    private String userName;   
    private String password;   

    /**  
      * ����ʼ��Ự����  
      */   
    public Properties getProperties(){   
      Properties p = new Properties();   
      p.put("mail.smtp.host", this.mailServerHost);   
      p.put("mail.smtp.port", this.mailServerPort);   
     
      return p;   
    }   
    public String getMailServerHost() {   
      return mailServerHost;   
    }   
    public void setMailServerHost(String mailServerHost) {   
      this.mailServerHost = mailServerHost;   
    }  
    public String getMailServerPort() {   
      return mailServerPort;   
    }  
    public void setMailServerPort(String mailServerPort) {   
      this.mailServerPort = mailServerPort;   
    }  


    public String getPassword() {   
      return password;   
    }  
    public void setPassword(String password) {   
      this.password = password;   
    }  

    public String getUserName() {   
      return userName;   
    }  
    public void setUserName(String userName) {   
      this.userName = userName;   
    }  


}
