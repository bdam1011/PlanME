package tw.org.iii.cma.Service;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tw.org.iii.cma.dao.MessageRepository;
import tw.org.iii.cma.domain.MessageBean;

@SpringBootTest
public class MessageRepositoryServiceTest {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Test
	public void inserTest() {
		MessageBean messageBean = new MessageBean();
		messageBean.setDeleted(false);
		messageBean.setIsReported(false);
		messageBean.setMSlike(1);
		messageBean.setMST(new Date());
		messageBean.setMStext("twomessage");
		messageRepository.save(messageBean);
	}
	
	
}
