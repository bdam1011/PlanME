package tw.org.iii.cma.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.AtrphotoRepository;
import tw.org.iii.cma.dao.AttractionsRepository;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.TravelUnitRepository;
import tw.org.iii.cma.domain.AtrphotoBean;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.MemberBean;
//import tw.org.iii.cma.utils.JsonUtils;
import tw.org.iii.cma.domain.TravelUnitBean;

@Service
@Transactional
public class AttractionsRepositoryService {
	@Autowired
	private AttractionsRepository attractionsRepository;

	@Autowired
	private AtrphotoRepository atrphotoRepository;

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TravelUnitRepository travelUnitRepository;
	
	// select all or select one by id
	public List<AttractionsBean> select(AttractionsBean bean) {
		List<AttractionsBean> result = null;
		if (bean != null && bean.getATRid() != null && !bean.getATRid().equals(0)) {
			Optional<AttractionsBean> optional = attractionsRepository.findById(bean.getATRid());
			if (optional.isPresent()) {
				result = new ArrayList<AttractionsBean>();
				result.add(optional.get());
			}
		} else {

			result = attractionsRepository.findAll();
		}
		return result;
	}

	// select by page 每頁16筆
	public List<AttractionsBean> selectPage(Integer page) {
		List<AttractionsBean> result = null;
		Page<AttractionsBean> pageResult = null;
		Pageable pageable = PageRequest.of(page, 16, Sort.by("ATRid").ascending());
		pageResult = attractionsRepository.findAll(pageable);
		result = pageResult.getContent();
		
		return result;
	}

	// 模糊搜尋景點名稱
	public List<AttractionsBean> selectAttractionsName(AttractionsBean bean) {
		List<AttractionsBean> result = null;
		if (bean != null && bean.getATRname() != null) {
			result = attractionsRepository.findByATRnameContaining(bean.getATRname());
		}
		return result;
	}
	
	// 搜尋縣市景點
	public List<AttractionsBean> selectCityAttractions(AttractionsBean bean) {
		List<AttractionsBean> result = null;
		if (bean != null && bean.getATRaddress() != null) {
			result = attractionsRepository.findByATRaddressContaining(bean.getATRaddress());
		}
		return result;
	}
	

	// select photo
	public List<AtrphotoBean> selectPhoto(AttractionsBean bean) {
		List<AtrphotoBean> result = null;
		if (bean != null && bean.getATRid() != null && !bean.getATRid().equals(0)) {
			Optional<AtrphotoBean> optional = atrphotoRepository.findById(bean.getATRid());
			if (optional.isPresent()) {
				result = new ArrayList<AtrphotoBean>();
				result.add(optional.get());
			}
		}
		return result;
	}

	// select photo and attraction
	public List<AttractionsBean> selectPhotoAndAtrs(AttractionsBean bean) {
		List<AttractionsBean> result = null;
		if (bean != null && bean.getATRid() != null && !bean.getATRid().equals(0)) {
			Optional<AttractionsBean> optional = attractionsRepository.findById(bean.getATRid());
			if (optional.isPresent()) {
				result = new ArrayList<AttractionsBean>();
				result.add(optional.get());
			}
		}
		return result;
	}

	public AttractionsBean insert(AttractionsBean bean) {
		AttractionsBean result = null;
		if (bean != null && bean.getATRid() == null) {
			return attractionsRepository.save(bean);
		}
		return result;
	}

	public AttractionsBean update(AttractionsBean bean) {
		AttractionsBean result = null;
		if (bean != null && bean.getATRid() != null) {
			AttractionsBean select = attractionsRepository.findByATRid(bean.getATRid());
			if (select!=null) {
				bean.setATRlike(select.getATRlike());
				bean.setAtrphotoBeans(select.getAtrphotoBeans());
				bean.setTravelUnitBeans(select.getTravelUnitBeans());			
				return attractionsRepository.save(bean);
			}
		}
		return result;
	}

	public boolean delete(AttractionsBean bean) {
		boolean result = false;
		if (bean != null && bean.getATRid() != null) {
			boolean exist = attractionsRepository.existsById(bean.getATRid());
			if (exist) {
				attractionsRepository.deleteById(bean.getATRid());
				result = true;
			}
		}
		return result;
	}

	// insert 景點 OpenData
	public AttractionsBean insertOpeanData(AttractionsBean bean) throws IOException {
		AttractionsBean result = null;
		if (bean != null && bean.getATRid() == null) {
//			String url = "https://gis.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json";
//			String jsonData = JsonUtils.fetchOpendata(url);
			File file = new File("C:\\jsonformatter.txt");// 讀取檔案位置
			BufferedReader reader = new BufferedReader(new java.io.FileReader(file));// 使用BuffereReader 加入讀取
			String data;
			StringBuffer jsonData = new StringBuffer();
			while ((data = reader.readLine()) != null) // 讀取json檔
			{
				jsonData.append(data); // 將讀取到的資料存入jsonData
			}
			if (jsonData != null) {
				JSONObject obj = new JSONObject(jsonData.toString());
				JSONObject XML_Head = obj.getJSONObject("XML_Head");
				JSONObject infos = XML_Head.getJSONObject("Infos");
				JSONArray info = infos.getJSONArray("Info");
				for (int i = 0; i < info.length(); i++) {
					MemberBean m1 = memberRepository.findByMBid(1);
					AttractionsBean tempbean = new AttractionsBean();
					List<AtrphotoBean> tempAtrphotoBeans = new ArrayList();
					AtrphotoBean pic1 = new AtrphotoBean();
					AtrphotoBean pic2 = new AtrphotoBean();
					AtrphotoBean pic3 = new AtrphotoBean();

					tempbean.setATRname((info.getJSONObject(i).getString("Name")));
					Boolean temp = attractionsRepository.existsByATRname(tempbean.getATRname());
					if (!temp) {

						try {
							tempbean.setATRaddress((info.getJSONObject(i).getString("Add")));
							tempbean.setATRintroduce((info.getJSONObject(i).getString("Toldescribe")));
							tempbean.setATRlongitude((info.getJSONObject(i).getDouble("Px")));
							tempbean.setATRlantitude((info.getJSONObject(i).getDouble("Py")));
							tempbean.setATRtel((info.getJSONObject(i).getString("Tel")));
							tempbean.setATRopentime((info.getJSONObject(i).getString("Opentime")));
//							tempbean.setATRGM((info.getJSONObject(i).getString("Map")));
							attractionsRepository.save(tempbean);

							if (!info.getJSONObject(i).getString("Picture1").isEmpty()) {
								pic1.setTitle(info.getJSONObject(i).getString("Picdescribe1"));
								pic1.setPhoto(info.getJSONObject(i).getString("Picture1"));
								pic1.setMemberBean(m1);
								pic1.setAttractionsBean(tempbean);
								atrphotoRepository.save(pic1);
								tempAtrphotoBeans.add(pic1);
								if (!info.getJSONObject(i).getString("Picture2").isEmpty()) {
									pic2.setTitle(info.getJSONObject(i).getString("Picdescribe2"));
									pic2.setPhoto(info.getJSONObject(i).getString("Picture2"));
									pic2.setMemberBean(m1);
									pic2.setAttractionsBean(tempbean);
									atrphotoRepository.save(pic2);
									tempAtrphotoBeans.add(pic2);
									if (!info.getJSONObject(i).getString("Picture3").isEmpty()) {
										pic3.setTitle(info.getJSONObject(i).getString("Picdescribe3"));
										pic3.setPhoto(info.getJSONObject(i).getString("Picture3"));
										pic3.setMemberBean(m1);
										pic3.setAttractionsBean(tempbean);
										atrphotoRepository.save(pic3);
										tempAtrphotoBeans.add(pic3);
									}
								}
								tempbean.setAtrphotoBeans(tempAtrphotoBeans);
								attractionsRepository.save(tempbean);
							}else {
								pic1.setTitle(info.getJSONObject(i).getString("Picdescribe1"));
								pic1.setPhoto("https://image.cache.storm.mg/styles/smg-800x533-fp/s3/media/image/2020/11/07/20201107-092915_U13380_M651499_4ac4.jpg?itok=6KFZde7p");
								pic1.setMemberBean(m1);
								pic1.setAttractionsBean(tempbean);
								atrphotoRepository.save(pic1);
								tempAtrphotoBeans.add(pic1);
								tempbean.setAtrphotoBeans(tempAtrphotoBeans);
								attractionsRepository.save(tempbean);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.toString();
						}
					}
					bean = tempbean;
				}
				return attractionsRepository.save(bean);
			}
		}
		return result;
	}

}
