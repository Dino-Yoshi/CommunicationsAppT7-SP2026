package testing;


import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

// Specify Suite
@Suite

// Specify Class Selection
@SelectClasses({UserConstructor.class})



public class CommunicationAppTestSuite {}
