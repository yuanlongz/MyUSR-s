package service;

import dataSource.UnitOfWork;
import domain.Service;
import domain.ServiceStatus;

public class ServiceLogic {
	private static UnitOfWork uow;
	public static void completeService(Service service) {
		UnitOfWork.newCurrent();
		
		//TODO:update actual processed items
		//calcualte final bill
		service.calcuateBill();
		//set service status to COMPLETE
		service.setStatus(ServiceStatus.COMPLETE);
		service.update();
		//TODO:send notification
		
		
		try {
			UnitOfWork.getCurrent().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void cancelService(Service service) {
		UnitOfWork.newCurrent();
		service.setStatus(ServiceStatus.CANCEL);
		service.update();
		service.delete();
		//TODO:send notification
		try {
			UnitOfWork.getCurrent().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
