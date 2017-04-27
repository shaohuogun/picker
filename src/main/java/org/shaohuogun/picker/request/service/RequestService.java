package org.shaohuogun.picker.request.service;

import java.util.List;

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
	public Request createRequest(Request request) throws Exception {
		if (request == null) {
			throw new NullPointerException("Request cann't be null.");
		}

		requestDao.insert(request);
		return requestDao.selectById(request.getId());
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
	public Request modifyRequest(Request request) throws Exception {
		if (request == null) {
			throw new NullPointerException("Request cann't be null.");
		}

		requestDao.update(request);
		return requestDao.selectById(request.getId());
	}

}
