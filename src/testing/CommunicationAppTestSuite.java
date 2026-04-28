package testing;


import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

// Specify Suite
@Suite

// Specify Class Selection
@SelectClasses({
	UserConstructor.class, 
	UserGetUID.class, 
	UserGetUserName.class, 
	UserGetPassword.class, 
	UserGetStatus.class,
	UserIsIT.class, 
	UserSetUsername.class, 
	UserSetUID.class, 
	UserSetPassword.class, 
	UserSetStatus.class, 
	
	ITUserConstructor.class,
	ITUserGetITUID.class, 
	
	RequestConstructor.class, 
	RequestGetUID.class, 
	RequestGetCreatedDate.class, 
	RequestGetData.class,
	RequestGetSenderType.class, 
	RequestGetRecipientType.class, 
	RequestGetType.class, 
	RequestGetSenderID.class, 
	RequestGetRecipientID.class,
	
	AuthRegisterUser.class,
    AuthDuplicateUser.class,
    AuthValidUser.class,
    AuthIncorrectPassword.class,
    AuthLogout.class,
    AuthGetUID.class,

    ConnectAddClient.class,
    ConnectDuplicateClient.class,
    ConnectGetClient.class,
    ConnectRemoveClient.class,

    ContactAddContact.class,
    ContactDuplicateContact.class,
    ContactRemoveContact.class,
    ContactSearchContact.class,

    GroupCreateGroup.class,
    GroupAddMember.class,
    GroupRemoveMember.class,
    GroupSearchGroup.class,

    LogConstructor.class,
    LogGetters.class,
    LogToString.class,

    LoggerLogInfo.class,
    LoggerLogWarning.class,
    LoggerLogError.class,
    LoggerSaveLogs.class,
    LoggerGetSavedLogs.class,

    StorageConstructor.class,
    StorageSaveUser.class,
    StorageLoadUsers.class,
    StorageOfflineMessages.class,
    StorageSaveMessage.class,
    StorageLoadChatHistory.class,

	})



public class CommunicationAppTestSuite {}
