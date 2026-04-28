package testing;


import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

// Specify Suite
@Suite

// Specify Class Selection
@SelectClasses({UserConstructor.class, UserGetUID.class, UserGetUserName.class, UserGetPassword.class, UserGetStatus.class,
	UserIsIT.class, UserSetUsername.class, UserSetUID.class, UserSetPassword.class, UserSetStatus.class, ITUserConstructor.class,
	ITUserGetITUID.class, RequestConstructor.class, RequestGetUID.class, RequestGetCreatedDate.class, RequestGetData.class,
	RequestGetSenderType.class, RequestGetRecipientType.class, RequestGetType.class, RequestGetSenderID.class, RequestGetRecipientID.class,
	})



public class CommunicationAppTestSuite {}
