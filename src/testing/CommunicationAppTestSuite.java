package testing;


import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

// Specify Suite
@Suite

// Specify Class Selection
@SelectClasses({UserConstructor.class, UserGetUID.class, UserGetUserName.class, UserGetPassword.class, UserGetStatus.class,
	UserIsIT.class, UserSetUsername.class, UserSetUID.class})



public class CommunicationAppTestSuite {}
