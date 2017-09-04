package org.shaohuogun.picker.request.service;

import java.util.List;

import org.shaohuogun.common.Entity;
import org.shaohuogun.common.Pagination;
import org.shaohuogun.picker.request.dao.RequestDao;
import org.shaohuogun.picker.request.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestService {

	@Autowired
	private RequestDao requestDao;

	@Transactional
	public Request createRequest(Request req) throws Exception {
		if (req == null) {
			throw new NullPointerException("Request cann't be null.");
		}

		requestDao.insert(req);
		return requestDao.selectById(req.getId());
	}

	public Request getRequest(String id) throws Exception {
		if ((id == null) || id.isEmpty()) {
			throw new IllegalArgumentException("Request's id cann't be null or empty.");
		}

		return requestDao.selectById(id);
	}

	public List<Request> getRequests(Pagination pagination) throws Exception {
		if (pagination == null) {
			throw new NullPointerException("Pagination cann't be null.");
		}

		// TODO Auto-generated method stub
		return null;
	}

	public Request getRequestByStatus(String status) throws Exception {
		if ((status == null) || status.isEmpty()) {
			throw new IllegalArgumentException("Status cann't be null or empty.");
		}

		return requestDao.selectByStatus(status);
	}

	@Transactional
	public Request modifyRequest(Request req) throws Exception {
		if (req == null) {
			throw new NullPointerException("Request cann't be null.");
		}

		requestDao.update(req);
		return requestDao.selectById(req.getId());
	}
	
	public int getRequestCountOfCreator(String creator) throws Exception {
		if ((creator == null) || creator.isEmpty()) {
			throw new IllegalArgumentException("Creator cann't be null or empty.");
		}
		
		return requestDao.countByCreator(creator);
	}
	
	public Pagination getRequestsOfCreator(String creator, Pagination pagination) throws Exception {
		if ((creator == null) || creator.isEmpty()) {
			throw new IllegalArgumentException("Creator cann't be null or empty.");
		}
		
		if (pagination == null) {
			throw new NullPointerException("Pagination cann't be null.");
		}
		
		int offset = (pagination.getPageIndex() - 1) * pagination.getPageSize();
		int limit = pagination.getPageSize();
		List<Entity> requests = requestDao.selectByCreator(creator, offset, limit);
		pagination.setObjects(requests);
		return pagination;
	}

}
