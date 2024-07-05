package com.example.application.proxies;

import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.domains.contracts.proxies.CalculatorProxy;
import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;
import com.example.webservice.schema.DivRequest;
import com.example.webservice.schema.DivResponse;
import com.example.webservice.schema.DivideRequest;
import com.example.webservice.schema.DivideResponse;
import com.example.webservice.schema.MultRequest;
import com.example.webservice.schema.MultResponse;
import com.example.webservice.schema.MultiplyRequest;
import com.example.webservice.schema.MultiplyResponse;
import com.example.webservice.schema.SubRequest;
import com.example.webservice.schema.SubResponse;
import com.example.webservice.schema.SubtractRequest;
import com.example.webservice.schema.SubtractResponse;

public class CalculatorProxyImpl extends WebServiceGatewaySupport implements CalculatorProxy {
	private static final WebServiceMessageCallback requestCallback = new SoapActionCallback("http://example.com/webservices/schemas/calculator");

	@Override
	public double add(double a, double b) {
		var request = new AddRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (AddResponse) getWebServiceTemplate().marshalSendAndReceive(request, requestCallback);
		return response.getAddResult();
	}

	@Override
	public double subtract(double a, double b) {
		var request = new SubRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (SubResponse) getWebServiceTemplate().marshalSendAndReceive(request, requestCallback);
		return response.getSubResult();
	}

	@Override
	public double multiply(double a, double b) {
		var request = new MultRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (MultResponse) getWebServiceTemplate().marshalSendAndReceive(request, requestCallback);
		return response.getMultResult();
	}

	@Override
	public double divide(double a, double b) {
		var request = new DivRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (DivResponse) getWebServiceTemplate().marshalSendAndReceive(request, requestCallback);
		return response.getDivResult();
	}

}
