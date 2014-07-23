package com.massmailingcorp.api;

import java.util.List;

public interface CommercialMailingService
{

	void send(String subject, String body, List<String> addresses);

}
