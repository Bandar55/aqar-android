package com.aqar55.constant;

import java.io.Serializable;

public interface Constants extends Serializable {

    public static final int animDuration = 500;

    String kAppPreferences = "PeepsInPreferences";
    String kDefaultAppName = "HomeFuud";

    String kCurrentUser = "currentUser";
    String kData = "data";
    String kType = "type";
    String kStatus = "status";
    String kMessage = "message";
    String kMsg = "msg";
    String kSuccess = "SUCCESS";
    String kLikesDislikes = "likesDislikes";
    String kLikesMap = "kMap";
    String kConfirmReservation = "confirmReservation";

    String kSeperator = "__";
    String kEmptyString = "";
    String kWhitespace = " ";
    Number kEmptyNumber = 0;

    String kMessageInternalInconsistency = "Some internal inconsistency occurred. Please try again.";
    String kMessageNetworkError = "Device does not connect to internet.";
    String kSocketTimeOut = kDefaultAppName + " Server not responding..";
    String kMessageServerNotRespondingError = kDefaultAppName + " server not responding!";
    String kMessageConnecting = "Connecting...";
    String kError = "Error";

    String kName = "name";
    String kToken = "token";
    String kRequestKey = "requestKey";
    String kIsFirstTime = "isFirstTime";
    String kRememberMe = "rememberMe";


}
