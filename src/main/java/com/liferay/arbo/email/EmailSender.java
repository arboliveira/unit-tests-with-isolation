package com.liferay.arbo.email;

public interface EmailSender
{

	void send(Message message, Address target);

}
