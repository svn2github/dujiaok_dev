package com.ssnn.dujiaok.web.action.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.ArrayStringUtils;
import com.ssnn.dujiaok.web.action.BasicAction;

/**
 * 自驾
 * @author shenjia.caosj 2012-1-20
 *
 */

@SuppressWarnings("serial")
public class SelfDriveAction extends BasicAction implements ModelDriven<SelfDriveDO>{

	private SelfDriveDO selfDrive = new SelfDriveDO();
	
	private SelfDriveService selfDriveService ;
	
	private List<String> payTypesList ;
	private List<String> imagesList ;
	private List<String> productTypesList ;
	private List<String> addProductsList ;
	
	
	@Override
	public String execute() throws Exception {
		if(selfDrive!=null && StringUtils.isNotBlank(selfDrive.getProductId())){
			selfDrive = selfDriveService.getSelfDriveWithDetails(selfDrive.getProductId()) ;
			if(selfDrive != null){
				payTypesList = ArrayStringUtils.toList(selfDrive.getPayTypes()) ;
				productTypesList = ArrayStringUtils.toList(selfDrive.getProductTypes()) ;
				imagesList = ArrayStringUtils.toList(selfDrive.getImages()) ;
				addProductsList = ArrayStringUtils.toList(selfDrive.getAddProducts()) ;
			}
		}
		return SUCCESS ;
	}
	
	/**
	 * 发布自驾
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		
		selfDrive.setImages(ArrayStringUtils.toString(imagesList)) ;
		selfDrive.setPayTypes(ArrayStringUtils.toString(payTypesList)) ;
		selfDrive.setProductTypes(ArrayStringUtils.toString(productTypesList)) ;
		selfDrive.setAddProducts(ArrayStringUtils.toString(addProductsList)) ;
		if(StringUtils.isBlank(selfDrive.getProductId())){
			selfDrive = selfDriveService.createSelfDriveAndDetails(selfDrive) ;
		}else{
			selfDrive = selfDriveService.updateSelfDriveAndDetails(selfDrive) ;
		}
		
		return SUCCESS ;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String success() throws Exception {
		return SUCCESS ;
	}
	
	
	@Override
	public SelfDriveDO getModel() {
		return selfDrive ;
	}
	
	
	/**-------------------------------------------------------------------------------------**/
	public SelfDriveDO getSelfDrive() {
		return selfDrive;
	}
	public void setSelfDrive(SelfDriveDO selfDrive) {
		this.selfDrive = selfDrive;
	}
	public List<String> getPayTypesList() {
		return payTypesList;
	}
	public void setPayTypesList(List<String> payTypesList) {
		this.payTypesList = payTypesList;
	}
	public List<String> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	public List<String> getProductTypesList() {
		return productTypesList;
	}
	public void setProductTypesList(List<String> productTypesList) {
		this.productTypesList = productTypesList;
	}
	public List<String> getAddProductsList() {
		return addProductsList;
	}
	public void setAddProductsList(List<String> addProductsList) {
		this.addProductsList = addProductsList;
	}
	public void setSelfDriveService(SelfDriveService selfDriveService) {
		this.selfDriveService = selfDriveService;
	}

}
