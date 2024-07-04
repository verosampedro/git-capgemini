package com.proyectoUno.application.proxies;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;
import com.example.webservice.schema.DivRequest;
import com.example.webservice.schema.DivResponse;
import com.example.webservice.schema.MultRequest;
import com.example.webservice.schema.MultResponse;
import com.example.webservice.schema.SubRequest;
import com.example.webservice.schema.SubResponse;
import com.proyectoUno.domains.contracts.proxies.CalculatorProxy;

public class CalculatorProxyImpl extends WebServiceGatewaySupport implements CalculatorProxy {
	public double add(double a, double b) {
		var request = new AddRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (AddResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getAddResult();
	}
	
	public double sub(double a, double b) {
		var request = new SubRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (SubResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getSubResult();
	}

	public double mult(double a, double b) {
		var request = new MultRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (MultResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getMultResult();
	}
	
	public double div(double a, double b) {
		var request = new DivRequest();
		request.setOp1(a);
		request.setOp2(b);
		var response = (DivResponse) getWebServiceTemplate().marshalSendAndReceive(request,
				new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
		return response.getDivResult();
	}
}