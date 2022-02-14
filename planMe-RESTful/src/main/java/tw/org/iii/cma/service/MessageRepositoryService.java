package tw.org.iii.cma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.MessageRepository;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.MessageBean;



@Service
@Transactional
public class MessageRepositoryService {
	@Autowired
	private MessageRepository messageRepository;
	
	public List<MessageBean> select(MessageBean bean) {
		List<MessageBean> result = null;
		if(bean!=null && bean.getMSGid()!=null && !bean.getMSGid().equals(0)) {
			Optional<MessageBean> optional = messageRepository.findById(bean.getMSGid());
			if(optional.isPresent()) {
				result = new ArrayList<MessageBean>();
				result.add(optional.get());
			}
		} else {
			result = messageRepository.findAll();
		}
		return result;
	}
	
	public List<MessageBean> selectReported(Integer page) {
		List<MessageBean> result = null;
		Page<MessageBean> pageResult = null;
		Pageable pageable = PageRequest.of(page, 20, Sort.by("MSGid").ascending());
		pageResult = messageRepository.findByisReported(true,pageable);
		
		if(pageResult!=null) {
			result = pageResult.getContent();
		}
		return result;
	}
	
	public MessageBean insert(MessageBean bean) {
		MessageBean result = null;
		if(bean!=null && bean.getMSGid()==null) {
			return messageRepository.save(bean);
		}
		return result;
	}
	
	
	public MessageBean update(Integer msgid, Boolean reported) {
		MessageBean result = null;
		if(msgid!=null) {
			MessageBean select = messageRepository.findByMSGid(msgid);
			if(select!=null) {
				select.setIsReported(reported);
				return messageRepository.save(select);
			}
		}
		return result;
	}
	
	public MessageBean updateLike(Integer MSGid,Boolean like) {
		MessageBean result = null;
		if (MSGid!=null) {
			Optional<MessageBean> optional = messageRepository.findById(MSGid);
			if (optional.isPresent()) {
				// 設定uploadtime 不變
				result = optional.get();
				java.util.Date uploadTime = result.getMST();
				result.setMST(uploadTime);
				if(like) {
					result.setMSlike(result.getMSlike()+1);
				}else {
					result.setMSlike(result.getMSlike()-1);
				}
				
				return messageRepository.save(result);
			}
		}
		return result;
	}
	
	//soft deleted
	public boolean softDelete(MessageBean bean) {
		boolean  result = false;
		if(bean!=null && bean.getMSGid()!=null) {
			boolean exist = messageRepository.existsById(bean.getMSGid());
			if(exist) {
				Optional<MessageBean> optional = messageRepository.findById(bean.getMSGid());
				optional.get().setDeleted(true);
				messageRepository.save(optional.get());
				result = true;
			}
		}
		return result;
	}
	
//	//hard delete
//	public boolean delete(MessageBean bean) {
//		boolean result = false;
//		if(bean!=null && bean.getMSGid()!=null && bean.getMBid()!=null) {
//			boolean exist = messageRepository.existsByMSGidAndMBid(bean.getMSGid(),bean.getMBid());
//			if(exist) {
//				messageRepository.deleteById(bean.getMSGid());
//				result = true;
//			}
//		}
//		return result;
//	}
}
