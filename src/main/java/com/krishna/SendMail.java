package com.krishna;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.util.StringUtils;
import com.krishna.beans.ContactInfoBean;

public class SendMail implements RequestHandler<ContactInfoBean, String> {

	@Override
	public String handleRequest(ContactInfoBean contactInfo, Context context) {
		String to="shiva.krishnaah@gmail.com";
        String from="shiva.krishnaah@gmail.com";
        Destination destination = new Destination().withToAddresses(new String[]{to});
        if(!StringUtils.isNullOrEmpty(contactInfo.getEmail())){
        	destination = new Destination().withToAddresses(new String[]{to,contactInfo.getEmail()});
        }
       
        // Create the subject and body of the message.
        Content subject = new Content().withData("Test Email ");
        Content textBody = new Content().withData("This is sample  "+contactInfo.getDesignation()); 
        Body body = new Body().withText(textBody);
        
        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);
        AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();
        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);
      try{
    	  client.sendEmail(request);
    	  return "Email sent sucessfully";
      }catch(Exception ex){
    	  ex.printStackTrace();
      }
        return "Email sending failed";
	}

}
